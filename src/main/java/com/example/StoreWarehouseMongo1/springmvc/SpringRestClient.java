//package com.example.StoreWarehouseMongo1.springmvc;
//
//import com.example.StoreWarehouseMongo1.model.AuthTokenInfo;
//import com.example.StoreWarehouseMongo1.model.User;
//import io.jsonwebtoken.lang.Assert;
//import java.net.URI;
//import java.util.Arrays;
//import java.util.LinkedHashMap;
//import org.apache.tomcat.util.codec.binary.Base64;
//import org.springframework.data.mongodb.core.mapping.Document;
//import org.springframework.http.HttpEntity;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpMethod;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.client.RestTemplate;
//
///**
// *
// * @author Tasos
// */
//@Document(collection = "token")
//public class SpringRestClient {
//    
//    public static final String REST_SERVICE_URI = "http://localhost:8080/StoreWarehouseMongo1";
//    public static final String AUTH_SERVER_URI = "http://localhost:8080/StoreWarehouseMongo1/oauth/token";
//    public static final String QPM_PASSWORD_GRANT = "?grant_type=password&username=bill&password=abc123";
//    public static final String QPM_ACCESS_TOKEN = "?access_token=";
//
//    /*
//     * Prepare HTTP Headers.
//     */
//    private static HttpHeaders getHeaders() {
//        HttpHeaders headers = new HttpHeaders();
//        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
//        return headers;
//    }
//
//    /*
//     * Add HTTP Authorization header, using Basic-Authentication to send client-credentials.
//     */
//    private static HttpHeaders getHeadersWithClientCredentials() {
//        String plainClientCredentials = "my-trusted-client:secret";
//        String base64ClientCredentials = new String(Base64.encodeBase64(plainClientCredentials.getBytes()));
//        HttpHeaders headers = getHeaders();
//        headers.add("Authorization", "Basic " + base64ClientCredentials);
//        return headers;
//    }
//
//    @SuppressWarnings({"unchecked"})
//    private static AuthTokenInfo sendTokenRequest() {
//        RestTemplate restTemplate = new RestTemplate();
//
//        HttpEntity<String> request = new HttpEntity<String>(getHeadersWithClientCredentials());
//        ResponseEntity<Object> response = restTemplate.exchange(AUTH_SERVER_URI + QPM_PASSWORD_GRANT, HttpMethod.POST, request, Object.class);
//        LinkedHashMap<String, Object> map = (LinkedHashMap<String, Object>) response.getBody();
//        AuthTokenInfo tokenInfo = null;
//
//        if (map != null) {
//            tokenInfo = new AuthTokenInfo();
//            tokenInfo.setAccess_token((String) map.get("access_token"));
//            tokenInfo.setToken_type((String) map.get("token_type"));
//            tokenInfo.setRefresh_token((String) map.get("refresh_token"));
//            tokenInfo.setExpires_in((int) map.get("expires_in"));
//            tokenInfo.setScope((String) map.get("scope"));
//            System.out.println(tokenInfo);
//        } else {
//            System.out.println("No user exist----------");
//
//        }
//        return tokenInfo;
//    }
//    
//    /*
//     * Send a GET request to get a specific user.
//     */
//    private static void getUser(AuthTokenInfo tokenInfo){
//        Assert.notNull(tokenInfo, "Authenticate first please......");
//        System.out.println("\nTesting getUser API----------");
//        RestTemplate restTemplate = new RestTemplate();
//        HttpEntity<String> request = new HttpEntity<String>(getHeaders());
//        ResponseEntity<User> response = restTemplate.exchange(REST_SERVICE_URI+"/user/1"+QPM_ACCESS_TOKEN+tokenInfo.getAccess_token(),
//                HttpMethod.GET, request, User.class);
//        User user = response.getBody();
//        System.out.println(user);
//    }
//      
//    /*
//     * Send a POST request to create a new user.
//     */
//    private static void createUser(AuthTokenInfo tokenInfo) {
//        Assert.notNull(tokenInfo, "Authenticate first please......");
//        System.out.println("\nTesting create User API----------");
//        RestTemplate restTemplate = new RestTemplate();
//        User user = new User(0,"Sarah",51,134);
//        HttpEntity<Object> request = new HttpEntity<Object>(user, getHeaders());
//        URI uri = restTemplate.postForLocation(REST_SERVICE_URI+"/user/"+QPM_ACCESS_TOKEN+tokenInfo.getAccess_token(),
//                request, User.class);
//        System.out.println("Location : "+uri.toASCIIString());
//    }
//  
//    /*
//     * Send a PUT request to update an existing user.
//     */
//    private static void updateUser(AuthTokenInfo tokenInfo) {
//        Assert.notNull(tokenInfo, "Authenticate first please......");
//        System.out.println("\nTesting update User API----------");
//        RestTemplate restTemplate = new RestTemplate();
//        User user  = new User(1,"Tomy",33, 70000);
//        HttpEntity<Object> request = new HttpEntity<Object>(user, getHeaders());
//        ResponseEntity<User> response = restTemplate.exchange(REST_SERVICE_URI+"/user/1"+QPM_ACCESS_TOKEN+tokenInfo.getAccess_token(),
//                HttpMethod.PUT, request, User.class);
//        System.out.println(response.getBody());
//    }
//  
//    /*
//     * Send a DELETE request to delete a specific user.
//     */
//    private static void deleteUser(AuthTokenInfo tokenInfo) {
//        Assert.notNull(tokenInfo, "Authenticate first please......");
//        System.out.println("\nTesting delete User API----------");
//        RestTemplate restTemplate = new RestTemplate();
//        HttpEntity<String> request = new HttpEntity<String>(getHeaders());
//        restTemplate.exchange(REST_SERVICE_URI+"/user/3"+QPM_ACCESS_TOKEN+tokenInfo.getAccess_token(),
//                HttpMethod.DELETE, request, User.class);
//    }
//  
//  
//    /*
//     * Send a DELETE request to delete all users.
//     */
//    private static void deleteAllUsers(AuthTokenInfo tokenInfo) {
//        Assert.notNull(tokenInfo, "Authenticate first please......");
//        System.out.println("\nTesting all delete Users API----------");
//        RestTemplate restTemplate = new RestTemplate();
//        HttpEntity<String> request = new HttpEntity<String>(getHeaders());
//        restTemplate.exchange(REST_SERVICE_URI+"/user/"+QPM_ACCESS_TOKEN+tokenInfo.getAccess_token(),
//                HttpMethod.DELETE, request, User.class);
//    }
//}
