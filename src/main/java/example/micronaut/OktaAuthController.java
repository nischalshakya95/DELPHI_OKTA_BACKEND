package example.micronaut;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.QueryValue;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;

@Controller
@Secured(SecurityRule.IS_ANONYMOUS)
public class OktaAuthController {

  @Get("/api/v1/login-callback")
  @Secured(SecurityRule.IS_ANONYMOUS)
  public HttpResponse<String> getOktaAccessToken(
      @QueryValue String code, @QueryValue String state) {
    System.out.println("code " + code);
    System.out.println("state " + state);
    return HttpResponse.ok().body("Authentication Successful");
  }
}
