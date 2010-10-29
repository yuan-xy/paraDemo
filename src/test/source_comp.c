 void main()
 {
		int i,j;
		int[][] a = new int[100][100];
		int[][] b = new int[100][100];
		
		for(i=0; i < 100; i++ )
			for(j=0; j < 100; j++ )
			{
				a[i][j] = j;
				b[i][j] = i;
			}
		
		for(i=1; i < 100; i++)
			for(j=1; j < 100; j++){
				a[i][j] = a[i][j] + b[i-1][j];
				b[i][j] = b[i][j] + a[i][j-1];
			}
}