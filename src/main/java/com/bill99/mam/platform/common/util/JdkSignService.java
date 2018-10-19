package com.bill99.mam.platform.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Base64Utils;

import com.bill99.mam.platform.config.AcmsConfig;

import java.io.InputStream;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;

/**
 * @author liang.chen.coc
 */
public class JdkSignService {

    private final static Logger logger = LoggerFactory.getLogger(JdkSignService.class);
    private String algorithm = "SHA1withRSA";
    /**
     * 快钱私钥
     */
    private PrivateKey privateKey;
    /**
     * 商户公钥
     */
    private PublicKey publicKey;

    public boolean verifySignature(byte[] body, String signature) {
        try {
            Signature signaturer = Signature.getInstance(algorithm);
            signaturer.initVerify(publicKey);
            signaturer.update(body);
            return signaturer.verify(Base64Utils.decode(signature.getBytes()));
        } catch (Exception e) {
            logger.error("Verify signature failed!", e);
            return false;
        }
    }

    public String sign(byte[] body) {
        try {
            Signature signature = Signature.getInstance(algorithm);
            signature.initSign(privateKey);
            signature.update(body);
            return new String(Base64Utils.encode(signature.sign()), "utf-8");
        } catch (Exception e) {
            logger.error("Sign message failed!", e);
            return null;
        }
    }

    public void initKey(AcmsConfig acmsConfig) throws Exception {
        InputStream keyStoreInputStream = getClass().getResourceAsStream(acmsConfig.getKeyStorePath());
        if (keyStoreInputStream == null) {
            throw new Exception("Cannot find resource at classpath: " + acmsConfig.getKeyStorePath());
        }
        KeyStore ks = KeyStore.getInstance("PKCS12");
        ks.load(keyStoreInputStream, acmsConfig.getKeyStorePwd().toCharArray());//密钥库的密码
        this.privateKey = (PrivateKey) ks.getKey(acmsConfig.getKeyAlias(), acmsConfig.getKeyPwd().toCharArray());//密钥别名 /密钥访问密码

        // 读取快钱证书
        InputStream certInputStream = getClass().getResourceAsStream(acmsConfig.getCertPath());
        if (certInputStream == null) {
            throw new Exception("Cannot find resource at classpath: " + acmsConfig.getCertPath());
        }
        CertificateFactory certificatefactory = CertificateFactory.getInstance("X.509");
        Certificate cert = certificatefactory.generateCertificate(certInputStream);
        this.publicKey = cert.getPublicKey();
    }
}
