package example.micronaut.auth;

import example.micronaut.okta.OktaAuthService;
import io.micronaut.http.HttpRequest;
import io.micronaut.security.authentication.AuthenticationProvider;
import io.micronaut.security.authentication.AuthenticationRequest;
import io.micronaut.security.authentication.AuthenticationResponse;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;

import java.util.HashMap;
import java.util.Map;

@Singleton
public class AuthenticationProviderUserPassword implements AuthenticationProvider {

  @Inject private OktaAuthService oktaAuthService;

  @Override
  public Publisher<AuthenticationResponse> authenticate(
      HttpRequest<?> httpRequest, AuthenticationRequest<?, ?> authenticationRequest) {
    return Flux.create(
        emitter -> {
          String username = String.valueOf(authenticationRequest.getIdentity());
          String password = String.valueOf(authenticationRequest.getSecret());
          boolean isLogin = oktaAuthService.login(username, password);
          System.out.println(oktaAuthService.getUser());
          Map<String, Object> userProfileMap = new HashMap<>();
          userProfileMap.put("openIdToken", oktaAuthService.getUser().getId());
          for (Map.Entry<String, String> m : oktaAuthService.getUser().getProfile().entrySet()) {
            userProfileMap.put(m.getKey(), m.getValue());
          }
          if (isLogin) {
            emitter.next(AuthenticationResponse.success(username, userProfileMap));
            emitter.complete();
          } else {
            emitter.error(AuthenticationResponse.exception());
          }
        },
        FluxSink.OverflowStrategy.ERROR);
  }
}
