
public class Container<K,C> {

	private K key;
	private C node;

	public Container(K key, C node) {
		this.key = key;
		this.node = node;
	}

	public C getValue() {
		return node;
	}

	public K getKey() {
		return key;
	}

}
