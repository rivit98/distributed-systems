# -*- coding: utf-8 -*-
#
# Copyright (c) ZeroC, Inc. All rights reserved.
#
#
# Ice version 3.7.5
#
# <auto-generated>
#
# Generated from file `Tester.ice'
#
# Warning: do not edit this file.
#
# </auto-generated>
#

from sys import version_info as _version_info_
import Ice, IcePy

# Start of module Tester
_M_Tester = Ice.openModule('Tester')
__name__ = 'Tester'

if '_t_SeqInt' not in _M_Tester.__dict__:
    _M_Tester._t_SeqInt = IcePy.defineSequence('::Tester::SeqInt', (), IcePy._t_long)

if '_t_SeqString' not in _M_Tester.__dict__:
    _M_Tester._t_SeqString = IcePy.defineSequence('::Tester::SeqString', (), IcePy._t_string)

if '_t_SeqDouble' not in _M_Tester.__dict__:
    _M_Tester._t_SeqDouble = IcePy.defineSequence('::Tester::SeqDouble', (), IcePy._t_double)

if 'SmallData' not in _M_Tester.__dict__:
    _M_Tester.SmallData = Ice.createTempClass()
    class SmallData(object):
        def __init__(self, n1=0, n2=0, s1='', b1=False, iSeq1=None):
            self.n1 = n1
            self.n2 = n2
            self.s1 = s1
            self.b1 = b1
            self.iSeq1 = iSeq1

        def __hash__(self):
            _h = 0
            _h = 5 * _h + Ice.getHash(self.n1)
            _h = 5 * _h + Ice.getHash(self.n2)
            _h = 5 * _h + Ice.getHash(self.s1)
            _h = 5 * _h + Ice.getHash(self.b1)
            if self.iSeq1:
                for _i0 in self.iSeq1:
                    _h = 5 * _h + Ice.getHash(_i0)
            return _h % 0x7fffffff

        def __compare(self, other):
            if other is None:
                return 1
            elif not isinstance(other, _M_Tester.SmallData):
                return NotImplemented
            else:
                if self.n1 is None or other.n1 is None:
                    if self.n1 != other.n1:
                        return (-1 if self.n1 is None else 1)
                else:
                    if self.n1 < other.n1:
                        return -1
                    elif self.n1 > other.n1:
                        return 1
                if self.n2 is None or other.n2 is None:
                    if self.n2 != other.n2:
                        return (-1 if self.n2 is None else 1)
                else:
                    if self.n2 < other.n2:
                        return -1
                    elif self.n2 > other.n2:
                        return 1
                if self.s1 is None or other.s1 is None:
                    if self.s1 != other.s1:
                        return (-1 if self.s1 is None else 1)
                else:
                    if self.s1 < other.s1:
                        return -1
                    elif self.s1 > other.s1:
                        return 1
                if self.b1 is None or other.b1 is None:
                    if self.b1 != other.b1:
                        return (-1 if self.b1 is None else 1)
                else:
                    if self.b1 < other.b1:
                        return -1
                    elif self.b1 > other.b1:
                        return 1
                if self.iSeq1 is None or other.iSeq1 is None:
                    if self.iSeq1 != other.iSeq1:
                        return (-1 if self.iSeq1 is None else 1)
                else:
                    if self.iSeq1 < other.iSeq1:
                        return -1
                    elif self.iSeq1 > other.iSeq1:
                        return 1
                return 0

        def __lt__(self, other):
            r = self.__compare(other)
            if r is NotImplemented:
                return r
            else:
                return r < 0

        def __le__(self, other):
            r = self.__compare(other)
            if r is NotImplemented:
                return r
            else:
                return r <= 0

        def __gt__(self, other):
            r = self.__compare(other)
            if r is NotImplemented:
                return r
            else:
                return r > 0

        def __ge__(self, other):
            r = self.__compare(other)
            if r is NotImplemented:
                return r
            else:
                return r >= 0

        def __eq__(self, other):
            r = self.__compare(other)
            if r is NotImplemented:
                return r
            else:
                return r == 0

        def __ne__(self, other):
            r = self.__compare(other)
            if r is NotImplemented:
                return r
            else:
                return r != 0

        def __str__(self):
            return IcePy.stringify(self, _M_Tester._t_SmallData)

        __repr__ = __str__

    _M_Tester._t_SmallData = IcePy.defineStruct('::Tester::SmallData', SmallData, (), (
        ('n1', (), IcePy._t_int),
        ('n2', (), IcePy._t_int),
        ('s1', (), IcePy._t_string),
        ('b1', (), IcePy._t_bool),
        ('iSeq1', (), _M_Tester._t_SeqInt)
    ))

    _M_Tester.SmallData = SmallData
    del SmallData

