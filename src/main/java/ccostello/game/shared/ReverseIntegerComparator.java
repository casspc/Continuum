package ccostello.game.shared;

import java.util.Comparator;

public class ReverseIntegerComparator implements Comparator<Integer> {

	public int compare(Integer i1, Integer i2) {
		return -1 * i1.compareTo( i2 );
	}

}
