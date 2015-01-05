import java.util.Scanner;

public class AgeCalculator {

    private int wrongAnswers = 0;
    private int correctAnswers = 0;
    private final QuestionGenerator questionGenerator = QuestionGenerator.getInstance();

    public void run() {
        System.out.println("Wie viele Fragen wollen Sie beantworten?");
        int numberOfQuestions = getInput();

        for (int i = 0; i < numberOfQuestions; i++) {
            guess(questionGenerator);
        }
        System.out.println(correctAnswers + " von " + (correctAnswers + wrongAnswers) + " Antworten richtig (" + calcPercent(correctAnswers, wrongAnswers) + "%).");
    }

    private double calcPercent(double right, double wrong) {
        return wrong == 0 ? 100 :(right/(right+wrong)*100);
    }

    private void guess(QuestionGenerator questionGenerator) {
        QuestionGenerator.Question guess = questionGenerator.newGuess(1855);
        System.out.println("Wie alt ist eine Person heute, wenn sie am " + guess.getFormattedDate() + " geboren wurde?");
        int answer = getInput();

        if (answer == guess.getCorrectAnswer()) {
            correctAnswers++;
            System.out.println("Korrekt!");
        } else {
            wrongAnswers++;
            System.out.println("Leider falsch (richtig: " + guess.getCorrectAnswer() + ")");
        }
    }

    private int getInput() {
        Scanner sc = new Scanner(System.in);
        while (sc.hasNext()) {
            if (sc.hasNextInt()) {
                return sc.nextInt();
            }
            sc.nextLine(); //  skip invalid input line
            System.out.println("Eingabe nicht korrekt, bitte nochmal:");
        }
        return 0;
    }
}