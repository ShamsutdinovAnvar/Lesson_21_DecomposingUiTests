package com.wegotrip.specs;

import com.wegotrip.api.AuthorizationApi;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import static com.wegotrip.api.AuthorizationApi.ALLURE_TESTOPS_SESSION;
import static com.wegotrip.helpers.CustomApiListener.withCustomTemplates;
import static com.wegotrip.specs.tests.BaseTest.credentialsConfig;

public class LoginSpecs {

    public final static String USERNAME = credentialsConfig.username();
    public final static String PASSWORD = credentialsConfig.password();
    public final static String TOKEN = credentialsConfig.token();

    public static RequestSpecification getRequestSpec() {

        AuthorizationApi authorizationApi = new AuthorizationApi();
        String xsrfToken = authorizationApi.getXsrfToken(TOKEN);
        String authorizationCookie = authorizationApi
                .getAuthorizationCookie(TOKEN, USERNAME, PASSWORD);

        return RestAssured
                .given()
                .log().all()
                .filter(withCustomTemplates())
                .header("X-XSRF-TOKEN", xsrfToken)
                .cookies("XSRF-TOKEN", xsrfToken,
                        ALLURE_TESTOPS_SESSION, authorizationCookie)
                .contentType(ContentType.JSON);

    }

}
