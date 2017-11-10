/**
 * P1GUI.java - Constructs the main interface
 * Begun 10/31/17
 * @author Andrew Eissen
 */

//package infixexpressions;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

final class P1GUI extends JFrame {

    // Window-related variables
    private String title;
    private int width;
    private int height;

    // GUI-related variables
    private JFrame mainFrame;
    private JPanel mainPanel, inputPanel, buttonPanel, resultsPanel;
    private JLabel expressionLabel, resultsLabel;
    private JTextField expressionField, resultsField;
    private JButton evaluateButton;

    // Default constructor
    public P1GUI() {
        super("Infix Expression Evaluator");
        this.setWindowTitle("Infix Expression Evaluator");
        this.setWindowWidth(400);
        this.setWindowHeight(175);
    }

    /**
     * Fully parameterized constructor
     * @param title
     * @param width
     * @param height
     */
    public P1GUI(String title, int width, int height) {
        super(title);
        this.setWindowTitle(title);
        this.setWindowWidth(width);
        this.setWindowHeight(height);
    }

    /**
     * Setter for window title
     * @param title
     * @return void
     */
    private void setWindowTitle(String title) {
        this.title = title;
    }

    /**
     * Setter for window width
     * @param width
     * @return void
     */
    private void setWindowWidth(int width) {
        if (width < 400) {
            this.width = 400;
        } else {
            this.width = width;
        }
    }

    /**
     * Setter for window height
     * @param height
     * @return void
     */
    private void setWindowHeight(int height) {
        if (height < 175) {
            this.height = 175;
        } else {
            this.height = height;
        }
    }

    /**
     * Getter for title
     * @return this.title
     */
    private String getWindowTitle() {
        return this.title;
    }

    /**
     * Getter for width
     * @return this.width
     */
    private int getWindowWidth() {
        return this.width;
    }

    /**
     * Getter for height
     * @return this.height
     */
    private int getWindowHeight() {
        return this.height;
    }

    /**
     * Method assembles the GUI
     * @return void
     */
    private void constructGUI() {
        mainPanel = new JPanel(new GridLayout(3, 1, 10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Mini-panels
        inputPanel = new JPanel(new FlowLayout());
        buttonPanel = new JPanel(new FlowLayout());
        resultsPanel = new JPanel(new FlowLayout());

        //Definitions
        expressionLabel = new JLabel("Enter Infix Expression");
        expressionField = new JTextField(20);
        evaluateButton = new JButton("Evaluate");
        resultsLabel = new JLabel("Result");
        resultsField = new JTextField(20);

        // Add elements to proper mini-panels
        inputPanel.add(expressionLabel);
        inputPanel.add(expressionField);

        buttonPanel.add(evaluateButton);

        resultsPanel.add(resultsLabel);
        resultsPanel.add(resultsField);

        // Add mini-panels to the mainPanel
        mainPanel.add(inputPanel);
        mainPanel.add(buttonPanel);
        mainPanel.add(resultsPanel);

        // Results should not be editable
        resultsField.setEditable(false);

        // Handler for click events
        evaluateButton.addMouseListener(new GUIMouseAdapter());

        // Assemble Frame
        mainFrame = new JFrame(this.getWindowTitle());
        mainFrame.setSize(this.getWindowWidth(), this.getWindowHeight());
        mainFrame.setContentPane(mainPanel);
        mainFrame.setResizable(false);
        mainFrame.setVisible(true);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    /**
     * Main method, init
     * @param args
     * @return void
     */
    public static void main(String[] args) {
        P1GUI newGUI = new P1GUI();
        newGUI.constructGUI();
    }

    /**
     * Class for handling button clicks, extends MouseAdapter
     * @see java.awt.event.MouseAdapter
     */
    class GUIMouseAdapter extends MouseAdapter {

        // Default constructor
        public GUIMouseAdapter() {}

        /**
         * Handles mouse clicks of <code>Evaluate</code> button
         * @param e
         * @return void
         */
        @Override
        public void mousePressed(MouseEvent e) {
            if (expressionField.getText().equals("")) {
                return;
            }

            InfixEval evaluator = new InfixEval();
            String expression = expressionField.getText();
            int result = evaluator.evaluateExpression(expression);
            resultsField.setText("" + result);
        }
    }
}