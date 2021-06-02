package server;

import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.protocol.TProtocolFactory;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TSimpleServer;
import org.apache.thrift.transport.TServerSocket;
import thrift.BigData;
import thrift.MediumData;
import thrift.SmallData;
import thrift.Tester;

import java.util.List;

class TesterImpl implements Tester.Iface {
    private static <T> void iterateAndIgnore(List<T> arr){
        long i = 0;
        for(T v : arr){
            i++;
        }
    }

    @Override
    public void processSmall(SmallData smallData) {
        iterateAndIgnore(smallData.getISeq1());
    }

    @Override
    public void processMedium(MediumData mediumData) {
        processSmall(mediumData.getSmallData());

        iterateAndIgnore(mediumData.getSSeq1());
    }

    @Override
    public void processBig(BigData bigData) {
        processMedium(bigData.getMediumData());

        iterateAndIgnore(bigData.getISeq2());
        iterateAndIgnore(bigData.getSSeq2());
        iterateAndIgnore(bigData.getDSeq1());
        iterateAndIgnore(bigData.getDSeq2());
    }
}

class Server {
    public static void main(String[] args) {
        try {
            var processor = new Tester.Processor<>(new TesterImpl());

            var serverTransport = new TServerSocket(9090);

            var protocolFactory = new TBinaryProtocol.Factory();
//            TProtocolFactory protocolFactory = new TCompactProtocol.Factory();

            var server = new TSimpleServer(
                    new TServer.Args(serverTransport)
                            .protocolFactory(protocolFactory)
                            .processor(processor)
            );

            System.out.println("Starting server...");
            server.serve();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
