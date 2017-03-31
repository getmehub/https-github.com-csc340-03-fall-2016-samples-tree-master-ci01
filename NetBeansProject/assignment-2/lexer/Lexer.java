package lexer;

/**
 * The Lexer class is responsible for scanning the source file
 * which is a stream of characters and returning a stream of
 * tokens; each token object will contain the string (or access
 * to the string) that describes the token along with an
 * indication of its location in the source program to be used
 * for error reporting; we are tracking line numbers; white spaces
 * are space, tab, newlines
 */
public class Lexer {
    private boolean atEOF = false;
    private char ch;
    private SourceReader source;

    
    private int startPosition, endPosition, linePosition;

    public Lexer( String sourceFile ) throws Exception {
       
        new TokenType( );
        source = new SourceReader( sourceFile );
        ch = source.read( );
    }

    public static void main( String args[] ) {
        Token tok;

        try {


            if ( args.length < 1 ) {
                System.err.println( "No argument found" );
                System.exit( 1 );
            }
            Lexer lex = new Lexer( args[ 0 ] );
            while ( true ) {
                tok = lex.nextToken( );
                String p = String.format( "%1$-15s Left: %2$-5d Right: %3$-5d Line: %4$-5d",
                        tok.getSymbol( ),
                        tok.getLeftPosition( ),
                        tok.getRightPosition( ),
                        lex.source.getLineno( ) );

                System.out.println( p );

            }

        } catch ( Exception e ) {
        }
    }


    /**
     * newIdTokens are either ids or reserved words; new id's will be inserted
     * in the symbol table with an indication that they are id's
     *
     * @param id            is the String just scanned - it's either an id or reserved word
     * @param startPosition is the column in the source file where the token begins
     * @param endPosition   is the column in the source file where the token ends
     * @return the Token; either an id or one for the reserved words
     */
    public Token newIdToken( String id, int startPosition, int endPosition, int linePosition ) {
        return new Token( startPosition, endPosition, linePosition, Symbol.symbol( id, Tokens.Identifier ) );
    }

    /**
     * number tokens are inserted in the symbol table; we don't convert the
     * numeric strings to numbers until we load the bytecodes for interpreting;
     * this ensures that any machine numeric dependencies are deferred
     * until we actually run the program; i.e. the numeric constraints of the
     * hardware used to compile the source program are not used
     *
     * @param number        is the int String just scanned
     * @param startPosition is the column in the source file where the int begins
     * @param endPosition   is the column in the source file where the int ends
     * @return the int Token
     */
    public Token newNumberToken( String number, int startPosition, int endPosition, int linePosition ) {
        return new Token(
                startPosition, endPosition, linePosition,
                Symbol.symbol( number, Tokens.INTeger )
        );
    }

    /**
     * build the token for operators (+ -) or separators (parens, braces)
     * filter out comments which begin with two slashes
     *
     * @param s             is the String representing the token
     * @param startPosition is the column in the source file where the token begins
     * @param endPosition   is the column in the source file where the token ends
     * @return the Token just found
     */
    public Token makeToken( String s, int startPosition, int endPosition ) {
        if ( s.equals( "//" ) ) {
            try {
                int oldLine = source.getLineno( );

                do {
                    ch = source.read( );
                } while ( oldLine == source.getLineno( ) );
            } catch ( Exception e ) {
                atEOF = true;
            }

            return nextToken( );
        }

        // be sure it's a valid token
        Symbol sym = Symbol.symbol( s, Tokens.BogusToken );
        if ( sym == null ) {
            System.out.println( "******** illegal character: " + s );
            atEOF = true;
            return nextToken( );
        }

        return new Token( startPosition, endPosition, linePosition, sym );
    }

    /**
     * @return the next Token found in the source file
     */
    public Token nextToken( ) {
      
        if ( atEOF ) {
            if ( source != null ) {
                source.printSource( ); 
                source.close( );
                source = null;
            }

            return null;
        }
        try {
            while ( Character.isWhitespace( ch ) ) {
                ch = source.read( );
            }
        } catch ( Exception e ) {
            atEOF = true;
            return nextToken( );
        }

        startPosition = source.getPosition( );
        endPosition = startPosition - 1;

        if ( Character.isJavaIdentifierStart( ch ) ) {
         
            String id = "";
            try {
                do {
                    endPosition++;
                    id += ch;
                    ch = source.read( );
                } while ( Character.isJavaIdentifierPart( ch ) );
            } catch ( Exception e ) {
                atEOF = true;
            }

            return newIdToken( id, startPosition, endPosition, linePosition );
        }

        if ( Character.isDigit( ch ) ) {
            
            String number = "";
            try {
                do {
                    endPosition++;
                    number += ch;
                    ch = source.read( );
                } while ( Character.isDigit( ch ) );
            } catch ( Exception e ) {
                atEOF = true;
            }

            return newNumberToken( number, startPosition, endPosition, linePosition );
        }
        String charOld = "" + ch;
        String op = charOld;
        Symbol sym;
        try {
            endPosition++;
            ch = source.read( );
            op += ch;
           
            sym = Symbol.symbol( op, Tokens.BogusToken );

            if ( sym == null ) {
                
                return makeToken( charOld, startPosition, endPosition );
            }

            endPosition++;
            ch = source.read( );
            return makeToken( op, startPosition, endPosition );
        } catch ( Exception e ) {
        }

        atEOF = true;
        if ( startPosition == endPosition ) {
            op = charOld;
        }
        return makeToken( op, startPosition, endPosition );
    }
}
