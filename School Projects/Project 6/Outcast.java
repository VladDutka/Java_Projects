import stdlib.StdIn;
import stdlib.StdOut;

public class Outcast {
    // The wordnet to use to find outcast.
    WordNet wordnet;    

    // Constructs an Outcast object given the WordNet semantic lexicon.
    public Outcast(WordNet wordnet) {
        this.wordnet = wordnet;
    }

    // Returns the outcast noun from nouns.
    public String outcast(String[] nouns) {
        // Index of the noun compared to the others
        int indexOfMax = -1;    
        // Biggest difference of the noun
        int maxValue = Integer.MIN_VALUE;   

        // For each noun, finds the biggest noun by going through the nouns
        // then it returns the noun.
        for (int i = 0; i < nouns.length; i++) {
            String currentNoun = nouns[i];
            int distance = 0;
            for (String noun : nouns) {
                distance += wordnet.distance(currentNoun, noun);
            }
            if (distance >= maxValue) {
                indexOfMax = i;
                maxValue = distance;
            }
        }

        return nouns[indexOfMax];
    }

    // Unit tests the data type. [DO NOT EDIT]
    public static void main(String[] args) {
        WordNet wordnet = new WordNet(args[0], args[1]);
        Outcast outcast = new Outcast(wordnet);
        String[] nouns = StdIn.readAllStrings();
        String outcastNoun = outcast.outcast(nouns);
        for (String noun : nouns) {
            StdOut.print(noun.equals(outcastNoun) ? "*" + noun + "* " : noun + " ");
        }
        StdOut.println();
    }
}
