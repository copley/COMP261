import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

public class LeafNode<K, C> implements Node<K, C> {

	private List<K> keys;
	private List<C> children;
	TreeMap<K, C> map;
	private int size;
	private C next;

	public LeafNode() {
//		map = new TreeMap<K, C>();
		keys = new ArrayList<K>();
//		keys.add(null);
		children = new ArrayList<C>();

	}

	 @Override
	 public void addChild(C c) {
	 children.add(c);
	 }

	 @Override
	 public void addKey(K k) {
	 keys.add(k);
	 size++;
	 }

	@Override
	public int size() {
		return size;
	}

	@Override
	public C getChild(int i) {
		return children.get(i);
	}

	@Override
	public K getKey(int i) {
		return keys.get(i);
	}

	@Override
	public void setNext(C c) {
		next = c;
	}

	@Override
	public void setSize(int s) {
		size = s;
	}

	@Override
	public C getNext() {
		return next;
	}

	@Override
	public void addChild(C c, int i) {
		children.set(i, c);
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
	 size--;
	 return keys.remove(i);
	 }

	 @Override
	 public C removeChild(int i) {
	 return children.remove(i);
	 }

}