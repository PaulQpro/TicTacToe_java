import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter number of players (1 or 2)");
        String input = scan.nextLine();
        input = input.trim();
        int players = 0;
        while (!(input.equals("1") || input.equals("2"))) {
            System.out.println("Wrong input, type 1 or 2");
            input = scan.nextLine();
            input = input.trim();
        }
        players = Integer.parseInt(input);
        tictactoeHelper helper = new tictactoeHelper();
        String pl1 = helper.SymbolSelector();
        String pl2 = "0";
        switch (pl1) {
            case "X":
                pl2 = "0";
                break;
            case "0":
                pl2 = "X";
                break;
        }
        tictactoe game = new tictactoe(players, pl1, pl2);
        game.start();
    }
}

class tictactoeHelper {
    public String SymbolSelector() {
        Scanner scan = new Scanner(System.in);
        System.out.println("Select symbol ('X' or '0')");
        String SYM = scan.nextLine();
        boolean cont = false;
        while (!cont) {
            switch (SYM) {
                case "X":
                case "x":
                case "Х":
                case "х":
                case "+":
                    SYM = "X";
                    cont = true;
                    break;
                case "0":
                case "O":
                case "o":
                case "О":
                case "о":
                    SYM = "0";
                    cont = true;
                    break;
                default:
                    System.out.println("Wrong input, type 'X' or '0'");
            }
            if (cont) {
                break;
            }
            SYM = scan.nextLine();
        }
        return SYM;
    }
}

public class tictactoe {
    private String[][] board = new String[][]{{" ", " ", " "}, {" ", " ", " "}, {" ", " ", " "},};
    private int players = 1;
    private String p1;
    private String p2;
    private final Scanner scan = new Scanner(System.in);

    public tictactoe(int players, String p1, String p2) {
        this.p1 = p1;
        this.p2 = p2;
        this.players = players;
    }

    public void start() {
        this.print();
        System.out.println(players);
        if (this.players == 1) {
            this.game1P();
        } else {
            this.game2P();
        }
    }

    private void game2P() {
        for (int i = 0; i < 9; i++) {
            player(1);
            print();
            if (checkWin()) {
                return;
            }
            i++;
            if (i % 2 != 0) {
                if (i == 9) {
                    break;
                }
                player(2);
                print();
                if (checkWin()) {
                    return;
                }
            }
        }
    }

    private void game1P() {
        for (int i = 0; i < 9; i++) {
            player(1);
            print();
            if (checkWin()) {
                return;
            }
            i++;
            if (i % 2 != 0) {
                if (i == 9) {
                    break;
                }
                bot();
                print();
                if (checkWin()) {
                    return;
                }
            }
        }
    }

    private void player(int player) {
        boolean err = true;
        int x = 0;
        int y = 0;
        while (err) {
            if (player == 1) {
                System.out.println("Select place: (" + this.p1 + ")");
            } else {
                System.out.println("Select place: (" + this.p2 + ")");
            }
            String place = scan.nextLine();
            if (place.length() == 2) {
                if (place.substring(1).equals("1") || place.substring(1).equals("2") || place.substring(1).equals("3")) {
                    x = Integer.parseInt(place.substring(1));
                    switch (place.substring(0, 1)) {
                        case "A":
                        case "a":
                        case "А":
                        case "а":
                            y = 1;
                            err = false;
                            break;
                        case "B":
                        case "b":
                        case "В":
                        case "в":
                            y = 2;
                            err = false;
                            break;
                        case "C":
                        case "c":
                        case "С":
                        case "с":
                            y = 3;
                            err = false;
                            break;
                        default:
                            System.out.println("Error, select again!");
                            err = true;
                            break;
                    }
                    x -= 1;
                    y -= 1;
                    if (err == false) {
                        if (board[x][y].equals(" ")) {
                            break;
                        } else {
                            err = true;
                            System.out.println("Error, select again!");
                        }
                    }
                } else {
                    err = true;
                    System.out.println("Error, select again!");
                }
            } else {
                err = true;
                System.out.println("Error, select again!");
            }
        }
        if (player == 1) {
            board[x][y] = this.p1;
        } else {
            board[x][y] = this.p2;
        }
    }

