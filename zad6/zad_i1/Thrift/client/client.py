import sys
import random
import string
import time
import inspect
import os
import statistics
sys.path.append('./gen-py')



from Tester import Tester
from Tester.ttypes import SmallData, MediumData, BigData

from thrift import Thrift
from thrift.transport import TSocket
from thrift.transport import TTransport
from thrift.protocol import TBinaryProtocol, TJSONProtocol, TCompactProtocol


def random_string(N):
    return ''.join(random.choice(string.ascii_uppercase + string.digits) for _ in range(N))


def random_bool():
    return bool(random.getrandbits(1))


def random_i_list(N):
    return [random.randint(0, 10000) for _ in range(N)]


def random_d_list(N):
    return [random.uniform(1, 100.0) for _ in range(N)]


def random_s_list(N):
    return [random_string(10) for _ in range(N)]


def gen_small(iSeq1_len):
    return Tester.SmallData(
        n1=random.randint(1, 1000),
        n2=random.randint(1, 1000),
        s1=random_string(10),
        b1=random_bool(),
        iSeq1=random_i_list(iSeq1_len)
    )


def gen_medium(iSeq1_len, sSeq1_len):
    return Tester.MediumData(
        smallData=gen_small(iSeq1_len),
        n3=random.randint(1, 1000),
        n4=random.randint(1, 1000),
        d1=random.uniform(1, 100.0),
        d2=random.uniform(1, 100.0),
        s2=random_string(10),
        b2=random_bool(),
        sSeq1=random_s_list(sSeq1_len)
    )


def gen_big(iSeq1_len, sSeq1_len, iSeq2_len, sSeq2_len, dSeq1_len, dSeq2_len):
    return Tester.BigData(
        mediumData=gen_medium(iSeq1_len, sSeq1_len),
        n5=random.randint(1, 1000),
        n6=random.randint(1, 1000),
        n7=random.randint(1, 1000),
        n8=random.randint(1, 1000),
        n9=random.randint(1, 1000),
        n10=random.randint(1, 1000),
        s3=random_string(10),
        s4=random_string(10),
        s5=random_string(10),
        s6=random_string(10),
        s7=random_string(10),
        s8=random_string(10),
        d3=random.uniform(1, 100.0),
        d4=random.uniform(1, 100.0),
        d5=random.uniform(1, 100.0),
        b3=random_bool(),
        b4=random_bool(),
        b5=random_bool(),
        iSeq2=random_i_list(iSeq2_len),
        sSeq2=random_s_list(sSeq2_len),
        dSeq1=random_d_list(dSeq1_len),
        dSeq2=random_d_list(dSeq2_len)
    )

def timeit(func):
    startTime = time.perf_counter_ns()
    func()
    return time.perf_counter_ns() - startTime

def do_test(send_func, gen_func, size):
    times = []
    args_num = len(inspect.signature(gen_func).parameters)
    for i in range(1000):
    # for i in range(1):
        data = gen_func(*[size for _ in range(args_num)])
        f = lambda: send_func(data)
        t = timeit(f)
        times.append(t)

    return times

def main():
    transport = TSocket.TSocket("localhost", 9090)
    # transport = TSocket.TSocket("192.168.0.206", 9090)
    transport = TTransport.TBufferedTransport(transport)

    # protocol = TBinaryProtocol.TBinaryProtocol(transport)
    protocol = TCompactProtocol.TCompactProtocol(transport)

    client = Tester.Client(protocol)
    transport.open()

    # warmup
    client.processSmall(gen_small(2))
    client.processMedium(gen_medium(2, 2))
    client.processBig(gen_big(2, 2, 2, 2, 2, 2))

    random.seed(1337)
    results = {}

    for send_func, gen_func, desc in [
        (client.processSmall, gen_small, "small"),
        (client.processMedium, gen_medium, "medium"),
        (client.processBig, gen_big, "big")
    ]:
        for size in [5, 100, 300]:
            print(desc, size, end=' ')
            r = do_test(send_func, gen_func, size)
            results[f"{desc}-{size}"] = r
            print(round(statistics.mean(r) / 1000000.0, 4), "ms")

    OUTDIR = '../../output/thrift/localhost_binary/'
    # OUTDIR = '../../output/thrift/localhost_compact/'
    # OUTDIR = '../../output/thrift/lan_binary/'
    # OUTDIR = '../../output/thrift/lan_compact/'
    os.makedirs(OUTDIR, exist_ok=True)

    for k, v in results.items():
        with open(f"{OUTDIR}{k}.csv", "wt") as f:
            f.write(','.join(map(str, v)))


if __name__ == '__main__':
    main()
