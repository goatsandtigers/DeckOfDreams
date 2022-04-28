package com.goatsandtigers.deckofdreams.card.view;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;

import com.goatsandtigers.deckofdreams.card.negative.NegativeCard;

public class NegativeCardView extends CardView {

    public NegativeCardView(Context context, NegativeCard card, Point size) {
        super(context, card, size);
    }

    @Override
    public int getTextColor() {
        return Color.RED;
    }
}
