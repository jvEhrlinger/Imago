import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

/*
Author: James Ehrlinger
Object to represent the killer.
*/
public class Killer extends Thread {
    Room currentRoom;
    Boolean isChasing;
    Player player;

    ArrayList<Room> path;
    Boolean isRunning;

    public Killer(ArrayList<Room> path, Boolean isChasing) {
        this.currentRoom = path.get(0);
        this.path = path;
        this.isChasing = isChasing;
    }

    @Override
    public void run() {
        if (!this.isChasing) {
            //We aren't chasing, we're waiting.
            int waitCount = 0;
            while (waitCount < 120) {
                waitCount++;
                try {
                    TimeUnit.SECONDS.sleep(1);   //need to add the ability to parse <1 sec delays
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        } else {
            //We're chasing now
            this.isRunning = true;
            while(this.isRunning) {
                if (this.currentRoom.playerOccupied) {
                    try {
                        DialogueUtils.printImage("resources\\face.txt");
                        DialogueUtils.printWordsStaggered("HE IS", 1);
                        DialogueUtils.printCharsStaggered("HERE", 1);
                    } catch (FileNotFoundException | InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    this.isRunning = false;
                    System.exit(0);
                }
                this.currentRoom.killerEnter();
                try {
                    TimeUnit.SECONDS.sleep(10);   //need to add the ability to parse <1 sec delays
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                this.nextPath();
            }

        }
    }

    public void nextPath() {
        this.path.remove(0);
        this.currentRoom = path.get(0);
    }
}
