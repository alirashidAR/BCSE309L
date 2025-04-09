package DES;

public class DESKeyGenerator {

    // PC-1 table: 64-bit key → 56-bit
    static final int[] PC1 = {
            57,49,41,33,25,17,9,
            1,58,50,42,34,26,18,
            10,2,59,51,43,35,27,
            19,11,3,60,52,44,36,
            63,55,47,39,31,23,15,
            7,62,54,46,38,30,22,
            14,6,61,53,45,37,29,
            21,13,5,28,20,12,4
    };

    // PC-2 table: 56-bit → 48-bit subkey
    static final int[] PC2 = {
            14,17,11,24,1,5,3,28,
            15,6,21,10,23,19,12,4,
            26,8,16,7,27,20,13,2,
            41,52,31,37,47,55,30,40,
            51,45,33,48,44,49,39,56,
            34,53,46,42,50,36,29,32
    };

    // Number of left shifts for each round
    static final int[] SHIFTS = {
            1, 1, 2, 2, 2, 2, 2, 2,
            1, 2, 2, 2, 2, 2, 2, 1
    };

    // Perform permutation using table
    static String permute(String input, int[] table) {
        StringBuilder output = new StringBuilder();
        for (int pos : table) {
            output.append(input.charAt(pos - 1));
        }
        return output.toString();
    }

    // Left circular shift
    static String leftShift(String input, int shifts) {
        return input.substring(shifts) + input.substring(0, shifts);
    }

    // Generate 16 subkeys from 64-bit input key
    static String[] generateKeys(String key64bit) {
        String key56bit = permute(key64bit, PC1); // Step 1: Apply PC-1
        String C = key56bit.substring(0, 28);     // Left half
        String D = key56bit.substring(28);        // Right half

        String[] subkeys = new String[16];

        for (int i = 0; i < 16; i++) {
            // Step 2: Shift both halves
            C = leftShift(C, SHIFTS[i]);
            D = leftShift(D, SHIFTS[i]);

            // Step 3: Combine and apply PC-2
            String combined = C + D;
            subkeys[i] = permute(combined, PC2);
        }

        return subkeys;
    }

    public static void main(String[] args) {
        // Example 64-bit key (in binary)
        String key = "0001001100110100010101110111100110011011101111001101111111110001";

        String[] keys = generateKeys(key);
        for (int i = 0; i < keys.length; i++) {
            System.out.println("K" + (i + 1) + ": " + keys[i]);
        }
    }
}
