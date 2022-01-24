package org.example.api.gateway.template.utils;

import org.apache.commons.codec.binary.Hex;
import org.apache.tomcat.util.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;

public final class EncryptionUtils {
    private final static Logger log = LoggerFactory.getLogger(EncryptionUtils.class);

    private static final String HMAC_SHA512 = "HmacSHA512";
    private static final String HMAC_SHA256 = "HmacSHA256";

    public static Optional<String> encryptHmacSha512Base64(String privateKey, String hashData) {
        try {
            final byte[] rawHmac = hmac(privateKey, hashData, HMAC_SHA512);
            return Optional.of(Base64.encodeBase64String(rawHmac));
        } catch (Exception e) {
            log.error("encryptHmacSha512Base64 fail, privateKey={} hashData={}", privateKey, hashData, e);
            return Optional.empty();
        }
    }

    public static Optional<String> encryptHmacSha256(String privateKey, String hashData) {
        try {
            final byte[] rawHmac = hmac(privateKey, hashData, HMAC_SHA256);
            return Optional.of(Hex.encodeHexString(rawHmac));
        } catch (Exception e) {
            log.error("encryptHmacSha256 fail, privateKey={} hashData={}", privateKey, hashData, e);
            return Optional.empty();
        }
    }

    private static byte[] hmac(String privateKey, String hashData, String shaType)
            throws NoSuchAlgorithmException,
            InvalidKeyException {
        final byte[] hMACKey = privateKey.getBytes(StandardCharsets.UTF_8);
        final byte[] hMACData = hashData.getBytes(StandardCharsets.UTF_8);

        final SecretKeySpec secretKey = new SecretKeySpec(hMACKey, shaType);
        final Mac mac = Mac.getInstance(shaType);
        mac.init(secretKey);
        return mac.doFinal(hMACData);
    }

}
