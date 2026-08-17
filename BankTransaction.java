import java.time.LocalDateTime;

public class BankTransaction {

    public enum TransactionType {
        TRANSFER, BILL_PAYMENT, TOP_UP, INTER_BANK_TRANSFER, REVERSAL
    }

    public enum Status {
        PENDING, PROCESSED, CANCELLED
    }

    private final int transactionId;
    private final String accountId;
    private final double amount;
    private final TransactionType type;
    private final LocalDateTime timestamp;
    private Status status;

    public BankTransaction(int transactionId, String accountId,
                            double amount, TransactionType type) {
        this.transactionId = transactionId;
        this.accountId = accountId;
        this.amount = amount;
        this.type = type;
        this.timestamp = LocalDateTime.now();
        this.status = Status.PENDING;
    }

    public int getTransactionId() { return transactionId; }
    public String getAccountId() { return accountId; }
    public double getAmount() { return amount; }
    public TransactionType getType() { return type; }
    public LocalDateTime getTimestamp() { return timestamp; }
    public Status getStatus() { return status; }
    public void setStatus(Status status) { this.status = status; }

    @Override
    public String toString() {
        return String.format("TXN%d | %s | %.0f | %s | %s | %s",
                transactionId, accountId, amount, type, timestamp, status);
    }
}
