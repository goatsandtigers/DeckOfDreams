package com.goatsandtigers.deckofdreams.ui.shop;

import android.content.Context;

import com.goatsandtigers.deckofdreams.GameController;
import com.goatsandtigers.deckofdreams.cards.opponentforce.OpponentForceCard;

public class OpponentForceShopRow extends ShopRow {

    public OpponentForceShopRow(Context context, GameController gameController) {
        super(context, OpponentForceCard.listCards(), gameController);
    }
}
