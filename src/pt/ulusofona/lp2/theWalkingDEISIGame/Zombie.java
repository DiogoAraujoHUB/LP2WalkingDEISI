package pt.ulusofona.lp2.theWalkingDEISIGame;

import pt.ulusofona.lp2.theWalkingDEISIGame.classesCriaturas.*;
import pt.ulusofona.lp2.theWalkingDEISIGame.classesEquipamentos.CabecaAlho;

import java.util.ArrayList;
import java.util.List;

public abstract class Zombie extends Creature {

    public Zombie(int id, String nome, int x, int y) {
        super(id, nome, x, y);

        this.tipo = 1;
    }

    public void move( Mapa map, int destinoX, int destinoY ) {
        //parte o equipamento na posição que move para
        //Será que tamos a retirar o equipamento da lista de equipamentos quando partimos? Tenho que testar
        if ( map.getMapId(destinoX, destinoY)  == -1 ) {
            pickEquipment();
            map.getPosition( destinoX, destinoY ).setEquipamento(null);
        }

        //move normalmente
        map.setPositionType(x, y, 0);
        map.setPositionType( destinoX, destinoY, 1);
        map.getPosition(destinoX, destinoY ).setCreature( map.getPosition(x,y).getCreature() );
        map.getPosition( x, y ).setCreature( null );
        x = destinoX;
        y = destinoY;
    }

    public Creature convert(Mapa map, Creature creatureAttacked, int xD, int yD) {
        Creature zombieMade = null;

        int id = creatureAttacked.getId();
        String nome = creatureAttacked.getNome();
        int x = creatureAttacked.getX();
        int y = creatureAttacked.getY();

        if (creatureAttacked instanceof IdosoHumano) {
            zombieMade = new IdosoZombie(id, nome, x, y);
        }
        if (creatureAttacked instanceof CriancaHumano) {
            zombieMade = new CriancaZombie(id, nome, x, y);
        }
        if (creatureAttacked instanceof MilitarHumano) {
            zombieMade = new MilitarZombie(id, nome, x, y);
        }
        if (creatureAttacked instanceof AdultoHumano) {
            zombieMade = new AdultoZombie(id, nome, x, y);
        }

        map.getPosition(xD,yD).setCreature(null);
        map.getPosition(xD,yD).setCreature(zombieMade);
        map.setPositionType(xD,yD,1);
        return zombieMade;
    }

    public abstract String toString();

    public abstract String getImagePNG();
}
