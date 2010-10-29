package ap;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

public class KosarajuArithmetic {
	private List<Vector<IDirectedGraphElement>> _stronglyConnectedSubgraphs = new ArrayList<Vector<IDirectedGraphElement>>();

	private List<IDirectedGraphElement> _directedGraph = null;

	private List<IDirectedGraphElement> _numberedEleList = new ArrayList<IDirectedGraphElement>();

	private List<IDirectedGraphElement> _elementStack = new ArrayList<IDirectedGraphElement>();

	public KosarajuArithmetic(List<IDirectedGraphElement> directedGraph) {
		_directedGraph = directedGraph;
	}

	public void execute() throws ArithmeticFailedException {
		validGraph();

		initDirectedGraph();

		dfsForSetElementNum();

		changeEdgeDirect();

		initDirectedGraph();

		dfsForFindSCS();

		changeEdgeDirect();
	}

	private void validGraph() {
		if (_directedGraph == null)
			throw new ArithmeticFailedException();
	}

	private void initDirectedGraph() {
		for (Iterator<IDirectedGraphElement> iter = _directedGraph.iterator(); iter
				.hasNext();) {
			iter.next().setVisitStatus(false);
		}
	}

	private void dfsForSetElementNum() {
		for (Iterator<IDirectedGraphElement> iter = _directedGraph.iterator(); iter
				.hasNext();) {
			IDirectedGraphElement item = iter.next();
			if (!item.getVisitStatus()) {
				dfsForSetElementNumIterBlock(item);
			}
		}
	}

	private void dfsForSetElementNumIterBlock(IDirectedGraphElement element) {
		element.setVisitStatus(true);

		for (Iterator<IDirectedGraphElement> iter = element.getOutputList()
				.iterator(); iter.hasNext();) {
			IDirectedGraphElement item = iter.next();
			if (!item.getVisitStatus()) {
				dfsForSetElementNumIterBlock(item);
			}
		}

		_numberedEleList.add(element);
	}

	private void changeEdgeDirect() {
		for (Iterator<IDirectedGraphElement> iter = _directedGraph.iterator(); iter
				.hasNext();) {
			iter.next().changeEdgeDirect();
		}
	}

	private void dfsForFindSCS() {
		for (int i = _numberedEleList.size() - 1; i >= 0; i--) {
			IDirectedGraphElement item = _numberedEleList.get(i);
			if (!item.getVisitStatus()) {
				dfsForFindSCSIterBlock(item);
				
				buildStronglyConnectedSubgraph();
			}
		}
	}

	private void buildStronglyConnectedSubgraph() {
		Vector<IDirectedGraphElement> stronglyConnectedSubgraph = new Vector<IDirectedGraphElement>();
		stronglyConnectedSubgraph.addAll(_elementStack);
		_stronglyConnectedSubgraphs.add(stronglyConnectedSubgraph);
		_elementStack.clear();
	}

	private void dfsForFindSCSIterBlock(IDirectedGraphElement element) {
		element.setVisitStatus(true);

		_elementStack.add(element);

		IDirectedGraphElement next = findBiggerNumAndUnvisitedOutput(element);

		if (next != null)
			dfsForFindSCSIterBlock(next);
	}

	private IDirectedGraphElement findBiggerNumAndUnvisitedOutput(
			IDirectedGraphElement element) {
		IDirectedGraphElement target = null;

		for (Iterator<IDirectedGraphElement> iter = element.getOutputList()
				.iterator(); iter.hasNext();) {
			IDirectedGraphElement item = iter.next();

			if (!item.getVisitStatus() && needUpdate(item, target)) {
				target = item;
			}
		}

		return target;
	}

	private boolean needUpdate(IDirectedGraphElement newItem,
			IDirectedGraphElement oldItem) {
		if (oldItem == null)
			return true;
		return _numberedEleList.indexOf(oldItem) < _numberedEleList
				.indexOf(newItem);
	}

	public List<Vector<IDirectedGraphElement>> getResult() {
		return _stronglyConnectedSubgraphs;
	}
}

class ArithmeticFailedException extends RuntimeException {

}