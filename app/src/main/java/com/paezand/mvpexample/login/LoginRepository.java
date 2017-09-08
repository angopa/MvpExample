package com.paezand.mvpexample.login;

/**
 * Created by paezand on 9/1/17.
 */

public interface LoginRepository {

    User getUser();

    void setUser(User user);
}
