package com.goatsandtigers.deckofdreams.card.view;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;

import com.goatsandtigers.deckofdreams.card.positive.PositiveCard;

public class PositiveCardView extends CardView {

    public PositiveCardView(Context context, PositiveCard card, Point size) {
        super(context, card, size);
    }

    @Override
    public int getTextColor() {
        return Color.BLUE;
    }
}
