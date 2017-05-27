package cq.game.bots;

/**
 * Created by Fredi Šarić on 27.05.17..
 */
public class Tuple<L, R> {

	private final L left;
	private final R right;

	public Tuple(L left, R right) {
		this.left = left;
		this.right = right;
	}

	public L getLeft() {
		return left;
	}

	public R getRight() {
		return right;
	}
}
