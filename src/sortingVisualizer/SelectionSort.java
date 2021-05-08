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

	public void run() {
		sort();
		SortComplete.currentlySorting = false;
	}

	public void sort() {
		int num = 0;
		int current = 0;
		for (int i = 0; i < needToBeSorted.length; i++) {
			current = i;
			for (int j = needToBeSorted.length - 1; j > i; j--) {
				if (needToBeSorted[current] >= needToBeSorted[j]) {
					current = j;
				}
				main.refreshArray(needToBeSorted, current, j - 1);
				try {
					Thread.sleep(SortComplete.sleep);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			num = needToBeSorted[i];
			needToBeSorted[i] = needToBeSorted[current];
			needToBeSorted[current] = num;
		}
		main.refreshArray(needToBeSorted);
	}

}
