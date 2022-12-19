package com.goatsandtigers.deckofdreams.cards;

public abstract class Card {

    public abstract String getName();
    public abstract String getOnPurchaseText();
    public abstract String getOnDrawText();
    public abstract int getColor();
    public abstract int getCost();
}
