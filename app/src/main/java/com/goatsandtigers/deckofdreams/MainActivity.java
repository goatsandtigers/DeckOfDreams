package com.goatsandtigers.deckofdreams;

import android.app.AlertDialog;
import android.os.Bundle;
import android.util.DisplayMetrics;

import com.goatsandtigers.deckofdreams.cards.Card;
import com.goatsandtigers.deckofdreams.cards.actions.Action;
import com.goatsandtigers.deckofdreams.cards.actions.ActionOrdinal;
import com.goatsandtigers.deckofdreams.cards.actions.OnDrawEndTurn;
import com.goatsandtigers.deckofdreams.cards.actions.OnDrawGainMerit;
import com.goatsandtigers.deckofdreams.cards.actions.OnDrawMayNotPurchase;
import com.goatsandtigers.deckofdreams.cards.actions.OnDrawPurchaseOneCard;
import com.goatsandtigers.deckofdreams.cards.actions.OnDrawTrashCardTypes;
import com.goatsandtigers.deckofdreams.cards.actions.OnPurchaseEndTurn;
import com.goatsandtigers.deckofdreams.cards.actions.OnPurchasePurchaseOneCard;
import com.goatsandtigers.deckofdreams.cards.actions.OnPurchasePurchaseShopRow;
import com.goatsandtigers.deckofdreams.player.Player;
import com.goatsandtigers.deckofdreams.player.Turn;
import com.goatsandtigers.deckofdreams.ui.BitmapUtils;
import com.goatsandtigers.deckofdreams.ui.CardStringUtils;
import com.goatsandtigers.deckofdreams.ui.card.CardView;
import com.goatsandtigers.deckofdreams.ui.main.DeckFragment;
import com.goatsandtigers.deckofdreams.ui.main.DiscardPileFragment;
import com.goatsandtigers.deckofdreams.ui.main.ShopAndDreamFragment;
import com.goatsandtigers.deckofdreams.ui.popups.SelectCardFromShopView;
import com.goatsandtigers.deckofdreams.ui.popups.SelectShopRowView;
import com.goatsandtigers.deckofdreams.ui.shop.ShopRow;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

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
    private DiscardPileFragment discardPileFragment = new DiscardPileFragment();
    private List<Player> players;
    private int currentPlayerIndex;
    private Turn currentTurn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        newGame();
        deckFragment.setPlayer(players.get(0));
        discardPileFragment.setPlayer(players.get(0));

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ViewPager viewPager = binding.viewPager;
        viewPager.setAdapter(new DeckOfDreamsFragmentPagerAdapter(getSupportFragmentManager()));
        TabLayout tabs = binding.tabs;
        tabs.setupWithViewPager(viewPager);
        FloatingActionButton fab = binding.fab;

        fab.setOnClickListener(view -> {
            if (isTurnOver()) {
                showMsg(MsgConstants.PLEASE_PRESS_END_TURN);
                return;
            }

            if (currentTurn.getMerit() >= 1) {
                Card card = shopAndDreamFragment.drawCard();
                if (card == null) {
                    showMsg(MsgConstants.NO_CARDS_TO_DRAW);
                    waitForPlayerToEndTurn();
                    return;
                }
                if (shouldTrashOnDraw(card)) {
                    shopAndDreamFragment.removeLastDreamCard();
                    trashCard(card);
                    return;
                }
                currentTurn.spendMerit(1);
                shopAndDreamFragment.refresh();
                deckFragment.refresh();
                showMsg("Card drawn. 1 merit spent.");
                performOnDrawActions(card);
            } else {
                showMsg("Unable to draw card. 1 merit required.");
            }
        });
        fab.setImageBitmap(BitmapUtils.textAsBitmap("Draw"));

        FloatingActionButton endTurnButton = binding.endTurn;
        endTurnButton.setOnClickListener(view -> {
            if (!currentTurn.isOver() && currentTurn.getMerit() > 0 && !getCurrentPlayer().getDeck().isEmpty()) {
                showMsg("Turn cannot end while merit > 0 and cards in deck.");
            } else {
                nextTurn();
            }
        });
        endTurnButton.setImageBitmap(BitmapUtils.textAsBitmap("End"));

        //sectionsPagerAdapter.getDeckFragment().setPlayer(players.get(0));
    }

    private boolean shouldTrashOnDraw(Card card) {
        for (Card dreamCard : shopAndDreamFragment.getDreamCards()) {
            if (dreamCard instanceof OnDrawTrashCardTypes) {
                List<Class<?>> cardsTypesToTrash = ((OnDrawTrashCardTypes) dreamCard).cardTypesToTrash();
                if (cardsTypesToTrash.contains(card.getClass())) {
                    return true;
                }
            }
        }
        return false;
    }

    private void trashCard(Card card) {
        String cardName = card.getName();
        String cardCausingTrash = "Opponent Force";
        for (Card dreamCard : shopAndDreamFragment.getDreamCards()) {
            if (dreamCard instanceof OnDrawTrashCardTypes) {
                List<Class<?>> cardsTypesToTrash = ((OnDrawTrashCardTypes) dreamCard).cardTypesToTrash();
                if (cardsTypesToTrash.contains(card.getClass())) {
                    cardCausingTrash = dreamCard.getName();
                }
            }
        }
        String msg = String.format("%s card trashed because the dream contains %s.", cardName, cardCausingTrash);
        showMsg(msg);
    }

    public void showMsg(String msg) {
        if (msg == null) {
            return;
        }

        Snackbar.make(binding.getRoot(), msg, Snackbar.LENGTH_LONG)
                .show();
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
        discardPileFragment.setPlayer(getCurrentPlayer());
    }

    private void nextTurn() {
        moveDreamCardsToDiscardPile();
        currentPlayerIndex++;
        currentPlayerIndex %= players.size();
        currentTurn = new Turn(getCurrentPlayer());
        updateForNewTurn();
    }

    private void moveDreamCardsToDiscardPile() {
        List<Card> dreamCards = shopAndDreamFragment.getDreamCards();
        dreamCards.forEach(getCurrentPlayer()::addCardToDiscardPile);
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
        showMsg(card.getOnPurchaseMsg(this));

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
                waitForPlayerToEndTurn();
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
        String msg = "Cancelled purchase of " + card.getName() + ".";
        showMsg(msg);

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

    private void performOnDrawActions(Card card) {
        showMsg(card.getOnDrawMsg(this));

        boolean inChain = cardActions != null && !cardActions.isEmpty();
        if (!inChain) {
            cardActions = new ArrayList<>();
        }

        if (card instanceof OnDrawGainMerit) {
            cardActions.add(new Action(ActionOrdinal.GAIN_MERIT, () -> {
                int gainedMerit = ((OnDrawGainMerit) card).getMeritGainedOnDraw();
                getCurrentTurn().gainMerit(gainedMerit);
                shopAndDreamFragment.refresh();
                showMsg(gainedMerit + " merit gained.");
            }));
        }

        if (card instanceof OnDrawPurchaseOneCard) {
            addPurchaseOneCardAction();
        }

        if (card instanceof OnDrawTrashCardTypes) {
            List<Class<?>> cardTypesToTrash = cardTypesToTrash();
            String cardTypeNamesToTrash = CardStringUtils.joinCardTypeNames(cardTypesToTrash);
            String msg = String.format("All %s cards drawn this turn will have no effect and will be trashed.", cardTypeNamesToTrash);
            showMsg(msg);
        }

        if (card instanceof OnDrawEndTurn) {
            cardActions.add(new Action(ActionOrdinal.END_TURN, () -> {
                waitForPlayerToEndTurn();
                nextAction();
            }));
        }

        if (!inChain) {
            nextAction();
        }
    }

    private void addPurchaseOneCardAction() {
        cardActions.add(new Action(ActionOrdinal.PURCHASE_ONE_CARD, () -> {
            List<ShopRow> shopRows = shopAndDreamFragment.getShopRows();
            Consumer<CardView> onClose = cardView -> {
                if (cardView != null) {
                    shopRows.forEach(shopRow -> shopRow.removeCard(cardView));
                    addCardToDreamWithoutPaying(cardView.getCard());
                    nextAction();
                } else {
                    new AlertDialog.Builder(this)
                            .setMessage("Are you sure you don't want to purchase a card for free?")
                            .setPositiveButton("Yes I don't want to", null)
                            .setNegativeButton("No I want to purchase a card for free", (a, b) -> {
                                addPurchaseOneCardAction();
                                nextAction();
                            }).show();
                }
            };
            new SelectCardFromShopView(this, shopRows, onClose).show();
        }));
    }

    private Player getCurrentPlayer() {
        return players.get(currentPlayerIndex);
    }

    private void waitForPlayerToEndTurn() {
        if (currentTurn != null) {
            currentTurn.setOver(true);
        }
    }

    public boolean isTurnOver() {
        return currentTurn != null && currentTurn.isOver();
    }

    @Override
    public Card getPurchaseNotAllowedCardInDream() {
        for (Card card : shopAndDreamFragment.getDreamCards()) {
            if (card instanceof OnDrawMayNotPurchase) {
                return card;
            }
        }
        return null;
    }

    @Override
    public List<Class<?>> cardTypesToTrash() {
        List<Class<?>> result = new ArrayList<>();
        for (Card card : shopAndDreamFragment.getDreamCards()) {
            if (card instanceof OnDrawTrashCardTypes) {
                List<Class<?>> cardsTypesToTrash = ((OnDrawTrashCardTypes) card).cardTypesToTrash();
                result.addAll(cardsTypesToTrash);
            }
        }
        return result;
    }

    @Override
    public DisplayMetrics getDisplayMetrics() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics;
    }

    public class DeckOfDreamsFragmentPagerAdapter extends FragmentPagerAdapter {

        public DeckOfDreamsFragmentPagerAdapter(@NonNull FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0: return shopAndDreamFragment;
                case 1: return deckFragment;
                default: return discardPileFragment;
            }
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0: return "Dream";
                case 1: return "Deck";
                default: return "Discard Pile";
            }
        }
    }
}