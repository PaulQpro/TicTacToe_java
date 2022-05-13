package edu.project.tictactoe;

import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        tictactoeHelper helper = new tictactoeHelper();
        char res = helper.menu(MenuTypes.UI);
        while (res != 'q') {
            if (res == 'p') {
                int players = helper.PlayersSelector();
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
                res = helper.menu(MenuTypes.CONSOLE);
            }
            else if (res == 'h') {
                helper.help();
                res = helper.menu(MenuTypes.CONSOLE);
            }
        }
        System.out.print("Goodbye!");
    }
}

enum MenuTypes{
    UI,
    CONSOLE
}

class tictactoeHelper {
    private final Scanner scan = new Scanner(System.in);
    public void help(){
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
    public char menu( MenuTypes type){
        char r;
        if(type == MenuTypes.UI) {
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
        do{
            System.out.print("tictactoe\\>");
            r = scan.nextLine().toLowerCase().charAt(0);
            if(!(r=='q'||r=='h'||r=='p'||r=='m')){
                System.out.println("Wrong! Select one from menu");
            }
            else {
                err = false;
            }
        }while (err);
        if(r=='m'){
            return menu(MenuTypes.UI);
        }
        else{
            return r;
        }
    }
    public int PlayersSelector(){
        System.out.println("Enter number of players (1 or 2)");
        String input = scan.nextLine();
        input = input.trim();
        int players = 0;
        while (!(input.equals("1") || input.equals("2"))) {
            System.out.println("Wrong input, type 1 or 2");
            input = scan.nextLine();
            input = input.trim();
        }
        return Integer.parseInt(input);
    }
    public String SymbolSelector() {
        System.out.println("Select symbol ('X' or '0')");
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

public class tictactoe  {
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
            this.game(1);
        } else {
            this.game(2);
        }
    }

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
                if(pl == 1){
                    bot();
                }
                else {
                    player(2);
                }
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
