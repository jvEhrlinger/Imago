import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class TestLibrary {

    // This is a simple test of functionality for the operations on the player object.
    private static void playerTest() throws IOException {
        System.out.println("This is the player test. Start by giving the player a name.");
        String name = DialogueUtils.getInput();
        Player testPlayer = new Player(name, null);

        System.out.printf("The player's name is %s\n", testPlayer.name);

        System.out.println("Name an item to give to the player.");

        String item = DialogueUtils.getInput();

        testPlayer.addToInventory(item);

        System.out.println("Printing inventory...");

        testPlayer.printInventory();

        System.out.printf("Attempting to remove the item %s\n", item);

        testPlayer.removeFromInventory(item);

        System.out.println("Printing inventory...");

        testPlayer.printInventory();
    }

    // This is a simple test demonstrating proof-of-concept for a progressive dialogue system in which lines are printed with
    // a specified delay in between.
    private static void dialogueTest() throws InterruptedException {
        System.out.println("This is a test process for a demonstration of a progressive text system.");

        System.out.println("Begin Test 1 -> simple printing with wait statements in between");
        System.out.println("This is line 1. There will be a 1 second delay before printing line 2.");

        TimeUnit.SECONDS.sleep(1);

        System.out.println("This is line 2. We're going to break up each word in the next line into it's own line, with a 1 second delay in between.");

        DialogueUtils.printWordsStaggered("This is the staggered word test.", 1);

        System.out.println("Our next test is the staggered character test. Each character is printed in-line with a delay between them.");

        DialogueUtils.printCharsStaggered("This is the staggered character test.", 1);
    }

    public static void main(String[] args) throws InterruptedException, IOException {
        playerTest();
        dialogueTest();
    }

}
