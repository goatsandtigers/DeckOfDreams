package com.goatsandtigers.deckofdreams.ui.shop;

import android.app.AlertDialog;
import android.content.Context;
import android.view.MotionEvent;
import android.widget.LinearLayout;

import com.goatsandtigers.deckofdreams.GameController;
import com.goatsandtigers.deckofdreams.MsgConstants;
import com.goatsandtigers.deckofdreams.cards.Card;
import com.goatsandtigers.deckofdreams.ui.card.CardView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class ShopRow extends LinearLayout {

    private static final int NUM_CARDS_IN_ROW = 3;

    private final List<Card> allCardsInCategory;
    private final GameController gameController;
    private final List<CardView> cardViewsInRow = new ArrayList<>();

    public ShopRow(Context context, List<Card> allCardsInCategory,  GameController gameController) {
        super(context);
        this.allCardsInCategory = new ArrayList<>(allCardsInCategory);
        this.gameController = gameController;
        repopulate();
    }

    public void repopulate() {
        while (cardViewsInRow.size() < NUM_CARDS_IN_ROW) {
            int r = (int) (Math.random() * allCardsInCategory.size());
            Card card = allCardsInCategory.get(r);
            addCard(card);
        }
    }

    public void addCard(Card card) {
        CardView cardView = new CardView(getContext(), card) {
            @Override
            public boolean onInterceptTouchEvent(MotionEvent ev) {
                return true;
            }

            @Override
            public boolean onTouchEvent(MotionEvent event) {
                if (gameController.isTurnOver()) {
                    gameController.showMsg(MsgConstants.PLEASE_PRESS_END_TURN);
                    return false;
                }

                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    purchaseCard(this);
                }
                return true;
            }
        };
        cardViewsInRow.add(cardView);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(140, 398);
        layoutParams.setMargins(24, 0, 0, 0);
        addView(cardView, layoutParams);
    }

    private void purchaseCard(CardView cardView) {
        Card card = cardView.getCard();
        if (gameController.canAffordCard(card)) {
            removeCard(cardView);
            gameController.purchaseCard(card);
        } else {
            String msg = String.format("The card \"%s\" costs %d.\n\nCurrent merit: %d", card.getName(), card.getCost(), gameController.getCurrentTurn().getMerit());
            showMsg("Unable to purchase", msg);
        }
    }

    public void removeCard(CardView cardView) {
        removeView(cardView);
        cardViewsInRow.remove(cardView);
    }

    public void showMsg(String title, String msg) {
        new AlertDialog.Builder(getContext())
                .setTitle(title)
                .setMessage(msg)
                .setPositiveButton("OK", null)
                .create()
                .show();
    }

    public List<CardView> getCardViewsInRow() {
        return Collections.unmodifiableList(cardViewsInRow);
    }
    
}
