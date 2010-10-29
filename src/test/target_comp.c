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
		
		#pragma omp parallel num_threads(200)		
		for(i=1; i < 100; i++)
			for(j=1; j < 100; j++)
			{
				if( (omp_get_num_threads()-100) == (i-j-1) )
					a[i][j] = a[i][j] + b[i-1][j];
				if( (omp_get_num_threads()-100) == (i-j) )
					b[i][j] = b[i][j] + a[i][j-1];
			}

		/*
		for(p=-100; p <= 99; p++)
			for(j=1; j < 100; j++)
			{
				if( p == i-j-1 )
					a[i][j] = a[i][j] + b[i-1][j];
				if( p == i-j )
					b[i][j] = b[i][j] + a[i][j-1];
			}
		*/
}