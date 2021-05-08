package sortingVisualizer;

/**
 * @author arunabhsarkar
 *
 */
public class InsertionSort implements Runnable {

	private Integer[] needToBeSorted;
	private Visualizer sortFrame;

	public InsertionSort(Integer[] needToBeSorted, Visualizer sortFrame) {
		this.needToBeSorted = needToBeSorted;
		this.sortFrame = sortFrame;
	}

	public void run() {
		sort();
		SortComplete.currentlySorting = false;
	}

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
				sortFrame.refreshArray(needToBeSorted, i, insert);
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
		sortFrame.refreshArray(needToBeSorted);

	}
}
