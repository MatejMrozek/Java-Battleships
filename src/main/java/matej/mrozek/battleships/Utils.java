package matej.mrozek.battleships;

import java.util.Random;

public class Utils {
    private static final Random random = new Random();

    public static int randomRange(int min, int max) {
        return random.nextInt(max - min) + min;
    }

    public static boolean randomBoolean() {
        return random.nextBoolean();
    }
}
