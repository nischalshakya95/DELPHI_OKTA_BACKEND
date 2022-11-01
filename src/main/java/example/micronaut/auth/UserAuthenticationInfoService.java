package example.micronaut.auth;

import io.micronaut.security.authentication.Authentication;
import io.micronaut.security.utils.SecurityService;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

import java.util.Optional;

@Singleton
public class UserAuthenticationInfoService {

  @Inject private SecurityService securityService;

  public String getUserEmail() {
    Optional<Authentication> optionalAuthentication = securityService.getAuthentication();
    if (optionalAuthentication.isPresent()) {
      Authentication authentication = optionalAuthentication.get();
      return authentication.getName();
    }
    return null;
  }
}
