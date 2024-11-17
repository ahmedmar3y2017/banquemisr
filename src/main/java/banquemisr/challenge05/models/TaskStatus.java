package banquemisr.challenge05.models;

import java.util.Random;

public enum TaskStatus {
    TODO, IN_PROGRESS, DONE;

    public String toString() {
        switch (this) {
            case TODO:
                return "Todo";
            case IN_PROGRESS:
                return "In_Progress";
            case DONE:
                return "Done";

        }
        return "";
    }
    private static final Random PRNG = new Random();

    public static TaskStatus randomDirection()  {
        TaskStatus[] directions = values();
        return directions[PRNG.nextInt(directions.length)];
    }


}
