package ap;

import util.MatrixUtils;

public class InEquation {

	public int[][] solve(int[][] A){
		if(A.length==0||A[0].length==0) return MatrixUtils.ZeroMatrix;
		int[][] M=MatrixUtils.transpose(A);
		int r0=0;
		int c0=0;
		int[][] B=MatrixUtils.eye(A[0].length);
		while(true){
			//step 1
			int r2=r0;
			int c2=c0;
			int rn;
			while(existsM(M,r2,c2)){
				MatrixUtils.columnExchange(M, c0, c2);
				MatrixUtils.rowExchange(M, r0, r2);
				MatrixUtils.rowExchange(B, r0, r2);
				if(M[r2][c2]<0){
					MatrixUtils.scalarOperation(M[r2], -1, '*');
					MatrixUtils.scalarOperation(B[r2], -1, '*');
				}
				for(int row=r0;row<M.length;row++){
					if(row!=r2&&M[row][r2]!=0){
						int u=-(M[row][r2]/M[r2][c2]);
						MatrixUtils.unimodular3(M, row, r2, u);
						MatrixUtils.unimodular3(B, row, r2, u);
					}
				}
				r2+=1;
				c2+=1;
			}
			//step 2
			boolean noMoreSoln=false;
			for(int row=r0;row<r2-1;row++){
				for(int col=c2;col<M[0].length;col++){
					if(M[row][col]<0) noMoreSoln=true;
				}
			}
			if(!noMoreSoln){
				M[r2]=MatrixUtils.add(M[r0], M[r0+1]);
				for(int i=r0+2;i<r2-1;i++) M[r2]=MatrixUtils.add(M[r2], M[i]);
			}
			//step 3
			if(noMoreSoln){
				for(int r=r2;r<M.length;r++){
					MatrixUtils.rowExchange(M, r, r0+r-r2);
					MatrixUtils.rowExchange(B, r, r0+r-r2);
				}
				rn=r0+M.length-r2+1;
			}else{
				rn=M.length+1;
				for(int col=c2;col<M[0].length;col++){
					boolean hasRowNegtive=false;
					boolean hasRowPositive=false;
					for(int row=r0;row<M.length;row++){
						if(M[row][col]<0){
							hasRowNegtive=true;
							break;
						}
					}
					int r;
					for(r=r0;r<M.length;r++){
						if(M[r][col]<0){
							hasRowNegtive=true;
							break;
						}
					}
					if(hasRowNegtive){
						if(hasRowPositive){
							for(int row=r0;row<rn-1;row++){
								if(M[row][col]<0){
									int u=(int) Math.ceil(-M[row][col]/M[r][col]);
									MatrixUtils.unimodular3(M, row, r, u);
									MatrixUtils.unimodular3(B, row, r, u);
								}
							}
						}else{
							for(int row=rn-2;row>=r0;row--){
								if(M[row][col]<0){
									rn=rn-1;
									MatrixUtils.unimodular2(M, row, rn);
									MatrixUtils.unimodular2(B, row, rn);
								}
							}
						}
					}
				}
				
			}
			//step 4
			for(int row=r0;row<rn-1;row++){
				for(int col=1;col<c0-1;col++){
					if(M[row][col]<0){
						int r;
						for(r=0;r<r0-1;r++){
							if(M[r][col]>0) break;
						}
						int u=(int) Math.ceil(-M[row][col]/M[r][col]);
						MatrixUtils.unimodular3(M, row, r, u);
						MatrixUtils.unimodular3(B, row, r, u);
					}
				}
			}
			//setp 5
			if(noMoreSoln || rn>M.length || rn==r0){
				int i=M.length-rn+1;
				int[][] ret=new int[B.length-i][B.length];
				for(int j=0;j<ret.length;j++) ret[j]=B[j];
				return ret;
			}else{
				int cn = M[0].length;
				for(int col=M[0].length-1;col>=0;col--){
					boolean noPositive=false;
					int r=0;
					for(;r<rn-1;r++){
						if(M[r][col]>0) break;
					}
					if(r==rn) noPositive=true;
					if(noPositive){
						cn--;
						MatrixUtils.unimodular2(M, col, cn);
					}
				}
				r0=rn;
				c0=cn;
			}
			return B;
		}
	}
	
	private boolean existsM(int[][] M,int r2,int c2){
		for(int i=r2;i<M.length;i++){
			for(int j=c2;j<M[0].length;j++){
				if(M[i][j]!=0) return true;
			}
		}
		return false;
	}
}
