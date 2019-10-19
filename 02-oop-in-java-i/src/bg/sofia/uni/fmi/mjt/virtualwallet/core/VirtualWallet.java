package bg.sofia.uni.fmi.mjt.virtualwallet.core;

import bg.sofia.uni.fmi.mjt.virtualwallet.core.card.Card;
import bg.sofia.uni.fmi.mjt.virtualwallet.core.payment.PaymentInfo;
import bg.sofia.uni.fmi.mjt.virtualwallet.core.payment.TransactionInfo;

import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;

public class VirtualWallet implements VirtualWalletAPI {

    private static final int TRANSACTION_HISTORY_LIMIT = 10;

    private final Map<String, Card> cards = new HashMap<>();

    private final Queue<TransactionInfo> transactionsHistory = new PriorityQueue<>(TRANSACTION_HISTORY_LIMIT);

    @Override
    public boolean registerCard(Card cardToAdd) {
        if (this.cards.size() >= 5) {
            return false;
        }

        if (cardToAdd == null || cardToAdd.getName() == null ||
                this.cards.containsKey(cardToAdd.getName())) {
            return false;
        }

        this.cards.put(cardToAdd.getName(), cardToAdd);
        return true;
    }

    @Override
    public boolean executePayment(Card card, PaymentInfo paymentInfo) {
        if (card == null || paymentInfo == null) {
            return false;
        }

        boolean isSuccessfulPayment = card.executePayment(paymentInfo.getCost());
        if (isSuccessfulPayment) {
            TransactionInfo transaction = new TransactionInfo(card.getName(), paymentInfo);
            this.addToHistory(transaction);
        }

        return isSuccessfulPayment;
    }

    @Override
    public boolean feed(Card card, double amount) {
        if (card == null || amount < 0) {
            return false;
        }

        if (!this.cards.containsKey(card.getName())) {
            return false;
        }

        card.setAmount(card.getAmount() + amount);
        return true;
    }

    @Override
    public Card getCardByName(String name) {
        return this.cards.get(name);
    }

    @Override
    public int getTotalNumberOfCards() {
        return this.cards.size();
    }

    private void addToHistory(TransactionInfo transactionInfo) {
        if (this.transactionsHistory.size() == TRANSACTION_HISTORY_LIMIT) {
            System.out.println(this.transactionsHistory.poll());
        }

        this.transactionsHistory.add(transactionInfo);
    }
}
