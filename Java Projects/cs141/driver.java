package cs141;

import java.util.Scanner;

public class driver {


    // Set up some global variables so I can have multiple lines of

    // the test program work on common variables.  Should only need at

    // most 3 of each.

    public static Fraction               F;

    public static MixedFraction         MF;

    public static int           line_count = 0;


    public static void main( String[] args ) {

	Scanner           k = new Scanner( System.in );

	String         line;

	String[]      parts;


	System.out.println();

	while ( k.hasNext() ) {

	    line_count++;

	    line = k.nextLine();

	    if ( line.matches( "^\\s*//.*" ) ) continue; // skip comments

	    if ( line.matches( "^\\s*$" ) ) continue;    // skip blank lines

	    parts = line.split( "\\s+" ); // Split on one or more whitespace

	    if ( parts[0].toLowerCase().charAt(0) == 'f' )
 
		fc_test( parts );

	    else if ( parts[0].toLowerCase().charAt(0) == 'm' )
 
		mfc_test( parts );

	    else

		log( "Unable to determine class test...skipping" );

	}

	log( "Test complete." );

	System.out.println();

    }


    public static void log( String S ) {

	String str = "Line " + String.format( "%3s", ""+line_count ) + ": ";

	
	str += String.format( "%-70s", S ) + "   " + "(F:" + F + ";MF:" + MF + ")";

	System.out.println( str );

    }


    public static void log( boolean status, String S ) {

	int len = ( status ) ? 7 : 4;

	String tmp = "  " + S;


	len += S.length();

	log( String.format( "%70s",
 
			    tmp
 
			    + String.format( "%" + ( 70 - len ) + "s",

					     (status)?"SUCCESS":"FAIL").replace(' ','.')));

    }

    
    // Interesting coincidence...all methods in Fraction except the

    // constructor start with a different letter!

    public static void fc_test( String[] parts ) {

	char c = parts[1].toLowerCase().charAt(0);


	switch ( c ) {
	case 'f' : fc_constructor( parts ); break;

	case 'i' : fc_inverse( parts ); break;

	case 'n' : fc_negate( parts ); break;

	case 'a' : fc_add( parts ); break;

	case 's' : fc_sub( parts ); break;

	case 'm' : fc_mul( parts ); break;

	case 'd' : fc_div( parts ); break;

	case 't' : fc_toString( parts ); break;

	case 'e' : fc_equals( parts ); break;

	default : log( "Invalid Fraction method found: " + parts[1] );

	}

    }


    public static boolean fc_prep( String method, String[] parts, int numArgs ) {

	log( "Testing Fraction " + method );

	if ( ( parts.length - 3 > numArgs ) ||

	     ( parts.length - 3 < numArgs ) ) {

	    log( false, "Fraction '" + method + "' test was given incorrect arguments" );

	    return false;

	}
 
	if ( F == null ) {

	    log( "  Fraction object F was null, setting to 1/2" );

	    try {

		F = new Fraction( 1, 2 );

	    } catch ( Exception e ) {

		log( false, "Unable to test '" + method + "'; creation of " +

		     "  Fraction object F raised exception" );

		return false;

	    }

	    log( "  Fraction object F is now 1/2, continuing with '" + method + "' test" );

	} else {

	    log( "  Fraction object F is currently " + F.toString() );

	}

	return true;

    }


    public static void fc_result( String[] parts ) {

	String[] answer = parts[ parts.length - 1 ].split( "," );

	
	if ( F.toString().equals( answer[0] + "/" + answer[1] ) ) {

	    log( true, "Test matched expected result " +

		 answer[0] + "/" + answer[1] );

	} else {

	    log( false, "Test failed matching expected result " +

		 answer[0] + "/" + answer[1] );

	}

    }


