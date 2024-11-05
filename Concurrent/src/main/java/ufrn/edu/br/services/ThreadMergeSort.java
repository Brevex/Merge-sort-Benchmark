package ufrn.edu.br.services;

import java.util.ArrayList;
import java.util.List;

/**
 * The ThreadMergeSort class extends the Thread class to perform
 * merge sort on a subset of a list concurrently.
 * 
 * This class allows sorting a portion of a list in a separate thread,
 * enabling parallel processing of the left and right halves of the list.
 * 
 * @version 1.0
 * @since 2024-04-27
 */
public class ThreadMergeSort extends Thread {
    List<Integer> list;

    /**
     * Constructs a new ThreadMergeSort with the specified name and list.
     * 
     * @param name The name of the thread.
     * @param list The list of integers to sort.
     */
    public ThreadMergeSort(String name, List<Integer> list) {
        super(name);
        this.list = list;
    }

    /**
     * Default constructor for ThreadMergeSort.
     */
    public ThreadMergeSort() {
    }

    /**
     * The entry point of the thread. It initiates the merge sort on the list.
     */
    @Override
    public void run() {
        mergeSort(list, 0, list.size() - 1);
    }

    /**
     * Recursively performs merge sort on the specified portion of the list.
     * 
     * @param list  The list of integers to sort.
     * @param left  The starting index of the portion to sort.
     * @param right The ending index of the portion to sort.
     */
    public void mergeSort(List<Integer> list, int left, int right) {
        if (left >= right) {
            return;
        } else {
            int middle = (left + right) / 2;

            mergeSort(list, left, middle);
            mergeSort(list, middle + 1, right);
            merge(list, left, middle, right);
        }
    }

    /**
     * Merges two sorted sublists into a single sorted list.
     * 
     * @param list   The list containing the sublists to merge.
     * @param start  The starting index of the first sublist.
     * @param middle The ending index of the first sublist.
     * @param end    The ending index of the second sublist.
     */
    public void merge(List<Integer> list, int start, int middle, int end) {
        List<Integer> result = new ArrayList<>();
        int indexLeft = start;
        int indexRight = middle + 1;

        while (indexLeft <= middle && indexRight <= end) {
            if (list.get(indexLeft) <= list.get(indexRight)) {
                result.add(list.get(indexLeft++));
            } else {
                result.add(list.get(indexRight++));
            }
        }

        while (indexLeft <= middle) {
            result.add(list.get(indexLeft++));
        }

        while (indexRight <= end) {
            result.add(list.get(indexRight++));
        }

        for (int i = 0; i < result.size(); i++) {
            list.set(start + i, result.get(i));
        }
    }
}
