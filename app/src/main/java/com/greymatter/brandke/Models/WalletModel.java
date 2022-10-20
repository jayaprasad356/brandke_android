package Models;

public class WalletModel {

    private String Amount;
    private String Date;
    private String Status;

    public WalletModel(String amount, String date, String status) {
        Amount = amount;
        Date = date;
        Status = status;
    }

    public String getAmount() {
        return Amount;
    }

    public void setAmount(String amount) {
        Amount = amount;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }
}
