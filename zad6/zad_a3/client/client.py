import random
import sys
import time

import grpc
import os
import uuid
import names
import json

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

    def gen_driver_license_request(self):
        return CaseRequestData(
            driverLicenseData=DriverLicenseData(
                client=self.me,
                category=random.choice(['A', 'B', 'C'])
            )
        )

    def gen(self):
        yield ClientReady(
            clientReady=self.me
        )

        gen_map = {
            1: self.gen_driver_license_request
        }

        # time.sleep(1)
        # yield self.gen_driver_license_request()
        # return

        print("""
1. driver license
2. id card
3. cos
0. wyjscie
""")

        while 1:
            try:
                answer = int(input())
                if answer == 0:
                    return
                    # self.db.save(DB_PATH)
                    # sys.exit(0)
                else:
                    f = gen_map.get(answer, None)
                    if f:
                        yield f()
                    else:
                        print("Invalid option", answer)
            except:
                pass

    def start(self, connection_str):
        cases_left = len(self.db.cases)
        if cases_left:
            print(f"Cases left ({cases_left}) ->", ', '.join(map(str, self.db.cases.keys())))

        with grpc.insecure_channel(connection_str) as channel:
            self.stub = Office_pb2_grpc.OfficeStub(channel)

            examinedCases = self.stub.helloRequest(self.me)
            examinedCases = examinedCases.results
            if not examinedCases:
                if cases_left:
                    print("No examined cases for you!")
            else:
                for case in examinedCases:
                    caseID = case.case.id
                    message = case.message
                    print(f"[caseResult] Case id={caseID} has beed examined! Message: {message}")
                    del self.db.cases[caseID]

                self.db.save()

            try:
                self.stream = self.stub.caseRequest(self.gen())
                for res in self.stream:
                    whichone = res.WhichOneof('result')
                    if whichone == 'caseAck':
                        data: CaseAck = getattr(res, whichone)
                        caseID = data.case.id
                        resTime = data.resolutionTime
                        print(f"[{whichone}] Your case got id={caseID}, resolution time ~{resTime}ms")

                        self.db.cases[caseID] = resTime
                        self.db.save()

                    elif whichone == 'caseResult':
                        data: CaseResult = getattr(res, whichone)
                        caseID = data.case.id
                        message = data.message
                        print(f"[{whichone}] Case id={caseID} has beed examined! Message: {message}")

                        del self.db.cases[caseID]
                        self.db.save()

            except grpc.RpcError as e:
                status_code = e.code()
                print(e, status_code)


if __name__ == '__main__':
    # if len(sys.argv) != 2:
    #     print("please provide clientID")
    #     sys.exit(1)
    #
    # id = int(sys.argv[1])
    id = 1
    DB_PATH = f"./client_db{id}.json"
    if os.path.exists(DB_PATH):
        db = db_load()
    else:
        db = SimpleDB()

    client = OfficeClient(db)
    client.start("localhost:50051")
