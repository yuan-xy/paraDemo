package ap;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

public class KosarajuArithmeticTest extends TestCase {
	/**
	 * Case for<br>
	 * V<sub>1</sub> -> V<sub>2</sub><br>
	 * V<sub>2</sub> -> V<sub>6</sub><br>
	 * V<sub>6</sub> -> V<sub>4</sub><br>
	 * V<sub>4</sub> -> V<sub>3</sub><br>
	 * V<sub>5</sub> -> V<sub>4</sub><br>
	 * V<sub>3</sub> -> V<sub>2</sub><br>
	 * V<sub>5</sub> -> V<sub>1</sub><br>
	 */
	public void testCase1() {
		List<IDirectedGraphElement> directGraph = buildDirectGraphForTestCase1();

		KosarajuArithmetic arithm = new KosarajuArithmetic(directGraph);

		arithm.execute();

		assertEquals(3, arithm.getResult().size());
	}

	private List<IDirectedGraphElement> buildDirectGraphForTestCase1() {
		List<IDirectedGraphElement> directGraph = new ArrayList<IDirectedGraphElement>();

		// init data structure
		ElementForTst ele1 = new ElementForTst();
		ele1._index = 1;
		directGraph.add(ele1);

		ElementForTst ele2 = new ElementForTst();
		ele2._index = 2;
		directGraph.add(ele2);

		ElementForTst ele3 = new ElementForTst();
		ele3._index = 3;
		directGraph.add(ele3);

		ElementForTst ele4 = new ElementForTst();
		ele4._index = 4;
		directGraph.add(ele4);

		ElementForTst ele5 = new ElementForTst();
		ele5._index = 5;
		directGraph.add(ele5);

		ElementForTst ele6 = new ElementForTst();
		ele6._index = 6;
		directGraph.add(ele6);

		// build relationship
		ele1.getInputList().add(ele5);
		ele1.getOutputList().add(ele2);

		ele2.getInputList().add(ele1);
		ele2.getInputList().add(ele3);
		ele2.getOutputList().add(ele6);

		ele3.getInputList().add(ele4);
		ele3.getOutputList().add(ele2);

		ele4.getInputList().add(ele6);
		ele4.getInputList().add(ele5);
		ele4.getOutputList().add(ele3);

		ele5.getOutputList().add(ele4);
		ele5.getOutputList().add(ele1);

		ele6.getInputList().add(ele2);
		ele6.getOutputList().add(ele4);

		return directGraph;
	}

	/**
	 * Case for<br>
	 * V<sub>1</sub> -> V<sub>2</sub><br>
	 * V<sub>2</sub> -> V<sub>6</sub><br>
	 * V<sub>6</sub> -> V<sub>4</sub><br>
	 * V<sub>4</sub> -> V<sub>3</sub><br>
	 * V<sub>4</sub> -> V<sub>5</sub><br>
	 * V<sub>3</sub> -> V<sub>2</sub><br>
	 * V<sub>5</sub> -> V<sub>1</sub><br>
	 */
	public void testCase2() {
		List<IDirectedGraphElement> directGraph = buildDirectGraphForTestCase2();

		KosarajuArithmetic arithm = new KosarajuArithmetic(directGraph);

		arithm.execute();

		assertEquals(1, arithm.getResult().size());
	}

	private List<IDirectedGraphElement> buildDirectGraphForTestCase2() {
		List<IDirectedGraphElement> directGraph = new ArrayList<IDirectedGraphElement>();

		// init data structure
		ElementForTst ele1 = new ElementForTst();
		ele1._index = 1;
		directGraph.add(ele1);

		ElementForTst ele2 = new ElementForTst();
		ele2._index = 2;
		directGraph.add(ele2);

		ElementForTst ele3 = new ElementForTst();
		ele3._index = 3;
		directGraph.add(ele3);

		ElementForTst ele4 = new ElementForTst();
		ele4._index = 4;
		directGraph.add(ele4);

		ElementForTst ele5 = new ElementForTst();
		ele5._index = 5;
		directGraph.add(ele5);

		ElementForTst ele6 = new ElementForTst();
		ele6._index = 6;
		directGraph.add(ele6);

		// build relationship
		ele1.getInputList().add(ele5);
		ele1.getOutputList().add(ele2);

		ele2.getInputList().add(ele1);
		ele2.getInputList().add(ele3);
		ele2.getOutputList().add(ele6);

		ele3.getInputList().add(ele4);
		ele3.getOutputList().add(ele2);

		ele4.getInputList().add(ele6);
		ele4.getOutputList().add(ele5);
		ele4.getOutputList().add(ele3);

		ele5.getInputList().add(ele4);
		ele5.getOutputList().add(ele1);

		ele6.getInputList().add(ele2);
		ele6.getOutputList().add(ele4);

		return directGraph;
	}

