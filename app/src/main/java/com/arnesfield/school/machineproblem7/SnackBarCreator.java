package com.arnesfield.school.machineproblem7;

import android.support.design.widget.Snackbar;
import android.view.View;

/**
 * Created by User on 05/28.
 */

public final class SnackBarCreator {

    private static String message;

    public static void set(String message) {
        SnackBarCreator.message = message;
    }

    public static void show(View view) {
        try {
            if (!(message.isEmpty() || message.matches("[\\s]+")))
                Snackbar.make(view, message, Snackbar.LENGTH_SHORT).show();
        } catch (Exception e) {}
        view = null;
        message = null;
    }
}
