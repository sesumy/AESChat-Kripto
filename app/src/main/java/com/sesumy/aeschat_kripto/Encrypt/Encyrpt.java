package com.sesumy.aeschat_kripto.Encrypt;

import java.nio.charset.Charset;
import java.util.ArrayList;

/**
 * Created by sesumy on 9.12.2016.
 */

public class Encyrpt {
    final static Charset UTF_8 = Charset.forName("UTF-8");


    public  ArrayList<Integer> toBinary(String text){
        String oneChar="";
        String k=text;
        String full="";
        ArrayList<Integer> plainText_binary=new ArrayList<Integer>();
        for (char c : k.toCharArray()){
            oneChar=String.format("%08d", Integer.parseInt(Integer.toBinaryString((int) c)));
            full+=oneChar;
        }
        for(int i=0;i<full.length();i++){
            String s = ""+full.charAt(i);
            int binary_one=Integer.parseInt(s);
            plainText_binary.add(binary_one);

        }
        return plainText_binary;
    }
    public ArrayList<Integer> key_Binary(String key){
        String intString="";
        String key_toBinary="";
        outerloop:
        //8 BİTLİK FORMA DÖNÜŞTÜRDÜK..
        while(key_toBinary.length()<129){
            for (char c : key.toCharArray()) {
                intString=String.format("%08d", Integer.parseInt(Integer.toBinaryString((int) c)));
                //Aldığımız karakteri binary'e dönüştürüp stringte birleştiriyoruz...
                key_toBinary+=intString;
                if(key_toBinary.length()==128){
                    break outerloop;
                }
            }
        }
        //DİZİYE ÇEVİRDİK XORLAMA YAPARKEN KOLAYLAŞTIRACAK İŞİMİZİ
        ArrayList<Integer> binary_array=new ArrayList<Integer>();
        for(int i=0;i<key_toBinary.length();i++){
            String s = ""+key_toBinary.charAt(i);
            int binary_one=Integer.parseInt(s);
            binary_array.add(binary_one);
        }

        return binary_array;
    }
    public String ECB_Hesaplama(String text){
        Encyrpt kb=new Encyrpt ();
        String KEY = "Merhaba";
        ArrayList<Integer> key_list=kb.key_Binary(KEY);
        ArrayList<Integer> plainText_Binary_Int =kb.toBinary(text);
        ArrayList<Integer> cipher_Binary_text=new ArrayList<Integer>();

        int b=0;
        for(int a=0 ;a<plainText_Binary_Int.size();a++){
            if(b<plainText_Binary_Int.size()){
                int s=plainText_Binary_Int.get(a);
                int n=key_list.get(b);
                int l=n^s;
                if(b==127){
                    b=0;
                }
                b++;
                cipher_Binary_text.add(l);
            }
        }
        String cipher ="";
        for(int ct = 0 ;ct<cipher_Binary_text.size(); ct++){
            cipher += Integer.toString((cipher_Binary_text.get(ct)));
        }
        return cipher;
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
}
