package pt.ulusofona.lp2.theWalkingDEISIGame;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class TWDGameManager {
    List<Humano> humanos = new ArrayList<>();
    List<Zombie> zombies = new ArrayList<>();
    int initialTeam = 0;

    //Construtor vazio
    public TWDGameManager() {
    }

    //leitura do ficheiro texto
    //e carregar para a memória a informação relevante
    public boolean startGame( File ficheiroInicial ) {
        

        return true;
    }

    //Tem que devolver o tamanho do bairro
    //Conforme lido do ficheiro respetivo
    //na posição 0 tem que ser lido o número de linhas
    //e na posição 1 o número de colunas
    public int[] getWorldSize() {
        int[] worldSize = new int[2];
        int numLinhas = 0;
        int numColunas = 0;

        return worldSize;
    }

    //Devolve o ID da equipa que vai jogar no primeiro turno
    public int getInitialTeam() {
        return this.initialTeam;
    }

    //Devolve uma lista com todos os objetos "Humano" no jogo
    public List<Humano> getHumans() {
        return this.humanos;
    }

    //Devolve uma lista com todos os objetos "Zombie" no jogo
    public List<Zombie> getZombies() {
        return this.zombies;
    }
}
