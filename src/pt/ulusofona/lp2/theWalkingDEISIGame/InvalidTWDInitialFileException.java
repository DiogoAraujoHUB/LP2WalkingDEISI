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

    public InvalidTWDInitialFileException(int numCreaturesSeen, String[] lineSeen) {
        this.numCreaturesSeen = numCreaturesSeen;
        this.lineSeen = lineSeen;
    }

    //Retorna true se o ficheiro tiver pelo menos 2 creaturas
    public boolean validNrOfCreatures() {
        return numCreaturesSeen >= 2;
    }


    public boolean validCreatureDefinition() {
        return lineSeen.length == 5;
    }


    public String getErroneousLine() {
        String line = "";

        for ( int pos = 0; pos < lineSeen.length; pos++ ) {
            if ( pos == lineSeen.length - 1 ) {
                line += lineSeen[pos];
            } else {
                line = lineSeen[pos] + " - ";
            }
        }

        return line;
    }
}
