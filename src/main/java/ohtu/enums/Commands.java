package ohtu.enums;

public enum Commands {

    ADD('A'), LIST('L'), EXIT('E');

    private final char command;

    Commands(char command) {
            this.command = command;
    }

    public char getCommand() {
            return this.command;
    }
}
