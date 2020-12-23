package pt.ulusofona.lp2.theWalkingDEISIGame;

import java.util.ArrayList;
import java.util.List;

public abstract class Zombie extends Creature {

    public Zombie(int id, int tipo, String nome, int x, int y) {
        super(id, tipo, nome, x, y);
    }

    public void move( Mapa map, int destinoX, int destinoY, int tipoMovido ) {
        //parte o equipamento na posição que move para
        //Será que tamos a retirar o equipamento da lista de equipamentos quando partimos? Tenho que testar
        if ( map.getMapId( destinoX, destinoY )  == -1 ) {

            pickEquipment();
            map.getPosition( destinoX, destinoY ).setEquipamento(null);
        }

        //move normalmente
        map.setPositionType( x, y, 0 );
        map.setPositionType( destinoX, destinoY, 1 );
        map.getPosition(destinoX, destinoY ).setCreature( map.getPosition(x,y).getCreature() );
        map.getPosition( x, y ).setCreature( null );
        x = destinoX;
        y = destinoY;
    }

    public abstract String toString();

    public abstract String getImagePNG();
}
