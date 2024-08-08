package RBTree;

//import static org.junit.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class RedBlackTree {
	private static RedBlackTree.Node root;
	private static final int RED = 0;
	private static final int BLACK = 1;

	private static final Set<String> dictionary = new HashSet<>();

	public static class Node implements Comparable<RedBlackTree.Node> {
		String key;
		Node parent;
		Node leftChild;
		Node rightChild;
		boolean isRed;
		int color;

		public Node(String data) {
			this.key = data;
			this.leftChild = null;
			this.rightChild = null;
			this.isRed = true;
			this.color = RED;
			this.parent = null;
		}

		public int compareTo(RedBlackTree.Node n) {
			return key.compareTo(n.key);
		}

		public boolean isLeaf() {
			if (this.equals(root) && this.leftChild == null && this.rightChild == null) {
				return true;
			}
			if (this.equals(root)) {
				return false;
			}
			if (this.leftChild == null && this.rightChild == null) {
				return true;
			}
			return false;
		}

	}

	public boolean isLeaf(RedBlackTree.Node n) {
		if (n.equals(root) && n.leftChild == null && n.rightChild == null) {
			return true;
		}
		if (n.equals(root)) {
			return false;
		}
		if (n.leftChild == null && n.rightChild == null) {
			return true;
		}
		return false;

	}

	public interface Visitor {
		void visit(Node n);
	}

	public void visit(Node n) {
		System.out.println(n.key);
	}

	public void printTree() {
		RedBlackTree.Node n = root;
		printTree(n);
	}

	public void printTree(RedBlackTree.Node n) {
		System.out.print(n.key);
		if (n.isLeaf()) {
			return;
		}
		printTree(n.leftChild);
		printTree(n.rightChild);
	}

	public void insert(String data) {
		addNode(data);
	}

	public void addNode(String data) {
		Node toInsert = new Node(data);
		if (root == null) {
			this.root = toInsert;
		} else {
			Node p = root;
			while (p.leftChild != null || p.rightChild != null) {
				if (p.compareTo(toInsert) > 0 && p.leftChild != null) {
					p = p.leftChild;
				} else if (p.compareTo(toInsert) < 0 && p.rightChild != null) {
					p = p.rightChild;
				} else {
					break;
				}
			}
			if (p.compareTo(toInsert) > 0) {
				if (p.leftChild != null) {
					toInsert.leftChild = p.leftChild;
					p.leftChild = toInsert;
				} else {
					p.leftChild = toInsert;
				}
			} else if (p.compareTo(toInsert) < 0) {
				if (p.rightChild != null) {
					toInsert.rightChild = p.rightChild;
					p.rightChild = toInsert;
				} else {
					p.rightChild = toInsert;
				}
			}
			toInsert.parent = p;
		}
		fixTree(toInsert);
	}

	public RedBlackTree.Node lookup(String k) {
		if (this.root == null) {
			return null;
		}
		RedBlackTree.Node current = this.root;
		RedBlackTree.Node look = new RedBlackTree.Node(k);
		while (current != null) {
			if (look.compareTo(current) < 0) {
				current = current.leftChild;
			} else if (look.compareTo(current) > 0) {
				current = current.rightChild;
			} else if (current.key.equals(k)) {
				return current;
			}
		}
		return null;
	}

	public RedBlackTree.Node getSibling(RedBlackTree.Node n) {
		if (n == this.root) {
			return null;
		}
		if (isLeftChild(n.parent, n)) {
			return n.parent.rightChild;
		} else {
			return n.parent.leftChild;
		}
	}

	public RedBlackTree.Node getAunt(RedBlackTree.Node n) {
		RedBlackTree.Node grandparent = getGrandparent(n);
		if (grandparent == null) {
			return null;
		} else if (isLeftChild(grandparent, n.parent)) {
			return grandparent.rightChild;
		} else {
			return grandparent.leftChild;
		}
	}

	public RedBlackTree.Node getGrandparent(RedBlackTree.Node n) {
		if (n.parent == null) {
			return null;
		} else {
			return n.parent.parent;
		}
	}

	public void rotateLeft(RedBlackTree.Node n) {
		Node y = n.rightChild;
		n.rightChild = y.leftChild;
		if (y.leftChild != null) {
			y.leftChild.parent = n;
		}
		y.parent = n.parent;
		if (n.parent == null) {
			this.root = y;
		} else if (isLeftChild(n.parent, n)) {
			n.parent.leftChild = y;
		} else {
			n.parent.rightChild = y;
		}
		y.leftChild = n;
		n.parent = y;
	}

	public void rotateRight(RedBlackTree.Node n) {
		Node y = n.leftChild;
		n.leftChild = y.rightChild;
		if (y.rightChild != null) {
			y.rightChild.parent = n;
		}
		y.parent = n.parent;
		if (n.parent == null) {
			this.root = y;
		} else if (!isLeftChild(n.parent, n)) {
			n.parent.rightChild = y;
		} else {
			n.parent.leftChild = y;
		}
		y.rightChild = n;
		n.parent = y;
	}

	public boolean isLeftChild(RedBlackTree.Node parent, RedBlackTree.Node child) {
		if (child.compareTo(parent) < 0) {
			return true;
		}
		return false;
	}

	public void fixTree(RedBlackTree.Node current) {
		RedBlackTree.Node parent = current.parent;
		RedBlackTree.Node grandparent = getGrandparent(current);
		if (current.equals(root)) {
			current.color = BLACK;
			current.isRed = false;
		} else if (!current.parent.isRed) {

		} else if (current.isRed && parent.isRed) {
			if (getAunt(current) == null || !getAunt(current).isRed) {
				if (isLeftChild(grandparent, parent) && !isLeftChild(parent, current)) {
					rotateLeft(parent);
					fixTree(parent);

				} else if (!isLeftChild(grandparent, parent) && isLeftChild(parent, current)) {
					rotateRight(parent);
					fixTree(parent);
				} else if (isLeftChild(grandparent, parent) && isLeftChild(parent, current)) {
					parent.color = BLACK;
					parent.isRed = false;
					grandparent.color = RED;
					grandparent.isRed = true;
					rotateRight(grandparent);
				} else if (!isLeftChild(grandparent, parent) && !isLeftChild(parent, current)) {
					parent.color = BLACK;
					parent.isRed = false;
					grandparent.color = RED;
					grandparent.isRed = true;
					rotateLeft(grandparent);
				}
			} else if (getAunt(current).isRed) {
				parent.color = BLACK;
				parent.isRed = false;
				getAunt(current).color = BLACK;
				getAunt(current).isRed = false;
				grandparent.color = RED;
				grandparent.isRed = true;
				fixTree(grandparent);
			}
		}

	}

	public boolean isEmpty(RedBlackTree.Node n) {
		if (n.key == null) {
			return true;
		}
		return false;
	}

	public void preOrderVisit(Visitor v) {
		preOrderVisit(root, v);
	}

	private static void preOrderVisit(RedBlackTree.Node n, Visitor v) {
		if (n == null) {
			return;
		}
		v.visit(n);
		;
		preOrderVisit(n.leftChild, v);
		preOrderVisit(n.rightChild, v);
	}

	public String suggest(String s) {
		String[] words = s.split(" ");
		String n = "";
		for (String word : words) {
			if (lookup(word) == null) {
				n += word + " ";
			}
		}
		return n;
	}

	private static String spellCheck(String s) {
		if (dictionary.contains(s.toLowerCase())) {
			return s;
		}
		String suggestion = getClosestWord(s);
		return suggestion;
	}

	static int compute_Levenshtein_distance(String str1, String str2) {
		if (str1.isEmpty()) {
			return str2.length();
		}
		if (str2.isEmpty()) {
			return str1.length();
		}
		int replace = compute_Levenshtein_distance(str1.substring(1), str2.substring(1))
				+ NumOfReplacement(str1.charAt(0), str2.charAt(0));

		int insert = compute_Levenshtein_distance(str1, str2.substring(1)) + 1;
		int delete = compute_Levenshtein_distance(str1.substring(1), str2) + 1;
		return minm_edits(replace, insert, delete);
	}

	static int NumOfReplacement(char c1, char c2) {
		return c1 == c2 ? 0 : 1;
	}

	static int minm_edits(int... nums) {
		return Arrays.stream(nums).min().orElse(Integer.MAX_VALUE);
	}

	private static String getClosestWord(String s) {
		String closest = null;
		int minDist = Integer.MAX_VALUE;

		for (String word : dictionary) {
			int dist = compute_Levenshtein_distance(s.toLowerCase(), word);
			if (dist < minDist) {
				minDist = dist;
				closest = word;
			}
		}
		return closest;
	}

	public static void main(String[] args) throws Exception {
		RedBlackTree rbt = new RedBlackTree();
		Path path = Paths.get("./src/dictionary3.txt");

		// making rbt
		Scanner in = new Scanner(path);
		while (in.hasNextLine()) {
			String s = in.nextLine();
			rbt.insert(s);
		}

		// making dictionary hashset
		try (BufferedReader br = new BufferedReader(new FileReader("./src/dictionary3.txt"))) {
			String line;
			while ((line = br.readLine()) != null) {
				dictionary.add(line.trim());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		//Properties props = new Properties();
		//props.setProperty("annotators", "tokenize, ssplit");
		//StanfordCoreNLP pipeline = new StanfordCoreNLP(props);
		//String text = rbt.suggest("hlep talk cat jab devour");
		//Annotation document = new Annotation(text);
		//pipeline.annotate(document);
		//for (CoreMap sentence : document.get(CoreAnnotations.SentencesAnnotation.class)) {
		//	for (CoreLabel token : sentence.get(CoreAnnotations.TokensAnnotation.class)) {
		//		String word = token.word();
		//		String correctedWord = spellCheck(word);
		//		System.out.println("Original: " + word + " | Corrected: " + correctedWord);
		//	}

		//}
		String[] words = rbt.suggest("hlep talk cat jab devour").split(" ");
		for(String word: words)
		{
			System.out.println("Original: " + word + " | Corrected: " + spellCheck(word));
		}
	}
}
