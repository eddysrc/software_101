package il.ac.tau.cs.sw1.ex8.histogram;

import java.util.*;

/**************************************
 *  Add your code to this class !!!   *
 **************************************/
public class HashMapHistogram<T extends Comparable<T>> implements IHistogram<T>{

	private HashMap<T, Integer> map;

	public HashMapHistogram() {
		this.map = new HashMap<>();
	}

	@Override
	public Iterator<T> iterator() {
		return new HashMapHistogramIterator<>(this.map);
	}

	@Override
	public void addItem(T item) {
		if (!this.map.containsKey(item)) this.map.put(item, 1);
		else {
			int new_val = this.map.get(item);
			new_val++;
			this.map.put(item, new_val);
		}
	}

	@Override
	public void removeItem(T item) throws IllegalItemException {
		if (!this.map.containsKey(item)) throw new IllegalItemException();
		int new_val = this.map.get(item);
		new_val--;
		if (new_val == 0) this.map.remove(item);
		else this.map.put(item, new_val);
	}

	@Override
	public void addItemKTimes(T item, int k) throws IllegalKValueException {
		if (k < 1) throw new IllegalKValueException(k);
		this.map.put(item, this.getCountForItem(item) + k);
	}

	@Override
	public void removeItemKTimes(T item, int k) throws IllegalItemException, IllegalKValueException {
		if (!this.map.containsKey(item)) throw new IllegalItemException();
		if (k < 1 || k > this.getCountForItem(item)) throw new IllegalKValueException(k);
		this.map.put(item, this.getCountForItem(item) - k);
		if (this.map.get(item) == 0) this.map.remove(item);
	}

	@Override
	public int getCountForItem(T item) {
		if (this.map.containsKey(item)) return this.map.get(item);
		return 0;
	}

	@Override
	public void addAll(Collection<T> items) {
		for (T item: items){
			this.addItem(item);
		}
	}

	@Override
	public void clear() {
		this.map = new HashMap<>();
	}

	@Override
	public Set<T> getItemsSet() {
		return this.map.keySet();
	}

	@Override
	public void update(IHistogram<T> anotherHistogram) {
		int value;
		for (T key: anotherHistogram.getItemsSet()){
			value = anotherHistogram.getCountForItem(key);
			try { // Should never fail though...
				this.addItemKTimes(key, value);
			}
			catch (IllegalKValueException e) {
				e.printStackTrace();
			}
		}
	}

	
}
