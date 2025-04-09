package DSS;

import java.security.*;

public class DigitalSigner {

    public static byte[] signMessage(PrivateKey privateKey, String message) throws Exception{

        Signature dsa = Signature.getInstance("SHA1withDSA","SUN");

        dsa.initSign(privateKey);

        dsa.update(message.getBytes());

        return dsa.sign();
    }
}
