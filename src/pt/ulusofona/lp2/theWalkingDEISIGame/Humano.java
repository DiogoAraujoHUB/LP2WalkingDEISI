package pt.ulusofona.lp2.theWalkingDEISIGame;

import pt.ulusofona.lp2.theWalkingDEISIGame.classesCriaturas.*;
import pt.ulusofona.lp2.theWalkingDEISIGame.classesEquipamentos.*;

public abstract class Humano extends Creature {

    protected Equipamento equipamentoApanhado;

    protected boolean insideSafeHaven;
    protected boolean envenenado;
    protected int turnosRestantes;

    public Humano(int id, String nome, int x, int y) {
        super(id, nome, x, y);

        this.tipo = 1;
        this.insideSafeHaven = false;
        this.envenenado = false;
        this.turnosRestantes = 6;

        this.equipamentoApanhado = null;
    }

    public Equipamento getEquipamentoApanhado() {
        return this.equipamentoApanhado;
    }

    public void setEquipamentoApanhado( Equipamento equipamento ) {
        this.equipamentoApanhado = equipamento;
    }

    public boolean getInsideSafeHaven() {
        return this.insideSafeHaven;
    }

    public void goInsideSafeHaven() {
        this.insideSafeHaven = true;
    }

    public boolean getEnvenenado() {
        return this.envenenado;
    }

    public int getTurnosRestantes() {
        return this.turnosRestantes;
    }

