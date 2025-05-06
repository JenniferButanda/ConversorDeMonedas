import com.google.gson.JsonObject;

public class Converter {
    private String from;
    private String to;
    private double amount;
    private JsonObject rates;

    public void setFrom(String from) {
        this.from = from;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public double getAmount() {
        return amount;
    }

    public double convert(String from, String to, double amount, JsonObject rates) {
        double tasaFrom = rates.get(from).getAsDouble();
        double tasaTo = rates.get(to).getAsDouble();

        double originalAmount = amount / tasaFrom;
        return originalAmount * tasaTo;
    }
}
