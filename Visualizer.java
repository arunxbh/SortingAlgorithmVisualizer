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
	private final int SIZE_H = 500;
	private final int SPEED_O = 20;
	private final int SPEED_L = 1;
	private final int SPEED_H = 1000;

	private final String[] algorithmOptions = { "Merge Sort", "Selection Sort", "Insertion Sort" };

	private int SIZE_MOD;

	private JPanel wrapper;
	private JPanel arrayWrapper;
	private JPanel buttonWrapper;
	private JPanel[] dataObj;
	private JButton startVis;
	private JComboBox<String> select;
	private JSlider speedControl;
	private JSlider sizeControl;
	private JLabel currentSpeed;
	private JLabel currentSize;
	private GridBagConstraints grid;
	private JCheckBox step;

	public Visualizer() {
		super("Sorting Algorithim Visualizing Program");

		// This section initializes all the instance variables
		startVis = new JButton("Start");
		buttonWrapper = new JPanel();
		arrayWrapper = new JPanel();
		wrapper = new JPanel();
		sizeControl = new JSlider(SIZE_L, SIZE_H, SIZE_O);
		speedControl = new JSlider(SPEED_L, SPEED_H, SPEED_O);
		currentSpeed = new JLabel("Speed: " + SPEED_O + " milliseconds");
		currentSize = new JLabel("Size: " + SIZE_O + " data points");
		step = new JCheckBox("Stepped Data");
		grid = new GridBagConstraints();

		select = new JComboBox<String>();
		for (String option : algorithmOptions) {
			select.addItem(option);
		}
		
		// This section sets the layout for the JPanel
		arrayWrapper.setLayout(new GridBagLayout());
		wrapper.setLayout(new BorderLayout());

		grid.insets = new Insets(0, 1, 0, 1);
		grid.anchor = GridBagConstraints.SOUTH;

		startVis.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SortComplete.beginSort((String) select.getSelectedItem());
			}
		});

		step.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SortComplete.stepped = step.isSelected();
			}
		});

		speedControl.setMinorTickSpacing(SPEED_L);
		speedControl.setMajorTickSpacing(SPEED_H);
		speedControl.setPaintTicks(true);

		speedControl.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				currentSpeed.setText("Speed: " + Integer.toString(speedControl.getValue()) + " milliseconds");
				validate();
				SortComplete.sleep = speedControl.getValue();
			}
		});

		sizeControl.setMinorTickSpacing(SIZE_L);
		sizeControl.setMajorTickSpacing(SIZE_H);
		sizeControl.setPaintTicks(true);

		sizeControl.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg1) {
				currentSize.setText("Size: " + Integer.toString(sizeControl.getValue()) + " data points");
				validate();
				SortComplete.sortDataCounter = sizeControl.getValue();
			}
		});

		buttonWrapper.add(step);
		buttonWrapper.add(currentSpeed);
		buttonWrapper.add(currentSize);
		buttonWrapper.add(speedControl);
		buttonWrapper.add(sizeControl);
		buttonWrapper.add(startVis);
		buttonWrapper.add(select);

		wrapper.add(buttonWrapper, BorderLayout.SOUTH);
		wrapper.add(arrayWrapper);

		add(wrapper);

		setExtendedState(JFrame.MAXIMIZED_BOTH);

		addComponentListener(new ComponentListener() {

			@Override
			public void componentResized(ComponentEvent e) {
				// This resets the size modifier
				// 90 percent of the windows height divided by the size of the sorted array
				SIZE_MOD = (int) ((getHeight() * 0.9) / dataObj.length);
			}

			@Override
			public void componentMoved(ComponentEvent e) {
			}

			@Override
			public void componentShown(ComponentEvent e) {
			}

			@Override
			public void componentHidden(ComponentEvent e) {
			}

		});

		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);

	}

	public void drawArray(Integer[] data) {
		dataObj = new JPanel[SortComplete.sortDataCounter];
		arrayWrapper.removeAll();
		SIZE_MOD = (int) ((getHeight() * 0.9) / dataObj.length);
		for (int i = 0; i < SortComplete.sortDataCounter; i++) {
			dataObj[i] = new JPanel();
			dataObj[i].setPreferredSize(new Dimension(SortComplete.widthBlock, data[i] * SIZE_MOD));
			dataObj[i].setBackground(Color.red);
			arrayWrapper.add(dataObj[i], grid);
		}
		repaint();
		validate();
	}

	public void refreshArray(Integer[] x) {
		refreshArray(x, -1);
	}

	public void refreshArray(Integer[] x, int y) {
		refreshArray(x, y, -1);
	}

	public void refreshArray(Integer[] x, int y, int z) {
		refreshArray(x, y, z, -1);
	}

	public void refreshArray(Integer[] data, int working, int comparing, int reading) {
		arrayWrapper.removeAll();
		for (int i = 0; i < dataObj.length; i++) {
			dataObj[i] = new JPanel();
			dataObj[i].setPreferredSize(new Dimension(SortComplete.widthBlock, data[i] * SIZE_MOD));
			if (i == working) {
				dataObj[i].setBackground(Color.green);
			} else if (i == comparing) {
				dataObj[i].setBackground(Color.yellow);
			} else if (i == reading) {
				dataObj[i].setBackground(Color.black);
			} else {
				dataObj[i].setBackground(Color.black);
			}
			arrayWrapper.add(dataObj[i], grid);
		}
		repaint();
		validate();
	}

}
