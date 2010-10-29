package dep;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

/**
 * 表示单个循环变量的上下界
 * @author ylt
 *
 */
public class LoopBound{
	public LoopBound(int xi){
		this.i=xi;
		lower=new ArrayList<int[]>();
		upper=new ArrayList<int[]>();
	}
	/**
	 * 变量x的索引位置
	 */
	private int i;
	/**
	 * 变量的下界的列表，每一个下界用一个int[]数组表示,依次为[ai,a0,a1,...an,b],表示aixi>=a0x0+a1x1+...+anxn+b.
	 */
	private List<int[]> lower;
	/**
	 * 变量的上界的列表，每一个上界用一个int[]数组表示,依次为[ai,a0,a1,...an,b],表示aixi<=a0x0+a1x1+...+anxn+b.
	 */
	private List<int[]> upper;
	/**
	 * 所处的整个循环
	 */
	private LoopBounds bounds;

	public LoopBounds getBounds() {
		return bounds;
	}

	public void setBounds(LoopBounds bounds) {
		this.bounds = bounds;
	}
	public int getI() {
		return i;
	}
	public List<int[]> getLower() {
		return lower;
	}
	public List<int[]> getUpper() {
		return upper;
	}

	/**
	 * 变量x的名字
	 */
	public String getName(){
		return bounds.getName(i);
	}
	
	public void prettyPrint(PrintStream out){
		out.format("for(int %s=%s;%s<=%s;%s++)\n", getName(),printLower(),getName(),printUpper(),getName());
	}
	
	private String printUpper(){
		if(upper.size()==0) throw new RuntimeException("循环变量"+getName()+"无上界。");
		if(upper.size()==1) return print(upper.get(0));
		StringBuffer sb = new StringBuffer("min(");
		for(int[] ii : upper){
			sb.append(print(ii));
			sb.append(',');
		}
		removeLast(sb,',');
		sb.append(")");
		return sb.toString();
	}
	
	private String printLower(){
		if(lower.size()==0) throw new RuntimeException("循环变量"+getName()+"无下界。");
		if(lower.size()==1) return print(lower.get(0));
		StringBuffer sb = new StringBuffer("max(");
		for(int[] ii : lower){
			sb.append(print(ii));
			sb.append(',');
		}
		removeLast(sb,',');
		sb.append(")");
		return sb.toString();
	}
	
	private String print(int[] ii){
		StringBuffer sb = new StringBuffer();
		for(int j=1;j<ii.length-1;j++){
			if(ii[j]==0) continue;
			if(ii[j]!=1){
				if(ii[j]>0){
					sb.append(ii[j]);
					sb.append('*');
				}else{
					if(sb.length()>0){
						assert sb.charAt(sb.length()-1)=='+';
						sb.deleteCharAt(sb.length()-1);
						sb.append(ii[j]);
						sb.append('*');
					}else{
						sb.append(ii[j]);
						sb.append('*');
					}
				}
			}
			sb.append(bounds.getName(j-1));
			sb.append('+');
		}
		if(ii[ii.length-1]!=0){
			int i=ii[ii.length-1];
			if(i>0){
				sb.append(i);
			}else{
				if(sb.length()>0){
					assert sb.charAt(sb.length()-1)=='+';
					sb.deleteCharAt(sb.length()-1);
					sb.append(i);
				}else{
					sb.append(i);
				}
			}
		}
		removeLast(sb,'+');
		if(sb.length()==0) return "0";
		if(ii[0]==1) return sb.toString();
		StringBuffer sb2 = new StringBuffer("(");
		sb2.append(sb);
		sb.append(")/");
		sb.append(ii[0]);
		return sb.toString();
	}
	
	private void removeLast(StringBuffer sb,char c) {
		if(sb.length()==0) return;
		if(sb.charAt(sb.length()-1)==c) sb.deleteCharAt(sb.length()-1);
	}
	
}