    public void move(Mapa map, int destinoX, int destinoY) {
        int tipoDeixado = 0;

        //Vamos verificar se o humano é idoso, logo se tiver um equipamento e mover,
        //Larga esse equipamento que antes tinha apanhado na posição onde o apanhou
        if ( map.getPosition(x,y).getCreature() instanceof IdosoHumano && equipamentoApanhado != null ) {
            tipoDeixado = -1;
            map.getPosition(x,y).setEquipamento(equipamentoApanhado);

            equipamentoApanhado = null;
        }

        //no caso de tarmos a andar para cima de uma arma
        if ( map.getMapId(destinoX,destinoY) == -1 ) {
            //verifica se ja tem uma arma
            if ( equipamentoApanhado != null ) {
                tipoDeixado = - 1;
                map.getPosition(x,y).setEquipamento( equipamentoApanhado );
                equipamentoApanhado.setX(x);
                equipamentoApanhado.setY(y);
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

        //Fazer com que o equipamento se movesse com o humano
        if (equipamentoApanhado != null) {
            equipamentoApanhado.setX(x);
            equipamentoApanhado.setY(y);
        }
    }

    public boolean attack(Mapa map, Creature creatureAttacked, int xD, int yD) {

        //Vamos ver se estamos a atacar com uma criança humana com uma espada
        if ( map.getPosition(x,y).getCreature() instanceof CriancaHumano ) {
            if (equipamentoApanhado instanceof EspadaHattoriHanzo) {
                if (creatureAttacked instanceof CriancaZombie) {
                    map.getPosition(xD,yD).setCreature(null);
                    move(map, xD, yD);

                    return true;
                }

                return false;
            }
        }

        //Verificar se estamos a atacar um vampiro!
        if ( creatureAttacked instanceof VampiroZombie ) {

            //Para matar um vampiro, necessitamos de usar uma estaca
            if ( equipamentoApanhado instanceof EstacaMadeira ) {
                map.getPosition(xD,yD).setCreature(null);
                move(map, xD, yD);

                return true;
            }

            return false;
        }

        //Ver se estamos a utilizar uma pistola para atacar o zombie
        if ( equipamentoApanhado instanceof PistolaWaltherPPK ) {

            //A pistola já não tem balas!
            if ( equipamentoApanhado.getNumUses() == 0 ) {
                return false;
            }

            //Reduce number of bullets by one and shoot zombie
            equipamentoApanhado.setNumUses(equipamentoApanhado.getNumUses() - 1);
            map.getPosition(xD,yD).setCreature(null);
            move(map, xD, yD);
            return true;
        }

        //Attack normally
        map.getPosition(xD,yD).setCreature(null);
        move(map, xD, yD);
        return true;
    }

    public boolean defend(Mapa map, Creature creatureAttacking) {
        //O vampiro não pode atacar alguem que tenha uma cabeca de alho
        if (equipamentoApanhado instanceof CabecaAlho) {
            if ( creatureAttacking instanceof VampiroZombie ) {
                return true;
            }

            return false;
        }
        //O zombie idoso não pode atacar um humano com a revista maria
        if ( equipamentoApanhado instanceof RevistaMaria ) {
            if ( creatureAttacking instanceof IdosoZombie ) {
                return true;
            }

            return false;
        }

        //O equipamento que o humano tem é um escudo de madeira
        if (equipamentoApanhado instanceof EscudoMadeira) {
            //Se quem o estiver a empunhar for um militar, então tem mais um uso
            if ( ((EscudoMadeira) equipamentoApanhado).getUtilizadoPorMilitar() &&
                    map.getPosition(x,y).getCreature() instanceof MilitarHumano) {

                ((EscudoMadeira) equipamentoApanhado).setUtilizadoPorMilitar(false);
                return true;
            }

            if ( equipamentoApanhado.getNumUses() == 0 ) {
                return false;
            }

            equipamentoApanhado.setNumUses(equipamentoApanhado.getNumUses() - 1);
            return true;
        }

        if (equipamentoApanhado instanceof GarrafaLixivia) {
            if ( equipamentoApanhado.getNumUses() == 0 ) {
                return false;
            }

            equipamentoApanhado.setNumUses(equipamentoApanhado.getNumUses() - 1);
            return true;
        }

        return true;
    }

    //Same as attack, but without the move
    public boolean defendWithAttack(Mapa map, Creature creatureAttacked, int xD, int yD) {
        //Ver se temos uma garrafa de lixivia para nos defendermos com
        if (equipamentoApanhado instanceof GarrafaLixivia) {
            //Já não conseguimos utilizar a garrafa pois está vazia!
            if ( equipamentoApanhado.getNumUses() == 0 ) {
                return false;
            }

            //Vamos usar um pouco da garrafa para nos defendermos do zombie
            equipamentoApanhado.setNumUses(equipamentoApanhado.getNumUses() - 1);
            map.getPosition(xD,yD).setCreature(null);
            return true;
        }

        //Vamos ver se isto é uma criança humana
        if ( map.getPosition(x,y).getCreature() instanceof CriancaHumano ) {
            if (equipamentoApanhado instanceof EspadaHattoriHanzo) {
                if (creatureAttacked instanceof CriancaZombie) {
                    map.getPosition(xD,yD).setCreature(null);
                    return true;
                }

                return false;
            }
        }

        //Verificar se estamos a atacar um vampiro!
        if ( creatureAttacked instanceof VampiroZombie ) {

            //Para matar um vampiro, necessitamos de usar uma estaca
            if ( equipamentoApanhado instanceof EstacaMadeira ) {
                map.getPosition(xD,yD).setCreature(null);
                return true;
            }

            return false;
        }

        //Ver se estamos a utilizar uma pistola para atacar o zombie
        if ( equipamentoApanhado instanceof PistolaWaltherPPK ) {

            //A pistola já não tem balas!
            if ( equipamentoApanhado.getNumUses() == 0 ) {
                return false;
            }

            //Reduce number of bullets by one and shoot zombie
            equipamentoApanhado.setNumUses(equipamentoApanhado.getNumUses() - 1);
            map.getPosition(xD,yD).setCreature(null);
            return true;
        }

        //Attack normally
        map.getPosition(xD,yD).setCreature(null);
        return true;
    }

    public abstract String toString();

    public abstract String getImagePNG();
}
