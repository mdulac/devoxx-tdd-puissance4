package fr.ippon.contest.puissance4;

import java.util.Random;

import static fr.ippon.contest.puissance4.Puissance4.EtatJeu.*;
import static java.lang.System.currentTimeMillis;

public class Puissance4Impl implements Puissance4 {

    private static final int LINES_COUNT = 6;
    private static final int COLUMNS_COUNT = 7;

    private static final char EMPTY = '-';

    private EtatJeu gameState = EN_COURS;

    private char[][] gameGrid = new char[][]{
            {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY}
            , {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY}
            , {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY}
            , {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY}
            , {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY}
            , {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY}
    };

    private int empties = LINES_COUNT * COLUMNS_COUNT;

    private static final char FIRST_PLAYER = 'J';
    private static final char SECOND_PLAYER = 'R';
    private static final char[] PLAYERS = new char[]{FIRST_PLAYER, SECOND_PLAYER};
    private int currentPlayerIndex = 0;

    private static final String PLAYER_NOT_VALID = "Player must be valid";
    private static final String COLUMN_NOT_VALID = "Column must be valid";
    private static final String GRID_LINES_COUNT_NOT_VALID = "Must have 6 lines";
    private static final String GRID_COLUMN_COUNT_NOT_VALID = "Must have 7 columns by row";
    private static final String COLUMN_FULL = "Column is full";
    private static final String UNKNOWN_PLAYER = "Player doesn't exist";

    @Override
    public void nouveauJeu() {
        gameGrid = new char[][]{
                {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY}
                , {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY}
                , {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY}
                , {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY}
                , {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY}
                , {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY}
        };

        currentPlayerIndex = new Random(currentTimeMillis()).nextInt() % 2;
        gameState = EN_COURS;
    }

    @Override
    public void chargerJeu(char[][] grille, char tour) {

        assertGridIsValid(grille);

        if (!isPlayerValid(tour)) {
            throw new IllegalArgumentException(PLAYER_NOT_VALID);
        }

        gameGrid = grille;
        currentPlayerIndex = playerIndex(tour);
        gameState = EN_COURS;

        for (int l = 0; l < gameGrid.length; l++) {
            for (int c = 0; c < gameGrid[l].length; c++) {
                if (gameGrid[l][c] != EMPTY) {
                    checkLinesForPlayerFrom(getOccupant(l, c), l, c);
                }
            }
        }

        if (gameState == EN_COURS) {
            empties = countEmpties();

            if (empties <= 0) {
                gameState = MATCH_NUL;
            }
        }

    }

    @Override
    public EtatJeu getEtatJeu() {
        return gameState;
    }

    @Override
    public char getTour() {
        return currentPlayer();
    }

    @Override
    public char getOccupant(int ligne, int colonne) {
        return gameGrid[ligne][colonne];
    }

    @Override
    public void jouer(int colonne) {
        if (colonne < 0 || colonne > gameGrid.length) {
            throw new IllegalArgumentException(COLUMN_NOT_VALID);
        }

        int line = selectFirstLineEmptyAtColumn(colonne);

        gameGrid[line][colonne] = currentPlayer();
        empties--;

        if (empties <= 0) {
            gameState = MATCH_NUL;
        }

        checkLinesForPlayerFrom(currentPlayer(), line, colonne);

        changePlayer();
    }

    private void assertGridIsValid(char[][] grille) {

        if (grille.length != LINES_COUNT) {
            throw new IllegalArgumentException(GRID_LINES_COUNT_NOT_VALID);
        }
        for (char[] line : grille) {
            if (line.length != COLUMNS_COUNT) {
                throw new IllegalArgumentException(GRID_COLUMN_COUNT_NOT_VALID);
            }
        }
    }

    private int playerIndex(char player) {
        for (int i = 0; i <= PLAYERS.length - 1; i++) {
            if (player == PLAYERS[i]) {
                return i;
            }
        }

        throw new IllegalStateException(UNKNOWN_PLAYER);
    }

    private boolean isPlayerValid(char player) {
        try {
            playerIndex(player);
        } catch (IllegalStateException e) {
            return false;
        }

        return true;
    }

    private int selectFirstLineEmptyAtColumn(int column) {
        for (int i = gameGrid.length - 1; i >= 0; i--) {
            if (gameGrid[i][column] == EMPTY) {
                return i;
            }
        }

        throw new IllegalStateException(COLUMN_FULL);
    }

    private char currentPlayer() {
        return PLAYERS[currentPlayerIndex];
    }

    private void changePlayer() {
        currentPlayerIndex = ++currentPlayerIndex % 2;
    }

    private int countEmpties() {
        int empties = 0;
        for (char[] line : gameGrid) {
            for (char cell : line) {
                if (cell == EMPTY) {
                    empties++;
                }
            }
        }

        return empties;
    }

    private void checkLinesForPlayerFrom(char player, int line, int column) {
        checkVerticalLine(player, line, column);
        checkHorizontalLine(player, line, column);
        checkFirstDiagonalLine(player, line, column);
        checkSecondDiagonalLine(player, line, column);
    }

    private void checkSecondDiagonalLine(char player, int line, int column) {
        int lineLength = 1;

        int j = line + 1;

        for (int i = column - 1; i >= 0; i--) {
            if (j < gameGrid.length && gameGrid[j][i] == player) {
                lineLength++;
                j++;
            } else {
                break;
            }
        }

        j = line - 1;

        for (int i = column + 1; i < gameGrid[0].length; i++) {
            if (j >= 0 && gameGrid[j][i] == player) {
                lineLength++;
                j--;
            } else {
                break;
            }
        }

        hasPlayerWon(player, lineLength);
    }

    private void checkFirstDiagonalLine(char player, int line, int column) {
        int lineLength = 1;

        int j = line - 1;

        for (int i = column - 1; i >= 0; i--) {
            if (j >= 0 && gameGrid[j][i] == player) {
                lineLength++;
                j--;
            } else {
                break;
            }
        }

        j = line + 1;

        for (int i = column + 1; i < gameGrid[0].length; i++) {
            if (j < gameGrid.length && gameGrid[j][i] == player) {
                lineLength++;
                j++;
            } else {
                break;
            }
        }

        hasPlayerWon(player, lineLength);
    }

    private void checkHorizontalLine(char player, int line, int column) {
        int lineLength = 1;

        for (int i = column - 1; i >= 0; i--) {
            if (gameGrid[line][i] == player) {
                lineLength++;
            } else {
                break;
            }
        }

        for (int i = column + 1; i < gameGrid[0].length; i++) {
            if (gameGrid[line][i] == player) {
                lineLength++;
            } else {
                break;
            }
        }

        hasPlayerWon(player, lineLength);
    }

    private void checkVerticalLine(char player, int line, int column) {
        int lineLength = 1;

        for (int i = line - 1; i >= 0; i--) {
            if (gameGrid[i][column] == player) {
                lineLength++;
            } else {
                break;
            }
        }

        for (int i = line + 1; i < gameGrid.length; i++) {
            if (gameGrid[i][column] == player) {
                lineLength++;
            } else {
                break;
            }
        }

        hasPlayerWon(player, lineLength);
    }

    private void hasPlayerWon(char player, int lineLength) {
        if (lineLength >= 4) {
            gameState = player == FIRST_PLAYER ? JAUNE_GAGNE : ROUGE_GAGNE;
        }
    }

}
