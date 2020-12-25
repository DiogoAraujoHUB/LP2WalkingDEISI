package pt.ulusofona.lp2.theWalkingDEISIGame;

public abstract class Equipamento {
    protected int id;
    protected int tipo;
    protected int x, y;
    protected int numUses;

    public Equipamento( int id, int tipo, int x, int y) {
        this.id = id;
        this.tipo = tipo;
        this.x = x;
        this.y = y;

        this.numUses = -1;     //O numUses == -1 quer dizer que tem número de uses infinito
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

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getNumUses() {
        return this.numUses;
    }

    public void setNumUses(int numUses) {
        this.numUses = numUses;
    }

    public String toString() {
        return id + " | " + tipo + " | @ (" + x + ", " + y + ")";
    }
}
