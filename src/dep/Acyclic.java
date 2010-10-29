package dep;

import java.util.ArrayList;
import java.util.List;

import util.DependenceAnalysisUtil;

public class Acyclic implements ExactDependenceTst {

	private Node[] directedGraph;

	private List<Node> nodeVisitedStack = new ArrayList<Node>();

	public int[][] A;
	public int[] b;

	/**
	 * @see dep.ExactDependenceTst#dependent(int[][], int[])
	 */
	public boolean dependent(int[][] A, int[] b)
			throws NotApplicableCaseException {
		this.A = A;
		this.b = b;
		buildDirectedGraph();
		if (existCyclic()) {
			if (!dfsForSetBounds())
				return false;

			List<Integer> indexlist = new ArrayList<Integer>();
			for (int i = 0; i < this.A.length; i++) {
				int nonZero = 0;
				for (int j = 0; j < this.A[i].length; j++) {
					if (this.A[i][j] != 0) {
						nonZero++;
						break;
					}
				}
				if (nonZero != 0)
					indexlist.add(i);
			}

			int[][] newA = new int[indexlist.size()][];
			int[] newb = new int[indexlist.size()];
			for (int i = 0; i < indexlist.size(); i++) {
				newA[i] = this.A[indexlist.get(i)].clone();
				newb[i] = this.b[indexlist.get(i)];
			}
			this.A = newA;
			this.b = newb;

			throw new NotApplicableCaseException();
		} else {
			if (!dfsForSetBounds())
				return false;
			else
				return true;
		}
	}

	/**
	 * 深度优先搜索整个有向图并进行等价消元
	 * @return 如果存在上界小于下界的情况返回false,否则返回true
	 */
	private boolean dfsForSetBounds() {
		Node result = null;
		do {
			if (this.A[0].length == 1)
				return DependenceAnalysisUtil.checkBounds(this.A, this.b);
			result = findLeafNode();
			if (result != null) {
				if (result.getLowerBound() > result.getUpperBound())
					return false;
				result.setValue(result.getUpperBound());
				updateMartrix(result);
				buildDirectedGraph();
			}
		} while (result != null);
		return true;
	}

	/**
	 * 将被设置好的变量应用于原不等式组,消元并更新矩阵
	 * @param result 通过深度优先搜索找到的叶节点
	 */
	private void updateMartrix(Node result) {
		int[][] A = this.A;
		int[] b = this.b;
		int[][] newA = new int[A.length][A[0].length - 1];
		int[] newb = new int[b.length];
		for (int i = 0; i < A.length; i++) {
			for (int j = 0, t = 0; j < A[i].length; j++) {
				int varIndex = result.getVarIndex()
						% (directedGraph.length / 2);
				if (varIndex == j) {
					if (result.getVarIndex() > varIndex)
						newb[i] = -A[i][varIndex] * result.getValue() + b[i];
					else
						newb[i] = A[i][varIndex] * result.getValue() + b[i];
				} else {
					newA[i][t++] = A[i][j];
				}
			}
		}
		this.A = newA;
		this.b = newb;
	}

	/**
	 * 深度优先搜索有向图中的叶节点
	 * @return 如果寻找到叶节点,返回其对象,否则返回null
	 */
	private Node findLeafNode() {
		nodeVisitedStack.clear();
		for (int i = 0; i < directedGraph.length; i++) {
			nodeVisitedStack.add(directedGraph[i]);
			Node result = dfsForLeafNodeFind(directedGraph[i]);
			if (result != null) {
				nodeVisitedStack.remove(directedGraph[i]);
				return result;
			}

			nodeVisitedStack.remove(directedGraph[i]);
		}
		return null;
	}

	/**
	 * @see dep.Acyclic#findLeafNode()<br>
	 * 递归方法
	 * @param node
	 * @return
	 */
	private Node dfsForLeafNodeFind(Node node) {
		if (!(node.getDepNode().size() > 0))
			return node;
		for (int i = 0; i < node.getDepNode().size(); i++) {
			Node depNode = node.getDepNode().get(i);
			if (nodeVisitedStack.contains(depNode))
				continue;
			nodeVisitedStack.add(depNode);
			Node result = dfsForLeafNodeFind(depNode);
			if (result != null) {
				nodeVisitedStack.remove(depNode);
				return result;
			}
			nodeVisitedStack.remove(depNode);
		}
		return null;
	}

