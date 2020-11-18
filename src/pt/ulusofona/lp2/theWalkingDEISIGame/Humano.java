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

    public List<Equipamento> getEquipamentoApanhado() {
        return this.equipamentoApanhado;
    }

    public int getNumEquipamentoApanhado() {
        return this.numEquipamentosApanhados;
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
