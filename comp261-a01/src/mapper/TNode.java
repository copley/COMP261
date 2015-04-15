package mapper;
import java.util.*;

/**
*
* Class to store the N-ary Trie Node. This node contains a list of roads that belong to this node and a link to its children
* @author Diego Trazzi
* 
*/

public class TNode {

	private Map<Character,TNode> children = new HashMap<Character, TNode>();
	private List<Road> roads = new ArrayList<Road>();
	
	public TNode(){
	}
	
	public void printChildren(){
		System.out.println("TNode ############");
		for (Character c : children.keySet()) {
			System.out.println(c);
		}
		System.out.println("END: ############");
	}
	
	/**
	 * Add a new node to his children list, takes a character and a parent node
	 * @param character : key holding the letter
	 * @param node : top node
	 * @return TNode
	 */
	public TNode addNode(Character character, TNode node){
		TNode newNode = new TNode();
		children.put(character, newNode);
		return newNode;
		}
	
	/**
	 * Loops though each character of the string name of the road and adds nodes, if necessary, to the tree
	 * @param road
	 */
	public void addRoad(Road road){
		TNode node = this;
		//System.out.print(road.getName());
		for (Character c : road.getName().toCharArray()){
			if (node.children.containsKey(c)){
				node = node.children.get(c);
			} else {
				node = node.addNode(c, node);
			}
		}
		node.roads.add(road);
	}
	
	/**
	 * Check if children contain the characters of the string name
	 * @param name : Street name to check
	 * @return The node containing the street name or null
	 */
	public TNode diveToNode(String name){
		TNode node = this;
		for (Character c : name.toCharArray()){
			if (node.children.containsKey(c)){
				node = node.children.get(c);
			} else {
				return null;
			}
		}
		return node;
	}
	
	/**
	 * Get a list of all roads from the current node to the descending branches
	 * @return List<Road>
	 */
	public List<Road> getRoads(){
		List<Road> subRoads = this.roads;
		for (TNode child : children.values()){
			roads.addAll(child.getRoads());
		}
		return subRoads;
	}
}