package com.goatsandtigers.deckofdreams.card.view;

import android.content.Context;
import android.graphics.Point;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.goatsandtigers.deckofdreams.card.Card;
import com.goatsandtigers.deckofdreams.card.negative.NegativeCard;
import com.goatsandtigers.deckofdreams.card.neutral.NeutralCard;
import com.goatsandtigers.deckofdreams.card.positive.PositiveCard;

public abstract class CardView extends LinearLayout {

    public static final int RIGHT_MARGIN = 8;

    public CardView(Context context, Card card, Point size) {
        super(context);
        setOrientation(VERTICAL);
        LayoutParams params = new LayoutParams(size.x, size.y);
        params.setMargins(0, 0, RIGHT_MARGIN, 0);
        setLayoutParams(params);
        addNameLabel(card);
        addCostLabel(card);
        if (card.getOnPurchaseText() == null) {
            if (card.getOnDrawText() != null) {
                String onDrawText = "On draw: " + card.getOnDrawText();
                addBodyLabel(onDrawText);
            }
        } else if (card.getOnDrawText() == null) {
            String onPurchaseText = "On purchase: " + card.getOnPurchaseText();
            addBodyLabel(onPurchaseText);
        } else if (card.getOnDrawText().equals(card.getOnPurchaseText())) {
            String text = "On purchase, on draw: " + card.getOnDrawText();
            addBodyLabel(text);
        } else {
            String onPurchaseText = "On purchase: " + card.getOnPurchaseText();
            addBodyLabel(onPurchaseText);
            String onDrawText = "On draw: " + card.getOnDrawText();
            addBodyLabel(onDrawText);
        }
    }

    private void addNameLabel(Card card) {
        TextView tv = new TextView(getContext());
        tv.setText(card.getName());
        tv.setTextColor(getTextColor());
        addView(tv);
    }

    private void addCostLabel(Card card) {
        if (card.getCost() == 0) {
            return;
        }
        TextView tv = new TextView(getContext());
        String text = "Cost: " + card.getCost();
        tv.setText(text);
        tv.setTextColor(getTextColor());
        addView(tv);
    }

    private void addBodyLabel(String text) {
        TextView tv = new TextView(getContext());
        tv.setText(text);
        tv.setTextColor(getTextColor());
        addView(tv);
    }

    public abstract int getTextColor();

    public static CardView newInstance(Context context, Card card, Point size) {
        if (card instanceof PositiveCard) {
            return new PositiveCardView(context, (PositiveCard) card, size);
        } else if (card instanceof NeutralCard) {
            return new NeutralCardView(context, (NeutralCard) card, size);
        } else if (card instanceof NegativeCard) {
            return new NegativeCardView(context, (NegativeCard) card, size);
        } else {
            throw new IllegalStateException("Unexpected card class: " + card.getClass());
        }
    }
}
