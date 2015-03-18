package fr.ippon.contest.puissance4;

import java.util.Random;

import static fr.ippon.contest.puissance4.Puissance4.EtatJeu.EN_COURS;
import static fr.ippon.contest.puissance4.Puissance4.EtatJeu.MATCH_NUL;
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

    private static final char[] players = new char[]{'J', 'R'};

    private int currentPlayerIndex = 0;
    private int empties = LINES_COUNT * COLUMNS_COUNT;

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
            throw new IllegalArgumentException("Player must be valid");
        }

        gameGrid = grille;
        currentPlayerIndex = playerIndex(tour);
        empties = countEmpties();

        if (empties <= 0) {
            gameState = MATCH_NUL;
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
            throw new IllegalArgumentException("Column must be valid");
        }

        int line = selectFirstLineEmptyAtColumn(colonne);

        gameGrid[line][colonne] = currentPlayer();
        empties--;

        if (empties <= 0) {
            gameState = MATCH_NUL;
        }

        changePlayer();
    }

    private void assertGridIsValid(char[][] grille) {

        if (grille.length != LINES_COUNT) {
            throw new IllegalArgumentException("Must have 6 lines");
        }
        for (char[] line : grille) {
            if (line.length != COLUMNS_COUNT) {
                throw new IllegalArgumentException("Must have 7 columns by row");
            }
        }
    }

    private int playerIndex(char player) {
        for (int i = 0; i <= players.length - 1; i++) {
            if (player == players[i]) {
                return i;
            }
        }

        throw new IllegalStateException("Player doesn't exist");
    }

    private boolean isPlayerValid(char player) {
        try {
            playerIndex(player);
        } catch (Exception e) {
            return false;
        }

        return true;
    }

    private int selectFirstLineEmptyAtColumn(int column) {
        for (int i = gameGrid.length - 1; i > -1; i--) {
            if (gameGrid[i][column] == EMPTY) {
                return i;
            }
        }

        throw new IllegalStateException("Column is full");
    }

    private char currentPlayer() {
        return players[currentPlayerIndex];
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

}
