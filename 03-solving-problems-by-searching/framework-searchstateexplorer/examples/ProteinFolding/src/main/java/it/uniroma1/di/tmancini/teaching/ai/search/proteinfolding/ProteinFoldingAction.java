package it.uniroma1.di.tmancini.teaching.ai.search.proteinfolding;

import it.uniroma1.di.tmancini.teaching.ai.search.Action;

public class ProteinFoldingAction extends Action {

    ProteinFoldingState state;
    int x;
    int y;
    char c;


    public ProteinFoldingAction(ProteinFoldingState state, int x, int y, char c){
        // l'azione che aggiunge c (con c in {"H", "P"}) in posizione (x,y)
    }

    @Override
    public double getCost() {
        // calcolare il costo (Attenzione!)
        // può essere negativo...
        return 0;
    }


    /*=========================

    Add equals(Object oo), hashCode(), clone() and toString()!

     =========================*/
}