	/**
	 * Case for<br>
	 * V<sub>0</sub> -> V<sub>1</sub>, V<sub>1</sub> -> V<sub>6</sub><br>
	 * V<sub>2</sub> -> V<sub>1</sub>, V<sub>3</sub> -> V<sub>2</sub><br>
	 * V<sub>3</sub> -> V<sub>4</sub>, V<sub>4</sub> -> V<sub>5</sub><br>
	 * V<sub>4</sub> -> V<sub>6</sub>, V<sub>5</sub> -> V<sub>6</sub><br>
	 * V<sub>5</sub> -> V<sub>7</sub>, V<sub>6</sub> -> V<sub>5</sub><br>
	 * V<sub>7</sub> -> V<sub>8</sub>, V<sub>8</sub> -> V<sub>7</sub><br>
	 * V<sub>8</sub> -> V<sub>0</sub><br>
	 */
	public void testCase3() {
		List<IDirectedGraphElement> directGraph = buildDirectGraphForTestCase3();

		KosarajuArithmetic arithm = new KosarajuArithmetic(directGraph);

		arithm.execute();

		assertEquals(4, arithm.getResult().size());
	}

	private List<IDirectedGraphElement> buildDirectGraphForTestCase3() {
		List<IDirectedGraphElement> directGraph = new ArrayList<IDirectedGraphElement>();

		// init data structure
		ElementForTst ele0 = new ElementForTst();
		ele0._index = 0;
		directGraph.add(ele0);

		ElementForTst ele1 = new ElementForTst();
		ele1._index = 1;
		directGraph.add(ele1);

		ElementForTst ele2 = new ElementForTst();
		ele2._index = 2;
		directGraph.add(ele2);

		ElementForTst ele3 = new ElementForTst();
		ele3._index = 3;
		directGraph.add(ele3);

		ElementForTst ele4 = new ElementForTst();
		ele4._index = 4;
		directGraph.add(ele4);

		ElementForTst ele5 = new ElementForTst();
		ele5._index = 5;
		directGraph.add(ele5);

		ElementForTst ele6 = new ElementForTst();
		ele6._index = 6;
		directGraph.add(ele6);

		ElementForTst ele7 = new ElementForTst();
		ele6._index = 7;
		directGraph.add(ele7);

		ElementForTst ele8 = new ElementForTst();
		ele1._index = 8;
		directGraph.add(ele8);

		// build relationship
		ele0.getInputList().add(ele8);
		ele0.getOutputList().add(ele1);

		ele1.getInputList().add(ele0);
		ele1.getInputList().add(ele2);
		ele1.getOutputList().add(ele6);

		ele2.getInputList().add(ele3);
		ele2.getOutputList().add(ele1);

		ele3.getOutputList().add(ele2);
		ele3.getOutputList().add(ele4);

		ele4.getInputList().add(ele3);
		ele4.getOutputList().add(ele5);
		ele4.getOutputList().add(ele6);

		ele5.getInputList().add(ele4);
		ele5.getInputList().add(ele6);
		ele5.getOutputList().add(ele6);
		ele5.getOutputList().add(ele7);

		ele6.getInputList().add(ele1);
		ele6.getInputList().add(ele5);
		ele6.getInputList().add(ele4);
		ele6.getOutputList().add(ele5);

		ele7.getInputList().add(ele5);
		ele7.getInputList().add(ele8);
		ele7.getOutputList().add(ele8);

		ele8.getInputList().add(ele7);
		ele8.getOutputList().add(ele0);
		ele8.getOutputList().add(ele7);

		return directGraph;
	}
}

class ElementForTst implements IDirectedGraphElement {
	List<IDirectedGraphElement> _inputList = new ArrayList<IDirectedGraphElement>();

	List<IDirectedGraphElement> _outputList = new ArrayList<IDirectedGraphElement>();

	boolean _isVisited = false;

	int _index;

	public void changeEdgeDirect() {
		List<IDirectedGraphElement> swapTmp = _inputList;
		_inputList = _outputList;
		_outputList = swapTmp;
	}

	public List<IDirectedGraphElement> getInputList() {
		return _inputList;
	}

	public List<IDirectedGraphElement> getOutputList() {
		return _outputList;
	}

	public boolean getVisitStatus() {
		return _isVisited;
	}

	public void setVisitStatus(boolean isVisited) {
		_isVisited = isVisited;
	}
}