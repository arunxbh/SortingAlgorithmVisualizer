package sortingVisualizer;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * @author arunabhsarkar
 *
 */
@SuppressWarnings("serial")
public class Visualizer extends JFrame {

	private final int SIZE_O = 100;
	private final int SIZE_L = 1;
	private final int SIZE_H = 200;
	private final int SPEED_O = 20;
	private final int SPEED_L = 1;
	private final int SPEED_H = 1000;

	private final String[] algorithmOptions = { "Merge Sort", "Selection Sort", "Insertion Sort" };

	private int SIZE_MOD;

	private JPanel wrapper;
	private JPanel array;
	private JPanel button;
	private JPanel[] dataObj;
	private JButton startVis;
	private JComboBox<String> select;
	private JSlider speedControl;
	private JSlider sizeControl;
	private JLabel currentSpeed;
	private JLabel currentSize;
	private GridBagConstraints grid;

	public Visualizer() {
		super("Sorting Algorithim Visualizing Program");

		// This section initializes all the instance variables
		startVis = new JButton("Start");
		button = new JPanel();
		array = new JPanel();
		wrapper = new JPanel();
		sizeControl = new JSlider(SIZE_L, SIZE_H, SIZE_O);
		speedControl = new JSlider(SPEED_L, SPEED_H, SPEED_O);
		currentSpeed = new JLabel("Speed: " + SPEED_O + " milliseconds");
		currentSize = new JLabel("Size: " + SIZE_O + " data points");
		grid = new GridBagConstraints();

		select = new JComboBox<String>();
		for (String option : algorithmOptions) {
			select.addItem(option);
		}
		
		// This section sets the layout for the JPanel
		array.setLayout(new GridBagLayout());
		wrapper.setLayout(new BorderLayout());

		grid.insets = new Insets(0, 1, 0, 1);
		grid.anchor = GridBagConstraints.SOUTH;

		speedControl.setMinorTickSpacing(SPEED_L);
		speedControl.setMajorTickSpacing(SPEED_H);
		speedControl.setPaintTicks(true);
		// This section alters the JLabel as the slider changes
		speedControl.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				currentSpeed.setText("Speed: " + Integer.toString(speedControl.getValue()) + " milliseconds");
				validate();
				SortComplete.sleep = speedControl.getValue();
			}
		});

		sizeControl.setMinorTickSpacing(SIZE_L);
		sizeControl.setMajorTickSpacing(SIZE_H);
		sizeControl.setPaintTicks(true);
		// This section alters the JLabel as the slider changes
		sizeControl.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				currentSize.setText("Size: " + Integer.toString(sizeControl.getValue()) + " data points");
				validate();
				SortComplete.sortDataCounter = sizeControl.getValue();
			}
		});

		button.add(select);
		button.add(currentSpeed);
		button.add(speedControl);
		button.add(currentSize);
		button.add(sizeControl);
		button.add(startVis);

		wrapper.add(button, BorderLayout.NORTH);
		wrapper.add(array);
		add(wrapper);

		setExtendedState(JFrame.MAXIMIZED_BOTH);

		// This section resizes the components for the sorted array
		addComponentListener(new ComponentListener() {
			@Override
			public void componentResized(ComponentEvent e) {
				// This resets the size modifier
				// 90 percent of the windows height divided by the size of the sorted array
				SIZE_MOD = (int) ((getHeight() * 0.9) / dataObj.length);
			}

			@Override public void componentMoved(ComponentEvent e) {}
			@Override public void componentShown(ComponentEvent e) {}
			@Override public void componentHidden(ComponentEvent e) {}
		});
		
		// This section listens for the start button to be pushed and begin sorting
				startVis.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						SortComplete.beginSort((String) select.getSelectedItem());
					}
				});

		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);

	}

	/**
	 * This method will draw the array of data to the JFrame that is inputted.
	 * 
	 * @param data is a randomized set of data inputs
	 */
	public void drawArray(Integer[] data) {
		dataObj = new JPanel[SortComplete.sortDataCounter];
		array.removeAll();
		SIZE_MOD = (int) ((getHeight() * 0.9) / dataObj.length);
		for (int i = 0; i < SortComplete.sortDataCounter; i++) {
			dataObj[i] = new JPanel();
			dataObj[i].setPreferredSize(new Dimension(SortComplete.widthData, data[i] * SIZE_MOD));
			dataObj[i].setBackground(Color.red);
			array.add(dataObj[i], grid);
		}
		repaint();
		validate();
	}

	/**
	 * This method will refresh and redraw the array as the sort is occurring
	 * 
	 * @param data is a set of data inputs
	 */
	public void refreshArray(Integer[] data) {
		refreshArray(data, -1, -1, -1);
	}

	/**
	 * This method will refresh and redraw the array as the sort is occurring
	 * 
	 * @param data is a set of data inputs
	 * @param working is the data index thatis being worked on
	 */
	public void refreshArray(Integer[] data, int working) {
		refreshArray(data, working, -1, -1);
	}

	/**
	 * This method will refresh and redraw the array as the sort is occurring
	 * 
	 * @param data is a set of data inputs
	 * @param working is the data index thatis being worked on
	 * @param comparing is the data index that work is comparing to
	 */
	public void refreshArray(Integer[] data, int working, int comparing) {
		refreshArray(data, working, comparing, -1);
	}

	/**
	 * This method will refresh and redraw the array as the sort is occurring
	 * 
	 * @param data is a set of data inputs
	 * @param working is the data index thatis being worked on
	 * @param comparing is the data index that work is comparing to
	 * @param reading is the data index that is being read
	 */
	public void refreshArray(Integer[] data, int working, int comparing, int reading) {
		array.removeAll();
		for (int i = 0; i < dataObj.length; i++) {
			dataObj[i] = new JPanel();
			dataObj[i].setPreferredSize(new Dimension(SortComplete.widthData, data[i] * SIZE_MOD));
			if (i == working) {
				dataObj[i].setBackground(Color.green);
			} else if (i == comparing) {
				dataObj[i].setBackground(Color.yellow);
			} else if (i == reading) {
				dataObj[i].setBackground(Color.black);
			} else {
				dataObj[i].setBackground(Color.black);
			}
			array.add(dataObj[i], grid);
		}
		repaint();
		validate();
	}

}