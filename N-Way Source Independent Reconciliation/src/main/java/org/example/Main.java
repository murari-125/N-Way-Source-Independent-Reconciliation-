import java.io.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {

    public static void main(String[] args) throws IOException {
        // Input files
        String file1 = "file1.csv";
        String file2 = "file2.csv";

        // Output directories for partitioned files
        String outputDir1 = "output/file1";
        String outputDir2 = "output/file2";

        // Number of partitions and threads
        int numPartitions = 10; // Example: split into 10 parts
        int numThreads = 4;     // Number of available cores

        // Step 1: Split the large files into smaller part files
        FileSplitter splitter1 = new FileSplitter(file1, outputDir1, numPartitions);
        FileSplitter splitter2 = new FileSplitter(file2, outputDir2, numPartitions);

        splitter1.split();
        splitter2.split();

        // Step 2: Create a thread pool and reconcile part files in parallel
        ExecutorService executor = Executors.newFixedThreadPool(numThreads);
        FileReconciler reconciler = new FileReconciler();

        for (int i = 0; i < numPartitions; i++) {
            String partFile1 = outputDir1 + "/part_" + i + ".csv";
            String partFile2 = outputDir2 + "/part_" + i + ".csv";
            String outputFile = "output/diff.csv";

            // Create a task to reconcile part files
            ReconcileTask task = new ReconcileTask(partFile1, partFile2, outputFile, reconciler);
            executor.execute(task);
        }

        // Shutdown the executor after completing tasks
        executor.shutdown();
        while (!executor.isTerminated()) {
            // Wait for all threads to finish
        }

        System.out.println("Reconciliation complete. Check output/diff.csv for results.");
    }
}
