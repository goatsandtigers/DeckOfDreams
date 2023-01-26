package com.goatsandtigers.deckofdreams.ui.card;

import android.content.Context;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.goatsandtigers.deckofdreams.R;
import com.goatsandtigers.deckofdreams.cards.Card;

public class CardView extends LinearLayout {

    public static final int WIDTH = 200;

    private final Card card;

    public CardView(Context context, Card card) {
        super(context);
        this.card = card;
        setOrientation(VERTICAL);
        setLayoutParams(new LinearLayout.LayoutParams(WIDTH, 200));
        setPadding(8, 8, 8, 8);
        setBackgroundColor(card.getColor());
        displayCost();
        addView(buildNameTextView());
    }

    private TextView buildNameTextView() {
        TextView tv = new TextView(getContext());
        tv.setText(card.getName());
        return tv;
    }

    private void displayCost() {
        if (card.getCost() > 0) {
            LinearLayout costLayout = new LinearLayout(getContext());
            ImageView costImageView = new ImageView(getContext());
            costImageView.setImageResource(R.drawable.cost);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(50, 50);
            costImageView.setLayoutParams(params);
            costLayout.addView(costImageView);
            TextView tv = new TextView(getContext());
            String costString = String.valueOf(card.getCost());
            tv.setText(costString);
            costLayout.addView(tv);
            addView(costLayout);
        }
    }

    public Card getCard() {
        return card;
    }
}
