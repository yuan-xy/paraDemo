/*
 * 
 * Created on 2004-6-15
 */
package util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;

class StreamGobbler extends Thread {
	InputStream is;
	String type;
	StringWriter sw;

	StreamGobbler(InputStream is, String type) {
		this.is = is;
		this.type = type;
		this.sw=new StringWriter();
	}

	public void run() {
		try {
			InputStreamReader isr = new InputStreamReader(is);
			BufferedReader br = new BufferedReader(isr);
			String line = null;
			while ((line = br.readLine()) != null){
				sw.write(line);
				sw.write('\n');
				//GoodWindowsExec.log.debug(type + ">" + line);
			}
		} catch (IOException ioe) {
			throw new RuntimeException(ioe);
		}
	}
}

public class GoodShell {
	
//	public static void main(String[] args){
//		System.out.println(exec("ver"));
//		System.out.println(exec("ipconfig /all"));
//		System.out.println(exec("set"));
//	}
	
	public static boolean isOsWindows(){
		return System.getProperty("os.name").toLowerCase().startsWith("win");
	}
	
	public static boolean isOsWindows2000(){
		return System.getProperty("os.name").equalsIgnoreCase("Windows 2000");
	}
	public static boolean isOsWindowsXP(){
		return System.getProperty("os.name").equalsIgnoreCase("Windows XP");
	}
	
	public static String exec(String commandWin,String commandUnix){
		try {
			String osName = System.getProperty("os.name");
			String[] cmd = new String[3];
			if(isOsWindows()) {
				if(commandWin==null||commandWin.trim().length()==0) return "";
				if (osName.equals("Windows 98")||osName.equals("Windows 95")) {
					cmd[0] = "command.com";
					cmd[1] = "/C";
					cmd[2] = commandWin;
				}else{
					cmd[0] = "cmd.exe";
					cmd[1] = "/C";
					cmd[2] = commandWin;
				}
			}else{
				if(commandUnix==null||commandUnix.trim().length()==0) return "";
				cmd[0] = "sh";
				cmd[1] = "-c";
				cmd[2] = commandUnix;
			}
			Runtime rt = Runtime.getRuntime();
			Process proc = null;
			if(cmd[2].startsWith("\"")){//不是cmd的内部命令
				proc = rt.exec(cmd[2]);
			}else{
				proc = rt.exec(cmd);
			}
			StreamGobbler errorGobbler =
				new StreamGobbler(proc.getErrorStream(), "ERR");
			StreamGobbler outputGobbler =
				new StreamGobbler(proc.getInputStream(), "OUT");
			errorGobbler.start();
			outputGobbler.start();
			int exitVal = proc.waitFor();
			if(errorGobbler.sw.toString().length()>0){
				throw new RuntimeException(errorGobbler.sw.toString());
			}
			return outputGobbler.sw.toString();
		} catch (InterruptedException t) {
			throw new RuntimeException(t);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	public static String exec(String command) {
		return exec(command,command);
	}

}