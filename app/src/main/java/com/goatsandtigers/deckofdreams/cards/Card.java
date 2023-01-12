package com.goatsandtigers.deckofdreams.cards;

import com.goatsandtigers.deckofdreams.GameController;

public abstract class Card {

    public abstract String getName();
    public abstract String getOnPurchaseText();
    public abstract String getOnDrawText();
    public abstract int getColor();
    public abstract int getCost();

    public String getOnPurchaseMsg(GameController gameController) {
        return "Purchased " + getName() + ".";
    }

    public String getOnDrawMsg(GameController gameController) {
        return null;
    }
}