    public static void fc_constructor( String[] parts ) {

	int     numArgs = parts.length - 3;

	String  argsStr = ( numArgs == 1 ) ? parts[2] : parts[2] + " " + parts[3];

	int        ARG1 = Integer.parseInt( parts[2] );

	int        ARG2 = ( numArgs == 1 ) ? 0 : Integer.parseInt( parts[3] );


	log( "Testing Fraction constructor with args: " + argsStr );

	if ( numArgs > 2 || numArgs < 1 ) {

	    log( false, "Invalid number of arguments" );

	    return;

	}

	if ( numArgs == 1 ) {

	    try {

		F = new Fraction( ARG1 );

		log( "  Fraction object F created from Integer " + argsStr );

		fc_result( parts );

	    } catch ( Exception e ) {

		log( false, "Exception raised creating Fraction from Integer "

		     + argsStr );

	    }

	} else {

	    try {

		F = new Fraction( ARG1, ARG2 );

		log( "  Fraction object F created from " + argsStr );

		fc_result( parts );

	    } catch ( Exception e ) {

		log( false, "Exception raised creating Fraction from "

		     + argsStr );

	    }

	}
 
    }


    public static void fc_inverse( String[] parts ) {

	if ( ! fc_prep( "inverse", parts, 0 ) ) return;

	try {
	    F = F.inverse();

	} catch ( Exception e ) {

	    if ( F.toString().charAt(0) == '0' ) {

		log( true, "Fraction 'inverse' raised exception when" +

		     " F numerator was 0" );

	    } else {

		log( false, "Fraction 'inverse' raised exception" );


	    }

	}

	fc_result( parts );

    }


    public static void fc_negate( String[] parts ) {

	if ( ! fc_prep( "negate", parts, 0 ) ) return;

	try {

	    F = F.negate();

	} catch ( Exception e ) {

	    log( false, "Fraction 'inverse' raised exception" );

	    return;

	}

	fc_result( parts );

    }


    public static void fc_add( String[] parts ) {

	Fraction tmp;

	String[] values;


	if ( ! fc_prep( "add", parts, 1 ) ) return;

	values = parts[2].split( "," );

	if ( values.length != 2 ) {

	    log( false, "Fraction 'add' test has invalid arguments" );

	    return;

	}

	try {

	    tmp = new Fraction( Integer.parseInt( values[0] ),
 
				Integer.parseInt( values[1] ) );

	    log( "  Created tmp Fraction object " + tmp );

	} catch ( Exception e ) {

	    log( false, "Failed to create tmp Fraction object " + parts[2] );

	    return;

	}

	try {

	    F = F.add( tmp );

	    log( "  F object is now " + F );

	} catch ( Exception e ) {

	    log( false, "Fraction 'add' raised exception" );

	    return;

	}

	fc_result( parts );

    }


    public static void fc_sub( String[] parts ) {

	Fraction tmp;

	String[] values;


	if ( ! fc_prep( "subtract", parts, 1 ) ) return;

	values = parts[2].split( "," );

	if ( values.length != 2 ) {

	    log( false, "Fraction 'subtract' test has invalid arguments" );

	    return;

	}

	try {

	    tmp = new Fraction( Integer.parseInt( values[0] ), 

				Integer.parseInt( values[1] ) );

	    log( "  Created tmp Fraction object " + tmp );

	} catch ( Exception e ) {

	    log( false, "Failed to create tmp Fraction object " + parts[2] );

	    return;

	}

	try {

	    F = F.subtract( tmp );

	    log( "  F object is now " + F );

	} catch ( Exception e ) {

	    log( false, "Fraction 'subtract' raised exception" );

	    return;

	}

	fc_result( parts );

    }


    public static void fc_mul( String[] parts ) {

	Fraction tmp;

	String[] values;


	if ( ! fc_prep( "multiply", parts, 1 ) ) return;

	values = parts[2].split( "," );

	if ( values.length != 2 ) {

	    log( false, "Fraction 'multiply' test has invalid arguments" );

	    return;

	}

	try {

	    tmp = new Fraction( Integer.parseInt( values[0] ),
 
				Integer.parseInt( values[1] ) );

	    log( "  Created tmp Fraction object " + tmp );

	} catch ( Exception e ) {

	    log( false, "Failed to create tmp Fraction object " + parts[2] );

	    return;

	}

	try {

	    F = F.multiply( tmp );

	    log( "  F object is now " + F );

	} catch ( Exception e ) {

	    log( false, "Fraction 'multiply' raised exception" );

	    return;

	}

	fc_result( parts );

    }


