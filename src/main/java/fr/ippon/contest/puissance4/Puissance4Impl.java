package fr.ippon.contest.puissance4;

import java.util.Random;

import static fr.ippon.contest.puissance4.Puissance4.EtatJeu.EN_COURS;

public class Puissance4Impl implements Puissance4 {

    private EtatJeu gameState = EN_COURS;

    private char[][] gameGrid = new char[][]{
            {'-', '-', '-', '-', '-', '-', '-'}
            , {'-', '-', '-', '-', '-', '-', '-'}
            , {'-', '-', '-', '-', '-', '-', '-'}
            , {'-', '-', '-', '-', '-', '-', '-'}
            , {'-', '-', '-', '-', '-', '-', '-'}
            , {'-', '-', '-', '-', '-', '-', '-'}
    };

    private static final char[] players = new char[]{'J', 'R'};

    private int currentPlayerIndex;
    private char currentPlayer;

    @Override
    public void nouveauJeu() {
        gameGrid = new char[][]{
                {'-', '-', '-', '-', '-', '-', '-'}
                , {'-', '-', '-', '-', '-', '-', '-'}
                , {'-', '-', '-', '-', '-', '-', '-'}
                , {'-', '-', '-', '-', '-', '-', '-'}
                , {'-', '-', '-', '-', '-', '-', '-'}
                , {'-', '-', '-', '-', '-', '-', '-'}
        };

        currentPlayerIndex = new Random(System.currentTimeMillis()).nextInt() % 2;
        currentPlayer = players[currentPlayerIndex];
        gameState = EN_COURS;
    }

    @Override
    public void chargerJeu(char[][] grille, char tour) {

        assertGridIsValid(grille);

        if (!isPlayerValid(tour)) {
            throw new IllegalArgumentException("Player must be valid");
        }

        gameGrid = grille;
        currentPlayer = tour;
        currentPlayerIndex = playerIndex(tour);
    }

    @Override
    public EtatJeu getEtatJeu() {
        return gameState;
    }

    @Override
    public char getTour() {
        return currentPlayer;
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

        gameGrid[line][colonne] = currentPlayer;
        changePlayer();
    }

    private void changePlayer() {
        currentPlayerIndex = ++currentPlayerIndex % 2;
        currentPlayer = players[currentPlayerIndex];
    }

    private void assertGridIsValid(char[][] grille) {
        if (grille.length != 6) {
            throw new IllegalArgumentException("Must have 6 lines");
        }
        for (char[] line : grille) {
            if (line.length != 7) {
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
        for (char p : players) {
            if (player == p) {
                return true;
            }
        }

        return false;
    }

    private int selectFirstLineEmptyAtColumn(int column) {
        for (int i = gameGrid.length - 1; i > -1; i--) {
            if (gameGrid[i][column] == '-') {
                return i;
            }
        }

        throw new IllegalStateException("Column is full");
    }

}
