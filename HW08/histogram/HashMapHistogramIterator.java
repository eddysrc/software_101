package il.ac.tau.cs.sw1.ex8.histogram;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;


/**************************************
 *  Add your code to this class !!!   *
 **************************************/

public class HashMapHistogramIterator<T extends Comparable<T>> implements Iterator<T>{

	private HashMap<T, Integer> map_copy;
	private ArrayList<T> word_list = new ArrayList<>();
	private int iter = 0;
	private int size;

	public HashMapHistogramIterator(HashMap<T , Integer> origin_map){
		this.map_copy = (HashMap<T, Integer>) origin_map.clone();
		for (T word: this.map_copy.keySet()){
			this.word_list.add(word);
		}

		HashMapHistogramComparator comp = new HashMapHistogramComparator(this.map_copy);
		this.word_list.sort(comp);
		this.size = word_list.size();
	}

	public static class HashMapHistogramComparator<T extends Comparable<T>> implements Comparator<T>{

		private HashMap<T, Integer> map;

		public HashMapHistogramComparator(HashMap<T, Integer> map){
			this.map = map;
		}

		@Override
		public int compare(T object1, T object2){
			if (this.map.get(object1) > this.map.get(object2)) return -1;
			if (this.map.get(object1) < this.map.get(object2)) return 1;

			return object1.compareTo(object2);
		}
	}

	@Override
	public boolean hasNext() {
		return this.iter < this.size;
	}

	@Override
	public T next() {
		this.iter += 1;
		return this.word_list.get(this.iter - 1);
	}

	@Override
	public void remove() {
		throw new UnsupportedOperationException(); //no need to change this
	}
}



/*public class HashMapHistogramIterator<T extends Comparable<T>> implements Iterator<T>{ // My first solution without the comparator.
	
	private HashMap<T, Integer> map_copy;

	public HashMapHistogramIterator(HashMap<T , Integer> origin_map){
		this.map_copy = (HashMap<T, Integer>) origin_map.clone();
	}
	
	@Override
	public boolean hasNext() {
		return this.map_copy.size() > 0;
	}

	private T getSmallerT(T first, T second){
		if (first.compareTo(second) < 0) return first;
		return second;
	}

	private  T findNext(HashMap<T, Integer> map){
		int max_count = 0;
		int current_count = 0;
		T best_value = null;
		for (T key: map.keySet()){
			if (best_value == null) best_value = key;
			current_count = map.get(key);
			if (current_count >= max_count){
				if (current_count == max_count){
					best_value = getSmallerT(best_value, key);
				}
				else best_value = key;
				max_count = current_count;
			}
		}
		return best_value;
	}

	@Override
	public T next() {
		T rtrn = findNext(this.map_copy);
		this.map_copy.remove(rtrn);
		return rtrn;
	}

	@Override
	public void remove() {
		throw new UnsupportedOperationException(); //no need to change this
	}
}*/
