package swen221.list;

/**
 * Generic interface to set and get a integers into a list
 * @author Diego Trazzi
 * @version 0.1
 */
public interface IntList {

	/**
	 * Add an integer to the list
	 * @param x Integer to be added
	 */
	public void add(int x);

	/**
	 * Remove an integer from the list at given index
	 * @param Index : Index of the integer to be removed
	 * @return Integer removed
	 */
	public int remove(int index);

	/**
	 * Get an integer from the list at given index
	 * @param index : Index of the integer to be queried in the list
	 * @return Integer pulled form the list
	 */
	public int get(int index);

	/**
	 * Set and integer at specific index in the list. It will overwirete any previous value
	 * @param index : Index of the integer to be queried in the list
	 * @param value : Value of the integer to be added
	 * @return the element previously at the specified position
	 */
	public int set(int index, int value);

	/**
	 * Clear the list
	 */
	public void clear();

	/**
	 * Gets the size of the list
	 * @return Integer of the size of the list
	 */
	public int size();
}

