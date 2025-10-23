import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.*;
import org.apache.commons.lang3.StringUtils;

class Main {
    static Game game = null;
    static Player[] players = null;
    static int totalGamesLaunched = 0;
    static boolean startFlag = true;
    //    static boolean restartFlag = true;
    static boolean menuFlag = true;
    static boolean gameStarted = false;
    static boolean movesFlag = true;
    static boolean movesBreakFlag = true;
    static String exceptText1 = """

                                Please, start the game before trying do some
                                actions in the game, which doesn't exist yet!
                                """;
    public static void main(String[] args) {

        while(startFlag) {
            menuFlag = true;
            System.out.println("\t\t\t\tWelcome to the Tic Tac Toe!");
            System.out.println("\t\t\t\t(games totally played: " + totalGamesLaunched + ")\n");
            showPromts();

            int playerResponse = safeMenuResponse();

            while(menuFlag) {
                if(playerResponse == 1) {
                    if(gameStarted) {
                        menuFlag = false;
                        movesFlag = true;
                        movesBreakFlag = true;
                        game.setToOneVal(0);
                        game.setToOneVal(" ___ ");
                        game.gameFieldToDisp[1][1] = "|___|";
                        gamePlay();
                    } else {
                        System.out.println("\nYou haven't started a game yet. Please, type (2) to start a new one!\n");
                        showPromts();
                        playerResponse = safeMenuResponse();
                    }

                } else if(playerResponse == 2) {
                    menuFlag = false;
                    movesFlag = true;
                    movesBreakFlag = true;
                    if(gameStarted) {
                        boolean checkExitFlag = true;
                        menuPrintPlayerInfo();
                        System.out.print("\nPlease, enter 1 if you want to proceed or 0 if you want to interrupt: ");
                        Scanner scCheckExit = new Scanner(System.in);
                        while(checkExitFlag) {
                            int checkExit = safeIntInput(scCheckExit);
                            System.out.println();
                            if(checkExit == 1) {
                                checkExitFlag = false;
                                menuResetPlayerInfo();
                            } else if(checkExit == 0) {
                                checkExitFlag = false;
                                menuFlag = false;
                                System.out.println("You have interrupted the exit...\n");
                                showPromts();
                                playerResponse = safeMenuResponse();
                            } else {
                                System.out.println("\nPlease, enter the proper value!\n");
                            }
                        }
                    }
                    totalGamesLaunched = 0;
                    game = new Game();
                    gameStarted = true;
                    Scanner scGame = new Scanner(System.in);

                    // Main game logic
                    // Player chooses a side:
                    chooseNicks(scGame);
                    while(game.players[0].getNick().equals(game.players[1].getNick())) {
                        System.out.println("Please, choose different nicknames!");
                        chooseNicks(scGame);
                    }

                    boolean XPossible = true;
                    boolean OPossible = true;

                    for(int p=0; p<=1; p++) {
                        int playerOrder = p + 1;
                        boolean preGameplayFlag = true;
                        while(preGameplayFlag) {
                            System.out.println("Player " + playerOrder + ", choose a side (\"X\" or \"O\") (1)");
                            System.out.println("Player " + playerOrder + ", change a nick (2)");
                            System.out.println("Player " + playerOrder + ", proceed (3)\n");

                            int playerChoice = safeIntInput(scGame);
                            if(playerChoice == 1) {
                                boolean sideCheckFlag = true;
                                while(sideCheckFlag) {
                                    System.out.print("\nEnter \"X\" or \"O\" as your side choice: ");
                                    char sideChoice = scGame.next().toLowerCase().charAt(0);
                                    System.out.println(sideChoice);
                                    if(sideChoice == 'x') {
                                        if(XPossible) {
                                            sideCheckFlag = false;
                                            XPossible = false;
                                            preGameplayFlag = false;
                                            game.players[p].setSide(sideChoice);
                                        } else {
                                            System.out.println("Sorry, but \"X\" is already taken. Please, choose \"O\"");
                                        }
                                    } else if(sideChoice == 'o') {
                                        if(OPossible) {
                                            sideCheckFlag = false;
                                            OPossible = false;
                                            preGameplayFlag = false;
                                            game.players[p].setSide(sideChoice);
                                        }
                                    } else {
                                        System.out.println("Please, enter either \"X\", or \"O\"!");
                                    }
                                }
                            } else if(playerChoice == 2) {
                                System.out.print("\nPlease, enter your nick: ");
                                String newNick = scGame.next();
                                System.out.println();
                                game.players[p].setNick(newNick);
                            } else if(playerChoice == 3) {
                                System.out.println(XPossible);
                                System.out.println(OPossible);
                                if(XPossible != OPossible) {
                                    System.out.println("\nPlease, choose the side firstly!\n");
                                } else {
                                    preGameplayFlag = false;
                                }
                            } else {
                                System.out.println("\nPlease, enter the proper value!\n");
                            }
                        }
                    }

                    // Gameplay algorithms:
                    Game.displayField(game.gameFieldToDisp);
                    if(game.players[1].side == 'x') {
                        Player playerTemp = game.players[0];
                        game.players[0] = game.players[1];
                        game.players[1] = playerTemp;
                    }
                    gamePlay();

                } else if(playerResponse == 3) {
                    menuFlag = false;
                    menuPrintPlayerInfo();


                } else if (playerResponse == 4) {
                    menuFlag = false;
                    menuResetPlayerInfo();

                } else if(playerResponse == 5) {
                    System.out.println("\n\t\t\t\tTHE RULES\n");
                    System.out.println("\tI'm sure, you know all the rules, my dear friend)");
                    System.out.println("Have a nice game! (Press \"Enter\" to proceed)\n");

                    Scanner scRulesProceed = new Scanner(System.in);
                    scRulesProceed.nextLine();
                    menuFlag = false;

                } else if(playerResponse == 6) {
                    System.out.println("\n\nExit...");
                    System.out.println("\nSEE YOU SOON!");
                    menuFlag = false;
                    startFlag = false;
                } else {
                    System.out.println("\nPlease, choose another option!\n");
                    playerResponse = safeMenuResponse();
                    showPromts();
                }
            }
        }


    }

