package example.micronaut.controller;

import example.micronaut.auth.UserAuthenticationInfoService;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;
import jakarta.inject.Inject;

import javax.annotation.security.RolesAllowed;
import java.util.Map;

@Controller
@Secured(SecurityRule.IS_AUTHENTICATED)
public class OktaAuthController {

  @Inject private UserAuthenticationInfoService userAuthenticationInfoService;

  @Get("/user-info")
  @RolesAllowed({"ROLE_ADMIN"})
  public HttpResponse<Map<String, String>> getUserInfo() {
    return HttpResponse.ok().body(Map.of("email", userAuthenticationInfoService.getUserEmail()));
  }

}
