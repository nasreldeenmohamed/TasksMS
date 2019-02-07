package com.blacksquare.tasksms.viewModels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.blacksquare.tasksms.views.activities.TasksActivity;

public class LoginViewModel extends AndroidViewModel {
    public static final String USERNAME = "username1";

    public LoginViewModel(@NonNull Application application) {
        super(application);
    }

    public void LoginUser(String username) {
        if (!username.equals("")) {
            Toast.makeText(getApplication(), "Welcome, " + username, Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getApplication(), TasksActivity.class);
            intent.putExtra("username", username);
            getApplication().getApplicationContext().startActivity(intent);
        } else {
            Toast.makeText(getApplication(), "Username is empty!", Toast.LENGTH_SHORT).show();
        }
    }

}
