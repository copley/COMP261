import java.util.List;

public interface Node<K, C> {

	/**
	 * Adds a childe/value to the end of the node
	 * 
	 * @param c
	 */
	public void addChild(C c);

	/**
	 * Adds a key to the end of the node
	 * 
	 * @param k
	 */
	public void addKey(K k);

	/**
	 * Adds a child at specified index
	 * 
	 * @param c
	 * @param i
	 */
	public void addChild(C c, int i);

	/**
	 * Adds a key at specified index
	 * 
	 * @param k
	 * @param i
	 */
	public void addKey(K k, int i);

	/**
	 * Retrives the child/value at specified index i
	 * 
	 * @param i
	 * @return
	 */
	public C getChild(int i);

	/**
	 * Retruve the key form the array at the specified index i
	 * 
	 * @param i
	 * @return returns the key
	 */
	public K getKey(int i);

	/**
	 * Retrives the current size of the node
	 * 
	 * @return
	 */
	public int size();

	/**
	 * Sets the size of the node (different form maxsize)
	 * 
	 * @param s
	 */
	public void setSize(int s);

	public K removeKey(int i);

	public C removeChild(int i);

	public void setNext(C c);

	public C getNext();
	
	public void addKV(K k, C c);
	
}