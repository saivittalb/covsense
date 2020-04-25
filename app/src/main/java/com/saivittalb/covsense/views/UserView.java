package com.saivittalb.covsense.views;

import com.saivittalb.covsense.models.User;

public class UserView {
    public static void printUser(User user) {
        System.out.println(user.phone);
        System.out.println(user.status);
    }
}
