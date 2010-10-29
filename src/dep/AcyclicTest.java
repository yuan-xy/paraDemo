package dep;

import util.MatrixUtilsTest;
import junit.framework.TestCase;

public class AcyclicTest extends TestCase {
	/**
	 * Case for<br>
	 * 0 <= t<sub>1</sub> <= 10<br>
	 * 0 <= t<sub>2</sub> <= 10<br>
	 * 0 <= t<sub>3</sub> <= 10<br>
	 * t<sub>1</sub> + 2t<sub>2</sub> <= 0<br>
	 * t<sub>3</sub> - t<sub>2</sub> <= 0<br>
	 */
	public void testForAcyclicAndFalseCase() {
		int[][] A = new int[][] { 
				{ 1, 0, 0 }, 
				{ -1, 0, 0 }, 
				{ 0, 1, 0 },
				{ 0, -1, 0 }, 
				{ 0, 0, 1 }, 
				{ 0, 0, -1 }, 
				{ -1, -2, 0 },
				{ 0, 1, -1 } 
			};
		int[] b = new int[] { -1, 10, -1, 10, -1, 10, 0, 0 };

		assertFalse(new Acyclic().dependent(A, b));
	}

	/**
	 * Case for<br>
	 * 1 <= t<sub>1</sub> <= 10<br>
	 * 1 <= t<sub>2</sub> <= 10<br>
	 * 0 <= t<sub>3</sub> <= 4<br>
	 * t<sub>2</sub> - t<sub>1</sub> <= 0<br>
	 * t<sub>1</sub> - t<sub>3</sub> - 4 <= 0<br>
	 */
	public void testForAcyclicAndTrueCase() {
		int[][] A = new int[][] { 
				{ 1, 0, 0 }, 
				{ -1, 0, 0 }, 
				{ 0, 1, 0 },
				{ 0, -1, 0 }, 
				{ 0, 0, 1 }, 
				{ 0, 0, -1 }, 
				{ 1, -1, 0 },
				{ -1, 0, 1 }
			};
		int[] b = new int[] { -1, 10, -1, 10, 0, 4, 0, 4 };

		assertTrue(new Acyclic().dependent(A, b));
	}

	/**
	 * Case for<br>
	 * 1 <= t<sub>1</sub> <= 10<br>
	 * 1 <= t<sub>2</sub> <= 10<br>
	 * -5 <= t<sub>3</sub> <= -4<br>
	 * t<sub>2</sub> - t<sub>1</sub> <= 0<br>
	 * t<sub>1</sub> - t<sub>3</sub> - 4 <= 0<br>
	 * t<sub>4</sub> - t<sub>3</sub> <= 0<br>
	 * t<sub>4</sub> - t<sub>5</sub> <= 0<br>
	 * t<sub>5</sub> - t<sub>6</sub> <= 0<br>
	 * t<sub>6</sub> - t<sub>4</sub> <= 0<br>
	 */
	public void testForCyclicAndFalseCase() {
		int[][] A = new int[][] { 
				{ 1, 0, 0, 0, 0, 0 }, 
				{ -1, 0, 0, 0, 0, 0 }, 
				{ 0, 1, 0, 0, 0, 0 },
				{ 0, -1, 0, 0, 0, 0 }, 
				{ 0, 0, 1, 0, 0, 0 }, 
				{ 0, 0, -1, 0, 0, 0 }, 
				{ 1, -1, 0, 0, 0, 0 },
				{ -1, 0, 1, 0, 0, 0 },
				{0, 0, -1, 1, 0, 0 },
				{0, 0, 0, -1, 1, 0 },
				{0, 0, 0, 0, -1, 1 },
				{0, 0, 0, 1, 0, -1 },
			};
		int[] b = new int[] { -1, 10, -1, 10, 5, -4, 0, 4, 0, 0, 0, 0 };
		
		assertFalse(new Acyclic().dependent(A, b));
	}

	/**
	 * Case for<br>
	 * 1 <= t<sub>1</sub> <= 10<br>
	 * 1 <= t<sub>2</sub> <= 10<br>
	 * 0 <= t<sub>3</sub> <= 4<br>
	 * t<sub>2</sub> - t<sub>1</sub> <= 0<br>
	 * t<sub>1</sub> - t<sub>3</sub> - 4 <= 0<br>
	 * t<sub>4</sub> - t<sub>3</sub> <= 0<br>
	 * t<sub>4</sub> - t<sub>5</sub> <= 0<br>
	 * t<sub>5</sub> - t<sub>6</sub> <= 0<br>
	 * t<sub>6</sub> - t<sub>4</sub> <= 0<br>
	 */
	public void testForCyclicAndExceptionCase() {
		int[][] A = new int[][] { 
				{ 1, 0, 0, 0, 0, 0 }, 
				{ -1, 0, 0, 0, 0, 0 }, 
				{ 0, 1, 0, 0, 0, 0 },
				{ 0, -1, 0, 0, 0, 0 }, 
				{ 0, 0, 1, 0, 0, 0 }, 
				{ 0, 0, -1, 0, 0, 0 }, 
				{ 1, -1, 0, 0, 0, 0 },
				{ -1, 0, 1, 0, 0, 0 },
				{0, 0, 1, -1, 0, 0 },
				{0, 0, 0, -1, 1, 0 },
				{0, 0, 0, 0, -1, 1 },
				{0, 0, 0, 1, 0, -1 },
			};
		int[] b = new int[] { -1, 10, -1, 10, 0, 4, 0, 4, 0, 0, 0, 0 };
		
		Acyclic acyclic = new Acyclic();
		
		try{
			acyclic.dependent(A, b);
			fail("应该抛出异常");
		}catch(NotApplicableCaseException e){
			int[][] A2 = new int[][]{
				{ -1, 0, 0 },
				{ -1, 1, 0 },
				{ 0, -1, 1 },
				{ 1, 0, -1 }
			};
			
			int[] b2 = new int[]{ 4, 0, 0, 0 };
			
			//因为涉及到独立变量的约束可以满足，所以还需要继续采用其它测试判定。
			//在抛出异常之前，需要化简A、b矩阵，去掉只涉及独立变量的这部分约束。
			MatrixUtilsTest.equalMatrix(A2, acyclic.A);
			MatrixUtilsTest.equalMatrix(b2, acyclic.b);
		}
	}
	
	/**
	 * Case for<br>
	 * t<sub>1</sub> + 2t<sub>2</sub> - t<sub>3</sub> <= 0<br>
	 */
	public void testForAcyclicAndTrueCase2(){
		int[][] A = new int[][] {
			{-1, -2, 1}
		};
		int[] b = new int[]{0};
		
		assertTrue(new Acyclic().dependent(A, b));
	}
	
	/**
	 * Case for<br>
	 * t<sub>1</sub> + 2t<sub>2</sub> - t<sub>3</sub> <= 0<br>
	 * 0 <= t<sub>1</sub> <= 2<br>
	 * 0 <= t<sub>1</sub> <= 5<br>
	 * -3 <= t<sub>1</sub> <= -1<br>
	 */
	public void testForAcyclicAndFalseCase2(){
		int[][] A = new int[][] {
			{ -1, -2, 1 },
			{ 1, 0, 0 },
			{ -1, 0, 0 },
			{ 1, 0, 0 },
			{ -1, 0, 0 },
			{ 1, 0, 0 },
			{ -1, 0, 0 }
		};
		int[] b = new int[]{ 0, 0, 2, 0, 5, 3, -1 };
		
		assertFalse(new Acyclic().dependent(A, b));
	}
}
