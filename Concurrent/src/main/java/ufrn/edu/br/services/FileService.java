package ufrn.edu.br.services;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * The FileService class provides utility methods for reading from and writing
 * to files, as well as recording execution metrics.
 * 
 * This class handles file operations such as reading integer lists from input
 * files, writing sorted lists to output files, and logging performance metrics.
 * 
 * @version 1.0
 * @since 2024-04-27
 */
public class FileService {

    /**
     * Reads integers from a file specified by the given path.
     * 
     * @param path The path to the input file.
     * @return A list of integers read from the file.
     * @throws IOException If an I/O error occurs while reading the file.
     */
    public List<Integer> readFile(String path) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(path));
        ArrayList<Integer> numbers = new ArrayList<>();

        String line;
        while ((line = br.readLine()) != null) {
            String[] nums = line.trim().split("\\s+");
            for (String num : nums) {
                if (!num.isEmpty()) {
                    numbers.add(Integer.parseInt(num));
                }
            }
        }

        br.close();
        return numbers;
    }

    /**
     * Writes a list of numbers to a file at the specified path.
     * 
     * @param path    The path to the output file.
     * @param numbers The list of numbers to write.
     * @param name    An optional name for the case, can be null.
     * @throws IOException If an I/O error occurs while writing to the file.
     */
    public void writeFile(String path, List<? extends Number> numbers, String name) throws IOException {
        File file = new File(path);
        file.getParentFile().mkdirs();

        FileWriter fw = new FileWriter(file, false);

        if (name != null) {
            fw.write("Case " + name);
            fw.write("\n");
        }

        for (Number num : numbers) {
            fw.write(num.toString() + " ");
        }

        fw.write("\n");
        fw.close();
    }

    /**
     * Writes execution metrics to a metrics file.
     * 
     * @param path           The path to the metrics file.
     * @param executionTimes A list of execution durations in milliseconds.
     * @param caseName       The name of the case being recorded.
     * @throws IOException If an I/O error occurs while writing to the file.
     */
    public void writeMetrics(String path, List<Long> executionTimes, String caseName) throws IOException {
        File file = new File(path);
        file.getParentFile().mkdirs();

        FileWriter fw = new FileWriter(file, true);

        fw.write("Metrics for Case " + caseName + " (Concurrent Version):\n\n");

        long max = executionTimes.stream().mapToLong(Long::longValue).max().getAsLong();
        long min = executionTimes.stream().mapToLong(Long::longValue).min().getAsLong();
        double avg = calculateAverage(executionTimes);
        double stdDev = calculateStandardDeviation(executionTimes, avg);

        fw.write("Maximum Time (ms): " + max + "\n");
        fw.write("Minimum Time (ms): " + min + "\n");
        fw.write("Average Time (ms): " + String.format("%.4f", avg) + "\n");
        fw.write("Standard Deviation (ms): " + String.format("%.4f", stdDev) + "\n");
        fw.write("\n----------------------------------------\n\n");

        fw.close();
    }

    /**
     * Calculates the average of a list of long values.
     * 
     * @param times The list of long values.
     * @return The average value.
     */
    private double calculateAverage(List<Long> times) {
        return times.stream().mapToLong(Long::longValue).average().getAsDouble();
    }

    /**
     * Calculates the standard deviation of a list of long values.
     * 
     * @param times The list of long values.
     * @param mean  The mean value of the list.
     * @return The standard deviation.
     */
    private double calculateStandardDeviation(List<Long> times, double mean) {
        double variance = times.stream()
                .mapToDouble(time -> Math.pow(time - mean, 2))
                .sum() / times.size();
        return Math.sqrt(variance);
    }
}
