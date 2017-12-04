package com.test.github.reposearch;


import com.google.gson.Gson;
import com.test.github.reposearch.entity.GitHubRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(
        webEnvironment = RANDOM_PORT,
        classes = GitHubRepositorySearchApplication.class)
@AutoConfigureMockMvc
@TestPropertySource(
        locations = "classpath:application-integrationtest.properties")
public class GitHubRepositorySearchIT {


    @Autowired
    private MockMvc mvc;

    @Value("${github.max.search.results}")
    private int githubMaxSearchResults;

    @Test
    public void givenThereAreSomeRepos_whenSearchBasedOnAValidLanguage_thenStatus200() throws Exception {
        mvc.perform(get("/githubrepositories/search?language=java")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    public void givenThereAreSomeRepos_whenSearchOnLanguageWithMoreReposThanMaxResults_thenMaxNumberOfResultsReturned() throws Exception {
        MvcResult result = mvc.perform(get("/githubrepositories/search?language=java")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn();

        GitHubRepository[] gitRepositories = new Gson().fromJson(result.getResponse().getContentAsString(), GitHubRepository[].class);

        assertThat(gitRepositories.length, is(githubMaxSearchResults ));
    }

    @Test
    public void givenThereAreSomeRepos_WhenSearchBasedWithoutGivingAnyLanguageInQuery_theStatus400() throws Exception {
        mvc.perform(get("/githubrepositories/search")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(400));
    }

    @Test
    public void givenThereAreSomeRepos_WhenSearchBasedOnAnInvalidLanguage_thenStatus500() throws Exception {
        MvcResult result = mvc.perform(get("/githubrepositories/search?language=123456")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(500))
                .andReturn();

        assertThat(result.getResponse().getErrorMessage(), is("Some unexpected error has occurred. Please try again"));
    }
}
