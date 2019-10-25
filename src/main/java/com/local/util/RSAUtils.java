package com.local.util;

import org.apache.commons.codec.binary.Base64;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import java.math.BigInteger;
import java.net.URLEncoder;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

public class RSAUtils {
    public final static String PUBLICKEY="MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCQsYyflAp9vHV15yzElwqCxqxA6XsdlhUuQdrn7082NDrpcU1JaWlmW3WYBsjmzbQBx8zLi3bmCNTtf8gMXDvVZPVCOXkc20Jl9MnKjMygGzbbmjf4maDDmRGBbqPlOWP3y2omncpWK+9T3mSZJzrF+wreSHaxn7g8QsATpk2lQwIDAQAB";
    public final static String PRIVATEKEY="MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAJCxjJ+UCn28dXXnLMSXCoLGrEDpex2WFS5B2ufvTzY0OulxTUlpaWZbdZgGyObNtAHHzMuLduYI1O1/yAxcO9Vk9UI5eRzbQmX0ycqMzKAbNtuaN/iZoMOZEYFuo+U5Y/fLaiadylYr71PeZJknOsX7Ct5IdrGfuDxCwBOmTaVDAgMBAAECgYBM6nUMZ0/kYwg1srvzjS8DMcVjhDn0ElIUEuHhJS+AaudeLOKewbBLvxaVyBWHy5WC1Mki8nIz+kHmNmULXQRYreMzm4MXm2/OTX+vaS+Zj7Lav0BCT/EzyCdJWergUD3sFxqSN07RVyN5Iy1pYK3TnCGxXMl5TQRH9OohYwUUYQJBAOUgNGXPr5BKmc/6h9iYHIfsV6toMWn8lZB51C1seJm30jcdrvd2agX4O4g24KEQWSiHUOZdd0tCg9aEcbbD86kCQQChqiiKO4R+sIkaYAew0UbteySjrNy3EvM490Cb0cBmmpT40YzQTvNDA7AiC7VzqyaDnOwdKlWD2EEBEJG/DOULAkBVXlRngqQlHMaGSRAIUVSACDz57k5K8QNA20OE0R92FplKU9L2/LWwF7cGn3u+RvsRMKmhyz1BWz0H1j0QHZ5RAkAlzP/Fsrz8UpL8U8/4jDaleNHMY7MIK6ore8Tjqlvuod6Lf5QJcSx4UvhwPYSkEzTiOWMMPsXYGky4zwMFRSKZAkEAvQxBkSuUf5o7PhEXzay8KP5LPDFYW+mJQrAmg7/rtSt0wGScPo1bjMnMe8yEZ7LgBHzzVZ9aND9gXzmpPJ+P2w==";

