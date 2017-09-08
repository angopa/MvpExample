package com.paezand.mvpexample.login;

import android.support.annotation.Nullable;

/**
 * Created by paezand on 9/1/17.
 */

public class LoginActivityPresenter implements LoginActivityMVP.Presenter {

    @Nullable
    private LoginActivityMVP.View view;

    private LoginActivityMVP.Model model;

    public LoginActivityPresenter(LoginActivityMVP.Model model) {
        this.model = model;
    }

    @Override
    public void setView(LoginActivityMVP.View view) {
        this.view = view;
    }

    @Override
    public void loginButtonClicked() {
        if (view == null) {
            return;
        }

        if (view.getFirstName().trim().equals("") || view.getLastName().trim().equals("")) {
            view.showInputError();
        } else {
            model.createUser(view.getFirstName(), view.getFirstName());
            view.showUserSavedMessage();
        }
    }

    @Override
    public void getCurrentUser() {
        if (view == null) {
            return;
        }

        final User user = model.getUser();

        if (user == null){
            view.showUserNotAvailable();
        } else {
            view.setFirstName(user.getFirstName());
            view.setLastName(user.getLastName());
        }
    }
}
