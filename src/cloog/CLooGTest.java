package cloog;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;

import junit.framework.TestCase;

public class CLooGTest extends TestCase {

	public void test() throws URISyntaxException {
		cloogoptions opt = CLooG.cloog_options_malloc();
		URL url=CLooGTest.class.getResource("11_3_4.cloog.txt");
		File f=new File(url.toURI());
		SWIGTYPE_p_FILE cf=CLooG.fopen(f.getAbsolutePath(), "r");
		cloogprogram prog = CLooG.cloog_program_read(cf, opt);
		prog = CLooG.cloog_program_generate(prog, opt);
		CLooG.cloog_program_pprint(CLooG.getStdout(), prog, opt);
		CLooG.fclose(cf);
		/*
		 * 
for (j=3;j<=38;j++) {
  for (i=1;i<=min(j-2,-j+39);i++) {
    S1(i = i,j = j) ;
  }
}
		 */
		CLooG.cloog_options_free(opt);
		CLooG.cloog_program_free(prog);
	}
	
	public static void main(String[] args){
		System.out.format("release:%s,version:%s\n",CLooGJNI.CLOOG_RELEASE_get(),CLooGJNI.CLOOG_VERSION_get());
		cloogoptions opt = CLooG.cloog_options_malloc();
		cloogprogram prog = CLooG.cloog_program_read(CLooG.getStdin(), opt);
		prog = CLooG.cloog_program_generate(prog, opt);
		CLooG.cloog_program_pprint(CLooG.getStdout(), prog, opt);
		CLooG.cloog_options_free(opt);
		CLooG.cloog_program_free(prog);
	}

}
