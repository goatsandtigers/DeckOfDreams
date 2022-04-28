package com.goatsandtigers.deckofdreams.card.negative;

import com.goatsandtigers.deckofdreams.card.Card;

public abstract class NegativeCard extends Card {

    public NegativeCard(String name, String onPurchaseText, String onDrawText, int onDrawMomentIncrease) {
        super(name, 0, onPurchaseText, onDrawText, onDrawMomentIncrease);
    }

    public static NegativeCard random() {
        return new NegativeCardKilling();
    }
}
