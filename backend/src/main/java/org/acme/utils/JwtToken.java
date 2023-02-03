package org.acme.utils;

import io.smallrye.jwt.build.Jwt;
import io.smallrye.jwt.build.JwtClaimsBuilder;
import org.acme.models.Role;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.enterprise.context.ApplicationScoped;
import java.io.InputStream;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

@ApplicationScoped
public class JwtToken {
    @ConfigProperty(name = "mp.jwt.verify.issuer") public String issuer;
    @ConfigProperty(name = "stay_fit.quarkusjwt.jwt.duration") public Long duration;
    public String generateToken(String username, Set<Role> roles, Long userId) throws Exception{
        String privateKeyLocation = "/private.pem";
        PrivateKey privateKey = readPrivateKey(privateKeyLocation);

        JwtClaimsBuilder claimsBuilder = Jwt.claims();
        Long currentTimeInSecs = currentTimeInSecs();

        Set<String> groups = new HashSet<>();
        for (Role role : roles) groups.add(role.getName());

        claimsBuilder.issuer(issuer);
        claimsBuilder.subject(username);
        claimsBuilder.issuedAt(currentTimeInSecs);
        claimsBuilder.expiresAt(currentTimeInSecs + duration);
        claimsBuilder.groups(groups);
        claimsBuilder.claim("USER_ID",userId);

        return  claimsBuilder.jws().signatureKeyId(privateKeyLocation).sign(privateKey);
    }


    public PrivateKey readPrivateKey(final String pemResName) throws Exception {
        try(InputStream contentIS = JwtToken.class.getResourceAsStream(pemResName)) {
            byte[] tmp = new byte[4096];
            int length = contentIS.read(tmp);
            return decodePrivateKey(new  String(tmp, 0, length,"UTF-8"));
        }
    }

    public PrivateKey decodePrivateKey(final String pemEncoded) throws Exception{
        byte[] encodeBytes = toEncodedBytes(pemEncoded);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(encodeBytes);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        return kf.generatePrivate(keySpec);
    }


    public  byte[] toEncodedBytes(final String pemEncoded){
        final String normalizePem = removeBeginEnd(pemEncoded);
        return Base64.getDecoder().decode(normalizePem);
    }

    public String removeBeginEnd(String pem){
        pem = pem.replaceAll("-----BEGIN (.*)-----", "");
        pem = pem.replaceAll("-----END (.*)----", "");
        pem = pem.replaceAll("\r\n", "");
        pem = pem.replaceAll("\n", "");
        return pem.trim();
    }

    public Long currentTimeInSecs() {
        long currentTimeMS = System.currentTimeMillis();
        return (currentTimeMS / 1000);
    }
}
