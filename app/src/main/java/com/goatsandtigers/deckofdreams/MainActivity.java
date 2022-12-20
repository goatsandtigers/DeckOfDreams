package com.goatsandtigers.deckofdreams;

import android.os.Bundle;

import com.goatsandtigers.deckofdreams.cards.Card;
import com.goatsandtigers.deckofdreams.cards.actions.Action;
import com.goatsandtigers.deckofdreams.cards.actions.ActionOrdinal;
import com.goatsandtigers.deckofdreams.cards.actions.OnPurchaseEndTurn;
import com.goatsandtigers.deckofdreams.cards.actions.OnPurchasePurchaseOneCard;
import com.goatsandtigers.deckofdreams.cards.actions.OnPurchasePurchaseShopRow;
import com.goatsandtigers.deckofdreams.player.Player;
import com.goatsandtigers.deckofdreams.player.Turn;
import com.goatsandtigers.deckofdreams.ui.card.CardView;
import com.goatsandtigers.deckofdreams.ui.main.DeckFragment;
import com.goatsandtigers.deckofdreams.ui.main.ShopAndDreamFragment;
import com.goatsandtigers.deckofdreams.ui.popups.SelectCardFromShopView;
import com.goatsandtigers.deckofdreams.ui.popups.SelectShopRowView;
import com.goatsandtigers.deckofdreams.ui.shop.ShopRow;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import com.goatsandtigers.deckofdreams.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;

public class MainActivity extends AppCompatActivity implements GameController {

    private ActivityMainBinding binding;
    private ShopAndDreamFragment shopAndDreamFragment = new ShopAndDreamFragment(this);
    private DeckFragment deckFragment = new DeckFragment();
    private List<Player> players;
    private int currentPlayerIndex;
    private Turn currentTurn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        newGame();
        deckFragment.setPlayer(players.get(0));

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ViewPager viewPager = binding.viewPager;
        viewPager.setAdapter(new DeckOfDreamsFragmentPagerAdapter(getSupportFragmentManager()));
        TabLayout tabs = binding.tabs;
        tabs.setupWithViewPager(viewPager);
        FloatingActionButton fab = binding.fab;

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shopAndDreamFragment.drawCard();
                /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
            }
        });

        //sectionsPagerAdapter.getDeckFragment().setPlayer(players.get(0));
    }

    private void newGame() {
        players = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            players.add(new Player("Player " + (i + 1)));
        }
        currentPlayerIndex = 0;
        currentTurn = new Turn(getCurrentPlayer());
        updateForNewTurn();
    }

    private void updateForNewTurn() {
        shopAndDreamFragment.setTurn(currentTurn);
        deckFragment.setPlayer(getCurrentPlayer());
    }


    private void nextTurn() {
        currentPlayerIndex++;
        currentPlayerIndex %= players.size();
        currentTurn = new Turn(getCurrentPlayer());
        updateForNewTurn();
    }

    @Override
    public void purchaseCard(Card card) {
        currentTurn.spendMerit(card.getCost());
        addCardToDreamWithoutPaying(card);
    }

    private void addCardToDreamWithoutPaying(Card card) {
        shopAndDreamFragment.addCardToDream(card);
        performOnPurchaseActions(card);
    }

    @Override
    public Turn getCurrentTurn() {
        return currentTurn;
    }

    @Override
    public boolean canAffordCard(Card card) {
        return currentTurn.getMerit() >= card.getCost();
    }

    List<Action> cardActions = Collections.emptyList();

    private void performOnPurchaseActions(Card card) {
        boolean inChain = cardActions != null && !cardActions.isEmpty();
        if (!inChain) {
            cardActions = new ArrayList<>();
        }

        if (card instanceof OnPurchasePurchaseOneCard) {
            cardActions.add(new Action(ActionOrdinal.PURCHASE_ONE_CARD, () -> {
                List<ShopRow> shopRows = shopAndDreamFragment.getShopRows();
                Consumer<CardView> onClose = cardView -> {
                    if (cardView != null) {
                        shopRows.forEach(shopRow -> shopRow.removeCard(cardView));
                        addCardToDreamWithoutPaying(cardView.getCard());
                        nextAction();
                    } else {
                        cancelPurchase(card);
                    }
                };
                new SelectCardFromShopView(this, shopRows, onClose).show();
            }));
        }
        if (card instanceof OnPurchasePurchaseShopRow) {
            cardActions.add(new Action(ActionOrdinal.PURCHASE_SHOP_ROW, () -> {
                List<ShopRow> shopRows = shopAndDreamFragment.getShopRows();
                Consumer<ShopRow> onClose = shopRow -> {
                    if (shopRow != null) {
                        List<CardView> cardViewsInRow = new ArrayList<>(shopRow.getCardViewsInRow());
                        for (CardView cardView : cardViewsInRow) {
                            shopRow.removeCard(cardView);
                            addCardToDreamWithoutPaying(cardView.getCard());
                        }
                        nextAction();
                    } else {
                        cancelPurchase(card);
                        removeAction(ActionOrdinal.END_TURN);
                    }
                };
                new SelectShopRowView(this, shopRows, onClose).show();
            }));
        }
        if (card instanceof OnPurchaseEndTurn) {
            cardActions.add(new Action(ActionOrdinal.END_TURN, () -> {
                nextTurn();
                nextAction();
            }));
        }

        if (!inChain) {
            nextAction();
        }
    }

    private void removeAction(ActionOrdinal actionOrder) {
        Iterator<Action> it = cardActions.iterator();
        while (it.hasNext()) {
            if (it.next().getActionOrder() == actionOrder) {
                it.remove();
            }
        }
    }

    private void cancelPurchase(Card card) {
        shopAndDreamFragment.returnCard(card);
        shopAndDreamFragment.removeLastDreamCard();
    }

    private void nextAction() {
        // Sort chained actions into chronological order
        Collections.sort(cardActions, Comparator.comparingInt(action -> action.getActionOrder().ordinal()));

        // Execute the first action in the chain then remove it from the chain
        if (cardActions != null && !cardActions.isEmpty()) {
            cardActions.remove(0).getRunnable().run();
        }
    }

    private Player getCurrentPlayer() {
        return players.get(currentPlayerIndex);
    }

    public class DeckOfDreamsFragmentPagerAdapter extends FragmentPagerAdapter {

        public DeckOfDreamsFragmentPagerAdapter(@NonNull FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if (position == 0) {
                return shopAndDreamFragment;
            } else {
                return deckFragment;
            }
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return position == 0 ? "Dream" : "Deck";
        }
    }
}