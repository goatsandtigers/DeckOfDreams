package com.goatsandtigers.deckofdreams.ui.popups;

import android.content.Context;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.goatsandtigers.deckofdreams.ui.card.CardView;
import com.goatsandtigers.deckofdreams.ui.shop.ShopRow;

import java.util.List;
import java.util.function.Consumer;

public class SelectShopRowView extends OnActionDialog<ShopRow> {

    public SelectShopRowView(Context context, List<ShopRow> showRows, Consumer<ShopRow> onClose) {
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
        tv.setText("Please tap row to purchase.");
        return tv;
    }

    private LinearLayout buildRow(ShopRow shopRow) {
        LinearLayout row = new LinearLayout(getContext());
        for (CardView cardView : shopRow.getCardViewsInRow()) {
            row.addView(new CardView(getContext(), cardView.getCard()));
        }
        row.setOnClickListener(view -> close(shopRow));
        return row;
    }

    private Button buildCancelButton(Context context, Consumer<ShopRow> onClose) {
        Button cancelButton = new Button(context);
        cancelButton.setText("Cancel");
        cancelButton.setOnClickListener(view -> close(null));
        return cancelButton;

    }
}
