/*
Author: James Ehrlinger
This houses all the tools for printing out dialogue and interacting with the console in the ways I like. Eventually I'd
like to put in ASCII art in here too, but we'll see about that.
*/
import java.io.*;
import java.sql.Array;
import java.text.CharacterIterator;
import java.text.StringCharacterIterator;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class DialogueUtils {

    static void printCharsStaggered(String line, double delay) throws InterruptedException {
        CharacterIterator itr = new StringCharacterIterator(line);

        while (itr.current() != CharacterIterator.DONE) {

            // Print the current character
            System.out.print(itr.current());

            // Wait for 1 second
            TimeUnit.SECONDS.sleep((long) delay);

            // Get the next letter
            itr.next();
        }
    }

    static void printWordsStaggered(String line, double delay) throws InterruptedException {
        // Cut the words up into an array
        String[] words = line.strip().split(" ");

        // Loop through it, printing each word on its own line with a delay.
        for(String word : words) {
            System.out.println(word);
            TimeUnit.SECONDS.sleep((long) delay);   //need to add the ability to parse <1 sec delays
        }                                           //use the milliseconds unit and multiply by 1000
                                                    //maybe I won't need it?
        System.out.println("\n");
    }

    static String getInput() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.print(">");
        return reader.readLine();
    }

    static void printImage(String filename) throws FileNotFoundException, InterruptedException {
        File file = new File(filename);
        Scanner scanner = new Scanner(file);
        System.out.print("\b");

        ArrayList<String> imgList = new ArrayList<String>();

        while (scanner.hasNextLine()) {
            imgList.add(scanner.nextLine());
        }

        int iterator = 0;
        int bunchIterator = 0;

        System.out.print("\b");

        while(iterator < imgList.size()) {
            StringBuilder builder = new StringBuilder();
            while (bunchIterator < 3 && iterator < imgList.size()) {
                builder.append(imgList.get(iterator));
                builder.append("\n");
                iterator++;
                bunchIterator++;
            }
            System.out.print(builder);
            bunchIterator = 0;
            TimeUnit.SECONDS.sleep(1);

        }
    }
}
