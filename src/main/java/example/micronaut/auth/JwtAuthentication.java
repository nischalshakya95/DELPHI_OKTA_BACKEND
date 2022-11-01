package example.micronaut.auth;

import example.micronaut.jwt.OktaTokenValidator;
import io.micronaut.core.util.StringUtils;
import io.micronaut.http.HttpRequest;
import io.micronaut.security.authentication.Authentication;
import io.micronaut.security.filters.AuthenticationFetcher;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

@Singleton
public class JwtAuthentication implements AuthenticationFetcher {

  @Inject private OktaTokenValidator oktaTokenValidator;

  @Override
  public Publisher<Authentication> fetchAuthentication(HttpRequest<?> request) {
    return Mono.create(
        emitter -> {
          String bearerToken = request.getHeaders().get("Authorization");
          if (StringUtils.isEmpty(bearerToken)) {
            emitter.success();
            return;
          }
          String token = bearerToken.split(" ")[1];
          if (!oktaTokenValidator.validateOpenIdToken(token)) {
            emitter.success();
            return;
          }
          Map<String, Object> oktaTokenClaimMap = oktaTokenValidator.getClaims(token);
          // Fetch the user from LDAP to get the user roles. As the role based access is not
          // available in the Okta
          emitter.success(
              Authentication.build(
                  String.valueOf(oktaTokenClaimMap.get("sub")),
                  List.of("ROLE_ADMIN"),
                  oktaTokenClaimMap));
        });
  }
}
