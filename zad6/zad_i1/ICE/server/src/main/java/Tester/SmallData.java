//
// Copyright (c) ZeroC, Inc. All rights reserved.
//
//
// Ice version 3.7.5
//
// <auto-generated>
//
// Generated from file `Tester.ice'
//
// Warning: do not edit this file.
//
// </auto-generated>
//

package Tester;

public class SmallData implements java.lang.Cloneable,
                                  java.io.Serializable
{
    public int n1;

    public int n2;

    public String s1;

    public boolean b1;

    public long[] iSeq1;

    public SmallData()
    {
        this.s1 = "";
    }

    public SmallData(int n1, int n2, String s1, boolean b1, long[] iSeq1)
    {
        this.n1 = n1;
        this.n2 = n2;
        this.s1 = s1;
        this.b1 = b1;
        this.iSeq1 = iSeq1;
    }

    public boolean equals(java.lang.Object rhs)
    {
        if(this == rhs)
        {
            return true;
        }
        SmallData r = null;
        if(rhs instanceof SmallData)
        {
            r = (SmallData)rhs;
        }

        if(r != null)
        {
            if(this.n1 != r.n1)
            {
                return false;
            }
            if(this.n2 != r.n2)
            {
                return false;
            }
            if(this.s1 != r.s1)
            {
                if(this.s1 == null || r.s1 == null || !this.s1.equals(r.s1))
                {
                    return false;
                }
            }
            if(this.b1 != r.b1)
            {
                return false;
            }
            if(!java.util.Arrays.equals(this.iSeq1, r.iSeq1))
            {
                return false;
            }

            return true;
        }

        return false;
    }

    public int hashCode()
    {
        int h_ = 5381;
        h_ = com.zeroc.IceInternal.HashUtil.hashAdd(h_, "::Tester::SmallData");
        h_ = com.zeroc.IceInternal.HashUtil.hashAdd(h_, n1);
        h_ = com.zeroc.IceInternal.HashUtil.hashAdd(h_, n2);
        h_ = com.zeroc.IceInternal.HashUtil.hashAdd(h_, s1);
        h_ = com.zeroc.IceInternal.HashUtil.hashAdd(h_, b1);
        h_ = com.zeroc.IceInternal.HashUtil.hashAdd(h_, iSeq1);
        return h_;
    }

    public SmallData clone()
    {
        SmallData c = null;
        try
        {
            c = (SmallData)super.clone();
        }
        catch(CloneNotSupportedException ex)
        {
            assert false; // impossible
        }
        return c;
    }

    public void ice_writeMembers(com.zeroc.Ice.OutputStream ostr)
    {
        ostr.writeInt(this.n1);
        ostr.writeInt(this.n2);
        ostr.writeString(this.s1);
        ostr.writeBool(this.b1);
        ostr.writeLongSeq(this.iSeq1);
    }

    public void ice_readMembers(com.zeroc.Ice.InputStream istr)
    {
        this.n1 = istr.readInt();
        this.n2 = istr.readInt();
        this.s1 = istr.readString();
        this.b1 = istr.readBool();
        this.iSeq1 = istr.readLongSeq();
    }

    static public void ice_write(com.zeroc.Ice.OutputStream ostr, SmallData v)
    {
        if(v == null)
        {
            _nullMarshalValue.ice_writeMembers(ostr);
        }
        else
        {
            v.ice_writeMembers(ostr);
        }
    }

    static public SmallData ice_read(com.zeroc.Ice.InputStream istr)
    {
        SmallData v = new SmallData();
        v.ice_readMembers(istr);
        return v;
    }

    static public void ice_write(com.zeroc.Ice.OutputStream ostr, int tag, java.util.Optional<SmallData> v)
    {
        if(v != null && v.isPresent())
        {
            ice_write(ostr, tag, v.get());
        }
    }

    static public void ice_write(com.zeroc.Ice.OutputStream ostr, int tag, SmallData v)
    {
        if(ostr.writeOptional(tag, com.zeroc.Ice.OptionalFormat.FSize))
        {
            int pos = ostr.startSize();
            ice_write(ostr, v);
            ostr.endSize(pos);
        }
    }

    static public java.util.Optional<SmallData> ice_read(com.zeroc.Ice.InputStream istr, int tag)
    {
        if(istr.readOptional(tag, com.zeroc.Ice.OptionalFormat.FSize))
        {
            istr.skip(4);
            return java.util.Optional.of(SmallData.ice_read(istr));
        }
        else
        {
            return java.util.Optional.empty();
        }
    }

    private static final SmallData _nullMarshalValue = new SmallData();

    /** @hidden */
    public static final long serialVersionUID = -919712753L;
}
