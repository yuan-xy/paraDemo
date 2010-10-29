package dep;

import junit.framework.TestCase;

public class LoopResidueTest extends TestCase{
	
	/**
	 * Case for<br>
	 * -1 <= t<sub>1</sub> <= 10<br>
	 * -1 <= t<sub>2</sub> <= 10<br>
	 * 0 <= t<sub>3</sub> <= 4<br>
	 * t<sub>2</sub> <= t<sub>1</sub> <br>
	 * 2t<sub>1</sub> <= 2t<sub>3</sub> - 7<br>
	 */
	public void testForCyclicAndTrueCase() {
		int[][] A = {{1,0,0},{-1,0,0},{0,1,0},
				{0,-1,0},{0,0,1},{0,0,-1},
				{1,-1,0},{-2,0,2}};
		int[] b = {1,10,1,10,0,4,0,-7};
		
		LoopResidue test = new LoopResidue();
		this.assertTrue(test.dependent(A, b));
		
	}
	
	/**
	 * Case for<br>
	 * 1 <= t<sub>1</sub> <= 10<br>
	 * 1 <= t<sub>2</sub> <= 10<br>
	 * 0 <= t<sub>3</sub> <= 4<br>
	 * t<sub>2</sub> <= t<sub>1</sub> <br>
	 * 2t<sub>1</sub> <= 2t<sub>3</sub> - 7<br>
	 */
	public void testForCyclicAndFalseCase() {
		int[][] A = {{1,0,0},{-1,0,0},{0,1,0},
				{0,-1,0},{0,0,1},{0,0,-1},
				{1,-1,0},{-2,0,2}};
		int[] b = {-1,10,-1,10,0,4,0,-7};
		
		LoopResidue test = new LoopResidue();
		this.assertFalse(test.dependent(A, b));
	}

	/**
	 * Case for<br>
	 * 1 <= t<sub>1</sub> <= 10<br>
	 * 1 <= t<sub>2</sub> <= 10<br>
	 * 0 <= t<sub>3</sub> <= 4<br>
	 * t<sub>2</sub> <= t<sub>1</sub>-10 <br>
	 * 2t<sub>1</sub> <= -2t<sub>3</sub> - 7<br>
	 */	
	public void testForNAButFalseCase() {
		int[][] A = {{1,0,0},{-1,0,0},{0,1,0},
				{0,-1,0},{0,0,1},{0,0,-1},
				{1,-1,0},{-2,0,-2}};
		int[] b = {-1,10,-1,10,0,4,-10,-7};
		
		LoopResidue test = new LoopResidue();
		this.assertFalse(test.dependent(A, b));
	}
	
	/**
	 * Case for<br>
	 * -1 <= t<sub>1</sub> <= 10<br>
	 * -1 <= t<sub>2</sub> <= 10<br>
	 * 0 <= t<sub>3</sub> <= 4<br>
	 * t<sub>2</sub> <= t<sub>1</sub> <br>
	 * 2t<sub>1</sub> <= -2t<sub>3</sub> - 7<br>
	 */
	public void testForNAExceptionCase1() {
		int[][] A = {{1,0,0},{-1,0,0},{0,1,0},
				{0,-1,0},{0,0,1},{0,0,-1},
				{1,-1,0},{-2,0,-2}};
		int[] b = {1,10,1,10,0,4,0,-7};
		
		LoopResidue test = new LoopResidue();
		try{
			test.dependent(A, b);		
			fail("应该抛出异常");
		}catch(NotApplicableCaseException e){
			System.out.println("1:Not Applicable!");
		}
	}

	/**
	 * Case for<br>
	 * -1 <= t<sub>1</sub> <= 10<br>
	 * -1 <= t<sub>2</sub> <= 10<br>
	 * 0 <= t<sub>3</sub> <= 4<br>
	 * t<sub>2</sub> <= t<sub>1</sub> <br>
	 * 2t<sub>1</sub> <= 3t<sub>3</sub> - 7<br>
	 */
	public void testForNAExceptionCase2() {
		int[][] A = {{1,0,0},{-1,0,0},{0,1,0},
				{0,-1,0},{0,0,1},{0,0,-1},
				{1,-1,0},{-2,0,3}};
		int[] b = {1,10,1,10,0,4,0,-7};
		
		LoopResidue test = new LoopResidue();
		try{
			test.dependent(A, b);		
			fail("应该抛出异常");
		}catch(NotApplicableCaseException e){
			System.out.println("2:Not Applicable!");
		}
	}
}
