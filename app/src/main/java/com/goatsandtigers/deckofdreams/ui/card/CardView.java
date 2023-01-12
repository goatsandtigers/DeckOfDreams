package com.goatsandtigers.deckofdreams.ui.card;

import android.content.Context;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.goatsandtigers.deckofdreams.cards.Card;

public class CardView extends LinearLayout {

    public static final int WIDTH = 200;

    private final Card card;

    public CardView(Context context, Card card) {
        super(context);
        this.card = card;
        setLayoutParams(new LinearLayout.LayoutParams(WIDTH, 200));
        setPadding(8, 8, 8, 8);
        setBackgroundColor(card.getColor());
        addView(buildNameTextView());
    }

    private TextView buildNameTextView() {
        TextView tv = new TextView(getContext());
        tv.setText(card.getName());
        return tv;
    }

    public Card getCard() {
        return card;
    }
}
