package careerdevs.API.GoRest.API.v2.controllers;

import careerdevs.API.GoRest.API.v2.models.GoRestAPIResponse;
import careerdevs.API.GoRest.API.v2.models.GoRestAPIUser;
import org.apache.catalina.filters.HttpHeaderSecurityFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/api/rest/user")
public class GoRestController {

    @Autowired
    private Environment env;

    @GetMapping("/alluser-p1")
    public GoRestAPIResponse getUsers (RestTemplate restTemplate){
        //String access = "https://gorest.co.in/public/v1/users?access-token=" + env.getProperty("goRest.key");
        //String URL = "https://gorest.co.in/public/v1/users";
       return restTemplate.getForObject("https://gorest.co.in/public/v1/users", GoRestAPIResponse.class);
    }

    @GetMapping ("/getuser")
    public Object getUser(RestTemplate restTemplate, @RequestParam (name = "id", defaultValue = "1") String id) {
        String URL = "https://gorest.co.in/public/v1/users" + id;
        return restTemplate.getForObject(URL, GoRestAPIResponse.class).getData();
    }

    @DeleteMapping("/delete")
    public String removeUser(RestTemplate restTemplate, @RequestParam(name = "id") String id) {

        String URL = "https://gorest.co.in/public/v1/users/" + id;
        try {
            HttpHeaders header = new HttpHeaders();
            header.setBearerAuth(env.getProperty("bearer.token"));

            HttpEntity request = new HttpEntity(header);
            restTemplate.exchange(URL, HttpMethod.DELETE, request ,GoRestAPIUser.class);
            return "You have deleted the user: " + id;
        } catch (HttpClientErrorException.Unauthorized e) {
            return "No bearer token detected. You need authorization";
        } catch (HttpClientErrorException.NotFound e) {
            return "ID did not match a user in the database.";
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return e.getMessage();
        }
    }

    @PostMapping("/create")
    public Object createUser(RestTemplate restTemplate,
                             @RequestParam(name = "name") String name,
                             @RequestParam(name = "email") String email,
                             @RequestParam(name = "status") String status,
                             @RequestParam(name = "gender") String gender){

        String URL = "https://gorest.co.in/public/v1/users/";

        try {
            HttpHeaders header = new HttpHeaders();
            header.setBearerAuth(env.getProperty("bearer.token"));

            GoRestAPIUser newUser = new GoRestAPIUser(name, email, gender, status);

            HttpEntity request = new HttpEntity(newUser,header);
            return restTemplate.exchange(URL, HttpMethod.POST, request ,GoRestAPIResponse.class);

        } catch (HttpClientErrorException.Unauthorized e) {
            return "No bearer token detected. You need authorization";

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return e.getMessage();
        }
    }





}
