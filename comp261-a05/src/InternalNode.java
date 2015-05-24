import java.util.ArrayList;
import java.util.List;

public class InternalNode implements Node {

	private List<Integer> keys;
	private List<Node> children;
	private int size;
	private Node next;

	public InternalNode() {
		keys = new ArrayList<Integer>();
		children = new ArrayList<Node>();
	}

//	@Override
	public void addChild(Node c) {
		children.add(c);
		size++;
	}

	@Override
	public void addKey(Integer k) {
		keys.add(k);
	}

//	@Override
	public void addKey(Integer k, int indx) {
		if (indx >= size) {
			size++;
		}
		keys.set(indx, k);
	}
	
	@Override
	public int size() {
		return size;
	}

//	@Override
	public Node getChild(int i) {
		return children.get(i);
	}

//	@Override
	public int getKey(int i) {
		if (keys.size() > 0){
			return (int) keys.get(i);
		}
		return 0;
	}

	@Override
	public void setSize(int s) {
		size = s;
	}

	@Override
	public Integer removeKey(int i) {
		return keys.remove(i);
	}

//	@Override
	public Node removeChild(int i) {
		size--;
		return children.remove(i);
	}

//	@Override
	public void setNext(Node n) {
		next = n;
	}

//	@Override
	public Node getNext() {
		return next;
	}

//	@Override
	public void addChild(Node c,int indx) {
		children.set(indx, c);
	}


}