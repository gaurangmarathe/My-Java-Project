import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class Calculator implements ActionListener {

    // Frame and components
    private JFrame frame;
    private JTextField displayField;
    private JPanel buttonPanel;

    // Variables for calculations
    private double num1 = 0;
    private double num2 = 0;
    private char operator;
    private boolean isNewNumber = true;

    // Button labels
    private String[] buttonLabels = {
        "7", "8", "9", "/",
        "4", "5", "6", "*",
        "1", "2", "3", "-",
        "C", "0", "=", "+"
    };

    public Calculator() {
        // Initialize the frame
        frame = new JFrame("Simple Calculator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout(5, 5));
        frame.setResizable(false);
        frame.setLocationRelativeTo(null); // Center the frame

        // Initialize the display field
        displayField = new JTextField();
        displayField.setEditable(false);
        displayField.setFont(new Font("Arial", Font.BOLD, 24));
        displayField.setHorizontalAlignment(JTextField.RIGHT);
        displayField.setText("0");

        // Initialize the button panel
        buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(4, 4, 10, 10)); // 4x4 grid with spacing

        // Add buttons to the panel
        for (String label : buttonLabels) {
            JButton button = new JButton(label);
            button.setFont(new Font("Arial", Font.BOLD, 18));
            button.addActionListener(this); // Add action listener to each button
            buttonPanel.add(button);
        }

        // Add components to the frame
        frame.add(displayField, BorderLayout.NORTH);
        frame.add(buttonPanel, BorderLayout.CENTER);

        // Adjust frame size and make it visible
        frame.pack();
        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        if (command.matches("[0-9]")) {
            // Handle number buttons
            if (isNewNumber) {
                displayField.setText(command);
                isNewNumber = false;
            } else {
                displayField.setText(displayField.getText() + command);
            }
        } else if (command.equals("C")) {
            // Handle clear button
            displayField.setText("0");
            num1 = 0;
            num2 = 0;
            operator = ' ';
            isNewNumber = true;
        } else if (command.equals("=")) {
            // Handle equals button
            if (operator != ' ' && !isNewNumber) {
                num2 = Double.parseDouble(displayField.getText());
                double result = calculate(num1, num2, operator);
                displayField.setText(String.valueOf(result));
                num1 = result; // Set result for potential chained operations
                isNewNumber = true;
            }
        } else {
            // Handle operator buttons (+, -, *, /)
            if (!isNewNumber) {
                num1 = Double.parseDouble(displayField.getText());
                operator = command.charAt(0);
                isNewNumber = true;
            }
        }
    }

    private double calculate(double n1, double n2, char op) {
        switch (op) {
            case '+':
                return n1 + n2;
            case '-':
                return n1 - n2;
            case '*':
                return n1 * n2;
            case '/':
                if (n2 == 0) {
                    JOptionPane.showMessageDialog(frame, "Cannot divide by zero.", "Error", JOptionPane.ERROR_MESSAGE);
                    return 0; // Or handle as an error
                }
                return n1 / n2;
            default:
                return 0;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Calculator());
    }
}