package com.goatsandtigers.deckofdreams.cards.generosity;

import com.goatsandtigers.deckofdreams.cards.actions.OnDrawPurchaseOneCard;

public class GenerosityGiveFearlessnessCard extends GenerosityCard implements OnDrawPurchaseOneCard {
    @Override
    public String getName() {
        return "Give Fearlessness";
    }

    @Override
    public String getOnPurchaseText() {
        return null;
    }

    @Override
    public String getOnDrawText() {
        return "You help someone overcome their own troubles. Purchase one card for free.";
    }

    @Override
    public int getCost() {
        return 1;
    }
}
