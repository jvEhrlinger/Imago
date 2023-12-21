/*
Author: James Ehrlinger
Rooms are instantiated to build the map. They contain lists for adjacent rooms, items, and booleans for whether
the player and killer are 'in' them. When a move method is called on the player or killer, they consult their rooms to
see if the move is valid. When pickup methods are called, we also consult the room to see if the item is there.
*/

import java.util.ArrayList;
import java.util.Locale;
import java.util.Objects;

public class Room {
    private final String roomName;
    private ArrayList<Room> adjacentRooms;
    private ArrayList<String> itemList;
    public Boolean playerOccupied;

    private final String entranceMessage;
    private final String killerMessage;

    public Room(
            String roomName,
            String entranceMessage,
            String killerMessage
    ) {
        this.roomName = roomName;
        this.entranceMessage = entranceMessage;
        this.killerMessage = killerMessage;

        this.adjacentRooms = new ArrayList<>();
        this.itemList = new ArrayList<>();

        this.playerOccupied = false;
    }

    public Boolean isAdjacent(String roomName) {
        for (Room room : adjacentRooms) {
            if (Objects.equals(roomName.strip().toLowerCase(), room.roomName.toLowerCase())) {
                return true;
            }
        }
        return false;
    }

    public Room getAdjacentRoom(String roomName) {
        for (Room room : adjacentRooms) {
            if (Objects.equals(roomName.strip().toLowerCase(), room.roomName.toLowerCase())) {
                return room;
            }
        }
        //This should never happen. This is only called after the room is checked for adjacency.
        //Could probably roll this and the above into one function which is called on an attempted room move.
        //Future me problems.
        return null;
    }

    public void addAdjacentRoom(Room room) {
        this.adjacentRooms.add(room);
    }

    public void addItem(String string) {
        this.itemList.add(string);
    }

    public void enter() {
        this.playerOccupied = true;
        System.out.printf("You enter the %s.\n", this.roomName);
        System.out.println(this.entranceMessage);
    }

    public void lookIntoAdjacentRooms() {
        System.out.println("You can see the...");
        for (Room room : adjacentRooms) {
            System.out.println(room.roomName);
        }
    }

    public void leave() {
        this.playerOccupied = false;
    }

    public void killerEnter() {
        System.out.print("\b");
        System.out.println(killerMessage);
        System.out.print(">");
    }
}
