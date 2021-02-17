package xyz.codeark.whistler.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Directory implements Comparable<Directory> {
    protected String path;
    protected String name;
    private String branchName;
    private Boolean isGitRepository;
    private Boolean isGradleProject;

    @Override
    public String toString() {
        return "Directory{" +
                "path='" + path + '\'' +
                ", name='" + name + '\'' +
                ", branchName='" + branchName + '\'' +
                ", isGitRepository='" + isGitRepository + '\'' +
                ", isGradleProject='" + isGradleProject + '\'' +
                '}';
    }

    @Override
    public int compareTo(Directory o) {
        return this.name.compareTo(o.name);
    }
}
