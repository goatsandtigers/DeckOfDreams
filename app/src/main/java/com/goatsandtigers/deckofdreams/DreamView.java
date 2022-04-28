package com.goatsandtigers.deckofdreams;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;

import com.goatsandtigers.deckofdreams.card.Card;
import com.goatsandtigers.deckofdreams.card.starter.StartingCardPositive;
import com.goatsandtigers.deckofdreams.card.view.CardView;

public class DreamView extends GridView {

    private final Player player;
    private final ListAdapter adapter;

    public DreamView(Context context, Player player) {
        super(context);
        this.player = player;
        setBackgroundColor(Color.YELLOW);
        setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.FILL_PARENT));
        setNumColumns(4);
        setColumnWidth(AUTO_FIT);
        setStretchMode(STRETCH_COLUMN_WIDTH);
        adapter = new BaseAdapter() {
            @Override
            public int getCount() {
                return player.getDream().size();
            }

            @Override
            public Card getItem(int i) {
                return player.getDream().get(i);
            }

            @Override
            public long getItemId(int i) {
                return 0;
            }

            @Override
            public View getView(int i, View view, ViewGroup viewGroup) {
                Card card = getItem(i);
                Point cardSize = new Point(200, 200);
                CardView cardView = CardView.newInstance(getContext(), card, cardSize);
                return cardView;
            }
        };
        setAdapter(adapter);
    }

    public void update() {
        invalidateViews();
    }
}
