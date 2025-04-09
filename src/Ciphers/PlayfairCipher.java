package Ciphers;

import java.util.*;

public class PlayfairCipher {
    private char[][] matrix;

    public PlayfairCipher(String key){
        matrix = generateMatrix(key);
    }

    public char[][] generateMatrix(String key) {
        key = key.toUpperCase().replace("J", "I");
        Set<Character> usedChars = new LinkedHashSet<>();
        String alphabet = "ABCDEFGHIKLMNOPQRSTUVWXYZ";
        for (char ch : (key + alphabet).toCharArray()) {
            if (!usedChars.contains(ch)) {
                usedChars.add(ch);
            }
        }
        char[][] matrix = new char[5][5];
        Iterator<Character> iter = usedChars.iterator();
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                matrix[i][j] = iter.next();
            }
        }
        return matrix;
    }

    private int[] findPosition(char ch){
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (matrix[i][j] == ch) {
                    return new int[]{i, j};
                }
            }
        }
        return null;
    }

    private List<String> processDigraphs(String text){
        text = text.toUpperCase().replace("J", "I").replace(" ","");
        List<String> digraphs = new ArrayList<>();
        StringBuilder sb = new StringBuilder(text);
        for(int i=0;i<sb.length();i+=2)
        {
            if(i==sb.length()-1 || sb.charAt(i)==sb.charAt(i+1)){
                sb.insert(i+1,'X');
            }
            digraphs.add(sb.substring(i,i+2));
        }
        return digraphs;
    }

    private String cipher(String text,boolean encrypt){
        List<String> digraphs = processDigraphs(text);
        StringBuilder results = new StringBuilder();

        for(String digraph:digraphs){
            int []pos1 = findPosition(digraph.charAt(0));
            int []pos2 = findPosition(digraph.charAt(1));

            if(pos1[0]==pos2[0]){
                pos1[1] = (pos1[1]+(encrypt?1:4))%5;
                pos2[1] = (pos2[1]+(encrypt?1:4))%5;
            }else if(pos1[1]==pos2[1]){
                pos1[0]= (pos1[0]+(encrypt?1:4))%5;
                pos2[0] = (pos2[0]+(encrypt?1:4))%5;
            }else{
                int temp = pos1[1];
                pos1[1] = pos2[1];
                pos2[1] = temp;
            }
            results.append(matrix[pos1[0]][pos1[1]]);
            results.append(matrix[pos2[0]][pos2[1]]);
        }

        return results.toString();
    }

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.println("Enter the text to be encrypted : ");
        String text = input.nextLine();
        PlayfairCipher c = new PlayfairCipher(text);
        String encrypted = c.cipher(text,true);
        System.out.println(encrypted);
        System.out.println("Decrypted text: " + c.cipher(encrypted,false));
    }
}
