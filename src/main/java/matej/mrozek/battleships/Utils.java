package matej.mrozek.battleships;

import java.util.Random;

public class Utils {
    private static final Random random = new Random();

    public static int randomRange(int min, int max) {
        int result = random.nextInt(max - min) + min;
        Game.DEBUG.info("Generated random number between " + min + " and " + max + ": " + result);
        return result;
    }

    public static boolean randomBoolean() {
        boolean bool = random.nextBoolean();
        Game.DEBUG.info("Generated random boolean: " + bool);
        return bool;
    }
}
