import java.io.*;

/** CompactPrefixTree class, implements Dictionary ADT and
 *  several additional methods. Can be used as a spell checker.
 *  Fill in code in the methods of this class. You may add additional methods. */
public class CompactPrefixTree implements Dictionary {

    private Node root; // the root of the tree

    /** Default constructor.
     * Creates an empty "dictionary" (compact prefix tree).
     * */
    public CompactPrefixTree(){
        root = new Node();
    }

    /**
     * Creates a dictionary ("compact prefix tree")
     * using words from the given file.
     * @param filename the name of the file with words
     */
    public CompactPrefixTree(String filename) {
        // FILL IN CODE:
        // Read each word from the file, add it to the tree
        root = new Node();
        int i = 0;
        try(BufferedReader reader = new BufferedReader(new FileReader(filename))){
            String line = reader.readLine();
            while(line != null){
                add(line);
                line = reader.readLine();
//                System.out.println("adding:"+ (i++));
            }
        }catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void found(){
        Node ap = new Node();
        ap.prefix = "ap";
        root.children[getIndex('a')] = ap;
        Node ca = new Node();
        ca.prefix = "ca";
        root.children[getIndex('c')] = ca;

        Node e = new Node();
        e.prefix = "e";
        e.isWord = true;
        ap.children[getIndex('e')] = e;
        Node ple = new Node();
        ple.prefix = "ple";
        ple.isWord = true;
        ap.children[getIndex('p')] = ple;

        Node rt = new Node();
        rt.prefix = "rt";
        rt.isWord = true;
        ca.children[getIndex('r')] = rt;
        Node t = new Node();
        t.prefix = "t";
        t.isWord = true;
        ca.children[getIndex('t')] = t;

        Node s = new Node();
        s.prefix = "s";
        s.isWord = true;
        t.children[getIndex('s')] = s;
    }

    /** Adds a given word to the dictionary.
     * @param word the word to add to the dictionary
     */
    public void add(String word) {
        root = add(word.toLowerCase(), root); // Calling private add method
    }

    /**
     * Checks if a given word is in the dictionary
     * @param word the word to check
     * @return true if the word is in the dictionary, false otherwise
     */
    public boolean check(String word) {
        return check(word.toLowerCase(), root); // Calling private check method
    }

    /**
     * Checks if a given prefix is a prefix of any word stored in the dictionary
     * @param prefix The prefix of a word
     * @return true if the prefix is a prefix of any word in the dictionary, false otherwise
     */
    public boolean checkPrefix(String prefix) {
        return checkPrefix(prefix.toLowerCase(), root); // Calling private checkPrefix method
    }


    /**
     * Prints all the words in the dictionary, in alphabetical order,
     * one word per line.
     */
    public void print() {
        print("", root); // Calling private print method
    }

    /**
     * Print out the nodes of the compact prefix tree, in a pre-order fashion.
     * First, print out the root at the current indentation level
     * (followed by * if the node's valid bit is set to true),
     * then print out the children of the node at a higher indentation level.
     */
    public void printTree() {
        // FILL IN CODE
        printTreeHelper(root, 0);
//        System.out.println(toString("", root, 0));
    }

    private void printTreeHelper(Node node, int indent){
        if (node != null) {
            for(int i = 0; i < indent; i++) System.out.print(" ");
            if(node.isWord) {
                System.out.println(node.prefix + "*");
            }else{
                System.out.println(node.prefix);
            }
            for(Node child: node.children){
                printTreeHelper(child, indent + 1);
            }
        }
    }

    /**
     * Print out the nodes of the tree to a file, using indentations to specify the level
     * of the node.
     * @param filename the name of the file where to output the tree
     */
    public void printTree(String filename) {
        // FILL IN CODE
        // Same as printTree, but outputs info to a file
        try(PrintWriter pw = new PrintWriter(filename)){
            pw.write(toString("", root, 0));

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    public String toString(String s, Node node, int indent) {
        StringBuilder sb = new StringBuilder();
        sb.append(s);
        if(node == null){
            return sb.toString();
        }
        for(int i = 0; i < indent; i++) sb.append(" ");
        if(node.isWord) sb.append(node.prefix + "*\n");
        if(!node.isWord) sb.append(node.prefix + "\n");
        for(Node child: node.children){
            sb.append(toString(s, child, indent + 1));
        }

        return sb.toString();
    }

    /**
     * Return an array of the entries in the dictionary that are as close as possible to
     * the parameter word.  If the word passed in is in the dictionary, then
     * return an array of length 1 that contains only that word.  If the word is
     * not in the dictionary, then return an array of numSuggestions different words
     * that are in the dictionary, that are as close as possible to the target word.
     * Implementation details are up to you, but you are required to make it efficient
     * and make good use ot the compact prefix tree.
     *
     * @param word The word to check
     * @param numSuggestions The length of the array to return.  Note that if the word is
     * in the dictionary, this parameter will be ignored, and the array will contain a
     * single world.
     * @return An array of the closest entries in the dictionary to the target word
     */

    public String[] suggest(String word, int numSuggestions) {
        // FILL IN CODE
        // Note: you need to create a private suggest method in this class
        // (like we did for methods add, check, checkPrefix)


        return null; // don't forget to change it
    }

    // ---------- Private helper methods ---------------

    /**
     *  A private add method that adds a given string to the tree
     * @param s the string to add
     * @param node the root of a tree where we want to add a new string

     * @return a reference to the root of the tree that contains s
     */
    private Node add(String s, Node node) {
        // FILL IN CODE
//        System.out.println(s);
        if(node == null){
            Node newNode = new Node();
            newNode.prefix = s;
            newNode.isWord = true;
            return newNode;
        }
        if(node.prefix.equals(s)){
            if(!node.isWord) node.isWord = true;
            return node;
        }
        if(s.startsWith(node.prefix)){
            s = getSuffix(s, node.prefix);
            int i = getIndex(s.charAt(0));
            node.children[i] = add(s, node.children[i]);
            return node;
        }

        String common = findLongestCommonPrefix(s, node.prefix);
//        System.out.println(common);
        int l = common.length();
        node.prefix = node.prefix.substring(l);
        s = s.substring(l);
        Node newNode = new Node();
        newNode.prefix = common;
        newNode.children[getIndex(node.prefix.charAt(0))] = node;
        if(!s.isEmpty()) {
            newNode.children[getIndex(s.charAt(0))] = add(s, newNode.children[getIndex(s.charAt(0))]);
        }
        if(s.isEmpty()){
            newNode.isWord = true;
        }
        return newNode;
    }


    /** A private method to check whether a given string is stored in the tree.
     *
     * @param s the string to check
     * @param node the root of a tree
     * @return true if the prefix is in the dictionary, false otherwise
     */
    private boolean check(String s, Node node) {
        // FILL IN CODE
        if(node == null) return false;
        if(!s.startsWith(node.prefix)) return false;
        if(s.equals(node.prefix) && !node.isWord) return false;
        if(s.equals(node.prefix) && node.isWord) return true;

        s = getSuffix(s, node.prefix);
        int i = getIndex(s.charAt(0));
        return check(s, node.children[i]); // don't forget to change it
    }

    private String getSuffix(String sl, String ss){
        int i = ss.length();
        String result = sl.substring(i);
        return result;
    }

    /**
     * A private recursive method to check whether a given prefix is in the tree
     *
     * @param prefix the prefix
     * @param node the root of the tree
     * @return true if the prefix is in the dictionary, false otherwise
     */
    private boolean checkPrefix(String prefix, Node node) {
        // FILL IN CODE
        if(node == null) return false;
        if(prefix.length() <= node.prefix.length()){
            return node.prefix.startsWith(prefix);
        }
        if(!prefix.startsWith(node.prefix)) return false;


        prefix = getSuffix(prefix, node.prefix);
        int i = getIndex(prefix.charAt(0));
        return checkPrefix(prefix, node.children[i]); // don't forget to change it
    }

    /**
     * Outputs all the words stored in the dictionary
     * to the console, in alphabetical order, one word per line.
     * @param s the string obtained by concatenating prefixes on the way to this node
     * @param node the root of the tree
     */
    private void print(String s, Node node) {
        // FILL IN CODE
        if(node == null) return;
        if(node.isWord) {
            System.out.println(s + node.prefix);
        }
        for (int i = 0; i < 26; i++) {
            print(s + node.prefix, node.children[i]);
        }
    }
    /**
     * get the index of the character from a to z
     * @param c the character you want to get the index
     */
    public int getIndex(char c){
        return (int)c - (int)'a';
    }

    /**
     * computes the	 longest common	prefix	of	two	strings
     * return the index of the first not common letter
     * @param s1 the first string
     * @param s2 the secound string
     */
    public String findLongestCommonPrefix(String s1, String s2){
        int i = 0;
        while(i < Math.min(s1.length(), s2.length())){
            if(s1.charAt(i) != s2.charAt(i)){
                break;
            }
            i++;
        }
        return s1.substring(0, i);
    }

    // FILL IN CODE: add a private suggest method. Decide which parameters
    // it should have

    // --------- Private class Node ------------
    // Represents a node in a compact prefix tree
    private class Node {
        String prefix; // prefix stored in the node
        Node children[]; // array of children (26 children)
        boolean isWord; // true if by concatenating all prefixes on the path from the root to this node, we get a valid word

        Node() {
            isWord = false;
            prefix = "";
            children = new Node[26]; // initialize the array of children
        }
    }

}
