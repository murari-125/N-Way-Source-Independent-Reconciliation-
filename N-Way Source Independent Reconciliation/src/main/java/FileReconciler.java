import java.io.*;
import java.util.*;

public class FileReconciler {

    public void reconcile(String partFile1, String partFile2, String outputFile) throws IOException {
        Set<String> file1Data = loadFileData(partFile1);
        Set<String> file2Data = loadFileData(partFile2);

        BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile, true));

        // Find records in file1 not in file2
        for (String record : file1Data) {
            if (!file2Data.contains(record)) {
                writer.write("File1 extra: " + record + "\n");
            }
        }

        // Find records in file2 not in file1
        for (String record : file2Data) {
            if (!file1Data.contains(record)) {
                writer.write("File2 extra: " + record + "\n");
            }
        }

        writer.close();
    }

    private Set<String> loadFileData(String fileName) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(fileName));
        Set<String> data = new HashSet<>();
        String line;
        while ((line = reader.readLine()) != null) {
            data.add(line);
        }
        reader.close();
        return data;
    }
}
