package com.goatsandtigers.deckofdreams.cards.generosity;

import com.goatsandtigers.deckofdreams.cards.actions.OnDrawGainMerit;

public class Generosity4Card extends GenerosityCard implements OnDrawGainMerit {
    @Override
    public String getName() {
        return "+4";
    }

    @Override
    public String getOnPurchaseText() {
        return null;
    }

    @Override
    public String getOnDrawText() {
        return "Gain 4 moments.";
    }

    @Override
    public int getCost() {
        return 4;
    }

    @Override
    public int getMeritGainedOnDraw() {
        return 4;
    }
}
