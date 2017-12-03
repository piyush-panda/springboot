package com.test.github.reposearch.util;

import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;

@Component
public class RequestRateLimitHandler {

    public void handleRateLimitCondition(HttpClientErrorException ex) {
        long timeWhenRateLimitResets = Long.parseLong(ex.getResponseHeaders().get("X-RateLimit-Reset").get(0))*1000l;
        long currentTimeMillis = getCurrentTimeInMillis();
        long timeToWaitTillReset = timeWhenRateLimitResets - currentTimeMillis;
        if(timeToWaitTillReset>0) {
            sleepDueToRateLimit(timeToWaitTillReset);
        }
    }

    protected long getCurrentTimeInMillis() {
        return System.currentTimeMillis();
    }

    protected void sleepDueToRateLimit(long time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException ex) {
            throw new RuntimeException(ex);
        }
    }
}
