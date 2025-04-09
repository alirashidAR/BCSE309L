package DSS;

import java.security.*;

public class KeyGenerator {

    public static KeyPair generateKeyPair() throws Exception{

        SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");

        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("DSA");

        keyPairGenerator.initialize(1024,secureRandom);

        return keyPairGenerator.generateKeyPair();
    }
}
