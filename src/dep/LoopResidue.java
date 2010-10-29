package dep;

import java.util.List;
import java.util.Stack;

public class LoopResidue implements ExactDependenceTst {

	int NEG_INFINITE = -32768;
	int[][] graph;
//	int[] visited;
	Stack<Integer> path;
	List<int[]> cyclicpath;
	boolean appropriate = true;
	boolean nodep = false;
	
	/**
	 * @see dep.ExactDependenceTst#dependent(int[][], int[])
	 */
	public boolean dependent(int[][] A, int[] b) throws NotApplicableCaseException {
		// TODO Auto-generated method stub
		constructGraph(A,b);
		findCyclicPath();
		if(appropriate == true)
		{
			if(nodep == true)
				return false;
			else
				return true;
		}
		else
		{
			if(nodep == true)
				return false;
			else
				throw new NotApplicableCaseException();
		}
	}
	
	/**
	 * 得到构造图中节点v的第一个邻居节点,用于图的DFS
	 * @param v
	 * @return 第一个邻居节点索引
	 */
	private int getFirstNeighbor(int v)
	{
		int i;
		for(i=0; i < graph[v].length; i++)
			if(graph[v][i] != NEG_INFINITE)
				return i;
		return -1;
	}
	
	/**
	 * 得到构造图中节点v在w后的下一个邻居节点,用于图的DFS
	 * @param v
	 * @param w
	 * @return w后下一个v的邻居节点
	 */
	private int getNextNeighbor(int v,int w)
	{
		int i;
		for(i=w+1; i < graph[v].length; i++)
			if(graph[v][i] != NEG_INFINITE)
				return i;
		return -1;
	}
	
	/**
	 * 查找所有有环路径,并对路径权重和进行检测
	 *
	 */
	private void findCyclicPath() {
		int nodenum = graph[0].length;
		path = new Stack<Integer>();
		
		for(int i=0; i < nodenum; i++)
		{
			if(nodep == false)
			{
				int[] visited = new int[nodenum];
				for(int j=0;j < nodenum; j++)
					visited[j] = 0;
				path.clear();
				path.add(i);
				DFS(i,i,visited);
			}
			else
				break;
		}
	}
	
	/**
	 * DFS遍历所有路径
	 * @param root 此次路径原始根节点
	 * @param v 此次DFS调用的根节点
	 * @param visited 节点访问数组
	 * @return 0表示顺利遍历完,-1表示出现有环路径权重和为负,未遍历完
	 */
	private int DFS(int root,int v,int[] visited) {
		visited[v] = -1;
		
		int w = getFirstNeighbor(v);
		while((w != -1) && (nodep == false))
		{
			if(visited[w] == 0)
			{
				path.push(w);
				DFS(root,w,visited);
				visited[w] = 1;
			}
			else if(visited[w] == -1)
			{
				if(root == w)
				{
					if(checkPathWeight(path) < 0)
					{
						nodep = true;
						return -1;
					}
				}
//				else
//					while(path.pop() != v);
			}
			w = getNextNeighbor(v,w);
		}
		while(!path.empty())
		{
			int a = path.pop();
			if(a != v)
				visited[a] = 0;
			else
				break;
		}
		return 0;
	}
	
	/**
	 * 检查数组中是否只包含一个非零元素(即判断是否表示常数上下届)
	 * @param a 检测的数组
	 * @return 若单一非零元素则返回其在数组中的索引,否则为-1
	 */
	private int checkIsSingle(int[] a) {
		boolean flag = false;
		int index = -1;
		for(int i=0; i < a.length; i++)
		{
			if(a[i] != 0)
			{
				if(!flag)
				{
					index = i;
					flag = true;
				}
				else
					return -1;
			}			
		}
		return index;
	}

	/**
	 * 构造 表示Ax+b>=0 的图
	 * @param A   系数矩阵
	 * @param b   常数向量
	 */
	private void constructGraph(int[][] A,int[] b) {
		int node = A[0].length + 1;
		graph = new int[node][node];
//		visited = new int[node];
		for(int i=0; i < node; i++)
		{
//			visited[i] = 0;
			for(int j=0; j < node; j++)
				graph[i][j] = NEG_INFINITE;
		}
		for(int i=0; i < A.length; i++)
		{
				int nindex = checkIsSingle(A[i]);
				if(nindex != -1)
				{
					if(A[i][nindex] > 0)
					{
						//v >= c
						graph[0][nindex+1] = b[i];
					}
					else
					{
						graph[nindex+1][0] = b[i];
					}
				}
				else
				{
					int[] ind = checkAppropriate(A[i]);
					if(appropriate != false)
					{
						if(A[i][ind[0]] > 0)
						{
							graph[ind[1]+1][ind[0]+1] = (int)Math.floor((double)b[i]/A[i][ind[0]]);
						}
						else
						{
							graph[ind[0]+1][ind[1]+1] = (int)Math.floor((double)b[i]/A[i][ind[1]]);
						}
					}
				}
		}

	}
	
	/**
	 * 检查是否合适用loopresidue算法,若a中非零项超过2,或者非零项同号,
	 * 或者绝对值不等,则不适用循环留数算法
	 * @param a  待检测的数组
	 * @return  返回数组a中不为0的项的下标,函数中设置全局量appropriate
	 */
	private int[] checkAppropriate(int[] a) {
		int count = 0;
		int[] index = new int[2];
		boolean minus = false;
		boolean first = true;
		int diff = 0;
		//???? if nonzero is >2, then not appropriate?
		if(appropriate != false)
		{
			for(int i=0; i < a.length; i++)
			{
				if(a[i] < 0)
				{
					if(minus == true)
					{
						if(first == true)
						{
							first = false;
							count++;
							index[count-1] = i;
							diff = Math.abs(a[i]);
						}
						else
						{
							appropriate = false;
							break;
						}
					}
					else if(appropriate == true)
					{
						minus = true;
						first = false;
						count++;
						if((count > 2) || ((diff != 0)
								&& (diff != Math.abs(a[i]))))
						{
							appropriate = false;
							break;
						}
						else						
						{
							index[count-1] = i;
							diff = Math.abs(a[i]);
						}
					}
				}
				else if(a[i] > 0)
				{
					if(minus == false)
					{
						if(first == true)
						{
							first = false;
							count++;
							index[count-1] = i;
							diff = Math.abs(a[i]);
						}
						else
						{
							appropriate = false;
							break;
						}
					}
					else if(appropriate == true)
					{
						minus = false;
						count ++;
						if((count > 2) || ((diff != 0)
								&& (diff != a[i])))
						{
							appropriate = false;
							break;
						}
						else						
						{
							index[count-1] = i;
							diff = a[i];
						}
					}
				}
			}
		}
		return index;
	}
	
	/**
	 * 计算路径权重和
	 * @param path   待检测路径
	 * @return   路径权重和
	 */
	private int checkPathWeight(Stack<Integer> path) {
		int weight = 0;
		for(int i=0; i < path.size()-1; i++)
		{
			weight += graph[path.get(i)][path.get(i+1)];
		}
		weight += graph[path.get(path.size()-1)][path.get(0)];
		return weight;
	}
}
