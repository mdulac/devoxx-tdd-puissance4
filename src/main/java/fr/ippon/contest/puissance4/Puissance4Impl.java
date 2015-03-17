package fr.ippon.contest.puissance4;

import java.util.Random;

import static fr.ippon.contest.puissance4.Puissance4.EtatJeu.EN_COURS;

public class Puissance4Impl implements Puissance4 {

    private EtatJeu gameState;

    private char[][] gameGrid;

    private static final char[] players = new char[]{'J', 'R'};

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

        currentPlayer = players[new Random(System.currentTimeMillis()).nextInt() % 2];
        gameState = EN_COURS;
    }

    @Override
    public void chargerJeu(char[][] grille, char tour) {

    }

    @Override
    public EtatJeu getEtatJeu() {
        return gameState;
    }

    @Override
    public char getTour() {
        return 0;
    }

    @Override
    public char getOccupant(int ligne, int colonne) {
        return 0;
    }

    @Override
    public void jouer(int colonne) {

    }
}
