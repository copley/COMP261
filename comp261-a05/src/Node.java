public interface Node<K,C> {

	public void addChild(C c);

	public void addKey(K k);

	public void addChild(C c, int i);

	public void addKey(K k, int i);

	public C getChild(int i);

	public K getKey(int i);

	public int size();

	public void setSize(int s);

	public K removeKey(int i);

	public C removeChild(int i);

	public void setNext(C n);

	public C getNext();
}