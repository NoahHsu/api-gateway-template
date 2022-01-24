package org.example.api.gateway.template.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import okhttp3.MediaType;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

@SuppressWarnings("unchecked")
public class CustomRetryCallUtilsTest {

    private final Call<String> originCall = mock(Call.class);
    private static final String mockResult = "mockResult";

    @Test
    public void callFirstOk() throws IOException {
        final Call<String> call = mock(Call.class);
        when(call.execute()).thenReturn(Response.success(mockResult));
        when(originCall.clone()).thenReturn(call);

        final Response<String> res = CustomRetryCallUtils.retryCall(originCall, 2, s -> !s.isBlank())
                                                         .orElseThrow();
        Assertions.assertTrue(res.isSuccessful());
        assertEquals(res.body(), mockResult);
        verify(originCall, times(1)).clone();
        verify(call, times(1)).execute();
    }

    @Test
    public void callFirstFailSecondOk() throws IOException {
        final Call<String> call = mock(Call.class);
        when(call.execute()).thenReturn(
                Response.error(400, ResponseBody.create(mockResult, MediaType.get("Application/json"))),
                Response.success(mockResult));
        when(originCall.clone()).thenReturn(call);

        final Response<String> res = CustomRetryCallUtils.retryCall(originCall, 2, s -> !s.isBlank())
                                                         .orElseThrow();
        Assertions.assertTrue(res.isSuccessful());
        assertEquals(res.body(), mockResult);
        verify(originCall, times(2)).clone();
        verify(call, times(2)).execute();
    }

    @Test
    public void callFirstCustomConditionNotFitSecondOk() throws IOException {
        final Call<String> call = mock(Call.class);
        when(call.execute()).thenReturn(
                Response.success(""),
                Response.success(mockResult));
        when(originCall.clone()).thenReturn(call);

        final Response<String> res = CustomRetryCallUtils.retryCall(originCall, 2, s -> !s.isBlank())
                                                         .orElseThrow();
        Assertions.assertTrue(res.isSuccessful());
        assertEquals(res.body(), mockResult);
        verify(originCall, times(2)).clone();
        verify(call, times(2)).execute();
    }

    @Test
    public void callFailExceedLimit() throws IOException {
        final Call<String> call = mock(Call.class);
        when(call.execute()).thenReturn(
                Response.error(400, ResponseBody.create(mockResult, MediaType.get("Application/json"))));
        when(originCall.clone()).thenReturn(call);

        final Response<String> res = CustomRetryCallUtils.retryCall(originCall, 1, s -> !s.isBlank())
                                                         .orElseThrow();
        Assertions.assertFalse(res.isSuccessful());
        assertEquals(mockResult, res.errorBody().string());
        verify(originCall, times(2)).clone();
        verify(call, times(1)).execute();
    }

    @Test
    public void callThrowException() throws IOException {
        final Call<String> call = mock(Call.class);
        when(call.execute()).thenThrow(new IOException("mock"));
        when(originCall.clone()).thenReturn(call);

        final Optional<Response<String>> res = CustomRetryCallUtils.retryCall(originCall, 1);
        Assertions.assertTrue(res.isEmpty());
        verify(originCall, times(2)).clone();
        verify(call, times(1)).execute();
    }

    @Test
    public void callWithoutCustomCondition() throws IOException {
        final Call<String> call = mock(Call.class);
        when(call.execute()).thenReturn(Response.success(mockResult));
        when(originCall.clone()).thenReturn(call);

        final Response<String> res = CustomRetryCallUtils.retryCall(originCall, 2)
                                                         .orElseThrow();
        Assertions.assertTrue(res.isSuccessful());
        assertEquals(res.body(), mockResult);
        verify(originCall, times(1)).clone();
        verify(call, times(1)).execute();
    }

    @Test
    public void callLimitBelow1() {
        final IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                                                                () -> CustomRetryCallUtils.retryCall(originCall, 0));
        assertEquals("limit should greater than 0", exception.getMessage());

    }
}