    private static Map<Integer, String> keyMap = new HashMap<Integer, String>();  //用于封装随机产生的公钥与私钥
    public static void main(String[] args) throws Exception {
        String privateKey="MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAJCxjJ+UCn28dXXnLMSXCoLGrEDpex2WFS5B2ufvTzY0OulxTUlpaWZbdZgGyObNtAHHzMuLduYI1O1/yAxcO9Vk9UI5eRzbQmX0ycqMzKAbNtuaN/iZoMOZEYFuo+U5Y/fLaiadylYr71PeZJknOsX7Ct5IdrGfuDxCwBOmTaVDAgMBAAECgYBM6nUMZ0/kYwg1srvzjS8DMcVjhDn0ElIUEuHhJS+AaudeLOKewbBLvxaVyBWHy5WC1Mki8nIz+kHmNmULXQRYreMzm4MXm2/OTX+vaS+Zj7Lav0BCT/EzyCdJWergUD3sFxqSN07RVyN5Iy1pYK3TnCGxXMl5TQRH9OohYwUUYQJBAOUgNGXPr5BKmc/6h9iYHIfsV6toMWn8lZB51C1seJm30jcdrvd2agX4O4g24KEQWSiHUOZdd0tCg9aEcbbD86kCQQChqiiKO4R+sIkaYAew0UbteySjrNy3EvM490Cb0cBmmpT40YzQTvNDA7AiC7VzqyaDnOwdKlWD2EEBEJG/DOULAkBVXlRngqQlHMaGSRAIUVSACDz57k5K8QNA20OE0R92FplKU9L2/LWwF7cGn3u+RvsRMKmhyz1BWz0H1j0QHZ5RAkAlzP/Fsrz8UpL8U8/4jDaleNHMY7MIK6ore8Tjqlvuod6Lf5QJcSx4UvhwPYSkEzTiOWMMPsXYGky4zwMFRSKZAkEAvQxBkSuUf5o7PhEXzay8KP5LPDFYW+mJQrAmg7/rtSt0wGScPo1bjMnMe8yEZ7LgBHzzVZ9aND9gXzmpPJ+P2w==";
        String publicKey="MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCQsYyflAp9vHV15yzElwqCxqxA6XsdlhUuQdrn7082NDrpcU1JaWlmW3WYBsjmzbQBx8zLi3bmCNTtf8gMXDvVZPVCOXkc20Jl9MnKjMygGzbbmjf4maDDmRGBbqPlOWP3y2omncpWK+9T3mSZJzrF+wreSHaxn7g8QsATpk2lQwIDAQAB";
        //生成公钥和私钥
        genKeyPair();
        //加密字符串
        String message = "df723820";
        System.out.println("随机生成的公钥为:" + keyMap.get(0));
        System.out.println("随机生成的私钥为:" + keyMap.get(1));
//        String messageEn = encrypt(message,keyMap.get(0));
        String messageEn = encrypt(message,publicKey);
        System.out.println(message + "\t加密后的字符串为:" + messageEn);
//        String messageDe = decrypt(messageEn,keyMap.get(1));
        String messageDe = decrypt(messageEn,privateKey);
        System.out.println("还原后的字符串为:" + messageDe);
    }

    /**
     * 随机生成密钥对
     * @throws NoSuchAlgorithmException
     */
    public static void genKeyPair() throws NoSuchAlgorithmException {
        // KeyPairGenerator类用于生成公钥和私钥对，基于RSA算法生成对象
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
        // 初始化密钥对生成器，密钥大小为96-1024位
        keyPairGen.initialize(1024,new SecureRandom());
        // 生成一个密钥对，保存在keyPair中
        KeyPair keyPair = keyPairGen.generateKeyPair();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();   // 得到私钥
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();  // 得到公钥
        String publicKeyString = new String(Base64.encodeBase64(publicKey.getEncoded()));
        // 得到私钥字符串
        String privateKeyString = new String(Base64.encodeBase64((privateKey.getEncoded())));
        // 将公钥和私钥保存到Map
        keyMap.put(0,publicKeyString);  //0表示公钥
        keyMap.put(1,privateKeyString);  //1表示私钥
    }
    /**
     * RSA公钥加密
     *
     * @param str
     *            加密字符串
     * @param publicKey
     *            公钥
     * @return 密文
     * @throws Exception
     *             加密过程中的异常信息
     */
    public static String encrypt( String str, String publicKey ) throws Exception{
        //base64编码的公钥
        byte[] decoded = Base64.decodeBase64(publicKey);
        RSAPublicKey pubKey = (RSAPublicKey) KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(decoded));
        //RSA加密
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, pubKey);
        String outStr = Base64.encodeBase64String(cipher.doFinal(str.getBytes("UTF-8")));
        return outStr;
    }

    /**
     * RSA私钥解密
     *
     * @param str
     *            加密字符串
     * @param privateKey
     *            私钥
     * @return 铭文
     * @throws Exception
     *             解密过程中的异常信息
     */
    public static String decrypt(String str, String privateKey) throws Exception{
        //64位解码加密后的字符串
        byte[] inputByte = Base64.decodeBase64(str.getBytes("UTF-8"));
        //base64编码的私钥
        byte[] decoded = Base64.decodeBase64(privateKey);
        RSAPrivateKey priKey = (RSAPrivateKey) KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(decoded));
        //RSA解密
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, priKey);
        String outStr = new String(cipher.doFinal(inputByte));
        return outStr;
    }
}
