#ifndef CALC_ICE
#define CALC_ICE

module Tester
{
    sequence<long> SeqInt;
    sequence<string> SeqString;
    sequence<double> SeqDouble;

    struct SmallData {
        int n1;
        int n2;
        string s1;
        bool b1;
        SeqInt iSeq1;
    };

    struct MediumData {
        SmallData smallData;
        int n3;
        int n4;
        double d1;
        double d2;
        string s2;
        bool b2;
        SeqString sSeq1;
    };

    struct BigData {
        MediumData mediumData;
        int n5;
        int n6;
        int n7;
        int n8;
        int n9;
        int n10;
        string s3;
        string s4;
        string s5;
        string s6;
        string s7;
        string s8;
        double d3;
        double d4;
        double d5;
        bool b3;
        bool b4;
        bool b5;
        SeqInt iSeq2;
        SeqString sSeq2;
        SeqDouble dSeq1;
        SeqDouble dSeq2;
    };

    interface TesterIface
    {
        void processSmall(SmallData smallData);
        void processMedium(MediumData mediumData);
        void processBig(BigData bigData);
    };
};

#endif