package xyz.codeark.whistler.service;

import lombok.extern.slf4j.Slf4j;
import org.eclipse.jgit.api.CreateBranchCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.ListBranchCommand;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Ref;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import xyz.codeark.whistler.dto.Directory;
import xyz.codeark.whistler.exceptions.WhistlerRestException;
import xyz.codeark.whistler.util.RestConstants;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Service
public class GitService {
    private static final String GRADLE_FILE = "build.gradle";
    private static final String GIT_DIRECTORY = ".git";

    public static void main(String[] args) {
        checkoutBranch(discoverRepositories("/home/dragos/dev/projects/", 1)
                        .stream().filter(dir -> dir.getName().equals("whistler")).findFirst().get(),
                "testTwo");
    }

    public static void checkoutBranch(Directory directory, String branchName) {
        if (branchExistsLocally(directory, branchName)) {
            try {
                log.info("Checking out local branch={} for repo={}", branchName, directory.getName());
                Git.open(new File(directory.getPath()))
                        .checkout()
                        .setCreateBranch(false)
                        .setName(branchName)
                        .call();
            } catch (GitAPIException | IOException e) {
                log.error("Something went wrong while checking out the branch={} for the repository={}", branchName, directory.getName(), e);
                throw new WhistlerRestException(RestConstants.ERROR_WHILE_CHECKING_OUT_BRANCH, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else if (branchExistsRemotely(directory, branchName)) {
            try {
                log.info("Checking out remote branch={} for repo={}", branchName, directory.getName());
                Git.open(new File(directory.getPath()))
                        .checkout()
                        .setCreateBranch(true)
                        .setName(branchName)
                        .setUpstreamMode(CreateBranchCommand.SetupUpstreamMode.TRACK)
                        .setStartPoint("origin/" + branchName)
                        .call();
            } catch (GitAPIException | IOException e) {
                log.error("Something went wrong while checking out the branch={} for the repository={}", branchName, directory.getName(), e);
                throw new WhistlerRestException(RestConstants.ERROR_WHILE_CHECKING_OUT_BRANCH, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            log.warn("The branch does not exist locally or remotely");
            throw new WhistlerRestException(RestConstants.BRANCH_DOES_NOT_EXIST, HttpStatus.BAD_REQUEST);
        }

        pullRebase(directory, branchName);
    }

    public static void pullRebase(Directory directory, String branchName) {
        try {
            log.info("Rebasing branch={} for repo={}", branchName, directory.getName());
            Git.open(new File(directory.getPath()))
                    .pull()
                    .setRemoteBranchName(branchName)
                    .setRebase(true)
                    .call();
        } catch (GitAPIException | IOException e) {
            log.error("Something went wrong while rebasing the branch={} for the repository={}", branchName, directory.getName(), e);
            throw new WhistlerRestException(RestConstants.ERROR_WHILE_REBASING_BRANCH, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private static Boolean branchExistsLocally(Directory directory, String branchName) {
        try {
            return Git.open(new File(directory.getPath()))
                    .branchList()
                    .call()
                    .stream()
                    .map(Ref::getName)
                    .collect(Collectors.toList())
                    .contains("refs/heads/" + branchName);
        } catch (GitAPIException | IOException e) {
            log.error("Something went wrong while checking if the branch={} exists locally for the repository={}", branchName, directory.getName(), e);
            throw new WhistlerRestException(RestConstants.ERROR_WHILE_CHECKING_BRANCH_EXISTS, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private static Boolean branchExistsRemotely(Directory directory, String branchName) {
        try {
            return Git.open(new File(directory.getPath()))
                    .branchList()
                    .setListMode(ListBranchCommand.ListMode.REMOTE)
                    .call()
                    .stream()
                    .map(Ref::getName)
                    .collect(Collectors.toList())
                    .contains("refs/remotes/origin/" + branchName);
        } catch (GitAPIException | IOException e) {
            log.error("Something went wrong while checking if the branch={} exists remotely for the repository={}", branchName, directory.getName(), e);
            throw new WhistlerRestException(RestConstants.ERROR_WHILE_CHECKING_BRANCH_EXISTS, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public static List<Directory> discoverRepositories(String rootDirectory, int maxDirectoryDepth) {
        log.info("Discovering gradle projects and git repositories in {}, max depth {}", rootDirectory, maxDirectoryDepth);
        List<Directory> directories = new LinkedList<>();
        try (Stream<Path> paths = Files.walk(Paths.get(rootDirectory), maxDirectoryDepth)) {
            paths.forEach(filePath -> {
                if (Files.isDirectory(filePath)) {
                    Directory directory = new Directory();
                    directory.setPath(filePath.toString());
                    directory.setName(filePath.getFileName().toString());
                    if (Files.exists(filePath.resolve(GRADLE_FILE))) {
                        directory.setIsGradleProject(true);
                    }
                    if (Files.exists(filePath.resolve(GIT_DIRECTORY))) {
                        directory.setIsGitRepository(true);
                        try {
                            directory.setBranchName(Git.open(new File(filePath.toString())).getRepository().getBranch());
                        } catch (IOException e) {
                            log.error(e.getMessage());
                        }
                    }
                    directories.add(directory);
                }
            });
        } catch (IOException e) {
            log.error("There was an error while discovering the directories. ", e);
            throw new WhistlerRestException(RestConstants.DIRECTORY_DISCOVERY_FAILURE, HttpStatus.ACCEPTED);
        }

        log.info("Directory discovery completed successfully");
        return directories;
    }
}
