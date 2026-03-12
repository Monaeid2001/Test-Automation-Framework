package com.automationexercise.apis;

import com.automationexercise.utils.logs.LogsManager;
import com.automationexercise.validations.Verification;
import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.HashMap;
import java.util.Map;

public class UserManagmentAPI {
    RequestSpecification requestSpecification;
    Response response;
    Verification verification;
    public UserManagmentAPI(){
        requestSpecification= RestAssured.given();
            verification = new Verification();
    }
    //endpoints
    private static final String createAccount_endpoint = "/createAccount";
    private static final String deleteAccount_endpoint = "/deleteAccount";

    //api methods
    @Step("create user account with full details")
    public UserManagmentAPI createRegisterUserAccount(String name, String email, String password, String title, String birth_date, String birth_month, String birth_year, String firstname, String lastname,
                                                      String company, String address1, String address2, String country, String zipcode, String state, String city, String mobile_number){

    Map<String, String> formParams = new HashMap<>();
        formParams.put("name", name);
        formParams.put("email", email);
        formParams.put("password", password);
        formParams.put("title", title);
        formParams.put("birth_date", birth_date);
        formParams.put("birth_month", birth_month);
        formParams.put("birth_year", birth_year);
        formParams.put("firstname", firstname);
        formParams.put("lastname", lastname);
        formParams.put("company", company);
        formParams.put("address1", address1);
        formParams.put("address2", address2);
        formParams.put("country", country);
        formParams.put("zipcode", zipcode);
        formParams.put("state", state);
        formParams.put("city", city);
        formParams.put("mobile_number", mobile_number);
        response = requestSpecification.spec(Builder.getUserManagmentRequestSpecification(formParams))
                .post(createAccount_endpoint);
        LogsManager.info(response.asPrettyString());

        return this;
    }

    @Step("create user account with minimal details")
    public UserManagmentAPI createRegisterUserAccount(String name, String email, String password, String firstname, String lastname)
    {
        Map<String, String> formParams = new HashMap<>();
        formParams.put("name", name);
        formParams.put("email", email);
        formParams.put("password", password);
        formParams.put("title", "Mrs");
        formParams.put("birth_date", "1");
        formParams.put("birth_month", "January");
        formParams.put("birth_year", "1990");
        formParams.put("firstname", firstname);
        formParams.put("lastname", lastname);
        formParams.put("company", "company");
        formParams.put("address1", "address1");
        formParams.put("address2", "address2");
        formParams.put("country", "India");
        formParams.put("zipcode", "12345");
        formParams.put("state", "state");
        formParams.put("city", "city");
        formParams.put("mobile_number", "1234567890");
        response = requestSpecification.spec(Builder.getUserManagmentRequestSpecification(formParams))
                .post(createAccount_endpoint);
        LogsManager.info(response.asPrettyString());

        return this;
    }
  @Step("Delete user account with email and password")
    public UserManagmentAPI deleteUserAccount(String email, String password){
        Map<String, String> formParams = new HashMap<>();
        formParams.put("email", email);
        formParams.put("password", password);
        response = requestSpecification.spec(Builder.getUserManagmentRequestSpecification(formParams))
                .delete(deleteAccount_endpoint);
        LogsManager.info(response.asPrettyString());

        return this;
    }


    //validations
    @Step("Verify that user is created successfully with response message")
    public UserManagmentAPI verifyUserCreatedSuccessfully(){
        verification.Equals(
                response.jsonPath().get("message"),"User created!","User is not created "
        );
        return this;
    }
        @Step("Verify that user is deleted successfully with response message")
    public UserManagmentAPI verifyUserDeletedSuccessfully(){
        verification.Equals(
                response.jsonPath().get("message"),"Account deleted!","Account is not deleted "
        );
        return this;
        }


}
