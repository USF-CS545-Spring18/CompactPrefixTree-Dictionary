# project2-YaleiShi
project2-YaleiShi created by GitHub Classroom

The suggest method:

Firstly check if the word is in the tree, if so, just return an array only having the word;
if not, call the helper method;
Helper method:
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
     * @param word the prefix we stored along the path to here
     * @param search the left String of the original search word
     * @param numSuggestions the max number of suggestions
     * @param index the index of first available place in the array
     * @param array the array we are to return
     * @param node the node we are to operate
     * @return an array we have added the words
