package pt.ulusofona.lp2.theWalkingDEISIGame;

import java.util.ArrayList;
import java.util.List;

public class Humano {
    private int twoHanded;
    private int id;
    private int x, y;
    private int numEquipamentosApanhados;

    private String nome;
    private List<Equipamento> equipamentoApanhado;

    Humano(int id, String nome, int x, int y) {
        this.id = id;
        this.nome = nome;
        this.x = x;
        this.y = y;
        this.equipamentoApanhado = new ArrayList<Equipamento>();
        this.numEquipamentosApanhados = 0;
    }

    public String getImagePNG() {
        return "Human.png";
    }

    public int getId() {
        return this.id;
    }

    public int getX() {
        return this.x;
    }

    public void setX( int x ) {
        this.x = x;
    }

    public int getY() {
        return this.y;
    }

    public void setY( int y ) {
        this.y = y;
    }

    public String getNome() {
        return this.nome;
    }

    public String toString() {
        String texto = id + " | Humano | Os Vivos |" + nome + "";
        texto += numEquipamentosApanhados + " @ (" + x + ", " + y + ")";

        return texto;
    }

    public List<Equipamento> getEquipamentoApanhado() {
        return this.equipamentoApanhado;
    }

    public int getNumEquipamentoApanhado() {
        return this.numEquipamentosApanhados;
    }

    public void move( Mapa map, int destinoX, int destinoY, int tipoMovido ) {
        //no caso de tivermos a sair de cima de uma arma
        if ( twoHanded == 1 ) {
            map.getPosition(x,y).setEquipamento( equipamentoApanhado.get(0) );
            equipamentoApanhado.remove(0);
            twoHanded = 0;

            map.setPositionType(x,y,-1);
            map.setPositionType( destinoX, destinoY, tipoMovido );
            map.getPosition(destinoX,destinoY).setHuman( map.getPosition(x,y).getHuman() );
            map.getPosition(x,y).setHuman(null);
            x = destinoX;
            y = destinoY;
            return;
        }

        //no caso de tarmos a andar para cima de uma arma
        if ( map.getMapId(destinoX,destinoY) == -1 ) {
            equipamentoApanhado.add( map.getPosition(destinoX,destinoY).getEquipamento() );
            map.getPosition(destinoX,destinoY).setEquipamento(null);
            apanharEquipamento();

            if ( equipamentoApanhado.size() != 1 ) {
                twoHanded = 1;
            }
        }

        //mover normalmente
        map.setPositionType( x, y, 0 );
        map.setPositionType( destinoX, destinoY, tipoMovido );
        map.getPosition(destinoX,destinoY).setHuman( map.getPosition(x,y).getHuman() );
        map.getPosition(x,y).setHuman(null);
        x = destinoX;
        y = destinoY;
    }

    public void apanharEquipamento() {
        numEquipamentosApanhados++;
    }

    public int getTwoHanded() {
        return twoHanded;
    }

    public void setTwoHanded( int twoHanded ) {
        this.twoHanded = twoHanded;
    }
}
