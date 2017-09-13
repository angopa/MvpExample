package com.paezand.mvpexample.ui.login;

/**
 * Created by paezand on 9/1/17.
 */

public class MemoryRepository implements LoginRepository {

    private User user;

    @Override
    public User getUser() {
        if (user == null) {
            User user = new User("Fox", "Mulder");
            user.setId(0);
            return user;
        } else {
            return user;
        }
    }

    @Override
    public void setUser(User user) {
        if (user == null) {
            user = getUser();
        }
        this.user = user;
    }
}
