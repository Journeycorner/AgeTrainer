package at.journeycorner;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Scanner;

public class AgeCalculator {

    private final QuestionGenerator questionGenerator = QuestionGenerator.getInstance();
    private int wrongAnswers = 0;
    private int correctAnswers = 0;

    public void run() {
        System.out.println("Wie viele Fragen willst du beantworten?");
        int numberOfQuestions = getInput();

        Instant start = Instant.now();
        for (int i = 0; i < numberOfQuestions; i++) {
            guess(questionGenerator);
        }
        Duration duration = Duration.ofMillis(Instant.now().minus(start.toEpochMilli(), ChronoUnit.MILLIS).toEpochMilli());
        System.out.println("\n" + correctAnswers + " von " + (correctAnswers + wrongAnswers) + " Antworten richtig ("
                + calcPercent(correctAnswers, wrongAnswers) + "%), Zeit: " + formatDuration(duration));
    }

    private String formatDuration(Duration duration) {
        long s = duration.getSeconds();
        return String.format("%d:%02d:%02d", s / 3600, (s % 3600) / 60, (s % 60));
    }

    private double calcPercent(double right, double wrong) {
        return wrong == 0 ? 100 : (right / (right + wrong) * 100);
    }

    private void guess(QuestionGenerator questionGenerator) {
        QuestionGenerator.Question guess = questionGenerator.newGuess(1855);
        System.out.println("\nWie alt ist eine Person heute, wenn sie am " + guess.getFormattedDate()
                + " geboren wurde?");
        int answer = getInput();

        if (answer == guess.getCorrectAnswer()) {
            correctAnswers++;
            System.out.println("korrekt!");
        } else {
            wrongAnswers++;
            System.out.println("leider falsch (richtig: " + guess.getCorrectAnswer() + ")");
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
