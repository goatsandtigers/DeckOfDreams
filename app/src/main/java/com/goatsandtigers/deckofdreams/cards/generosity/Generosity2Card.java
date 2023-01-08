package com.goatsandtigers.deckofdreams.cards.generosity;

import com.goatsandtigers.deckofdreams.cards.actions.OnDrawGainMerit;

public class Generosity2Card extends GenerosityCard implements OnDrawGainMerit {

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

    @Override
    public int getMeritGainedOnDraw() {
        return 2;
    }
}
