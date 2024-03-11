/**
 * Binary tree node
 */
public class BTNode<T> {
	// Data fields
	public T data; // Stores some data in the node
	public BTNode<T> left; // Stores a reference to the left child node
	public BTNode<T> right; // Stores a reference to the right child node


	public BTNode(T data) { this.data = data; }

	public BTNode(T data, BTNode<T> left, BTNode<T> right) {
		this.data = data;
		this.left = left;
		this.right = right;
	}

}
