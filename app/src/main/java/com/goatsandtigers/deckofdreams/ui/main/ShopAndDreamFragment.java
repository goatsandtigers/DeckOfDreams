package com.goatsandtigers.deckofdreams.ui.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.goatsandtigers.deckofdreams.GameController;
import com.goatsandtigers.deckofdreams.cards.Card;
import com.goatsandtigers.deckofdreams.player.Player;
import com.goatsandtigers.deckofdreams.player.Turn;
import com.goatsandtigers.deckofdreams.ui.card.DreamView;
import com.goatsandtigers.deckofdreams.ui.player.TurnView;
import com.goatsandtigers.deckofdreams.ui.shop.ShopRow;
import com.goatsandtigers.deckofdreams.ui.shop.ShopView;

import java.util.List;

public class ShopAndDreamFragment extends Fragment {

    private GameController gameController;

    private Turn turn;
    private ShopView shopView;
    private TurnView turnView;
    private DreamView dreamView;

    public ShopAndDreamFragment(GameController gameController) {
        this.gameController = gameController;
    }

    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        LinearLayout root = new LinearLayout(getContext());
        root.setOrientation(LinearLayout.VERTICAL);
        shopView = new ShopView(getContext(), gameController);
        turnView = new TurnView(getContext());
        dreamView = new DreamView(getContext(), new Player(null));
        root.addView(shopView);
        root.addView(turnView);
        root.addView(dreamView);
        if (turn != null) {
            setTurn(turn);
        }
        return root;
    }

    public void setTurn(Turn turn) {
        this.turn = turn;
        if (turnView != null) {
            turnView.setTurn(turn);
            refresh();
        }
        if (shopView != null) {
            shopView.repopulate();
        }
        if (dreamView != null) {
            dreamView.setPlayer(turn.getPlayer());
            dreamView.clear();
        }
    }

    public void refresh() {
        turnView.refresh();
    }

    public void drawCard() {
        if (dreamView != null) {
            dreamView.drawCard();
        }
    }

    public void addCardToDream(Card card) {
        dreamView.addCard(card);
        refresh();
    }

    public List<ShopRow> getShopRows() {
        return shopView.getShopRows();
    }

    public void returnCard(Card card) {
        shopView.addCard(card);
    }

    public void removeLastDreamCard() {
        dreamView.removeLastCard();
    }

    public List<Card> getDreamCards() {
        return dreamView.getDrawnCards();
    }

}
