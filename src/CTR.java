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

    public CTR(String k) {
        // test
        this.key = k;

    }

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

    private String permutation(String X) {
        StringBuilder result = new StringBuilder("0000000000000000");
        StringBuilder x = new StringBuilder(X);

        for (int i = 0; i < 16; i++) {
            result.setCharAt(bitpermutation[i], x.charAt(i));
        }
        return result.toString();
    }

    private String sBox(String X) {
        StringBuilder result = new StringBuilder();
        StringBuilder x = new StringBuilder(X);
        for (int i = 0; i < 16; i += 4) {
            result.append(sbox.get(x.substring(i, i + 4)));
        }
        return result.toString();
    }

    private String sBoxInv(String X) {
        StringBuilder result = new StringBuilder();
        StringBuilder x = new StringBuilder(X);
        for (int i = 0; i < 16; i += 4) {
            result.append(getKey(x.substring(i, i + 4)));
        }

        return result.toString();
    }

    private String getKey(String value) {
        for (String key : sbox.keySet()) {
            if (sbox.get(key).equals(value)) {
                return key;
            }
        }
        return null;
    }


    private String doSPNinv(String x) {
        String y = "";
        y = xor(x, k0_); //init


        y = sBoxInv(y);
        y = permutation(y);
        y = xor(y, k1_);

        y = sBoxInv(y);
        y = permutation(y);
        y = xor(y, k2_);

        y = sBoxInv(y);
        y = permutation(y);
        y = xor(y, k3_);

        y = sBoxInv(y);
        y = xor(y, k4_);

        return y;

    }
    // TODO: no diffrent keys in CTR, x-1 and increment


    public String encrypt(String chiffre) {


        String result = "";

        return result;
    }

    public String decrypt(String chiffre) {
        yMin1 = chiffre.substring(0, 16);

        String[] block = new String[chiffre.length() / 16 - 1];

        for (int i = 0; i < chiffre.length(); i += 16) {
            block[i / 16] = chiffre.substring(i, i + 16);
        }

        String result = "";


        for (int i = 0; i < block.length; i++) {
            block[i] = xor(yMin1, k0_;

            block[i] = sBox(block[i]);
            block[i] = permutation(block[i]);
            block[i] = xor(block[i], k1_);

            block[i] = sBox(block[i]);
            block[i] = permutation(block[i]);
            block[i] = xor(block[i], k2_);

            block[i] = sBox(block[i]);
            block[i] = permutation(block[i]);
            block[i] = xor(block[i], k3_);

            block[i] = sBox(block[i]);
            block[i] = xor(block[i], k4_);

            plus1(yMin1);
        }
        return bitToText(result);
    }


    private String xor(String x, String y) {
        String result = "";
        for (int i = 0; i < 32; i++) {
            if (x.charAt(i) != y.charAt(i))
                result += '1';
            else
                result += '0';
        }
        return result;
    }

    private void plus1(String bitstream) {
        int bits = Integer.parseInt(bitstream, 2);
        bits += 1;
        bitstream = Integer.toString(bits);
    }

    private String bitToText(String bitstream) {
        String[] ascii = new String[bitstream.length() / 4];

        for (int i = 0; i < bitstream.length(); i += 4) {
            ascii[i / 4] = bitstream.substring(i, i + 4);
        }

        String result = "";

        for (int i = 0; i < ascii.length; i++) {
            result += (char) Integer.parseInt(ascii[i], 2);
        }

        return result;
    }

}
