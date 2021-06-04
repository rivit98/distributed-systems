protoc.exe -I=. --java_out=server/src/main/java --plugin=protoc-gen-grpc-java=protoc-gen-grpc-java-1.37.0-windows-x86_64.exe --grpc-java_out=server/src/main/java Office.proto
python -m grpc_tools.protoc -I. --python_out=client --grpc_python_out=client Office.proto


