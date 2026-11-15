import java.util.Arrays;
import java.util.Random;

/**
 * This algorithm implements a presorted approach to computing the intersection, C,
 * of two sets of numbers, A and B. Integers arrays are used to store the contents
 * of the sets.
 * 
 * @author Ethan Rocklin
 */

public class PresortedSetIntersection {

    // MAX_NUM_COUNT fields must be nonnegative
    private static final int MAX_NUM_COUNT_A = 15;
    private static final int MAX_NUM_COUNT_B = 15;
    // MIN and MAX fields can be any valid 32-bit integer
    private static final int SET_A_MIN = -10;
    private static final int SET_A_MAX = 10;
    private static final int SET_B_MIN = -10;
    private static final int SET_B_MAX = 10;

    public static void main(String[] args) {
        Random rand = new Random();
        // Set declarations if you want to specify the elements
//        int[] setA = {};
//        int[] setB = {};
        // Set declarations if you want random sets; generated duplicates are
        // not included to adhere to the definition of a set
        int[] setA = new int[MAX_NUM_COUNT_A];
        int[] setB = new int[MAX_NUM_COUNT_B];
        for (int i = 0; i < MAX_NUM_COUNT_A; i++) {
            setA[i] = rand.nextInt(SET_A_MIN, SET_A_MAX + 1);
        }
        for (int i = 0; i < MAX_NUM_COUNT_B; i++) {
            setB[i] = rand.nextInt(SET_B_MIN, SET_B_MAX + 1);
        }
        // Removes duplicates from left to right to ensure the definition of a set is satisfied
        setA = Arrays.stream(setA).distinct().toArray();
        setB = Arrays.stream(setB).distinct().toArray();
        // Prints the results of the algorithm before sorting the sets,
        // after soring, and the intersection set
        System.out.print("\nSet A (unsorted)\t: " );
        printArray(setA);
        System.out.print("Set B (unsorted)\t: " );
        printArray(setB);
        int[] intersection = findIntersection(setA, setB);
        System.out.print("Set A (sorted)\t\t: " );
        printArray(setA);
        System.out.print("Set B (sorted)\t\t: " );
        printArray(setB);
        System.out.print("Intersection\t\t: " );
        printArray(intersection);
    }

    /**
     * This method finds the intersection set of two sets A and B. The intersection set
     * is the set of all elements that are included in A and B. Time Complexity: Θ(n * log n).
     *
     * @param setA a set of integers, represented as an array
     * @param setB another set of integers, represented as an array
     * @return an array of integers representing the intersection of the passed sets
     */
    private static int[] findIntersection(int[] setA, int[] setB) {
        // Sorts both arrays in nondecreasing order
        mergeSort(setA);
        mergeSort(setB);
        int[] intersection = null;
        // Determines what the maximum number of intersected elements to size returning array
        if (setA.length < setB.length || setA.length == setB.length) {
            intersection = new int[setA.length];
        } else {
            intersection = new int[setB.length];
        }
        // Initializes pointers and intersection counter for scanning arrays
        int i = 0;
        int j = 0;
        int count = 0;
        // Incrementally scans and compares elements in both sets for intersected elements
        while (i < setA.length && j < setB.length) {
            if (setA[i] == setB[j]) {
                intersection[count] = setA[i];
                count++;
                i++;
                j++;
            } else if (setA[i] < setB[j]) {
                i++;
            } else {
                j++;
            }
        }
        // Reassigns the intersection set to a copy of itself without any
        // potential unoccupied cells
        intersection = Arrays.copyOf(intersection, count);
        return intersection;
    }

    /**
     * This method performs Merge Sort on an array of integers. Time Complexity: Θ(n * log n).
     *
     * @param arr An array of integers
     * @return The array sorted in increasing order
     */
    private static int[] mergeSort(int[] arr) {
        // Edge cases and recursive base case
        if (arr.length < 2) {
            return arr;
        }
        int[] leftPart = Arrays.copyOfRange(arr, 0, arr.length / 2);
        int[] rightPart = Arrays.copyOfRange(arr, arr.length / 2, arr.length);
        mergeSort(leftPart);
        mergeSort(rightPart);
        merge(leftPart, rightPart, arr);
        return arr;
    }

    /**
     * This method is a helper to the Merge Sort algorithm. It merges two sorted
     * parts of an array back into that array.
     *
     * @param leftPart the first sorted subarray
     * @param rightPart the second sorted subarray
     * @param sorted the array used to merge the left and right parts
     * @return a sorted array in ascending order
     */
    private static int[] merge(int[] leftPart, int[] rightPart, int[] sorted) {
        // Indices for the three arrays
        int i = 0;
        int j = 0;
        int k = 0;
        while (i < leftPart.length && j < rightPart.length) {
            if (leftPart[i] <= rightPart[j]) {
                sorted[k] = leftPart[i];
                i++;
            } else {
                sorted[k] = rightPart[j];
                j++;
            }
            k++;
        }
        // Copies any remaining, sorted elements into the sorted array
        while (i < leftPart.length) {
            sorted[k++] = leftPart[i++];
        }
        while (j < rightPart.length) {
            sorted[k++] = rightPart[j++];
        }
        return sorted;
    }

    /**
     * This method prints an integer array's contents to the console.
     *
     * @param arr the integer array to print
     */
    private static void printArray(int[] arr) {
        // If a set contains no elements; an empty set
        if (arr.length == 0) {
            System.out.print("∅\n");
        } else {
            System.out.print("[");
            for (int i = 0; i < arr.length - 1; i++) {
                System.out.print(arr[i] + ", ");
            }
            System.out.print(arr[arr.length - 1] + "]\n");
        }
    }

}
