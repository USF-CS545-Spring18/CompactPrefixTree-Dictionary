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
            }
        }catch (IOException e) {
            e.printStackTrace();
        }

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
    }

    /**
     * the helper method of printTree, take the indent and the node of root
     * @param node
     * @param indent
     */
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

    /**
     * Save tree to String, help print it in a file
     * @param s
     * @param node
     * @param indent
     * @return
     */
    public String toString(String s, Node node, int indent) {
        StringBuilder sb = new StringBuilder();
        sb.append(s);
        if(node == null){
            return sb.toString();
        }
        for(int i = 0; i < indent; i++) sb.append(" ");
        if(node.isWord) sb.append(node.prefix + "*" + System.lineSeparator());
        if(!node.isWord) sb.append(node.prefix + System.lineSeparator());
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
        String[] ss;
        //check if the word is in the tree, if so, just return an array only having the word
        if(check(word)){
            ss = new String[1];
            ss[0] = word;
            return ss;
        }
        //if not, call the helper method
        ss = new String[numSuggestions];
        ss = suggest("", word, numSuggestions, 0, ss, root);

        return ss; // don't forget to change it
    }

    /**
     * the helper method of suggest, recursively dig into the tree, keep tracking
     * the array size all the time to make sure once the array is full, break out the program.
     *
     * Note: when this method is called, it means the search word is not in the dictionary;
     *
     * Algorithm: 1. check the index, if index == numSuggestions which means the array is full,
     *            return the array without any change;
     *            2. check the node, if the node is null, which means there is no words and there
     *            will be no words in the deeper place, so return the array without any change;
     *            3. save the "word" by plus the prefix of the node
     *            4. if the search String is still longer than the prefix, it means we can still
     *            dig deeper, since the search word is impossible equal to the prefix(by the note),
     *            we simply cut off the search String, leave the part after the index of prefix.length
     *            and just dig into the specific child of the node by call this method recursively.
     *            5. after we come back from the deeper place of the tree, check if the array is full now,
     *            if so, return the array.
     *            6. if the array still have place, and the "word" is truely a word, add it into the array,
     *            and make the index++;
     *            7. check if the array is full again
     *            8. if the array still have place, we can still dig deeper. This time, we would not go to
     *            a specific one but iterate all the child of the node. Everytime we come back from the child
     *            we check the size of array, if full, break out;
     *            9. finally return the array to the upper method.
     *            10.Since we put the "dig specific child" in front of others, we would firstly add the deepest
     *            words in the tree into the array.
     * @param word
     * @param search
     * @param numSuggestions
     * @param index
     * @param array
     * @param node
     * @return
     */
    // ---------- Private helper methods ---------------
    private String[] suggest(String word, String search, int numSuggestions, int index, String[] array, Node node){
        if(index == numSuggestions){ //the array is full;
            return array;
        }
        if(node == null){// this path has no other words witch can be added;
            return array;
        }
        word = word + node.prefix;
        if(search.length() > node.prefix.length()){
            search = search.substring(node.prefix.length());
            array = suggest(word, search, numSuggestions, index, array, node.children[getIndex(search.charAt(0))]);
            index = getArrayIndex(array);//get the first null index;
        }

        if(index == numSuggestions) return array; // always checking the array size;

        if(node.isWord){
            array[index] = word;
            index++;
        }
        if(index == numSuggestions) return array; // always checking the array size;
        for(Node child: node.children){
            array = suggest(word, "", numSuggestions, index, array, child);
            index = getArrayIndex(array);
            if(index == numSuggestions) break;
        }
        return array;
    }

    /**
     * helper method to get the array's first available index;
     * @param s
     * @return
     */
    private int getArrayIndex(String[] s){
        int i;
        for(i = 0; i < s.length; i++){
            if(s[i] == null) break;
        }
        return i;
    }

    /**
     *  A private add method that adds a given string to the tree
     * @param s the string to add
     * @param node the root of a tree where we want to add a new string

     * @return a reference to the root of the tree that contains s
     */
    private Node add(String s, Node node) {
        // FILL IN CODE
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

    /**
     * get the suffix of the longer String by cutting off the shorter one
     * @param sl
     * @param ss
     * @return
     */
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
