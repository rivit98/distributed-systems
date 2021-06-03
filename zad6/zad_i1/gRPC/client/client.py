import grpc
import random
import string
import time
import inspect
import os
import statistics

import Tester_pb2
import Tester_pb2_grpc


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
    return Tester_pb2.SmallData(
        n1=random.randint(1, 1000),
        n2=random.randint(1, 1000),
        s1=random_string(10),
        b1=random_bool(),
        iSeq1=random_i_list(iSeq1_len)
    )


def gen_medium(iSeq1_len, sSeq1_len):
    return Tester_pb2.MediumData(
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
    return Tester_pb2.BigData(
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
    channel = grpc.insecure_channel("localhost:50051")
    # channel = grpc.insecure_channel("192.168.0.206:50051")
    stub = Tester_pb2_grpc.TesterStub(channel)
    # warmup
    stub.processSmall(gen_small(2))
    stub.processMedium(gen_medium(2, 2))
    stub.processBig(gen_big(2, 2, 2, 2, 2, 2))

    random.seed(1337)
    results = {}

    for send_func, gen_func, desc in [
        (stub.processSmall, gen_small, "small"),
        (stub.processMedium, gen_medium, "medium"),
        (stub.processBig, gen_big, "big")
    ]:
        for size in [5, 100, 300]:
            print(desc, size, end=' ')
            r = do_test(send_func, gen_func, size)
            results[f"{desc}-{size}"] = r
            print(round(statistics.mean(r) / 1000000.0, 4), "ms")

    OUTDIR = '../../output/grpc/localhost/'
    # OUTDIR = '../../output/grpc/lan/'
    os.makedirs(OUTDIR, exist_ok=True)

    for k, v in results.items():
        with open(f"{OUTDIR}{k}.csv", "wt") as f:
            f.write(','.join(map(str, v)))


if __name__ == '__main__':
    main()
