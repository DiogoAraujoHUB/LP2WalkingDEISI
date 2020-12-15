package pt.ulusofona.lp2.theWalkingDEISIGame;

import java.util.ArrayList;
import java.util.List;

public class Zombie extends Creature {

    Zombie( int id, String nome, int x, int y, int deslocamentoMaximo, boolean moveFree ) {
        super(id, nome, x, y, deslocamentoMaximo, moveFree);
    }

    public String getImagePNG() {
        return "Zombie.png";
    }

    public String toString() {
        String texto = id + " | Zombie | Os Outros |" + nome + "";
        texto += numEquipamentos + " @ (" + x + ", " + y + ")";

        return texto;
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
        map.setPositionType( destinoX, destinoY, 3 );
        map.getPosition(destinoX, destinoY ).setCreature( map.getPosition(x,y).getCreature() );
        map.getPosition( x, y ).setCreature( null );
        x = destinoX;
        y = destinoY;
    }

}
