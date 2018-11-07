package domain;

import java.io.FileNotFoundException;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.json.simple.parser.ParseException;
import org.xml.sax.SAXException;

public class Problem {
	StateSpace space = new StateSpace();
	State i_state = new State();

	public Problem(String json)
			throws FileNotFoundException, IOException, ParseException, ParserConfigurationException, SAXException {
		Object[] ss = new Object[2];
		ss = TSFReader.parseJSON(space, i_state, json);
		space = (StateSpace) ss[0];
		i_state = (State) ss[1];
	}

	public Problem() {
		// TODO Auto-generated constructor stub
	}

	public StateSpace getSpace() {
		return space;
	}

	public void setSpace(StateSpace space) {
		this.space = space;
	}

	public State getI_state() {
		return i_state;
	}

	public void setI_state(State i_state) {
		this.i_state = i_state;
	}

	public boolean isGoal(State s) {
		if (s.getN_list().isEmpty()) {
			return true;
		}
		return false;
	}
}
