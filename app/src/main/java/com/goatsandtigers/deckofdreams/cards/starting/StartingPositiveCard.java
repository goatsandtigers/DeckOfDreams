package com.goatsandtigers.deckofdreams.cards.starting;

import android.graphics.Color;

import com.goatsandtigers.deckofdreams.cards.Card;

public class StartingPositiveCard extends Card {

    @Override
    public String getName() {
        return "Starting Card";
    }

    @Override
    public String getOnPurchaseText() {
        return null;
    }

    @Override
    public String getOnDrawText() {
        return "Gain 1 moment.";
    }

    @Override
    public int getColor() {
        return Color.rgb(0f, 0f, 1f);
    }

    @Override
    public int getCost() {
        return 0;
    }
}
