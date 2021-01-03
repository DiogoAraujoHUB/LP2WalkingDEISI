package pt.ulusofona.lp2.theWalkingDEISIGame;

//Podia retirar as variaveis boolean se eu fizesse o move dependendo de cada Criatura individualmente
public abstract class Creature {
    protected int id;
    protected int tipo;
    protected String nome;
    protected int x, y;
    protected int numEquipamentos;
    protected int deslocamentoMaximo;

    protected boolean ableToMoveLinearly;
    protected boolean ableToMoveFreely;
    protected boolean hasDied;

    public Creature(int id, String nome, int x, int y) {
        this.id = id;
        this.nome = nome;
        this.x = x;
        this.y = y;

        this.numEquipamentos = 0;

        this.hasDied = false;
    }

    public boolean getHasDied() {
        return this.hasDied;
    }

    public void beDestroyed() {
        this.hasDied = true;
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

    public boolean getAbleToMoveFreely() {
        return this.ableToMoveFreely;
    }

    public boolean getAbleToMoveLinearly() {
        return this.ableToMoveLinearly;
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

    public void setNumEquipamentos(int number) {
        this.numEquipamentos = number;
    }

    public void pickEquipment() {
        numEquipamentos++;
    }

    public abstract String getImagePNG();

    public abstract String toString();

    public abstract void move(Mapa map, int destinoX, int destinoY);
}
