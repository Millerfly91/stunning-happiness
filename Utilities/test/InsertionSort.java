
import java.util.ArrayList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author James
 */
public class InsertionSort {

    public static void main(String[] argv) {
        sortArray();
    }

    static ArrayList<Integer> generateRandomIntArray(int size) {
        ArrayList<Integer> sortedArray = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            sortedArray.add(i);
        }
        ArrayList<Integer> randomArray = new ArrayList<>(size);
        for (int k = 0; k < size; k++) {
            int position = (int) (Math.random() * sortedArray.size());
            randomArray.add(sortedArray.get(position));
            sortedArray.remove(position);
        }
        return randomArray;
    }

    boolean compareArrays(ArrayList<Integer> array1, ArrayList<Integer> array2) {
        if (array1 == null || array2 == null
                || array1.size() != array2.size()) {
            return false;
        }
        for (int i = 0; i < array1.size(); i++) {
            if (!array1.get(i).equals(array2.get(i))) {
                return false;
            }
        }
        return true;
    }

    static ArrayList<Integer> sortArray() {
        int i = 0;
        ArrayList<Integer> sortedArray = new ArrayList<>();
        ArrayList<Integer> nums = generateRandomIntArray(15);
        System.out.println("Original array: " + nums);
        for (Integer num : nums) {
            i = 0;
            int j = 0;
            while (i < (nums.size() - 1)) {
                int A = nums.get(i);
                int B = nums.get(i + 1);
                if (A > B) {
                    nums.set(i + 1, A);
                    nums.set(i, B);
                }
                i++;
            }
            j++;
        }
        System.out.println("Sorted array: " + nums);

        return sortedArray;
    }
}
