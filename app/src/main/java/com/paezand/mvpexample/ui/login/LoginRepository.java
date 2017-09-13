package com.paezand.mvpexample.ui.login;

/**
 * Created by paezand on 9/1/17.
 */

public interface LoginRepository {

    User getUser();

    void setUser(User user);
}
