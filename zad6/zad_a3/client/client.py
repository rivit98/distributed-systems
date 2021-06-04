import random
import time
from datetime import timedelta, datetime
from dateutil.relativedelta import relativedelta
import grpc
import os
import uuid
import names
import json
import sys

from Office_pb2 import *
import Office_pb2_grpc


class SimpleDB:
    def __init__(self, uuid=None, name=None, surname=None, cases=None, **kwargs):
        if cases is None:
            self.cases = {}
        else:
            self.cases = cases

        self.uuid = uuid
        self.name = name
        self.surname = surname
        self.loaded = False

    def save(self):
        try:
            with open(DB_PATH, "wt") as f:
                json.dump(self.__dict__, f)
        except Exception as e:
            print("Cannot save data", e)


def db_load():
    try:
        with open(DB_PATH, "rt") as f:
            data = json.load(f)
            db = SimpleDB(**data)
            db.cases = {int(k): v for k, v in db.cases.items()}
            db.loaded = True
            return db
    except Exception as e:
        print("Cannot load data", e)


class OfficeClient:
    def __init__(self, db):
        self.db: SimpleDB = db
        if not self.db.loaded:
            self.uuid = self.db.uuid = str(uuid.uuid4())
            name, surname = names.get_full_name().split(' ')
            self.name, self.surname = name, surname
            self.db.name, self.db.surname = name, surname
            self.db.save()
        else:
            self.uuid = self.db.uuid
            self.name, self.surname = self.db.name, self.db.surname

        self.me = Client(
            clientID=self.uuid,
            name=self.name,
            surname=self.surname
        )

        self.stub = None
        self.stream = None
        self.channel = None
        self.exit = False


    def random_birth_date(self):
        years = random.randint(10, 24)
        months = random.randint(0, 12)
        end = datetime.now() - relativedelta(years=years, months=months)
        return end.strftime("%d/%m/%Y")


    def gen_driver_license_request(self):
        return CaseRequestData(
            client=self.me,
            driverLicenseData=DriverLicenseData(
                birthDate=self.random_birth_date(),
                category=random.choice(['A', 'B', 'C'])
            )
        )

    def gen_id_card_request(self):
        return CaseRequestData(
            client=self.me,
            idCardData=IDCardData(
                birthDate=self.random_birth_date(),
                placeOfResidence=random.choice(["Krakow", "Warszawa", "Rzeszow", "Szczecin"])
            )
        )

    def gen_register_company_request(self):
        return CaseRequestData(
            client=self.me,
            registerCompanyData=RegisterCompanyData(
                companyName=random.choice(['Krakpol', 'Krakex', 'Krakbud']),
                startUpCapital=random.uniform(10_000, 100_000)
            )
        )

    def gen_client_connect_request(self):
        return CaseRequestData(
            client=self.me,
            clientReady=ClientReady()
        )

    def gen(self):
        yield self.gen_client_connect_request()

        gen_map = {
            1: self.gen_driver_license_request,
            2: self.gen_id_card_request,
            3: self.gen_register_company_request
        }

        print("""
1. driver license
2. id card
3. register company
0. exit
""")

        while 1:
            try:
                answer = int(input())
                if answer == 0:
                    self.db.save()
                    self.exit = True
                    self.channel.close()
                    sys.exit(1)
                else:
                    f = gen_map.get(answer, None)
                    if f:
                        yield f()
                    else:
                        print("Invalid option", answer)
            except Exception as e:
                print(e)

    def start(self, connection_str):
        options = (
            ('grpc.keepalive_time_ms', 10000),                      # send keepalive ping every 10 second, default is 2 hours
            ('grpc.keepalive_timeout_ms', 5000),                    # keepalive ping time out after 5 seconds, default is 20 seoncds
            ('grpc.keepalive_permit_without_calls', True),          # allow keepalive pings when there's no gRPC calls
            ('grpc.http2.max_pings_without_data', 0),               # allow unlimited amount of keepalive pings without data
            ('grpc.http2.min_time_between_pings_ms', 10000),        # allow grpc pings from client every 10 seconds
            ('grpc.http2.min_ping_interval_without_data_ms', 5000), # allow grpc pings from client without data every 5 seconds
        )

        with grpc.insecure_channel(connection_str, options) as channel:
            self.channel = channel
            self.stub = Office_pb2_grpc.OfficeStub(channel)

            cases_left = len(self.db.cases)
            if cases_left:
                print(f"Cases left ({cases_left}) ->", ', '.join(map(str, self.db.cases.keys())))

            try:
                resolvedCases = self.stub.getResolvedCases(self.me)
                resolvedCases = resolvedCases.results
                if not resolvedCases:
                    if cases_left:
                        print("No resolved cases for you!")
                else:
                    for caseResult in resolvedCases:
                        caseID = caseResult.case.id
                        message = caseResult.caseResolvedResult.message
                        print(f"[caseResult] Case id={caseID} has been examined! Message: {message}")
                        del self.db.cases[caseID]

                    self.db.save()

                self.stream = self.stub.caseRequest(self.gen())
                for res in self.stream:
                    caseID = res.case.id
                    whichone = res.WhichOneof('result')
                    if whichone == 'caseAck':
                        data: CaseAck = getattr(res, whichone)
                        resTime = data.resolutionTime // 1000
                        print(f"[caseAck] Your case got id={caseID}, resolution time ~{resTime}s")

                        self.db.cases[caseID] = resTime
                        self.db.save()

                    elif whichone == 'caseResolvedResult':
                        data: CaseResult = getattr(res, whichone)
                        message = data.message
                        print(f"[caseResolved] Case id={caseID} has been examined! Message: {message}")

                        del self.db.cases[caseID]
                        self.db.save()

            except grpc.RpcError as e:
                status = e.code()
                details = e.details()
                if status == grpc.StatusCode.UNAVAILABLE:
                    print("Cannot connect to gRPC server - retrying")
                    time.sleep(3)
                elif details == 'Stream removed':
                    print("Server disconnected - reconnecting...")
                    time.sleep(3)
                else:
                    print(details)


if __name__ == '__main__':
    if len(sys.argv) != 2:
        print("please provide clientID")
        sys.exit(1)

    id = int(sys.argv[1])
    # id = 1
    DB_PATH = f"./client_db{id}.json"
    if os.path.exists(DB_PATH):
        db = db_load()
    else:
        db = SimpleDB()

    client = OfficeClient(db)
    while not client.exit:
        client.start("localhost:50051")
