package com.goatsandtigers.deckofdreams.cards.nonvirtuous;

public class NonVirtuousStealingCard extends NonVirtuousCard {

    @Override
    public String getName() {
        return "Stealing";
    }

    @Override
    public String getOnPurchaseText() {
        return "Add any card from the shop to the player's hand without paying for it.";
    }

    @Override
    public String getOnDrawText() {
        return "You may not purchase any card for the rest of the dream.";
    }
}
