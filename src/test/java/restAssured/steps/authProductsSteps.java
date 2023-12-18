package restAssured.steps;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.restassured.response.Response;
import org.junit.Assert;
import pojo.products;
import pojo.transactions;
import utility.apiConstant;
import utility.restExtension;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class authProductsSteps {

    static String token;
    static Response response;
    restExtension extension;

    @Given("I perform an authentication for {string} with a body of login")
    public void iPerformAnAuthenticationForWithABodyOfLogin (String uri,DataTable table){
        extension = new restExtension(uri,apiConstant.apiMethods.POST,null);
        HashMap<String, String> authBody = new HashMap<>();
        authBody.put(table.cell(0,0),table.cell(1,0));
        authBody.put(table.cell(0,1),table.cell(1,1));
        token = extension.authenticationMethod(authBody);

    }

    @And("I perform a {string} op for {string}")
    public void iPerformAOpFor (String method,String uri){

        System.out.println("null");
        extension = new restExtension(uri,method,token);
        response = extension.executeAPI();

    }

    @And("I perform a {string} op for {string} with datatable")
    public void iPerformAOpFor (String method,String uri,DataTable table){
        HashMap<String, String> body = new HashMap<>();
        System.out.println(table.height() + " " + table.width());
//        body.put(table.cell(0,0), table.cell(1,0));
//        body.put(table.cell(0,1), table.cell(1,1));
//        body.put(table.cell(0,2), table.cell(1,2));
        for (int i = 0; i < table.width(); i++) {
            body.put(table.cell(0,i),table.cell(1,i));
        }
        extension = new restExtension(uri,method,token);
        response = extension.executeWitBody(body);
    }

    @Then("I should see the {string} as {string} for {string}")
    public void iShouldSeeTheAs (String actual,String expected,String endpoint){
        extension.printResponse(response);
        if(endpoint.equalsIgnoreCase("transactions")) {
            transactions transactions = response.getBody().as(transactions.class);
            assertThat(transactions.getCost(),is(Integer.parseInt(expected)));
        } else if(endpoint.equalsIgnoreCase("products")) {
            products products = response.getBody().as(products.class);
            assertThat(products.getName(),is(expected));

        }
    }

    @And("I perform a {string} operation with query parameter for {string}")
    public void iPerformAGetOperationWithQueryParameterFor (String method,String uri,DataTable table){
        restExtension extension = new restExtension(uri,method,token);
        HashMap<String, String> query = new HashMap<>();
        query.put(table.cell(0,0),table.cell(1,0));
        response = extension.executeWithQuery(query);
    }

    @Then("I should see the {string} as {string} form a query response")
    public void iShouldSeeTheAsFormAQueryResponse (String actual,String expected){

        List<HashMap<String, String>> responseMap = response.jsonPath().getList("$");
        for (HashMap<String, String> singleObject : responseMap) {
            if(singleObject.get(actual).equals(expected)) {
                assertThat(singleObject.get(actual),is(expected));
            }

        }
    }

    private static void getApplicationIdBasedOnId (Response response,String id){
        List<HashMap<String, String>> responseMap = response.jsonPath().getList("$");
        for (HashMap<String, String> singleObject : responseMap) {
            if(singleObject.get("id").equals(id)) {
                //String return singleObject.get("applicationId");
            }
        }
        //throw new NoSuchElementException("Cannot find applicationId for id: " + id);
    }

    @Then("I should have the status code as {string}")
    public void iShouldHaveTheStatusCodeAs (String statusCode){
        String responseCode = String.valueOf(response.then().extract().statusCode());
        Assert.assertEquals(responseCode,statusCode);
    }

    @Then("validate schema")
    public void validateSchema (){
        String json = response.getBody().asString();
        assertThat(json,matchesJsonSchemaInClasspath("restAssured/productsSchema.json"));
    }
}






