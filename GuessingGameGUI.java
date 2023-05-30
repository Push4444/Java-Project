import javax.swing.*;

public class GuessingGame {

    public static void main(String[] args) {
        int computerNumber = (int) (Math.random() * 100 + 1);
        int userAnswer = 0;
        System.out.println("The Correct guess would be: " + computerNumber);

        int count = 1;
        while (userAnswer != computerNumber) {
            String response = JOptionPane.showInputDialog(null, "Enter your Guess Number between 1 to 100", "Guessing game", 3);
            userAnswer = Integer.parseInt(response);
            JOptionPane.showMessageDialog(null, determineGuess(userAnswer, computerNumber, count));
            count ++;
        }
    }

    public static String determineGuess(int userAnswer, int computerNumber, int count) {
        if (userAnswer <= 0 || userAnswer > 100)
            return "Your guess is wrong";
        else if (userAnswer == computerNumber)
            return "Correct Guess!! Total Guesses Count: " + count;
        else if (userAnswer > computerNumber)
            return "Your guess is too high!!\nTry Again: " + count;
        else if (userAnswer < computerNumber)
            return "Your guess is too low!!\nTry Again: " + count;
        else
            return "Your guess is incorrect \nTry Again: " + count;
    }
}
