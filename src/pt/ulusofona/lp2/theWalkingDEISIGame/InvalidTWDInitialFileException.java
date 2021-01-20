package pt.ulusofona.lp2.theWalkingDEISIGame;

public class InvalidTWDInitialFileException extends Exception {

    private int numCreaturesSeen;
    private String[] lineSeen;

    public InvalidTWDInitialFileException() {
    }

    public InvalidTWDInitialFileException(int numCreaturesSeen) {
        this.numCreaturesSeen = numCreaturesSeen;
    }

    public InvalidTWDInitialFileException(String[] lineSeen) {
        this.lineSeen = lineSeen;
    }

    //Retorna true se o ficheiro tiver pelo menos 2 creaturas
    public boolean validNrOfCreatures() {
        if ( numCreaturesSeen < 2 ) {
            return false;
        }

        return true;
    }


    public boolean validCreatureDefinition() {
        if ( lineSeen.length != 5 ) {
            return false;
        }

        return true;
    }


    public String getErroneousLine() {
        return "line";
    }
}
