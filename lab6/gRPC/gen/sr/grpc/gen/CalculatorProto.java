// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: calculator.proto

package sr.grpc.gen;

public final class CalculatorProto {
  private CalculatorProto() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_calculator_ArithmeticOpArguments_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_calculator_ArithmeticOpArguments_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_calculator_ArithmeticOpResult_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_calculator_ArithmeticOpResult_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_calculator_ArithmeticMultArguments_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_calculator_ArithmeticMultArguments_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_calculator_ComplexArithmeticOpArguments_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_calculator_ComplexArithmeticOpArguments_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_calculator_ComplexArithmeticOpResult_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_calculator_ComplexArithmeticOpResult_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\020calculator.proto\022\ncalculator\"3\n\025Arithm" +
      "eticOpArguments\022\014\n\004arg1\030\001 \001(\005\022\014\n\004arg2\030\002 " +
      "\001(\005\"!\n\022ArithmeticOpResult\022\013\n\003res\030\001 \001(\005\"\'" +
      "\n\027ArithmeticMultArguments\022\014\n\004args\030\001 \003(\005\"" +
      "W\n\034ComplexArithmeticOpArguments\022)\n\006optyp" +
      "e\030\001 \001(\0162\031.calculator.OperationType\022\014\n\004ar" +
      "gs\030\002 \003(\001\"(\n\031ComplexArithmeticOpResult\022\013\n" +
      "\003res\030\001 \001(\001*3\n\rOperationType\022\007\n\003SUM\020\000\022\007\n\003" +
      "AVG\020\001\022\007\n\003MIN\020\002\022\007\n\003MAX\020\0032\370\001\n\nCalculator\022J" +
      "\n\003Add\022!.calculator.ArithmeticOpArguments" +
      "\032\036.calculator.ArithmeticOpResult\"\000\022O\n\010Su" +
      "btract\022!.calculator.ArithmeticOpArgument" +
      "s\032\036.calculator.ArithmeticOpResult\"\000\022M\n\004M" +
      "ult\022#.calculator.ArithmeticMultArguments" +
      "\032\036.calculator.ArithmeticOpResult\"\0002{\n\022Ad" +
      "vancedCalculator\022e\n\020ComplexOperation\022(.c" +
      "alculator.ComplexArithmeticOpArguments\032%" +
      ".calculator.ComplexArithmeticOpResult\"\000B" +
      " \n\013sr.grpc.genB\017CalculatorProtoP\001b\006proto" +
      "3"
    };
    descriptor = com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
        });
    internal_static_calculator_ArithmeticOpArguments_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_calculator_ArithmeticOpArguments_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_calculator_ArithmeticOpArguments_descriptor,
        new java.lang.String[] { "Arg1", "Arg2", });
    internal_static_calculator_ArithmeticOpResult_descriptor =
      getDescriptor().getMessageTypes().get(1);
    internal_static_calculator_ArithmeticOpResult_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_calculator_ArithmeticOpResult_descriptor,
        new java.lang.String[] { "Res", });
    internal_static_calculator_ArithmeticMultArguments_descriptor =
      getDescriptor().getMessageTypes().get(2);
    internal_static_calculator_ArithmeticMultArguments_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_calculator_ArithmeticMultArguments_descriptor,
        new java.lang.String[] { "Args", });
    internal_static_calculator_ComplexArithmeticOpArguments_descriptor =
      getDescriptor().getMessageTypes().get(3);
    internal_static_calculator_ComplexArithmeticOpArguments_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_calculator_ComplexArithmeticOpArguments_descriptor,
        new java.lang.String[] { "Optype", "Args", });
    internal_static_calculator_ComplexArithmeticOpResult_descriptor =
      getDescriptor().getMessageTypes().get(4);
    internal_static_calculator_ComplexArithmeticOpResult_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_calculator_ComplexArithmeticOpResult_descriptor,
        new java.lang.String[] { "Res", });
  }

  // @@protoc_insertion_point(outer_class_scope)
}
