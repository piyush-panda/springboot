package com.test.github.reposearch;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.test.github.reposearch")
public class GitHubRepositorySearchApplication implements CommandLineRunner {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(GitHubRepositorySearchApplication.class, args);
    }

    @Override
    public void run(String... strings) throws Exception {
    }
}