    public static int safeMenuResponse() {
        boolean menuFlag = true;
        int[] possibleResponses = {1, 2, 3, 4, 5, 6};
        Scanner scMenu = new Scanner(System.in);
        int playerResponse = 0;
        while(menuFlag) {
            playerResponse = safeIntInput(scMenu);
            for(int response: possibleResponses) {
                if(playerResponse == response) {
                    menuFlag = false;
                    break;
                }
            }
            if(menuFlag) {
                System.out.println("Please, enter the proper kind of response (1, 2, 3, 4, 5 or 6)!");
            }
        }
        return playerResponse;
    }

    public static void showPromts() {
        int _totalGamesLaunched = totalGamesLaunched + 1;
        System.out.println("Start the " + _totalGamesLaunched + " game (1)");
        System.out.println("Start new game (2)");
        System.out.println("Show all the players' statistics (3)");
        System.out.println("Reset all players' statistics (4)");
        System.out.println("The rules (5)");
        System.out.println("Exit the entire program (6)\n");
    }

    public static void chooseNicks(Scanner localScanner) {
        for(int p=0; p<=1; p++) {
            int playerOrder = p + 1;
            System.out.print("\nPlayer " + playerOrder + ", please, enter your nick: ");
            String nick = localScanner.next();
            System.out.println();
            game.players[p] = new Player(nick, '-', 0, 0,0, 0,0);
        }
    }

    public static void menuResetPlayerInfo() {
        if(gameStarted) {
            menuFlag = false;
            players = game.players;
            for(Player player: players) {
                player.resetPlayerInfo();
            }
            System.out.println("\nThe information about the both players was reset!\n");
        } else {
            System.out.println(exceptText1);
        }
    }

