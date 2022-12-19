package com.goatsandtigers.deckofdreams.ui.shop;

import android.content.Context;

import com.goatsandtigers.deckofdreams.GameController;
import com.goatsandtigers.deckofdreams.cards.nonvirtuous.NonVirtuousCard;

public class NonVirtuousShopRow extends ShopRow {

    public NonVirtuousShopRow(Context context, GameController gameController) {
        super(context, NonVirtuousCard.listCards(), gameController);
    }
}
