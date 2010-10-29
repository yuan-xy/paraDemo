package cloog;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;

import util.GoodShell;

public class CopyDllCloog {
	
	public static void copy(){
		if(GoodShell.isOsWindows()){
			URL url=CopyDllCloog.class.getResource("cloog.dll");
			try {
				File f=new File(url.toURI());
				String s="copy \""+f.getAbsolutePath()+"\" %windir%\\system32";
				System.out.println(s);
				GoodShell.exec(s);
			} catch (URISyntaxException e) {
				e.printStackTrace();
			}
		}else{
			URL url=CopyDllCloog.class.getResource("libcloog.so");
			try {
				File f=new File(url.toURI());
				String s="cp \""+f.getAbsolutePath()+"\" /usr/lib";
				System.out.println(s);
				GoodShell.exec(s);
			} catch (URISyntaxException e) {
				e.printStackTrace();
			}
		}
	}

}
