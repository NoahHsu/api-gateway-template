package org.example.api.gateway.template.config;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.example.api.gateway.template.client.ExampleClient;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import okhttp3.logging.HttpLoggingInterceptor.Level;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Configuration
public class ApiClientConfig {
    @Value("${apiClient.timeout}")
    private long timeout;

    @Value("${app.endpoint.example}")
    String exampleEndpoint;

    public ApiClientConfig() {
    }

    @Bean
    public ExampleClient exampleClient() {
        final OkHttpClient cli = generateOkHttpClient(Headers.of(Map.of("Content-Type", "application/json",
                                                                        "Authorization", "exampleAuth")));
        return generateApiClient(cli, exampleEndpoint, ExampleClient.class);
    }

    private OkHttpClient generateOkHttpClient(Headers customHeader) {
        final HttpLoggingInterceptor logger = new HttpLoggingInterceptor();
        // print header and body in INFO level
        logger.setLevel(Level.BODY);
        return new OkHttpClient.Builder()
                .connectTimeout(timeout, TimeUnit.SECONDS)
                .callTimeout(timeout, TimeUnit.SECONDS)
                .readTimeout(timeout, TimeUnit.SECONDS)
                .writeTimeout(timeout, TimeUnit.SECONDS)
                .addInterceptor(chain -> {
                    final Request original = chain.request();
                    final Request request = original.newBuilder()
                                                    .headers(original.headers())
                                                    .method(original.method(), original.body())
                                                    .headers(customHeader)
                                                    .build();
                    return chain.proceed(request);
                })
                .addInterceptor(logger)
                .build();
    }

    private <R> R generateApiClient(OkHttpClient cli, String baseEndpoint, Class<R> clazz) {
        return new Retrofit.Builder()
                .baseUrl(baseEndpoint)
                .addConverterFactory(GsonConverterFactory.create())
                .client(cli)
                .build()
                .create(clazz);
    }
}
