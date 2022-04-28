package com.goatsandtigers.deckofdreams;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.goatsandtigers.deckofdreams.card.Card;
import com.goatsandtigers.deckofdreams.card.PlayerOutOfCardsException;
import com.goatsandtigers.deckofdreams.shop.ShopView;

public class MainActivity extends AppCompatActivity implements IGameListener {

    private final Game game;

    private ShopView shopView;
    private LinearLayout bodyLayout;
    private LinearLayout playerLayoutContainer;
    private PlayerView playerView;

    public MainActivity() {
        game = new Game();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        createBodyLayout();
        setContentView(bodyLayout);
    }

    private void createBodyLayout() {
        shopView = new ShopView(this, game.getShop(), this);
        playerLayoutContainer = new LinearLayout(this);
        bodyLayout = new LinearLayout(this);
        bodyLayout.setOrientation(LinearLayout.VERTICAL);
        bodyLayout.addView(shopView);
        shopView.setBackgroundColor(Color.GREEN);
        bodyLayout.addView(playerLayoutContainer);
        refreshPlayerLayout();
    }

    @Override
    public void nextDream() {
        game.startNextPlayersTurn();
        refreshPlayerLayout();
    }

    @Override
    public void refreshDream() {
        refreshPlayerLayout();
    }

    @Override
    public Player getCurrentPlayer() {
        return game.getCurrentPlayer();
    }

    @Override
    public void removeCardFromShop(Card card) {
        game.getShop().removeCard(card);
        shopView.updateCards();
    }

    @Override
    public void showMsg(String msg) {
        new AlertDialog.Builder(this)
                .setMessage(msg)
                .setNegativeButton("Ok", null)
                .create()
                .show();
    }

    private void refreshPlayerLayout() {
        playerLayoutContainer.removeAllViews();
        playerView = new PlayerView(this, game.getCurrentPlayer(), this);
        playerLayoutContainer.addView(playerView);
    }
}
