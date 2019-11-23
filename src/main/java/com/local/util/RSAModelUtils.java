package com.local.util;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.math.BigInteger;
import java.net.URLEncoder;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.util.HashMap;

public class RSAModelUtils {
    // 自己模A
    public final static String moduleA = "90601109469093574428233633522531625424685960148022932700959007730634004725939249460042833655952669238496931360731329455805373451148578405758545267155192957095697640760879021573787303881221405387563600084167457612193933258188836366017084860911197401052429037345882264520193434706210382918033207986647667886063";
    // 自己私钥模指数A
    public final static String privateKeyA = "14396752277509505837856860391895331601578949127691392971112304599032951236949072186351008891055298494738957278951677143487757436566539443635953589760809607650775383838390716296390630234868201329256510458082160611128936643447643777520373165502001955997795727815069786552831146033247529856855726546622181534753";
    public final static String puclicKeyA="65537";// 公钥模指数C
    /**
     * 生成公钥和私钥
     * @throws NoSuchAlgorithmException
     *
     */
    public static HashMap<String, Object> getKeys() throws NoSuchAlgorithmException{
        HashMap<String, Object> map = new HashMap<String, Object>();
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA", new org.bouncycastle.jce.provider.BouncyCastleProvider());
        keyPairGen.initialize(1024);
        KeyPair keyPair = keyPairGen.generateKeyPair();
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        map.put("public", publicKey);
        map.put("private", privateKey);
        return map;
    }
    /**
     * 使用模和指数生成RSA公钥
     *
     *
     * @param modulus
     *            模
     * @param exponent
     *            指数
     * @return
     */
    public static RSAPublicKey getPublicKey(String modulus, String exponent) {
        try {
            BigInteger b1 = new BigInteger(modulus);
            BigInteger b2 = new BigInteger(exponent);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA", new org.bouncycastle.jce.provider.BouncyCastleProvider());
            RSAPublicKeySpec keySpec = new RSAPublicKeySpec(b1, b2);
            return (RSAPublicKey) keyFactory.generatePublic(keySpec);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 使用模和指数生成RSA私钥

     * /None/NoPadding】
     *
     * @param modulus
     *            模
     * @param exponent
     *            指数
     * @return
     */
    public static RSAPrivateKey getPrivateKey(String modulus, String exponent) {
        try {
            BigInteger b1 = new BigInteger(modulus);
            BigInteger b2 = new BigInteger(exponent);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA", new org.bouncycastle.jce.provider.BouncyCastleProvider());
            RSAPrivateKeySpec keySpec = new RSAPrivateKeySpec(b1, b2);
            return (RSAPrivateKey) keyFactory.generatePrivate(keySpec);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    /**
     * 公钥加密
     * @param data
     * @param modulus
     * @param exponent
     * @return
     * @throws Exception
     */
    public static String encryptByPublicKey(String data, String modulus, String exponent) throws Exception {
        RSAPublicKey publicKey=getPublicKey(modulus, exponent);
        return encryptByPublicKey(data, publicKey);
    }

    /**
     * 公钥加密
     *
     * @param data
     * @param publicKey
     * @return
     * @throws Exception
     */
    public static String encryptByPublicKey(String data, RSAPublicKey publicKey)
            throws Exception {
        String mi = "";
        KeyGenerator kgen = KeyGenerator.getInstance("AES");
        kgen.init(128, new SecureRandom(data.getBytes()));
        SecretKey secretKey = kgen.generateKey();
        byte[] enCodeFormat = secretKey.getEncoded();
        SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
        Cipher cipher = Cipher.getInstance("RSA", new org.bouncycastle.jce.provider.BouncyCastleProvider());
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        // 模长
        int key_len = publicKey.getModulus().bitLength() / 8;
        // 加密数据长度 <= 模长-11
//        String[] datas = splitString(data, key_len - 11);
//        //如果明文长度大于模长-11则要分组加密
//        for (String s : datas) {
//        	mi += bcd2Str(cipher.doFinal(s.getBytes()));
//        }
        byte[] dataByte = data.getBytes("utf-8");
//        System.out.println("dataByte"+dataByte.length);
        byte[][] arrays=splitArray(dataByte, key_len-11);
        for (byte[] bs : arrays) {
//            System.out.println("bs"+bs.length);
            mi += bcd2Str(cipher.doFinal(bs));
        }
        return mi;
    }

    /**
     * 私钥解密
     *
     * @param data
     * @param modulus
     * @param exponent
     * @return
     * @throws Exception
     */
    public static String decryptByPrivateKey(String data,String modulus, String exponent)   throws Exception {
        RSAPrivateKey privateKey=getPrivateKey(modulus, exponent);
        return  decryptByPrivateKey(data, privateKey,false);
    }

    public static String decryptByPrivateKey(String data, RSAPrivateKey privateKey) throws Exception{
        return  decryptByPrivateKey(data, privateKey,true);
    }

    /**
     * 私钥解密
     *
     * @param data
     * @param privateKey
     * @return
     * @throws Exception
     */
    private static String decryptByPrivateKey(String data, RSAPrivateKey privateKey, boolean flag)
            throws Exception {
        KeyGenerator kgen = KeyGenerator.getInstance("AES");
        kgen.init(128, new SecureRandom(data.getBytes()));
        SecretKey secretKey = kgen.generateKey();
        byte[] enCodeFormat = secretKey.getEncoded();
        SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
        Cipher cipher = Cipher.getInstance("RSA", new org.bouncycastle.jce.provider.BouncyCastleProvider());
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        //模长
        int key_len = privateKey.getModulus().bitLength() / 8;
        byte[] bytes = data.getBytes();
        byte[] bcd = ASCII_To_BCD(bytes, bytes.length);
        //System.err.println(bcd.length);
        //如果密文长度大于模长则要分组解密
        String ming = "";
        byte[][] arrays = splitArray(bcd, key_len);
//        for (int i = arrays.length-1; i >=0; i--) {
//        	byte[] arr=arrays[i];
//        	ming = new String(cipher.doFinal(arr));
////        	ming=new String(cipher.doFinal(arr))+ming;
//		}
        for(byte[] arr : arrays){
            ming += new String(cipher.doFinal(arr), "UTF-8");
        }
        if(flag==true){
            ming=new StringBuilder(ming).reverse().toString();
        }
        return ming;
    }

    /**
     * 产生签名
     *
     * @param data
     * @param modulus
     * @param exponent
     * @return
     * @throws Exception
     */
    public static String sign(String data, String modulus, String exponent) throws Exception {
        RSAPrivateKey privateKey=getPrivateKey(modulus, exponent);
        return sign(data, privateKey);
    }
    /**
     * 产生签名
     *
     * @param data
     * @param privateKey
     * @return
     * @throws Exception
     */
    public static String sign(String data, RSAPrivateKey privateKey) throws Exception {
        // 用私钥对信息生成数字签名
        Signature signature = Signature.getInstance("MD5withRSA");
        signature.initSign(privateKey);
        byte[] bytes = data.getBytes();
        signature.update(bytes);
        return encryptBASE64(signature.sign());
    }
    /**验证签名
     *
     * @param data
     * @param modulus
     * @param exponent
     * @param sign
     * @return
     * @throws Exception
     */
    public static boolean verify(String data, String modulus, String exponent, String sign) throws Exception {
        RSAPublicKey privateKey=getPublicKey(modulus, exponent);
        return verify(data, privateKey,sign);
    }
    /**
     * 验证签名
     *
     * @param data
     * @param publicKey
     * @param sign
     * @return
     * @throws Exception
     */
    public static boolean verify(String data, RSAPublicKey publicKey, String sign) throws Exception {
        Signature signature = Signature.getInstance("MD5withRSA");
        signature.initVerify(publicKey);
        byte[] bytes = data.getBytes();
        signature.update(bytes);
        // 验证签名是否有效
        return signature.verify(decryptBASE64(sign));
    }

    /**
     * BASE64解密
     * @param key
     * @return
     * @throws Exception
     */
    public static byte[] decryptBASE64(String key) throws Exception {
        return (new BASE64Decoder()).decodeBuffer(key);
    }

    /**
     * BASE64加密
     *
     * @param key
     * @return
     * @throws Exception
     */
    public static String encryptBASE64(byte[] key) throws Exception {
        return (new BASE64Encoder()).encodeBuffer(key);
    }
    /**
     * ASCII码转BCD码
     *
     */
    public static byte[] ASCII_To_BCD(byte[] ascii, int asc_len) {
        byte[] bcd = new byte[asc_len / 2];
        int j = 0;
        for (int i = 0; i < (asc_len + 1) / 2; i++) {
            bcd[i] = asc_to_bcd(ascii[j++]);
            bcd[i] = (byte) (((j >= asc_len) ? 0x00 : asc_to_bcd(ascii[j++])) + (bcd[i] << 4));
        }
        return bcd;
    }
    public static byte asc_to_bcd(byte asc) {
        byte bcd;

        if ((asc >= '0') && (asc <= '9'))
            bcd = (byte) (asc - '0');
        else if ((asc >= 'A') && (asc <= 'F'))
            bcd = (byte) (asc - 'A' + 10);
        else if ((asc >= 'a') && (asc <= 'f'))
            bcd = (byte) (asc - 'a' + 10);
        else
            bcd = (byte) (asc - 48);
        return bcd;
    }
    /**
     * BCD转字符串
     */
    public static String bcd2Str(byte[] bytes) {
        char temp[] = new char[bytes.length * 2], val;

        for (int i = 0; i < bytes.length; i++) {
            val = (char) (((bytes[i] & 0xf0) >> 4) & 0x0f);
            temp[i * 2] = (char) (val > 9 ? val + 'A' - 10 : val + '0');

            val = (char) (bytes[i] & 0x0f);
            temp[i * 2 + 1] = (char) (val > 9 ? val + 'A' - 10 : val + '0');
        }
        return new String(temp);
    }
    /**
     * 拆分字符串
     */
    public static String[] splitString(String string, int len) {
        int x = string.length() / len;
        int y = string.length() % len;
        int z = 0;
        if (y != 0) {
            z = 1;
        }
        String[] strings = new String[x + z];
        String str = "";
        for (int i=0; i<x+z; i++) {
            if (i==x+z-1 && y!=0) {
                str = string.substring(i*len, i*len+y);
            }else{
                str = string.substring(i*len, i*len+len);
            }
            strings[i] = str;
        }
        return strings;
    }
    /**
     *拆分数组
     */
    public static byte[][] splitArray(byte[] data,int len){
        int x = data.length / len;
        int y = data.length % len;
        int z = 0;
        if(y!=0){
            z = 1;
        }
        byte[][] arrays = new byte[x+z][];
        byte[] arr;
        for(int i=0; i<x+z; i++){
            if(i==x+z-1 && y!=0){
                arr = new byte[y];
                System.arraycopy(data, i*len, arr, 0, y);
            }else{
                arr = new byte[len];
                System.arraycopy(data, i*len, arr, 0, len);
            }
            arrays[i] = arr;
        }
        return arrays;
    }
    public static void main(String[] args) throws Exception{
        HashMap<String, Object> map = getKeys();
        //生成公钥和私钥
        RSAPublicKey publicKey = (RSAPublicKey) map.get("public");
        RSAPrivateKey privateKey = (RSAPrivateKey) map.get("private");
        //模
        String modulus = publicKey.getModulus().toString();
//        String modulus=md;
        System.out.println(modulus.length()+"pubkey modulus="+modulus);
        //公钥指数
        String public_exponent = publicKey.getPublicExponent().toString();
        System.out.println(public_exponent.length()+"pubkey exponent="+public_exponent);
        //私钥指数
        String private_exponent = privateKey.getPrivateExponent().toString();
        System.out.println(private_exponent.length()+"private exponent="+private_exponent);
        //明文
        String ming = URLEncoder.encode("我爱你中国");
        System.out.println(ming);
        //使用模和指数生成公钥和私钥
        RSAPublicKey pubKey = RSAModelUtils.getPublicKey(modulus, public_exponent);
        RSAPrivateKey priKey = RSAModelUtils.getPrivateKey(modulus, private_exponent);
        //加密后的密文
        String mi = RSAModelUtils.encryptByPublicKey(ming, pubKey);
        System.out.println("mi="+mi);
        //解密后的明文
        String ming2 = RSAModelUtils.decryptByPrivateKey(mi, priKey);
        System.out.println("ming2="+ming2);
        //产生签名
        String src= mi;
        System.out.println(src.length());
        String sign = RSAModelUtils.sign(ming, privateKey);
        System.out.println("sign="+sign+"=======");
        //验证签名
        boolean flag = RSAModelUtils.verify(ming2, publicKey, sign);
        System.out.println("flag="+flag);

        String miwen1=RSAModelUtils.encryptByPublicKey("ceshi00000001", "140665109966300705579111750532230577456681389157803165823432984180390590697243034721024889331048034000772923836647832702428464307443874625569222531508090025003869827080524700268549466543779182827158334180440832612895833191624374557418142699053826101151718412878662431365877367657300295791073384275030072530209",
                "65537");
        System.out.println(miwen1);
        String jiemi1=RSAModelUtils.decryptByPrivateKey(miwen1,  "140665109966300705579111750532230577456681389157803165823432984180390590697243034721024889331048034000772923836647832702428464307443874625569222531508090025003869827080524700268549466543779182827158334180440832612895833191624374557418142699053826101151718412878662431365877367657300295791073384275030072530209",
                "8194750901801060376597168981370162575790920301577620078945742612581156831745028099621176243434111933944962741784357313546117105235526699733330662454764295352892276540116900314192594760577842352404566114356251580003844586124027744351961239850820527735822366980612730307656057091585251447271429754725849779529");
        System.out.println(jiemi1);
    }
}
