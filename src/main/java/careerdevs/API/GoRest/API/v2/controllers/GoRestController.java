package careerdevs.API.GoRest.API.v2.controllers;

import careerdevs.API.GoRest.API.v2.models.GoRestAPIUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class GoRestController {

    @Autowired
    private Environment env;

    @GetMapping("/user")
    public GoRestAPIUser userInfo (RestTemplate restTemplate){

        String access = "https://gorest.co.in/public/v1/users?access-token=" + env.getProperty("goRest.key");
        String URL = "https://gorest.co.in/public/v1/users";



        GoRestAPIUser rest = restTemplate.getForObject(URL, GoRestAPIUser.class);
        return rest;
    }


}
