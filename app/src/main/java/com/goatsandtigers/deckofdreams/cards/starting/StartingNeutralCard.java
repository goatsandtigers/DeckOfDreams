package com.goatsandtigers.deckofdreams.cards.starting;

import android.graphics.Color;

import com.goatsandtigers.deckofdreams.cards.Card;

public class StartingNeutralCard extends Card {

    @Override
    public String getName() {
        return "Starting card";
    }

    @Override
    public String getOnPurchaseText() {
        return null;
    }

    @Override
    public String getOnDrawText() {
        return null;
    }

    @Override
    public int getColor() {
        return Color.rgb(0.9f, 0.9f, 0.9f);
    }

    @Override
    public int getCost() {
        return 0;
    }
}
