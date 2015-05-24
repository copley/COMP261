import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LeafNode<V> implements Node {

	private List<Integer> keys;
	private List<V> values;
	private int size;
	private Node next;

	public LeafNode() {
		keys = new ArrayList<Integer>();
		values = new ArrayList<V>();
	}

	// @Override
	public void addValue(V v) {
		values.add(v);
	}

	@Override
	public void addKey(Integer k) {
		// add value in correct place .....
		keys.add(k);
		Collections.sort(keys);
		size++;
	}

	@Override
	public int size() {
		return size;
	}

	// @Override
	public V getValue(int i) {
		return values.get(i);
	}

	// @Override
	public int getKey(int i) {
		return keys.get(i);
	}

	@Override
	public void setSize(int s) {
		size = s;
	}

	@Override
	public Integer removeKey(int i) {
		size--;
		return keys.remove(i);
	}

	// @Override
	public V removeValue(int i) {
		return values.remove(i);
	}

	// @Override
	public void setNext(Node n) {
		next = n;
	}

	// @Override
	public Node getNext() {
		return next;
	}

	// @Override
	public void addValue(V c, int i) {
		values.set(i, c);
	}

	// @Override
	// public void addKey(Integer k, int i) {
	// if(i>= size){
	// size++;
	// }
	// keys.set(i, k);
	// }

}