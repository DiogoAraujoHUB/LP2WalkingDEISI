package pt.ulusofona.lp2.theWalkingDEISIGame;

public class Equipamento {
    private int id;
    private int tipo;
    private int x, y;
    private int numUses;

    public Equipamento( int id, int tipo, int x, int y, int numUses ) {
        this.id = id;
        this.tipo = tipo;
        this.x = x;
        this.y = y;
        this.numUses = numUses; //if numUses == -1, then it has no limited amount of uses
    }

    public int getId() {
        return this.id;
    }

    public int getTipo() {
        return this.tipo;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public int getNumUses() {
        return this.numUses;
    }

    public String toString() {
        return id + " | " + tipo + " | @ (" + x + ", " + y + ")";
    }
}
