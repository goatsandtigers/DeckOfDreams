package com.goatsandtigers.deckofdreams.card.neutral;

import android.app.AlertDialog;
import android.content.Context;

import com.goatsandtigers.deckofdreams.DialogChooseCardFromDream;
import com.goatsandtigers.deckofdreams.IGameListener;
import com.goatsandtigers.deckofdreams.Player;
import com.goatsandtigers.deckofdreams.card.Card;
import com.goatsandtigers.deckofdreams.shop.Shop;

public class NeutralCardPurifyFromCurrentDream extends NeutralCard implements DialogChooseCardFromDream.DialogChooseCardFromDreamListener {

    private AlertDialog dialog;

    public NeutralCardPurifyFromCurrentDream() {
        super("Purify",
                2,
                "Trash any card in current dream then trash this card.",
                null,
                0);
    }
    @Override
    public void performOnPurchaseAction(Context context, Shop shop, IGameListener gameListener) {
        // TODO show popup of possible cards to trash
        Player player = gameListener.getCurrentPlayer();
        DialogChooseCardFromDream view = new DialogChooseCardFromDream(context, gameListener, this);
        dialog = new AlertDialog.Builder(context)
                .setView(view)
                .setTitle("Dialog Box")
                .setNegativeButton("Cancel", null)
                .create();
        dialog.show();
    }

    @Override
    public void onCardChosen(Card card, IGameListener gameListener) {
        Player player = gameListener.getCurrentPlayer();
        player.removeCardFromDream(card);
        player.deductMoments(getCost());
        gameListener.removeCardFromShop(this);
        gameListener.refreshDream();
        dialog.dismiss();
    }
}
