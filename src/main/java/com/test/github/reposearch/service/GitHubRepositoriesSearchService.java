package com.test.github.reposearch.service;

import com.test.github.reposearch.entity.GitHubRepository;
import com.test.github.reposearch.entity.RepositorySearchResult;
import com.test.github.reposearch.util.GitHubAPIRequestDispatcher;
import com.test.github.reposearch.util.ApplicationConfig;
import com.test.github.reposearch.util.RequestRateLimitHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class GitHubRepositoriesSearchService {

    private GitHubAPIRequestDispatcher<RepositorySearchResult> requestDispatcher;
    private RequestRateLimitHandler requestRateLimitHandler;
    private ApplicationConfig config;

    @Autowired
    public GitHubRepositoriesSearchService(GitHubAPIRequestDispatcher<RepositorySearchResult> requestDispatcher,
                                           RequestRateLimitHandler requestRateLimitHandler,
                                           ApplicationConfig config){
        this.requestDispatcher = requestDispatcher;
        this.requestRateLimitHandler = requestRateLimitHandler;
        this.config = config;
    }

    public List<GitHubRepository> searchRepositories(String language){
        int pageNumber = 1;
        int resultsFetched = 0;
        long totalResultCount = 0;

        List<GitHubRepository> gitRepositories = new ArrayList<>();

        try {
            do {
                String url = String.format(config.getGithubSearchApiUrl(), language, pageNumber, config.getSearchResultPerPage());
                RepositorySearchResult repositorySearchResult = requestDispatcher.doGet(url, RepositorySearchResult.class);
                gitRepositories.addAll(Arrays.asList(repositorySearchResult.getGitRepositories()));
                resultsFetched += repositorySearchResult.getGitRepositories().length;
                totalResultCount = repositorySearchResult.getTotalCount();
                pageNumber++;
            } while ((resultsFetched < totalResultCount) && (resultsFetched < config.getGithubMaxSearchResults()));
            return gitRepositories;
        }catch (HttpClientErrorException ex){
            if("403 Forbidden".equalsIgnoreCase(ex.getMessage())){
                requestRateLimitHandler.handleRateLimitCondition(ex);
                return searchRepositories(language);
            }
            throw ex;
        }
    }
}
