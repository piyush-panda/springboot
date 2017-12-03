package com.test.github.reposearch.util;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.web.client.HttpClientErrorException;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.RETURNS_DEEP_STUBS;
import static org.mockito.Mockito.when;


public class RequestRateLimitHandlerTest {

    @Test
    public void handleRateLimitCondition_WhenRateLimitResetIfGreaterThanCurrentTime_SleepsForTheDifference(){
        TestableRequestRateLimitHandler requestRateLimitHandler = new TestableRequestRateLimitHandler(1000);
        HttpClientErrorException exception = Mockito.mock(HttpClientErrorException.class, RETURNS_DEEP_STUBS);
        when(exception.getResponseHeaders().get("X-RateLimit-Reset").get(0)).thenReturn("2");

        requestRateLimitHandler.handleRateLimitCondition(exception);

        Assert.assertThat(requestRateLimitHandler.getSleepTime(), is(1000l));
    }


    @Test
    public void handleRateLimitCondition_WhenRateLimitResetIfLessThanCurrentTime_DoesNotSleep(){
        TestableRequestRateLimitHandler requestRateLimitHandler = new TestableRequestRateLimitHandler(2000);
        HttpClientErrorException exception = Mockito.mock(HttpClientErrorException.class, RETURNS_DEEP_STUBS);
        when(exception.getResponseHeaders().get("X-RateLimit-Reset").get(0)).thenReturn("1");

        requestRateLimitHandler.handleRateLimitCondition(exception);

        Assert.assertThat(requestRateLimitHandler.getSleepTime(), is(0l));
    }

    @Test
    public void handleRateLimitCondition_WhenRateLimitResetIfEqualToCurrentTime_DoesNotSleep(){
        TestableRequestRateLimitHandler requestRateLimitHandler = new TestableRequestRateLimitHandler(1000);
        HttpClientErrorException exception = Mockito.mock(HttpClientErrorException.class, RETURNS_DEEP_STUBS);
        when(exception.getResponseHeaders().get("X-RateLimit-Reset").get(0)).thenReturn("1");

        requestRateLimitHandler.handleRateLimitCondition(exception);

        Assert.assertThat(requestRateLimitHandler.getSleepTime(), is(0l));
    }


    class TestableRequestRateLimitHandler extends  RequestRateLimitHandler{

        private int currentTimeInMillis;
        private long sleepTime;

        public TestableRequestRateLimitHandler(int currentTimeInMillis){
            this.currentTimeInMillis = currentTimeInMillis;
        }

        @Override
        public long getCurrentTimeInMillis() {
            return currentTimeInMillis;
        }

        @Override
        protected void sleepDueToRateLimit(long time) {
            super.sleepDueToRateLimit(time);
            sleepTime = time;
        }

        public long getSleepTime() {
            return sleepTime;
        }
    }
}