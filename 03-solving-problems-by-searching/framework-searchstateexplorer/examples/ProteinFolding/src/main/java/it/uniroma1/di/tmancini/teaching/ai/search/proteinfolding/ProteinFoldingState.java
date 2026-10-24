package it.uniroma1.di.tmancini.teaching.ai.search.proteinfolding;

import it.uniroma1.di.tmancini.teaching.ai.search.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ProteinFoldingState extends State {

    private char[][] grid; // the underlying grid: does not correspond to the "protein grid", since it must have indices from 0 to 2n-1
    private int prefixIdx; // the index of the last aminoacid that has been placed
    private int x; // the x coord of the head of the protein
    private int y; // the y coord of the head of the protein
    private int psize;  // cached: the total size of the protein


    public ProteinFoldingState(ProteinFolding p) {
        super(p);
        this.prefixIdx = -1;
        this.psize = p.getProtein().length;
        this.grid = new char[(p.getSize()*2)-1][(p.getSize()*2)-1];
        this.x = -1;
        this.y = -1;
    }

    @Override
    public Collection<? extends Action> executableActions() {
        List<ProteinFoldingAction> result = new ArrayList<>();
        char nextC = ((ProteinFolding)this.getProblem()).getProtein()[this.prefixIdx + 1];
        int[][] dirs = {{0,1}, {0,-1}, {1,0}, {-1,0}};
        for (int[] d : dirs) {
            int nx = this.x + d[0];
            int ny = this.y + d[1];
            if (get(nx, ny) == 'e') {
                result.add(new ProteinFoldingAction(this, nx, ny, nextC));
            }
        }
        // calcolare le azioni eseguibili

        return result;
    }

    @Override
    public State resultingState(Action a) {
        // calcolare lo stato che si ottiene eseguendo a in this
        return null;
    }

    @Override
    public boolean isGoal() {
        // calcolare se this è una soluzione (easy!)
        return this.prefixIdx == this.psize - 1;
    }


    public static ProteinFoldingState newInitialState(ProteinFolding p) {
        ProteinFoldingState newState = new ProteinFoldingState(p);
        for (int x=-(newState.psize-1); x<=newState.psize-1; x++){
            for (int y=-(newState.psize-1); y<=newState.psize-1; y++) {
                newState.set(x, y, 'e');
            }
        }

        newState.set(0,0, p.getProtein()[0]);
        newState.x = 0;
        newState.y = 0;
        newState.prefixIdx = 0;

        return newState;
    }


    /**
     Gets the character at position (x,y) in the protein grid.

     */
    public char get(int x, int y){
        return this.grid[psize-1+x][psize-1+y];
    }

    /**
     Sets the character at position (x,y) in the protein grid.
     c must be either "P" or "H" (we do not want to remove aminoacids!)
     */
    public void set(int x, int y, char c){
        this.grid[psize-1+x][psize-1+y] = c;
    }



    // si potrebbe fare caching...
    public int getContacts() {
        // calcolare i contatti
        return 0;
    }


    public int getEnergy() {
        return -getContacts();
    }


    /*=========================

    Add equals(Object oo), hashCode(), clone() and toString()!

     =========================*/



}
