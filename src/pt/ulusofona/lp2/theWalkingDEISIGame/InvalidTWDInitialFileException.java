package pt.ulusofona.lp2.theWalkingDEISIGame;

public class InvalidTWDInitialFileException extends Exception {

    public InvalidTWDInitialFileException() {
    }

    //Retorna true se o ficheiro tiver pelo menos 2 creaturas
    public boolean validNrOfCreatures() {
        return true;
    }

    public boolean validCreatureDefinition() {
        return true;
    }


    public String getErroneousLine() {
        return "line";
    }
}
