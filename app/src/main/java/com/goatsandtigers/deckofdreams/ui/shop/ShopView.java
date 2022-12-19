package com.goatsandtigers.deckofdreams.ui.shop;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;

import com.goatsandtigers.deckofdreams.GameController;
import com.goatsandtigers.deckofdreams.cards.Card;
import com.goatsandtigers.deckofdreams.cards.generosity.GenerosityCard;
import com.goatsandtigers.deckofdreams.cards.nonvirtuous.NonVirtuousCard;

import java.util.Arrays;
import java.util.List;

public class ShopView extends LinearLayout {

    private static final int NUM_CARDS_IN_SHOP_ROW = 3;

    private GenerosityShopRow generosityShopRow;
    private NonVirtuousShopRow nonVirtuousShopRow;

    public ShopView(Context context, GameController gameController) {
        super(context);
        setOrientation(VERTICAL);
        generosityShopRow = new GenerosityShopRow(context, gameController);
        nonVirtuousShopRow = new NonVirtuousShopRow(context, gameController);
        addView(buildRow(generosityShopRow, nonVirtuousShopRow));
    }

    private LinearLayout buildRow(View... views) {
        LinearLayout row = new LinearLayout(getContext());
        for (View view : views) {
            row.addView(view);
        }
        return row;
    }

    public List<ShopRow> getShopRows() {
        return Arrays.asList(generosityShopRow, nonVirtuousShopRow);
    }

    public void repopulate() {
        generosityShopRow.repopulate();
        nonVirtuousShopRow.repopulate();
    }

    public void addCard(Card card) {
        if (card instanceof GenerosityCard) {
            generosityShopRow.addCard(card);
        } else if (card instanceof NonVirtuousCard) {
            nonVirtuousShopRow.addCard(card);
        }
    }
}
