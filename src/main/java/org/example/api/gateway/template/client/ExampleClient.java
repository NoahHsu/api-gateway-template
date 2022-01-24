package org.example.api.gateway.template.client;

import org.example.api.gateway.template.model.example.ExampleHealth;
import org.example.api.gateway.template.model.example.ExampleRequest;
import org.example.api.gateway.template.model.example.ExampleResponse;
import org.example.api.gateway.template.constants.ApiConstants;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ExampleClient {
    String PATH_HEALTH = "actuator/health";
    String PATH_POST_EXAMPLE = ApiConstants.BASE_URL + ApiConstants.EXAMPLE_URL;

    @GET(PATH_HEALTH)
    Call<ExampleHealth> getHealth();

    @POST(PATH_POST_EXAMPLE)
    Call<ExampleResponse> postExample(@Body ExampleRequest request);
}
