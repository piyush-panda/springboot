package com.test.github.reposearch.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class GitHubRepository {
    private String id;
    private String name;
    @JsonProperty(value = "html_url")
    private String url;
    private Owner owner;
}
