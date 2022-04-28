package com.goatsandtigers.deckofdreams.card.neutral;

import com.goatsandtigers.deckofdreams.card.Card;

public abstract class NeutralCard extends Card {

    public NeutralCard(String name, int cost, String onPurchaseText, String onDrawText, int onDrawMomentIncrease) {
        super(name, cost, onPurchaseText, onDrawText, onDrawMomentIncrease);
    }

    public static NeutralCard random() {
        return new NeutralCardPurifyFromCurrentDream();
    }
}
