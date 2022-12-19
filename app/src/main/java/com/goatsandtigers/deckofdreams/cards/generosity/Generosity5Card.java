package com.goatsandtigers.deckofdreams.cards.generosity;

public class Generosity5Card extends GenerosityCard {
    @Override
    public String getName() {
        return "+5";
    }

    @Override
    public String getOnPurchaseText() {
        return null;
    }

    @Override
    public String getOnDrawText() {
        return "Gain 5 moments.";
    }

    @Override
    public int getCost() {
        return 5;
    }
}
