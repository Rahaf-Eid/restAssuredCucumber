package utility;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Properties;

public class restExtension {
    private RequestSpecBuilder builder = new RequestSpecBuilder();
    private String method, url;

    Properties properties = new Properties();

    /**
     * @param uri
     * @param method
     * @param token
     */

    public restExtension (String uri,String method,String token){
        try {
            InputStream inputStream = Files.newInputStream(Paths.get(System.getProperty("user.dir") + "/src/main/java/ApiConfiguration/cofig.properties"));
            properties.load(inputStream);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.url = (properties.getProperty("apiBaseURL")) + uri;
        this.method = method;

        if(token != null) {
            builder.addHeader("Authorization","Bearer " + token);
        }

    }

    /**
     * executeAPI to execute get/post/delete etc..
     *
     * @return the Response
     */
    public Response executeAPI (){
        RequestSpecification requestSpecification = builder.build();
        RequestSpecification request = RestAssured.given();
        request.contentType(ContentType.JSON);
        request.spec(requestSpecification);

        if(this.method.equalsIgnoreCase(apiConstant.apiMethods.GET)) {
            return request.get(this.url);
        } else if(this.method.equalsIgnoreCase(apiConstant.apiMethods.POST)) {
            return request.post(this.url);
        } else if(this.method.equalsIgnoreCase(apiConstant.apiMethods.DELETE)) {
            return request.delete(this.url);
        } else if(this.method.equalsIgnoreCase(apiConstant.apiMethods.PUT)) {
            return request.put(this.url);
        } else if(this.method.equalsIgnoreCase(apiConstant.apiMethods.PATCH)) {
            return request.patch(this.url);
        }

        return null;
    }

    /**
     * to get the token
     *
     * @param body
     * @return string token
     */
    public String authenticationMethod (Map<String, String> body){
        builder.setBody(body);
        return executeAPI().getBody().jsonPath().get("access_token");

    }

    /**
     * to take the path of an endpoint
     *
     * @param queryPath
     * @return
     */
    public Response executeWithQuery (Map<String, String> queryPath){
        builder.addQueryParams(queryPath);
        return executeAPI();
    }

    public Response executeWithPathAndBody (Map<String, String> path,Map<String, String> body){
        builder.setBody(body);
        builder.addPathParams(path);
        return executeAPI();
    }

    public Response executeWitBody (Map<String, String> body){
        builder.setBody(body);
        return executeAPI();
    }


    public Response executeWithPath (Map<String, String> path){
        builder.addPathParams(path);
        return executeAPI();
    }


    /**
     * to print the response
     *
     * @param response
     */
    public void printResponse (Response response){
        System.out.println("Response: \n " + response.body().prettyPrint());
    }
}
