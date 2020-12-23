package pt.ulusofona.lp2.theWalkingDEISIGame;

public abstract class Animal extends Creature {

    public Animal(int id, int tipo, String nome, int x, int y) {
        super(id, tipo, nome, x, y);
    }

    public void move( Mapa map, int destinoX, int destinoY, int tipoMovido ) {
        int tipoDeixado = 0;

        //no caso de tarmos a andar para cima de uma arma
        //O cão come o equipamento
        if ( map.getMapId(destinoX,destinoY) == -1 ) {
            pickEquipment();
            map.getPosition( destinoX, destinoY ).setEquipamento(null);
        }

        //mover normalmente
        map.setPositionType( x, y, tipoDeixado );
        map.setPositionType( destinoX, destinoY, tipoMovido );
        map.getPosition(destinoX,destinoY).setCreature( map.getPosition(x,y).getCreature() );
        map.getPosition(x,y).setCreature(null);
        x = destinoX;
        y = destinoY;
    }

    public abstract String getImagePNG();

    public abstract String toString();
}
