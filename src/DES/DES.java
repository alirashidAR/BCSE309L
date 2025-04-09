package DES;
import java.util.*;

public class DES {

    // Initial Permutation (IP)
    static final int[] IP = {
            58, 50, 42, 34, 26, 18, 10, 2,
            60, 52, 44, 36, 28, 20, 12, 4,
            62, 54, 46, 38, 30, 22, 14, 6,
            64, 56, 48, 40, 32, 24, 16, 8,
            57, 49, 41, 33, 25, 17, 9, 1,
            59, 51, 43, 35, 27, 19, 11, 3,
            61, 53, 45, 37, 29, 21, 13, 5,
            63, 55, 47, 39, 31, 23, 15, 7
    };

    // Final Permutation (IP^-1)
    static final int[] IP_INV = {
            40, 8, 48, 16, 56, 24, 64, 32,
            39, 7, 47, 15, 55, 23, 63, 31,
            38, 6, 46, 14, 54, 22, 62, 30,
            37, 5, 45, 13, 53, 21, 61, 29,
            36, 4, 44, 12, 52, 20, 60, 28,
            35, 3, 43, 11, 51, 19, 59, 27,
            34, 2, 42, 10, 50, 18, 58, 26,
            33, 1, 41, 9, 49, 17, 57, 25
    };

    // Expansion table
    static final int[] E = {
            32, 1, 2, 3, 4, 5,
            4, 5, 6, 7, 8, 9,
            8, 9,10,11,12,13,
            12,13,14,15,16,17,
            16,17,18,19,20,21,
            20,21,22,23,24,25,
            24,25,26,27,28,29,
            28,29,30,31,32, 1
    };

    // Permutation P
    static final int[] P = {
            16, 7, 20, 21, 29, 12, 28, 17,
            1,15, 23, 26, 5,18, 31, 10,
            2, 8, 24, 14, 32, 27, 3, 9,
            19,13, 30, 6, 22, 11, 4, 25
    };

    // Simple S-Box for illustration
    static final int[][][] S_BOX = {
            { // S1
                    {14,4,13,1}, {2,15,11,8}, {3,10,6,12}, {5,9,0,7}
            },
            {
                    {0,15,7,4}, {14,2,13,1}, {10,6,12,11}, {9,5,3,8}
            }
            // ... rest of 8 S-boxes omitted for simplicity
    };

    // Permute bits using a table
    static String permute(String input, int[] table) {
        StringBuilder output = new StringBuilder();
        for (int index : table) {
            output.append(input.charAt(index - 1));
        }
        return output.toString();
    }

    // Expand 32-bit to 48-bit using E-table
    static String expand(String bits) {
        return permute(bits, E);
    }

    // Apply S-boxes (only first for demo)
    static String sBox(String input) {
        String sixBits = input.substring(0, 6);
        int row = Integer.parseInt("" + sixBits.charAt(0) + sixBits.charAt(5), 2);
        int col = Integer.parseInt(sixBits.substring(1, 5), 2);
        int val = S_BOX[0][row][col];
        return String.format("%4s", Integer.toBinaryString(val)).replace(' ', '0');
    }

    // Feistel function f(R, K)
    static String feistel(String right, String subkey) {
        String expanded = expand(right);
        String xored = xor(expanded, subkey);
        String sOut = sBox(xored); // Apply one S-box for simplicity
        return permute(sOut, P);
    }

    // XOR two binary strings
    static String xor(String a, String b) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < a.length(); i++) {
            result.append(a.charAt(i) == b.charAt(i) ? '0' : '1');
        }
        return result.toString();
    }

    // Key generation (dummy keys)
    static String[] generateSubkeys(String key) {
        String[] keys = new String[16];
        for (int i = 0; i < 16; i++) {
            keys[i] = key.substring(0, 48); // Dummy: 48-bit from 64-bit key
        }
        return keys;
    }

    // Encrypt 64-bit block with 64-bit key
    static String encrypt(String plaintext, String key) {
        String permuted = permute(plaintext, IP);
        String L = permuted.substring(0, 32);
        String R = permuted.substring(32);

        String[] keys = generateSubkeys(key);

        for (int i = 0; i < 16; i++) {
            String temp = R;
            R = xor(L, feistel(R, keys[i]));
            L = temp;
        }

        String preOutput = R + L; // swap final
        return permute(preOutput, IP_INV);
    }

    public static void main(String[] args) {
        // 64-bit plaintext and key (as binary strings)
        String plaintext = "0110011001100001011100110111010001101111011100110110100101110110"; // "fastosiv"
        String key =      "0001001100110100010101110111100110011011101111001101111111110001";

        String ciphertext = encrypt(plaintext, key);
        System.out.println("Encrypted: " + ciphertext);
    }
}
