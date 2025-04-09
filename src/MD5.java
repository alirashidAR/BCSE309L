import java.security.MessageDigest;
import java.util.Base64;
import java.util.Scanner;

public class MD5 {
    public static void main(String[] args) throws Exception {

        MessageDigest md = MessageDigest.getInstance("MD5");


        Scanner sc = new Scanner(System.in);
        System.out.print("Enter a message: ");
        String message = sc.nextLine();


        md.update(message.getBytes());
        byte[] digest = md.digest();


        String base64Encoded = Base64.getEncoder().encodeToString(digest);
        System.out.println("Base64 Encoded MD5 Hash: " + base64Encoded);


        StringBuilder sb = new StringBuilder();
        for (byte b : digest) {
            sb.append(String.format("%02x", b));
        }
        System.out.println("Hexadecimal MD5 Hash  : " + sb.toString());


        System.out.println("\nNote: MD5 is not reversible. You cannot decrypt it.");
    }
}
