package polylib;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;

import util.GoodShell;

public class CopyDll {

	public static void copy(){
		if(GoodShell.isOsWindows()){
			URL url=CopyDll.class.getResource("polylib32.dll");
			try {
				File f=new File(url.toURI());
				String s="copy \""+f.getAbsolutePath()+"\" %windir%";
				System.out.println(s);
				GoodShell.exec(s);
			} catch (URISyntaxException e) {
				e.printStackTrace();
			}
		}else{
			URL url=CopyDll.class.getResource("libpolylib32.so");
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
