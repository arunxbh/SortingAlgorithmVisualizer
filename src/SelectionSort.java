package sortingVisualizer;

/**
 * @author arunabhsarkar
 *
 */
public class SelectionSort implements Runnable {

	private Integer[] needToBeSorted;
	private Visualizer main;

	public SelectionSort(Integer[] needToBeSorted, Visualizer main) {
		this.needToBeSorted = needToBeSorted;
		this.main = main;
	}

	/**
	 * This method will run the selection sort method.
	 */
	public void run() {
		sort();
		SortComplete.currentlySorting = false;
	}

	/**
	 * This method runs the selection sorting algorithm in a time complexity of
	 * O(N^2)
	 */
	public void sort() {
		int min = 0;
		for (int i = 0; i < needToBeSorted.length; i++) {
			min = i;
			for (int j = needToBeSorted.length - 1; j > i; j--) {
				if (needToBeSorted[min] >= needToBeSorted[j]) {
					min = j;
				}
				main.refreshArray(needToBeSorted, min, j - 1);
				try {
					Thread.sleep(SortComplete.sleep);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			if (min != i) {
				swap(i, min);
			}
		}
		main.refreshArray(needToBeSorted);
	}

	public void swap(int i, int j) {
		int swap = needToBeSorted[i];
		needToBeSorted[i] = needToBeSorted[j];
		needToBeSorted[j] = swap;
	}

}
