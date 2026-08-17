public class Main {
    public static void main(String[] args) {
        BankSystem bank = new BankSystem();

        bank.add(1001, "ACC9981", 2_500_000,
                BankTransaction.TransactionType.TRANSFER);
        bank.add(1002, "ACC1122", 150_000,
                BankTransaction.TransactionType.BILL_PAYMENT);
        bank.add(1003, "ACC5567", 500_000,
                BankTransaction.TransactionType.TOP_UP);

        System.out.println("--- Fast lookup (fraud monitoring) ---");
        System.out.println("Search 1002 -> " + bank.search(1002));

        System.out.println("--- Proses transaksi sesuai urutan FIFO ---");
        bank.process();
        bank.process();

        System.out.println("--- Fraud alert pada transaksi terakhir ---");
        bank.undo();

        System.out.println("--- Status akhir ---");
        System.out.println("Pending: " + bank.pendingCount());
        System.out.println("Undoable: " + bank.undoableCount());
        System.out.println("Total terdaftar: " + bank.totalRegistered());
    }
}
