package com.goatsandtigers.deckofdreams.ui.popups;

import android.content.Context;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.goatsandtigers.deckofdreams.ui.card.CardView;
import com.goatsandtigers.deckofdreams.ui.shop.ShopRow;

import java.util.List;
import java.util.function.Consumer;

public class SelectCardFromShopView extends OnActionDialog<CardView> {

    public SelectCardFromShopView(Context context, List<ShopRow> showRows, Consumer<CardView> onClose) {
        super(context, onClose);
        LinearLayout body = new LinearLayout(context);
        body.setOrientation(LinearLayout.VERTICAL);
        body.addView(buildInstructionsLabel(context));
        for (ShopRow shopRow : showRows) {
            LinearLayout row = buildRow(shopRow);
            body.addView(row);
        }
        body.addView(buildCancelButton(context, onClose));
        addView(body);
    }

    private TextView buildInstructionsLabel(Context context) {
        TextView tv = new TextView(context);
        tv.setText("Please tap card to purchase.");
        return tv;
    }

    private LinearLayout buildRow(ShopRow shopRow) {
        LinearLayout row = new LinearLayout(getContext());
        for (CardView cardView : shopRow.getCardViewsInRow()) {
            CardView cardViewClone = new CardView(getContext(), cardView.getCard());
            cardViewClone.setOnClickListener(view -> close(cardView));
            row.addView(cardViewClone);
        }
        return row;
    }

    private Button buildCancelButton(Context context, Consumer<CardView> onClose) {
        Button cancelButton = new Button(context);
        cancelButton.setText("Cancel");
        cancelButton.setOnClickListener(view -> close(null));
        return cancelButton;

    }
}
