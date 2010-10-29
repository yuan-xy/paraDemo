package jniUtil;

import junit.framework.TestCase;

public class JNITest extends TestCase {
	
	public void testLibc(){
		CFunction time = new CFunction(libc(), "time");
		int ires = time.callInt(new Object[]{ null });
		long cur=System.currentTimeMillis();
		System.out.println("\ntime() reports seconds since 1/1/70 as " + ires);
		System.out.println("current milli-seconds since  1/1/70 is "+cur);
		String s1=ires+"";
		String s2=cur+"";
		assertTrue(s2.indexOf(s1)!=-1);
	}
	
	public void testLibm(){
		CFunction sin = new CFunction(libm(), "sin");
		double dres = sin.callDouble(new Object[]{new Double(2.0) });
		double d=Math.sin(2.0d);
		double difference=d-dres;
		System.out.println("\nc: "+dres+",java: "+d+",difference is "+difference);
		assertTrue(difference<0.01);
	}

	public static String libm(){
		String osName = System.getProperty("os.name");
		if(osName.toLowerCase().startsWith("win")){
			return "msvcrt.dll";
		}else{
		    return "libm.so";
		}
	}
	
	public static String libc(){
		String osName = System.getProperty("os.name");
		if(osName.toLowerCase().startsWith("win")){
			return "msvcrt.dll";
		}else{
		    return "libc.so";
		}
	}
	
}
