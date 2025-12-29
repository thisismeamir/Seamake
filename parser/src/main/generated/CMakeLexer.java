// Generated from CMake.g4 by ANTLR 4.13.2
package io.github.thisismeamir.seamake.parser;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast", "CheckReturnValue", "this-escape"})
public class CMakeLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.13.2", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, Identifier=3, Unquoted_argument=4, Escape_sequence=5, 
		Quoted_argument=6, Bracket_argument=7, Bracket_comment=8, Line_comment=9, 
		Newline=10, Space=11;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	private static String[] makeRuleNames() {
		return new String[] {
			"T__0", "T__1", "Identifier", "Unquoted_argument", "Escape_sequence", 
			"Escape_identity", "Escape_encoded", "Escape_semicolon", "Quoted_argument", 
			"Quoted_cont", "Bracket_argument", "Bracket_arg_nested", "Bracket_comment", 
			"Line_comment", "Newline", "Space"
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


	public CMakeLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "CMake.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getChannelNames() { return channelNames; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public static final String _serializedATN =
		"\u0004\u0000\u000b\u00ad\u0006\uffff\uffff\u0002\u0000\u0007\u0000\u0002"+
		"\u0001\u0007\u0001\u0002\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002"+
		"\u0004\u0007\u0004\u0002\u0005\u0007\u0005\u0002\u0006\u0007\u0006\u0002"+
		"\u0007\u0007\u0007\u0002\b\u0007\b\u0002\t\u0007\t\u0002\n\u0007\n\u0002"+
		"\u000b\u0007\u000b\u0002\f\u0007\f\u0002\r\u0007\r\u0002\u000e\u0007\u000e"+
		"\u0002\u000f\u0007\u000f\u0001\u0000\u0001\u0000\u0001\u0001\u0001\u0001"+
		"\u0001\u0002\u0001\u0002\u0005\u0002(\b\u0002\n\u0002\f\u0002+\t\u0002"+
		"\u0001\u0003\u0001\u0003\u0004\u0003/\b\u0003\u000b\u0003\f\u00030\u0001"+
		"\u0004\u0001\u0004\u0001\u0004\u0003\u00046\b\u0004\u0001\u0005\u0001"+
		"\u0005\u0001\u0005\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001"+
		"\u0006\u0001\u0006\u0003\u0006A\b\u0006\u0001\u0007\u0001\u0007\u0001"+
		"\u0007\u0001\b\u0001\b\u0001\b\u0001\b\u0005\bJ\b\b\n\b\f\bM\t\b\u0001"+
		"\b\u0001\b\u0001\t\u0001\t\u0001\t\u0003\tT\b\t\u0001\t\u0003\tW\b\t\u0001"+
		"\n\u0001\n\u0001\n\u0001\n\u0001\u000b\u0001\u000b\u0001\u000b\u0001\u000b"+
		"\u0001\u000b\u0001\u000b\u0005\u000bc\b\u000b\n\u000b\f\u000bf\t\u000b"+
		"\u0001\u000b\u0003\u000bi\b\u000b\u0001\f\u0001\f\u0001\f\u0001\f\u0001"+
		"\f\u0001\f\u0001\f\u0001\f\u0001\r\u0001\r\u0001\r\u0001\r\u0005\rw\b"+
		"\r\n\r\f\rz\t\r\u0001\r\u0001\r\u0005\r~\b\r\n\r\f\r\u0081\t\r\u0001\r"+
		"\u0001\r\u0005\r\u0085\b\r\n\r\f\r\u0088\t\r\u0001\r\u0001\r\u0005\r\u008c"+
		"\b\r\n\r\f\r\u008f\t\r\u0003\r\u0091\b\r\u0001\r\u0001\r\u0003\r\u0095"+
		"\b\r\u0001\r\u0003\r\u0098\b\r\u0001\r\u0001\r\u0001\u000e\u0001\u000e"+
		"\u0003\u000e\u009e\b\u000e\u0001\u000e\u0004\u000e\u00a1\b\u000e\u000b"+
		"\u000e\f\u000e\u00a2\u0001\u000e\u0001\u000e\u0001\u000f\u0004\u000f\u00a8"+
		"\b\u000f\u000b\u000f\f\u000f\u00a9\u0001\u000f\u0001\u000f\u0001d\u0000"+
		"\u0010\u0001\u0001\u0003\u0002\u0005\u0003\u0007\u0004\t\u0005\u000b\u0000"+
		"\r\u0000\u000f\u0000\u0011\u0006\u0013\u0000\u0015\u0007\u0017\u0000\u0019"+
		"\b\u001b\t\u001d\n\u001f\u000b\u0001\u0000\n\u0003\u0000AZ__az\u0004\u0000"+
		"09AZ__az\u0006\u0000\t\n\r\r  \"#()\\\\\u0004\u000009;;AZaz\u0002\u0000"+
		"\"\"\\\\\u0004\u0000\n\n\r\r==[[\u0002\u0000\n\n\r\r\u0003\u0000\n\n\r"+
		"\r[[\u0001\u0001\n\n\u0002\u0000\t\t  \u00c2\u0000\u0001\u0001\u0000\u0000"+
		"\u0000\u0000\u0003\u0001\u0000\u0000\u0000\u0000\u0005\u0001\u0000\u0000"+
		"\u0000\u0000\u0007\u0001\u0000\u0000\u0000\u0000\t\u0001\u0000\u0000\u0000"+
		"\u0000\u0011\u0001\u0000\u0000\u0000\u0000\u0015\u0001\u0000\u0000\u0000"+
		"\u0000\u0019\u0001\u0000\u0000\u0000\u0000\u001b\u0001\u0000\u0000\u0000"+
		"\u0000\u001d\u0001\u0000\u0000\u0000\u0000\u001f\u0001\u0000\u0000\u0000"+
		"\u0001!\u0001\u0000\u0000\u0000\u0003#\u0001\u0000\u0000\u0000\u0005%"+
		"\u0001\u0000\u0000\u0000\u0007.\u0001\u0000\u0000\u0000\t5\u0001\u0000"+
		"\u0000\u0000\u000b7\u0001\u0000\u0000\u0000\r@\u0001\u0000\u0000\u0000"+
		"\u000fB\u0001\u0000\u0000\u0000\u0011E\u0001\u0000\u0000\u0000\u0013P"+
		"\u0001\u0000\u0000\u0000\u0015X\u0001\u0000\u0000\u0000\u0017h\u0001\u0000"+
		"\u0000\u0000\u0019j\u0001\u0000\u0000\u0000\u001br\u0001\u0000\u0000\u0000"+
		"\u001d\u00a0\u0001\u0000\u0000\u0000\u001f\u00a7\u0001\u0000\u0000\u0000"+
		"!\"\u0005(\u0000\u0000\"\u0002\u0001\u0000\u0000\u0000#$\u0005)\u0000"+
		"\u0000$\u0004\u0001\u0000\u0000\u0000%)\u0007\u0000\u0000\u0000&(\u0007"+
		"\u0001\u0000\u0000\'&\u0001\u0000\u0000\u0000(+\u0001\u0000\u0000\u0000"+
		")\'\u0001\u0000\u0000\u0000)*\u0001\u0000\u0000\u0000*\u0006\u0001\u0000"+
		"\u0000\u0000+)\u0001\u0000\u0000\u0000,/\b\u0002\u0000\u0000-/\u0003\t"+
		"\u0004\u0000.,\u0001\u0000\u0000\u0000.-\u0001\u0000\u0000\u0000/0\u0001"+
		"\u0000\u0000\u00000.\u0001\u0000\u0000\u000001\u0001\u0000\u0000\u0000"+
		"1\b\u0001\u0000\u0000\u000026\u0003\u000b\u0005\u000036\u0003\r\u0006"+
		"\u000046\u0003\u000f\u0007\u000052\u0001\u0000\u0000\u000053\u0001\u0000"+
		"\u0000\u000054\u0001\u0000\u0000\u00006\n\u0001\u0000\u0000\u000078\u0005"+
		"\\\u0000\u000089\b\u0003\u0000\u00009\f\u0001\u0000\u0000\u0000:;\u0005"+
		"\\\u0000\u0000;A\u0005t\u0000\u0000<=\u0005\\\u0000\u0000=A\u0005r\u0000"+
		"\u0000>?\u0005\\\u0000\u0000?A\u0005n\u0000\u0000@:\u0001\u0000\u0000"+
		"\u0000@<\u0001\u0000\u0000\u0000@>\u0001\u0000\u0000\u0000A\u000e\u0001"+
		"\u0000\u0000\u0000BC\u0005\\\u0000\u0000CD\u0005;\u0000\u0000D\u0010\u0001"+
		"\u0000\u0000\u0000EK\u0005\"\u0000\u0000FJ\b\u0004\u0000\u0000GJ\u0003"+
		"\t\u0004\u0000HJ\u0003\u0013\t\u0000IF\u0001\u0000\u0000\u0000IG\u0001"+
		"\u0000\u0000\u0000IH\u0001\u0000\u0000\u0000JM\u0001\u0000\u0000\u0000"+
		"KI\u0001\u0000\u0000\u0000KL\u0001\u0000\u0000\u0000LN\u0001\u0000\u0000"+
		"\u0000MK\u0001\u0000\u0000\u0000NO\u0005\"\u0000\u0000O\u0012\u0001\u0000"+
		"\u0000\u0000PV\u0005\\\u0000\u0000QS\u0005\r\u0000\u0000RT\u0005\n\u0000"+
		"\u0000SR\u0001\u0000\u0000\u0000ST\u0001\u0000\u0000\u0000TW\u0001\u0000"+
		"\u0000\u0000UW\u0005\n\u0000\u0000VQ\u0001\u0000\u0000\u0000VU\u0001\u0000"+
		"\u0000\u0000W\u0014\u0001\u0000\u0000\u0000XY\u0005[\u0000\u0000YZ\u0003"+
		"\u0017\u000b\u0000Z[\u0005]\u0000\u0000[\u0016\u0001\u0000\u0000\u0000"+
		"\\]\u0005=\u0000\u0000]^\u0003\u0017\u000b\u0000^_\u0005=\u0000\u0000"+
		"_i\u0001\u0000\u0000\u0000`d\u0005[\u0000\u0000ac\t\u0000\u0000\u0000"+
		"ba\u0001\u0000\u0000\u0000cf\u0001\u0000\u0000\u0000de\u0001\u0000\u0000"+
		"\u0000db\u0001\u0000\u0000\u0000eg\u0001\u0000\u0000\u0000fd\u0001\u0000"+
		"\u0000\u0000gi\u0005]\u0000\u0000h\\\u0001\u0000\u0000\u0000h`\u0001\u0000"+
		"\u0000\u0000i\u0018\u0001\u0000\u0000\u0000jk\u0005#\u0000\u0000kl\u0005"+
		"[\u0000\u0000lm\u0001\u0000\u0000\u0000mn\u0003\u0017\u000b\u0000no\u0005"+
		"]\u0000\u0000op\u0001\u0000\u0000\u0000pq\u0006\f\u0000\u0000q\u001a\u0001"+
		"\u0000\u0000\u0000r\u0090\u0005#\u0000\u0000s\u0091\u0001\u0000\u0000"+
		"\u0000tx\u0005[\u0000\u0000uw\u0005=\u0000\u0000vu\u0001\u0000\u0000\u0000"+
		"wz\u0001\u0000\u0000\u0000xv\u0001\u0000\u0000\u0000xy\u0001\u0000\u0000"+
		"\u0000y\u0091\u0001\u0000\u0000\u0000zx\u0001\u0000\u0000\u0000{\u007f"+
		"\u0005[\u0000\u0000|~\u0005=\u0000\u0000}|\u0001\u0000\u0000\u0000~\u0081"+
		"\u0001\u0000\u0000\u0000\u007f}\u0001\u0000\u0000\u0000\u007f\u0080\u0001"+
		"\u0000\u0000\u0000\u0080\u0082\u0001\u0000\u0000\u0000\u0081\u007f\u0001"+
		"\u0000\u0000\u0000\u0082\u0086\b\u0005\u0000\u0000\u0083\u0085\b\u0006"+
		"\u0000\u0000\u0084\u0083\u0001\u0000\u0000\u0000\u0085\u0088\u0001\u0000"+
		"\u0000\u0000\u0086\u0084\u0001\u0000\u0000\u0000\u0086\u0087\u0001\u0000"+
		"\u0000\u0000\u0087\u0091\u0001\u0000\u0000\u0000\u0088\u0086\u0001\u0000"+
		"\u0000\u0000\u0089\u008d\b\u0007\u0000\u0000\u008a\u008c\b\u0006\u0000"+
		"\u0000\u008b\u008a\u0001\u0000\u0000\u0000\u008c\u008f\u0001\u0000\u0000"+
		"\u0000\u008d\u008b\u0001\u0000\u0000\u0000\u008d\u008e\u0001\u0000\u0000"+
		"\u0000\u008e\u0091\u0001\u0000\u0000\u0000\u008f\u008d\u0001\u0000\u0000"+
		"\u0000\u0090s\u0001\u0000\u0000\u0000\u0090t\u0001\u0000\u0000\u0000\u0090"+
		"{\u0001\u0000\u0000\u0000\u0090\u0089\u0001\u0000\u0000\u0000\u0091\u0097"+
		"\u0001\u0000\u0000\u0000\u0092\u0094\u0005\r\u0000\u0000\u0093\u0095\u0005"+
		"\n\u0000\u0000\u0094\u0093\u0001\u0000\u0000\u0000\u0094\u0095\u0001\u0000"+
		"\u0000\u0000\u0095\u0098\u0001\u0000\u0000\u0000\u0096\u0098\u0007\b\u0000"+
		"\u0000\u0097\u0092\u0001\u0000\u0000\u0000\u0097\u0096\u0001\u0000\u0000"+
		"\u0000\u0098\u0099\u0001\u0000\u0000\u0000\u0099\u009a\u0006\r\u0000\u0000"+
		"\u009a\u001c\u0001\u0000\u0000\u0000\u009b\u009d\u0005\r\u0000\u0000\u009c"+
		"\u009e\u0005\n\u0000\u0000\u009d\u009c\u0001\u0000\u0000\u0000\u009d\u009e"+
		"\u0001\u0000\u0000\u0000\u009e\u00a1\u0001\u0000\u0000\u0000\u009f\u00a1"+
		"\u0005\n\u0000\u0000\u00a0\u009b\u0001\u0000\u0000\u0000\u00a0\u009f\u0001"+
		"\u0000\u0000\u0000\u00a1\u00a2\u0001\u0000\u0000\u0000\u00a2\u00a0\u0001"+
		"\u0000\u0000\u0000\u00a2\u00a3\u0001\u0000\u0000\u0000\u00a3\u00a4\u0001"+
		"\u0000\u0000\u0000\u00a4\u00a5\u0006\u000e\u0000\u0000\u00a5\u001e\u0001"+
		"\u0000\u0000\u0000\u00a6\u00a8\u0007\t\u0000\u0000\u00a7\u00a6\u0001\u0000"+
		"\u0000\u0000\u00a8\u00a9\u0001\u0000\u0000\u0000\u00a9\u00a7\u0001\u0000"+
		"\u0000\u0000\u00a9\u00aa\u0001\u0000\u0000\u0000\u00aa\u00ab\u0001\u0000"+
		"\u0000\u0000\u00ab\u00ac\u0006\u000f\u0000\u0000\u00ac \u0001\u0000\u0000"+
		"\u0000\u0017\u0000).05@IKSVdhx\u007f\u0086\u008d\u0090\u0094\u0097\u009d"+
		"\u00a0\u00a2\u00a9\u0001\u0006\u0000\u0000";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}