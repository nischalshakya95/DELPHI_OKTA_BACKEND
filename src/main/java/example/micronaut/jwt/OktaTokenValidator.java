package example.micronaut.jwt;

import com.okta.jwt.AccessTokenVerifier;
import com.okta.jwt.Jwt;
import com.okta.jwt.JwtVerificationException;
import com.okta.jwt.JwtVerifiers;
import jakarta.inject.Singleton;

import java.time.Duration;
import java.util.Map;

@Singleton
public class OktaTokenValidator {

  private static final String issuer = "https://dev-74151855.okta.com/oauth2/default";

  private static final String audience = "api://default";

  private static final int CONNECTION_TIMEOUT = 2;

  private static final int MAX_ATTEMPTS = 2;

  private static final int MAX_ELAPSED = 10;

  private final AccessTokenVerifier jwtVerifier =
      JwtVerifiers.accessTokenVerifierBuilder()
          .setIssuer(issuer)
          .setAudience(audience)
          .setConnectionTimeout(Duration.ofSeconds(CONNECTION_TIMEOUT))
          .setRetryMaxAttempts(MAX_ATTEMPTS)
          .setRetryMaxElapsed(Duration.ofSeconds(MAX_ELAPSED))
          .build();
  ;

  public boolean validateOpenIdToken(String token) {
    try {
      jwtVerifier.decode(token);
      return true;
    } catch (JwtVerificationException e) {
      e.printStackTrace();
    }
    return false;
  }

  public Map<String, Object> getClaims(String token) {
    try {
      Jwt jwt = jwtVerifier.decode(token);
      return jwt.getClaims();
    } catch (JwtVerificationException e) {
      e.printStackTrace();
    }
    return null;
  }
}
