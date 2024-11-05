package ufrn.edu.br;

import ufrn.edu.br.services.FileService;
import ufrn.edu.br.services.SequentialMergeSortService;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * The Main class for the Sequential version of the Merge Sort application.
 * It reads input files, performs sequential merge sort, writes the sorted
 * output, and records execution metrics.
 * 
 * This class processes multiple input files sequentially, sorts them using a
 * single-threaded merge sort algorithm, and records performance metrics.
 * 
 * @version 1.0
 * @since 2024-04-27
 */
public class Main {
    private static final String INPUT_DIR = "input";
    private static final String OUTPUT_DIR = "output";

    /**
     * The entry point of the Sequential Merge Sort application.
     */
    public static void main(String[] args) {
        List<String> inputs = new ArrayList<>(Arrays.asList("A", "B", "C", "D", "E", "F", "G", "H", "I", "J"));
        FileService fileService = new FileService();
        File outputDirectory = new File(OUTPUT_DIR);

        if (!outputDirectory.exists()) {
            outputDirectory.mkdirs();
        }

        SequentialMergeSortService sequentialMergeSortService = new SequentialMergeSortService();

        for (String input : inputs) {
            List<Long> executionDurations = new ArrayList<>();

            try {
                String inputPath = String.format("%s/%s.in", INPUT_DIR, input);
                String outputPath = String.format("%s/%s.out", OUTPUT_DIR, input);

                ArrayList<Integer> list = (ArrayList<Integer>) fileService.readFile(inputPath);

                for (int i = 0; i < 20; i++) {
                    ArrayList<Integer> currentList = new ArrayList<>(list);

                    long startTime = System.currentTimeMillis();
                    sequentialMergeSortService.mergeSort(currentList, 0, currentList.size() - 1);
                    long endTime = System.currentTimeMillis();
                    long totalTime = endTime - startTime;

                    if (i == 19) {
                        fileService.writeFile(outputPath, currentList, null);
                    }

                    executionDurations.add(totalTime);
                }

                String metricsPath = OUTPUT_DIR + "/metrics.txt";
                fileService.writeMetrics(metricsPath, executionDurations, input);

            } catch (Exception e) {
                System.err.println("Error processing file " + input + ": " + e.getMessage());
                e.printStackTrace();
            }
        }
    }
}