    public static void fc_div( String[] parts ) {

	Fraction tmp;

	String[] values;


	if ( ! fc_prep( "divide", parts, 1 ) ) return;

	values = parts[2].split( "," );

	if ( values.length != 2 ) {

	    log( false, "Fraction 'divide' test has invalid arguments" );

	    return;

	}

	try {

	    tmp = new Fraction( Integer.parseInt( values[0] ),
 
				Integer.parseInt( values[1] ) );

	    log( "  Created tmp Fraction object " + tmp );

	} catch ( Exception e ) {

	    log( false, "Failed to create tmp Fraction object " + parts[2] );

	    return;

	}

	try {

	    F = F.divide( tmp );

	    log( "  F object is now " + F );

	} catch ( Exception e ) {

	    if ( tmp.toString().charAt(0) == '0' )

		log( true, "Fraction 'divide' raised exception on div by 0" );

	    else

		log( false, "Fraction 'divide' raised exception" );

	    return;

	}

	fc_result( parts );

    }


  // Redundant as fc_result and prep rely on toString working.

    public static void fc_toString( String[] parts ) {

	if ( ! fc_prep( "toString", parts, 0 ) ) return;

	fc_result( parts );

    }


    public static void fc_equals( String[] parts ) {

	boolean result = parts[ parts.length - 1 ].toLowerCase().charAt(0) == 't';
	
	String[] values;

	Fraction tmpF;


	if ( ! fc_prep( "equals", parts, 1 ) ) return;

	values = parts[2].split( "," );

	if ( values.length != 2 ) {

	    log( false, "Invalid argument syntax" );

	    return;

	}

	try {

	    tmpF = new Fraction( Integer.parseInt( values[0] ),

				 Integer.parseInt( values[1] ) );

	    log( "  Temp Fraction object created: " + tmpF );

	} catch ( Exception e ) {

	    if ( Integer.parseInt( values[1] ) == 0 ) 

		log( true, "Fraction constructor raised valid exception" );

	    else

		log( false, "Fraction constructor raised exception " );

	    return;

	}

	try {

	    if ( F.equals( tmpF ) == result )
 
		log( true, "Test matched expected result " + result );

	    else

		log( false, "Test failed matching expected result " + result );

	} catch ( Exception e ) {

	    log( false, "MixedFraction 'equals' raised exception" );

	}

    }


    public static void mfc_test( String[] parts ) {

	char c = parts[1].toLowerCase().charAt(0);

	
	if ( c == 't' ) c = parts[1].toLowerCase().charAt(7);

	if ( c == 'm' ) c = parts[1].toLowerCase().charAt(1);


	switch ( c ) {
	case 'i' : mfc_constructor( parts ); break;

	case 'p' : mfc_toImproper( parts ); break;

	case 'n' : mfc_negate( parts ); break;

	case 'a' : mfc_add( parts ); break;

	case 's' : mfc_sub( parts ); break;

	case 'u' : mfc_mul( parts ); break;

	case 'd' : mfc_div( parts ); break;


	case 'g' : mfc_toString( parts ); break;

	case 'e' : mfc_equals( parts ); break;

	default : log( "Invalid MixedFraction method found: " + parts[1] );

	}

    }


    public static boolean mfc_prep( String method, String[] parts, int numArgs ) {

	log( "Testing MixedFraction " + method );

	if ( ( parts.length - 3 > numArgs ) ||

	     ( parts.length - 3 < numArgs ) ) {

	    log( false, "MixedFraction '" + method
 
		 + "' test was given incorrect arguments" );

	    return false;

	} 

	if ( MF == null ) {

	    log( "  MixedFraction object MF was null, setting to 1 1/2" );

	    try {

		MF = new MixedFraction( 1, 1, 2 );

	    } catch ( Exception e ) {

		log( false, "Unable to test '" + method + "'; creation of " +

		     "MixedFraction object MF raised exception" );

		return false;

	    }

	    log( "  MixedFraction object MF is now 1 1/2, continuing with '" +
 
		 method + "' test" );

	} else {

	    log( "  MixedFraction object MF is currently " + MF.toString() );

	}

	return true;

    }


