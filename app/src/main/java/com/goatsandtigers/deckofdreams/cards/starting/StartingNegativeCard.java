package com.goatsandtigers.deckofdreams.cards.starting;

import android.graphics.Color;

import com.goatsandtigers.deckofdreams.cards.Card;

public class StartingNegativeCard extends Card {

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
        return "Lose 1 moment.";
    }

    @Override
    public int getColor() {
        return Color.rgb(0.9f, 0f, 0f);
    }

    @Override
    public int getCost() {
        return 0;
    }
}
