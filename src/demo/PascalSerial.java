package demo;

public class PascalSerial {
	
	public static int[][] pascal=new int[100][100];

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		for(int i=0;i<100;i++){
			pascal[i][0]=1;
			pascal[i][i]=1;
		}
		for(int i=2;i<100;i++){
			for(int j=1;j<i;j++){
				pascal[i][j]=pascal[i-1][j-1]+pascal[i-1][j];
			}
		}
		for(int i=0;i<pascal.length;i++){
			System.out.println("\n");
			for(int j=0;j<pascal.length;j++){
				System.out.print(pascal[i][j]+",");
			}
		}
	}

}
