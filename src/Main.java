import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * spn data
 * r = 4
 * n = 4
 * m = 4
 * s = 32
 */
public class Main {
    private static HashMap<String, String> sbox;
    private static HashMap<Integer, Integer> bitpermutation;


    static int r = 4;
    static int n = 4;
    static int m = 4;
    static int s = 32;

    static String X_TEST = "0001001010001111";
    static String Y_TEST = "1010111010110100";
    static String K_TEST = "00010001001010001000110000000000";

    static String chiffre ="000001001101001000001011101110000000001010001" +
            "111100011100111111101100000010100010100001110" +
            "10000000010011011001110010101110110000";
    static String k = "00111010100101001101011000111111";

    public static void main(String[] args) {

        // Test encryption
//        CTR test = new CTR(K_TEST);
//        String encryption = test.decrypt(X_TEST);
//        System.out.println(encryption);
//        System.out.println(Y_TEST.equals(encryption));

        // decrypt chiffre
        CTR ctr1 = new CTR(k);
        String decryption = ctr1.decrypt(chiffre);
        System.out.println(decryption);

    }



    public static <T, E> Set<T> getKeysByValue(Map<T, E> map, E value) {
        return map.entrySet()
                .stream()
                .filter(entry -> Objects.equals(entry.getValue(), value))
                .map(Map.Entry::getKey)
                .collect(Collectors.toSet());
    }


    public static String fromASCIItoString(int[] input) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < input.length; i++) {
            result.append(Character.toString((char) input[i]));
        }
        return result.toString();
    }

    public String intToBinary(int ascIIvalText) {
        String binary = Integer.toString(ascIIvalText, 2);
        return binary;
    }

    public int binaryToint(String binary) {
        int intval = Integer.parseInt(binary, 2);
        return intval;
    }


    public static String weissschritt(String x, StringBuilder k0) {
        StringBuilder result = new StringBuilder();
        int key0 = Integer.parseInt(k0.toString(), 2);
        int y0 = Integer.parseInt(x, 2);
        int result_xor = key0 ^ y0;
        result.append(result_xor);
        System.out.print("Weisschritt: " + result);
        return result.toString();
    }

  /*  public static String encrypt(String yMinus1) {
        calculateKeys(K_TEST);
        String result = weissschritt(k0.toString(), new StringBuilder(X_TEST));
        result = sbox(result);
        result = bitpermutation(result, bitpermutation);
        result = weissschritt(k1.toString(), new StringBuilder(result));
        result = sbox(result);
        result = bitpermutation(result, bitpermutation);
        result = weissschritt(k2.toString(), new StringBuilder(result));
        result = sbox(result);
        result = weissschritt(k3.toString(), new StringBuilder(result));

        return result;
    }

    public static String decrypt(String y) {
        String result = weissschritt(y, k0_strich);

        result = sbox_inv(result.toString());
        result = bitpermutation(result, bitpermutation);
        result = weissschritt(result, k1);
        result = sbox_inv(result.toString());
        result = bitpermutation(result, bitpermutation);
        result = weissschritt(result, k2);
        result = sbox_inv(result.toString());
        result = weissschritt(result, k3);

        return result;
    }*/
}