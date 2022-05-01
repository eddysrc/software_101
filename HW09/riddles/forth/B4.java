package il.ac.tau.cs.sw1.ex9.riddles.forth;


import java.util.ArrayList;
import java.util.Iterator;

public class B4 implements Iterator<String> {

    private ArrayList<String> iter = new ArrayList<>();
    private int index = 0;

    public B4(String[] args, int k) {
        for (int i = 0; i < k; i++) {
            for (String s: args){
                iter.add(s);
            }
        }
    }

    @Override
    public boolean hasNext() {
        if (index == iter.size()) return false;
        return true;
    }

    @Override
    public String next() {
        return iter.get(index++);
    }
}
