package ufrn.edu.br.services;

import java.util.ArrayList;
import java.util.List;

/**
 * The SequentialMergeSortService class provides methods to perform
 * merge sort on a list of integers in a sequential (single-threaded) manner.
 * 
 * This class implements the merge sort algorithm without using multiple
 * threads, suitable for environments where concurrency is not required or
 * desired.
 * 
 * @version 1.0
 * @since 2024-04-27
 */
public class SequentialMergeSortService {

    /**
     * Recursively performs merge sort on the specified portion of the list.
     * 
     * @param currentList The list of integers to sort.
     * @param left        The starting index of the portion to sort.
     * @param right       The ending index of the portion to sort.
     */
    public void mergeSort(List<Integer> currentList, int left, int right) {
        if (left >= right) {
            return;
        } else {
            int middle = (left + right) / 2;

            mergeSort(currentList, left, middle);
            mergeSort(currentList, middle + 1, right);
            merge(currentList, left, middle, right);
        }
    }

    /**
     * Merges two sorted sublists into a single sorted list.
     * 
     * @param currentList The list containing the sublists to merge.
     * @param start       The starting index of the first sublist.
     * @param middle      The ending index of the first sublist.
     * @param end         The ending index of the second sublist.
     */
    private void merge(List<Integer> currentList, int start, int middle, int end) {
        ArrayList<Integer> result = new ArrayList<>();
        int indexLeft = start;
        int indexRight = middle + 1;

        while (indexLeft <= middle && indexRight <= end) {
            if (currentList.get(indexLeft) <= currentList.get(indexRight)) {
                result.add(currentList.get(indexLeft++));
            } else {
                result.add(currentList.get(indexRight++));
            }
        }

        while (indexLeft <= middle) {
            result.add(currentList.get(indexLeft++));
        }

        while (indexRight <= end) {
            result.add(currentList.get(indexRight++));
        }

        for (int i = 0; i < result.size(); i++) {
            currentList.set(start + i, result.get(i));
        }
    }
}
