package pt.ulusofona.lp2.theWalkingDEISIGame;

public class Cao extends Animal {

    private Equipamento equipamentoApanhado;

    public Cao(int id, String nome, int x, int y) {
        super(id, nome, x, y);

        this.deslocamentoMaximo = 2;
    }

    public void move( Mapa map, int destinoX, int destinoY ) {
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
        map.setPositionType(x, y, tipoDeixado);
        map.setPositionType(destinoX, destinoY, 1);
        map.getPosition(destinoX,destinoY).setCreature(map.getPosition(x,y).getCreature());
        map.getPosition(x,y).setCreature(null);
        x = destinoX;
        y = destinoY;
    }

    public String getImagePNG() {
        return "Cão.png";
    }

    public String toString() {
        String texto = id + " | Cão | Os Vivos |" + nome + "";
        texto += numEquipamentos + " @ (" + x + ", " + y + ")";

        return texto;
    }
}