if 'MediumData' not in _M_Tester.__dict__:
    _M_Tester.MediumData = Ice.createTempClass()
    class MediumData(object):
        def __init__(self, smallData=Ice._struct_marker, n3=0, n4=0, d1=0.0, d2=0.0, s2='', b2=False, sSeq1=None):
            if smallData is Ice._struct_marker:
                self.smallData = _M_Tester.SmallData()
            else:
                self.smallData = smallData
            self.n3 = n3
            self.n4 = n4
            self.d1 = d1
            self.d2 = d2
            self.s2 = s2
            self.b2 = b2
            self.sSeq1 = sSeq1

        def __eq__(self, other):
            if other is None:
                return False
            elif not isinstance(other, _M_Tester.MediumData):
                return NotImplemented
            else:
                if self.smallData != other.smallData:
                    return False
                if self.n3 != other.n3:
                    return False
                if self.n4 != other.n4:
                    return False
                if self.d1 != other.d1:
                    return False
                if self.d2 != other.d2:
                    return False
                if self.s2 != other.s2:
                    return False
                if self.b2 != other.b2:
                    return False
                if self.sSeq1 != other.sSeq1:
                    return False
                return True

        def __ne__(self, other):
            return not self.__eq__(other)

        def __str__(self):
            return IcePy.stringify(self, _M_Tester._t_MediumData)

        __repr__ = __str__

    _M_Tester._t_MediumData = IcePy.defineStruct('::Tester::MediumData', MediumData, (), (
        ('smallData', (), _M_Tester._t_SmallData),
        ('n3', (), IcePy._t_int),
        ('n4', (), IcePy._t_int),
        ('d1', (), IcePy._t_double),
        ('d2', (), IcePy._t_double),
        ('s2', (), IcePy._t_string),
        ('b2', (), IcePy._t_bool),
        ('sSeq1', (), _M_Tester._t_SeqString)
    ))

    _M_Tester.MediumData = MediumData
    del MediumData

