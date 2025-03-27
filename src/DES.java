import java.math.BigInteger;

public class DES {
    // Initial Permutation Table
    private static final int[] IP = {
            58,50,42,34,26,18,10,2, 60,52,44,36,28,20,12,4,
            62,54,46,38,30,22,14,6, 64,56,48,40,32,24,16,8,
            57,49,41,33,25,17,9,1, 59,51,43,35,27,19,11,3,
            61,53,45,37,29,21,13,5, 63,55,47,39,31,23,15,7
    };

    // Final Permutation Table
    private static final int[] FP = {
            40,8,48,16,56,24,64,32, 39,7,47,15,55,23,63,31,
            38,6,46,14,54,22,62,30, 37,5,45,13,53,21,61,29,
            36,4,44,12,52,20,60,28, 35,3,43,11,51,19,59,27,
            34,2,42,10,50,18,58,26, 33,1,41,9,49,17,57,25
    };

    // Expansion Table
    private static final int[] E = {
            32,1,2,3,4,5, 4,5,6,7,8,9, 8,9,10,11,12,13,
            12,13,14,15,16,17, 16,17,18,19,20,21, 20,21,22,23,24,25,
            24,25,26,27,28,29, 28,29,30,31,32,1
    };

    // Permutation Table
    private static final int[] P = {
            16,7,20,21,29,12,28,17, 1,15,23,26,5,18,31,10,
            2,8,24,14,32,27,3,9, 19,13,30,6,22,11,4,25
    };

    // S-Boxes (Simplified Example, Normally 8 S-Boxes)
    private static final int[][][] S = {
            {
                    {14,4,13,1,2,15,11,8,3,10,6,12,5,9,0,7},
                    {0,15,7,4,14,2,13,1,10,6,12,11,9,5,3,8},
                    {4,1,14,8,13,6,2,11,15,12,9,7,3,10,5,0},
                    {15,12,8,2,4,9,1,7,5,11,3,14,10,0,6,13}
            }
            // Remaining 7 S-Boxes would be defined similarly
    };

    // Initial Permutation Function
    private static long initialPermutation(long input) {
        long output = 0;
        for (int i = 0; i < 64; i++) {
            int bitPos = 63 - (IP[i] - 1);
            long bit = (input >> bitPos) & 1;
            output |= (bit << (63 - i));
        }
        return output;
    }

    // Final Permutation Function
    private static long finalPermutation(long input) {
        long output = 0;
        for (int i = 0; i < 64; i++) {
            int bitPos = 63 - (FP[i] - 1);
            long bit = (input >> bitPos) & 1;
            output |= (bit << (63 - i));
        }
        return output;
    }

    // Expansion Function
    private static long expansion(int input) {
        long output = 0;
        for (int i = 0; i < 48; i++) {
            int bitPos = 31 - (E[i] - 1);
            long bit = (input >> bitPos) & 1;
            output |= (bit << (47 - i));
        }
        return output;
    }

    // Feistel Function (Simplified)
    private static int feistel(int R, long subkey) {
        long expanded = expansion(R);
        expanded ^= subkey; // XOR with subkey
        return (int)(expanded & 0xFFFFFFFFL); // Simplified, real implementation uses S-Boxes
    }

    // DES Encryption Function
    public static long desEncrypt(long plaintext, long key) {
        long permutedText = initialPermutation(plaintext);
        int L = (int)(permutedText >> 32);
        int R = (int)(permutedText & 0xFFFFFFFFL);

        for (int i = 0; i < 16; i++) {
            int temp = R;
            R = L ^ feistel(R, key); // Placeholder key usage
            L = temp;
        }

        long combined = ((long)R << 32) | (L & 0xFFFFFFFFL);
        return finalPermutation(combined);
    }

    // DES Decryption Function
    public static long desDecrypt(long ciphertext, long key) {
        long permutedText = initialPermutation(ciphertext);
        int L = (int)(permutedText >> 32);
        int R = (int)(permutedText & 0xFFFFFFFFL);

        // Reverse order of key application (same Feistel function)
        for (int i = 0; i < 16; i++) {
            int temp = R;
            R = L ^ feistel(R, key); // Placeholder key usage
            L = temp;
        }

        long combined = ((long)R << 32) | (L & 0xFFFFFFFFL);
        return finalPermutation(combined);
    }

    public static void main(String[] args) {
        long plaintext = 0x0123456789ABCDEFL;
        long key = 0x133457799BBCDFF1L;

        long ciphertext = desEncrypt(plaintext, key);
        System.out.printf("Plaintext:  0x%016X\n", plaintext);
        System.out.printf("Ciphertext: 0x%016X\n", ciphertext);

        long decryptedText = desDecrypt(ciphertext, key);
        System.out.printf("Decrypted:  0x%016X\n", decryptedText);
    }
}