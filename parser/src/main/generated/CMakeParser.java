// Generated from CMake.g4 by ANTLR 4.13.2
package io.github.thisismeamir.seamake.parser;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast", "CheckReturnValue", "this-escape"})
public class CMakeParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.13.2", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, Identifier=3, Unquoted_argument=4, Escape_sequence=5, 
		Quoted_argument=6, Bracket_argument=7, Bracket_comment=8, Line_comment=9, 
		Newline=10, Space=11;
	public static final int
		RULE_file_ = 0, RULE_command_invocation = 1, RULE_single_argument = 2, 
		RULE_compound_argument = 3;
	private static String[] makeRuleNames() {
		return new String[] {
			"file_", "command_invocation", "single_argument", "compound_argument"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'('", "')'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, null, null, "Identifier", "Unquoted_argument", "Escape_sequence", 
			"Quoted_argument", "Bracket_argument", "Bracket_comment", "Line_comment", 
			"Newline", "Space"
		};
	}
	private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}

	@Override
	public String getGrammarFileName() { return "CMake.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public CMakeParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@SuppressWarnings("CheckReturnValue")
	public static class File_Context extends ParserRuleContext {
		public TerminalNode EOF() { return getToken(CMakeParser.EOF, 0); }
		public List<Command_invocationContext> command_invocation() {
			return getRuleContexts(Command_invocationContext.class);
		}
		public Command_invocationContext command_invocation(int i) {
			return getRuleContext(Command_invocationContext.class,i);
		}
		public File_Context(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_file_; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CMakeListener ) ((CMakeListener)listener).enterFile_(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CMakeListener ) ((CMakeListener)listener).exitFile_(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CMakeVisitor ) return ((CMakeVisitor<? extends T>)visitor).visitFile_(this);
			else return visitor.visitChildren(this);
		}
	}

	public final File_Context file_() throws RecognitionException {
		File_Context _localctx = new File_Context(_ctx, getState());
		enterRule(_localctx, 0, RULE_file_);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(11);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==Identifier) {
				{
				{
				setState(8);
				command_invocation();
				}
				}
				setState(13);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(14);
			match(EOF);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class Command_invocationContext extends ParserRuleContext {
		public TerminalNode Identifier() { return getToken(CMakeParser.Identifier, 0); }
		public List<Single_argumentContext> single_argument() {
			return getRuleContexts(Single_argumentContext.class);
		}
		public Single_argumentContext single_argument(int i) {
			return getRuleContext(Single_argumentContext.class,i);
		}
		public List<Compound_argumentContext> compound_argument() {
			return getRuleContexts(Compound_argumentContext.class);
		}
		public Compound_argumentContext compound_argument(int i) {
			return getRuleContext(Compound_argumentContext.class,i);
		}
		public Command_invocationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_command_invocation; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CMakeListener ) ((CMakeListener)listener).enterCommand_invocation(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CMakeListener ) ((CMakeListener)listener).exitCommand_invocation(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CMakeVisitor ) return ((CMakeVisitor<? extends T>)visitor).visitCommand_invocation(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Command_invocationContext command_invocation() throws RecognitionException {
		Command_invocationContext _localctx = new Command_invocationContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_command_invocation);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(16);
			match(Identifier);
			setState(17);
			match(T__0);
			setState(22);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & 218L) != 0)) {
				{
				setState(20);
				_errHandler.sync(this);
				switch (_input.LA(1)) {
				case Identifier:
				case Unquoted_argument:
				case Quoted_argument:
				case Bracket_argument:
					{
					setState(18);
					single_argument();
					}
					break;
				case T__0:
					{
					setState(19);
					compound_argument();
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				}
				setState(24);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(25);
			match(T__1);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class Single_argumentContext extends ParserRuleContext {
		public TerminalNode Identifier() { return getToken(CMakeParser.Identifier, 0); }
		public TerminalNode Unquoted_argument() { return getToken(CMakeParser.Unquoted_argument, 0); }
		public TerminalNode Bracket_argument() { return getToken(CMakeParser.Bracket_argument, 0); }
		public TerminalNode Quoted_argument() { return getToken(CMakeParser.Quoted_argument, 0); }
		public Single_argumentContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_single_argument; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CMakeListener ) ((CMakeListener)listener).enterSingle_argument(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CMakeListener ) ((CMakeListener)listener).exitSingle_argument(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CMakeVisitor ) return ((CMakeVisitor<? extends T>)visitor).visitSingle_argument(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Single_argumentContext single_argument() throws RecognitionException {
		Single_argumentContext _localctx = new Single_argumentContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_single_argument);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(27);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & 216L) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class Compound_argumentContext extends ParserRuleContext {
		public List<Single_argumentContext> single_argument() {
			return getRuleContexts(Single_argumentContext.class);
		}
		public Single_argumentContext single_argument(int i) {
			return getRuleContext(Single_argumentContext.class,i);
		}
		public List<Compound_argumentContext> compound_argument() {
			return getRuleContexts(Compound_argumentContext.class);
		}
		public Compound_argumentContext compound_argument(int i) {
			return getRuleContext(Compound_argumentContext.class,i);
		}
		public Compound_argumentContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_compound_argument; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CMakeListener ) ((CMakeListener)listener).enterCompound_argument(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CMakeListener ) ((CMakeListener)listener).exitCompound_argument(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CMakeVisitor ) return ((CMakeVisitor<? extends T>)visitor).visitCompound_argument(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Compound_argumentContext compound_argument() throws RecognitionException {
		Compound_argumentContext _localctx = new Compound_argumentContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_compound_argument);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(29);
			match(T__0);
			setState(34);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & 218L) != 0)) {
				{
				setState(32);
				_errHandler.sync(this);
				switch (_input.LA(1)) {
				case Identifier:
				case Unquoted_argument:
				case Quoted_argument:
				case Bracket_argument:
					{
					setState(30);
					single_argument();
					}
					break;
				case T__0:
					{
					setState(31);
					compound_argument();
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				}
				setState(36);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(37);
			match(T__1);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static final String _serializedATN =
		"\u0004\u0001\u000b(\u0002\u0000\u0007\u0000\u0002\u0001\u0007\u0001\u0002"+
		"\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0001\u0000\u0005\u0000\n\b"+
		"\u0000\n\u0000\f\u0000\r\t\u0000\u0001\u0000\u0001\u0000\u0001\u0001\u0001"+
		"\u0001\u0001\u0001\u0001\u0001\u0005\u0001\u0015\b\u0001\n\u0001\f\u0001"+
		"\u0018\t\u0001\u0001\u0001\u0001\u0001\u0001\u0002\u0001\u0002\u0001\u0003"+
		"\u0001\u0003\u0001\u0003\u0005\u0003!\b\u0003\n\u0003\f\u0003$\t\u0003"+
		"\u0001\u0003\u0001\u0003\u0001\u0003\u0000\u0000\u0004\u0000\u0002\u0004"+
		"\u0006\u0000\u0001\u0002\u0000\u0003\u0004\u0006\u0007(\u0000\u000b\u0001"+
		"\u0000\u0000\u0000\u0002\u0010\u0001\u0000\u0000\u0000\u0004\u001b\u0001"+
		"\u0000\u0000\u0000\u0006\u001d\u0001\u0000\u0000\u0000\b\n\u0003\u0002"+
		"\u0001\u0000\t\b\u0001\u0000\u0000\u0000\n\r\u0001\u0000\u0000\u0000\u000b"+
		"\t\u0001\u0000\u0000\u0000\u000b\f\u0001\u0000\u0000\u0000\f\u000e\u0001"+
		"\u0000\u0000\u0000\r\u000b\u0001\u0000\u0000\u0000\u000e\u000f\u0005\u0000"+
		"\u0000\u0001\u000f\u0001\u0001\u0000\u0000\u0000\u0010\u0011\u0005\u0003"+
		"\u0000\u0000\u0011\u0016\u0005\u0001\u0000\u0000\u0012\u0015\u0003\u0004"+
		"\u0002\u0000\u0013\u0015\u0003\u0006\u0003\u0000\u0014\u0012\u0001\u0000"+
		"\u0000\u0000\u0014\u0013\u0001\u0000\u0000\u0000\u0015\u0018\u0001\u0000"+
		"\u0000\u0000\u0016\u0014\u0001\u0000\u0000\u0000\u0016\u0017\u0001\u0000"+
		"\u0000\u0000\u0017\u0019\u0001\u0000\u0000\u0000\u0018\u0016\u0001\u0000"+
		"\u0000\u0000\u0019\u001a\u0005\u0002\u0000\u0000\u001a\u0003\u0001\u0000"+
		"\u0000\u0000\u001b\u001c\u0007\u0000\u0000\u0000\u001c\u0005\u0001\u0000"+
		"\u0000\u0000\u001d\"\u0005\u0001\u0000\u0000\u001e!\u0003\u0004\u0002"+
		"\u0000\u001f!\u0003\u0006\u0003\u0000 \u001e\u0001\u0000\u0000\u0000 "+
		"\u001f\u0001\u0000\u0000\u0000!$\u0001\u0000\u0000\u0000\" \u0001\u0000"+
		"\u0000\u0000\"#\u0001\u0000\u0000\u0000#%\u0001\u0000\u0000\u0000$\"\u0001"+
		"\u0000\u0000\u0000%&\u0005\u0002\u0000\u0000&\u0007\u0001\u0000\u0000"+
		"\u0000\u0005\u000b\u0014\u0016 \"";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}