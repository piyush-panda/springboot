package com.test.github.reposearch.controller;

import com.test.github.reposearch.entity.GitHubRepository;
import com.test.github.reposearch.service.GitHubRepositoriesSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class GitHubRepositoriesSearchController {

    private GitHubRepositoriesSearchService searchService;

    @Autowired
    public GitHubRepositoriesSearchController(GitHubRepositoriesSearchService searchService){
        this.searchService = searchService;
    }

    @RequestMapping(value = "/githubrepositories/search", method = RequestMethod.GET, produces = "application/json")
    public List<GitHubRepository> searchRepositories(@RequestParam(value = "language") String language){
        return this.searchService.searchRepositories(language);
    }
}
