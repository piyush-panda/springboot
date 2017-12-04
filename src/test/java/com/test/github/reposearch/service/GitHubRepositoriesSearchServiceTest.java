package com.test.github.reposearch.service;

import com.test.github.reposearch.entity.GitHubRepository;
import com.test.github.reposearch.entity.RepositorySearchResult;
import com.test.github.reposearch.util.ApplicationConfig;
import com.test.github.reposearch.util.GitHubAPIRequestDispatcher;
import com.test.github.reposearch.util.RequestRateLimitHandler;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Answers;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class GitHubRepositoriesSearchServiceTest {

    @Mock
    private GitHubAPIRequestDispatcher<RepositorySearchResult> requestDispatcher;
    @Mock
    private RequestRateLimitHandler requestRateLimitHandler;
    @Mock(answer = Answers.RETURNS_SMART_NULLS)
    private ApplicationConfig config;

    @InjectMocks
    private GitHubRepositoriesSearchService searchService;

    @Test
    public void searchRepositories_WhenRateLimitReached_SubsequentRequestShouldStillPass() throws Exception {

        RepositorySearchResult expectedResult = new RepositorySearchResult();
        expectedResult.setGitRepositories(new GitHubRepository[1]);
        when(requestDispatcher.doGet(anyString(), Matchers.<Class>any())).thenThrow(new HttpClientErrorException(HttpStatus.FORBIDDEN))
                .thenReturn(expectedResult);

        List<GitHubRepository> actualGitRepositories = searchService.searchRepositories("TestLanguage");

        assertThat(actualGitRepositories.size(), is(1));
    }

    @Test
    public void searchRepositories_WhenTotalNumberOfRepositoriesIsLessThanMaxSearchResultCount_TotalNumberOfRepositoriesReturned(){
        when(config.getGithubMaxSearchResults()).thenReturn(2);
        when(config.getSearchResultPerPage()).thenReturn(1);
        RepositorySearchResult expectedResult = new RepositorySearchResult();
        expectedResult.setGitRepositories(new GitHubRepository[1]);
        expectedResult.setTotalCount(1);

        when(requestDispatcher.doGet(anyString(), Matchers.<Class>any())).thenReturn(expectedResult);

        List<GitHubRepository> actualGitRepositories = searchService.searchRepositories("TestLanguage");

        assertThat(actualGitRepositories.size(), is(1));
    }

    @Test
    public void searchRepositories_WhenMaxSearchResultCountIsLessThanTotalNumberOfRepositories_WhenMaxSearchResultCountRepositoriesReturned(){
        when(config.getGithubMaxSearchResults()).thenReturn(1);
        when(config.getSearchResultPerPage()).thenReturn(1);
        RepositorySearchResult expectedResult = new RepositorySearchResult();
        expectedResult.setGitRepositories(new GitHubRepository[1]);
        expectedResult.setTotalCount(2);

        when(requestDispatcher.doGet(anyString(), Matchers.<Class>any())).thenReturn(expectedResult);

        List<GitHubRepository> actualGitRepositories = searchService.searchRepositories("TestLanguage");

        assertThat(actualGitRepositories.size(), is(1));
    }

}