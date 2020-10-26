package main.api.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.*;

public class CalendarResponse {

    @JsonProperty("years")
    private Set<String> years;

    @JsonProperty("posts")
    private Map<String, Integer> posts;


    public CalendarResponse(Set<String> years, Map<String, Integer> posts) {
        this.years = years;
        this.posts = posts;
    }

    public Set<String> getYears() {
        return years;
    }

    public void setYears(Set<String> years) {
        this.years = years;
    }

    public Map<String, Integer> getPosts() {
        return posts;
    }

    public void setPosts(Map<String, Integer> posts) {
        this.posts = posts;
    }
}
