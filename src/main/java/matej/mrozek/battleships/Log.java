package matej.mrozek.battleships;

public class Log {
    public Log() {
        System.out.println();
    }

    public Log(String text) {
        new Log(text, true);
    }

    public Log(String text, boolean newLine) {
        if (newLine) {
            System.out.println(text);
        } else {
            System.out.print(text);
        }
    }
}