    public static void menuPrintPlayerInfo() {
        if(gameStarted) {
            menuFlag = false;
            players = game.players;
            for(Player player: players) {
                player.printPlayerInfo();
            }
        } else {
            System.out.println(exceptText1);
        }
    }

    public static int safeIntInput(Scanner sc) {
        String possibleInp = sc.next();
        while(!StringUtils.isNumeric(possibleInp)) {
            System.out.println("\nPlease, enter a numeric positive integer number!\n");
            possibleInp = sc.next();
        }
        return Integer.parseInt(possibleInp);
    }

    public static void gamePlay() {
        while(movesFlag) {
            for(Player playerInGame: game.players) {
                if(movesBreakFlag) {
                    System.out.println("\n" + playerInGame.getNick() + ", your turn (" + playerInGame.getSide() + ")\n");
                    game.moveAndChoose(playerInGame);
                    game.gameFieldToDisp[1][1] = StringFunc.addStrings(game.gameFieldToDisp[1][1].trim(), "|");
                    playerInGame.setCoords(0, 1);
                    playerInGame.setCoords(1, 1);
                    if(game.victoryCheck() == 9) {
                        game.victoryCase(game.players[0]);
                        game.lossCase(game.players[1]);
                        break;
                    } else if(game.victoryCheck() == 15) {
                        game.victoryCase(game.players[1]);
                        game.lossCase(game.players[0]);
                        break;
                    } else if(game.victoryCheck() == -1) {
                        System.out.println("\nIT'S A DRAW!!!\n\n");
                        Player localPlayerX = game.players[0];
                        Player localPlayerO = game.players[1];

                        localPlayerX.setDraws(localPlayerX.getDraws() + 1);
                        localPlayerO.setDraws(localPlayerO.getDraws() + 1);
                        localPlayerX.updateScore();
                        localPlayerO.updateScore();
                        localPlayerX.setGamesPlayed(localPlayerX.getGamesPlayed() + 1);
                        localPlayerO.setGamesPlayed(localPlayerO.getGamesPlayed() + 1);
                        movesFlag = false;
                        break;
                    }
                } else {
                    break;
                }
            }
        }
        totalGamesLaunched++;
    }
}

class Game {
    Player[] players = new Player[2];

    int[][] gameFieldValues = {{0, 0, 0}, {0, 0, 0}, {0, 0, 0}};
    String[][] gameFieldToDisp = {{" ___ ", " ___ ", " ___ "}, {" ___ ", "|___|", " ___ "}, {" ___ ", " ___ ", " ___ "}};

