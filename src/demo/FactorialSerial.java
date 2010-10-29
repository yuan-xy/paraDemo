package demo;

import java.math.BigInteger;

public class FactorialSerial {
	
	public static void main(String[] args) {
		System.out.println(fac(10));
		System.out.println(fac(100));
	}

	private static BigInteger fac(int n) {
		if(n<0) throw new IllegalArgumentException();
		if(n==0) return BigInteger.ZERO;
		BigInteger ret=new BigInteger("1",10);
		for(int i=2;i<=n;i++){
			ret=ret.multiply(new BigInteger(i+"",10));
		}
		return ret;
	}

}
