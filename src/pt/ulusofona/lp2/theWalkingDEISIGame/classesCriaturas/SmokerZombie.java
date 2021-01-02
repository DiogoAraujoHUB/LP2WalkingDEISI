package pt.ulusofona.lp2.theWalkingDEISIGame.classesCriaturas;

import pt.ulusofona.lp2.theWalkingDEISIGame.Creature;
import pt.ulusofona.lp2.theWalkingDEISIGame.Humano;
import pt.ulusofona.lp2.theWalkingDEISIGame.Mapa;
import pt.ulusofona.lp2.theWalkingDEISIGame.Zombie;

public class SmokerZombie extends Zombie {

    private boolean currentlyAttacking;

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

        this.currentlyAttacking = false;
        this.currentlyPulling = false;
        this.creatureBeingPulled = null;
    }

    public boolean getCurrentlyAttacking() {
        return this.currentlyAttacking;
    }

    public void setCurrentlyAttacking(boolean currentlyAttacking) {
        this.currentlyAttacking = currentlyAttacking;
    }

    public boolean getCurrentlyPulling() {
        return this.currentlyPulling;
    }

    public Creature getCreatureBeingPulled() {
        return this.creatureBeingPulled;
    }

    public void stopPulling() {
        this.creatureBeingPulled = null;
        this.currentlyPulling = false;
    }

    public int getAmountOfLifeLeft() {
        return this.amountOfLifeLeft;
    }

    //The Smoker from left 4 dead 2 uses his tongue to pull a human onto him then attack him
    public void useTongueToPull(Mapa map) {
        //Se não termos criatura para puxar, ou não tivermos a puxar ninguem de momento, não funciona
        if ( creatureBeingPulled == null || currentlyPulling == false ) {
            return;
        }

        int xFound = creatureBeingPulled.getX();
        int yFound = creatureBeingPulled.getY();

        if ( xFound == x ) {
            if ( yFound > y ) {
                //Se estiver algo no caminho, então não nos movemos
                if ( map.getPosition(xFound, yFound - 1).getTipo() != 0 ) {
                    return;
                }

                creatureBeingPulled.move(map, xFound, yFound - 1);
            } else {
                //Se estiver algo no caminho, então não nos movemos
                if ( map.getPosition(xFound, yFound + 1).getTipo() != 0 ) {
                    return;
                }

                creatureBeingPulled.move(map, xFound, yFound + 1);
            }
        } else {
            if ( xFound > x ) {
                //Se estiver algo no caminho, então não nos movemos
                if ( map.getPosition(xFound - 1, yFound).getTipo() != 0 ) {
                    return;
                }

                creatureBeingPulled.move(map, xFound - 1, yFound);
            } else {
                //Se estiver algo no caminho, então não nos movemos
                if ( map.getPosition(xFound + 1, yFound).getTipo() != 0 ) {
                    return;
                }

                creatureBeingPulled.move(map, xFound + 1, yFound);
            }
        }
    }

    //posX and posY are the position the creature is in
    public boolean grabCreature(Mapa map, Creature creatureAttacked) {
        //Não consegue agarrar criaturas que estejam ao seu lado
        if ( calculateDistanceToPosition(creatureAttacked.getX(), creatureAttacked.getY() ) == 1 ) {
            return false;
        }

        if ( creatureAttacked instanceof Humano) {
            ((Humano) creatureAttacked).getGrabbed(map.getPosition(x, y).getCreature() );
        }

        creatureBeingPulled = creatureAttacked;
        currentlyPulling = true;
        return true;
    }

    public int calculateDistanceToHumano() {
        int distance = 0;

        if ( creatureBeingPulled.getX() == x) {
            if ( creatureBeingPulled.getY() > y) {
                distance = creatureBeingPulled.getY() - y;
            } else {
                distance = y - creatureBeingPulled.getY();
            }
        } else {
            if ( creatureBeingPulled.getX() > x ) {
                distance = creatureBeingPulled.getX() - x;
            } else {
                distance = x - creatureBeingPulled.getX();
            }
        }

        return distance;
    }

    public int calculateDistanceToPosition(int xD, int yD) {
        int distance = 0;

        if ( xD == x) {
            if ( yD > y) {
                distance = yD - y;
            } else {
                distance = y - yD;
            }
        } else if ( yD == y ) {
            if ( xD > x ) {
                distance = xD- x;
            } else {
                distance = x - xD;
            }
        }

        return distance;
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
