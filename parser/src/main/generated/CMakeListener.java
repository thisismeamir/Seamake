// Generated from CMake.g4 by ANTLR 4.13.1
package com.github.thisismeamir.seemake.parser;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link CMakeParser}.
 */
public interface CMakeListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link CMakeParser#file_}.
	 * @param ctx the parse tree
	 */
	void enterFile_(CMakeParser.File_Context ctx);
	/**
	 * Exit a parse tree produced by {@link CMakeParser#file_}.
	 * @param ctx the parse tree
	 */
	void exitFile_(CMakeParser.File_Context ctx);
	/**
	 * Enter a parse tree produced by {@link CMakeParser#command_invocation}.
	 * @param ctx the parse tree
	 */
	void enterCommand_invocation(CMakeParser.Command_invocationContext ctx);
	/**
	 * Exit a parse tree produced by {@link CMakeParser#command_invocation}.
	 * @param ctx the parse tree
	 */
	void exitCommand_invocation(CMakeParser.Command_invocationContext ctx);
	/**
	 * Enter a parse tree produced by {@link CMakeParser#single_argument}.
	 * @param ctx the parse tree
	 */
	void enterSingle_argument(CMakeParser.Single_argumentContext ctx);
	/**
	 * Exit a parse tree produced by {@link CMakeParser#single_argument}.
	 * @param ctx the parse tree
	 */
	void exitSingle_argument(CMakeParser.Single_argumentContext ctx);
	/**
	 * Enter a parse tree produced by {@link CMakeParser#compound_argument}.
	 * @param ctx the parse tree
	 */
	void enterCompound_argument(CMakeParser.Compound_argumentContext ctx);
	/**
	 * Exit a parse tree produced by {@link CMakeParser#compound_argument}.
	 * @param ctx the parse tree
	 */
	void exitCompound_argument(CMakeParser.Compound_argumentContext ctx);
}