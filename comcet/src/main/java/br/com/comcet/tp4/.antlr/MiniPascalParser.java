// Generated from c:/Danilo/Projetos/Melhores/compiler/comcet/src/main/java/br/com/comcet/tp4/MiniPascal.g4 by ANTLR 4.13.1
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast", "CheckReturnValue"})
public class MiniPascalParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.13.1", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, T__8=9, 
		T__9=10, T__10=11, T__11=12, T__12=13, T__13=14, T__14=15, T__15=16, T__16=17, 
		T__17=18, T__18=19, T__19=20, T__20=21, T__21=22, T__22=23, T__23=24, 
		T__24=25, T__25=26, T__26=27, T__27=28, T__28=29, T__29=30, T__30=31, 
		T__31=32, T__32=33, T__33=34, T__34=35, T__35=36, T__36=37, T__37=38, 
		VAR_TYPE=39, BOOLEAN=40, ID=41, INTEGER=42, WHITESPACE=43, COMMENT_BRACE=44, 
		COMMENT_PAREN=45, STRING=46;
	public static final int
		RULE_program = 0, RULE_varDeclarations = 1, RULE_varDeclaration = 2, RULE_subroutineDeclarations = 3, 
		RULE_subroutineDeclaration = 4, RULE_parameters = 5, RULE_expression = 6, 
		RULE_command = 7, RULE_assignmentCommand = 8, RULE_blockCommand = 9, RULE_ifCommand = 10, 
		RULE_whileCommand = 11, RULE_repeatCommand = 12, RULE_callCommand = 13, 
		RULE_readCommand = 14, RULE_writeCommand = 15, RULE_arguments = 16;
	private static String[] makeRuleNames() {
		return new String[] {
			"program", "varDeclarations", "varDeclaration", "subroutineDeclarations", 
			"subroutineDeclaration", "parameters", "expression", "command", "assignmentCommand", 
			"blockCommand", "ifCommand", "whileCommand", "repeatCommand", "callCommand", 
			"readCommand", "writeCommand", "arguments"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'program'", "';'", "'.'", "'var'", "','", "':'", "'function'", 
			"'('", "')'", "'procedure'", "'not'", "'*'", "'/'", "'and'", "'+'", "'-'", 
			"'or'", "'xor'", "'='", "'<>'", "'<'", "'>'", "'<='", "'>='", "':='", 
			"'begin'", "'end'", "'if'", "'then'", "'else'", "'while'", "'do'", "'repeat'", 
			"'until'", "'readln'", "'read'", "'writeln'", "'write'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, null, "VAR_TYPE", "BOOLEAN", "ID", "INTEGER", "WHITESPACE", 
			"COMMENT_BRACE", "COMMENT_PAREN", "STRING"
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
	public String getGrammarFileName() { return "MiniPascal.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public MiniPascalParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ProgramContext extends ParserRuleContext {
		public TerminalNode ID() { return getToken(MiniPascalParser.ID, 0); }
		public BlockCommandContext blockCommand() {
			return getRuleContext(BlockCommandContext.class,0);
		}
		public TerminalNode EOF() { return getToken(MiniPascalParser.EOF, 0); }
		public VarDeclarationsContext varDeclarations() {
			return getRuleContext(VarDeclarationsContext.class,0);
		}
		public SubroutineDeclarationsContext subroutineDeclarations() {
			return getRuleContext(SubroutineDeclarationsContext.class,0);
		}
		public ProgramContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_program; }
	}

	public final ProgramContext program() throws RecognitionException {
		ProgramContext _localctx = new ProgramContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_program);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(34);
			match(T__0);
			setState(35);
			match(ID);
			setState(36);
			match(T__1);
			setState(38);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__3) {
				{
				setState(37);
				varDeclarations();
				}
			}

			setState(41);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__6 || _la==T__9) {
				{
				setState(40);
				subroutineDeclarations();
				}
			}

			setState(43);
			blockCommand();
			setState(44);
			match(T__2);
			setState(45);
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
	public static class VarDeclarationsContext extends ParserRuleContext {
		public List<VarDeclarationContext> varDeclaration() {
			return getRuleContexts(VarDeclarationContext.class);
		}
		public VarDeclarationContext varDeclaration(int i) {
			return getRuleContext(VarDeclarationContext.class,i);
		}
		public VarDeclarationsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_varDeclarations; }
	}

	public final VarDeclarationsContext varDeclarations() throws RecognitionException {
		VarDeclarationsContext _localctx = new VarDeclarationsContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_varDeclarations);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(47);
			match(T__3);
			setState(51); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(48);
				varDeclaration();
				setState(49);
				match(T__1);
				}
				}
				setState(53); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==ID );
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
	public static class VarDeclarationContext extends ParserRuleContext {
		public List<TerminalNode> ID() { return getTokens(MiniPascalParser.ID); }
		public TerminalNode ID(int i) {
			return getToken(MiniPascalParser.ID, i);
		}
		public TerminalNode VAR_TYPE() { return getToken(MiniPascalParser.VAR_TYPE, 0); }
		public VarDeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_varDeclaration; }
	}

	public final VarDeclarationContext varDeclaration() throws RecognitionException {
		VarDeclarationContext _localctx = new VarDeclarationContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_varDeclaration);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(55);
			match(ID);
			setState(60);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__4) {
				{
				{
				setState(56);
				match(T__4);
				setState(57);
				match(ID);
				}
				}
				setState(62);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(63);
			match(T__5);
			setState(64);
			match(VAR_TYPE);
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
	public static class SubroutineDeclarationsContext extends ParserRuleContext {
		public List<SubroutineDeclarationContext> subroutineDeclaration() {
			return getRuleContexts(SubroutineDeclarationContext.class);
		}
		public SubroutineDeclarationContext subroutineDeclaration(int i) {
			return getRuleContext(SubroutineDeclarationContext.class,i);
		}
		public SubroutineDeclarationsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_subroutineDeclarations; }
	}

	public final SubroutineDeclarationsContext subroutineDeclarations() throws RecognitionException {
		SubroutineDeclarationsContext _localctx = new SubroutineDeclarationsContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_subroutineDeclarations);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(67); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(66);
				subroutineDeclaration();
				}
				}
				setState(69); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==T__6 || _la==T__9 );
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
	public static class SubroutineDeclarationContext extends ParserRuleContext {
		public Token returnType;
		public TerminalNode ID() { return getToken(MiniPascalParser.ID, 0); }
		public BlockCommandContext blockCommand() {
			return getRuleContext(BlockCommandContext.class,0);
		}
		public TerminalNode VAR_TYPE() { return getToken(MiniPascalParser.VAR_TYPE, 0); }
		public ParametersContext parameters() {
			return getRuleContext(ParametersContext.class,0);
		}
		public VarDeclarationsContext varDeclarations() {
			return getRuleContext(VarDeclarationsContext.class,0);
		}
		public SubroutineDeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_subroutineDeclaration; }
	}

	public final SubroutineDeclarationContext subroutineDeclaration() throws RecognitionException {
		SubroutineDeclarationContext _localctx = new SubroutineDeclarationContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_subroutineDeclaration);
		int _la;
		try {
			setState(101);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__6:
				enterOuterAlt(_localctx, 1);
				{
				setState(71);
				match(T__6);
				setState(72);
				match(ID);
				setState(73);
				match(T__7);
				setState(75);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==ID) {
					{
					setState(74);
					parameters();
					}
				}

				setState(77);
				match(T__8);
				setState(78);
				match(T__5);
				setState(79);
				((SubroutineDeclarationContext)_localctx).returnType = match(VAR_TYPE);
				setState(80);
				match(T__1);
				setState(82);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==T__3) {
					{
					setState(81);
					varDeclarations();
					}
				}

				setState(84);
				blockCommand();
				setState(85);
				match(T__1);
				}
				break;
			case T__9:
				enterOuterAlt(_localctx, 2);
				{
				setState(87);
				match(T__9);
				setState(88);
				match(ID);
				setState(89);
				match(T__7);
				setState(91);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==ID) {
					{
					setState(90);
					parameters();
					}
				}

				setState(93);
				match(T__8);
				setState(94);
				match(T__1);
				setState(96);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==T__3) {
					{
					setState(95);
					varDeclarations();
					}
				}

				setState(98);
				blockCommand();
				setState(99);
				match(T__1);
				}
				break;
			default:
				throw new NoViableAltException(this);
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
	public static class ParametersContext extends ParserRuleContext {
		public List<VarDeclarationContext> varDeclaration() {
			return getRuleContexts(VarDeclarationContext.class);
		}
		public VarDeclarationContext varDeclaration(int i) {
			return getRuleContext(VarDeclarationContext.class,i);
		}
		public ParametersContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_parameters; }
	}

	public final ParametersContext parameters() throws RecognitionException {
		ParametersContext _localctx = new ParametersContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_parameters);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(103);
			varDeclaration();
			setState(108);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__1) {
				{
				{
				setState(104);
				match(T__1);
				setState(105);
				varDeclaration();
				}
				}
				setState(110);
				_errHandler.sync(this);
				_la = _input.LA(1);
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
	public static class ExpressionContext extends ParserRuleContext {
		public ExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expression; }
	 
		public ExpressionContext() { }
		public void copyFrom(ExpressionContext ctx) {
			super.copyFrom(ctx);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class IdentifierContext extends ExpressionContext {
		public TerminalNode ID() { return getToken(MiniPascalParser.ID, 0); }
		public IdentifierContext(ExpressionContext ctx) { copyFrom(ctx); }
	}
	@SuppressWarnings("CheckReturnValue")
	public static class LitIntContext extends ExpressionContext {
		public TerminalNode INTEGER() { return getToken(MiniPascalParser.INTEGER, 0); }
		public LitIntContext(ExpressionContext ctx) { copyFrom(ctx); }
	}
	@SuppressWarnings("CheckReturnValue")
	public static class LitStrContext extends ExpressionContext {
		public TerminalNode STRING() { return getToken(MiniPascalParser.STRING, 0); }
		public LitStrContext(ExpressionContext ctx) { copyFrom(ctx); }
	}
	@SuppressWarnings("CheckReturnValue")
	public static class BinaryExprContext extends ExpressionContext {
		public ExpressionContext left;
		public Token op;
		public ExpressionContext right;
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public BinaryExprContext(ExpressionContext ctx) { copyFrom(ctx); }
	}
	@SuppressWarnings("CheckReturnValue")
	public static class LitBoolContext extends ExpressionContext {
		public TerminalNode BOOLEAN() { return getToken(MiniPascalParser.BOOLEAN, 0); }
		public LitBoolContext(ExpressionContext ctx) { copyFrom(ctx); }
	}
	@SuppressWarnings("CheckReturnValue")
	public static class FunctionCallContext extends ExpressionContext {
		public TerminalNode ID() { return getToken(MiniPascalParser.ID, 0); }
		public ArgumentsContext arguments() {
			return getRuleContext(ArgumentsContext.class,0);
		}
		public FunctionCallContext(ExpressionContext ctx) { copyFrom(ctx); }
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ParenExprContext extends ExpressionContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public ParenExprContext(ExpressionContext ctx) { copyFrom(ctx); }
	}
	@SuppressWarnings("CheckReturnValue")
	public static class UnaryExprContext extends ExpressionContext {
		public Token op;
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public UnaryExprContext(ExpressionContext ctx) { copyFrom(ctx); }
	}

	public final ExpressionContext expression() throws RecognitionException {
		return expression(0);
	}

	private ExpressionContext expression(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		ExpressionContext _localctx = new ExpressionContext(_ctx, _parentState);
		ExpressionContext _prevctx = _localctx;
		int _startState = 12;
		enterRecursionRule(_localctx, 12, RULE_expression, _p);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(128);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,12,_ctx) ) {
			case 1:
				{
				_localctx = new ParenExprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;

				setState(112);
				match(T__7);
				setState(113);
				expression(0);
				setState(114);
				match(T__8);
				}
				break;
			case 2:
				{
				_localctx = new UnaryExprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(116);
				((UnaryExprContext)_localctx).op = match(T__10);
				setState(117);
				expression(9);
				}
				break;
			case 3:
				{
				_localctx = new LitIntContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(118);
				match(INTEGER);
				}
				break;
			case 4:
				{
				_localctx = new LitBoolContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(119);
				match(BOOLEAN);
				}
				break;
			case 5:
				{
				_localctx = new LitStrContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(120);
				match(STRING);
				}
				break;
			case 6:
				{
				_localctx = new FunctionCallContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(121);
				match(ID);
				setState(122);
				match(T__7);
				setState(124);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & 78065325574400L) != 0)) {
					{
					setState(123);
					arguments();
					}
				}

				setState(126);
				match(T__8);
				}
				break;
			case 7:
				{
				_localctx = new IdentifierContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(127);
				match(ID);
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(141);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,14,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(139);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,13,_ctx) ) {
					case 1:
						{
						_localctx = new BinaryExprContext(new ExpressionContext(_parentctx, _parentState));
						((BinaryExprContext)_localctx).left = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(130);
						if (!(precpred(_ctx, 8))) throw new FailedPredicateException(this, "precpred(_ctx, 8)");
						setState(131);
						((BinaryExprContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & 28672L) != 0)) ) {
							((BinaryExprContext)_localctx).op = (Token)_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(132);
						((BinaryExprContext)_localctx).right = expression(9);
						}
						break;
					case 2:
						{
						_localctx = new BinaryExprContext(new ExpressionContext(_parentctx, _parentState));
						((BinaryExprContext)_localctx).left = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(133);
						if (!(precpred(_ctx, 7))) throw new FailedPredicateException(this, "precpred(_ctx, 7)");
						setState(134);
						((BinaryExprContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & 491520L) != 0)) ) {
							((BinaryExprContext)_localctx).op = (Token)_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(135);
						((BinaryExprContext)_localctx).right = expression(8);
						}
						break;
					case 3:
						{
						_localctx = new BinaryExprContext(new ExpressionContext(_parentctx, _parentState));
						((BinaryExprContext)_localctx).left = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(136);
						if (!(precpred(_ctx, 6))) throw new FailedPredicateException(this, "precpred(_ctx, 6)");
						setState(137);
						((BinaryExprContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & 33030144L) != 0)) ) {
							((BinaryExprContext)_localctx).op = (Token)_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(138);
						((BinaryExprContext)_localctx).right = expression(7);
						}
						break;
					}
					} 
				}
				setState(143);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,14,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class CommandContext extends ParserRuleContext {
		public AssignmentCommandContext assignmentCommand() {
			return getRuleContext(AssignmentCommandContext.class,0);
		}
		public IfCommandContext ifCommand() {
			return getRuleContext(IfCommandContext.class,0);
		}
		public WhileCommandContext whileCommand() {
			return getRuleContext(WhileCommandContext.class,0);
		}
		public RepeatCommandContext repeatCommand() {
			return getRuleContext(RepeatCommandContext.class,0);
		}
		public BlockCommandContext blockCommand() {
			return getRuleContext(BlockCommandContext.class,0);
		}
		public CallCommandContext callCommand() {
			return getRuleContext(CallCommandContext.class,0);
		}
		public ReadCommandContext readCommand() {
			return getRuleContext(ReadCommandContext.class,0);
		}
		public WriteCommandContext writeCommand() {
			return getRuleContext(WriteCommandContext.class,0);
		}
		public CommandContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_command; }
	}

	public final CommandContext command() throws RecognitionException {
		CommandContext _localctx = new CommandContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_command);
		try {
			setState(152);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,15,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(144);
				assignmentCommand();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(145);
				ifCommand();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(146);
				whileCommand();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(147);
				repeatCommand();
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(148);
				blockCommand();
				}
				break;
			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(149);
				callCommand();
				}
				break;
			case 7:
				enterOuterAlt(_localctx, 7);
				{
				setState(150);
				readCommand();
				}
				break;
			case 8:
				enterOuterAlt(_localctx, 8);
				{
				setState(151);
				writeCommand();
				}
				break;
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
	public static class AssignmentCommandContext extends ParserRuleContext {
		public TerminalNode ID() { return getToken(MiniPascalParser.ID, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public AssignmentCommandContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_assignmentCommand; }
	}

	public final AssignmentCommandContext assignmentCommand() throws RecognitionException {
		AssignmentCommandContext _localctx = new AssignmentCommandContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_assignmentCommand);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(154);
			match(ID);
			setState(155);
			match(T__24);
			setState(156);
			expression(0);
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
	public static class BlockCommandContext extends ParserRuleContext {
		public List<CommandContext> command() {
			return getRuleContexts(CommandContext.class);
		}
		public CommandContext command(int i) {
			return getRuleContext(CommandContext.class,i);
		}
		public BlockCommandContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_blockCommand; }
	}

	public final BlockCommandContext blockCommand() throws RecognitionException {
		BlockCommandContext _localctx = new BlockCommandContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_blockCommand);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(158);
			match(T__25);
			setState(159);
			command();
			setState(164);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,16,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(160);
					match(T__1);
					setState(161);
					command();
					}
					} 
				}
				setState(166);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,16,_ctx);
			}
			setState(168);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__1) {
				{
				setState(167);
				match(T__1);
				}
			}

			setState(170);
			match(T__26);
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
	public static class IfCommandContext extends ParserRuleContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public List<CommandContext> command() {
			return getRuleContexts(CommandContext.class);
		}
		public CommandContext command(int i) {
			return getRuleContext(CommandContext.class,i);
		}
		public IfCommandContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_ifCommand; }
	}

	public final IfCommandContext ifCommand() throws RecognitionException {
		IfCommandContext _localctx = new IfCommandContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_ifCommand);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(172);
			match(T__27);
			setState(173);
			expression(0);
			setState(174);
			match(T__28);
			setState(175);
			command();
			setState(178);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,18,_ctx) ) {
			case 1:
				{
				setState(176);
				match(T__29);
				setState(177);
				command();
				}
				break;
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
	public static class WhileCommandContext extends ParserRuleContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public CommandContext command() {
			return getRuleContext(CommandContext.class,0);
		}
		public WhileCommandContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_whileCommand; }
	}

	public final WhileCommandContext whileCommand() throws RecognitionException {
		WhileCommandContext _localctx = new WhileCommandContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_whileCommand);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(180);
			match(T__30);
			setState(181);
			expression(0);
			setState(182);
			match(T__31);
			setState(183);
			command();
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
	public static class RepeatCommandContext extends ParserRuleContext {
		public CommandContext command() {
			return getRuleContext(CommandContext.class,0);
		}
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public RepeatCommandContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_repeatCommand; }
	}

	public final RepeatCommandContext repeatCommand() throws RecognitionException {
		RepeatCommandContext _localctx = new RepeatCommandContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_repeatCommand);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(185);
			match(T__32);
			setState(186);
			command();
			setState(187);
			match(T__33);
			setState(188);
			expression(0);
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
	public static class CallCommandContext extends ParserRuleContext {
		public TerminalNode ID() { return getToken(MiniPascalParser.ID, 0); }
		public ArgumentsContext arguments() {
			return getRuleContext(ArgumentsContext.class,0);
		}
		public CallCommandContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_callCommand; }
	}

	public final CallCommandContext callCommand() throws RecognitionException {
		CallCommandContext _localctx = new CallCommandContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_callCommand);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(190);
			match(ID);
			setState(191);
			match(T__7);
			setState(193);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & 78065325574400L) != 0)) {
				{
				setState(192);
				arguments();
				}
			}

			setState(195);
			match(T__8);
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
	public static class ReadCommandContext extends ParserRuleContext {
		public TerminalNode ID() { return getToken(MiniPascalParser.ID, 0); }
		public ReadCommandContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_readCommand; }
	}

	public final ReadCommandContext readCommand() throws RecognitionException {
		ReadCommandContext _localctx = new ReadCommandContext(_ctx, getState());
		enterRule(_localctx, 28, RULE_readCommand);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(197);
			_la = _input.LA(1);
			if ( !(_la==T__34 || _la==T__35) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			setState(198);
			match(T__7);
			setState(199);
			match(ID);
			setState(200);
			match(T__8);
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
	public static class WriteCommandContext extends ParserRuleContext {
		public ArgumentsContext arguments() {
			return getRuleContext(ArgumentsContext.class,0);
		}
		public WriteCommandContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_writeCommand; }
	}

	public final WriteCommandContext writeCommand() throws RecognitionException {
		WriteCommandContext _localctx = new WriteCommandContext(_ctx, getState());
		enterRule(_localctx, 30, RULE_writeCommand);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(202);
			_la = _input.LA(1);
			if ( !(_la==T__36 || _la==T__37) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			setState(203);
			match(T__7);
			setState(204);
			arguments();
			setState(205);
			match(T__8);
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
	public static class ArgumentsContext extends ParserRuleContext {
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public ArgumentsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_arguments; }
	}

	public final ArgumentsContext arguments() throws RecognitionException {
		ArgumentsContext _localctx = new ArgumentsContext(_ctx, getState());
		enterRule(_localctx, 32, RULE_arguments);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(207);
			expression(0);
			setState(212);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__4) {
				{
				{
				setState(208);
				match(T__4);
				setState(209);
				expression(0);
				}
				}
				setState(214);
				_errHandler.sync(this);
				_la = _input.LA(1);
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

	public boolean sempred(RuleContext _localctx, int ruleIndex, int predIndex) {
		switch (ruleIndex) {
		case 6:
			return expression_sempred((ExpressionContext)_localctx, predIndex);
		}
		return true;
	}
	private boolean expression_sempred(ExpressionContext _localctx, int predIndex) {
		switch (predIndex) {
		case 0:
			return precpred(_ctx, 8);
		case 1:
			return precpred(_ctx, 7);
		case 2:
			return precpred(_ctx, 6);
		}
		return true;
	}

	public static final String _serializedATN =
		"\u0004\u0001.\u00d8\u0002\u0000\u0007\u0000\u0002\u0001\u0007\u0001\u0002"+
		"\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002\u0004\u0007\u0004\u0002"+
		"\u0005\u0007\u0005\u0002\u0006\u0007\u0006\u0002\u0007\u0007\u0007\u0002"+
		"\b\u0007\b\u0002\t\u0007\t\u0002\n\u0007\n\u0002\u000b\u0007\u000b\u0002"+
		"\f\u0007\f\u0002\r\u0007\r\u0002\u000e\u0007\u000e\u0002\u000f\u0007\u000f"+
		"\u0002\u0010\u0007\u0010\u0001\u0000\u0001\u0000\u0001\u0000\u0001\u0000"+
		"\u0003\u0000\'\b\u0000\u0001\u0000\u0003\u0000*\b\u0000\u0001\u0000\u0001"+
		"\u0000\u0001\u0000\u0001\u0000\u0001\u0001\u0001\u0001\u0001\u0001\u0001"+
		"\u0001\u0004\u00014\b\u0001\u000b\u0001\f\u00015\u0001\u0002\u0001\u0002"+
		"\u0001\u0002\u0005\u0002;\b\u0002\n\u0002\f\u0002>\t\u0002\u0001\u0002"+
		"\u0001\u0002\u0001\u0002\u0001\u0003\u0004\u0003D\b\u0003\u000b\u0003"+
		"\f\u0003E\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0003\u0004"+
		"L\b\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004"+
		"\u0003\u0004S\b\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004"+
		"\u0001\u0004\u0001\u0004\u0001\u0004\u0003\u0004\\\b\u0004\u0001\u0004"+
		"\u0001\u0004\u0001\u0004\u0003\u0004a\b\u0004\u0001\u0004\u0001\u0004"+
		"\u0001\u0004\u0003\u0004f\b\u0004\u0001\u0005\u0001\u0005\u0001\u0005"+
		"\u0005\u0005k\b\u0005\n\u0005\f\u0005n\t\u0005\u0001\u0006\u0001\u0006"+
		"\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006"+
		"\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0003\u0006"+
		"}\b\u0006\u0001\u0006\u0001\u0006\u0003\u0006\u0081\b\u0006\u0001\u0006"+
		"\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006"+
		"\u0001\u0006\u0001\u0006\u0005\u0006\u008c\b\u0006\n\u0006\f\u0006\u008f"+
		"\t\u0006\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001"+
		"\u0007\u0001\u0007\u0001\u0007\u0003\u0007\u0099\b\u0007\u0001\b\u0001"+
		"\b\u0001\b\u0001\b\u0001\t\u0001\t\u0001\t\u0001\t\u0005\t\u00a3\b\t\n"+
		"\t\f\t\u00a6\t\t\u0001\t\u0003\t\u00a9\b\t\u0001\t\u0001\t\u0001\n\u0001"+
		"\n\u0001\n\u0001\n\u0001\n\u0001\n\u0003\n\u00b3\b\n\u0001\u000b\u0001"+
		"\u000b\u0001\u000b\u0001\u000b\u0001\u000b\u0001\f\u0001\f\u0001\f\u0001"+
		"\f\u0001\f\u0001\r\u0001\r\u0001\r\u0003\r\u00c2\b\r\u0001\r\u0001\r\u0001"+
		"\u000e\u0001\u000e\u0001\u000e\u0001\u000e\u0001\u000e\u0001\u000f\u0001"+
		"\u000f\u0001\u000f\u0001\u000f\u0001\u000f\u0001\u0010\u0001\u0010\u0001"+
		"\u0010\u0005\u0010\u00d3\b\u0010\n\u0010\f\u0010\u00d6\t\u0010\u0001\u0010"+
		"\u0000\u0001\f\u0011\u0000\u0002\u0004\u0006\b\n\f\u000e\u0010\u0012\u0014"+
		"\u0016\u0018\u001a\u001c\u001e \u0000\u0005\u0001\u0000\f\u000e\u0001"+
		"\u0000\u000f\u0012\u0001\u0000\u0013\u0018\u0001\u0000#$\u0001\u0000%"+
		"&\u00e7\u0000\"\u0001\u0000\u0000\u0000\u0002/\u0001\u0000\u0000\u0000"+
		"\u00047\u0001\u0000\u0000\u0000\u0006C\u0001\u0000\u0000\u0000\be\u0001"+
		"\u0000\u0000\u0000\ng\u0001\u0000\u0000\u0000\f\u0080\u0001\u0000\u0000"+
		"\u0000\u000e\u0098\u0001\u0000\u0000\u0000\u0010\u009a\u0001\u0000\u0000"+
		"\u0000\u0012\u009e\u0001\u0000\u0000\u0000\u0014\u00ac\u0001\u0000\u0000"+
		"\u0000\u0016\u00b4\u0001\u0000\u0000\u0000\u0018\u00b9\u0001\u0000\u0000"+
		"\u0000\u001a\u00be\u0001\u0000\u0000\u0000\u001c\u00c5\u0001\u0000\u0000"+
		"\u0000\u001e\u00ca\u0001\u0000\u0000\u0000 \u00cf\u0001\u0000\u0000\u0000"+
		"\"#\u0005\u0001\u0000\u0000#$\u0005)\u0000\u0000$&\u0005\u0002\u0000\u0000"+
		"%\'\u0003\u0002\u0001\u0000&%\u0001\u0000\u0000\u0000&\'\u0001\u0000\u0000"+
		"\u0000\')\u0001\u0000\u0000\u0000(*\u0003\u0006\u0003\u0000)(\u0001\u0000"+
		"\u0000\u0000)*\u0001\u0000\u0000\u0000*+\u0001\u0000\u0000\u0000+,\u0003"+
		"\u0012\t\u0000,-\u0005\u0003\u0000\u0000-.\u0005\u0000\u0000\u0001.\u0001"+
		"\u0001\u0000\u0000\u0000/3\u0005\u0004\u0000\u000001\u0003\u0004\u0002"+
		"\u000012\u0005\u0002\u0000\u000024\u0001\u0000\u0000\u000030\u0001\u0000"+
		"\u0000\u000045\u0001\u0000\u0000\u000053\u0001\u0000\u0000\u000056\u0001"+
		"\u0000\u0000\u00006\u0003\u0001\u0000\u0000\u00007<\u0005)\u0000\u0000"+
		"89\u0005\u0005\u0000\u00009;\u0005)\u0000\u0000:8\u0001\u0000\u0000\u0000"+
		";>\u0001\u0000\u0000\u0000<:\u0001\u0000\u0000\u0000<=\u0001\u0000\u0000"+
		"\u0000=?\u0001\u0000\u0000\u0000><\u0001\u0000\u0000\u0000?@\u0005\u0006"+
		"\u0000\u0000@A\u0005\'\u0000\u0000A\u0005\u0001\u0000\u0000\u0000BD\u0003"+
		"\b\u0004\u0000CB\u0001\u0000\u0000\u0000DE\u0001\u0000\u0000\u0000EC\u0001"+
		"\u0000\u0000\u0000EF\u0001\u0000\u0000\u0000F\u0007\u0001\u0000\u0000"+
		"\u0000GH\u0005\u0007\u0000\u0000HI\u0005)\u0000\u0000IK\u0005\b\u0000"+
		"\u0000JL\u0003\n\u0005\u0000KJ\u0001\u0000\u0000\u0000KL\u0001\u0000\u0000"+
		"\u0000LM\u0001\u0000\u0000\u0000MN\u0005\t\u0000\u0000NO\u0005\u0006\u0000"+
		"\u0000OP\u0005\'\u0000\u0000PR\u0005\u0002\u0000\u0000QS\u0003\u0002\u0001"+
		"\u0000RQ\u0001\u0000\u0000\u0000RS\u0001\u0000\u0000\u0000ST\u0001\u0000"+
		"\u0000\u0000TU\u0003\u0012\t\u0000UV\u0005\u0002\u0000\u0000Vf\u0001\u0000"+
		"\u0000\u0000WX\u0005\n\u0000\u0000XY\u0005)\u0000\u0000Y[\u0005\b\u0000"+
		"\u0000Z\\\u0003\n\u0005\u0000[Z\u0001\u0000\u0000\u0000[\\\u0001\u0000"+
		"\u0000\u0000\\]\u0001\u0000\u0000\u0000]^\u0005\t\u0000\u0000^`\u0005"+
		"\u0002\u0000\u0000_a\u0003\u0002\u0001\u0000`_\u0001\u0000\u0000\u0000"+
		"`a\u0001\u0000\u0000\u0000ab\u0001\u0000\u0000\u0000bc\u0003\u0012\t\u0000"+
		"cd\u0005\u0002\u0000\u0000df\u0001\u0000\u0000\u0000eG\u0001\u0000\u0000"+
		"\u0000eW\u0001\u0000\u0000\u0000f\t\u0001\u0000\u0000\u0000gl\u0003\u0004"+
		"\u0002\u0000hi\u0005\u0002\u0000\u0000ik\u0003\u0004\u0002\u0000jh\u0001"+
		"\u0000\u0000\u0000kn\u0001\u0000\u0000\u0000lj\u0001\u0000\u0000\u0000"+
		"lm\u0001\u0000\u0000\u0000m\u000b\u0001\u0000\u0000\u0000nl\u0001\u0000"+
		"\u0000\u0000op\u0006\u0006\uffff\uffff\u0000pq\u0005\b\u0000\u0000qr\u0003"+
		"\f\u0006\u0000rs\u0005\t\u0000\u0000s\u0081\u0001\u0000\u0000\u0000tu"+
		"\u0005\u000b\u0000\u0000u\u0081\u0003\f\u0006\tv\u0081\u0005*\u0000\u0000"+
		"w\u0081\u0005(\u0000\u0000x\u0081\u0005.\u0000\u0000yz\u0005)\u0000\u0000"+
		"z|\u0005\b\u0000\u0000{}\u0003 \u0010\u0000|{\u0001\u0000\u0000\u0000"+
		"|}\u0001\u0000\u0000\u0000}~\u0001\u0000\u0000\u0000~\u0081\u0005\t\u0000"+
		"\u0000\u007f\u0081\u0005)\u0000\u0000\u0080o\u0001\u0000\u0000\u0000\u0080"+
		"t\u0001\u0000\u0000\u0000\u0080v\u0001\u0000\u0000\u0000\u0080w\u0001"+
		"\u0000\u0000\u0000\u0080x\u0001\u0000\u0000\u0000\u0080y\u0001\u0000\u0000"+
		"\u0000\u0080\u007f\u0001\u0000\u0000\u0000\u0081\u008d\u0001\u0000\u0000"+
		"\u0000\u0082\u0083\n\b\u0000\u0000\u0083\u0084\u0007\u0000\u0000\u0000"+
		"\u0084\u008c\u0003\f\u0006\t\u0085\u0086\n\u0007\u0000\u0000\u0086\u0087"+
		"\u0007\u0001\u0000\u0000\u0087\u008c\u0003\f\u0006\b\u0088\u0089\n\u0006"+
		"\u0000\u0000\u0089\u008a\u0007\u0002\u0000\u0000\u008a\u008c\u0003\f\u0006"+
		"\u0007\u008b\u0082\u0001\u0000\u0000\u0000\u008b\u0085\u0001\u0000\u0000"+
		"\u0000\u008b\u0088\u0001\u0000\u0000\u0000\u008c\u008f\u0001\u0000\u0000"+
		"\u0000\u008d\u008b\u0001\u0000\u0000\u0000\u008d\u008e\u0001\u0000\u0000"+
		"\u0000\u008e\r\u0001\u0000\u0000\u0000\u008f\u008d\u0001\u0000\u0000\u0000"+
		"\u0090\u0099\u0003\u0010\b\u0000\u0091\u0099\u0003\u0014\n\u0000\u0092"+
		"\u0099\u0003\u0016\u000b\u0000\u0093\u0099\u0003\u0018\f\u0000\u0094\u0099"+
		"\u0003\u0012\t\u0000\u0095\u0099\u0003\u001a\r\u0000\u0096\u0099\u0003"+
		"\u001c\u000e\u0000\u0097\u0099\u0003\u001e\u000f\u0000\u0098\u0090\u0001"+
		"\u0000\u0000\u0000\u0098\u0091\u0001\u0000\u0000\u0000\u0098\u0092\u0001"+
		"\u0000\u0000\u0000\u0098\u0093\u0001\u0000\u0000\u0000\u0098\u0094\u0001"+
		"\u0000\u0000\u0000\u0098\u0095\u0001\u0000\u0000\u0000\u0098\u0096\u0001"+
		"\u0000\u0000\u0000\u0098\u0097\u0001\u0000\u0000\u0000\u0099\u000f\u0001"+
		"\u0000\u0000\u0000\u009a\u009b\u0005)\u0000\u0000\u009b\u009c\u0005\u0019"+
		"\u0000\u0000\u009c\u009d\u0003\f\u0006\u0000\u009d\u0011\u0001\u0000\u0000"+
		"\u0000\u009e\u009f\u0005\u001a\u0000\u0000\u009f\u00a4\u0003\u000e\u0007"+
		"\u0000\u00a0\u00a1\u0005\u0002\u0000\u0000\u00a1\u00a3\u0003\u000e\u0007"+
		"\u0000\u00a2\u00a0\u0001\u0000\u0000\u0000\u00a3\u00a6\u0001\u0000\u0000"+
		"\u0000\u00a4\u00a2\u0001\u0000\u0000\u0000\u00a4\u00a5\u0001\u0000\u0000"+
		"\u0000\u00a5\u00a8\u0001\u0000\u0000\u0000\u00a6\u00a4\u0001\u0000\u0000"+
		"\u0000\u00a7\u00a9\u0005\u0002\u0000\u0000\u00a8\u00a7\u0001\u0000\u0000"+
		"\u0000\u00a8\u00a9\u0001\u0000\u0000\u0000\u00a9\u00aa\u0001\u0000\u0000"+
		"\u0000\u00aa\u00ab\u0005\u001b\u0000\u0000\u00ab\u0013\u0001\u0000\u0000"+
		"\u0000\u00ac\u00ad\u0005\u001c\u0000\u0000\u00ad\u00ae\u0003\f\u0006\u0000"+
		"\u00ae\u00af\u0005\u001d\u0000\u0000\u00af\u00b2\u0003\u000e\u0007\u0000"+
		"\u00b0\u00b1\u0005\u001e\u0000\u0000\u00b1\u00b3\u0003\u000e\u0007\u0000"+
		"\u00b2\u00b0\u0001\u0000\u0000\u0000\u00b2\u00b3\u0001\u0000\u0000\u0000"+
		"\u00b3\u0015\u0001\u0000\u0000\u0000\u00b4\u00b5\u0005\u001f\u0000\u0000"+
		"\u00b5\u00b6\u0003\f\u0006\u0000\u00b6\u00b7\u0005 \u0000\u0000\u00b7"+
		"\u00b8\u0003\u000e\u0007\u0000\u00b8\u0017\u0001\u0000\u0000\u0000\u00b9"+
		"\u00ba\u0005!\u0000\u0000\u00ba\u00bb\u0003\u000e\u0007\u0000\u00bb\u00bc"+
		"\u0005\"\u0000\u0000\u00bc\u00bd\u0003\f\u0006\u0000\u00bd\u0019\u0001"+
		"\u0000\u0000\u0000\u00be\u00bf\u0005)\u0000\u0000\u00bf\u00c1\u0005\b"+
		"\u0000\u0000\u00c0\u00c2\u0003 \u0010\u0000\u00c1\u00c0\u0001\u0000\u0000"+
		"\u0000\u00c1\u00c2\u0001\u0000\u0000\u0000\u00c2\u00c3\u0001\u0000\u0000"+
		"\u0000\u00c3\u00c4\u0005\t\u0000\u0000\u00c4\u001b\u0001\u0000\u0000\u0000"+
		"\u00c5\u00c6\u0007\u0003\u0000\u0000\u00c6\u00c7\u0005\b\u0000\u0000\u00c7"+
		"\u00c8\u0005)\u0000\u0000\u00c8\u00c9\u0005\t\u0000\u0000\u00c9\u001d"+
		"\u0001\u0000\u0000\u0000\u00ca\u00cb\u0007\u0004\u0000\u0000\u00cb\u00cc"+
		"\u0005\b\u0000\u0000\u00cc\u00cd\u0003 \u0010\u0000\u00cd\u00ce\u0005"+
		"\t\u0000\u0000\u00ce\u001f\u0001\u0000\u0000\u0000\u00cf\u00d4\u0003\f"+
		"\u0006\u0000\u00d0\u00d1\u0005\u0005\u0000\u0000\u00d1\u00d3\u0003\f\u0006"+
		"\u0000\u00d2\u00d0\u0001\u0000\u0000\u0000\u00d3\u00d6\u0001\u0000\u0000"+
		"\u0000\u00d4\u00d2\u0001\u0000\u0000\u0000\u00d4\u00d5\u0001\u0000\u0000"+
		"\u0000\u00d5!\u0001\u0000\u0000\u0000\u00d6\u00d4\u0001\u0000\u0000\u0000"+
		"\u0015&)5<EKR[`el|\u0080\u008b\u008d\u0098\u00a4\u00a8\u00b2\u00c1\u00d4";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}