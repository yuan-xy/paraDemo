package util;

public class DependenceAnalysisUtil {
	public static boolean isSingleVarInequ(int[] a) {
		int nonZeroVars = 0;
		for (int i = 0; i < a.length; i++) {
			if (a[i] != 0)
				nonZeroVars++;
		}
		if (nonZeroVars > 1)
			return false;
		else
			return true;
	}

	public static boolean checkBounds(int[][] A, int[] b) {
		int[][] bounds = new int[A[0].length][2];
		for (int i = 0; i < bounds.length; i++) {
			bounds[i][0] = Integer.MIN_VALUE;
			bounds[i][1] = Integer.MAX_VALUE;
		}

		for (int i = 0; i < A.length; i++) {
			for (int j = 0; j < A[i].length; j++) {
				if (A[i][j] > 0) {
					if (bounds[j][0] < -b[i] / A[i][j]) {
						bounds[j][0] = -b[i] / A[i][j];
					}
				} else if (A[i][j] < 0) {
					if (bounds[j][1] > -b[i] / A[i][j]) {
						bounds[j][1] = -b[i] / A[i][j];
					}
				}
			}
		}

		for (int i = 0; i < bounds.length; i++) {
			if (bounds[i][0] > bounds[i][1])
				return false;
		}

		return true;
	}
}
