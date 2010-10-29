package dep;

/*
 * 精确依赖测试的共同接口，包括单变量测试、无环测试和循环留数测试。
 * 为了不和Junit的命名习惯冲突，所以没有取名为ExactDependenceTest
 */
public interface ExactDependenceTst {
	
	/**
	 * 判断Ax+b>=0是否存在整数解。
	 * @param A
	 * @param b
	 * @return 如果存在解返回true，如果不存在解返回false。
	 * 		   如果该算法无法判断，抛出NotApplicableCaseException异常。
	 * @throws NotApplicableCaseException 
	 */
	public boolean dependent(int[][] A,int[] b) throws NotApplicableCaseException;

}

class NotApplicableCaseException extends RuntimeException{
	
}