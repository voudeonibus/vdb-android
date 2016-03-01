package com.voudeonibus.views.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import com.voudeonibus.R;
import com.voudeonibus.models.api.Trip;
import com.voudeonibus.views.components.DialogAddCategory;
import com.voudeonibus.views.components.TripItem;

public class ModalUtils {

    public static void setCategory(final Context context,  final Trip trip, TripItem tripItem) {
        DialogAddCategory dialog = new DialogAddCategory(context, trip, tripItem);
        dialog.show();
    }

    public static void confirmation(Context context, String title, String message, DialogInterface.OnClickListener clickListener) {
        new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(R.string.modal_yes, clickListener)
                .setNegativeButton(R.string.modal_no, null)
                .show();
    }

    public static void confirmation(Context context, String message, DialogInterface.OnClickListener clickListener) {
        confirmation(context, context.getString(R.string.modal_title_alert), message, clickListener);
    }




}
