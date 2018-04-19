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
    static String k = "00111010100101001101011000111111";
    static String X_TEST = "0001001010001111";
    static String Y_TEST = "1010111010110100";

    static StringBuilder k0 = new StringBuilder();
    static StringBuilder k1 = new StringBuilder();
    static StringBuilder k2 = new StringBuilder();
    static StringBuilder k3 = new StringBuilder();

    static StringBuilder k0_strich = new StringBuilder();
    static StringBuilder k1_strich = new StringBuilder();
    static StringBuilder k2_strich = new StringBuilder();
    static StringBuilder k3_strich = new StringBuilder();




    public static void main(String[] args) {




        sbox = new HashMap<String, String>() {{
            put("0000", "1110");
            put("0001", "0100");
            put("0010", "1101");
            put("0011", "0001");
            put("0100", "0010");
            put("0101", "1111");
            put("0110", "1011");
            put("0111", "1000");
            put("1000", "0011");
            put("1001", "1010");
            put("1010", "0110");
            put("1011", "1100");
            put("1100", "0101");
            put("1101", "1001");
            put("1110", "0000");
            put("1111", "0111");
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
        calculateKeys(k);
        getinvertKey();


        String Y_decrypted = decrypt(Y_TEST);
        System.out.print("Decrypted Value: " +Y_decrypted);

       /* Scanner scanner = new Scanner(System.in);
        String clearText = scanner.next();*/
    }

    public static void calculateKeys(String k) {
        for (int i = 0; i < 17; i++) {
            k0.append(k.charAt(i));
        }

        for (int i = 4; i < 21; i++) {
            k1.append(k.charAt(i));
        }

        for (int i = 8; i < 25; i++) {
            k2.append(k.charAt(i));
        }

        for (int i = 12; i < 29; i++) {
            k3.append(k.charAt(i));
        }
    }

    public static void calculateinvertKeys(String k) {

    }

    public static void key_xor() {

    }

    public static String sbox(String key) {
        StringBuilder value = new StringBuilder();

        while (key.length() % 16 != 0) {
            value.append(sbox.get(key));
        }
        return value.toString();
    }
    public static String sbox_inv(String x) {
        StringBuilder value = new StringBuilder();
String x_blocks = "";
        while (x.length() != 16) {
            int y = 4;
            for(int i = 0; i< x.length();i++){
                y = (i * 4) -1;
                i = i*4;
                x_blocks = x.substring(i,y);
                if(sbox.containsValue(x_blocks)) {
                    value.append(getKeysByValue(sbox,x_blocks));
                }else{
                    return "Im done Ghillardelli will fix this shit!!!";
                }
                }

        }
        return value.toString();
    }
    public static <T, E> Set<T> getKeysByValue(Map<T, E> map, E value) {
        return map.entrySet()
                .stream()
                .filter(entry -> Objects.equals(entry.getValue(), value))
                .map(Map.Entry::getKey)
                .collect(Collectors.toSet());
    }

    public static String bitmermutation(String bits, HashMap<Integer, Integer> permutation) {
        StringBuilder newBit = new StringBuilder();
        for (int i = 0; i < permutation.size(); i++) {
            newBit.insert(i, bits.charAt(permutation.get(i)));
        }
        String newBitreturn = newBit.toString();

        return newBitreturn;
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

    public static void ctrCount() {

    }
    public static void doSpn(String x ){
String y0 = weisschritt(X_TEST,k0_strich);
int counter = 1;
for (int i = 0; i < r-1; i++){
    String y0_sbox = sbox(y0);
    String y0_bitpermut = bitmermutation(y0_sbox,bitpermutation);
    String y1;
}

    }

    public static String weisschritt(String x,StringBuilder k0){
        StringBuilder result = new StringBuilder();
        int key0 = Integer.parseInt(k0.toString(),2);
int y0 = Integer.parseInt(x,2);
int result_xor = key0^y0;
        result.append(result_xor);
        System.out.print("Weisschritt: "+result);
        return result.toString();
    }

    public static String decrypt(String y){
        String reslut=  weisschritt(y,k0_strich);

        reslut = sbox_inv(reslut.toString());
reslut = bitmermutation(reslut,bitpermutation);
reslut = weisschritt(reslut,k1);
reslut = sbox_inv(reslut.toString());
reslut = bitmermutation(reslut,bitpermutation);
reslut = weisschritt(reslut,k2);
reslut=   sbox_inv(reslut.toString());
reslut = weisschritt(reslut,k3);

        return reslut;
    }
    public static void getinvertKey(){
        k0_strich = k3;
        k1_strich.append(bitmermutation(k2.toString(),bitpermutation));
        k2_strich.append(bitmermutation(k1.toString(),bitpermutation));
        k3_strich = k0;
    }
}