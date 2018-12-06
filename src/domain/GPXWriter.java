package domain;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;

public class GPXWriter {
	@SuppressWarnings("unused")
	private static final String TAG = GPXWriter.class.getName();

	public static int writePath(LinkedList<TreeNode> sol, TreeNode first, String strategy, int n_generated, int depth,
			float f) {
		File file = new File("output.gpx");
		String header = "<gpx version=\"1.1\" creator=\"The Special Flat\">\n";

		String desc = "\n<desc>\n" + "Strategy: " + strategy + "\n" + "Nodes generated: " + n_generated + "\n"
				+ "Depth: " + depth + "\n" + "Cost: " + f + "\n</desc>\n";

		if ((writeWPT(first, first.getCurrentState().getN_list(), header, desc, file) == -1)
				|| (writeTRKPT(sol, file) == -1)) {
			return -1;
		}

		return 0;
	}

	public static int writeWPT(TreeNode first, LinkedList<Node> start, String header, String desc, File file) {
		String segments = "\n<wpt lat=\"" + first.getCurrentState().getActualNode().getY() + "\" lon=\""
				+ first.getCurrentState().getActualNode().getX() + "\"><name>" + "[I]"
				+ first.getCurrentState().getActualNode().getID() + "</name></wpt>\n";
		for (Node l : start) {
			segments += "<wpt lat=\"" + l.getY() + "\" lon=\"" + l.getX() + "\"><name>" + "[V]" + l.getID()
					+ "</name></wpt>\n";
		}

		try {
			FileWriter writer = new FileWriter(file, false);
			writer.append(header);
			writer.append(desc);
			writer.append(segments);
			writer.flush();
			writer.close();
		} catch (IOException e) {
			return -1;
		}
		return 0;
	}

	public static int writeTRKPT(LinkedList<TreeNode> points, File file) {
		String segments = "\n<trk>\n";
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
		for (int i = points.size() - 1; i >= 0; i--) {
			segments += "<trkpt lat=\"" + points.get(i).getCurrentState().getActualNode().getY() + "\" lon=\""
					+ points.get(i).getCurrentState().getActualNode().getX() + "\"><ele>" + 0 + "</ele>" + "<time>"
					+ df.format(new Date()) + "</time>" + "<name>"
					+ points.get(i).getCurrentState().getActualNode().getID() + "</name></trkpt>\n";
		}

		String footer = "</trk></gpx>";

		try {
			FileWriter writer = new FileWriter(file, true);
			writer.append(segments);
			writer.append(footer);
			writer.flush();
			writer.close();
		} catch (IOException e) {
			return -1;
		}
		return 0;
	}
}
