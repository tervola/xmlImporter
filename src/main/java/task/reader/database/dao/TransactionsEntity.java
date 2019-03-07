package task.reader.database.dao;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * Created by user on 3/7/2019.
 */
@Entity
@javax.persistence.Table(name = "transactions", schema = "public", catalog = "main_store")
public class TransactionsEntity {
    private int transactionId;
    private String place;
    private BigDecimal amount;
    private String currency;
    private String card;

    @Id
    @Column(name = "transaction_id")
    public int getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(final int transactionId) {
        this.transactionId = transactionId;
    }

    @Basic
    @Column(name = "place")
    public String getPlace() {
        return place;
    }

    public void setPlace(final String place) {
        this.place = place;
    }

    @Basic
    @Column(name = "amount")
    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(final BigDecimal amount) {
        this.amount = amount;
    }

    @Basic
    @Column(name = "currency")
    public String getCurrency() {
        return currency;
    }

    public void setCurrency(final String currency) {
        this.currency = currency;
    }

    @Basic
    @Column(name = "card")
    public String getCard() {
        return card;
    }

    public void setCard(final String card) {
        this.card = card;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final TransactionsEntity that = (TransactionsEntity) o;

        if (transactionId != that.transactionId) return false;
        if (place != null ? !place.equals(that.place) : that.place != null) return false;
        if (amount != null ? !amount.equals(that.amount) : that.amount != null) return false;
        if (currency != null ? !currency.equals(that.currency) : that.currency != null) return false;
        if (card != null ? !card.equals(that.card) : that.card != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = transactionId;
        result = 31 * result + (place != null ? place.hashCode() : 0);
        result = 31 * result + (amount != null ? amount.hashCode() : 0);
        result = 31 * result + (currency != null ? currency.hashCode() : 0);
        result = 31 * result + (card != null ? card.hashCode() : 0);
        return result;
    }
}
