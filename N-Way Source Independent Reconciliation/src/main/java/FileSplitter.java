import java.io.*;
import java.util.*;

public class FileSplitter {

    private int numPartitions;
    private String fileName;
    private String outputDir;

    public FileSplitter(String fileName, String outputDir, int numPartitions) {
        this.fileName = fileName;
        this.outputDir = outputDir;
        this.numPartitions = numPartitions;
    }

    public void split() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(fileName));
        List<BufferedWriter> writers = new ArrayList<>();

        // Create partition files
        for (int i = 0; i < numPartitions; i++) {
            BufferedWriter writer = new BufferedWriter(new FileWriter(outputDir + "/part_" + i + ".csv"));
            writers.add(writer);
        }

        String line;
        while ((line = reader.readLine()) != null) {
            String[] columns = line.split(",");
            int studentID = Integer.parseInt(columns[0]);

            // Hash to determine which partition the record goes to
            int partition = studentID % numPartitions;
            writers.get(partition).write(line + "\n");
        }

        // Close resources
        reader.close();
        for (BufferedWriter writer : writers) {
            writer.close();
        }
    }
}
