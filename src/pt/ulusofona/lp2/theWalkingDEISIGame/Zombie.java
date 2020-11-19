package pt.ulusofona.lp2.theWalkingDEISIGame;

import java.util.ArrayList;
import java.util.List;

public class Zombie {
    private int id;
    private int x, y;
    private int numEquipamentosDestruidos;

    private String nome;

    Zombie( int id, String nome, int x, int y ) {
        this.id = id;
        this.nome = nome;
        this.x = x;
        this.y = y;
        this.numEquipamentosDestruidos = 0;
    }

    public int getId() {
        return this.id;
    }

    public String getImagePNG() {
        return "Zombie.png";
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

    public String toString() {
        String texto = id + " | Zombie | Os Outros |" + nome + "";
        texto += numEquipamentosDestruidos + " @ (" + x + ", " + y + ")";

        return texto;
    }

    public int getNumEquipamentosDestruidos() {
        return this.numEquipamentosDestruidos;
    }

    public void destroiEquipamento() {
        numEquipamentosDestruidos++;
    }

    public void move( int destinoX, int destinoY, Mapa map ) {
        //parte o equipamento na posição que move para
        if ( map.getMapId( destinoX, destinoY )  == -1 ) {
            destroiEquipamento();
            map.getPosition( destinoX, destinoY ).setEquipamento(null);
        }

        //move normalmente
        map.setPositionType( x, y, 0 );
        map.setPositionType( destinoX, destinoY, 3 );
        map.getPosition(destinoX, destinoY ).setZombie( map.getPosition(x,y).getZombie() );
        map.getPosition( x, y ).setZombie( null );
        x = destinoX;
        y = destinoY;
    }

    public void setX( int x ) {
        this.x = x;
    }

    public void setY( int y ) {
        this.y = y;
    }

}
