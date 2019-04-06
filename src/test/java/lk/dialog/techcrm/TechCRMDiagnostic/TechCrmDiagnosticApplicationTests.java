package lk.dialog.techcrm.TechCRMDiagnostic;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TechCrmDiagnosticApplicationTests {

	@Test
    public void generateTokBoxToken() {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary("MTAwMzg1NDIxN2ZkMzZiYmNjMmJlYzE5N2U4ZmY5NDdkZmYzYzk1OQ==");
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());
        Map<String, Object> map = new HashMap<>();
        map.put("alg", "HS256");
        map.put("typ", "JWT");
        String jwt = Jwts.builder()
                .setHeader(map)
                .claim("iss", "46248002")
                .claim("ist", "project")
                .claim("iat", currentTimeSeconds())
                .claim("exp", expireTimeSeconds())
                .claim("jti", "abcdefghi")
                .signWith(signatureAlgorithm, signingKey)
                .compact();
        System.out.println(jwt);
    }

    public  long currentTimeSeconds() {
        long nowMillis = System.currentTimeMillis();
        String a = String.valueOf(nowMillis);
        return Long.valueOf(a.substring(0, a.length() - 3));
    }
    public  long expireTimeSeconds() {
        long expireTime = currentTimeSeconds() + 20000;
        return Long.valueOf(String.valueOf(expireTime));
    }
}

