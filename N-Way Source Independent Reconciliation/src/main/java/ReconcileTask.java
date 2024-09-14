import java.io.IOException;

public class ReconcileTask implements Runnable {
    private String partFile1;
    private String partFile2;
    private String outputFile;
    private FileReconciler reconciler;

    public ReconcileTask(String partFile1, String partFile2, String outputFile, FileReconciler reconciler) {
        this.partFile1 = partFile1;
        this.partFile2 = partFile2;
        this.outputFile = outputFile;
        this.reconciler = reconciler;
    }

    @Override
    public void run() {
        try {
            reconciler.reconcile(partFile1, partFile2, outputFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
