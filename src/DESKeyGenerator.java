import java.util.Arrays;

public class DESKeyGenerator {
    // Permuted Choice 1 (PC-1) Table (64-bit -> 56-bit key)
    private static final int[] PC1 = {
            57,49,41,33,25,17,9, 1,58,50,42,34,26,18,
            10,2,59,51,43,35,27,19, 11,3,60,52,44,36,63,
            55,47,39,31,23,15,7, 62,54,46,38,30,22,14,6,
            61,53,45,37,29,21,13,5, 28,20,12,4
    };

    // Permuted Choice 2 (PC-2) Table (56-bit -> 48-bit key)
    private static final int[] PC2 = {
            14,17,11,24,1,5, 3,28,15,6,21,10,
            23,19,12,4,26,8, 16,7,27,20,13,2,
            41,52,31,37,47,55, 30,40,51,45,33,48,
            44,49,39,56,34,53, 46,42,50,36,29,32
    };

    // Number of Left Shifts for Each Round
    private static final int[] SHIFT_SCHEDULE = {
            1, 1, 2, 2, 2, 2, 2, 2, 1, 2, 2, 2, 2, 2, 2, 1
    };

    // Function to apply Permuted Choice 1 (PC-1)
    private static long permutedChoice1(long key) {
        long permutedKey = 0;
        for (int i = 0; i < 56; i++) {
            int bitPos = 64 - PC1[i];
            long bit = (key >> bitPos) & 1;
            permutedKey |= (bit << (55 - i));
        }
        return permutedKey;
    }

    // Function to apply Permuted Choice 2 (PC-2)
    private static long permutedChoice2(long key) {
        long permutedKey = 0;
        for (int i = 0; i < 48; i++) {
            int bitPos = 56 - PC2[i];
            long bit = (key >> bitPos) & 1;
            permutedKey |= (bit << (47 - i));
        }
        return permutedKey;
    }

    // Function to perform Left Circular Shifts
    private static long leftCircularShift(long keyPart, int shifts) {
        return ((keyPart << shifts) | (keyPart >> (28 - shifts))) & 0x0FFFFFFFL;
    }

    // Function to generate 16 round keys
    public static long[] generateSubkeys(long key) {
        long[] subkeys = new long[16];

        // Step 1: Apply PC-1 to get the 56-bit key
        long permutedKey = permutedChoice1(key);

        // Split into 28-bit halves
        int C = (int) (permutedKey >> 28);
        int D = (int) (permutedKey & 0x0FFFFFFF);

        // Generate 16 subkeys
        for (int i = 0; i < 16; i++) {
            C = (int) leftCircularShift(C, SHIFT_SCHEDULE[i]);
            D = (int) leftCircularShift(D, SHIFT_SCHEDULE[i]);

            // Combine C and D into a 56-bit key
            long combinedKey = ((long) C << 28) | D;

            // Apply PC-2 to get the final 48-bit subkey
            subkeys[i] = permutedChoice2(combinedKey);
        }

        return subkeys;
    }

    public static void main(String[] args) {
        long key = 0x133457799BBCDFF1L;  // Example 64-bit key
        long[] subkeys = generateSubkeys(key);

        // Print generated subkeys
        for (int i = 0; i < 16; i++) {
            System.out.printf("Round %2d Key: %012X\n", i + 1, subkeys[i]);
        }
    }
}
