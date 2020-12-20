package pt.ulusofona.lp2.theWalkingDEISIGame;

import java.util.ArrayList;
import java.util.List;

public class Zombie extends Creature {

    Zombie( int id, int tipo, String nome, int x, int y, int deslocamentoMaximo, boolean moveFree ) {
        super(id, tipo, nome, x, y, deslocamentoMaximo, moveFree);
    }

    public String getImagePNG() {
        return "Zombie.png";
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

    public String toString() {
        String creaturaVista = "";

        switch ( tipo ) {

            case 0:
                creaturaVista = "Criança (Zombie)";
                break;

            case 1:
                creaturaVista = "Adulto (Zombie)";
                break;

            case 2:
                creaturaVista = "Militar (Zombie)";
                break;

            case 3:
                creaturaVista = "Idoso (Zombie)";
                break;

            case 4:
                creaturaVista = "Zombie Vampiro";
                break;

            default:
                creaturaVista = "Zombie";
        }

        String texto = id + " | " + creaturaVista + " | Os Outros |" + nome + "";
        texto += numEquipamentos + " @ (" + x + ", " + y + ")";
        return texto;
    }
}