if 'BigData' not in _M_Tester.__dict__:
    _M_Tester.BigData = Ice.createTempClass()
    class BigData(object):
        def __init__(self, mediumData=Ice._struct_marker, n5=0, n6=0, n7=0, n8=0, n9=0, n10=0, s3='', s4='', s5='', s6='', s7='', s8='', d3=0.0, d4=0.0, d5=0.0, b3=False, b4=False, b5=False, iSeq2=None, sSeq2=None, dSeq1=None, dSeq2=None):
            if mediumData is Ice._struct_marker:
                self.mediumData = _M_Tester.MediumData()
            else:
                self.mediumData = mediumData
            self.n5 = n5
            self.n6 = n6
            self.n7 = n7
            self.n8 = n8
            self.n9 = n9
            self.n10 = n10
            self.s3 = s3
            self.s4 = s4
            self.s5 = s5
            self.s6 = s6
            self.s7 = s7
            self.s8 = s8
            self.d3 = d3
            self.d4 = d4
            self.d5 = d5
            self.b3 = b3
            self.b4 = b4
            self.b5 = b5
            self.iSeq2 = iSeq2
            self.sSeq2 = sSeq2
            self.dSeq1 = dSeq1
            self.dSeq2 = dSeq2

        def __eq__(self, other):
            if other is None:
                return False
            elif not isinstance(other, _M_Tester.BigData):
                return NotImplemented
            else:
                if self.mediumData != other.mediumData:
                    return False
                if self.n5 != other.n5:
                    return False
                if self.n6 != other.n6:
                    return False
                if self.n7 != other.n7:
                    return False
                if self.n8 != other.n8:
                    return False
                if self.n9 != other.n9:
                    return False
                if self.n10 != other.n10:
                    return False
                if self.s3 != other.s3:
                    return False
                if self.s4 != other.s4:
                    return False
                if self.s5 != other.s5:
                    return False
                if self.s6 != other.s6:
                    return False
                if self.s7 != other.s7:
                    return False
                if self.s8 != other.s8:
                    return False
                if self.d3 != other.d3:
                    return False
                if self.d4 != other.d4:
                    return False
                if self.d5 != other.d5:
                    return False
                if self.b3 != other.b3:
                    return False
                if self.b4 != other.b4:
                    return False
                if self.b5 != other.b5:
                    return False
                if self.iSeq2 != other.iSeq2:
                    return False
                if self.sSeq2 != other.sSeq2:
                    return False
                if self.dSeq1 != other.dSeq1:
                    return False
                if self.dSeq2 != other.dSeq2:
                    return False
                return True

        def __ne__(self, other):
            return not self.__eq__(other)

        def __str__(self):
            return IcePy.stringify(self, _M_Tester._t_BigData)

        __repr__ = __str__

    _M_Tester._t_BigData = IcePy.defineStruct('::Tester::BigData', BigData, (), (
        ('mediumData', (), _M_Tester._t_MediumData),
        ('n5', (), IcePy._t_int),
        ('n6', (), IcePy._t_int),
        ('n7', (), IcePy._t_int),
        ('n8', (), IcePy._t_int),
        ('n9', (), IcePy._t_int),
        ('n10', (), IcePy._t_int),
        ('s3', (), IcePy._t_string),
        ('s4', (), IcePy._t_string),
        ('s5', (), IcePy._t_string),
        ('s6', (), IcePy._t_string),
        ('s7', (), IcePy._t_string),
        ('s8', (), IcePy._t_string),
        ('d3', (), IcePy._t_double),
        ('d4', (), IcePy._t_double),
        ('d5', (), IcePy._t_double),
        ('b3', (), IcePy._t_bool),
        ('b4', (), IcePy._t_bool),
        ('b5', (), IcePy._t_bool),
        ('iSeq2', (), _M_Tester._t_SeqInt),
        ('sSeq2', (), _M_Tester._t_SeqString),
        ('dSeq1', (), _M_Tester._t_SeqDouble),
        ('dSeq2', (), _M_Tester._t_SeqDouble)
    ))

    _M_Tester.BigData = BigData
    del BigData

_M_Tester._t_TesterIface = IcePy.defineValue('::Tester::TesterIface', Ice.Value, -1, (), False, True, None, ())

