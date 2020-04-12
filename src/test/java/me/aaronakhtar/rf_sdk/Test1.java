package me.aaronakhtar.rf_sdk;

import me.aaronakhtar.rf_sdk.objects.User;

public class Test1 {

    public static void main(String[] args) throws Exception{
        User user = RaidForumsApi.getInstance().getUser("ThatDevAaron");
        System.out.println(user.getUsername());
        System.out.println(user.getJoinDate());
        System.out.println(user.getTimeSpentOnline());
        System.out.println(user.getTotalPosts());
        System.out.println(user.getTotalThreads());
    }

}
