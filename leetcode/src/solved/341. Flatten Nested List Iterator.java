/**
 * https://leetcode.com/problems/flatten-nested-list-iterator/
 */

/**
 * // This is the interface that allows for creating nested lists.
 * // You should not implement it, or speculate about its implementation
 * public interface NestedInteger {
 *
 *     // @return true if this NestedInteger holds a single integer, rather than a nested list.
 *     public boolean isInteger();
 *
 *     // @return the single integer that this NestedInteger holds, if it holds a single integer
 *     // Return null if this NestedInteger holds a nested list
 *     public Integer getInteger();
 *
 *     // @return the nested list that this NestedInteger holds, if it holds a nested list
 *     // Return null if this NestedInteger holds a single integer
 *     public List<NestedInteger> getList();
 * }
 */
//answer
public class NestedIterator implements Iterator<Integer> {

	private List<NestedInteger> nestedList;
	private int index;
	private NestedIterator iterator;

    public NestedIterator(List<NestedInteger> nestedList) {
		this.nestedList = nestedList;
		this.index = 0;
		this.iterator = null;
    }

    @Override
    public Integer next() {
		if (iterator != null) {
			Integer next = iterator.next();
			if (next != null) {
				return next;
			}
		}
		iterator = null;
		while(index < nestedList.size()) {
			NestedInteger current = nestedList.get(index++);
			if (current.isInteger()) {
				return current.getInteger();
			} else {
				iterator = new NestedIterator(current.getList());
				Integer next = iterator.next();
				if (next != null) {
					return next;
				}
				iterator = null;
			}
		}
		return null;
	}

    @Override
    public boolean hasNext() {
		if (iterator != null && iterator.hasNext()) {
			return true;
		}

		int i = index;
		while (i < nestedList.size()) {
			NestedInteger current = nestedList.get(i++);
			if (current.isInteger()) {
				return true;
			} else {
				NestedIterator iterator = new NestedIterator(current.getList());
				if (iterator.hasNext()) {
					return true;
				}
			}
		}
		return false;
    }
}

/**
 * Your NestedIterator object will be instantiated and called as such:
 * NestedIterator i = new NestedIterator(nestedList);
 * while (i.hasNext()) v[f()] = i.next();
 */


//a little more simple
public class NestedIterator implements Iterator<Integer> {

	private Iterator<Integer> iterator;

    public NestedIterator(List<NestedInteger> nestedList) {
		List<Integer> list = flatten(nestedList);
		this.iterator = list.iterator();
    }

	private List<Integer> flatten(List<NestedInteger> nestedList) {
		List<Integer> result = new ArrayList<>();
		for (NestedInteger nested : nestedList) {
			if (nested.isInteger()) {
				result.add(nested.getInteger());
			} else {
				result.addAll(flatten(nested.getList()));
			}
		}
		return result;
	}

    @Override
    public Integer next() {
        return iterator.next();
    }

    @Override
    public boolean hasNext() {
		return iterator.hasNext();        
    }

}
