package com.sesumy.aeschat_kripto.Encrypt;

import android.support.test.espresso.core.deps.guava.base.Splitter;
import android.support.test.espresso.core.deps.guava.collect.Iterables;
import android.util.Log;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sesumy on 9.12.2016.
 */
public class Encyrpt implements Serializable {
    private static final long serialVersionUID = 7526472295622776147L;
    private static final String charsetName="UTF-8";
    private static final char[] HEX_CHARS = "0123456789abcdef".toCharArray();
    static List<String> ikili = new ArrayList<String> ( );
    private static String[][] keyMatrisColumn = new String[4][4];
    private static String[][] newKey = new String[4][1] ;
    private static int satir = 0,sutun=0;
    private static String[][] newfirstKey = new String[4][4] ;
    private static String[][] textMatris = new String[4][4] ;

    static  int m=0;
    public static final String[][] sbox = {
            {"63", "7c", "77", "7b", "f2", "6b", "6f", "c5", "30", "01", "67", "2b", "fe", "d7", "ab", "76"},
            {"ca", "82", "c9", "7d", "fa", "59", "47", "f0", "ad", "d4", "a2", "af", "9c", "a4", "72", "c0"},
            {"b7", "fd", "93", "26", "36", "3f", "f7", "cc", "34", "a5", "e5", "f1", "71", "d8", "31", "15"},
            {"04", "c7", "23", "c3", "18", "96", "05", "9a", "07", "12", "80", "e2", "eb", "27", "b2", "75"},
            {"09", "83", "2c", "1a", "1b", "6e", "5a", "a0", "52", "3b", "d6", "b3", "29", "e3", "2f", "84"},
            {"53", "d1", "00", "ed", "20", "fc", "b1", "5b", "6a", "cb", "be", "39", "4a", "4c", "58", "cf"},
            {"d0", "ef", "aa", "fb", "43", "4d", "33", "85", "45", "f9", "02", "7f", "50", "3c", "9f", "a8"},
            {"51", "a3", "40", "8f", "92", "9d", "38", "f5", "bc", "b6", "da", "21", "10", "ff", "f3", "d2"},
            {"cd", "0c", "13", "ec", "5f", "97", "44", "17", "c4", "a7", "7e", "3d", "64", "5d", "19", "73"},
            {"60", "81", "4f", "dc", "22", "2a", "90", "88", "46", "ee", "b8", "14", "de", "5e", "0b", "db"},
            {"e0", "32", "3a", "0a", "49", "06", "24", "5c", "c2", "d3", "ac", "62", "91", "95", "e4", "79"},
            {"e7", "c8", "37", "6d", "8d", "d5", "4e", "a9", "6c", "56", "f4", "ea", "65", "7a", "ae", "08"},
            {"ba", "78", "25", "2e", "1c", "a6", "b4", "c6", "e8", "dd", "74", "1f", "4b", "bd", "8b", "8a"},
            {"70", "3e", "b5", "66", "48", "03", "f6", "0e", "61", "35", "57", "b9", "86", "c1", "1d", "9e"},
            {"e1", "f8", "98", "11", "69", "d9", "8e", "94", "9b", "1e", "87", "e9", "ce", "55", "28", "df"},
            {"8c", "a1", "89", "0d", "bf", "e6", "42", "68", "41", "99", "2d", "0f", "b0", "54", "bb", "16"}
    };

    public static final String[][] rcon = {
    {"01","02","04","08","10","20","40","80","1b","36"},
    {"00","00","00","00","00","00","00","00","00","00"},
    {"00","00","00","00","00","00","00","00","00","00"},
    {"00","00","00","00","00","00","00","00","00","00"}
    };

