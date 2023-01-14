package com.goatsandtigers.deckofdreams.cards.opponentforce;

import com.goatsandtigers.deckofdreams.cards.actions.OnDrawTrashCardTypes;
import com.goatsandtigers.deckofdreams.cards.nonvirtuous.NonVirtuousKillingCard;

import java.util.Arrays;
import java.util.List;

public class OpponentForceLifeSavingCard extends OpponentForceCard implements OnDrawTrashCardTypes {
    @Override
    public String getName() {
        return "Life Saving";
    }

    @Override
    public String getOnPurchaseText() {
        return null;
    }

    @Override
    public String getOnDrawText() {
        return "All killing cards drawn in the current dream are trashed and their effects are ignored.";
    }

    @Override
    public int getCost() {
        return 2;
    }

    @Override
    public List<Class<?>> cardTypesToTrash() {
        return Arrays.asList(NonVirtuousKillingCard.class);
    }
}
