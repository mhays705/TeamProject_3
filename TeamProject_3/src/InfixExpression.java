
import java.util.Stack;

public class InfixExpression {
	private Stack<String> operators; // Stores infix expression as stack
	private Stack<BTNode<String>> expTree; // Stores operand nodes for expression tree

	// Parse string for infix expression and convert it to expression tree
	public InfixExpression(String line) {
		operators = new Stack<>();
		expTree = new Stack<>();
		StringBuilder builder = new StringBuilder();
		char[] exp = line.toCharArray(); // Change input string into character array to tokenize characters
		System.out.println("Expression read from input: " + line);

		for (int i = 0; i < exp.length; i++) { // Loop through characters in expression
			char ch = exp[i];

			if (Character.isWhitespace(ch)) {  // Skip over spaces
				continue;
			}

			if (ch == '(') {   // If '(' add it to the operators stack
				operators.push(Character.toString(ch));
			} else if (Character.isDigit(ch)) {  // Check if character is digit
				builder.append(ch);
				if (((i + 1) < exp.length) && Character.isDigit(exp[i + 1])) {  // Check for second digit, if so append it to builder then create a node and add it to the node stack
					builder.append(exp[i + 1]);
					expTree.push(new BTNode(builder.toString()));
					builder.setLength(0); // Reset builder
					i++; // Advance to not reread second digit
				} else {
					expTree.push(new BTNode(builder.toString())); // If single digit create node and push to stack and reset builder
					builder.setLength(0);
				}
			}

			else if (ch == '+' || ch == '-' || ch == '/' || ch == '*' || ch == '%' || ch == '>' || ch == '<' // Check for single character operator and append to builder
					|| ch == '!' || ch == '&' || ch == '|' || ch == '^' || ch == '=') {

				builder.append(ch);

				if (((i + 1) < exp.length) && exp[i + 1] == '=' || ch == '&' || ch == '|') {  // Check if multi-character operator and append it to builder and push to operator stack
					builder.append(exp[i + 1]);
					operators.push(builder.toString());
					builder.setLength(0); // Reset builder	
					i++; // Advance to nor reread character
				} else {
					operators.push(builder.toString()); // If single character operator push on to operator stack and reset builder
					builder.setLength(0);
				}

			} else if (ch == ')') { // If character is closing parenthesis start creating expression tree until opening parenthesis is found
				while (!operators.isEmpty() && !operators.peek().equals("(")) {
					createTree(operators, expTree);
				}
				operators.pop();    // Pop the the opening parenthesis
			}
		}

		while (!operators.isEmpty()) {
			createTree(operators, expTree);
		}

		System.out.println("Expression after evaluation: " + evaluate(expTree.pop())); // Output evaluation of expression
	}
	
	


	/**
	 * Creates expression tree by combining operators from stack and operands from
	 * expTree stack
	 * 
	 * @param operators: stack of operators
	 * @param expTree:   stack of nodes containing operands
	 */
	private void createTree(Stack<String> operators, Stack<BTNode<String>> expTree) {
		BTNode root = new BTNode(operators.pop());

		root.right = expTree.pop(); // Top node on expTree is right child
		root.left = expTree.pop();  // Second to top node is left child

		expTree.push(root);

	}
	
	
	/**
	 * Evaluates expression tree recursively
	 * @param root: root node of expression tree
	 * @return: result of evaluation of expression
	 */
	private int evaluate(BTNode<String> root) {
		
		if (root == null) {
			return 0;
		}
		
		if (root.left == null && root.right == null) { // If node is a leaf node it is an operand
			return Integer.parseInt(root.data);
		}
		
		int left = evaluate(root.left);
		int right = evaluate(root.right);
		
		switch (root.data) {
		case "+":
			return left + right;
		case "-":
			return left - right ;
		case "*":
			return left * right;
		case "/":
			if (right == 0) {
				throw new ArithmeticException("Dividing by zero");
			}
			return left / right;
		case "%":
			if (right == 0) {
				throw new ArithmeticException("Dividing by zero");
			}
			return left % right;
		case "^":
			return ((int) Math.pow(left, right));
			
		case ">":
			return(left > right ? 1 : 0);
			
		case ">=":
			return(left >= right ? 1 : 0);
			
		case "<":
			return(left < right ? 1 : 0);
			
		case "<=":
			return(left <= right ? 1 : 0);
			
		case "==":
			return(left == right ? 1 : 0);
			
		case "!=":
			return(left != right ? 1 : 0);
			
		case "&&":
			return((left != 0 && right != 0) ? 1 : 0);
			
		case "||":
			return((left != 0 || right != 0) ? 1 : 0);
			
		default:
			throw new UnsupportedOperationException("Operator not supported: " + root.data);
		}
		
		
	}
	
}
