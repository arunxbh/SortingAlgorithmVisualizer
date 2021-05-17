package sortingVisualizer;

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
	public static int widthData;

	public static void main(String[] args) {
		main = new Visualizer();
		resetData();
		main.setLocationRelativeTo(null);
	}

	/**
	 * This static method will create a randomized set of data before
	 * the sort takes place.
	 * 
	 */
	public static void resetData() {
		// This boolean statement tests if data is being sorted and kicks out of
		// resetData
		if (currentlySorting) {
			return;
		}
		needToBeSorted = new Integer[sortDataCounter];
		widthData = (int) Math.max(Math.floor(500 / sortDataCounter), 1);
		for (int i = 0; i < needToBeSorted.length; i++) {
				needToBeSorted[i] = (int) (sortDataCounter * Math.random());
		}
		// In the case where incremental values are being used and are sorted, this
		// shuffles those values;
		main.drawArray(needToBeSorted);
	}

	/**
	 * This static method will start the sort that is selected from the JComboBox
	 * 
	 * @param algorithm is the selected sorting algorithm
	 */
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
				break;
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