package com.goatsandtigers.deckofdreams.cards.generosity;

import com.goatsandtigers.deckofdreams.cards.actions.OnDrawGainMerit;

public class Generosity3Card extends GenerosityCard implements OnDrawGainMerit {
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

    @Override
    public int getMeritGainedOnDraw() {
        return 3;
    }
}
