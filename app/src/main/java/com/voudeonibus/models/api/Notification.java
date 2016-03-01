package com.voudeonibus.models.api;

import io.realm.RealmList;
import io.realm.RealmObject;

public class Notification extends RealmObject {

    private Line line;

    private RealmList<DetailNotification> detailNotifications;

    public Line getLine() {
        return line;
    }

    public void setLine(Line line) {
        this.line = line;
    }

    public RealmList<DetailNotification> getDetailNotifications() {
        return detailNotifications;
    }

    public void setDetailNotifications(RealmList<DetailNotification> detailNotifications) {
        this.detailNotifications = detailNotifications;
    }
}
