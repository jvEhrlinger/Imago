/*
Author: James Ehrlinger
The player class is exactly that. It's instantiated as a representation of the player, and it's updated depending on
achievements they get and items they pick up.
*/

import java.util.ArrayList;
import java.util.HashMap;

public class Player {
    public String name;
    private ArrayList<String> inventory;
    private HashMap<String, Boolean> achievements;
    private Room currentRoom;

    public Player(String name, Room startRoom) {
        this.name = name;
        this.currentRoom = startRoom;
        this.inventory = new ArrayList<>();
        this.achievements = new HashMap<>();
    }

    public void addToInventory(String item) {
        this.inventory.add(item);
    }

    public void removeFromInventory(String item) {
        if (this.inventory.contains(item)) {
            this.inventory.remove(item);
        } else {
            System.out.printf(String.format("DEBUG: Attempted removal of %s failed; not in inventory.", item));
        }
    }

    public Boolean has(String item) {
        return this.inventory.contains(item);
    }

    public void printInventory() {
        if (this.inventory.isEmpty()) {
            System.out.println("Your inventory is empty.");
        } else {
            for (String item : this.inventory) {
                System.out.println(item);
            }
        }
    }

    public void moveRoom(String roomName) {
        if (this.currentRoom.isAdjacent(roomName)) {
            this.currentRoom.leave(); //make this so we can remove the playerOccupied flag
            this.currentRoom = this.currentRoom.getAdjacentRoom(roomName);
            this.currentRoom.enter();
        } else {
            System.out.println("You can't go there.");
        }
    }

    public void lookAround() {
        this.currentRoom.lookIntoAdjacentRooms();
    }
}
