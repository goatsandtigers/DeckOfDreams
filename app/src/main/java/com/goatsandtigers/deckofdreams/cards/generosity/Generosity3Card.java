package com.goatsandtigers.deckofdreams.cards.generosity;

public class Generosity3Card extends GenerosityCard {
    @Override
    public String getName() {
        return "+3";
    }

    @Override
    public String getOnPurchaseText() {
        return null;
    }

    @Override
    public String getOnDrawText() {
        return "Gain 3 moments.";
    }

    @Override
    public int getCost() {
        return 3;
    }
}
