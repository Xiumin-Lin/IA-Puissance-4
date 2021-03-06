package ia;

import game.Puissance4;
import player.Player;

import java.util.List;
import java.util.Random;

/**
 * Classe représentant un joueur humain
 *
 * @author Xiumin LIN, Celine Li
 */
public class Ia {
    private final Random rand = new Random();
    private int profondeurMax;

    public Ia(Niveau lvl) {
        this.profondeurMax = lvl.getProfondeur();
    }

    /**
     * v
     * Utilise l'algo minimax pour déterminer la colonne que l'ia souhaite pour une partie de Puissance 4.
     *
     * @param game       une partie de Puissance 4
     * @param profondeur la profondeur actuelle de l'algo minimax
     * @param isMax      est ce que l'agent est Max (est ce qu'on veut la valeur maximal de l'heuristique)
     * @param p          le joueur qui veut utiliser l'algo minimax (normalement un objet de class Computer)
     * @return la colonne que l'ia souhaite poser une pièce et sa valeur de l'heuristique
     */
    public int[] playMiniMax(Puissance4 game, int profondeur, boolean isMax, Player p) {
        if(game.isOver()) {
            if(game.getWinner() == null) return new int[]{-1, 0}; // si égalité, renvoie 0
            else return new int[]{-1, game.evaluation(p)}; // sinon la valeur de l'heuristique du plateau
        }

        // La liste des colonnes valide dont l'ia peut poser une pièce
        List<Integer> colonnesValide = game.getAvailablePlace();
        // On attribut un colonne au hasard au cas où l'algo n'arrive pas à choisir
        int col = colonnesValide.get(rand.nextInt(colonnesValide.size()));
        int value = isMax ? Integer.MIN_VALUE : Integer.MAX_VALUE;
        // Pour chaque coup possible
        for(int moveIndex : colonnesValide) {
            int newValue = 0;
            if(isMax) {
                newValue = minValue(game, profondeur, moveIndex, p);
                if(newValue > value) {
                    value = newValue;
                    col = moveIndex;
                }
            } else {
                newValue = maxValue(game, profondeur, moveIndex, p);
                if(newValue < value) {
                    value = newValue;
                    col = moveIndex;
                }
            }
        }
        return new int[]{col, value}; // la colonne que l'ia souhaite jouer et sa valeur de l'heuristique
    }

    public int minValue(Puissance4 game, int profondeur, int moveIndex, Player p) {
        int minValue;
        if(profondeur == this.profondeurMax) {
            minValue = game.evaluation(p);
        } else {
            Puissance4 copie = new Puissance4(game); // on crée une copie du jeu
            copie.placePiece(moveIndex);
            minValue = playMiniMax(copie, profondeur + 1, true, p)[1]; // recup la valeur minimal
        }
        return minValue;
    }

    public int maxValue(Puissance4 game, int profondeur, int moveIndex, Player p) {
        int maxValue;
        if(profondeur == this.profondeurMax) {
            maxValue = game.evaluation(p);
        } else {
            Puissance4 copie = new Puissance4(game); // on crée une copie du jeu
            copie.placePiece(moveIndex);
            maxValue = playMiniMax(copie, profondeur + 1, false, p)[1]; // recup la valeur minimal
        }
        return maxValue;
    }


    public int[] playAlphaBeta(Puissance4 game, int profondeur, boolean isMax, Player p, int alpha, int beta) {
        if(game.isOver()) {
            if(game.getWinner() == null) return new int[]{-1, 0}; // si égalité, renvoie 0
            else return new int[]{-1, game.evaluation(p)}; // sinon la valeur de l'heuristique du plateau
        }

        // La liste des colonnes valide dont l'ia peut poser une pièce
        List<Integer> colonnesValide = game.getAvailablePlace();
        // On attribut un colonne au hasard au cas où l'algo n'arrive pas à choisir
        int col = colonnesValide.get(rand.nextInt(colonnesValide.size()));
        int value = isMax ? Integer.MIN_VALUE : Integer.MAX_VALUE;
        // Pour chaque coup possible
        for(int moveIndex : colonnesValide) {
            int newValue = 0;
            // recup la valeur de l'heuristique
            if(profondeur == this.profondeurMax) {
                newValue = game.evaluation(p);
            } else {
                // si profondeur max non atteint, on crée une copie du jeu, place une pièce
                // a la colonne "moveIndex" et applique alpha-beta sur ce nouveau plateau de jeu
                Puissance4 copie = new Puissance4(game);
                copie.placePiece(moveIndex);
                newValue = playAlphaBeta(copie, profondeur + 1, !isMax, p, alpha, beta)[1];
            }
            if(isMax) {
                if(newValue > value) {
                    value = newValue;
                    col = moveIndex;
                }
                // elagage alpha-beta
                if(newValue >= beta) break;
                alpha = Math.max(alpha, newValue);
            } else {
                if(newValue < value) {
                    value = newValue;
                    col = moveIndex;
                }
                // elagage alpha-beta
                if(newValue <= alpha) break;
                beta = Math.min(beta, newValue);
            }
        }
        return new int[]{col, value}; // la colonne que l'ia souhaite jouer et sa valeur de l'heuristique
    }
}