    public static void mfc_result( String[] parts ) {

	String[] answer = parts[ parts.length - 1 ].split( "," );

	
	if ( MF.toString().equals( answer[0] + " " + answer[1] + "/" + answer[2] ) ) {

	    log( true, "Test matched expected result " +

	 answer[0] + " " + answer[1] + "/" + answer[2] );

	} else {

	    log( false, "Test failed matching expected result " +

		 answer[0] + " " + answer[1] + "/" + answer[2] );

	}

    }


    public static void mfc_constructor( String[] parts ) {

	int numArgs = parts.length - 3;

	String argStr = "";


	if ( numArgs > 0 && numArgs < 4 ) {

	    for ( int i = 0; i < numArgs; i++ ) argStr += " " + parts[ 2 + i ];

	    log( "Testing MixedFraction constructor with args:" + argStr );

	} else {

	    log( "Testing MixedFraction constructor" );
	
    log( false, "Invalid arg count" );

	    return;

	}

	
	// 5 constructors to test

	switch ( numArgs ) {

	case 1 : mfc_con1( parts ); break;

	case 2 : mfc_con2( parts ); break;

	case 3 : mfc_con3( parts ); break;

	default : log( false, "You should never be seeing this.  Contact me!" );

	}
 
   }


    public static void mfc_con1( String[] parts ) {

	String[] values = parts[2].split( "," );


	if ( values.length == 1 ) {
 	// Integer

	    try {

		MF = new MixedFraction( Integer.parseInt( values[ 0 ] ) );

	log( "  MixedFraction constructor created MF : " + MF );

	    } catch ( Exception e ) {

		log( false, "MixedFraction constructor raised exception" );

		return;

	    }

	} else if ( values.length == 2 ) {
 // fraction

	    try {

		F = new Fraction( Integer.parseInt( values[ 0 ] ),

				  Integer.parseInt( values[ 1 ] ) );

	    } catch ( Exception e ) {
		if ( Integer.parseInt( values[1] ) == 0 )

		    log( true, "Fraction constructor raised valid exception "
 
			 + "during MFcreation" );

		else

		    log( false, "Fraction constructor raised exception "

			 + "during MF creation" );

		return;

	    }

	    try {

		MF = new MixedFraction( F );

		log( "  MixedFraction constructor created MF : " + MF );

	    } catch ( Exception e ) {

		log( false, "MixedFraction constructor raised exception" );

		return;

	    }

	} else {

	    log( false, "Incorrect arg syntax" ); return;

	}

	// Check if matches result

	mfc_result( parts );

    }


    public static void mfc_con2( String[] parts ) {

	int ARG1 = Integer.parseInt(parts[2] );

	String[] values = parts[3].split( "," );


	if ( values.length == 1 ) {

	    // Was given a numerator and a denominator

	    try {
		
			MF = new MixedFraction( ARG1,

					Integer.parseInt( values[0] ) );

		log( "  MixedFraction constructor created MF : " + MF );

	    } catch ( Exception e ) {

		if ( Integer.parseInt( values[0] ) == 0 )

		    log( true, "MixedFraction constructor raised exception "
				 + "on 0 denominator" );

		else

		    log( false, "MixedFraction constructor raised exception" );

		return;

	    }

	} else if ( values.length == 2 ) {

	    // Was given a whole and a fraction

	    try {

		F = new Fraction( Integer.parseInt( values[0] ),				  Integer.parseInt( values[1] ) );

		log( "  Fraction created F during MixedFraction constructor" );

	    } catch ( Exception e ) {

		if ( Integer.parseInt( values[1] ) == 0 )
 
		    log( true, "Fraction constructor raised valid exception "
 
			 + "during MF creation" );

		else

		    log( false, "Fraction constructor raised exception "

			 + "during MF creation" );

		return;

	    }

	    try {	
		MF = new MixedFraction( ARG1, F );

		log( "  MixedFraction constructor created MF : " + MF );

	    } catch ( Exception e ) {

		log( false, "MixedFraction constructor raised exception" );

		return;

	    }

	} else {

	    log( false, "Incorrect arg syntax" );

	    return;

	}

	mfc_result( parts );

    }


