package xyz.codeark.whistler.controller;

import org.springframework.web.bind.annotation.RestController;
import xyz.codeark.whistler.service.GitService;

@RestController
public class GitResource {
    private GitService gitService;

    public GitResource(GitService gitService) {
        this.gitService = gitService;
    }


}
