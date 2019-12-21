package express;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import java.io.IOException;
import java.nio.charset.Charset;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

public class RSAUtils {
    public static final Charset UTF8 = Charset.forName("UTF-8");
    PublicKey pbkey;
    PrivateKey prkey;
    public void generateKey() {
        try {
            SecureRandom secureRandom = new SecureRandom();
            KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
            kpg.initialize(1024,secureRandom);
            KeyPair kp = kpg.genKeyPair();
            pbkey = kp.getPublic();
            prkey = kp.getPrivate();
        } catch (Exception e) {
        }
    }

    public PrivateKey getPrkey() {
        return prkey;
    }

    public PublicKey getPbkey() {
        return pbkey;
    }

    public void setPrkey(PrivateKey prkey) {
        this.prkey = prkey;
    }


    public static String getKeyString(Key key) {
        byte[] keyBytes = key.getEncoded();
        String s = (new BASE64Encoder()).encode(keyBytes);
        return s;
    }
    public static byte[] encrypt(PublicKey publicKey, String message) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        return cipher.doFinal(message.getBytes(UTF8));
    }
    public static byte[] decrypt(PrivateKey privateKey, byte [] encrypted) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);

        return cipher.doFinal(encrypted);
    }
    public static String base64Encode(byte[] data) {
        return new BASE64Encoder().encode(data);
    }
    public static byte[] base64Decode(String data) throws IOException {
        return new BASE64Decoder().decodeBuffer(data);
    }
    public static PublicKey StringToPublicKey(String key) throws Exception {
        byte[] keyBytes;
        keyBytes = (new BASE64Decoder()).decodeBuffer(key);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PublicKey publicKey = keyFactory.generatePublic(keySpec);
        return publicKey;
    }
    public static PrivateKey StringToPrivateKey(String key) throws Exception {
        byte[] keyBytes;
        keyBytes = (new BASE64Decoder()).decodeBuffer(key);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
        return privateKey;
    }
    public static String PublicKeyToString(PublicKey publicKey){
        return getKeyString(publicKey);
    }
    public static String PrivateKeyToString(PrivateKey privateKey){
        return getKeyString(privateKey);
    }
    public static String EncryptToString(String content,String PublicKey) throws Exception {
        return  base64Encode(encrypt(StringToPublicKey(PublicKey),content));
    }
    public static String DecryptToString(String encrypted,String PrivateKey) throws Exception {
        return new String(decrypt(StringToPrivateKey(PrivateKey),base64Decode(encrypted)),"UTF-8");
    }

}
