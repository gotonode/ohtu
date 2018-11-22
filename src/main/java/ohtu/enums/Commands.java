package ohtu.enums;

public enum Commands {

    ADD('A'), LIST('L'), EXIT('E'), MODIFY('M'), HELP('X'), DELETE('D');

    private final char command;

    Commands(char command) {
            this.command = command;
    }

    public char getCommand() {
            return this.command;
    }
}
