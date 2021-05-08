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

	public void sort(Integer[] needToBeSorted) {
		sort(needToBeSorted, 0, needToBeSorted.length - 1);
	}

	public void sort(Integer[] needToBeSorted, int firstIndex, int lastIndex) {

		int midIndex, leftIndex, rightIndex;
	
		int num;

		if (firstIndex >= lastIndex) {
			return;
		}
		midIndex = (firstIndex + lastIndex) / 2;
		sort(needToBeSorted, firstIndex, midIndex);
		sort(needToBeSorted, midIndex + 1, lastIndex);

		leftIndex = firstIndex;

		rightIndex = midIndex + 1;
		// check to see if we can skip the merge
		if (needToBeSorted[midIndex] <= needToBeSorted[rightIndex]) {
			return;
		}

		while (leftIndex <= midIndex && rightIndex <= lastIndex) {

			if (needToBeSorted[leftIndex] <= needToBeSorted[rightIndex]) {
				leftIndex++;
			} else {
				num = needToBeSorted[rightIndex];
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
	}

}
