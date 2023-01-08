package com.goatsandtigers.deckofdreams.ui.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.goatsandtigers.deckofdreams.cards.Card;
import com.goatsandtigers.deckofdreams.player.Player;
import com.goatsandtigers.deckofdreams.ui.card.CardView;

import java.util.HashMap;
import java.util.Map;

public class DiscardPileFragment extends Fragment {

    private LinearLayout root;
    private Player player;

    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        root = new LinearLayout(getContext());
        root.setOrientation(LinearLayout.VERTICAL);
        setPlayer(player);
        return root;
    }

    public void setPlayer(Player player) {
        this.player = player;
        refresh();
    }

    public void refresh() {
        if (root == null) {
            return;
        }

        root.removeAllViews();

        if (player.getDiscardPile().isEmpty()) {
            TextView noCardsTextView = buildTextView("No cards in discard pile.");
            root.addView(noCardsTextView);
            return;
        }

        Map<Class<? extends Card>, Integer> cardCounts = new HashMap<>();
        for (Card card : player.getDiscardPile()) {
            int previousCount = cardCounts.getOrDefault(card.getClass(), Integer.valueOf(0));
            cardCounts.put(card.getClass(), Integer.valueOf(previousCount + 1));
        }
        for (Map.Entry<Class<? extends Card>, Integer> entry : cardCounts.entrySet()) {
            Class<? extends Card> clazz = entry.getKey();
            int count = entry.getValue();
            LinearLayout row = buildRow(clazz, count);
            root.addView(row);
        }
    }

    private LinearLayout buildRow(Class<? extends Card> clazz, int count) {
        try {
            Card card = clazz.newInstance();
            CardView cardView = new CardView(getContext(), card);
            LinearLayout row = new LinearLayout(getContext());
            row.addView(cardView);
            String desc = card.getName() + "\n";
            if (card.getOnPurchaseText() != null) {
                desc += "On purchase: " + card.getOnPurchaseText() + "\n";
            }
            if (card.getOnDrawText() != null) {
                desc += "On draw: " + card.getOnDrawText() + "\n";
            }
            desc += count + " in deck.";
            row.addView(buildTextView(desc));
            return row;
        } catch (IllegalAccessException | java.lang.InstantiationException e) {
            e.printStackTrace();
            return new LinearLayout(getContext());
        }
    }

    private TextView buildTextView(String text) {
        TextView tv = new TextView(getContext());
        tv.setText(text);
        return tv;
    }
}