if 'TesterIfacePrx' not in _M_Tester.__dict__:
    _M_Tester.TesterIfacePrx = Ice.createTempClass()
    class TesterIfacePrx(Ice.ObjectPrx):

        def processSmall(self, smallData, context=None):
            return _M_Tester.TesterIface._op_processSmall.invoke(self, ((smallData, ), context))

        def processSmallAsync(self, smallData, context=None):
            return _M_Tester.TesterIface._op_processSmall.invokeAsync(self, ((smallData, ), context))

        def begin_processSmall(self, smallData, _response=None, _ex=None, _sent=None, context=None):
            return _M_Tester.TesterIface._op_processSmall.begin(self, ((smallData, ), _response, _ex, _sent, context))

        def end_processSmall(self, _r):
            return _M_Tester.TesterIface._op_processSmall.end(self, _r)

        def processMedium(self, mediumData, context=None):
            return _M_Tester.TesterIface._op_processMedium.invoke(self, ((mediumData, ), context))

        def processMediumAsync(self, mediumData, context=None):
            return _M_Tester.TesterIface._op_processMedium.invokeAsync(self, ((mediumData, ), context))

        def begin_processMedium(self, mediumData, _response=None, _ex=None, _sent=None, context=None):
            return _M_Tester.TesterIface._op_processMedium.begin(self, ((mediumData, ), _response, _ex, _sent, context))

        def end_processMedium(self, _r):
            return _M_Tester.TesterIface._op_processMedium.end(self, _r)

        def processBig(self, bigData, context=None):
            return _M_Tester.TesterIface._op_processBig.invoke(self, ((bigData, ), context))

        def processBigAsync(self, bigData, context=None):
            return _M_Tester.TesterIface._op_processBig.invokeAsync(self, ((bigData, ), context))

        def begin_processBig(self, bigData, _response=None, _ex=None, _sent=None, context=None):
            return _M_Tester.TesterIface._op_processBig.begin(self, ((bigData, ), _response, _ex, _sent, context))

        def end_processBig(self, _r):
            return _M_Tester.TesterIface._op_processBig.end(self, _r)

        @staticmethod
        def checkedCast(proxy, facetOrContext=None, context=None):
            return _M_Tester.TesterIfacePrx.ice_checkedCast(proxy, '::Tester::TesterIface', facetOrContext, context)

        @staticmethod
        def uncheckedCast(proxy, facet=None):
            return _M_Tester.TesterIfacePrx.ice_uncheckedCast(proxy, facet)

        @staticmethod
        def ice_staticId():
            return '::Tester::TesterIface'
    _M_Tester._t_TesterIfacePrx = IcePy.defineProxy('::Tester::TesterIface', TesterIfacePrx)

    _M_Tester.TesterIfacePrx = TesterIfacePrx
    del TesterIfacePrx

    _M_Tester.TesterIface = Ice.createTempClass()
    class TesterIface(Ice.Object):

        def ice_ids(self, current=None):
            return ('::Ice::Object', '::Tester::TesterIface')

        def ice_id(self, current=None):
            return '::Tester::TesterIface'

        @staticmethod
        def ice_staticId():
            return '::Tester::TesterIface'

        def processSmall(self, smallData, current=None):
            raise NotImplementedError("servant method 'processSmall' not implemented")

        def processMedium(self, mediumData, current=None):
            raise NotImplementedError("servant method 'processMedium' not implemented")

        def processBig(self, bigData, current=None):
            raise NotImplementedError("servant method 'processBig' not implemented")

        def __str__(self):
            return IcePy.stringify(self, _M_Tester._t_TesterIfaceDisp)

        __repr__ = __str__

    _M_Tester._t_TesterIfaceDisp = IcePy.defineClass('::Tester::TesterIface', TesterIface, (), None, ())
    TesterIface._ice_type = _M_Tester._t_TesterIfaceDisp

    TesterIface._op_processSmall = IcePy.Operation('processSmall', Ice.OperationMode.Normal, Ice.OperationMode.Normal, False, None, (), (((), _M_Tester._t_SmallData, False, 0),), (), None, ())
    TesterIface._op_processMedium = IcePy.Operation('processMedium', Ice.OperationMode.Normal, Ice.OperationMode.Normal, False, None, (), (((), _M_Tester._t_MediumData, False, 0),), (), None, ())
    TesterIface._op_processBig = IcePy.Operation('processBig', Ice.OperationMode.Normal, Ice.OperationMode.Normal, False, None, (), (((), _M_Tester._t_BigData, False, 0),), (), None, ())

    _M_Tester.TesterIface = TesterIface
    del TesterIface

# End of module Tester
