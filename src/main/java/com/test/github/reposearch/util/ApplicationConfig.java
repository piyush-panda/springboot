package com.test.github.reposearch.util;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class ApplicationConfig {

    @Value("${github.searchapi.url}")
    private String githubSearchApiUrl;
    @Value("${search.results.per.page}")
    private int searchResultPerPage;
    @Value("${github.max.search.results}")
    private int githubMaxSearchResults;
}
