package com.goatsandtigers.deckofdreams.cards.generosity;

public class Generosity2Card extends GenerosityCard {

    @Override
    public String getName() {
        return "+2";
    }

    @Override
    public String getOnPurchaseText() {
        return null;
    }

    @Override
    public String getOnDrawText() {
        return "Gain 2 moments";
    }

    @Override
    public int getCost() {
        return 2;
    }
}
