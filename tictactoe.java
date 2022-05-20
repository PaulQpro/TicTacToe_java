package edu.project.tictactoe;

import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        tictactoeHelper helper = new tictactoeHelper();
        char res = helper.menu(MenuTypes.UI);
        while (res != 'q') {
            if (res == 'p') {
                helper.finalPrep();
            } else if (res == 'h') {
                helper.help();
                res = helper.menu(MenuTypes.CONSOLE);
            }
        }
        System.out.print("Goodbye!");
    }
}

/**/
/*For menu displaying*/enum MenuTypes {
    UI,
    CONSOLE
}

/*Preparation for game*/class tictactoeHelper {
    private final Scanner scan = new Scanner(System.in);
    private String[] name = new String[2];

    /*Player's name input*/
    public void nameSelector(int pl) {
        System.out.print("Player №" + pl + ":Enter your name\n>");
        String Name = scan.nextLine().trim();
        boolean err = true;
        while (err) {
            if (Name.equals("")) {
                System.out.print("Emtry input, enter again!\n>");
                Name = scan.nextLine().trim();
            } else {
                err = false;
            }
        }
        this.name[pl - 1] = Name;
    }

    /*Auto-selecting 2nd player's symbol('0' or 'X') and final preparations*/
    public void finalPrep() {
        int players = this.playersSelector();
        String pl1 = this.symbolSelector();
        String pl2 = "0";
        switch (pl1) {
            case "X":
                pl2 = "0";
                break;
            case "0":
                pl2 = "X";
                break;
        }
        nameSelector(1);
        if (players == 2) {
            nameSelector(2);
        } else {
            this.name[1] = "Bot";
        }
        tictactoe game = new tictactoe(players, pl1, pl2, this.name);
        game.start();
        this.menu(MenuTypes.CONSOLE);
    }

    /*Help menu*/
    public void help() {
        System.out.println("   ┌────────────────────┐");
        System.out.println("═══╡This is a 3*3 board ╞═══");
        System.out.println("   ╞════════════════════╡");
        System.out.println("   │      A   B   C     │");
        System.out.println("   │    ╔═══╦═══╦═══╗   │");
        System.out.println("   │  1 ║   ║   ║   ║   │");
        System.out.println("   │    ╠═══╬═══╬═══╣   │");
        System.out.println("───┤  2 ║   ║   ║   ║   ├───");
        System.out.println("   │    ╠═══╬═══╬═══╣   │");
        System.out.println("   │  3 ║   ║   ║   ║   │");
        System.out.println("   │    ╚═══╩═══╩═══╝   │");
        System.out.println("   └────────────────────┘");
        System.out.println("To put symbol onto in you have to type:\n    1) 'A1', 'B2', 'C1', etc.;\n    2) 'a1', 'b2', 'c1', etc.;");
        System.out.println("and not:\n    1) '1A', '2B', '1C', etc.;\n    2) '1a', '2b', '1c', etc.;");
        System.out.println("P.S.: If you don't no common tic-tac-toe rules,\n      search in Google, not here!");
    }

    /*Main menu*/
    public char menu(MenuTypes type) {
        char r;
        if (type == MenuTypes.UI) {
            System.out.println("═══════════╦═════════════╦═══════════");
            System.out.println("───────────╢ Tic-Tac-Toe ╟───────────");
            System.out.println("═══════════╩═════════════╩═══════════");
            System.out.println("        ───┤ 'P' -- Play ├───");
            System.out.println("        ───┤ 'H' -- Help ├───");
            System.out.println("        ───┤ 'Q' -- Quit ├───");
            System.out.println("        ───┤ 'M' -- Menu ├───");
            System.out.println("═════════════════════════════════════");
        }
        boolean err = true;
        do {
            System.out.print("tictactoe\\>");
            r = scan.nextLine().toLowerCase().charAt(0);
            if (!(r == 'q' || r == 'h' || r == 'p' || r == 'm')) {
                System.out.println("Wrong! Select one from menu");
            } else {
                err = false;
            }
        } while (err);
        if (r == 'm') {
            return menu(MenuTypes.UI);
        } else {
            return r;
        }
    }

    /*Selecting Players count(1,2)*/
    public int playersSelector() {
        System.out.print("Enter number of players (1 or 2)\n>");
        String input = scan.nextLine();
        input = input.trim();
        int players = 0;
        while (!(input.equals("1") || input.equals("2"))) {
            System.out.print("Wrong input, type 1 or 2\n>");
            input = scan.nextLine();
            input = input.trim();
        }
        return Integer.parseInt(input);
    }

    /*Selecting 1st player's symbol('X' or '0')*/
    private String symbolSelector() {
        System.out.print("Select symbol ('X' or '0')\n>");
        String SYM = scan.nextLine();
        SYM = SYM.toUpperCase();
        boolean cont = false;
        while (!cont) {
            switch (SYM) {
                case "X":
                case "Х":
                case "+":
                    SYM = "X";
                    cont = true;
                    break;
                case "0":
                case "O":
                case "О":
                    SYM = "0";
                    cont = true;
                    break;
                default:
                    System.out.print("Wrong input, type 'X' or '0'\n>");
            }
            if (cont) {
                break;
            }
            SYM = scan.nextLine();
        }
        return SYM;
    }
}

