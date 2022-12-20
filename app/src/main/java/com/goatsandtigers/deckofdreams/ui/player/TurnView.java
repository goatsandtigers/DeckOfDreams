package com.goatsandtigers.deckofdreams.ui.player;

import android.content.Context;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.goatsandtigers.deckofdreams.player.Turn;

public class TurnView extends LinearLayout {

    private Turn turn;

    public TurnView(Context context) {
        super(context);
        setOrientation(VERTICAL);
    }

    public void setTurn(Turn turn) {
        this.turn = turn;
        refresh();
    }

    public void refresh() {
        removeAllViews();
        addView(buildTextView(turn.getPlayer().getName()));
        String meritString = "Merit: " + turn.getMerit();
        addView(buildTextView(meritString));
        String nonVirtuousActionsPerformed = "Non-virtuous actions performed: " + turn.getNumNonVirtuousCardsTurnedOver();
        addView(buildTextView(nonVirtuousActionsPerformed));
    }

    private TextView buildTextView(String text) {
        TextView tv = new TextView(getContext());
        tv.setText(text);
        return tv;
    }
}
