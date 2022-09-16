package example.micronaut.auth;

import example.micronaut.okta.OktaAuthService;
import io.micronaut.http.HttpRequest;
import io.micronaut.security.authentication.AuthenticationProvider;
import io.micronaut.security.authentication.AuthenticationRequest;
import io.micronaut.security.authentication.AuthenticationResponse;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import org.reactivestreams.Publisher;

@Singleton
public class AuthenticationProviderUserPassword implements AuthenticationProvider {

  @Inject private OktaAuthService oktaAuthService;

  @Override
  public Publisher<AuthenticationResponse> authenticate(
      HttpRequest<?> httpRequest, AuthenticationRequest<?, ?> authenticationRequest) {
    String username = String.valueOf(authenticationRequest.getIdentity());
    String password = String.valueOf(authenticationRequest.getSecret());

    boolean isAuthenticated = oktaAuthService.login(username, password);
    System.out.println(isAuthenticated);
    System.out.println(oktaAuthService.getUser());
    return null;
  }
}
