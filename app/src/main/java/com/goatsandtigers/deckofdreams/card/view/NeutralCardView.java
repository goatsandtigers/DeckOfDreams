package com.goatsandtigers.deckofdreams.card.view;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;

import com.goatsandtigers.deckofdreams.card.neutral.NeutralCard;

public class NeutralCardView extends CardView {

    public NeutralCardView(Context context, NeutralCard card, Point size) {
        super(context, card, size);
    }

    @Override
    public int getTextColor() {
        return Color.BLACK;
    }
}
