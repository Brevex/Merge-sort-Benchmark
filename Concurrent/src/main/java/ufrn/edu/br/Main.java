package ufrn.edu.br;

import ufrn.edu.br.services.FileService;
import ufrn.edu.br.services.ThreadMergeSort;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * The Main class for the Concurrent version of the Merge Sort application.
 * It reads input files, performs concurrent merge sort using multiple threads,
 * writes the sorted output, and records execution metrics.
 * 
 * This class processes multiple input files concurrently, sorts them using a
 * multi-threaded merge sort algorithm, and records performance metrics.
 * 
 * @version 1.0
 * @since 2024-04-27
 */

public class Main {
    private static final String INPUT_DIR = "input";
    private static final String OUTPUT_DIR = "output";

    /**
     * The entry point of the Concurrent Merge Sort application.
     */
    public static void main(String[] args) {
        List<String> inputs = new ArrayList<>(Arrays.asList("A", "B", "C", "D", "E", "F", "G", "H", "I", "J"));
        FileService fileService = new FileService();
        ThreadMergeSort threadMergeSort = new ThreadMergeSort();

        for (String input : inputs) {
            List<Long> executionDurations = new ArrayList<>();

            try {
                String inputPath = String.format("%s/%s.in", INPUT_DIR, input);
                String outputPath = String.format("%s/%s.out", OUTPUT_DIR, input);

                List<Integer> list = fileService.readFile(inputPath);

                int start = 0;
                int end = list.size() - 1;
                int middle = (start + end) / 2;

                List<Integer> listLeft = list.subList(start, middle + 1);
                List<Integer> listRight = list.subList(middle + 1, list.size());

                for (int i = 0; i < 20; i++) {
                    List<Integer> listLeftCopy = new ArrayList<>(listLeft);
                    List<Integer> listRightCopy = new ArrayList<>(listRight);

                    ThreadMergeSort threadLeft = new ThreadMergeSort("List on the left", listLeftCopy);
                    ThreadMergeSort threadRight = new ThreadMergeSort("List on the right", listRightCopy);

                    long startTime = System.currentTimeMillis();

                    threadLeft.start();
                    threadRight.start();

                    threadLeft.join();
                    threadRight.join();

                    List<Integer> result = new ArrayList<>();
                    result.addAll(listLeftCopy);
                    result.addAll(listRightCopy);

                    threadMergeSort.merge(result, 0, listLeftCopy.size() - 1, result.size() - 1);

                    long endTime = System.currentTimeMillis();
                    long totalTime = endTime - startTime;

                    if (i == 19) {
                        fileService.writeFile(outputPath, result, null);
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
