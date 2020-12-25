package pt.ulusofona.lp2.theWalkingDEISIGame;

public abstract class Animal extends Creature {

    public Animal(int id, String nome, int x, int y) {
        super(id, nome, x, y);

        this.tipo = 1;
    }

    public abstract void move( Mapa map, int destinoX, int destinoY );

    public abstract String getImagePNG();

    public abstract String toString();
}