    public static void mfc_con3( String[] parts ) {

	int[] ARGS = new int[3];


	try {

	    for ( int i = 0; i < 3; i++ )
	 ARGS[ i ] = Integer.parseInt( parts[ 2 + i ] );

	} catch ( Exception e ) {

	    log( false, "Invalid argument syntax" );

	    return;

	}

	try {

	    MF = new MixedFraction( ARGS[0], ARGS[1], ARGS[2] );

	    log( "MixedFraction constructor created MF : " + MF );

	} catch ( Exception e ) {

	    if ( ARGS[2] == 0 )
 
		log( true, "MixedFraction constructor raised valid exception" );

	    else

		log( false, "MixedFraction constructor raised exception" );

	    return;

	}

	mfc_result( parts );

    }

    
    public static void mfc_toImproper( String[] parts ) {

	if ( ! mfc_prep( "toImproper", parts, 0 ) ) return;

	
	try {

	    F = MF.toImproper();

	    log( "Fraction returned from MixedFraction 'toImproper': " + F );

	} catch ( Exception e ) {

	    log( false, "MixedFraction 'toImproper' raised exception" );

	    return;

	}

	fc_result( parts );

    }


    public static void mfc_negate( String[] parts ) {

	if ( ! mfc_prep( "negate", parts, 0 ) ) return;

	
	try {

	    MF = MF.negate();

	} catch ( Exception e ) {

	    log( false, "MixedFraction 'negate' raised exception" );

	    return;

	}

	mfc_result( parts );

    }


    // Redundant as mfc_result and prep rely on toString working.

    public static void mfc_toString( String[] parts ) {

	if ( ! mfc_prep( "toString", parts, 0 ) ) return;

	mfc_result( parts );

    }


    public static void mfc_equals( String[] parts ) {

	boolean result = parts[ parts.length - 1 ].toLowerCase().charAt(0) == 't';

	String[] values;

	Fraction tmpF;

	MixedFraction tmpMF;

	if ( ! mfc_prep( "equals", parts, 1 ) ) return;


	values = parts[2].split( "," );

	if ( values.length < 2 || values.length > 3 ) {

	    log( false, "Invalid argument syntax" );

	    return;

	}

	if ( values.length == 2 ) {

	    try {

		tmpF = new Fraction( Integer.parseInt( values[0] ),
		      Integer.parseInt( values[1] ) );

		log( "  Temp Fraction object created to test MixedFraction 'equals': " + tmpF );

	    } catch ( Exception e ) {

		if ( Integer.parseInt( values[1] ) == 0 )
 
		    log( true, "Fraction constructor raised valid exception" );

		else

		    log( false, "Fraction constructor raised exception " );

		return;

	    }

	    try {

		if ( MF.equals( tmpF ) == result )
 
		    log( true, "Test matched expected result " + result );

		else

		    log( false, "Test failed matching expected result " + result );

	    } catch ( Exception e ) {

		log( false, "MixedFraction 'equals' raised exception" );

	    }

	} else {

	    try {

		tmpMF = new MixedFraction( Integer.parseInt( values[0] ),

					    Integer.parseInt( values[1] ),

					    Integer.parseInt( values[2] ) );

		log( "  Temp MixedFraction object created: " + tmpMF );

	    } catch ( Exception e ) {

		if ( Integer.parseInt( values[2] ) == 0 )
 
		   log( true, "MixedFraction constructor raised valid exception" );

		else

		    log( false, "MixedFraction constructor raised exception" );

		return;

	    }

	    try {

		if ( MF.equals( tmpMF ) == result ) 

		    log( true, "Test matched expected result " + result );

		else

			log( false, "Test failed matching expected result " + result );

	    } catch ( Exception e ) {

		log( false, "MixedFraction 'equals' raised exception" );

	    }

	}

    }


