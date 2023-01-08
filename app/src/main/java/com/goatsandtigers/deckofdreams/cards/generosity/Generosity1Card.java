package com.goatsandtigers.deckofdreams.cards.generosity;

import com.goatsandtigers.deckofdreams.cards.actions.OnDrawGainMerit;

public class Generosity1Card extends GenerosityCard implements OnDrawGainMerit {

    @Override
    public String getName() {
        return "+1";
    }

    @Override
    public String getOnPurchaseText() {
        return null;
    }

    @Override
    public String getOnDrawText() {
        return "Gain 1 moment.";
    }

    @Override
    public int getCost() {
        return 1;
    }

    @Override
    public int getMeritGainedOnDraw() {
        return 1;
    }
}
