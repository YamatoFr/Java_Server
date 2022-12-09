package threadedServer;

import java.awt.*;
import java.text.NumberFormat;
import java.util.Vector;

import javax.swing.*;
import javax.swing.text.NumberFormatter;

public class GUI {

	// Attributes
	JFrame frame;
	Container contentPane;
	JButton validate;

	public void init() {
		frame = new JFrame("Java Server");
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);

		contentPane = frame.getContentPane();
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));

		validate = new JButton("Validate");

		frame.setVisible(true);
	}

	public void showField(String type, String fieldName, JPanel panel) {
		JLabel label = new JLabel(type + " " + fieldName + " : ");

		JComponent field = null;
		NumberFormat format = NumberFormat.getNumberInstance();
		NumberFormatter formatter = new NumberFormatter(format);

		switch (type) {
			case "String":
				field = new JTextArea("");
				((JTextArea) field).setColumns(20);
				break;

			case "char":
			case "Character":
				field = new JTextField("");
				((JTextField) field).setColumns(20);
				break;

			case "int":
			case "Integer":

				formatter.setValueClass(Integer.class);
				formatter.setMinimum(Integer.MIN_VALUE);
				formatter.setMaximum(Integer.MAX_VALUE);
				formatter.setAllowsInvalid(false);
				field = new JFormattedTextField(formatter);

				break;
			case "double":
			case "Double":
				format.setMinimumFractionDigits(1);
				format.setMaximumFractionDigits(5);
				formatter.setValueClass(Double.class);
				formatter.setMinimum(Double.MIN_VALUE);
				formatter.setMaximum(Double.MAX_VALUE);
				formatter.setAllowsInvalid(false);
				field = new JFormattedTextField(formatter);
				break;
			case "float":
			case "Float":
				format.setMinimumFractionDigits(1);
				format.setMaximumFractionDigits(5);
				formatter.setValueClass(Float.class);
				formatter.setMinimum(Float.MIN_VALUE);
				formatter.setMaximum(Float.MAX_VALUE);
				formatter.setAllowsInvalid(false);
				field = new JFormattedTextField(formatter);

				break;
			case "short":
			case "Short":
				formatter.setValueClass(Short.class);
				formatter.setMinimum(Short.MIN_VALUE);
				formatter.setMaximum(Short.MAX_VALUE);
				formatter.setAllowsInvalid(false);
				field = new JFormattedTextField(formatter);
				break;
			case "byte":
			case "Byte":
				formatter.setValueClass(Byte.class);
				formatter.setMinimum(Byte.MIN_VALUE);
				formatter.setMaximum(Byte.MAX_VALUE);
				formatter.setAllowsInvalid(false);
				field = new JFormattedTextField(formatter);
				break;
			case "boolean":
			case "Boolean":
				Vector<Boolean> item = new Vector<Boolean>();
				item.add(Boolean.TRUE);
				item.add(Boolean.FALSE);
				field = new JComboBox<Boolean>(item);
				break;
			case "long":
			case "Long":
				formatter.setValueClass(Long.class);
				formatter.setMinimum(Long.MIN_VALUE);
				formatter.setMaximum(Long.MAX_VALUE);
				formatter.setAllowsInvalid(false);
				field = new JFormattedTextField(formatter);
				break;
			default:
				System.out.println("Error");
		}

		label.setLabelFor(field);
		panel.add(label);
		field.setPreferredSize(new Dimension(100, 20));

		panel.add(field);
		contentPane.add(panel);
	}
}
