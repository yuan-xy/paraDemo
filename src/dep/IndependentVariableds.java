package dep;

public class IndependentVariableds {

	/**
	 * 步骤1:判断可否使用单变量测试
	 * 步骤2:用等式方程消元
	 * 步骤3:整理未知数的值域
	 * 步骤4:判断是否存在矛盾,即值域下界大于上界
	 * @param b 不等式组,代表边界条件
	 * @param a 等式组,代表数组访问条件
	 * @return
	 */
	public boolean independentVariablesTest(int[][] b, int[][] a) {
		//步骤1
		if (!isIndependent(a))
			return false;
		
		//步骤2
		for (int i = 0; i < a.length; i++) {
			for (int j = 0; j < a[i].length - 1; j++) {
				if (a[i][j] != 0) {
					elimination(b, a[i], j);
					break;
				}
			}
		}

		int[][] bounds = new int[b[0].length - 1][4];

		//步骤3
		for (int i = 0; i < b.length; i++) {
			for (int j = 0; j < b[i].length - 1; j++) {
				if (b[i][j] > 0) {
					if (bounds[j][0] < b[i][b[i].length - 1] / b[i][j]
							|| bounds[j][2] == 0) {
						bounds[j][0] = b[i][b[i].length - 1] / b[i][j];
						if (bounds[j][2] == 0)
							bounds[j][2] = 1;
					}
				} else if (b[i][j] < 0) {
					if (bounds[j][1] > b[i][b[i].length - 1] / b[i][j]
							|| bounds[j][3] == 0) {
						bounds[j][1] = b[i][b[i].length - 1] / b[i][j];
						if (bounds[j][3] == 0)
							bounds[j][3] = 1;
					}
				}
			}
		}

		//步骤4
		for (int i = 0; i < bounds.length; i++) {
			if (bounds[i][0] > bounds[i][1])
				return false;
		}

		return true;
	}

	/**
	 * 判断方程组中的方程是不是二元方程
	 * @param a 等式方程组a<sub>1</sub>i<sub>1</sub> + a<sub>2</sub>i<sub>2</sub> + ...= a<sub>n</sub>
	 * @return
	 */
	private boolean isIndependent(int[][] a) {
		int nonZero;
		for (int i = 0; i < a.length; i++) {
			nonZero = 0;
			for (int j = 0; j < a[i].length - 1; j++) {
				if (a[i][j] != 0)
					nonZero++;
			}
			if (nonZero > 2)
				return false;
		}
		return true;
	}

	/**
	 * 用等式方程组中的方程a_sub消去不等式组b中的一个未知数
	 * @param b 不等式组
	 * @param a_sub 等式方程
	 * @param index 消去项索引
	 */
	private void elimination(int[][] b, int[] a_sub, int index) {
		for (int i = 0; i < b.length; i++) {
			if (b[i][index] != 0) {
				for (int j = 0; j < a_sub.length; j++) {
					if (j != index && a_sub[j] != 0) {
						b[i][j] += -1 * b[i][index] * a_sub[j] / a_sub[index];
					}
				}
				b[i][index] = 0;
			}
		}
	}
}
