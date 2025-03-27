import java.math.BigInteger;
import java.security.SecureRandom;

public class RSA {
    private BigInteger n, d, e;
    private int bitLength = 2048;

    public RSA() {
        SecureRandom random = new SecureRandom();
        BigInteger p = BigInteger.probablePrime(bitLength / 2, random);
        BigInteger q = BigInteger.probablePrime(bitLength / 2, random);
        n = p.multiply(q);
        BigInteger phi = (p.subtract(BigInteger.ONE)).multiply(q.subtract(BigInteger.ONE));
        e = BigInteger.probablePrime(bitLength / 2, random);
        while (phi.gcd(e).compareTo(BigInteger.ONE) > 0 && e.compareTo(phi) < 0) {
            e = e.add(BigInteger.ONE);
        }
        d = e.modInverse(phi);
    }

    public BigInteger encrypt(BigInteger message) {
        return message.modPow(e, n);
    }

    public BigInteger decrypt(BigInteger message) {
        return message.modPow(d, n);
    }

    public static void main(String[] args) {
        RSA rsa = new RSA();
        String message = "Hello, RSA!";
        BigInteger messageBytes = new BigInteger(message.getBytes());

        BigInteger encrypted = rsa.encrypt(messageBytes);
        System.out.println("Encrypted: " + encrypted);

        BigInteger decrypted = rsa.decrypt(encrypted);
        System.out.println("Decrypted: " + new String(decrypted.toByteArray()));
    }
}
