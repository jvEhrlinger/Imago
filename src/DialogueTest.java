/*
Author: James Ehrlinger
This is a simple test demonstrating proof-of-concept for a progressive dialogue system in which lines are printed with
a specified delay in between. While important for stretching certain moments, I'd also like to use it to potentially
stagger the printing of certain ASCII art to build dread. Conceptually, this is probably most like Iron Lung's use of
static developed images as the main vehicle for the horror- at least that's what I hope anyway.
*/

import java.util.concurrent.TimeUnit;

public class DialogueTest {

    public static void main(String[] args) throws InterruptedException {
        System.out.println("This is a test process for a demonstration of a progressive text system.");

        System.out.println("Begin Test 1 -> simple printing with wait statements in between");
        System.out.println("This is line 1. There will be a 1 second delay before printing line 2.");

        TimeUnit.SECONDS.sleep(1);

        System.out.println("This is line 2. We're going to break up each word in the next line into it's own line, with a 1 second delay in between.");

        DialogueUtils.printWordsStaggered("This is the staggered word test.", 1);

        System.out.println("Our next test is the staggered character test. Each character is printed in-line with a delay between them.");

        DialogueUtils.printCharsStaggered("This is the staggered character test.", 1);
    }


}