    public void moveAndChoose(Player player) {
        char side = player.getSide();
        int numSide = side == 'x' ? 3 : 5;
        int[] currentPlayerCoords = {1, 1};

        char[] possibleDirections = {'w', 'a', 's', 'd', '0'};
        boolean directionFlag = true;
        boolean continueFlag = false;

        System.out.println();
        displayField(gameFieldToDisp);
        System.out.println();

        while(directionFlag) {
            Scanner scMove = new Scanner(System.in);
            System.out.println("\n===============================================");
            System.out.print("\nControl your movements by typing \"w\", \"s\", \"d\" or \"a\", choose: \"0\", esc.: \"e\": ");
            String sMove = scMove.next();
            char move = sMove.toLowerCase().charAt(0);
            System.out.println();

            for(char possibleDir: possibleDirections) {
                if(move == possibleDir) {
                    continueFlag = true;
                    break;
                }
            }

            if(move == 'e' && sMove.length() == 1) {
                System.out.println("Interrupt...");
                System.out.println("Interrupt...");
                System.out.println("Interrupt...\n");
                Main.movesFlag = false;
                Main.movesBreakFlag = false;
                break;
            }

            if(move == '0' && sMove.length() == 1) {
                if(gameFieldValues[currentPlayerCoords[0]][currentPlayerCoords[1]] == 0) {
                    gameFieldValues[currentPlayerCoords[0]][currentPlayerCoords[1]] = numSide;
                    gameFieldToDisp[currentPlayerCoords[0]][currentPlayerCoords[1]] = " _" + side + "_ ";
                    displayField(gameFieldToDisp);
                    break;
                } else {
                    System.out.println("This cell is already full!\n");
                    displayField(gameFieldToDisp);
                }

            }

            if(continueFlag && sMove.length() == 1) {
                if(coordBoundCheck(move, currentPlayerCoords)) {
                    switch(move) {
                        case 'w' -> {
                            pointerCheck();
                            player.setCoords(0, currentPlayerCoords[0] - 1);
                            currentPlayerCoords = player.getCoords();
                            choosingProcess(currentPlayerCoords);
                        }
                        case 's' -> {
                            pointerCheck();
                            player.setCoords(0, currentPlayerCoords[0] + 1);
                            currentPlayerCoords = player.getCoords();
                            choosingProcess(currentPlayerCoords);
                        }
                        case 'd' -> {
                            pointerCheck();
                            player.setCoords(1, currentPlayerCoords[1] + 1);
                            currentPlayerCoords = player.getCoords();
                            choosingProcess(currentPlayerCoords);
                        }
                        case 'a' -> {
                            pointerCheck();
                            player.setCoords(1, currentPlayerCoords[1] - 1);
                            currentPlayerCoords = player.getCoords();
                            choosingProcess(currentPlayerCoords);
                        }
                    }
                } else {
                    System.out.println("\nDirection \"" + move + "\" is out of the bounds of the field!\n");
                    displayField(gameFieldToDisp);
                }
            } else {
                System.out.println("Please, enter the proper direction ('w', 'a', 's' or 'd')!");
            }
        }
    }

    private static boolean coordBoundCheck(char dir, int[] currentCoords) {
        switch(dir) {
            case 'w' -> {
                return currentCoords[0] != 0;
            }
            case 's' -> {
                return currentCoords[0] != 2;
            }
            case 'd' -> {
                return currentCoords[1] != 2;
            }
            case 'a' -> {
                return currentCoords[1] != 0;
            }
            default -> {
                return false;
            }
        }
    }

    private void choosingProcess(int[] currentPlayerCoords) {
        String currentDispFieldPosition = gameFieldToDisp[currentPlayerCoords[0]][currentPlayerCoords[1]];
        gameFieldToDisp[currentPlayerCoords[0]][currentPlayerCoords[1]] = StringFunc.addStrings(currentDispFieldPosition.trim(), "|");
        displayField(gameFieldToDisp);
    }

    private void pointerCheck() {
        for(int i=0; i<=2; i++) {
            for(int j=0; j<=2; j++) {
                String currentCell = gameFieldToDisp[i][j];
                if(currentCell.charAt(0) == '|') {
                    gameFieldToDisp[i][j] = StringFunc.addStrings(StringFunc.customStringsTrim(currentCell, '|'), " ");
                }
            }
        }
    }

    public void setToOneVal(int val) {
        for(int i=0; i<=2; i++) {
            for(int j=0; j<=2; j++) {
                gameFieldValues[i][j] = val;
            }
        }
    }

    public void setToOneVal(String val) {
        for(int i=0; i<=2; i++) {
            for(int j=0; j<=2; j++) {
                gameFieldToDisp[i][j] = val;
            }
        }
    }

