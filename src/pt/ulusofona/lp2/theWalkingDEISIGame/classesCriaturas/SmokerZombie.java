package pt.ulusofona.lp2.theWalkingDEISIGame.classesCriaturas;

import pt.ulusofona.lp2.theWalkingDEISIGame.Creature;
import pt.ulusofona.lp2.theWalkingDEISIGame.Zombie;

public class SmokerZombie extends Zombie {

    //O Smoker, sendo um mini-boss, vai poder ser atacado duas vezes
    private int amountOfLifeLeft;
    private boolean currentlyPulling;
    private Creature creatureBeingPulled;

    public SmokerZombie(int id, String nome, int x, int y) {
        super(id, nome, x, y);

        this.tipo = 10;
        this.deslocamentoMaximo = 2;

        this.ableToMoveFreely = false;
        this.ableToMoveLinearly = true;

        this.amountOfLifeLeft = 2;

        this.currentlyPulling = false;
        this.creatureBeingPulled = null;
    }

    public int getAmountOfLifeLeft() {
        return this.amountOfLifeLeft;
    }

    //xD and yD are the position the creature is in
    public void useTongueToPull(Creature creatureAttacked, int xD, int yD) {

    }

    public void takeHit() {
        if ( amountOfLifeLeft == 0 ) {
            return;
        }

        this.amountOfLifeLeft--;
    }

    public String toString() {
        String texto = id + " | Smoker (Zombie) | Os Outros | " + nome.trim() + " ";

        if ( hasDied ) {
            texto += numEquipamentos + " @ RIP";
        } else {
            texto += numEquipamentos + " @ (" + x + ", " + y + ")";
        }

        return texto;
    }

    public String getImagePNG() {
        return "SmokerZombie.png";
    }
}
