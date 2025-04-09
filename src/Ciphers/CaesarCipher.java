package Ciphers;

import java.util.*;

public class CaesarCipher {

    public static String caesarCipher(String text, int shift){
        StringBuilder result = new StringBuilder();
        for(char ch: text.toCharArray()){
            if(Character.isLetter(ch)){
                char base = Character.isUpperCase(ch)?'A':'a';
                result.append((char)((ch-base+shift+26)%26+base));
            }else{
                result.append(ch);
            }
        }
        return result.toString();
    }

    public static void main(String[] args){
        Scanner sc  = new Scanner(System.in);
        System.out.print("Enter a string: ");
        String text = sc.nextLine();
        System.out.print("Enter a shift: ");
        int shift = sc.nextInt();
        String encryptedText = caesarCipher(text, shift);
        System.out.println("Caesar cipher text: " + encryptedText);
        System.out.println("Decypted text: " + caesarCipher(encryptedText    , -shift));
    }
}
