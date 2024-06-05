public class ColorfulOutput {
    // ANSI escape codes for text color
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

    public static void main(String[] args) {
        System.out.println(ANSI_RED + "This is red text." + ANSI_RESET);
        System.out.println(ANSI_GREEN + "This is green text." + ANSI_RESET);
        System.out.println(ANSI_YELLOW + "This is yellow text." + ANSI_RESET);
        System.out.println(ANSI_BLUE + "This is blue text." + ANSI_RESET);
        System.out.println(ANSI_PURPLE + "This is purple text." + ANSI_RESET);
        System.out.println(ANSI_CYAN + "This is cyan text." + ANSI_RESET);
        System.out.println(ANSI_WHITE + "This is white text." + ANSI_RESET);
    }
}
