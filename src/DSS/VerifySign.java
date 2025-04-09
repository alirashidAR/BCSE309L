package DSS;
import java.security.*;

public class VerifySign {

    public static boolean verifySign(String message, byte[] signature,PublicKey publicKey) throws Exception{

        Signature dsa = Signature.getInstance("SHA1withDSA","SUN");

        dsa.initVerify(publicKey);

        dsa.update(message.getBytes());
        return dsa.verify(signature);

    }
}
