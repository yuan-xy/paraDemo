#ifdef _OPENMP
	#include <omp.h>
#else
	#define omp_get_thread_num() 0
#endif

#include <stdio.h>
#include <stdlib.h>


int main(void){
	int i,j,TID,n=20;
	int pascal[n][n];
#pragma omp parallel for
	for(int i=0;i<n;i++){
		for(j=0;j<n;j++)
			pascal[i][j]=0;
	}
#pragma omp parallel for
	for(i=0;i<n;i++){
		pascal[i][0]=1;
		pascal[i][i]=1;
	}



#ifdef _OPENMP
#pragma omp parallel num_threads(n) shared(n) private(TID,i)
{
	TID=omp_get_thread_num();
	printf("TID:%d\n",TID);
	for(i=2;i<n;i++){
		#pragma omp barrier
		pascal[i][TID]=pascal[i-1][TID-1]+pascal[i-1][TID];
		system("sleep 0.1");
	}
}
#else
	for(i=2;i<n;i++){
		for(j=1;j<i;j++){
			pascal[i][j]=pascal[i-1][j-1]+pascal[i-1][j];
		}
	}
#endif


	for(i=0;i<n;i++){
		for(j=0;j<n;j++){
			printf("%d,",pascal[i][j]);
		}
		printf("\n");
	}
	return 0;
}

