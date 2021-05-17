
#ifndef CALC_ICE
#define CALC_ICE

module Demo
{
    sequence<long> seqOfNumbers;
    enum operation { MIN, MAX, AVG };
  
    exception NoInput {};

    struct A
    {
        short a;
        long b;
        float c;
        string d;
    };

    interface Calc
    {
          idempotent long add(int a, int b);
          idempotent long subtract(int a, int b);
          void op(A a1, short b1); //za??my, ?e to te? jest operacja arytmetyczna ;)
          idempotent float avg(seqOfNumbers seq) throws NoInput;
    };
};

#endif
