#ifdef _OPENMP
	#include <omp.h>
#else
	#define omp_get_thread_num() 0
#endif

#include <stdio.h>

int main(void){
	int i,n=10;
	int fac=1;
#pragma omp parallel for shared(n) private(i) reduction(*:fac)
	for(i=1;i<=n;i++){
		fac*=i;
	}
	printf("Thread%d - fac(%d)=%d\n",omp_get_thread_num(),n,fac);
	return 0;
}
