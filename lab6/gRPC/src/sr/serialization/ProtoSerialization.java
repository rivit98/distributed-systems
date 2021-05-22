package sr.serialization;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

import sr.proto.AddressBookProtos.Person;

public class ProtoSerialization {

    public static void main(String[] args) {
        try {
            new ProtoSerialization().testProto();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void testProto() throws IOException {
        Person p1 =
                Person.newBuilder()
                        .setId(123456)
                        .setName("W³odzimierz Wróblewski")
                        .setEmail("wrobel@poczta.com")
                        .addPhones(
                                Person.PhoneNumber.newBuilder()
                                        .setNumber("+48-12-555-4321")
                                        .setType(Person.PhoneType.HOME))
                        .addPhones(
                                Person.PhoneNumber.newBuilder()
                                        .setNumber("+48-699-989-796")
                                        .setType(Person.PhoneType.MOBILE))
                        .addAllSalaries(List.of(1.0, 3.0, 4.0))
                        .build();

        byte[] p1ser = null;

        long n = 100000;
        System.out.println("Performing proto serialization " + n + " times...");
        var start = System.currentTimeMillis();
        for (long i = 0; i < n; i++) {
            p1ser = p1.toByteArray();
        }
        var end = System.currentTimeMillis();
        System.out.println("... finished.");
        System.out.println((end - start) / (float) n + "ms");

        //serialize again (only once) and write to a file
        FileOutputStream file = new FileOutputStream("person2.ser");
        file.write(p1.toByteArray());
        file.close();

    }
}
