package pt.ulusofona.lp2.theWalkingDEISIGame;

public abstract class Ofensivo extends Equipamento {

    protected int range;

    public Ofensivo( int id, int tipo, int x, int y) {
        super(id, tipo, x, y);
    }

    public int getRange() {
        return this.range;
    }
}
