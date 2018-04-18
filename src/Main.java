import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;

/**
 * spn data
 *  r = 4
 *  n = 4
 *  m = 4
 *  s = 32
 */
public class Main {
    private static HashMap<Integer, Integer> sbox;
    private static HashMap<Integer, Integer> bitpermutation;

    static int r = 4;
    static int n = 4;
    static int m = 4;
    static int s = 32;
    static String k = "00111010100101001101011000111111";

    static StringBuilder k0 = new StringBuilder();
    static StringBuilder k1 = new StringBuilder();
    static StringBuilder k2 = new StringBuilder();
    static StringBuilder k3 = new StringBuilder();

    public static void main(String[] args) {
        sbox = new HashMap<Integer, Integer>() {{
            put(0, 0);
            put(1, 4);
            put(2, 13);
            put(3, 1);
            put(4, 2);
            put(5, 15);
            put(6, 11);
            put(7, 8);
            put(8, 3);
            put(9, 10);
            put(10, 6);
            put(11, 12);
            put(12, 5);
            put(13, 9);
            put(14, 0);
            put(15, 7);
        }};

        bitpermutation = new HashMap<Integer, Integer>() {{
            put(0, 0);
            put(1, 4);
            put(2, 8);
            put(3, 12);
            put(4, 1);
            put(5, 5);
            put(6, 9);
            put(7, 13);
            put(8, 2);
            put(9, 6);
            put(10, 10);
            put(11, 14);
            put(12, 3);
            put(13, 7);
            put(14, 11);
            put(15, 15);
        }};
System.out.print("bitpermutation generated "+bitpermutation +"\n NewArray: "+bitmermutation("1010101010101010",bitpermutation));
        calculateKeys(k);

        Scanner scanner = new Scanner(System.in);
        String clearText = scanner.next();
    }

    public static void calculateKeys(String k){
        for(int i = 0; i < 17; i++){
            k0.append(k.charAt(i));
        }

        for(int i = 4; i < 21; i++){
            k1.append(k.charAt(i));
        }

        for(int i = 8; i < 25; i++){
            k2.append(k.charAt(i));
        }

        for(int i = 12; i < 29; i++){
            k3.append(k.charAt(i));
        }
    }

    public static void calculateinvertKeys(String k){

    }

    public static void key_xor(){

    }

    public static void sbox(){

    }

    public static String bitmermutation(String bits, HashMap<Integer,Integer> permutation){
StringBuilder newBit = new StringBuilder();
      for(int i = 0; i< permutation.size();i++){
          newBit.insert(i,bits.charAt(permutation.get(i)));
          System.out.print(newBit);
          }
          String newBitreturn = newBit.toString();

return newBitreturn;
    }
    public static String fromASCIItoString(int[] input){
      StringBuilder result = new StringBuilder();
      for(int i = 0; i < input.length;i++) {
          result.append(Character.toString((char) input[i]));
      }
      return result.toString();
    }
    public String intToBinary(int ascIIvalText){
        String binary = Integer.toString(ascIIvalText,2);
        return binary;
    }
    public int binaryToint(String binary){
        int intval = Integer.parseInt(binary, 2);
        return intval;
    }
}