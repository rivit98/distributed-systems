namespace java thrift

struct SmallData {
    1: i32 n1,
    2: i32 n2,
    3: string s1,
    4: bool b1,
    5: list<i32> iSeq1,
}

struct MediumData {
    1: SmallData smallData,
    2: i32 n3,
    3: i32 n4,
    4: double d1,
    5: double d2,
    6: string s2,
    7: bool b2,
    8: list<string> sSeq1
}

struct BigData {
    1: MediumData mediumData,
    2: i32 n5,
    3: i32 n6,
    4: i32 n7,
    5: i32 n8,
    6: i32 n9,
    7: i32 n10,
    8: string s3,
    9: string s4,
    10: string s5,
    11: string s6,
    12: string s7,
    13: string s8,
    14: double d3,
    15: double d4,
    16: double d5,
    17: bool b3,
    18: bool b4,
    19: bool b5,
    20: list<i32> iSeq2,
    21: list<string> sSeq2,
    22: list<double> dSeq1,
    23: list<double> dSeq2,
}

service Tester {
    void processSmall(1: SmallData smallData)
    void processMedium(1: MediumData mediumData)
    void processBig(1: BigData bigData)
}