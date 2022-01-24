package org.example.api.gateway.template.utils;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class EncryptionUtilsTest {
    public static final String SECRET = "secret";
    public static final String PAYLOAD = "{\n"
                                         + "  \"input\": \"example\"\n"
                                         + '}';

    public static final String CVS_TOKEN =
            "b9zj4XkNwoZGbQ81ZIa060ahBr/QuMTT1pEvvSfrzQVjeiRrxvh8WgXcOTDAisZAuG6JSRAnV7XGo8pj4Sls6w==";

    @Test
    public void encryptHmacSha512Base64() {
        final Optional<String> hex = EncryptionUtils.encryptHmacSha512Base64(SECRET, PAYLOAD);
        Assertions.assertEquals(CVS_TOKEN, hex.orElseThrow());
    }

    @Test
    public void encryptHmacSha256() {
        final Optional<String> hex = EncryptionUtils.encryptHmacSha256(SECRET,
                                                                       "2021122284285271" + "1640573892");
        Assertions.assertEquals("e8596e4b5d9d001b672a3c8a1c6c8be67c62629ece9f0c0d735dc9c34f334690",
                                hex.orElseThrow());
    }

}
