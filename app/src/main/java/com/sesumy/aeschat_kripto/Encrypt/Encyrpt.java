package com.sesumy.aeschat_kripto.Encrypt;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sesumy on 9.12.2016.
 */
public class Encyrpt {
    private static final String KEY_ = "Merhaba";
    static String  key_Binary(String key){
        String intString="";
        String key_toBinary="";
        List<Integer> ascii = new ArrayList<> ();
            dongu_bitir:
            while(key_toBinary.length()<129){
                for (char c : key.toCharArray()) {
                    ascii.add((int) c);
                    intString=String.format("%08d", Integer.parseInt(Integer.toBinaryString((int) c)));
                    System.out.println(c);
                    key_toBinary+=intString;
                    if(key_toBinary.length()==128){
                        System.out.println("128 bit tamamlandı");
                        break dongu_bitir;
                    }
                }
            }
            System.out.println(key_toBinary);
            System.out.println(key_toBinary.length());
            return intString;
        }
    public String ECB_Encrypt(String Plain_Text){
        // Electronic Codebook
        // En temel block modudur. Plain-text, şifreleme algoritmasının istediği
        // uzunluklarda ki bloklar halinde bölünerek doğrudan işleme alınmasıdır.
        // Kolay hacklenebilir.
        // Bu yöntemin dezavantajı aynı açık metinlerin şifrelendiğinde aynı kapalı metinlere dönüştürmesidir; bu yüzden
        // mesajdaki örüntüleri belli eder. Başka bir deyişle mesaj gizliliği sağlanmayabilir ve
        // kriprografik protokollerde kullanılması tavsiye olunmaz.
        String intString="";
        String  plainText_toBinary="";
        List<Integer> ascii = new ArrayList<> ();
        //128 bite ayırarak xor lama işlemi yapacağız...

        for (char c : Plain_Text.toCharArray()) {
            ascii.add((int) c);
            intString=String.format("%08d", Integer.parseInt(Integer.toBinaryString((int) c)));
            System.out.println(c);
            plainText_toBinary+=intString;
        }
        plainText_toBinary.length ();


        return Plain_Text;
    }
    public String  CBC_Encrypt(String Plain_Text) {
        // Cipher Block Chaning-Elektronik Kod Defteri
        // CBC, ECB’nin problemine ( ECB’nin deterministik olması problemi )çok net bir çözüm getirmektedir.
        return Plain_Text;
    }
    public String  OFB_Encrypt(String Plain_Text) {
        // Output Feedback Mod
        // OFB ise CBC’den daha da farklı bir yaklaşım ile plain-text’i
        // datalara bölmektedir. İşin ilginci şifreleme algoritması plain-text üzerinde değilde IV ve Key üzerinde uygulanmaktadır.

        return Plain_Text;
    }
    public String  CFB_Encrypt(String Plain_Text) {

        return Plain_Text;
    }
    public String  CTR_Encrypt(String Plain_Text) {

        return Plain_Text;
    }
    public String  PCBC_Encrypt(String Plain_Text) {
        // Yayılan şifre-bloğu zincirleme kipi ya da açık metin şifre-bloğu zincirleme kipi kapalı metindeki ufak değişikliklerin
        // deşifreleme yaparken sonraki bloklara belirsiz bir şekilde yayılması için tasarlanmıştır.
        return Plain_Text;
    }
    public String OCB_Encrypt(String Plain_Text) {
        // Yayılan şifre-bloğu zincirleme kipi ya da açık metin şifre-bloğu zincirleme kipi kapalı metindeki ufak değişikliklerin
        // deşifreleme yaparken sonraki bloklara belirsiz bir şekilde yayılması için tasarlanmıştır.
        return Plain_Text;
    }
//aes-128-cbc:
// aes-128-cfb:
// aes-128-cfb1:
// aes-128-cfb8:
// aes-128-ecb:
// aes-128-ofb
// aes-192-cbc:
// aes-192-cfb:
// aes-192-cfb1:
// aes-192-cfb8:
// aes-192-ecb:
// aes-192-ofb:
// aes-256-cbc:
// aes-256-cfb:
// aes-256-cfb1:
// aes-256-cfb8:
// aes-256-ecb:

}
