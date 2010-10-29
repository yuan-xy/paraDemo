package demo;

/**
 * Author Ethan 
 */
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class FactorialParallel implements Runnable {

	public static List<BigInteger> resultList = new ArrayList<BigInteger>();
	public static int threadCon = 1;

	private int _startInteger = 0;
	private int _endInteger = 0;
	private int _threadIndex = -1;

	public FactorialParallel(int startInteger, int endInteger, int threadIndex) {
		_startInteger = startInteger;
		_endInteger = endInteger;
		_threadIndex = threadIndex;
	}

	public void run() {
		BigInteger ret = new BigInteger(_startInteger + "", 10);
		for (int i = _startInteger + 1; i <= _endInteger; i++) {
			ret = ret.multiply(new BigInteger(i + "", 10));
		}
		System.out.println("The result of thread " + _threadIndex + " is "
				+ ret);
		synchronized (resultList) {
			resultList.add(ret);
			if (resultList.size() == threadCon)
				resultList.notify();
		}
	}

	public static void main(String[] args) {
		int n = 100;
		int tmpCon = 0;
		Thread newOne;
		List<Thread> tList = new ArrayList<Thread>();
		while (tmpCon + 10 <= n) {
			newOne = new Thread(new FactorialParallel(tmpCon + 1, tmpCon + 10,
					threadCon++));
			tList.add(newOne);
			tmpCon += 10;
		}
		if (tmpCon < n) {
			newOne = new Thread(new FactorialParallel(tmpCon + 1, n, threadCon));
			tList.add(newOne);
		} else {
			threadCon--;
		}

		for (int i = 0; i < tList.size(); i++)
			tList.get(i).start();

		System.out.print(mergeResult());
	}

	public static BigInteger mergeResult() {

		BigInteger finalResult = new BigInteger("1", 10);

		synchronized (resultList) {
			if (resultList.size() != threadCon) {
				try {
					resultList.wait();
				} catch (InterruptedException e) {
				}
			}
		}

		if (resultList.size() > 0) {
			for (int i = 0; i < resultList.size(); i++) {
				finalResult = finalResult.multiply(resultList.get(i));
			}
			return finalResult;
		} else {
			return BigInteger.ZERO;
		}
	}
}
