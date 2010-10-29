package demo;

import java.util.ArrayList;
import java.util.List;

public class PascalParallel implements Runnable {

	public static PackInteger[][] matrix;

	public static int n;

	public static PackInteger finishedNum = new PackInteger();

	public static List<Thread> tList = new ArrayList<Thread>();

	private int threadIndex;

	public void run() {
		synchronized (matrix[threadIndex][threadIndex]) {
			matrix[threadIndex][threadIndex].value = 1;
		}

		if (threadIndex < 9) {
			PascalParallel pp = new PascalParallel();
			pp.setThreadIndex(threadIndex + 1);
			Thread next = new Thread(pp);
			next.start();
		}

		if (threadIndex == 0) {
			for (int i = threadIndex + 1; i < n; i++) {
				synchronized (matrix[i][0]) {
					matrix[i][0].value = 1;
					matrix[i][0].notifyAll();
				}
			}
		} else {
			for (int i = threadIndex + 1; i < n; i++) {
				synchronized (matrix[i - 1][threadIndex - 1]) {
					if (matrix[i - 1][threadIndex - 1].value == -1)
						try {
							matrix[i - 1][threadIndex - 1].wait();
						} catch (InterruptedException e) {
						}
				}

				synchronized (matrix[i][threadIndex]) {
					matrix[i][threadIndex].value = matrix[i - 1][threadIndex].value
							+ matrix[i - 1][threadIndex - 1].value;
					matrix[i][threadIndex].notifyAll();
				}
			}
		}

		synchronized (finishedNum) {
			finishedNum.value++;
			if (finishedNum.value == n)
				finishedNum.notify();
		}
	}

	public static void main(String[] args) {
		n = 10;
		matrix = new PackInteger[n][n];
		for (int i = 0; i < n; i++)
			for (int j = 0; j < n; j++) {
				matrix[i][j] = new PackInteger();
				matrix[i][j].value = -1;
			}

		PascalParallel pp = new PascalParallel();
		pp.setThreadIndex(0);
		Thread first = new Thread(pp);
		first.start();

		synchronized (finishedNum) {
			if (finishedNum.value != n) {
				try {
					finishedNum.wait();
				} catch (InterruptedException e) {
				}
			}
		}

		print();
	}

	public int getThreadIndex() {
		return threadIndex;
	}

	public void setThreadIndex(int threadIndex) {
		this.threadIndex = threadIndex;
	}

	private static void print() {
		for (int i = 0; i < n; i++) {
			for (int j = 0; j <= i; j++)
				System.out.print(matrix[i][j].value + "  ");
			System.out.println();
		}
	}
}

class PackInteger {
	public int value;
}