    public int victoryCheck() {
        int[][] g = gameFieldValues;
        boolean drawFlag = true;
        int diagSum1 = g[0][0] + g[1][1] + g[2][2];
        int diagSum2 = g[2][0] + g[1][1] + g[0][2];


        boolean diagSum9Bool = diagSum1 == 9 || diagSum2 == 9;
        boolean diagSum15Bool = diagSum1 == 15 || diagSum2 == 15;
        if(diagSum9Bool) {
            return 9;
        }
        if(diagSum15Bool) {
            return 15;
        }

        for(int i=0; i<=2; i++) {
            int horSum = IntStream.of(g[i]).sum();
            int vertSum = g[0][i] + g[1][i] + g[2][i];

            if(horSum == 9 || vertSum == 9) {
                return 9;
            }
            if(horSum == 15 || vertSum == 15) {
                return 15;
            }
        }
        for(int i=0; i<=2; i++) {
            for(int j=0; j<=2; j++) {
                if(g[i][j] == 0) {
                    drawFlag = false;
                    break;
                }
            }
        }
        if(drawFlag) {
            return -1;
        }
        return 0;
    }

    public void victoryCase(Player winPlayer) {
        System.out.println("\nPLAYER " + winPlayer.getNick() + " IS A WINNER!!!");
        winPlayer.setVictories(winPlayer.getVictories() + 1);
        winPlayer.setGamesPlayed(winPlayer.getGamesPlayed() + 1);
        winPlayer.updateScore();
    }

    public void lossCase(Player losPlayer) {
        System.out.println("\nPLAYER " + losPlayer.getNick() + " IS A LOSER!!!\n\n");
        losPlayer.setLosses(losPlayer.getLosses() + 1);
        losPlayer.setGamesPlayed(losPlayer.getGamesPlayed() + 1);
        losPlayer.updateScore();
        Main.movesFlag = false;
    }


    public static void displayField(String[][] field) {
        for(int i=0; i<=field.length - 1; i++) {
            for(int j=0; j<=field[0].length - 1; j++) {
                if (j==field[0].length - 1) {
                    System.out.println(field[i][j]);
                } else {
                    System.out.print(field[i][j]);
                }
            }
        }
    }

    public static void displayField(int[][] field) {
        for(int i=0; i<=field.length - 1; i++) {
            for(int j=0; j<=field[0].length - 1; j++) {
                if (j==field[0].length - 1) {
                    System.out.println(field[i][j]);
                } else {
                    System.out.print(field[i][j]);
                }
            }
        }
    }

    public Game() {
        System.out.println("\n\t\t\t\tLAUNCHING NEW GAME\n");
    }
}

class Player {
    String nick;
    char side;
    int score;
    int victories;
    int losses;
    int draws;
    int gamesPlayed;
    int[] coords = {1, 1};

    public Player(String nick, char side, int score, int victories, int losses, int draws, int gamesPlayed) {
        this.nick = nick;
        this.side = side;
        this.score = score;
        this.victories = victories;
        this.losses = losses;
        this.draws = draws;
        this.gamesPlayed = gamesPlayed;
    }

    public void updateScore() {
        score = victories - losses + draws;
    }

    public void printPlayerInfo() {
        System.out.println("\n========================================\nNick: " + nick + "\nSide: " + side + "\nScore: " + score + "\nVictories: " + victories + "\nLosses: " + losses + "\nDraws: " + draws + "\nGames played: " + gamesPlayed + "\n========================================\n");
    }

    public void resetPlayerInfo() {
        nick = "";
        score = 0;
        victories = 0;
        losses = 0;
        draws = 0;
        gamesPlayed = 0;
    }

    public void setCoords(int ind, int value) {
        coords[ind] = value;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public void setSide(char side) {
        this.side = side;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setVictories(int victories) {
        this.victories = victories;
    }

    public void setLosses(int losses) {
        this.losses = losses;
    }

    public void setDraws(int draws) {
        this.draws = draws;
    }

    public void setGamesPlayed(int gamesPlayed) {
        this.gamesPlayed = gamesPlayed;
    }

    public int[] getCoords() {
        return coords;
    }

    public String getNick() {
        return nick;
    }

    public char getSide() {
        return side;
    }

    public int getScore() {
        return score;
    }

    public int getVictories() {
        return victories;
    }

    public int getLosses() {
        return losses;
    }

    public int getDraws() {
        return draws;
    }

    public int getGamesPlayed() {
        return gamesPlayed;
    }
}
