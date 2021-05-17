package sortingVisualizer;

/**
 * @author arunabhsarkar
 *
 */
public class InsertionSort implements Runnable {

	private Integer[] needToBeSorted;
	private Visualizer main;

	public InsertionSort(Integer[] needToBeSorted, Visualizer main) {
		this.needToBeSorted = needToBeSorted;
		this.main = main;
	}

	/**
	 * This method will run the insertion sort method.
	 */
	public void run() {
		sort();
		SortComplete.currentlySorting = false;
	}

	/**
	 * This method runs the insertion sorting algorithm in a time complexity of
	 * O(N^2)
	 */
	public void sort() {
		int num = 0;
		int insert = 0;
		for (int i = 1; i < needToBeSorted.length; i++) {
			insert = i;
			for (int j = i - 1; j >= 0; j--) {
				if (needToBeSorted[j] > needToBeSorted[i]) {
					insert = j;
					if (j == 0) {
						break;
					}
				} else {
					break;
				}
				main.refreshArray(needToBeSorted, i, insert);
				try {
					Thread.sleep(SortComplete.sleep);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			num = needToBeSorted[i];
			for (int j = i; j > insert; j--) {
				needToBeSorted[j] = needToBeSorted[j - 1];
			}
			needToBeSorted[insert] = num;
		}
		main.refreshArray(needToBeSorted);
	}
}
