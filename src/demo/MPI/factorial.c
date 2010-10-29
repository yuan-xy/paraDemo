#include "mpi.h"
#include <stdio.h>

int factorial(int numprocs,int myid){
	int n=12;
	int myfac=1;
	int span=n/numprocs;
	int from=myid*span+1;
	int to=(myid+1)*span;
	int i;
	for(i=from;i<=to;i++) myfac*=i;
	return myfac;
}

int main(int argc,char *argv[])
{
	int numprocs,myid;
	int  namelen;
    char processor_name[MPI_MAX_PROCESSOR_NAME];
	int fac=0,myfac;
    MPI_Init(&argc,&argv);
    MPI_Comm_size(MPI_COMM_WORLD,&numprocs);
    MPI_Comm_rank(MPI_COMM_WORLD,&myid);
    MPI_Get_processor_name(processor_name,&namelen);

    fprintf(stdout,"Process %d of %d on %s\n",myid, numprocs, processor_name);
	fflush( stdout );

	myfac=factorial(numprocs,myid);
	fprintf(stdout,"processor:%d factorial part = %d\n",myid,myfac);
	fflush( stdout );
	MPI_Reduce(&myfac, &fac, 1, MPI_INT, MPI_PROD, 0, MPI_COMM_WORLD);
	if(myid==0){
		printf("12! = %d\n",fac);
	}
		
    MPI_Finalize();
    return 0;
}

