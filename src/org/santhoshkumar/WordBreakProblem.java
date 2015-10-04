package org.santhoshkumar;

import java.util.HashSet;

public class WordBreakProblem {

    public boolean breakWordDPTopDown(String s,
                               HashSet<String> dict,
                               HashSet<String> memory,
                               String answer) {

        if (s.length() == 0) {
            System.out.println(answer);
            return true;
        } else if (memory.contains(s)) {
            return false;
        } else {

            int index = 0;
            String word = "";
            while (index < s.length()) {
                word += s.charAt(index);// add one char at a time
                // check if word already being solved
                System.out.println(word);
                if (dict.contains(word)) {
                    if (breakWordDPTopDown(s.substring(index + 1), dict, memory,
                            answer + word + " ")) {
                        return true;
                    } else {
                        //System.out.println("backtrack: "+answer);
                        index++;
                    }
                } else {
                    index++;
                }
            }
            memory.add(s);// memorization for future;
            return false;
        }
    }

    public String breakWordDPBottomUp(String word, HashSet<String> dict){
        int T[][] = new int[word.length()][word.length()];

        for(int i=0; i < T.length; i++){
            for(int j=0; j < T[i].length ; j++){
                T[i][j] = -1; //-1 indicates string between i to j cannot be split
            }
        }

        //fill up the matrix in bottom up manner
        for(int l = 1; l <= word.length(); l++){
            for(int i=0; i < word.length() -l + 1 ; i++){
                int j = i + l-1;
                String s = word.substring(i,j+1);
                //if string between i to j is in dictionary T[i][j] is true
                System.out.println(s);
                if(dict.contains(s)){
                    T[i][j] = i;
                    continue;
                }
                //find a k between i+1 to j such that T[i][k-1] && T[k][j] are both true
                for(int k=i+1; k <= j; k++){
                    if(T[i][k-1] != -1 && T[k][j] != -1){
                        T[i][j] = k;
                        break;
                    }
                }
            }
        }
        if(T[0][word.length()-1] == -1){
            return null;
        }

        //create space separate word from string is possible
        StringBuffer buffer = new StringBuffer();
        int i = 0; int j = word.length() -1;
        while(i < j){
            int k = T[i][j];
            if(i == k){
                buffer.append(word.substring(i, j+1));
                break;
            }
            buffer.append(word.substring(i,k) + " ");
            i = k;
        }

        /**
        for(int in=0; in < T.length; in++){
            for(int jn=0; jn < T[in].length ; jn++){
                System.out.print(T[in][jn] + " ");
            }
            System.out.println();
        }
        //*/

        return buffer.toString();
    }

    public static void main(String[] args) {
        HashSet<String> dictionary = new HashSet<String>();
        dictionary.add("this");
        dictionary.add("is");
        dictionary.add("the");
        dictionary.add("word");
        dictionary.add("break");
        dictionary.add("problem");
        //String givenString = "thisistheproblem";
        String givenString = "thisistheproblem";

        WordBreakProblem ws = new WordBreakProblem();

        // create another HashSet so store the sub problems result
        HashSet<String> memorization = new HashSet<String>();

        ws.breakWordDPTopDown(givenString, dictionary, memorization, "");

        String result = ws.breakWordDPBottomUp(givenString, dictionary);
        System.out.print(result);

    }

}