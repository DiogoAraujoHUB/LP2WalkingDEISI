package pt.ulusofona.lp2.theWalkingDEISIGame;

public class Ofensivo extends Equipamento {

    private int range;

    public Ofensivo( int id, int tipo, int x, int y, int numUses, int range ) {
        super(id, tipo, x, y, numUses);

        this.range = range;
    }

    public int getRange() {
        return this.range;
    }
}
