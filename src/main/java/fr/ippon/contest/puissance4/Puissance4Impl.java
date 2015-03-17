package fr.ippon.contest.puissance4;

import static fr.ippon.contest.puissance4.Puissance4.EtatJeu.EN_COURS;

public class Puissance4Impl implements Puissance4 {

    private EtatJeu gameState;

    private char[][] gameGrid;

    @Override
    public void nouveauJeu() {
        gameState = EN_COURS;
        gameGrid = new char[][]{
                {'-', '-', '-', '-', '-', '-', '-'}
                , {'-', '-', '-', '-', '-', '-', '-'}
                , {'-', '-', '-', '-', '-', '-', '-'}
                , {'-', '-', '-', '-', '-', '-', '-'}
                , {'-', '-', '-', '-', '-', '-', '-'}
                , {'-', '-', '-', '-', '-', '-', '-'}
        };
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
