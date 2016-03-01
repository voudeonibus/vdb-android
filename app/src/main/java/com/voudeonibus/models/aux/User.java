package com.voudeonibus.models.aux;

import io.realm.RealmObject;

public class User extends RealmObject {

    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
