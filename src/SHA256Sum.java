import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class SHA256Sum {
    static final int READ_SIZE = 256;

    public static void main(String[] args) throws IOException, NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        if (args.length == 0) {
            byte[] x = new byte[READ_SIZE];
            int length;
            while ((length = System.in.read(x)) != -1) {
                digest.update(x, 0, length);
            }
            printHash(digest.digest(), "-");
        } else {
            for (String fileName : args) {
                Path path = Paths.get(fileName);
                digest.update(Files.readAllBytes(path));
                printHash(digest.digest(), fileName);
            }
        }
    }

    static void printHash(byte[] data, String fileName) throws NoSuchAlgorithmException {
        for (byte b : data) {
            System.out.printf("%02X", b);
        }
        System.out.println(" *" + fileName);
    }
}
