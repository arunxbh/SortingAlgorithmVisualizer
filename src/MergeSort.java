package sortingVisualizer;

/**
 * @author arunabhsarkar
 *
 */
public class MergeSort implements Runnable {

	public void run() {
		Integer[] needToBeSorted = SortComplete.needToBeSorted;
		sort(needToBeSorted);
		SortComplete.currentlySorting = false;
	}

	/**
	 * This method runs the merge sorting algorithm in a time complexity of
	 * O(NlogN).
	 * 
	 * @param needToBeSorted is the unsorted array.
	 */
	public void sort(Integer[] needToBeSorted) {
		sort(needToBeSorted, 0, needToBeSorted.length - 1);
	}

	/**
	 * This method runs the merge sorting algorithm in a time complexity of
	 * O(NlogN).
	 * 
	 * @param needToBeSorted is the unsorted array.
	 * @param firstIndex     is an arbitrary "first" index of the unsorted array.
	 * @param lastIndex      is an arbitrary "last" index of the unsorted array.
	 */
	public void sort(Integer[] needToBeSorted, int firstIndex, int lastIndex) {

		if (firstIndex < lastIndex) {

			int midIndex = (int) (firstIndex + lastIndex) / 2;
			int leftIndex = firstIndex;
			int rightIndex = midIndex + 1;

			// Recursion:
			sort(needToBeSorted, firstIndex, midIndex);
			sort(needToBeSorted, midIndex + 1, lastIndex);

			// check to see if we can skip the merge
			if (needToBeSorted[midIndex] <= needToBeSorted[rightIndex]) {
				return;
			}
			while (leftIndex <= midIndex && rightIndex <= lastIndex) {

				if (needToBeSorted[leftIndex] <= needToBeSorted[rightIndex]) {
					leftIndex++;
				} else {
					int num = needToBeSorted[rightIndex];
					for (int i = rightIndex - leftIndex; i > 0; i--) {
						needToBeSorted[leftIndex + i] = needToBeSorted[leftIndex + i - 1];
					}
					needToBeSorted[leftIndex] = num;
					leftIndex++;
					midIndex++;
					rightIndex++;
				}
				SortComplete.main.refreshArray(needToBeSorted, midIndex, rightIndex, leftIndex);
				try {
					Thread.sleep(SortComplete.sleep);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		} else {
			// Base case is when the firstIndex == lastIndex as the list has a length of 1.
			return;
		}
	}
}