    public static void mfc_add( String[] parts ) {

	MixedFraction tmp;

	String[] values;


	if ( ! mfc_prep( "add", parts, 1 ) ) return;

	values = parts[2].split( "," );

	if ( values.length != 3 ) {

	    log( false, "MixedFraction 'add' test has invalid arguments" );

	    return;
	}

	try {

	    tmp = new MixedFraction( Integer.parseInt( values[0] ),
 
				     Integer.parseInt( values[1] ),

				    Integer.parseInt( values[2] ) );

	    log( "  Created tmp MixedFraction object " + tmp );

	} catch ( Exception e ) {

	    log( false, "Failed to create tmp MixedFraction object " + parts[2] );

	    return;

	}

	try {

	    MF = MF.add( tmp );

	    log( "  MF object is now " + MF );

	} catch ( Exception e ) {
	    log( false, "MixedFraction 'add' raised exception" );

	    return;

	}

	mfc_result( parts );

    }


    public static void mfc_sub( String[] parts ) {
	MixedFraction tmp;

	String[] values;


	if ( ! mfc_prep( "subtract", parts, 1 ) ) return;

	values = parts[2].split( "," );

	if ( values.length != 3 ) {
	    log( false, "MixedFraction 'subtract' test has invalid arguments" );

	    return;

	}

	try {

	    tmp = new MixedFraction( Integer.parseInt( values[0] ),
 
				     Integer.parseInt( values[1] ),

				     Integer.parseInt( values[2] ) );

	    log( "  Created	tmp MixedFraction object " + tmp );

	} catch ( Exception e ) {

	    log( false, "Failed to create tmp MixedFraction object " + parts[2] );

	    return;

	}
	
	try {

	    MF = MF.subtract( tmp );

	    log( "  MF object is now " + MF );

	} catch ( Exception e ) {

	    log( false, "MixedFraction 'subtract' raised exception" );

	    return;

	}

	mfc_result( parts );

    }


    public static void mfc_mul( String[] parts ) {

	MixedFraction tmp;

	String[] values;
	if ( ! mfc_prep( "multiply", parts, 1 ) ) return;

	values = parts[2].split( "," );

	if ( values.length != 3 ) {

	    log( false, "MixedFraction 'multiply' test has invalid arguments" );

	    return;

	}

	try {

	    tmp = new MixedFraction( Integer.parseInt( values[0] ),

				     Integer.parseInt( values[1] ),

				     Integer.parseInt( values[2] ) );

	    log( "  Created tmp MixedFraction object " + tmp );

	} catch ( Exception e ) {

	    log( false, "Failed to create tmp MixedFraction object " + parts[2] );

	    return;

	}

	try {

	    MF = MF.multiply( tmp );

	    log( "  MF object is now " + MF );

	} catch ( Exception e ) {

	    log( false, "MixedFraction 'multiply' raised exception" );

	    return;

	}

	mfc_result( parts );

    }


    public static void mfc_div( String[] parts ) {

	MixedFraction tmp;

	String[] values;


	if ( ! mfc_prep( "divide", parts, 1 ) ) return;

	values = parts[2].split( "," );

	if ( values.length != 3 ) {

	    log( false, "MixedFraction 'divide' test has invalid arguments" );

	    return;

	}

	try {

	    tmp = new MixedFraction( Integer.parseInt( values[0] ),
 
				     Integer.parseInt( values[1] ),
				     Integer.parseInt( values[2] ) );

	    log( "  Created tmp MixedFraction object " + tmp );

	} catch ( Exception e ) {

	    log( false, "Failed to create tmp MixedFraction object " + parts[2] );

	    return;

	}

	try {

	    MF = MF.divide( tmp );

	    log( "  MF object is now " + MF );

	} catch ( Exception e ) {

	    log( false, "MixedFraction 'divide' raised exception" );

	    return;

	}

	mfc_result( parts );

    }


}

