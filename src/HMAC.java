import javax.crypto.*;
import javax.crypto.spec.*;
import java.nio.charset.*;
import java.security.*;
import java.util.*;

public class HMAC {
    private static final String HMAC_SHA256_ALGORITHM = "HmacSHA256";

    // Generate a random secure key
    private static byte[] generateSecureKey() {
        SecureRandom secureRandom = new SecureRandom();
        byte[] key = new byte[32]; // 256-bit key
        secureRandom.nextBytes(key);
        return key;
    }

    // Method to compute HMAC-SHA256
    public static String generateHMAC(String message, byte[] key) {
        try {
            Mac mac = Mac.getInstance(HMAC_SHA256_ALGORITHM);
            SecretKeySpec secretKeySpec = new SecretKeySpec(key, HMAC_SHA256_ALGORITHM);
            mac.init(secretKeySpec);
            byte[] macBytes = mac.doFinal(message.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(macBytes);
        } catch (Exception e) {
            throw new RuntimeException("Error generating HMAC", e);
        }
    }

    // Method to measure execution time for different message sizes
    public static void measurePerformance() {
        byte[] key = generateSecureKey();

        int[] sizes = {100, 1024, 10240}; // 100 bytes, 1 KB, 10 KB
        for (int size : sizes) {
            String message = "A".repeat(size);
            long startTime = System.nanoTime();
            String mac = generateHMAC(message, key);
            long endTime = System.nanoTime();

            System.out.println("Message Size: " + size + " bytes");
            System.out.println("HMAC: " + mac);
            System.out.println("Time Taken: " + (endTime - startTime) / 1_000_000.0 + " ms\n");
        }
    }

    public static void main(String[] args) {
        measurePerformance();
    }
}