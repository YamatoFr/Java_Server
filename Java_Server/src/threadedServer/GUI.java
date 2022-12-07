package threadedServer;

import java.awt.*;
import javax.swing.*;

public class GUI extends JPanel {
    private JLabel jcomp1;
    private JTextField jcomp2;
    private JTextField jcomp3;
    private JLabel jcomp4;
    private JLabel jcomp5;
    private JLabel jcomp6;
    private JLabel jcomp7;
    private JLabel jcomp8;
    private JTextArea jcomp9;
    private JTextField jcomp10;
    private JTextField jcomp11;
    private JTextField jcomp12;
    private JTextField jcomp13;
    private JButton jcomp14;
    private JLabel jcomp15;
    private JTextField jcomp16;

    public GUI() {
        // construct components
        jcomp1 = new JLabel("First name");
        jcomp2 = new JTextField(5);
        jcomp3 = new JTextField(5);
        jcomp4 = new JLabel("Last name");
        jcomp5 = new JLabel("eMail");
        jcomp6 = new JLabel("Age");
        jcomp7 = new JLabel("Birth day");
        jcomp8 = new JLabel("Birth month");
        jcomp9 = new JTextArea(5, 5);
        jcomp10 = new JTextField(5);
        jcomp11 = new JTextField(5);
        jcomp12 = new JTextField(5);
        jcomp13 = new JTextField(5);
        jcomp14 = new JButton("OK");
        jcomp15 = new JLabel("Birth year");
        jcomp16 = new JTextField(5);

        // adjust size and set layout
        setPreferredSize(new Dimension(344, 417));
        setLayout(null);

        // add components
        add(jcomp1);
        add(jcomp2);
        add(jcomp3);
        add(jcomp4);
        add(jcomp5);
        add(jcomp6);
        add(jcomp7);
        add(jcomp8);
        add(jcomp9);
        add(jcomp10);
        add(jcomp11);
        add(jcomp12);
        add(jcomp13);
        add(jcomp14);
        add(jcomp15);
        add(jcomp16);

        // set component bounds (only needed by Absolute Positioning)
        jcomp1.setBounds(15, 15, 100, 25);
        jcomp2.setBounds(120, 15, 200, 25);
        jcomp3.setBounds(120, 45, 200, 25);
        jcomp4.setBounds(15, 45, 100, 25);
        jcomp5.setBounds(15, 75, 100, 25);
        jcomp6.setBounds(15, 105, 100, 25);
        jcomp7.setBounds(15, 135, 100, 25);
        jcomp8.setBounds(15, 165, 100, 25);
        jcomp9.setBounds(15, 275, 305, 125);
        jcomp10.setBounds(120, 75, 200, 25);
        jcomp11.setBounds(120, 105, 200, 25);
        jcomp12.setBounds(120, 135, 200, 25);
        jcomp13.setBounds(120, 165, 200, 25);
        jcomp14.setBounds(120, 240, 100, 25);
        jcomp15.setBounds(15, 195, 100, 25);
        jcomp16.setBounds(120, 195, 200, 25);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("MyPanel");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.getContentPane().add(new GUI());
        frame.pack();
        frame.setVisible(true);
    }
}
