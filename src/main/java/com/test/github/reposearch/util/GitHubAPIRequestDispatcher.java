package com.test.github.reposearch.util;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class GitHubAPIRequestDispatcher<T> {
    private RestTemplate restTemplate = new RestTemplate();

    public T doGet(String url, Class<T> entityClass) {
        T t = restTemplate.getForObject(url, entityClass);
        return t;
    }
}
