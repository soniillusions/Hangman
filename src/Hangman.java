import java.util.*;

public class Hangman {
    private String word;
    private int attempts = 7;
    private int currentAttempt;
    private Set<Character> guessedLettersSet;
    private List<Character> guessedMask;

    public void start(String word) {
        initGame(word);
        printWordMask();

        while (attempts > 0) {
            String input = getAndValidateUserInput();
            boolean continueGame = processInput(input.charAt(0));

            if (!continueGame) {
                break;
            }
        }
    }

    private void initGame(String word) {
        this.word = word;
        this.currentAttempt = 0;
        this.attempts = 7;
        this.guessedLettersSet = new HashSet<>();
        this.guessedMask = new ArrayList<>(Collections.nCopies(word.length(), null));
    }

    private String getAndValidateUserInput() {
        Scanner scanner = new Scanner(System.in);
        String answer;
        while (true) {
            System.out.println();
            System.out.print("Введите букву: ");

            answer = scanner.nextLine().toLowerCase();

            if (answer.length() != 1) {
                System.out.println("Пожалуйста, введите одну букву!");
                continue;
            }

            if (answer.matches("\\d+")) {
                System.out.println("Это число!");
                continue;
            }

            if (guessedLettersSet.contains(answer.charAt(0))) {
                System.out.println("Эта буква уже была!");
                continue;
            }
            return answer;
        }

    }

    private boolean processInput(char letter) {
        if (isLetterInWord(letter)) {
            handleCorrectGuess(letter);
            return !isWin();
        } else {
            handleWrongGuess(letter);
            return attempts > 0;
        }
    }

    private boolean isLetterInWord(char letter) {
        return word.indexOf(letter) != -1;
    }

    private void handleCorrectGuess(char letter) {
        guessedLettersSet.add(letter);
        updateMask(letter);
        System.out.println("\n Отлично! Буква '" + letter + "' есть в слове!");
        printStatus();

        if (isWin()) {
            System.out.println("Поздравляю! Вы угадали слово: " + word);
        }
    }

    private void handleWrongGuess(char letter) {
        guessedLettersSet.add(letter);
        System.out.println(HangmanData.HANGMANPICS[currentAttempt]);
        attempts--;
        currentAttempt++;

        System.out.println("\n Буквы '" + letter + "' нет в слове!");
        printStatus();

        if (attempts == 0) {
            System.out.println("Вы проиграли! Загаданное слово: " + word);
        }
    }

    private void updateMask(char letter) {
        for (int i = 0; i < word.length(); i++) {
            if (word.charAt(i) == letter) {
                guessedMask.set(i, letter);
            }
        }
    }

    private boolean isWin() {
        for (Character c : guessedMask) {
            if (c == null) return false;
        }
        return true;
    }

    private void printStatus() {
        printWordMask();
        printGuessedLetters();
        printAttempts();
    }

    private void printWordMask() {
        StringBuilder maskDisplay = new StringBuilder();
        for (Character c : guessedMask) {
            maskDisplay.append(c == null ? "(_)" : c);
        }
        System.out.println("\n" + maskDisplay);
    }

    private void printGuessedLetters() {
        StringBuilder guessedDisplay = new StringBuilder("Используемые буквы: ");
        for (char c : guessedLettersSet) {
            guessedDisplay.append(c).append(" ");
        }
        System.out.println(guessedDisplay);
    }

    private void printAttempts() {
        System.out.println("Осталось попыток: " + attempts);
    }
}