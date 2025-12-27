// Generated from CMake.g4 by ANTLR 4.13.2
package com.iskportal.koly.parsers;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link CMakeParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface CMakeVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link CMakeParser#file_}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFile_(CMakeParser.File_Context ctx);
	/**
	 * Visit a parse tree produced by {@link CMakeParser#command_invocation}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCommand_invocation(CMakeParser.Command_invocationContext ctx);
	/**
	 * Visit a parse tree produced by {@link CMakeParser#single_argument}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSingle_argument(CMakeParser.Single_argumentContext ctx);
	/**
	 * Visit a parse tree produced by {@link CMakeParser#compound_argument}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCompound_argument(CMakeParser.Compound_argumentContext ctx);
}