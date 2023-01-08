package com.goatsandtigers.deckofdreams.cards.generosity;

import com.goatsandtigers.deckofdreams.cards.actions.OnDrawGainMerit;

public class Generosity6Card extends GenerosityCard implements OnDrawGainMerit {
    @Override
    public String getName() {
        return "+6";
    }

    @Override
    public String getOnPurchaseText() {
        return null;
    }

    @Override
    public String getOnDrawText() {
        return "Gain 6 moments.";
    }

    @Override
    public int getCost() {
        return 6;
    }

    @Override
    public int getMeritGainedOnDraw() {
        return 6;
    }
}
