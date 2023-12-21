import java.io.IOException;
import java.util.*;

/*
Author: James Ehrlinger
MotherBrain is the game controller, and what we run when we want to play. It builds the current map, puts the player in it,
and then handles parsing commands.
*/
public class MotherBrain {
    /*
    TODO: Making a big ol' todo for everything that did not get done tonight.
    Room navigation is mostly serviceable, at the very least it won't throw errors anymore.
    Biggest next step is probably adding the killer, and designing it's movement
    Q's -> Can I delete the '>' character and write to the console while the player still hasn't submitted an action?
        -> Do I need to implement a permanent tick system to time the killer's entrance? Or could I use something like
           a thread or accumulator?
    */
    private static ArrayList<Room> roomList;
    private static Player player;
    private static Killer killer;
    private static Killer chasingKiller;

    private static void makeGame() throws IOException {
        //build the house test map
        //First floor -> Kitchen, Living Room, Bathroom
        //Second floor -> Bedroom, Office, Attic

        Room kitchen = new Room(
                "Kitchen",
                "A pale yellow light reflects off the tile floor. The hum of the fridge can be heard.",
                "The front door opens."
        );

        Room livingRoom = new Room(
                "Living Room",
                "The furniture is covered, but it hasn't stopped the smell from leaking out. There is a large black spot on the ceiling, dripping into the carpet.",
                "Heavy footsteps can be heard on the carpet."
        );

        Room bathRoom = new Room(
                "Bathroom",
                "The bathtub is stained a brown-orange color, the room smells like iron.",
                "Glass shattering can be heard."
        );

        Room stairs = new Room(
                "Stairs",
                "The wood has begun to rot, and steps have cracked in places.",
                "The stairs creek loudly."
        );

        Room bedRoom = new Room(
                "Master Bedroom",
                "The smell makes your stomach turn. Something is on the bed. It's wet, and moving.",
                "He is in the master bedroom."
        );

        Room kidsBedRoom = new Room(
                "Child's Bedroom",
                "The walls are decorated with crayon drawings, and blocks and model cars are scattered on the floor.",
                "He is in the child's bedroom."
        );

        Room office = new Room(
                "Office",
                "Warm light bathes bookshelves that flank a desk. Papers are scattered all over it.",
                "He is in the office."
        );

        //Room objects made, time to add adjacency
        kitchen.addAdjacentRoom(livingRoom);
        kitchen.addAdjacentRoom(bathRoom);

        bathRoom.addAdjacentRoom(kitchen);

        livingRoom.addAdjacentRoom(kitchen);
        livingRoom.addAdjacentRoom(stairs);

        stairs.addAdjacentRoom(livingRoom);
        stairs.addAdjacentRoom(bedRoom);
        stairs.addAdjacentRoom(kidsBedRoom);
        stairs.addAdjacentRoom(office);

        bedRoom.addAdjacentRoom(stairs);
        bedRoom.addAdjacentRoom(kidsBedRoom);
        bedRoom.addAdjacentRoom(office);

        kidsBedRoom.addAdjacentRoom(stairs);
        kidsBedRoom.addAdjacentRoom(bedRoom);
        kidsBedRoom.addAdjacentRoom(office);

        office.addAdjacentRoom(stairs);
        office.addAdjacentRoom(bedRoom);
        office.addAdjacentRoom(kidsBedRoom);

        //adjacency complete, adding rooms to room list
        roomList = new ArrayList<>();

        roomList.add(kitchen);
        roomList.add(livingRoom);
        roomList.add(bathRoom);
        roomList.add(stairs);
        roomList.add(bedRoom);
        roomList.add(kidsBedRoom);
        roomList.add(office);

        //ask for a name, make the player, put them in the Kitchen.
        System.out.println("Player Name?");
        player = new Player(DialogueUtils.getInput(), kitchen);
        kitchen.enter();

        ArrayList<Room> path = new ArrayList<Room>();

        path.add(kitchen);
        path.add(bathRoom);
        path.add(livingRoom);
        path.add(stairs);
        path.add(bedRoom);
        path.add(kidsBedRoom);
        path.add(office);

        killer = new Killer(path, false);
        chasingKiller = new Killer(path, true);
    }

    enum CommandType {
        LOOK,
        MOVE,
        QUIT
    }

    private static Boolean parseCommand() throws IOException {
        String command = DialogueUtils.getInput();

        String[] comArgs = command.strip().split(" ");

        CommandType comType;
        switch (comArgs[0].toLowerCase()) {
            case "look" -> comType = CommandType.LOOK;
            case "move" -> comType = CommandType.MOVE;
            case "quit" -> comType = CommandType.QUIT;
            default -> {
                System.out.println("You can't do that.");
                return true;
            }
        }
        StringBuilder moveTarget = new StringBuilder();

        if (comType == CommandType.MOVE) {
            List<String> comList = Arrays.asList(comArgs);

            for (int i = 1; i < comList.size(); i++) {
                moveTarget.append(comList.get(i));
                moveTarget.append(" ");
            }
        }

        switch (comType) {
            case LOOK -> player.lookAround();
            case MOVE -> player.moveRoom(String.valueOf(moveTarget));
            case QUIT -> { return false; }
        }
        return true;
    }

    public static void main(String[] args) throws IOException {
        makeGame();
        killer.start();
        Boolean waitingToStart = true;
        while (true) {
            if (!parseCommand()) {
                return;
            }
            if (!killer.isAlive() && waitingToStart) {
                chasingKiller.start();
                waitingToStart = false;
            }
        }
    }
}

