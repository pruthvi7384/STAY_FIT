package org.acme.utils;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.enterprise.context.ApplicationScoped;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;

@ApplicationScoped
public class PBKDF2Encoder {
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    @ConfigProperty(name = "quarkus.password.secret")  private String secret;
    @ConfigProperty(name = "quarkus.password.iteration")  private Integer iteration;
    @ConfigProperty(name = "quarkus.password.keylength")  private Integer keylength;

    /**
     * More info (<a href="https://www.owasp.org/index.php/Hashing_Java">...</a>) 404 :(
     * @param cs password
     * @return encoded password
     */
    public String encode(CharSequence cs) {
        try {
            byte[] result = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512")
                    .generateSecret(new PBEKeySpec(cs.toString().toCharArray(), secret.getBytes(), iteration, keylength))
                    .getEncoded();
            return Base64.getEncoder().encodeToString(result);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException ex) {
            log.error("Error - ", ex);
        }
        return null;
    }
}
