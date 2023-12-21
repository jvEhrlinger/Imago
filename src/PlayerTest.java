/*
Author: James Ehrlinger
This is a simple test of functionality for the operations on the player object.
*/

import java.io.IOException;

public class PlayerTest {

    public static void main(String[] args) throws IOException {
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
}
