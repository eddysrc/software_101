package il.ac.tau.cs.sw1.ex7;
import java.util.*;

public class FractionalKnapSack implements Greedy<FractionalKnapSack.Item>{
    int capacity;
    List<Item> lst;

    FractionalKnapSack(int c, List<Item> lst1){
        capacity = c;
        lst = lst1;
    }

    public static class Item {
        double weight, value;
        Item(double w, double v) {
            weight = w;
            value = v;
        }

        @Override
        public String toString() {
            return "{" + "weight=" + weight + ", value=" + value + '}';
        }
    }

    //Sub functions for Iterator:

    private double calcValue(Item item){
        return item.value/item.weight;
    }

    private Item findNext(List<Item> item_list){
        Item current_max = null;
        double max_value = 0.;
        for (Item current: item_list){
            if (calcValue(current) > max_value){
                max_value = calcValue(current);
                current_max = current;
            }
        }
        item_list.remove(current_max);
        return current_max;
    }

    @Override
    public Iterator<Item> selection() {
        List<Item> items_copy = new ArrayList<Item>();
        items_copy.addAll(this.lst);
        return new Iterator<Item>() {
            @Override
            public boolean hasNext() {
                return !items_copy.isEmpty();
            }

            @Override
            public Item next() {
                return findNext(items_copy);
            }
        };
    }

    // Sub functions for feasibility:
    @Override
    public boolean feasibility(List<Item> candidates_lst, Item element) {
        int cur_weight = 0;
        for (Item item: candidates_lst){
            cur_weight += item.weight;
        }
        return (this.capacity - cur_weight > 0);
    }

    @Override
    public void assign(List<Item> candidates_lst, Item element) {
        int cur_weight = 0;
        for (Item item: candidates_lst){
            cur_weight += item.weight;
        }
        if (this.capacity - cur_weight >= element.weight) candidates_lst.add(element);
        else{
            int left_weight = this.capacity - cur_weight;
            Item partial_element = new Item(left_weight, element.value);
            candidates_lst.add(partial_element);
        }
    }

    @Override
    public boolean solution(List<Item> candidates_lst) {
        int cur_weight = 0;
        for (Item item: candidates_lst){
            cur_weight += item.weight;
        }
        if (this.capacity - cur_weight == 0) return true;
        if (candidates_lst.size() == this.lst.size()) return true;
        return false;
    }
}
