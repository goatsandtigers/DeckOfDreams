package com.goatsandtigers.deckofdreams.cards.nonvirtuous;

import com.goatsandtigers.deckofdreams.GameController;
import com.goatsandtigers.deckofdreams.cards.actions.OnDrawEndTurn;
import com.goatsandtigers.deckofdreams.cards.actions.OnPurchaseEndTurn;
import com.goatsandtigers.deckofdreams.cards.actions.OnPurchasePurchaseShopRow;

public class NonVirtuousKillingCard extends NonVirtuousCard implements OnPurchasePurchaseShopRow,
        OnPurchaseEndTurn, OnDrawEndTurn {

    @Override
    public String getName() {
        return "Killing";
    }

    @Override
    public String getOnPurchaseText() {
        return "Add all cards of any category from the shop into player's hand.";
    }

    @Override
    public String getOnDrawText() {
        return "You wake up screaming. The dream ends.";
    }

    public String getOnPurchaseMsg(GameController gameController) {
        return "Purchasing Killing card ends turn.";
    }

    @Override
    public String getOnDrawMsg(GameController gameController) {
        return "Drawing Killing card ends turn.";
    }

}
