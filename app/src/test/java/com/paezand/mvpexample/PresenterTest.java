package com.paezand.mvpexample;

import com.paezand.mvpexample.login.LoginActivityMVP;
import com.paezand.mvpexample.login.LoginActivityPresenter;
import com.paezand.mvpexample.login.User;

import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by paezand on 9/11/17.
 */

public class PresenterTest {

    LoginActivityMVP.Model mockLoginModel;
    LoginActivityMVP.View mockLoginView;
    LoginActivityPresenter presenter;
    User user;


    @Before
    public void setup() {

        mockLoginModel = mock(LoginActivityMVP.Model.class);

        user = new User("Fox", "Mulder");

        when(mockLoginModel.getUser()).thenReturn(user);

        mockLoginView = mock(LoginActivityMVP.View.class);

        presenter = new LoginActivityPresenter(mockLoginModel);

        presenter.setView(mockLoginView);

    }

    @Test
    public void noInteractionWithView() {
        presenter.getCurrentUser();

//        verifyZeroInteractions(mockLoginView);
    }

    @Test
    public void loadTheUserFromTheRepositoryWhenValidUserIsPresent() {
        when(mockLoginModel.getUser()).thenReturn(user);

        presenter.getCurrentUser();

        //Verify model interactions
        verify(mockLoginModel, times(1)).getUser();

        //Verify view interactions
        verify(mockLoginView, times(1)).setFirstName("Fox");
        verify(mockLoginView, times(1)).setLastName("Mulder");
        verify(mockLoginView, never()).showUserNotAvailable();
    }

    @Test
    public void shouldShowErrorMessageWhenUSerIsNull() {
        when(mockLoginModel.getUser()).thenReturn(null);

        presenter.getCurrentUser();

        //Verify model interactions
        verify(mockLoginModel, times(1)).getUser();

        //Verify view interactions
        verify(mockLoginView, times(0)).setFirstName("Fox");
        verify(mockLoginView, times(0)).setLastName("Mulder");
        verify(mockLoginView, times(1)).showUserNotAvailable();
    }

    @Test
    public void shouldCreateErrorMessageIfFieldAreEmpty() {
        //Set up the view mock
        when(mockLoginView.getFirstName()).thenReturn("");

        presenter.saveUser();

        verify(mockLoginView, times(1)).getFirstName();
        verify(mockLoginView, never()).getLastName();
        verify(mockLoginView, times(1)).showInputError();

        //Now tell mockView to return a value for first name and an empty last name
        when(mockLoginView.getFirstName()).thenReturn("Diana");
        when(mockLoginView.getLastName()).thenReturn("");

        presenter.saveUser();

        verify(mockLoginView, times(2)).getFirstName(); //Called two times now, once before, and once now
        verify(mockLoginView, times(1)).getLastName(); // Only called once
        verify(mockLoginView, times(2)).showInputError(); //Called two times now, once before, and once now
    }

    @Test
    public void shouldBeAbleToSaveAValidUser() {
        when(mockLoginView.getFirstName()).thenReturn("Dana");
        when(mockLoginView.getLastName()).thenReturn("Scully");

        presenter.saveUser();

        //Called two more times in the saveUser call
        verify(mockLoginView, times(2)).getFirstName();
        verify(mockLoginView, times(2)).getLastName();

        //Make sure the repository save the user
        verify(mockLoginModel, times(1)).createUser("Dana", "Scully");

        //Make sure that teh view showed the user saved message
        verify(mockLoginView, times(1)).showUserSavedMessage();
    }
}
