package example.micronaut.controller;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;

import java.util.Map;

@Controller
@Secured(SecurityRule.IS_ANONYMOUS)
public class AnonymousController {

  @Get("/anonymous")
  public HttpResponse<Map<String, String>> getAnonymous() {
    return HttpResponse.ok().body(Map.of("message", "This end point is not protected."));
  }
}
