package sortingVisualizer;

import java.util.ArrayList;
import java.util.Collections;

/**
 * @author arunabhsarkar
 *
 */
public class SortComplete {

	private static Thread threadSort;

	public static Visualizer main;
	public static Integer[] needToBeSorted;
	public static boolean currentlySorting = false;
	public static int sortDataCounter = 100;
	public static int sleep = 20;
	public static int widthBlock;
	// This boolean instance variable tells us if the values are incremental (true)
	// or randomzied (false).
	public static boolean stepped = false;

	public static void main(String[] args) {
		main = new Visualizer();
		resetData();
		main.setLocationRelativeTo(null);
	}

	public static void resetData() {
		// This boolean statement tests if data is being sorted and kicks out of
		// resetData
		if (currentlySorting) {
			return;
		}
		needToBeSorted = new Integer[sortDataCounter];
		widthBlock = (int) Math.max(Math.floor(500 / sortDataCounter), 1);
		for (int i = 0; i < needToBeSorted.length; i++) {
			if (stepped) {
				needToBeSorted[i] = i;
			} else {
				needToBeSorted[i] = (int) (sortDataCounter * Math.random());
			}
		}
		// In the case where incremental values are being used and are sorted, this
		// shuffles those values;
		if (stepped) {
			ArrayList<Integer> shuffle = new ArrayList<>();
			for (int i = 0; i < needToBeSorted.length; i++) {
				shuffle.add(needToBeSorted[i]);
			}
			Collections.shuffle(shuffle);
			needToBeSorted = shuffle.toArray(needToBeSorted);
		}
		main.drawArray(needToBeSorted);
	}

	public static void beginSort(String algorithm) {
		if (threadSort == null || !currentlySorting) {
			resetData();
			currentlySorting = true;

			switch (algorithm) {

			case "Merge Sort":
				threadSort = new Thread(new MergeSort());
				break;

			case "Selection Sort":
				threadSort = new Thread(new SelectionSort(needToBeSorted, main));

			case "Insertion Sort":
				threadSort = new Thread(new InsertionSort(needToBeSorted, main));
				break;

			default:
				currentlySorting = false;
				return;

			}

			threadSort.start();

		}
	}

}
