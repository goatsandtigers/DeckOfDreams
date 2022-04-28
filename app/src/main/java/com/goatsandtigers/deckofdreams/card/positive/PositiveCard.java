package com.goatsandtigers.deckofdreams.card.positive;

import com.goatsandtigers.deckofdreams.card.Card;

public abstract class PositiveCard extends Card {

    public PositiveCard(String name, int cost, String onPurchaseText, String onDrawText, int onDrawMomentIncrease, boolean addToDream) {
        super(name, cost, onPurchaseText, onDrawText, onDrawMomentIncrease, addToDream);
    }

    public static PositiveCard random() {
        return new PositiveCardGain4Moments();
    }
}
