package swen221.list;

import java.util.ArrayList;
import java.util.List;

import javax.swing.plaf.SliderUI;

/**
 * @author trazzidieg
 *
 */
public class SumList implements IntList {

	List<Integer> sList = new ArrayList<Integer>();
	Integer cSum = 0;

	public SumList(){
	}

	@Override
	public void add(int x) {
		sList.add(x);
		cSum += x;
	}

	@Override
	public int remove(int index) {
		int num = sList.get(index);
		sList.remove(index);
		cSum -= num;
		return num;
	}

	@Override
	public int get(int index) {
		return sList.get(index);
	}

	@Override
	public int set(int index, int value) {
		int x = sList.set(index, value);
		cSum -= x;
		cSum += value;
		return x;
	}

	@Override
	public void clear() {
		sList.clear();
		cSum = 0;
	}

	@Override
	public int size() {
		return sList.size();
	}

	public int sum(){
		return cSum;
	}

}
