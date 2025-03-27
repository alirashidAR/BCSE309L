import java.util.*;

public class HillCipher {
    private static int[][] keyMatrix;
    private static int[][] inverseKeyMatrix;
    private static int mod = 26;

    public static String encrypt(String text) {
        text = text.toUpperCase().replaceAll("[^A-Z]", "");
        int[] textVector = getNumericVector(text);
        int[] cipherVector = matrixMultiply(keyMatrix, textVector);
        return getStringFromVector(cipherVector);
    }

    public static String decrypt(String text) {
        int[] cipherVector = getNumericVector(text);
        int[] plainVector = matrixMultiply(inverseKeyMatrix, cipherVector);
        return getStringFromVector(plainVector);
    }

    private static int[] getNumericVector(String text) {
        int[] vector = new int[text.length()];
        for (int i = 0; i < text.length(); i++) {
            vector[i] = text.charAt(i) - 'A';
        }
        return vector;
    }

    private static String getStringFromVector(int[] vector) {
        StringBuilder result = new StringBuilder();
        for (int num : vector) {
            result.append((char) ('A' + num));
        }
        return result.toString();
    }

    private static int[] matrixMultiply(int[][] matrix, int[] vector) {
        int[] result = new int[vector.length];
        for (int i = 0; i < matrix.length; i++) {
            result[i] = 0;
            for (int j = 0; j < matrix[0].length; j++) {
                result[i] += matrix[i][j] * vector[j];
            }
            result[i] = Math.floorMod(result[i], mod);
        }
        return result;
    }

    private static int[][] invertKeyMatrix(int[][] matrix) {
        int det = determinant(matrix);
        int detInverse = modInverse(det, mod);
        int[][] adjugate = adjugateMatrix(matrix);
        int[][] inverse = new int[matrix.length][matrix.length];

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                inverse[i][j] = Math.floorMod(adjugate[i][j] * detInverse, mod);
            }
        }
        return inverse;
    }

    private static int determinant(int[][] matrix) {
        return Math.floorMod(matrix[0][0] * matrix[1][1] - matrix[0][1] * matrix[1][0], mod);
    }

    private static int modInverse(int num, int m) {
        for (int x = 1; x < m; x++) {
            if ((num * x) % m == 1) {
                return x;
            }
        }
        throw new ArithmeticException("Modular inverse does not exist");
    }

    private static int[][] adjugateMatrix(int[][] matrix) {
        int[][] adj = new int[2][2];
        adj[0][0] = matrix[1][1];
        adj[0][1] = -matrix[0][1];
        adj[1][0] = -matrix[1][0];
        adj[1][1] = matrix[0][0];
        return adj;
    }

    public static void main(String[] args) {
        String plaintext = "HELP";
        int[][] key = {{2, 3}, {3, 6}}; // Example 2x2 key matrix

        keyMatrix = key;
        inverseKeyMatrix = invertKeyMatrix(key);

        String encrypted = encrypt(plaintext);
        String decrypted = decrypt(encrypted);

        System.out.println("Encrypted: " + encrypted);
        System.out.println("Decrypted: " + decrypted);
    }
}