    public Encyrpt() {super ( );}
    public static  String  sifrele(BigInteger key, String text){
        keyCreate (key); //keyMatrisColumn oluşturuldu...
        //ilk olarak aldığımız key ile text i xorlayacağız
        //Texti hexaya çevirip diziye atalım.
        textMatris=matrisConvert (text);
        for(int i=0;i<4;i++) {
            for (int j = 0; j < 4; j++) {
                textMatris[i][j]=xorHex (textMatris[i][j],keyMatrisColumn[i][j]);
            }
        }//Şuan ilk key ile xorlama işlemimiiz yapıldı
        //ADDRAOUND KEY 1.AŞAMA TAMAMLANDI.


        for(int i=0;i<9;i++) {
            Log.e(String.valueOf (i)," Döngü numarası ");
            // 1- Subsituation Box 'tan değerlerini bulucaz.
            sBoxConvert (textMatris);
            // 2- Shift Row (Satırları sola kaydıracağız.)
            shiftRows (textMatris);
            // 3- Mix Column (Sutunları Belli bir matrisle Çarpıp colonları karıştırmış olacağız. )
            //Mix column işlmi bir matrisle çarpım durumudur.

            mixColumn (textMatris);
            //4-Şimdide anahtarımızı Ekledik(Yeni oluşan)
            addRoundKey(textMatris);
        }

        sBoxConvert (textMatris);
        shiftRows (textMatris);
        addRoundKey (textMatris);


        return matrisToString(textMatris);
    }
    public static String matrisToString(String[][] matris){

        StringBuilder stringBuilder=new StringBuilder ();

        for(int i=0;i<4;i++){

            for(int j=0;j<4;j++){
                stringBuilder.append(matris[i][j]);

            }

        }
        return stringBuilder.toString ();
    }
    public static String[][] addRoundKey(String[][] matris) {

         keySchedule ( );

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                matris[i][j] = xorHex (matris[i][j], keyMatrisColumn[i][j]);
            }
        }
        textMatris=matris;
    return textMatris;
    }
    public static List<String > keyCreate(BigInteger  key) {


        String k = key.toString ( );
        String keyString = "";
        for (char c : k.toCharArray ( )) {
            String oneChar = String.format ("%08d", Integer.parseInt (Integer.toBinaryString ((int) c)));
            keyString += oneChar;
            //Binary 'e çevrilmiş hali
        }

        Iterable<String> result = Splitter.fixedLength (4).split (keyString);
        String[] parts = Iterables.toArray (result, String.class);

        List<String> tekli = new ArrayList<String> ( );
        for (int i = 0; i < parts.length; i++) {

            int decimal = Integer.parseInt (parts[i], 2);
            String hexStr = Integer.toString (decimal, 16);
            tekli.add (hexStr);
        }

        List<String> ikili = new ArrayList<String> ( );
        String a;
        String b;
        StringBuilder br = new StringBuilder ( );

        for (int i = 0; i < parts.length - 1; i = i + 2) {
            a = tekli.get (i);
            b = tekli.get (i + 1);
            br.append (a);
            br.append (b);
            ikili.add (br.toString ( ));
            br.setLength (0);
        }
        System.out.println ("ikili dizi : "+ ikili);

        int l=0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                keyMatrisColumn[i][j] = ikili.get (l);
                l = l + 1;

            }
        }
        return ikili;
    }
    public static String[][] keySchedule() {

        newKey[0][0]=keyMatrisColumn[3][3];
        newKey[1][0]=keyMatrisColumn[0][3];
        newKey[2][0]=keyMatrisColumn[1][3];
        newKey[3][0]=keyMatrisColumn[2][3];

        int s=0;
        for(int i=0;i<4;i++){
            for (char ch: newKey[i][0].toCharArray()) {

                if(s==0){
                    satir = Character.digit(ch,16);
                    s++;
                }
                else{
                    sutun = Character.digit(ch,16);
                }}
            s=0;
            newKey[i][0]=sbox[satir][sutun];
        }
        for(int i=0;i<4;i++){
            newfirstKey[i][0]=  xorHex( xorHex(keyMatrisColumn[i][0],newKey[i][0]),rcon[i][m]);
        }
        for(int i=0;i<4;i++){
            for(int j=0;j<3;j++){
                newfirstKey[i][j+1] =xorHex(keyMatrisColumn[i][j+1],newfirstKey[i][j]);
                //Burada 1.matrisin 2.sutunuyla 2.matrisin 1.sutunu xorlanıyor
            }
        }
        System.out.print("Yeni Anahtar ");
        for(int i=0;i<4;i++){
            for(int j=0;j<4;j++){
                System.out.print(newfirstKey[i][j]);
                //Burada 1.matrisin 2.sutunuyla 2.matrisin 1.sutunu xorlanıyor
            }
            System.out.println(" ");
        }
        keyMatrisColumn=newfirstKey;
        m++;
        return keyMatrisColumn;
    }
    public static String[][] shiftRows(String[][] matris){
        String[][] newText = new String[4][4] ;
        newText[0][0]=matris[0][0];
        newText[0][1]=matris[0][1];
        newText[0][2]=matris[0][2];
        newText[0][3]=matris[0][3];

        newText[1][0]=matris[1][1];
        newText[1][1]=matris[1][2];
        newText[1][2]=matris[1][3];
        newText[1][3]=matris[1][0];

        newText[2][0]=matris[2][2];
        newText[2][1]=matris[2][3];
        newText[2][2]=matris[2][0];
        newText[2][3]=matris[2][1];

        newText[3][0]=matris[3][3];
        newText[3][1]=matris[3][0];
        newText[3][2]=matris[3][1];
        newText[3][3]=matris[3][2];
        textMatris=newText;
        return textMatris;
    }
    public static String[][] mixColumn(String[][] matris){
        String[][] mixColumnTable = {{"02","03","01","01"},{"01","02","03","01"},{"01","01","02","03"},{"03","01","01","02"}};
        //mixColumn işlemi için buradaki değerleri kullanacağız.
        String[][] returnMatris = new String[4][4];
        for(int i=0;i<4;i++){
            returnMatris[0][i]=
                    binarytoHex(xorBinaryString(
                            //2
                            binMixColumnDuzenle(hexToBinary(matris[0][i])),
                            //3
                            binMixColumnDuzenle(hexToBinary(matris[1][i])),
                            hexToBinary(matris[1][i]),
                            //1
                            hexToBinary(matris[2][i]),
                            //1
                            hexToBinary(matris[3][i])

                    ));

            returnMatris[1][i]=
                    binarytoHex(xorBinaryString(
                            // 1
                            hexToBinary(matris[0][i]),
                            //   2
                            binMixColumnDuzenle(hexToBinary(matris[1][i])),
                            //  3
                            binMixColumnDuzenle(hexToBinary(matris[2][i])),
                            hexToBinary(matris[2][i]),
                            //1
                            hexToBinary(matris[3][i])

                    ));

            returnMatris[2][i]=

                    binarytoHex( xorBinaryString(
                            // 1
                            hexToBinary(matris[0][i]),
                            //1
                            hexToBinary(matris[1][i]),
                            //   2
                            binMixColumnDuzenle(hexToBinary(matris[2][i])),
                            //  3
                            binMixColumnDuzenle(hexToBinary(matris[3][i])),
                            hexToBinary(matris[3][i])


                    ));

            returnMatris[3][i]=
                    binarytoHex( xorBinaryString(
                            //  3
                            binMixColumnDuzenle(hexToBinary(matris[0][i])),
                            hexToBinary(matris[0][i]),
                            // 1
                            hexToBinary(matris[1][i]),
                            //1
                            hexToBinary(matris[2][i]),
                            //   2
                            binMixColumnDuzenle(hexToBinary(matris[3][i]))
                    ));

        }
        System.out.print("After Mix Column");
        for(int k=0;k<4;k++){
            for(int l=0;l<4;l++){
                System.out.print(" "+ returnMatris[k][l]);
            }
            System.out.println(" ");
        }

        return returnMatris;
    }
    public static String[][] matrisConvert(String text){

        String k = text;
        String stringChar = "";
        for (char c : k.toCharArray ( )) {
            String oneChar = String.format ("%08d", Integer.parseInt (Integer.toBinaryString ((int) c)));
            stringChar += oneChar;
            //Binary 'e çevrilmiş hali
        }

        Iterable<String> result = Splitter.fixedLength (4).split (stringChar);
        String[] parts = Iterables.toArray (result, String.class);

        List<String> tekli = new ArrayList<String> ( );
        for (int i = 0; i < parts.length; i++) {

            int decimal = Integer.parseInt (parts[i], 2);
            String hexStr = Integer.toString (decimal, 16);
            tekli.add (hexStr);
        }

        List<String> ikiliText = new ArrayList<String> ( );
        String a;
        String b;
        StringBuilder br = new StringBuilder ( );

        for (int i = 0; i < parts.length - 1; i = i + 2) {
            a = tekli.get (i);
            b = tekli.get (i + 1);
            br.append (a);
            br.append (b);
            ikiliText.add (br.toString ( ));
            br.setLength (0);
        }
        System.out.println ("Matrise çevirilecek Liste text" + ikiliText);

        int l=0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                textMatris[i][j] = ikiliText.get (l);
                l = l + 1;
            }
        }
        return textMatris;
    }
    private static String[][] sBoxConvert(String[][] strings) {
        int s = 0;
        for (int i = 0; i < 4; i++) {
            for (char ch : strings[i][0].toCharArray ( )) {

                if (s == 0) {
                    satir = Character.digit (ch, 16);
                    s++;
                } else {
                    sutun = Character.digit (ch, 16);
                }
            }
            s = 0;
            strings[i][0] = sbox[satir][sutun];
        }
        return  strings;
    }
    public static String xorHex(String a, String b) {
        // TODO: Validation
        char[] chars = new char[a.length()];
        for (int i = 0; i < chars.length; i++) {
            chars[i] = toHex(fromHex(a.charAt(i)) ^ fromHex(b.charAt(i)));
        }
        return new String(chars);
    }
    private static int fromHex(char c) {
        if (c >= '0' && c <= '9') {
            return c - '0';
        }
        if (c >= 'A' && c <= 'F') {
            return c - 'A' + 10;
        }
        if (c >= 'a' && c <= 'f') {
            return c - 'a' + 10;
        }
        throw new IllegalArgumentException();
    }
    private static char toHex(int nybble) {
        if (nybble < 0 || nybble > 15) {
            throw new IllegalArgumentException();
        }
        return "0123456789ABCDEF".charAt(nybble);
    }
    public static String hexadecimal(String text) throws UnsupportedEncodingException {
        if (text == null) throw new NullPointerException();
        return asHex(text.getBytes(charsetName));
    }
    public static String asHex(byte[] buf) {
        char[] chars = new char[2 * buf.length];
        for (int i = 0; i < buf.length; ++i)
        {
            chars[2 * i] = HEX_CHARS[(buf[i] & 0xF0) >>> 4];
            chars[2 * i + 1] = HEX_CHARS[buf[i] & 0x0F];
        }
        return new String(chars);
    }
    static String binMixColumnDuzenle(String binString){
        ArrayList<Character> array=new ArrayList<>();
        ArrayList<String> arrayString=new ArrayList<String>();
        String listString = "";


        for(char ch:binString.toCharArray()){
            array.add(ch);
        }
        if(array.get(0)=='1'){ //EĞER ÇIKARACAĞIMIZ ELEMAN 1 ise 1B ile çarpmalıyız.
            array.remove(0);
            array.add('0');
            for (char s : array ){listString += s;}

            return	xorBinaryString(listString, "00011011");

        }
        else{ //EĞER ÇIKARACAĞIMIZ ELEMAN 0 ise Zaten iki ile çarpıldığından birşey yapmayacağız.

            array.remove(0);
            array.add('0');
            for (char s : array ){listString += s;}
            return listString;
        }
    }
    private static boolean bitOf(char in) {
        return (in == '1');
    }
    private static char charOf(boolean in) {
        return (in) ? '1' : '0';
    }
    public static String xorBinaryString(String a,String b) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < a.length(); i++) {
            sb.append(charOf(bitOf(a.charAt(i)) ^ bitOf(b.charAt(i))
            ));
        }

        String result = sb.toString();
        return result;
    }
    public static String xorBinaryString(String a,String b,String c,String d, String e) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < a.length(); i++) {
            sb.append(charOf(
                    bitOf(a.charAt(i)) ^ bitOf(b.charAt(i)) ^ bitOf(c.charAt(i))^bitOf(d.charAt(i))^bitOf(e.charAt(i)) ));
        }

        String result = sb.toString();
        return result;
    }
    static String hexToBinary(String Hex){
        int i = Integer.parseInt(Hex,16);
        String binary = String.format ("%08d",Integer.parseInt(Integer.toBinaryString(i)));
        return binary;

    }
    public static int strToDec(String a){
        int k= (int) Long.parseLong(a, 16);
        return k;
    }
    public static String decToHex(int a){
        String t;
        t=Integer.toHexString(a);
        return t;
    }

    private static String binarytoHex(String binary){
        int decimal = Integer.parseInt(binary,2);
        String hexStr = Integer.toString(decimal,16);
        if(hexStr.length()==1){
            StringBuilder strBu=new StringBuilder();
            strBu.append("0");
            strBu.append(hexStr);
            hexStr=strBu.toString();
        }
        return hexStr;
    }

}
