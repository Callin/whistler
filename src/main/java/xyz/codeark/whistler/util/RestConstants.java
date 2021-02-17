package xyz.codeark.whistler.util;

public class RestConstants {
    /**
     * Paths
     */
    public static final String API = "api/";
    public static final String DISCOVERY = API + "discovery";
    public static final String MAVEN = API + "mvn";
    public static final String GIT = API + "git";

    /**
     * Directory related exception messages
     */
    public static final String DIRECTORY_DISCOVERY_INVALID_PATH = "Invalid directory path";
    public static final String DIRECTORY_DISCOVERY_FAILURE = "Directory discovery failed due to IO error";

    /**
     * Maven related constants
     */
    public static final String MAVEN_BUILD_SUCCESS = "Maven build success";
    public static final String MAVEN_BUILD_FAIL = "Maven build fail";
    public static final String MAVEN_INVOKER_FAILURE = "Maven invoker failed";
    public static final String INVALID_MVN_MODULE_PATH = "Invalid maven module path";
    public static final String UNSUPPORTED_OPERATING_SYSTEM = "Unsupported operating system";
    public static final String MAVEN_PATH_NOT_FOUND_IN_PATH_VARIABLE = "Maven not found in path variable";

    /**
     * Git related constants
     */
    public static final String INVALID_GIT_REPOSITORY_PATH = "Invalid git repository path";
    public static final String GIT_PULL_FAILED = "Git pull failed";
    public static final String GIT_PULL_SUCCESS = "Git pull executed successfully";
    public static final String GIT_REPOSITORY_IS_UP_TO_DATE = "Git repository is up to date with origin";
    public static final String GIT_REPOSITORY_IS_AHEAD_OF_ORIGIN = "Git repository is ahead origin";
    public static final String GIT_REPOSITORY_IS_BEHIND_ORIGIN = "Git repository is behind origin";
    public static final String GIT_NO_REMOTE_TRACKING_OF_BRANCH = "Returned null, likely no remote tracking of branch";
    public static final String GIT_REPOSITORY_NO_REMOTE_ORIGIN_FOUND_IN_THE_LOCAL_CONFIG = "No remote origin found in the local git config file";
    public static final String GIT_ERROR_WHILE_UPDATING_REPOSITORY = "Error while updating the repository";
    public static final String ERROR_BUILDING_GIT_INSTANCE = "Error while building a Git instance";
    public static final String ERROR_FETCHING_INVALID_REMOTE = "Error while fetching remote branches. Invalid remote.";
    public static final String ERROR_FETCHING_TRANSPORT_FAILED = "Error while fetching remote branches. Transport operation failed, likely due to an authentication issue.";
    public static final String ERROR_CONNECTING_TO_REMOTE_REPOSITOY_AUTHENTICATION_IS_REQUIRED = "Error while connecting to remote repository. Authentication is required.";
    public static final String ERROR_CONNECTING_TO_REMOTE_REPOSITOY_AUTH_FAIL = "Auth fail.";
    public static final String ERROR_FETCHING_GITAPI_EXCEPTION = "Error while fetching remote branches. GIT API exception.";
    public static final String ERROR_WHILE_STASHING_CHANGES = "Error while stashing the changes";
    public static final String ERROR_WHILE_CHECKING_BRANCH_STATUS = "Error while checking the status";
    public static final String ERROR_WHILE_CHECKING_REMOTE_BRANCH = "Error while checking if remote branch exists";
    public static final String ERROR_WHILE_CHECKING_BRANCH_EXISTS = "Error while checking if branch exists locally";
    public static final String ERROR_WHILE_CHECKING_OUT_BRANCH = "Error while checking out local branch";
    public static final String ERROR_WHILE_REBASING_BRANCH = "Error while rebasing branch";
    public static final String BRANCH_DOES_NOT_EXIST = "The branch does not exist locally or remotely";
    public static final String ERROR_WHILE_GETTING_THE_TRANSPORT_PROTOCOL = "Error while getting the transport protocol";
    public static final String NO_SUPPORTED_TRANSPORT_PROTOCOL_FOUND = "No supported transport protocol found";
}