	/**
	 * 判断有向图是否存在环
	 * @return
	 */
	private boolean existCyclic() {
		nodeVisitedStack.clear();
		for (int i = 0; i < directedGraph.length; i++) {
			nodeVisitedStack.add(directedGraph[i]);
			if (dfsForCyclicCheck(directedGraph[i])) {
				nodeVisitedStack.remove(directedGraph[i]);
				return true;
			}
			nodeVisitedStack.remove(directedGraph[i]);
		}
		return false;
	}

	/**
	 * @see dep.Acyclic#existCyclic()
	 * 递归方法
	 * @param node
	 * @return 
	 */
	private boolean dfsForCyclicCheck(Node node) {
		for (int i = 0; i < node.getDepNode().size(); i++) {
			Node depNode = node.getDepNode().get(i);
			if (nodeVisitedStack.contains(depNode))
				return true;
			nodeVisitedStack.add(depNode);
			if (dfsForCyclicCheck(depNode)) {
				nodeVisitedStack.remove(depNode);
				return true;
			}
			nodeVisitedStack.remove(depNode);
		}
		return false;
	}

	/**
	 * 构造有向图
	 */
	private void buildDirectedGraph() {
		int[][] A = this.A;
		int[] b = this.b;
		int varCont = A[0].length;
		int nodeCont = varCont * 2;
		directedGraph = new Node[nodeCont];
		for (int i = 0; i < nodeCont; i++) {
			directedGraph[i] = new Node();
			directedGraph[i].setVarIndex(i);
		}
		for (int i = 0; i < A.length; i++) {
			for (int j = 0; j < varCont; j++) {
				if (A[i][j] > 0) {
					if (DependenceAnalysisUtil.isSingleVarInequ(A[i])) {
						if (directedGraph[j].getLowerBound() < -b[i] / A[i][j]) {
							directedGraph[j].setLowerBound(-b[i] / A[i][j]);
						}
						if (directedGraph[j + varCont].getUpperBound() > b[i]
								/ A[i][j]) {
							directedGraph[j + varCont].setUpperBound(b[i]
									/ A[i][j]);
						}
					} else {
						for (int t = 0; t < varCont; t++) {
							if (t != j && A[i][t] != 0) {
								if (A[i][t] > 0)
									directedGraph[j + varCont].getDepNode()
											.add(directedGraph[t]);
								else
									directedGraph[j + varCont].getDepNode()
											.add(directedGraph[t + varCont]);
							}
						}
					}
				} else if (A[i][j] < 0) {
					if (DependenceAnalysisUtil.isSingleVarInequ(A[i])) {
						if (directedGraph[j + varCont].getLowerBound() < b[i]
								/ A[i][j]) {
							directedGraph[j + varCont].setLowerBound(b[i]
									/ A[i][j]);
						}
						if (directedGraph[j].getUpperBound() > -b[i] / A[i][j]) {
							directedGraph[j].setUpperBound(-b[i] / A[i][j]);
						}
					} else {
						for (int t = 0; t < A[i].length; t++) {
							if (t != j && A[i][t] != 0) {
								if (A[i][t] > 0)
									directedGraph[j].getDepNode().add(
											directedGraph[t]);
								else
									directedGraph[j].getDepNode().add(
											directedGraph[t + varCont]);
							}
						}
					}
				}
			}
		}
	}
}

class Node {
	private int varIndex;

	private int value;

	private int upperBound = Integer.MAX_VALUE;

	private int lowerBound = Integer.MIN_VALUE;

	private List<Node> depNode;

	public int getVarIndex() {
		return varIndex;
	}

	public void setVarIndex(int varIndex) {
		this.varIndex = varIndex;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public int getUpperBound() {
		return upperBound;
	}

	public void setUpperBound(int upperBound) {
		this.upperBound = upperBound;
	}

	public int getLowerBound() {
		return lowerBound;
	}

	public void setLowerBound(int lowerBound) {
		this.lowerBound = lowerBound;
	}

	public List<Node> getDepNode() {
		if (depNode == null)
			setDepNode(new ArrayList<Node>());
		return depNode;
	}

	public void setDepNode(List<Node> depNode) {
		this.depNode = depNode;
	}
}
