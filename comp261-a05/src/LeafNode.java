import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import javax.management.openmbean.InvalidOpenTypeException;

public class LeafNode<K extends Comparable<K>, V> implements Node<K, V> {

	 private List<K> keys;
	 private List<V> values;
	 private int size = 0;
	 private V next = null;
	 private Map<K, V> keyValues = new TreeMap<K, V>();

	public LeafNode() {
		 keys = new ArrayList<K>();
		 values = new ArrayList<V>();
	}
	
	@Override
	public void addChild(V v) {
		throw new InvalidOpenTypeException();
//		values.add(v);
	}

	@Override
	public void addKey(K k) {
		throw new InvalidOpenTypeException();
//		keys.add(k);
//		size++;
	}
	
//	public void addKV(K k , V v){
//		if (size == 0 ){ // if the node is empty
//			keys.add(k);
//			values.add(v);
//			size++;
//			System.out.println("EMPTY NODE ADDING FIRST VAL\n");
//			return;
//		} else {
//			for (int i = 0; i < size; i++){
//				int cmp = k.compareTo(keys.get(i));
//				if (cmp < 0) { // value is smaller than node -> insert one before
//					keys.add(size-1, k);
//					values.add(size-1, v);
//					size++;
//					System.out.println("ADDED KEY/VALUE at: "+i+"\n");
//					return;
//				}
//			}
//		}
//	}
	
	
//	public void moveValues(LeafNode<K,V> sibling, Integer mid, Integer end) {
//		List<String> keys = getKeys();
//		for(int i = end - 1; i >= mid; i--) {
//			String key = keys.get(i);
//			sibling.addKeyValue(key, keyValues.get(key));
//			keyValues.remove(key);
//			size = size - 1;
//		}
//	}
	
	
	public void addKV(K key, V value) {
		keyValues.put(key, value);
			size=size+1;
		}
	

	@Override
	public int size() {
		return size;
	}

	@Override
	public V getChild(int i) {
		return values.get(i);
	}

	@Override
	public K getKey(int i) {
		return keys.get(i);
	}

	@Override
	public void setNext(V c) {
		next = c;
	}

	@Override
	public void setSize(int s) {
		size = s;
	}

	@Override
	public V getNext() {
		return next;
	}

	@Override
	public void addChild(V c, int i) {
		values.set(i, c);
	}

	@Override
	public void addKey(K k, int i) {
		if (i >= size) {
			size++;
		}
		keys.set(i, k);
	}

	@Override
	public K removeKey(int i) {
		int j = 0;
		for (K key : keyValues.keySet()){
			j++;
			if (i == j){
				keyValues.remove(key);
				size--;
				return key;
			}
		}
		return null;
	}

	@Override
	public V removeChild(int i) {
		int j = 0;
		for (K key : keyValues.keySet()){
			j++;
			if (i == j){
				V tmp = keyValues.get(key);
				keyValues.remove(key);
				size--;
				return tmp;
			}
		}
		return null;
	}


}