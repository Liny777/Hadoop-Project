package com.test.wordcount;

import java.util.ArrayList;

public class Keypair {
//    String followee;
    String follower;
    int similarity;

//    public String getFollowee() {
//        return followee;
//    }
//
//    public void setFollowee(String followee) {
//        this.followee = followee;
//    }

    public String getFollower() {
        return follower;
    }

    public void setFollower(String follower) {
        this.follower = follower;
    }

    public int getSimilarity() {
        return similarity;
    }

    public void setSimilarity(int similarity) {
        this.similarity = similarity;
    }

    public Keypair(int similarity, String follower) {
        this.similarity = similarity;
        this.follower = follower;
    }
}