    private void bot() {
        boolean check = false;
        for (int i = 0; i < 3; i++) {
            if (board[i][0].equals(p2) && board[i][1].equals(p2) && board[i][2].equals(" ")) {
                board[i][2] = p2;
                check = true;
                break;
            } else if (board[i][0].equals(p2) && board[i][1].equals(" ") && board[i][2].equals(p2)) {
                board[i][1] = p2;
                check = true;
                break;
            } else if (board[i][0].equals(" ") && board[i][1].equals(p2) && board[i][2].equals(p2)) {
                board[i][0] = p2;
                check = true;
                break;
            }
            if (board[0][i].equals(p2) && board[1][i].equals(p2) && board[2][i].equals(" ")) {
                board[2][i] = p2;
                check = true;
                break;
            } else if (board[0][i].equals(p2) && board[1][i].equals(" ") && board[2][i].equals(p2)) {
                board[1][i] = p2;
                check = true;
                break;
            } else if (board[0][i].equals(" ") && board[1][i].equals(p2) && board[2][i].equals(p2)) {
                board[0][i] = p2;
                check = true;
                break;
            }
        }
        if (!check) {
            if (board[0][0].equals(p2) && board[1][1].equals(p2) && board[2][2].equals(" ")) {
                board[2][2] = p2;
                check = true;
            } else if (board[0][0].equals(p2) && board[1][1].equals(" ") && board[2][2].equals(p2)) {
                board[1][1] = p2;
                check = true;
            } else if (board[0][0].equals(" ") && board[1][1].equals(p2) && board[2][2].equals(p2)) {
                board[0][0] = p2;
                check = true;
            }
            if (board[0][2].equals(p2) && board[1][1].equals(p2) && board[2][0].equals(" ")) {
                board[2][0] = p2;
                check = true;
            } else if (board[0][2].equals(p2) && board[1][1].equals(" ") && board[2][0].equals(p2)) {
                board[1][1] = p2;
                check = true;
            } else if (board[0][2].equals(" ") && board[1][1].equals(p2) && board[2][0].equals(p2)) {
                board[0][2] = p2;
                check = true;
            }
        }
        for (int i = 0; i < 3; i++) {
            if (check) {
                break;
            }
            if (board[i][0].equals(p1) && board[i][1].equals(p1) && board[i][2].equals(" ")) {
                board[i][2] = p2;
                check = true;
                break;
            } else if (board[i][0].equals(p1) && board[i][1].equals(" ") && board[i][2].equals(p1)) {
                board[i][1] = p2;
                check = true;
                break;
            } else if (board[i][0].equals(" ") && board[i][1].equals(p1) && board[i][2].equals(p1)) {
                board[i][0] = p2;
                check = true;
                break;
            }
            if (board[0][i].equals(p1) && board[1][i].equals(p1) && board[2][i].equals(" ")) {
                board[2][i] = p2;
                check = true;
                break;
            } else if (board[0][i].equals(p1) && board[1][i].equals(" ") && board[2][i].equals(p1)) {
                board[1][i] = p2;
                check = true;
                break;
            } else if (board[0][i].equals(" ") && board[1][i].equals(p1) && board[2][i].equals(p1)) {
                board[0][i] = p2;
                check = true;
                break;
            }
        }
        if (!check) {
            if (board[0][0].equals(p1) && board[1][1].equals(p1) && board[2][2].equals(" ")) {
                board[2][2] = p2;
                check = true;
            } else if (board[0][0].equals(p1) && board[1][1].equals(" ") && board[2][2].equals(p1)) {
                board[1][1] = p2;
                check = true;
            } else if (board[0][0].equals(" ") && board[1][1].equals(p1) && board[2][2].equals(p1)) {
                board[0][0] = p2;
                check = true;
            }
            if (board[0][2].equals(p1) && board[1][1].equals(p1) && board[2][0].equals(" ")) {
                board[2][0] = p2;
                check = true;
            } else if (board[0][2].equals(p1) && board[1][1].equals(" ") && board[2][0].equals(p1)) {
                board[1][1] = p2;
                check = true;
            } else if (board[0][2].equals(" ") && board[1][1].equals(p1) && board[2][0].equals(p1)) {
                board[0][2] = p2;
                check = true;
            }
        }
        if (check == true) {
            return;
        }
        int x = (int) (Math.random() * 3);
        int y = (int) (Math.random() * 3);
        while (board[x][y] != " ") {
            x = (int) (Math.random() * 3);
            y = (int) (Math.random() * 3);
        }
        this.board[x][y] = p2;
    }

    private boolean checkWin() {
        for (int i = 0; i < 3; i++) {
            if (board[i][0].equals("X") && board[i][1].equals("X") && board[i][2].equals("X")) {
                System.out.println("'X' wins!");
                return true;
            }
            if (board[i][0].equals("0") && board[i][1].equals("0") && board[i][2].equals("0")) {
                System.out.println("'0' wins!");
                return true;
            }
        }
        for (int i = 0; i < 3; i++) {
            if (board[0][i].equals("X") && board[1][i].equals("X") && board[2][i].equals("X")) {
                System.out.println("'X' wins!");
                return true;
            }
            if (board[0][i].equals("0") && board[1][i].equals("0") && board[2][i].equals("0")) {
                System.out.println("'0' wins!");
                return true;
            }
        }
        if (board[0][0].equals("X") && board[1][1].equals("X") && board[2][2].equals("X")) {
            System.out.println("'X' wins!");
            return true;
        }
        if (board[0][0].equals("0") && board[1][1].equals("0") && board[2][2].equals("0")) {
            System.out.println("'0' wins!");
            return true;
        }
        if (board[0][2].equals("X") && board[1][1].equals("X") && board[2][0].equals("X")) {
            System.out.println("'X' wins!");
            return true;
        }
        if (board[0][2].equals("0") && board[1][1].equals("0") && board[2][0].equals("0")) {
            System.out.println("'0' wins!");
            return true;
        }
        return false;
    }

    public void print() {
        System.out.println("    A   B   C");
        System.out.println("  ╔═══╦═══╦═══╗");
        System.out.println("1 ║ " + this.board[0][0] + " ║ " + this.board[0][1] + " ║ " + this.board[0][2] + " ║");
        System.out.println("  ╠═══╬═══╬═══╣");
        System.out.println("2 ║ " + this.board[1][0] + " ║ " + this.board[1][1] + " ║ " + this.board[1][2] + " ║");
        System.out.println("  ╠═══╬═══╬═══╣");
        System.out.println("3 ║ " + this.board[2][0] + " ║ " + this.board[2][1] + " ║ " + this.board[2][2] + " ║");
        System.out.println("  ╚═══╩═══╩═══╝");
    }
}
