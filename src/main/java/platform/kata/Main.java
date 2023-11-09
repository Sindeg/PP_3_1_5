package platform.kata;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

public class Main {
    private static final String URL_API = "http://94.198.50.185:7081/api/users";

    public static void main(String[] args) {
        RestTemplate restTemplate = new RestTemplate();

        // GET Запрос
        ResponseEntity<String> get = restTemplate.exchange(URL_API, HttpMethod.GET, null, String.class);

        String cookie = get.getHeaders().get("Set-Cookie").get(0);
        System.out.println("Полученные куки из GET запроса" + cookie);

        // POST Запрос
        User user = new User(3, "James", "Brown", 15);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Cookie", cookie);
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<User> entityPost = new HttpEntity<>(user, headers);

        ResponseEntity<String> post = restTemplate.exchange(URL_API, HttpMethod.POST, entityPost, String.class);
        String firstCode = post.getBody();
        System.out.println("Первая часть кода " + firstCode);

        // PATCH Запрос
        User updateUser = new User(3, "Thomas", "Shelby", 15);
        HttpEntity<User> entityPatch = new HttpEntity<>(updateUser, headers);

        ResponseEntity<String> patch = restTemplate.exchange(URL_API, HttpMethod.PUT, entityPatch, String.class);

        String secondCode = patch.getBody();
        System.out.println("Вторая часть кода " + secondCode);

        // Delete Запрос
        HttpEntity<User> entityDelete = new HttpEntity<>(null, headers);

        ResponseEntity<String> delete = restTemplate.exchange(URL_API + "/3", HttpMethod.DELETE, entityDelete, String.class);

        String thirdCode = delete.getBody();
        System.out.println("Третья часть кода " + thirdCode);

        System.out.println(firstCode + secondCode + thirdCode);
    }
}