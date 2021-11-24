package careerdevs.API.GoRest.API.v2.controllers;

import careerdevs.API.GoRest.API.v2.models.GoRestAPIResponse;
import careerdevs.API.GoRest.API.v2.models.GoRestAPIUser;
import careerdevs.API.GoRest.API.v2.models.GoRestMeta;
import careerdevs.API.GoRest.API.v2.models.GoRestResponseMulti;
import org.apache.catalina.filters.HttpHeaderSecurityFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;

@RestController
@RequestMapping("/api/rest/user")
public class GoRestController {

    @Autowired
    private Environment env;

    @GetMapping("/pageone")
    public GoRestResponseMulti getUsers (RestTemplate restTemplate){
       String URL = "https://gorest.co.in/public/v1/users";
       return restTemplate.getForObject(URL, GoRestResponseMulti.class);
    }

    @GetMapping ("/allusers")
    public Object allusers (RestTemplate restTemplate){
        GoRestResponseMulti res = getUsers(restTemplate);

        int pageNum = res.getMeta().getPagination().getPages();
        ArrayList<GoRestAPIUser> allUserData = new ArrayList<>();

        //adds all data from res data object (info from one page) to allUserData arraylist
        Collections.addAll(allUserData, res.getData());

        for (int i = 2; i <= pageNum ; i++) {
            String URL = "https://gorest.co.in/public/v1/users?page=" + i;
            GoRestAPIUser[] tempUserData = restTemplate.getForObject(URL, GoRestResponseMulti.class).getData();
            Collections.addAll(allUserData, tempUserData);
        }

        return allUserData;
    }

    @GetMapping ("/someusers")
    public Object someUsers (RestTemplate restTemplate){

        ArrayList<GoRestAPIUser> allUserData = new ArrayList<>();

        for (int i = 1; i <= 3 ; i++) {
            String URL = "https://gorest.co.in/public/v1/users?page=" + i;
            GoRestAPIUser[] tempUserData = restTemplate.getForObject(URL, GoRestResponseMulti.class).getData();
            Collections.addAll(allUserData, tempUserData);
        }

        return allUserData;
    }

    //get specific user mapping. (Requires ?id= and then a users id)
    @GetMapping ("/getuser")
    public Object getUser(RestTemplate restTemplate, @RequestParam (name = "id") String id) {
        String URL = "https://gorest.co.in/public/v1/users/" + id;
        return restTemplate.getForObject(URL, GoRestAPIResponse.class).getData();
    }

    @GetMapping ("/page/{page}")
    public Object getPage(RestTemplate restTemplate, @PathVariable(name = "page") String page){
        String URL = "https://gorest.co.in/public/v1/users?page=" + page;

        try {

            return restTemplate.getForObject(URL, GoRestResponseMulti.class).getData();

        } catch (HttpClientErrorException.NotFound e){

            return "Page not found";

        } catch (Exception e){

            System.out.println(e.getMessage());
            return e.getMessage();
        }
    }

    //delete user mapping. (Requires ?id= and then a users id)
    @DeleteMapping("/delete")
    public String removeUser(RestTemplate restTemplate, @RequestParam(name = "id") String id) {
        //set endpoint
        String URL = "https://gorest.co.in/public/v1/users/" + id;

        try {
            HttpHeaders header = new HttpHeaders();
            //gets bearer token from the the application.properties
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

    @PutMapping("/update")
    public Object updateUser(RestTemplate restTemplate,
                             @RequestParam(name = "id") String id,
                             @RequestParam(name = "name", required = false) String name,
                             @RequestParam(name = "email", required = false) String email,
                             @RequestParam(name = "status", required = false) String status,
                             @RequestParam(name = "gender", required = false) String gender) {


        String URL = "https://gorest.co.in/public/v1/users/" + id;

        try {
            HttpHeaders header = new HttpHeaders();
            //gets bearer token from the the application.properties
            header.setBearerAuth(env.getProperty("bearer.token"));

            GoRestAPIUser newUser = new GoRestAPIUser(name, email, gender, status);

            HttpEntity request = new HttpEntity(header);
            return restTemplate.exchange(URL, HttpMethod.PUT, request ,GoRestAPIResponse.class);

        } catch (HttpClientErrorException.Unauthorized e) {
            return "No bearer token detected. You need authorization";

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return e.getMessage();
        }
    }






}
