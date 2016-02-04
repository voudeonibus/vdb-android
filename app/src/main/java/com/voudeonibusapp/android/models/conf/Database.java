package com.voudeonibusapp.android.models.conf;

import java.util.Date;

import io.realm.RealmObject;

public class Database extends RealmObject {

    private Date last;
    private boolean showTutorial;

    public Date getLast() {
        return last;
    }

    public void setLast(Date last) {
        this.last = last;
    }

    public boolean isShowTutorial() {
        return showTutorial;
    }

    public void setShowTutorial(boolean showTutorial) {
        this.showTutorial = showTutorial;
    }
}
