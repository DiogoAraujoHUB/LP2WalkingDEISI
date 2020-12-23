package pt.ulusofona.lp2.theWalkingDEISIGame;

public class MapPosition {
    private int tipo;

    private SafeHaven safeHaven;    //Tipo == 2;
    private Creature creature;          //Tipo == 1;
    private Equipamento equipamento;    //Tipo == -1;

    public MapPosition() {
        tipo = 0;

        safeHaven = null;
        creature = null;
        equipamento = null;
    }

    public int getTipo() {
        return this.tipo;
    }

    public void setTipo( int tipo ) {
        this.tipo = tipo;
    }

    public Creature getCreature() {
        return this.creature;
    }

    public void setCreature( Creature creature ) {
        this.creature = creature;
    }

    public Equipamento getEquipamento() {
        return this.equipamento;
    }

    public void setEquipamento( Equipamento equipamento ) {
        this.equipamento = equipamento;
    }

    public SafeHaven getSafeHaven() {
        return this.safeHaven;
    }

    public void setSafeHaven(SafeHaven safeHaven) {
        this.safeHaven = safeHaven;
    }

    public String toString() {
        return "Tipo == " + tipo + " and Creature == " + creature + " and equipment == " + equipamento;
    }

}
