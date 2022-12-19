package com.goatsandtigers.deckofdreams.ui.popups;

import android.app.AlertDialog;
import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;

import java.util.function.Consumer;

public class OnActionDialog<T> extends LinearLayout {

    private final Consumer<T> onClose;

    private AlertDialog alertDialog;

    public OnActionDialog(Context context, Consumer<T> onClose) {
        super(context);
        this.onClose = onClose;
    }

    protected void close(T t) {
        onClose.accept(t);
        alertDialog.cancel();
    }

    public void show() {
        alertDialog = new AlertDialog.Builder(getContext())
                .setTitle("Action")
                .setView(this)
                .setCancelable(false)
                .create();
        alertDialog.show();
    }


}
