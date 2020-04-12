package me.aaronakhtar.rf_sdk.objects;

public class User {

    private String username;
    private String joinDate;
    private String timeSpentOnline;
    private String totalPosts;
    private String totalThreads;

    public User(String username, String joinDate, String timeSpentOnline, String totalPosts, String totalThreads) {
        this.username = username;
        this.joinDate = joinDate;
        this.timeSpentOnline = timeSpentOnline;
        this.totalPosts = totalPosts;
        this.totalThreads = totalThreads;
    }

    public String getUsername() {
        return username;
    }

    public String getJoinDate() {
        return joinDate;
    }

    public String getTimeSpentOnline() {
        return timeSpentOnline;
    }

    public String getTotalPosts() {
        return totalPosts;
    }

    public String getTotalThreads() {
        return totalThreads;
    }
}
