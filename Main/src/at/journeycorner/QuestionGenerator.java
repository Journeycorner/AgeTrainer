package at.journeycorner;

import java.text.DateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

public class QuestionGenerator {

    private static QuestionGenerator instance;
    private final DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.LONG, Locale.GERMAN);

    public Question newGuess(int startYear) {
        Date birthdate = getDate(startYear);

        String formattedDate = dateFormat.format(birthdate);
        int correctAnswer = (int)birthdate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate().until(LocalDate.now(), ChronoUnit.YEARS);

        return new Question(formattedDate, correctAnswer);
    }

    private Date getDate(int startYear) {
        GregorianCalendar gc = new GregorianCalendar();
        int year = randBetween(startYear, LocalDateTime.now().getYear());
        int dayOfYear = randBetween(1, gc.getActualMaximum(Calendar.DAY_OF_YEAR));
        gc.set(Calendar.YEAR, year);
        gc.set(Calendar.DAY_OF_YEAR, dayOfYear);
        return gc.getTime();
    }

    private int randBetween(int start, int end) {
        return start + (int) Math.round(Math.random() * (end - start));
    }

    public static QuestionGenerator getInstance() {
        if (instance == null) {
            instance = new QuestionGenerator();
        }
        return instance;
    }

    public class Question {
        private final String formattedDate;
        private final int correctAnswer;

        public Question(String formattedDate, int correctAnswer) {
            this.formattedDate = formattedDate;
            this.correctAnswer = correctAnswer;
        }

        public String getFormattedDate() {
            return this.formattedDate;
        }

        public int getCorrectAnswer() {
            return this.correctAnswer;
        }
    }
}