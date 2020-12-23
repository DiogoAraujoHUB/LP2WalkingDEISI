package pt.ulusofona.lp2.theWalkingDEISIGame;

//Ja reparei que estas variáveis todas estavam a estragar tudo, vou ter que mudar a maneira que estou a fazer isto
//Vou criar uma classe para cada creatura que este jogo pode ter!!!!
//(E, devido a isso, vou retirar as variaveis boolean que antes eram usadas para mais ou menos definir essas classes)

public abstract class Creature {
    protected int id;
    protected int tipo;
    protected String nome;
    protected int x, y;
    protected int numEquipamentos;
    protected int deslocamentoMaximo;

    public Creature(int id, int tipo, String nome, int x, int y) {
        this.id = id;
        this.tipo = tipo;
        this.nome = nome;
        this.x = x;
        this.y = y;

        this.numEquipamentos = 0;
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
