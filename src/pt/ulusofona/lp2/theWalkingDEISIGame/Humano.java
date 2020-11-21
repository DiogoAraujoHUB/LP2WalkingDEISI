package pt.ulusofona.lp2.theWalkingDEISIGame;

public class Humano {
    private int id;
    private int x, y;
    private int numEquipamentosApanhados;

    private String nome;
    private Equipamento equipamentoApanhado;

    Humano(int id, String nome, int x, int y) {
        this.id = id;
        this.nome = nome;
        this.x = x;
        this.y = y;
        this.equipamentoApanhado = null;
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

    public int getY() {
        return this.y;
    }

    public String getNome() {
        return this.nome;
    }

    public String toString() {
        String texto = id + " | Humano | Os Vivos |" + nome + "";
        texto += numEquipamentosApanhados + " @ (" + x + ", " + y + ")";

        return texto;
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
            apanharEquipamento();
        }

        //mover normalmente
        map.setPositionType( x, y, tipoDeixado );
        map.setPositionType( destinoX, destinoY, tipoMovido );
        map.getPosition(destinoX,destinoY).setHuman( map.getPosition(x,y).getHuman() );
        map.getPosition(x,y).setHuman(null);
        x = destinoX;
        y = destinoY;
    }

    public void apanharEquipamento() {
        numEquipamentosApanhados++;
    }
}
