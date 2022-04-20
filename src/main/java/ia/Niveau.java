package ia;

/**
 * Enumerate the different possible levels of an ia
 */
public enum Niveau {
    FAIBLE(1),
    MOYEN(5),
    FORT(8);

    private final int profondeurDeRecherche;

    /**
     * Constructeur par défaut
     * @param profondeur la prodondeur max utilisé dans l'algo MinMax ou AlphaBeta
     */
    Niveau(int profondeur){
        profondeurDeRecherche = profondeur;
    }

    /**
     * @return la profondeur max dans l'algo MinMax ou AlphaBeta
     */
    public int getProfondeur() {
        return profondeurDeRecherche;
    }
}