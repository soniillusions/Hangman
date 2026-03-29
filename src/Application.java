import java.util.Scanner;
import java.util.Random;

public class Application {
    public static void main() {
        Random rand = new Random();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println();
            System.out.print("Вы хотите начать новую игру? Y/N: ");
            String answer = scanner.nextLine();

            if (answer.equalsIgnoreCase("Y")) {
                String word = HangmanData.WORDS[rand.nextInt(HangmanData.WORDS.length)];
                Hangman game = new Hangman();
                game.start(word);
            } else {
                System.exit(0);
            }
        }
    }
}