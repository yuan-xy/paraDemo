package polylib;

import jniUtil.CPointer;
import junit.framework.TestCase;
import util.MatrixUtilsTest;

public class PolylibTest  extends TestCase{
	
	public static void test2(){
		Matrix con=Polylib.newMatrix(new int[][]{{1,-2,-3,5}});
		Polyhedron po=Polylib.Constraints2Polyhedron(con, 20);
		Matrix rays=Polylib.Polyhedron2Rays(po);
		Polylib.Matrix_Print(Polylib.getStdout(), "%4d", rays);
	}
	
	public static void test3(){
		Matrix con=Polylib.newMatrix(new int[][]{{0,0,0,7,-4},{1,-2,-3,0,5},{1,0,0,0,1}});
		Polyhedron po=Polylib.Constraints2Polyhedron(con, 20);
		Matrix rays=Polylib.Polyhedron2Rays(po);
		Polylib.Matrix_Print(Polylib.getStdout(), "%4d", rays);
	}
	
	public static void test4(){
		Matrix con=Polylib.newMatrix(new int[][]{{1,0,-1,0,0},{0,1,0,-1,1}});
		long t1=System.currentTimeMillis();
		int i=Polylib.Gauss(con, 2, 5);
		long t2=System.currentTimeMillis();
		System.out.println(t2-t1);
		Polylib.Matrix_Print(Polylib.getStdout(), "%4d", con);
	}
	
	public static void test(){
		 int[][] aa={{1,0,1,-1},{1,-1,0,6},{1,0,-1,7},{1,1,0,-2}};
	     Matrix a=Polylib.newMatrix(aa);
	     long p=SWIGTYPE_p_int.getCPtr(a.getP_Init());
	     CPointer cp=new CPointer(p);
	     int[] buf=new int[16];
	     cp.copyOut(0, buf, 0, 16);
	     int[] buf2=new int[16];
	     for(int i=0,k=0;i<4;i++){
	    	 for(int j=0;j<4;j++){
	    		 buf2[k++]=aa[i][j];
	    	 }
	     }
	     MatrixUtilsTest.equalMatrix(buf, buf2);

		 int[][] bb={{1,1,0,-1},{1,-1,0,3},{1,0,-1,5},{1,0,1,-2}};
	     Matrix b=Polylib.newMatrix(bb);
	     Polyhedron pa=Polylib.Constraints2Polyhedron(a, 200);
	     Matrix mm=Polylib.Polyhedron2Rays(pa);
	     Polylib.Matrix_Print(Polylib.getStdout(), "%4d", mm);
//	     Polyhedron pb=Polylib.Constraints2Polyhedron(b, 200);
//	     Polylib.Matrix_Free(a);
//	     Polylib.Matrix_Free(b);
//	     a=Polylib.Polyhedron2Constraints(pa);
//	     b=Polylib.Polyhedron2Constraints(pb);
//	     Polylib.printf("\na ="); 
//	     Polylib.Matrix_Print(Polylib.getStdout(), "%4d", a);
//	     Polylib.printf("\nb ="); 
//	     Polylib.Matrix_Print(Polylib.getStdout(), "%4d", b);
//	     Polyhedron pc=Polylib.DomainIntersection(pa, pb, 200);
//	     Polylib.printf("\nc = [a and b] ="); 
//	     Polylib.Polyhedron_Print(Polylib.getStdout(), "%4d", pc);
//	     
//		 int[][] dd={{0,1,0},{1,0,0},{0,0,1}};
//	     Matrix d=Polylib.newMatrix(dd);
//	     Polyhedron pd=Polylib.Polyhedron_Preimage(pc, d, 200);
//	     Polylib.Matrix_Free(d);
//	     Polylib.printf("\nd = transformed c ="); 
//	     Polylib.Polyhedron_Print(Polylib.getStdout(), "%4d", pd);
//	     Polylib.Domain_Free(pd);
//	     assertTrue(Polylib.PolyhedronIncludes(pa, pc)!=0);
//	     if(Polylib.PolyhedronIncludes(pa, pc)!=0){
//	    	 Polylib.printf("\n a include c."); 
//	     }
//	     assertTrue(Polylib.PolyhedronIncludes(pc, pb)==0);
//	     if(Polylib.PolyhedronIncludes(pc, pb)==0){
//	    	 Polylib.printf("\n c not include b."); 
//	     }
//	     Polylib.Domain_Free(pa);
//	     Polylib.Domain_Free(pb);
//	     Polylib.Domain_Free(pc);
	}

	public static void main(String[] args) {
		test();
	}

}

/*
4 4
1 0 1 -1
1 -1 0 6
1 0 -1 7
1 1 0 -2
4 4
1 1 0 -1
1 -1 0 3
1 0 -1 5
1 0 1 -2
3 3
0 1 0
1 0 0
0 0 1
*/
