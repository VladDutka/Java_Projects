import dsa.DiGraph;
import dsa.SeparateChainingHashST;
import dsa.Set;
import stdlib.In;
import stdlib.StdOut;

public class WordNet {
    // Symbol table to map noun to set of synset ids.
    SeparateChainingHashST<String, Set<Integer>> st = new SeparateChainingHashST<>();

    // Symbol table to map synset id to the corresponding synset string.
    SeparateChainingHashST<Integer, String> rst = new SeparateChainingHashST<>();

    // SCA object used to find shortest common ancestor.
    ShortestCommonAncestor sca;

    // Constructs a WordNet object given the names of the input (synset and hypernym) files.
    public WordNet(String synsets, String hypernyms) {
        // If either of the 2 input files are empty, throw an exception.
        if (synsets == null) {
            throw new NullPointerException("synsets is null");
        }   else if (hypernyms == null) {
            throw new NullPointerException("hypernyms is null");
        }

        // Read the input and store in the 2 text files.
        In synsetsInput = new In(synsets);
        In hypernymsInput = new In(hypernyms);
        // String to store full synsets file.
        String synsetsIn = synsetsInput.readAll();
        // String to store full hypernyms file.
        String hypernymsIn = hypernymsInput.readAll();     

        // For each line in the synset file, there is an i for a synset, the noun, and the gloss.
        // Connect the noun to a set of synset ids, and then map the synset id to the synset group.
         
        String[] synsetLines = synsetsIn.split("\n");
        for (int index = 0; index < synsetLines.length; index++) {
            String[] nounInfo = synsetLines[index].split(",");
            int id = Integer.parseInt(nounInfo[0]);
            String[] synset = nounInfo[1].split(" ");
            for (String noun: synset) {
                if (!st.contains(noun)) {
                    st.put(noun, new Set<>());
                }
                st.get(noun).add(id);
            }
            rst.put(id, nounInfo[1]);
        }

        // Holds connections between the synsets.
        DiGraph G = new DiGraph(synsetLines.length);  

        // Store the connections between synsets in digraph g.
        String[] hypernymLines = hypernymsIn.split("\n");

        for (int index = 0; index < hypernymLines.length; index++) {
            String[] pair = hypernymLines[index].split(",");
            if (pair.length == 1) {
                break;
            }
            G.addEdge(Integer.parseInt(pair[0]), Integer.parseInt(pair[1]));
        }

        // Initialize sca.
        sca = new ShortestCommonAncestor(G);
    }

    // Returns all WordNet nouns.
    public Iterable<String> nouns() {
        return st.keys();
    }

    // Returns true if the given word is a WordNet noun, and false otherwise.
    public boolean isNoun(String word) {
        if (word == null) {
            throw new NullPointerException("word is null");
        }
        return st.contains(word);
    }

    // Returns a synset that is a shortest common ancestor of noun1 and noun2.
    public String sca(String noun1, String noun2) {
        if (noun1 == null) {
            throw new NullPointerException("noun1 is null");
        }
        if (noun2 == null) {
            throw new NullPointerException("noun2 is null");
        }
        if (!isNoun(noun1)) {
            throw new IllegalArgumentException("noun1 is not a noun");
        }
        if (!isNoun(noun2)) {
            throw new IllegalArgumentException("noun2 is not a noun");
        }

        int ancestor = sca.ancestor(st.get(noun1), st.get(noun2));

        // Return the Synset id of the shortest common ancestor.
        return rst.get(ancestor);
    }

    // Returns the length of the shortest ancestral path between noun1 and noun2.
    public int distance(String noun1, String noun2) {
        if (noun1 == null) {
            throw new NullPointerException("noun1 is null");
        }
        if (noun2 == null) {
            throw new NullPointerException("noun2 is null");
        }
        if (!isNoun(noun1)) {
            throw new IllegalArgumentException("noun1 is not a noun");
        }
        if (!isNoun(noun2)) {
            throw new IllegalArgumentException("noun2 is not a noun");
        }
        // Find the shortest length between the 2 nouns.
        return sca.length(st.get(noun1), st.get(noun2));
    }

    // Unit tests the data type. [DO NOT EDIT]
    public static void main(String[] args) {
        WordNet wordnet = new WordNet(args[0], args[1]);
        String word1 = args[2];
        String word2 = args[3];
        int nouns = 0;
        for (String noun : wordnet.nouns()) {
            nouns++;
        }
        StdOut.printf("# of nouns = %d\n", nouns);
        StdOut.printf("isNoun(%s)? %s\n", word1, wordnet.isNoun(word1));
        StdOut.printf("isNoun(%s)? %s\n", word2, wordnet.isNoun(word2));
        StdOut.printf("isNoun(%s %s)? %s\n", word1, word2, wordnet.isNoun(word1 + " " + word2));
        StdOut.printf("sca(%s, %s) = %s\n", word1, word2, wordnet.sca(word1, word2));
        StdOut.printf("distance(%s, %s) = %s\n", word1, word2, wordnet.distance(word1, word2));
    }
}
