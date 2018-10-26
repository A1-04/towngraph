package domain;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class State {

	private Node actualNode;
	private LinkedList<Node> n_list;
	private String md5;

	public State(Node actualNode, LinkedList<Node> n_list) {
		super();
		this.actualNode = actualNode;
		this.n_list = sortNodes(n_list);
		this.md5 = getMD5(actualNode, n_list);
	}

	public State() {

	}

	public Node getActualNode() {
		return actualNode;
	}

	public String getMD5() {
		return md5;
	}

	public void setMD5(String md5) {
		this.md5 = md5;
	}

	public static String getMD5(Node n1, LinkedList<Node> nList) {

		String input = n1.getID() + ",[";
		for (Node i : nList) {
			input += i.getID() + ",";
		}
		input = input.substring(0, input.length() - 1);
		input += "]";

		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] messageDigest = md.digest(input.getBytes());
			BigInteger number = new BigInteger(1, messageDigest);
			String hashtext = number.toString(16);

			while (hashtext.length() < 32) {
				hashtext = "0" + hashtext;
			}
			return hashtext;
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
	}

	public LinkedList<Node> getN_list() {
		return n_list;
	}

	public LinkedList<Node> sortNodes(LinkedList<Node> nl) {

		Collections.sort(nl, new Comparator<Node>() {
			@Override
			public int compare(Node n1, Node n2) {
				if (Integer.parseInt(n1.getID().substring(1, n1.getID().length() - 1)) > Integer
						.parseInt(n2.getID().substring(1, n2.getID().length() - 1)))
					return 1;
				if (Integer.parseInt(n1.getID().substring(1, n1.getID().length() - 1)) < Integer
						.parseInt(n2.getID().substring(1, n2.getID().length() - 1)))
					return -1;
				return 0;
			}
		});

		return nl;
	}

}
