// Generated from C:/UNI/tercer curso/segundo_semestre/diseño_de_lenguajes_de_programacion/lab/project/dlp/src/parser/Cmm.g4 by ANTLR 4.13.2
package parser;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast", "CheckReturnValue", "this-escape"})
public class CmmLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.13.2", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		INT_CONSTANT=1, REAL_CONSTANT=2, CHAR_CONSTANT=3, ID=4, COMMENT=5, ML_COMMENT=6, 
		BLANK=7;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	private static String[] makeRuleNames() {
		return new String[] {
			"INT_CONSTANT", "REAL_CONSTANT", "FLOAT_CONSTANT", "CHAR_CONSTANT", "ID", 
			"COMMENT", "ML_COMMENT", "BLANK"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, "INT_CONSTANT", "REAL_CONSTANT", "CHAR_CONSTANT", "ID", "COMMENT", 
			"ML_COMMENT", "BLANK"
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


	public CmmLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "Cmm.g4"; }

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
		"\u0004\u0000\u0007z\u0006\uffff\uffff\u0002\u0000\u0007\u0000\u0002\u0001"+
		"\u0007\u0001\u0002\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002\u0004"+
		"\u0007\u0004\u0002\u0005\u0007\u0005\u0002\u0006\u0007\u0006\u0002\u0007"+
		"\u0007\u0007\u0001\u0000\u0001\u0000\u0005\u0000\u0014\b\u0000\n\u0000"+
		"\f\u0000\u0017\t\u0000\u0001\u0000\u0003\u0000\u001a\b\u0000\u0001\u0001"+
		"\u0001\u0001\u0001\u0001\u0003\u0001\u001f\b\u0001\u0001\u0001\u0001\u0001"+
		"\u0003\u0001#\b\u0001\u0001\u0001\u0001\u0001\u0003\u0001\'\b\u0001\u0003"+
		"\u0001)\b\u0001\u0001\u0002\u0003\u0002,\b\u0002\u0001\u0002\u0001\u0002"+
		"\u0004\u00020\b\u0002\u000b\u0002\f\u00021\u0001\u0002\u0001\u0002\u0001"+
		"\u0002\u0005\u00027\b\u0002\n\u0002\f\u0002:\t\u0002\u0003\u0002<\b\u0002"+
		"\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003"+
		"\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003"+
		"\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0003\u0003"+
		"O\b\u0003\u0001\u0004\u0001\u0004\u0005\u0004S\b\u0004\n\u0004\f\u0004"+
		"V\t\u0004\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0005\u0005"+
		"\\\b\u0005\n\u0005\f\u0005_\t\u0005\u0001\u0005\u0003\u0005b\b\u0005\u0001"+
		"\u0005\u0001\u0005\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0005"+
		"\u0006j\b\u0006\n\u0006\f\u0006m\t\u0006\u0001\u0006\u0001\u0006\u0001"+
		"\u0006\u0001\u0006\u0001\u0006\u0001\u0007\u0004\u0007u\b\u0007\u000b"+
		"\u0007\f\u0007v\u0001\u0007\u0001\u0007\u0002]k\u0000\b\u0001\u0001\u0003"+
		"\u0002\u0005\u0000\u0007\u0003\t\u0004\u000b\u0005\r\u0006\u000f\u0007"+
		"\u0001\u0000\b\u0001\u000019\u0001\u000009\u0002\u0000EEee\u0002\u0000"+
		"++--\u0003\u0000AZ__az\u0004\u000009AZ__az\u0002\u0001\n\n\r\r\u0003\u0000"+
		"\t\n\r\r  \u0089\u0000\u0001\u0001\u0000\u0000\u0000\u0000\u0003\u0001"+
		"\u0000\u0000\u0000\u0000\u0007\u0001\u0000\u0000\u0000\u0000\t\u0001\u0000"+
		"\u0000\u0000\u0000\u000b\u0001\u0000\u0000\u0000\u0000\r\u0001\u0000\u0000"+
		"\u0000\u0000\u000f\u0001\u0000\u0000\u0000\u0001\u0019\u0001\u0000\u0000"+
		"\u0000\u0003(\u0001\u0000\u0000\u0000\u0005;\u0001\u0000\u0000\u0000\u0007"+
		"N\u0001\u0000\u0000\u0000\tP\u0001\u0000\u0000\u0000\u000bW\u0001\u0000"+
		"\u0000\u0000\re\u0001\u0000\u0000\u0000\u000ft\u0001\u0000\u0000\u0000"+
		"\u0011\u0015\u0007\u0000\u0000\u0000\u0012\u0014\u0007\u0001\u0000\u0000"+
		"\u0013\u0012\u0001\u0000\u0000\u0000\u0014\u0017\u0001\u0000\u0000\u0000"+
		"\u0015\u0013\u0001\u0000\u0000\u0000\u0015\u0016\u0001\u0000\u0000\u0000"+
		"\u0016\u001a\u0001\u0000\u0000\u0000\u0017\u0015\u0001\u0000\u0000\u0000"+
		"\u0018\u001a\u00050\u0000\u0000\u0019\u0011\u0001\u0000\u0000\u0000\u0019"+
		"\u0018\u0001\u0000\u0000\u0000\u001a\u0002\u0001\u0000\u0000\u0000\u001b"+
		")\u0003\u0005\u0002\u0000\u001c\u001f\u0003\u0001\u0000\u0000\u001d\u001f"+
		"\u0003\u0005\u0002\u0000\u001e\u001c\u0001\u0000\u0000\u0000\u001e\u001d"+
		"\u0001\u0000\u0000\u0000\u001f \u0001\u0000\u0000\u0000 \"\u0007\u0002"+
		"\u0000\u0000!#\u0007\u0003\u0000\u0000\"!\u0001\u0000\u0000\u0000\"#\u0001"+
		"\u0000\u0000\u0000#&\u0001\u0000\u0000\u0000$\'\u0003\u0001\u0000\u0000"+
		"%\'\u0003\u0005\u0002\u0000&$\u0001\u0000\u0000\u0000&%\u0001\u0000\u0000"+
		"\u0000\')\u0001\u0000\u0000\u0000(\u001b\u0001\u0000\u0000\u0000(\u001e"+
		"\u0001\u0000\u0000\u0000)\u0004\u0001\u0000\u0000\u0000*,\u0003\u0001"+
		"\u0000\u0000+*\u0001\u0000\u0000\u0000+,\u0001\u0000\u0000\u0000,-\u0001"+
		"\u0000\u0000\u0000-/\u0005.\u0000\u0000.0\u0007\u0001\u0000\u0000/.\u0001"+
		"\u0000\u0000\u000001\u0001\u0000\u0000\u00001/\u0001\u0000\u0000\u0000"+
		"12\u0001\u0000\u0000\u00002<\u0001\u0000\u0000\u000034\u0003\u0001\u0000"+
		"\u000048\u0005.\u0000\u000057\u0007\u0001\u0000\u000065\u0001\u0000\u0000"+
		"\u00007:\u0001\u0000\u0000\u000086\u0001\u0000\u0000\u000089\u0001\u0000"+
		"\u0000\u00009<\u0001\u0000\u0000\u0000:8\u0001\u0000\u0000\u0000;+\u0001"+
		"\u0000\u0000\u0000;3\u0001\u0000\u0000\u0000<\u0006\u0001\u0000\u0000"+
		"\u0000=>\u0005\'\u0000\u0000>?\u0005\\\u0000\u0000?@\u0005n\u0000\u0000"+
		"@O\u0005\'\u0000\u0000AB\u0005\'\u0000\u0000BC\u0005\\\u0000\u0000CD\u0005"+
		"t\u0000\u0000DO\u0005\'\u0000\u0000EF\u0005\'\u0000\u0000FG\t\u0000\u0000"+
		"\u0000GO\u0005\'\u0000\u0000HI\u0005\'\u0000\u0000IJ\u0005\\\u0000\u0000"+
		"JK\u0001\u0000\u0000\u0000KL\u0003\u0001\u0000\u0000LM\u0005\'\u0000\u0000"+
		"MO\u0001\u0000\u0000\u0000N=\u0001\u0000\u0000\u0000NA\u0001\u0000\u0000"+
		"\u0000NE\u0001\u0000\u0000\u0000NH\u0001\u0000\u0000\u0000O\b\u0001\u0000"+
		"\u0000\u0000PT\u0007\u0004\u0000\u0000QS\u0007\u0005\u0000\u0000RQ\u0001"+
		"\u0000\u0000\u0000SV\u0001\u0000\u0000\u0000TR\u0001\u0000\u0000\u0000"+
		"TU\u0001\u0000\u0000\u0000U\n\u0001\u0000\u0000\u0000VT\u0001\u0000\u0000"+
		"\u0000WX\u0005/\u0000\u0000XY\u0005/\u0000\u0000Y]\u0001\u0000\u0000\u0000"+
		"Z\\\t\u0000\u0000\u0000[Z\u0001\u0000\u0000\u0000\\_\u0001\u0000\u0000"+
		"\u0000]^\u0001\u0000\u0000\u0000][\u0001\u0000\u0000\u0000^a\u0001\u0000"+
		"\u0000\u0000_]\u0001\u0000\u0000\u0000`b\u0007\u0006\u0000\u0000a`\u0001"+
		"\u0000\u0000\u0000bc\u0001\u0000\u0000\u0000cd\u0006\u0005\u0000\u0000"+
		"d\f\u0001\u0000\u0000\u0000ef\u0005/\u0000\u0000fg\u0005*\u0000\u0000"+
		"gk\u0001\u0000\u0000\u0000hj\t\u0000\u0000\u0000ih\u0001\u0000\u0000\u0000"+
		"jm\u0001\u0000\u0000\u0000kl\u0001\u0000\u0000\u0000ki\u0001\u0000\u0000"+
		"\u0000ln\u0001\u0000\u0000\u0000mk\u0001\u0000\u0000\u0000no\u0005*\u0000"+
		"\u0000op\u0005/\u0000\u0000pq\u0001\u0000\u0000\u0000qr\u0006\u0006\u0000"+
		"\u0000r\u000e\u0001\u0000\u0000\u0000su\u0007\u0007\u0000\u0000ts\u0001"+
		"\u0000\u0000\u0000uv\u0001\u0000\u0000\u0000vt\u0001\u0000\u0000\u0000"+
		"vw\u0001\u0000\u0000\u0000wx\u0001\u0000\u0000\u0000xy\u0006\u0007\u0000"+
		"\u0000y\u0010\u0001\u0000\u0000\u0000\u0011\u0000\u0015\u0019\u001e\""+
		"&(+18;NT]akv\u0001\u0006\u0000\u0000";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}