package org.example.api.gateway.template.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;
import retrofit2.Call;
import retrofit2.Response;

import java.util.Optional;
import java.util.function.Predicate;

import static java.lang.String.format;

public final class CustomRetryCallUtils {
    private final static Logger log = LoggerFactory.getLogger(CustomRetryCallUtils.class);

    /**
     * execute the Call at most @limit times, until success(Response.isSuccessful and match @conditions)
     */
    public static <R> Optional<Response<R>> retryCall(Call<R> origin, int limit, Predicate<R> conditions) {
        Response<R> res = null;
        Assert.isTrue(limit > 0, "limit should greater than 0");

        int times = 0;
        Call<R> clone = origin.clone();
        while (times < limit) {
            times++;
            try {
                res = clone.execute();
                if (res.isSuccessful() && conditions.test(res.body())) {
                    break;
                } else {
                    if (!res.isSuccessful()) {
                        log.error("the {}-times request call fail, returnCode = {} message= {}", times,
                                  res.code(), res.errorBody());
                    } else {
                        log.error("the {}-times request call custom condition not match, body= {}", times,
                                  res.body());
                    }
                    clone = origin.clone();
                }
            } catch (Exception e) {
                log.error(format("the %d-times request call throw exception", times), e);
                clone = origin.clone();
            }
        }
        return Optional.ofNullable(res);
    }

    /**
     * no custom condition, so custom predicate always return true
     */
    public static <R> Optional<Response<R>> retryCall(Call<R> origin, int limit) {
        return retryCall(origin, limit, response -> true);
    }
}
