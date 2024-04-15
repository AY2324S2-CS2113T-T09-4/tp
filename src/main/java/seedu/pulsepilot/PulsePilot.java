package seedu.pulsepilot;

import ui.Handler;

/**
 * Main class representing the entry-point for PulsePilot.
 */
public class PulsePilot {
    //@@author L5-Z
    /**
     * Main entry-point for PulsePilot.
     *
     * @param args Command-line arguments.
     */
    public static void main(String[] args) {
        Handler handler = new Handler();
        handler.initialiseBot();
        handler.processInput();
        handler.terminateBot();
    }
}
