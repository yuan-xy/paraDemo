	.text
.globl asm_dispatch
	.type	asm_dispatch, @function
asm_dispatch:
	pushl	%ebp
	movl	%esp, %ebp

	movl	20(%ebp), %esi
	movl	12(%ebp), %edx
	shl $2,%edx
    sub $4,%edx
	jc  args_done
args_loop:
	movl (%esi,%edx), %eax
	push %eax
	sub $4,%edx
	jge args_loop
args_done:
	call *8(%ebp)
	mov 32(%ebp), %edx
    or %edx, %edx
	jnz is_stdcall

	mov 12(%ebp), %edx
    shl $2, %edx
    add %edx, %esp

is_stdcall:
    mov 28(%ebp),%esi
	mov 24(%ebp),%edx
	dec %edx
	jge not_p64

	mov %eax, (%esi)
	movl $0, 4(%esi)
	jmp done
not_p64:
	dec %edx
jge not_i32
	mov %eax, (%esi)
    jmp done
not_i32:
	dec %edx
    jge not_f32

	fstpl (%esi)
	jmp done

not_f32:
	fstp (%esi)
done:

	leave
	ret
	.size	asm_dispatch, .-asm_dispatch
	.ident	"GCC: (GNU) 4.1.2 20070502 (Red Hat 4.1.2-12)"
	.section	.note.GNU-stack,"",@progbits
