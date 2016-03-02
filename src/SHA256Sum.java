import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class SHA256Sum {

    public static void main(String[] args) throws IOException, NoSuchAlgorithmException {
        byte[] data;
        if (args.length == 0) {
            byte[] x = new byte[1];
            ArrayList<Byte> tmp = new ArrayList<>();
            while (System.in.read(x) != -1) {
                tmp.add(x[0]);
            }
            data = new byte[tmp.size()];
            int j = 0;
            for (Byte i : tmp) {
                data[j] = i;
            }
            getHash(data, "-");
        } else {
            for (String fileName : args) {
                Path path = Paths.get(fileName);
                data = Files.readAllBytes(path);
                getHash(data, fileName);
            }
        }
    }

    static void getHash(byte[] data, String fileName) throws NoSuchAlgorithmException {
        MessageDigest md5 = MessageDigest.getInstance("SHA-256");
        byte[] digest = md5.digest(data);

        for (byte b : digest) {
            System.out.printf("%02X", b);
        }
        System.out.println(" *" + fileName);
    }
}
