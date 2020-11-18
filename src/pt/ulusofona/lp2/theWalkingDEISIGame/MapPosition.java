package pt.ulusofona.lp2.theWalkingDEISIGame;

public class MapPosition {
    private int tipo = 0;

    private Humano human;
    private Zombie zombie;
    private Equipamento equipamento;

    public MapPosition() {
        tipo = 0;
        human = null;
        zombie = null;
        equipamento = null;
    }

    public int getTipo() {
        return this.tipo;
    }

    public void setTipo( int tipo ) {
        this.tipo = tipo;
    }

    public Humano getHuman() {
        return this.human;
    }

    public void setHuman( Humano human ) {
        this.human = human;
    }

    public Zombie getZombie() {
        return this.zombie;
    }

    public void setZombie( Zombie zombie ) {
        this.zombie = zombie;
    }

    public Equipamento getEquipamento() {
        return this.equipamento;
    }

    public void setEquipamento( Equipamento equipamento ) {
        this.equipamento = equipamento;
    }

    public String toString() {
        return "Tipo == " + tipo + " and Human == " + human + " and Zombie == "
                + zombie + " and equipment == " + equipamento;
    }
}