/*Game itself*/public class tictactoe {
    /*Game field*/private String[][] board = new String[][]{{" ", " ", " "}, {" ", " ", " "}, {" ", " ", " "}};
    /*Players count*/private int players;
    /*Players' symbols*/private String[] sym = new String[2];
    /**/private String[] names = new String[2];
    private final Scanner scan = new Scanner(System.in);

    public tictactoe(int players, String p1, String p2, String[] names) {
        this.sym[0] = p1;
        this.sym[1] = p2;
        this.players = players;
        this.names = names;
    }

    /*Starting game*/
    public void start() {
        this.print();
        System.out.println(players);
        if (this.players == 1) {
            this.game(1);
        } else {
            this.game(2);
        }
    }

    /*Moves*/
    private void game(int pl) {
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
                if (pl == 1) {
                    bot();
                } else {
                    player(2);
                }
                print();
                if (checkWin()) {
                    return;
                }
            }
        }
    }

    /*Player's move*/
    private void player(int player) {
        boolean err = true;
        int x = 0;
        int y = 0;
        while (err) {
            if (player == 1) {
                System.out.print(this.names[0] + ", select place for move\n>");
            } else {
                System.out.print(this.names[1] + ", select place for move\n>");
            }
            String place = scan.nextLine();
            /*Validating input*/
            if (place.length() == 2) {
                if (place.substring(1).equals("1") || place.substring(1).equals("2") || place.substring(1).equals("3")) {
                    x = Integer.parseInt(place.substring(1));
                    switch (place.substring(0, 1).toUpperCase()) {
                        case "A":
                        case "А":
                            y = 1;
                            err = false;
                            break;
                        case "B":
                        case "В":
                            y = 2;
                            err = false;
                            break;
                        case "C":
                        case "С":
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
            board[x][y] = this.sym[0];
        } else {
            board[x][y] = this.sym[1];
        }
    }

    /*Bot's move*/
    private void bot() {
        boolean check = false;
        /*cheking for possible win*/
        for (int i = 0; i < 3; i++) {
            if (board[i][0].equals(sym[1]) && board[i][1].equals(sym[1]) && board[i][2].equals(" ")) {
                board[i][2] = sym[1];
                check = true;
                break;
            } else if (board[i][0].equals(sym[1]) && board[i][1].equals(" ") && board[i][2].equals(sym[1])) {
                board[i][1] = sym[1];
                check = true;
                break;
            } else if (board[i][0].equals(" ") && board[i][1].equals(sym[1]) && board[i][2].equals(sym[1])) {
                board[i][0] = sym[1];
                check = true;
                break;
            }
            if (board[0][i].equals(sym[1]) && board[1][i].equals(sym[1]) && board[2][i].equals(" ")) {
                board[2][i] = sym[1];
                check = true;
                break;
            } else if (board[0][i].equals(sym[1]) && board[1][i].equals(" ") && board[2][i].equals(sym[1])) {
                board[1][i] = sym[1];
                check = true;
                break;
            } else if (board[0][i].equals(" ") && board[1][i].equals(sym[1]) && board[2][i].equals(sym[1])) {
                board[0][i] = sym[1];
                check = true;
                break;
            }
        }
        /*cheking for possible win (continue)*/
        if (!check) {
            if (board[0][0].equals(sym[1]) && board[1][1].equals(sym[1]) && board[2][2].equals(" ")) {
                board[2][2] = sym[1];
                check = true;
            } else if (board[0][0].equals(sym[1]) && board[1][1].equals(" ") && board[2][2].equals(sym[1])) {
                board[1][1] = sym[1];
                check = true;
            } else if (board[0][0].equals(" ") && board[1][1].equals(sym[1]) && board[2][2].equals(sym[1])) {
                board[0][0] = sym[1];
                check = true;
            }
            if (board[0][2].equals(sym[1]) && board[1][1].equals(sym[1]) && board[2][0].equals(" ")) {
                board[2][0] = sym[1];
                check = true;
            } else if (board[0][2].equals(sym[1]) && board[1][1].equals(" ") && board[2][0].equals(sym[1])) {
                board[1][1] = sym[1];
                check = true;
            } else if (board[0][2].equals(" ") && board[1][1].equals(sym[1]) && board[2][0].equals(sym[1])) {
                board[0][2] = sym[1];
                check = true;
            }
        }
        /*cheking for possible lose*/
        for (int i = 0; i < 3; i++) {
            if (check) {
                break;
            }
            if (board[i][0].equals(sym[0]) && board[i][1].equals(sym[0]) && board[i][2].equals(" ")) {
                board[i][2] = sym[1];
                check = true;
                break;
            } else if (board[i][0].equals(sym[0]) && board[i][1].equals(" ") && board[i][2].equals(sym[0])) {
                board[i][1] = sym[1];
                check = true;
                break;
            } else if (board[i][0].equals(" ") && board[i][1].equals(sym[0]) && board[i][2].equals(sym[0])) {
                board[i][0] = sym[1];
                check = true;
                break;
            }
            if (board[0][i].equals(sym[0]) && board[1][i].equals(sym[0]) && board[2][i].equals(" ")) {
                board[2][i] = sym[1];
                check = true;
                break;
            } else if (board[0][i].equals(sym[0]) && board[1][i].equals(" ") && board[2][i].equals(sym[0])) {
                board[1][i] = sym[1];
                check = true;
                break;
            } else if (board[0][i].equals(" ") && board[1][i].equals(sym[0]) && board[2][i].equals(sym[0])) {
                board[0][i] = sym[1];
                check = true;
                break;
            }
        }
        /*cheking for possible lose (continue)*/
        if (!check) {
            if (board[0][0].equals(sym[0]) && board[1][1].equals(sym[0]) && board[2][2].equals(" ")) {
                board[2][2] = sym[1];
                check = true;
            } else if (board[0][0].equals(sym[0]) && board[1][1].equals(" ") && board[2][2].equals(sym[0])) {
                board[1][1] = sym[1];
                check = true;
            } else if (board[0][0].equals(" ") && board[1][1].equals(sym[0]) && board[2][2].equals(sym[0])) {
                board[0][0] = sym[1];
                check = true;
            }
            if (board[0][2].equals(sym[0]) && board[1][1].equals(sym[0]) && board[2][0].equals(" ")) {
                board[2][0] = sym[1];
                check = true;
            } else if (board[0][2].equals(sym[0]) && board[1][1].equals(" ") && board[2][0].equals(sym[0])) {
                board[1][1] = sym[1];
                check = true;
            } else if (board[0][2].equals(" ") && board[1][1].equals(sym[0]) && board[2][0].equals(sym[0])) {
                board[0][2] = sym[1];
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
        }//if can't win or lose
        this.board[x][y] = sym[1];
    }

    /*Cheking for win*/
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

    /*Displaying field*/
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
