import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class GuessingGameGUI extends JFrame {
    private int secretNumber;
    private int attempts;
    private JTextField textField;
    private JLabel resultLabel;

    public GuessingGameGUI() {
        setTitle("Guessing Game");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new FlowLayout());

        // Generate the secret number
        secretNumber = (int) (Math.random() * 100) + 1;
        attempts = 0;

        // Add a label and text field for user input
        JLabel promptLabel = new JLabel("Guess a number between 1 and 100:");
        textField = new JTextField(10);
        textField.addActionListener(new GuessListener());
        add(promptLabel);
        add(textField);

        // Add a label to display the result
        resultLabel = new JLabel("");
        add(resultLabel);

        setVisible(true);
    }

    private class GuessListener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            String text = textField.getText();
            int guess = Integer.parseInt(text);
            attempts++;

            if (guess < secretNumber) {
                resultLabel.setText("Too low! Try again.");
            } else if (guess > secretNumber) {
                resultLabel.setText("Too high! Try again.");
            } else {
                resultLabel.setText("Congratulations! You guessed the number in " + attempts + " attempts.");
                textField.setEditable(false);
            }

            textField.setText("");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new GuessingGameGUI();
            }
        });
    }
}
