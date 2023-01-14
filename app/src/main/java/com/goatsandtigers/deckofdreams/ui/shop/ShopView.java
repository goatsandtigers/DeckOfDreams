package com.goatsandtigers.deckofdreams.ui.shop;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;

import com.goatsandtigers.deckofdreams.GameController;
import com.goatsandtigers.deckofdreams.cards.Card;
import com.goatsandtigers.deckofdreams.cards.generosity.GenerosityCard;
import com.goatsandtigers.deckofdreams.cards.nonvirtuous.NonVirtuousCard;
import com.goatsandtigers.deckofdreams.cards.opponentforce.OpponentForceCard;

import java.util.Arrays;
import java.util.List;

public class ShopView extends LinearLayout {

    private GenerosityShopRow generosityShopRow;
    private NonVirtuousShopRow nonVirtuousShopRow;
    private OpponentForceShopRow opponentForceShopRow;

    public ShopView(Context context, GameController gameController) {
        super(context);
        setOrientation(VERTICAL);
        generosityShopRow = new GenerosityShopRow(context, gameController);
        nonVirtuousShopRow = new NonVirtuousShopRow(context, gameController);
        opponentForceShopRow = new OpponentForceShopRow(context, gameController);
        addView(buildRow(generosityShopRow, nonVirtuousShopRow));
        addView(buildRow(opponentForceShopRow));
    }

    private LinearLayout buildRow(View... views) {
        LinearLayout row = new LinearLayout(getContext());
        for (View view : views) {
            row.addView(view);
        }
        return row;
    }

    public List<ShopRow> getShopRows() {
        return Arrays.asList(generosityShopRow, nonVirtuousShopRow, opponentForceShopRow);
    }

    public void repopulate() {
        generosityShopRow.repopulate();
        nonVirtuousShopRow.repopulate();
        opponentForceShopRow.repopulate();
    }

    public void addCard(Card card) {
        if (card instanceof GenerosityCard) {
            generosityShopRow.addCard(card);
        } else if (card instanceof NonVirtuousCard) {
            nonVirtuousShopRow.addCard(card);
        } else if (card instanceof OpponentForceCard) {
            opponentForceShopRow.addCard(card);
        }
    }
}
