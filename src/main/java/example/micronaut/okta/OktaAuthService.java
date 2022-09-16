package example.micronaut.okta;

import com.okta.authn.sdk.AuthenticationException;
import com.okta.authn.sdk.AuthenticationStateHandler;
import com.okta.authn.sdk.client.AuthenticationClient;
import com.okta.authn.sdk.client.AuthenticationClients;
import com.okta.authn.sdk.resource.AuthenticationResponse;
import com.okta.authn.sdk.resource.User;
import io.micronaut.context.annotation.Property;
import io.micronaut.core.util.StringUtils;
import jakarta.inject.Singleton;

@Singleton
public class OktaAuthService {

  private final AuthenticationClient client;

  private AuthenticationStateHandler stateHandler;

  private AuthenticationResponse response;

  @Property(name = "${okta.issuer}")
  private final String issuerURL = "https://dev-74151855.okta.com";

  public OktaAuthService() {
    client = AuthenticationClients.builder().setOrgUrl(issuerURL).build();
  }

  public boolean login(String username, String password) {
    try {
      char[] passwordChar = password.toCharArray();
      response = client.authenticate(username, passwordChar, null, stateHandler);
      return !StringUtils.isEmpty(response.getSessionToken());
    } catch (AuthenticationException err) {
      System.out.println("Invalid Credentials " + err.getMessage());
      return false;
    }
  }

  public User getUser() {
    return response.getUser();
  }
}
