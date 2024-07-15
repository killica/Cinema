package cinema;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;

public class MessageDigest {

    private String s1;

    public static byte[] getSHA(String input) throws NoSuchAlgorithmException {
        // Static getInstance metoda se poziva
        //njome odredjujemo koji SHA algoritam zelimo da koristimoA 
        java.security.MessageDigest md = java.security.MessageDigest.getInstance("SHA-256");

        //metoda digest() se poziva i ona vraca niz bajtova
        //nastalih hesiranjem prosledjenog stringa
        return md.digest(input.getBytes(StandardCharsets.UTF_8));
    }

    public static String toHexString(byte[] hash) {
        BigInteger number = new BigInteger(1, hash);

        // Pretvaranje "digestovanog" stringa u hex vrednost
        StringBuilder hexString = new StringBuilder(number.toString(16));

        // Dodavanje vodecih nula
        while (hexString.length() < 32) {
            hexString.insert(0, '0');
        }

        return hexString.toString();
    }

    public MessageDigest(String s1) {
        try {
            this.s1 = toHexString(getSHA(s1));
        } // Za navodjenje pogresnih algoritama za hesiranje
        catch (NoSuchAlgorithmException e) {
            System.out.println("Exception thrown for incorrect algorithm: " + e);
        }
    }

    public String getSHA() {
        return this.s1;
    }

}
