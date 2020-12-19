package pt.ulusofona.lp2.theWalkingDEISIGame;

public abstract class Creature {
    protected int id;
    protected int tipo;
    protected String nome;
    protected int x, y;
    protected int numEquipamentos;
    protected int deslocamentoMaximo;

    protected boolean moveFree;

    public Creature( int id, int tipo, String nome, int x, int y, int deslocamentoMaximo, boolean moveFree ) {
        this.id = id;
        this.tipo = tipo;
        this.nome = nome;
        this.x = x;
        this.y = y;
        this.deslocamentoMaximo = deslocamentoMaximo;
        this.moveFree = moveFree;

        this.numEquipamentos = 0;
        this.deslocamentoMaximo = 0;
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

    public String getNome() {
        return this.nome;
    }

    public int getDeslocamentoMaximo() {
        return this.deslocamentoMaximo;
    }

    public int getNumEquipamentos() {
        return this.numEquipamentos;
    }

    public void pickEquipment() {
        numEquipamentos++;
    }

    public abstract String getImagePNG();

    public abstract String toString();

    public abstract void move(Mapa map, int destinoX, int destinoY, int tipoMovido);
}
