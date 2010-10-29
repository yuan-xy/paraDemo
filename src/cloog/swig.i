%module CLooG
%{
	#include <stdio.h>
	#include <cloog/cloog.h>
%}

%feature("immutable");  
extern const FILE *stdin;
%feature("immutable");  
extern const FILE *stdout;
%feature("immutable");  
extern const FILE *stderr;

extern int printf(const char * _Format, ...);
extern FILE* fopen (const char*, const char*);
extern int fclose(FILE*);


%include <cloog/cloog.h>
%include <cloog/options.h>
%include <cloog/matrix.h>
%include <cloog/domain.h>
%include <cloog/statement.h>
%include <cloog/block.h>
%include <cloog/names.h>
%include <cloog/loop.h>
%include <cloog/program.h>
%include <cloog/pprint.h>
