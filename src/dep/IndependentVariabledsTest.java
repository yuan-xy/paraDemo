package dep;

import junit.framework.TestCase;
import util.MatrixUtilsTest;

public class IndependentVariabledsTest extends TestCase {
	public void test() {
		int[][] b = new int[][] { { 1, 0, 0, 0, 0 }, { -1, 0, 0, 0, -10 },
				{ 0, 1, 0, 0, 0 }, { 0, -1, 0, 0, -10 }, { 0, 0, 1, 0, 0 },
				{ 0, 0, -1, 0, -10 }, { 0, 0, 0, 1, 0 }, { 0, 0, 0, -1, -10 } };

		int[][] a = new int[][] { { 1, 0,  0, -1, 10 }, { 0, -1, 1, 0, 11 } };

//		MatrixUtilsTest.assertEquals(false, new IndependentVariableds_orig()
//				.independentVariablesTest(b, a));
	}
}
