package com.goatsandtigers.deckofdreams.ui.shop;

import android.content.Context;

import com.goatsandtigers.deckofdreams.GameController;
import com.goatsandtigers.deckofdreams.cards.generosity.GenerosityCard;

public class GenerosityShopRow extends ShopRow {

    public GenerosityShopRow(Context context, GameController gameController) {
        super(context, GenerosityCard.listCards(), gameController);
    }
}
