package hash;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class CalcSHA256 {

    public static void main(String[] args) throws IOException, NoSuchAlgorithmException {
        FileReader fileReader = new FileReader(new File(args[0]));
        BufferedReader reader = new BufferedReader(fileReader);

        String fileName;
        while ((fileName = reader.readLine()) != null) {
            Path path = Paths.get(fileName);
            byte[] data = Files.readAllBytes(path);
            MessageDigest md5 = MessageDigest.getInstance("SHA-256");
            byte[] digest = md5.digest(data);
            printHex(digest);
        }
    }

    static void printHex(byte[] bytes) {
        for (byte b : bytes) {
            System.out.printf("%02X", b);
        }
        System.out.println();
    }
}
