package pt.ulusofona.lp2.theWalkingDEISIGame;

public class InvalidTWDInitialFileException extends Exception {

    private int numCreaturesSeen;
    private String[] arraySeen;
    private String lineSeen;

    public InvalidTWDInitialFileException() {
    }

    public InvalidTWDInitialFileException(int numCreaturesSeen, String lineSeen) {
        this.numCreaturesSeen = numCreaturesSeen;
        this.lineSeen = lineSeen;
    }

    public InvalidTWDInitialFileException(int numCreaturesSeen, String[] arraySeen, String lineSeen) {
        this.numCreaturesSeen = numCreaturesSeen;
        this.arraySeen = arraySeen;
        this.lineSeen = lineSeen;
    }

    //Retorna true se o ficheiro tiver pelo menos 2 creaturas
    public boolean validNrOfCreatures() {
        return numCreaturesSeen >= 2;
    }


    public boolean validCreatureDefinition() {
        if ( arraySeen == null ) {
            return false;
        }

        return arraySeen.length == 5;
    }

    public String getErroneousLine() {
        return lineSeen;
    }
}
