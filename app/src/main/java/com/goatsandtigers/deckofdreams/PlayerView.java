package com.goatsandtigers.deckofdreams;

import android.app.AlertDialog;
import android.content.Context;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.goatsandtigers.deckofdreams.card.PlayerOutOfCardsException;

public class PlayerView extends LinearLayout {

    private final Player player;
    private final IGameListener gameListener;

    private LinearLayout statsLayout;
    private TextView playerNameTextView;
    private TextView numMomentsTextView;
    private TextView numNegativeActionsAllowedTextView;
    private TextView numCardsInDeckTextView;
    private TextView numCardsInDiscardPileTextView;
    private LinearLayout buttonLayout;
    private Button drawCardButton;
    private Button endTurnButton;
    private DreamView dreamView;

    public PlayerView(Context context, Player player, IGameListener gameListener) {
        super(context);
        this.player = player;
        this.gameListener = gameListener;
        setOrientation(VERTICAL);
        buildStatsLayout();
        buildButtons();
        LinearLayout topRow = new LinearLayout(context);
        topRow.addView(statsLayout);
        topRow.addView(buttonLayout);
        addView(topRow);
        addDreamView();
        onChange();
    }

    private void buildStatsLayout() {
        statsLayout = new LinearLayout(getContext());
        statsLayout.setOrientation(VERTICAL);
        addPlayerNameTextView();
        addNumMomentsTextView();
        addNumNegativeActionsAllowedTextView();
        addNumCardsInDeckTextView();
        addNumCardsInDiscardPileTextView();
    }

    private void addPlayerNameTextView() {
        playerNameTextView = new TextView(getContext());
        playerNameTextView.setText(player.getName());
        statsLayout.addView(playerNameTextView);
    }

    private void addNumMomentsTextView() {
        numMomentsTextView = new TextView(getContext());
        statsLayout.addView(numMomentsTextView);
    }

    private void addNumNegativeActionsAllowedTextView() {
        numNegativeActionsAllowedTextView = new TextView(getContext());
        statsLayout.addView(numNegativeActionsAllowedTextView);
    }

    private void addNumCardsInDeckTextView() {
        numCardsInDeckTextView = new TextView(getContext());
        statsLayout.addView(numCardsInDeckTextView);
    }

    private void addNumCardsInDiscardPileTextView() {
        numCardsInDiscardPileTextView = new TextView(getContext());
        statsLayout.addView(numCardsInDiscardPileTextView);
    }

    private void buildButtons() {
        buttonLayout = new LinearLayout(getContext());
        drawCardButton = new Button(getContext());
        drawCardButton.setText("Draw card");
        drawCardButton.setOnClickListener(e -> drawCard());
        buttonLayout.addView(drawCardButton);

        endTurnButton = new Button(getContext());
        endTurnButton.setText("End Turn");
        endTurnButton.setOnClickListener(e -> endTurn());
        buttonLayout.addView(endTurnButton);
    }

    private void drawCard() {
        try {
            player.drawCard();
            onChange();
        } catch (NoMomentsRemainingException e) {
            showMsg("It costs one moment to draw a card. You have no moments left. Please either perform a negative action or press \"End turn\".");
        } catch (PlayerOutOfCardsException e) {
            if (player.getDiscardPile().isEmpty()) {
                showMsg("You have no more cards to play. Please press \"End turn\"");
            } else {
                player.addDiscardPileToDeck();
                drawCard();
            }
        }
    }

    private void endTurn() {
        int numUnusedCards = player.getDeck().size() + player.getDiscardPile().size();
        if (player.getNumMoments() == 0 || numUnusedCards == 0) {
            gameListener.nextDream();
        } else if (!player.getDeck().isEmpty() || !player.getDiscardPile().isEmpty()) {
            showMsg("You have " + player.getNumMoments() + " moments left. Please press \"Draw card\".");
        }
    }

    private void addDreamView() {
        dreamView = new DreamView(getContext(), player);
        addView(dreamView);
    }

    private void onChange() {
        String numMomentsText = "Remaining moments: " + player.getNumMoments();
        numMomentsTextView.setText(numMomentsText);

        String numNegativeActionsAllowedText = "Negative actions allowed: " + player.getNumNegativeActionsAllowed();
        numNegativeActionsAllowedTextView.setText(numNegativeActionsAllowedText);

        String numCardsInDeckText = "Cards left in deck: " + player.getDeck().size();
        numCardsInDeckTextView.setText(numCardsInDeckText);

        String numCardsInDiscardPileText = "Cards in discard pile: " + player.getDiscardPile().size();
        numCardsInDiscardPileTextView.setText(numCardsInDiscardPileText);

        dreamView.update();
    }

    private void showMsg(String msg) {
        new AlertDialog.Builder(getContext())
                .setMessage(msg)
                .setTitle("Dialog Box")
                .setNegativeButton("OK", null)
                .create()
                .show();
    }
}
