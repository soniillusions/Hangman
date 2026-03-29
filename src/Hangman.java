import java.util.Scanner;

public class Hangman {
    private String word;
    private int attempts = 7;
    private boolean[] letterGuessed = new boolean[26];
    private String[] guessedLetters;
    private int currentAttempt = -1;

    public Hangman() {
        letterGuessed = new boolean[26];
        currentAttempt = 0;
    }

    public void start(String word) {
        this.word = word;
        this.currentAttempt = -1;
        this.attempts = 7;
        this.letterGuessed = new boolean[26];
        this.guessedLetters = new String[word.length()];

        while (attempts > 0) {
            String input = getAndValidateUserInput();
            boolean continueGame = processInput(input);
            if (!continueGame) {
                break;
            }
        }
    }

    public String getAndValidateUserInput() {
        Scanner scanner = new Scanner(System.in);
        String answer;
        while (true) {
            System.out.println();
            System.out.print("Введите букву: ");
            answer = scanner.nextLine().toLowerCase();
            if (answer.length() != 1) {
                System.out.println("Пожалуйста, введите одну букву!");
            } else {
                break;
            }
        }
        return answer;
    }

    public String[] getWordMask() {
        String[] mask = new String[word.length()];

        for (int i = 0; i < word.length(); i++) {
            if (guessedLetters[i] != null) {
                mask[i] = guessedLetters[i];
            } else {
                mask[i] = "(_)";
            }
        }
        return mask;
    }

    public boolean processInput(String input) {
        char letter = input.charAt(0);
        int index = letter - 'a';

        if (isAlreadyGuessed(index)) {
            System.out.println("Эта буква уже была");
            printStatus(getWordMask());
            return true;
        }

        markAsGuessed(index);

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

    private boolean isAlreadyGuessed(int index) {
        return index >= 0 && index < 26 && letterGuessed[index];
    }

    private void markAsGuessed(int index) {
        if (index >= 0 && index < 26) {
            letterGuessed[index] = true;
        }
    }

    private void handleCorrectGuess(char letter) {
        updateMask(letter);
        System.out.println("\n Отлично! Буква '" + letter + "' есть в слове!");
        printStatus(getWordMask());

        if (isWin()) {
            System.out.println("Поздравляю! Вы угадали слово: " + word);
        }
    }

    private void handleWrongGuess(char letter) {
        attempts--;
        currentAttempt++;

        System.out.println("\n Буквы '" + letter + "' нет в слове!");
        System.out.println(HangmanData.HANGMANPICS[currentAttempt]);

        printStatus(getWordMask());

        if (attempts == 0) {
            System.out.println("Вы проиграли! Загаданное слово: " + word);
        }
    }

    private boolean isWin() {
        return checkWinCondition(getWordMask());
    }

    public void updateMask(char letter) {
        for (int i = 0; i < word.length(); i++) {
            if (word.charAt(i) == letter) {
                guessedLetters[i] = String.valueOf(letter);
            }
        }
    }

    public void printStatus(String[] mask) {
        System.out.println("\n" + String.join("", mask));

        StringBuilder guessed = new StringBuilder("Используемые буквы: ");
        for (int i = 0; i < 26; i++) {
            if (letterGuessed[i]) {
                guessed.append((char)('a' + i)).append(" ");
            }
        }

        System.out.println(guessed.toString());

        System.out.println("Осталось попыток: " + attempts);
    }

    public boolean checkWinCondition(String[] mask) {
        for (String s : mask) {
            if (s.equals("(_)")) {
                return false;
            }
        }
        return true;
    }
}