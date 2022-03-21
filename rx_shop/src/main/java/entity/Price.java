package entity;

public class Price {

    Currency currency;
    double value;

    public Price(Currency currency, double value) {
        this.currency = currency;
        this.value = value;
    }

    public Price(String currency, String value) {
        this.currency = Currency.valueOf(currency);
        this.value = Double.parseDouble(value);
    }
}
