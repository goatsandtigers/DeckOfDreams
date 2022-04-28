package com.goatsandtigers.deckofdreams;

import android.content.Context;
import android.graphics.Point;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.goatsandtigers.deckofdreams.card.Card;
import com.goatsandtigers.deckofdreams.card.view.CardView;

import java.util.List;

public class DialogChooseCardFromDream extends LinearLayout {

    private final DialogChooseCardFromDreamListener listener;

    public DialogChooseCardFromDream(Context context, IGameListener gameListener, DialogChooseCardFromDreamListener listener) {
        super(context);
        this.listener = listener;
        Player player = gameListener.getCurrentPlayer();
        List<Card> dream = player.getDream();
        setOrientation(VERTICAL);
        for (Card card : dream) {
            TextView cardView = buildCardView(card, gameListener);
            addView(cardView);
        }
    }

    private TextView buildCardView(Card card, IGameListener gameListener) {
        TextView cardView = new TextView(getContext());
        cardView.setText(card.getName());
        int textColor = CardView.newInstance(getContext(), card, new Point()).getTextColor();
        cardView.setTextColor(textColor);
        cardView.setOnClickListener(e -> listener.onCardChosen(card, gameListener));
        return cardView;
    }

    public interface DialogChooseCardFromDreamListener {
        void onCardChosen(Card card, IGameListener gameListener);
    }
}
