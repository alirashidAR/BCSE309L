package DSS;

import java.security.*;
import java.util.Base64;

public class DSS {
    public static void main(String[] args) {
        try {

            KeyPair keyPair = KeyGenerator.generateKeyPair();

            String message = "This is a confidential legal communication.";

            // 2. Sign the message
            byte[] signature = DigitalSigner.signMessage(keyPair.getPrivate(),message);

            System.out.println("========== DIGITAL SIGNATURE DEMO ==========");
            System.out.println("Original Message       : " + message);
            System.out.println("--------------------------------------------");

            // Print the signature in base64 for readability
            String encodedSignature = Base64.getEncoder().encodeToString(signature);
            System.out.println("Digital Signature (Base64): " + encodedSignature);

            // Print the public key (Base64)
            String publicKeyEncoded = Base64.getEncoder().encodeToString(keyPair.getPublic().getEncoded());
            System.out.println("Public Key (Base64)       : " + publicKeyEncoded);
            System.out.println("--------------------------------------------");

            // 3. Verify the signature
            boolean isVerified = VerifySign.verifySign(message, signature, keyPair.getPublic());

            System.out.println("Signature Verification Result: " + (isVerified ? "✅ SUCCESS" : "❌ FAILURE"));
            System.out.println("============================================");

        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}
