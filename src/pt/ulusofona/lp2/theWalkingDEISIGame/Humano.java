package pt.ulusofona.lp2.theWalkingDEISIGame;

public abstract class Humano extends Creature {

    private Equipamento equipamentoApanhado;

    public Humano(int id, int tipo, String nome, int x, int y) {
        super(id, tipo, nome, x, y);

        this.equipamentoApanhado = null;
    }

    public Equipamento getEquipamentoApanhado() {
        return this.equipamentoApanhado;
    }

    public void setEquipamentoApanhado( Equipamento equipamento ) {
        this.equipamentoApanhado = equipamento;
    }

    public void move( Mapa map, int destinoX, int destinoY, int tipoMovido ) {
        int tipoDeixado = 0;

        //no caso de tarmos a andar para cima de uma arma
        if ( map.getMapId(destinoX,destinoY) == -1 ) {
            //verifica se ja tem uma arma
            if ( equipamentoApanhado != null ) {
                tipoDeixado = - 1;
                map.getPosition(x,y).setEquipamento( equipamentoApanhado );
            }

            equipamentoApanhado =  map.getPosition(destinoX,destinoY).getEquipamento();
            map.getPosition(destinoX,destinoY).setEquipamento(null);
            pickEquipment();
        }

        //mover normalmente
        map.setPositionType( x, y, tipoDeixado );
        map.setPositionType( destinoX, destinoY, tipoMovido );
        map.getPosition(destinoX,destinoY).setCreature( map.getPosition(x,y).getCreature() );
        map.getPosition(x,y).setCreature(null);
        x = destinoX;
        y = destinoY;
    }

    public abstract String toString();

    public abstract String getImagePNG();
}
