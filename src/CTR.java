import java.util.HashMap;

public class CTR {

    private HashMap<String, String> sbox = new HashMap<String, String>() {{
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

    private int[] bitpermutation = {0, 4, 8, 12, 1, 5, 9, 13, 2, 6, 10, 14, 3, 7, 11, 15};

    private String key;
    private String yMin1;

    private String k0, k1, k2, k3, k4;
    private String k0_, k1_, k2_, k3_, k4_;

    /**
     * Constructor of CTR SPN object
     *
     * @param k 32 bit key
     */
    public CTR(String k) {
        this.key = k;
        calcKeys(key);
    }

    /**
     * Calculate 5 keys and there inverses
     *
     * @param k 32 bit key
     */
    private void calcKeys(String k) {
        k0 = "";
        for (int i = 0; i < 16; i++) {
            k0 += k.charAt(i);
        }
        k1 = "";
        for (int i = 4; i < 20; i++) {
            k1 += k.charAt(i);
        }
        k2 = "";
        for (int i = 8; i < 24; i++) {
            k2 += k.charAt(i);
        }
        k3 = "";
        for (int i = 12; i < 28; i++) {
            k3 += k.charAt(i);
        }
        k4 = "";
        for (int i = 16; i < 32; i++) {
            k4 += k.charAt(i);
        }

        k0_ = k4;
        k1_ = permutation(k3);
        k2_ = permutation(k2);
        k3_ = permutation(k1);
        k4_ = k0;
    }

    /**
     * Process Bitpermutation which is defined as class attribute
     *
     * @param X 16 Bit block
     * @return permutated Bitstream
     */
    private String permutation(String X) {
        StringBuilder result = new StringBuilder("0000000000000000");
        StringBuilder x = new StringBuilder(X);

        for (int i = 0; i < 16; i++) {
            result.setCharAt(bitpermutation[i], x.charAt(i));

        }
        return result.toString();
    }

    /**
     * Process S-Box which is defined as class attribute
     *
     * @param X 16 Bit block
     * @return processed Bitstream
     */
    private String sBox(String X) {
        StringBuilder result = new StringBuilder();
        StringBuilder x = new StringBuilder(X);
        for (int i = 0; i < 16; i += 4) {
            result.append(sbox.get(x.substring(i, i + 4)));
        }
        return result.toString();
    }

    /**
     * Process S-Box inverse
     *
     * @param X 16 Bit block
     * @return processed Bitstream
     */
    private String sBoxInv(String X) {
        StringBuilder result = new StringBuilder();
        StringBuilder x = new StringBuilder(X);
        for (int i = 0; i < 16; i += 4) {
            result.append(getKey(x.substring(i, i + 4)));
        }

        return result.toString();
    }

    /**
     * Get key out of the S-Box Hashmap
     *
     * @param value Hashmap value
     * @return key of Hashmap
     */
    private String getKey(String value) {
        for (String key : sbox.keySet()) {
            if (sbox.get(key).equals(value)) {
                return key;
            }
        }
        return null;
    }


    /**
     * Process the CTR SPN with all rounds
     *
     * @param x 16 Bit block
     * @return processed block
     */
    public String doSPN(String x) {
        String y = "";

        // initial round
        y = xor(x, k0);

        // 3 rounds
        y = sBox(y);
        y = permutation(y);
        y = xor(y, k1);

        y = sBox(y);
        y = permutation(y);
        y = xor(y, k2);

        y = sBox(y);
        y = permutation(y);
        y = xor(y, k3);

        // last round
        y = sBox(y);
        y = xor(y, k4);

        return y;

    }

    /**
     * Decryption algorithm
     *
     * @param chiffre Bitstream in blocks of 16 Bit
     * @return text as Bitstream
     */
    public String decrypt(String chiffre) {
        yMin1 = chiffre.substring(0, 16);

        // split chiffre from y-1
        String chiffreForDecode = chiffre.substring(16, chiffre.length());

        String[] y = new String[chiffreForDecode.length() / 16];
        String[] res = new String[chiffreForDecode.length() / 16];

        // y into blocks of 16
        for (int i = 0; i < chiffreForDecode.length(); i += 16) {
            y[i / 16] = chiffreForDecode.substring(i, i + 16);
        }

        // spn process for each block
        for (int i = 0; i < res.length; i++) {
            res[i] = doSPN(yMin1);
            yMin1 = plus1(yMin1);
        }

        // XOR after SPN with y
        String result = "";
        for (int i = 0; i < res.length; i++) {
            result += xor(res[i], y[i]);

        }
        return bitToText(removeAppendix(result));
    }

    /**
     * Remove the zeros with following one
     *
     * @param bitstring
     * @return resulting bitstream
     */
    private String removeAppendix(String bitstring) {
        StringBuilder removeZero = new StringBuilder(bitstring);

        // when last char 0 remove it
        if (removeZero.charAt(removeZero.length() - 1) == '0') {
            while (removeZero.charAt(removeZero.length() - 1) == '0') {
                removeZero.deleteCharAt(removeZero.length() - 1);

            }
            // remove following 1
            removeZero.deleteCharAt(removeZero.length() - 1);
        }
        return removeZero.toString();
    }

    /**
     * XOR two bitstreams of 16 bit
     *
     * @param x first bitstream
     * @param y second bitstream
     * @return XOR processed bitstream
     */
    private String xor(String x, String y) {
        String result = "";
        for (int i = 0; i < 16; i++) {
            if (x.charAt(i) != y.charAt(i))
                result += "1";
            else
                result += "0";
        }
        return result;
    }

    /**
     * Increment Bitstream with one
     *
     * @param bitstream
     * @return
     */
    private String plus1(String bitstream) {
        // convert string to integer and increment
        int bits = Integer.parseInt(bitstream, 2);
        bits = bits + 1;
        String bit = Integer.toString(bits, 2);

        // append needed zeros that bitsream % 16 = 0
        int numberOfBits = String.valueOf(bit).length();
        int numberOfLeadingZeroes = 16 - numberOfBits;
        StringBuilder toSixteenBit = new StringBuilder();
        if (numberOfLeadingZeroes > 0) {
            for (int i = 0; i < numberOfLeadingZeroes; i++) {
                toSixteenBit.append("0");
            }
        }
        toSixteenBit.append(bit);
        return toSixteenBit.toString();
    }


    /**
     * Convert Bitstream into a text
     *
     * @param bitstream
     * @return
     */
    private String bitToText(String bitstream) {
        String[] ascii = new String[bitstream.length() / 8];

        // into 8 bit blocks
        for (int i = 0; i < bitstream.length(); i += 8) {
            ascii[i / 8] = bitstream.substring(i, i + 8);
        }

        String result = "";

        for (int i = 0; i < ascii.length; i++) {
            result += (char) Integer.parseInt(ascii[i], 2);
        }

        return result;
    }

}
