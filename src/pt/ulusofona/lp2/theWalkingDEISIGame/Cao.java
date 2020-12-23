package pt.ulusofona.lp2.theWalkingDEISIGame;

public class Cao extends Animal {

    public Cao(int id, int tipo, String nome, int x, int y) {
        super(id, tipo, nome, x, y);

        this.deslocamentoMaximo = 2;
    }

    public String getImagePNG() {
        return null;
    }

    public String toString() {
        String texto = id + " | Cão | Os Vivos |" + nome + "";
        texto += numEquipamentos + " @ (" + x + ", " + y + ")";

        return texto;
    }
}
