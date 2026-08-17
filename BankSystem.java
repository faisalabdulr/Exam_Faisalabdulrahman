import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;

public class BankSystem {

    // FIFO: transaksi baru masuk ke tail, diambil dari head
    private final Deque<BankTransaction> processingQueue = new ArrayDeque<>();

    // LIFO: transaksi yang sudah PROCESSED disimpan untuk keperluan undo
    private final Deque<BankTransaction> undoStack = new ArrayDeque<>();

    // Fast lookup by transactionID, O(1) rata-rata
    private final Map<Integer, BankTransaction> transactionIndex = new HashMap<>();

    /** Signature wajib sesuai soal: add(int id, double amount) */
    public void add(int id, double amount) {
        add(id, "UNKNOWN_ACC", amount, BankTransaction.TransactionType.TRANSFER);
    }

    /** Overload untuk data transaksi yang lebih lengkap */
    public void add(int id, String accountId, double amount,
                     BankTransaction.TransactionType type) {
        if (transactionIndex.containsKey(id)) {
            throw new IllegalArgumentException("Duplicate transactionID: " + id);
        }
        BankTransaction txn = new BankTransaction(id, accountId, amount, type);
        processingQueue.offerLast(txn);   // O(1)
        transactionIndex.put(id, txn);    // O(1) rata-rata
    }

    /** Memproses satu transaksi paling depan (FIFO) */
    public BankTransaction process() {
        BankTransaction txn = processingQueue.pollFirst();  // O(1)
        if (txn == null) {
            System.out.println("Tidak ada transaksi pending.");
            return null;
        }
        txn.setStatus(BankTransaction.Status.PROCESSED);
        undoStack.push(txn);   // O(1), push ke head sebagai stack
        System.out.println("Processed: " + txn);
        return txn;
    }

    /** Membatalkan transaksi PROCESSED yang paling terakhir (LIFO) */
    public BankTransaction undo() {
        BankTransaction txn = undoStack.poll();   // O(1)
        if (txn == null) {
            System.out.println("Tidak ada transaksi yang bisa di-undo.");
            return null;
        }
        txn.setStatus(BankTransaction.Status.CANCELLED);
        System.out.println("Undo transaksi: " + txn);
        return txn;
    }

    /** Mencari transaksi berdasarkan ID tanpa full traversal, O(1) rata-rata */
    public BankTransaction search(int id) {
        BankTransaction txn = transactionIndex.get(id);
        if (txn == null) {
            System.out.println("Transaksi " + id + " tidak ditemukan.");
        }
        return txn;
    }

    public int pendingCount()   { return processingQueue.size(); }
    public int undoableCount()  { return undoStack.size(); }
    public int totalRegistered(){ return transactionIndex.size(); }
}
