package com.goatsandtigers.deckofdreams.shop;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.view.Display;
import android.widget.LinearLayout;

import com.goatsandtigers.deckofdreams.IGameListener;
import com.goatsandtigers.deckofdreams.Player;
import com.goatsandtigers.deckofdreams.card.Card;
import com.goatsandtigers.deckofdreams.card.negative.NegativeCard;
import com.goatsandtigers.deckofdreams.card.neutral.NeutralCard;
import com.goatsandtigers.deckofdreams.card.positive.PositiveCard;
import com.goatsandtigers.deckofdreams.card.view.CardView;
import com.goatsandtigers.deckofdreams.card.view.NegativeCardView;
import com.goatsandtigers.deckofdreams.card.view.NeutralCardView;
import com.goatsandtigers.deckofdreams.card.view.PositiveCardView;

public class ShopView extends LinearLayout {

    private final Shop shop;
    private final IGameListener gameListener;

    private LinearLayout negativeCardRow;
    private LinearLayout neutralCardRow;
    private LinearLayout positiveCardRow;

    public ShopView(Context context, Shop shop, IGameListener gameListener) {
        super(context);
        this.shop = shop;
        this.gameListener = gameListener;
        setOrientation(VERTICAL);
        createNegativeCardRow(context);
        createNeutralCardRow(context);
        createPositiveCardRow(context);
        updateCards();
    }

    private void createNegativeCardRow(Context context) {
        negativeCardRow = new LinearLayout(context);
        addView(negativeCardRow);
    }

    private void createNeutralCardRow(Context context) {
        neutralCardRow = new LinearLayout(context);
        addView(neutralCardRow);
    }

    private void createPositiveCardRow(Context context) {
        positiveCardRow = new LinearLayout(context);
        addView(positiveCardRow);
    }

    public void updateCards() {
        Point cardSize = getCardSize();
        negativeCardRow.removeAllViews();
        for (NegativeCard negativeCard : shop.getNegativeCards()) {
            NegativeCardView cardView = new NegativeCardView(getContext(), negativeCard, cardSize);
            cardView.setOnClickListener(e -> purchaseCard(negativeCard));
            negativeCardRow.addView(cardView);
        }
        neutralCardRow.removeAllViews();
        for (NeutralCard neutralCard : shop.getNeutralCards()) {
            NeutralCardView cardView = new NeutralCardView(getContext(), neutralCard, cardSize);
            cardView.setOnClickListener(e -> purchaseCard(neutralCard));
            neutralCardRow.addView(cardView);
        }
        positiveCardRow.removeAllViews();
        for (PositiveCard positiveCard : shop.getPositiveCards()) {
            PositiveCardView cardView = new PositiveCardView(getContext(), positiveCard, cardSize);
            cardView.setOnClickListener(e -> purchaseCard(positiveCard));
            positiveCardRow.addView(cardView);
        }
    }

    private void purchaseCard(Card card) {
        if (!canAffordCard(card)) {
            String msg = String.format("Not enough moments remaining to purchase %s.", card.getName());
            gameListener.showMsg(msg);
            return;
        }
        if (card.isAddToDream()) {
            Player player = gameListener.getCurrentPlayer();
            player.deductMoments(card.getCost());
            player.addCardToDream(card);
            gameListener.refreshDream();
            gameListener.removeCardFromShop(card);
        }
        card.performOnPurchaseAction(getContext(), shop, gameListener);
    }

    private boolean canAffordCard(Card card) {
        int momentsRemaining = gameListener.getCurrentPlayer().getNumMoments();
        return momentsRemaining >= card.getCost();
    }

    private Point getCardSize() {
        Display display = ((Activity) getContext()).getWindowManager().getDefaultDisplay();
        Point screenSize = new Point();
        try {
            display.getRealSize(screenSize);
        } catch (NoSuchMethodError err) {
            display.getSize(screenSize);
        }
        int cardWidth = (screenSize.x - CardView.RIGHT_MARGIN) / 3;
        int cardHeight = screenSize.y / 6;
        Point cardSize = new Point(cardWidth, cardHeight);
        return cardSize;
    }

}
