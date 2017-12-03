package com.test.github.reposearch.entity;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class RepositorySearchResult {

    @JsonProperty(value = "total_count")
    private long totalCount;
    @JsonProperty(value = "incomplete_results")
    private boolean incompleteResults;
    @JsonProperty(value = "items")
    private GitHubRepository[] gitRepositories;
}
