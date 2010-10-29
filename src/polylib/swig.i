%module Polylib
%{
	#include <stdio.h>
	#include <polylib/polylib32.h>
%}

%feature("immutable");  
extern const FILE *stdin;
%feature("immutable");  
extern const FILE *stdout;
%feature("immutable");  
extern const FILE *stderr;

extern int printf(const char * _Format, ...);


#define POLYLIB_BITS 32
#define LINEAR_VALUE_IS_INT

%include <polylib/arithmetique.h>
%include <polylib/arithmetic_errors.h>
%include <polylib/types.h>
%include <polylib/errormsg.h>
%include <polylib/vector.h>
%include <polylib/matrix.h>
%include <polylib/polyhedron.h>
%include <polylib/polyparam.h>
%include <polylib/param.h>
%include <polylib/alpha.h>
%include <polylib/ehrhart.h>
%include <polylib/ext_ehrhart.h>
%include <polylib/eval_ehrhart.h>
%include <polylib/SolveDio.h>
%include <polylib/Lattice.h>
%include <polylib/Matop.h>
%include <polylib/NormalForms.h>
%include <polylib/Zpolyhedron.h>

%include <polylib/matrix_addon.h>
%include <polylib/matrix_permutations.h>
%include <polylib/compress_parms.h>

#ifdef GNUMP
%include <gmp.h>
#endif
