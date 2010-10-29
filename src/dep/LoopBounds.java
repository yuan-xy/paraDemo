package dep;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 表示一个循环中所有循环变量的上下界
 * @author ylt
 *
 */
public class LoopBounds{
	public LoopBounds(){
		list=new ArrayList<LoopBound>();
	}
	public LoopBounds(List<LoopBound> list){
		this.list=list;
	}
	public LoopBounds(List<LoopBound> list,List<String> names){
		this.list=list;
		setNames(names);
	}
	/**
	 * 所有循环变量的上下界的列表，从外层循环变量到内层循环变量。
	 */
	private List<LoopBound> list;
	/**
	 * 循环变量x的名字。注意名称的顺序由矩阵A决定，和上面的list循序不一定相同。
	 */
	private List<String> names;
	
	public void add(LoopBound b){
		list.add(b);
		b.setBounds(this);
	}
	
	public List<LoopBound> getList() {
		return list;
	}
	public void setList(List<LoopBound> list) {
		this.list = list;
	}
	public List<String> getNames() {
		return names;
	}
	public void setNames(List<String> names) {
		assert list.size()==names.size();
		this.names = names;
	}
	
	public LoopBounds setNames(String[] ss){
		setNames(Arrays.asList(ss));
		return this;
	}
	
	/**
	 * 得到循环中第i个变量的名字
	 */
	public String getName(int i){
		if(names==null) return "x"+i;
		return names.get(i);
	}
	
	public void prettyPrint(PrintStream out){
		for(int i=0;i<list.size();i++){
			for(int j=0;j<i;j++) out.print(" ");
			list.get(i).prettyPrint(out);
		}
	}
}