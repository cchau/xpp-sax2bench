package home.chau.xmlparser.sax;

/**
 * Created by cchau on 6/21/2015.
 */
import java.io.*;
import org.xml.sax.*;
import org.xml.sax.helpers.*;
import java.net.*;

public class SAX1Test extends SAXTest {
    private Parser parser;
    private static boolean no_reuse;

    public static void main(String[] args) {
        if (args.length < 2) {
            usage("at least two arguments must be present");
            System.exit(1);
        }
        if(args.length == 3) {
            if(! "no_reuse".equals(args[2])) {
                usage("third option if present must be 'no_reuse'");
            }
            no_reuse = true;
        }
        new SAX1Test(args[0],Integer.parseInt(args[1]));
    }

    public SAX1Test(String file, int iterations) {
        run(file,iterations);
    }

    static public void usage(String msg) {

        System.err.println("Usage: java SAX1Test <file> <iterations> [no_reuse]");
        if(msg != null) System.err.println("Error:"+msg);
    }


    protected Parser makeParser() throws SAXException {
        try {
            Parser p = ParserFactory.makeParser();

            p.setErrorHandler(this);
            p.setDocumentHandler(new MyDocumentHandler());
            return p;
        } catch(Exception ex){
            throw new SAXException("could not create parser: "+ex, ex);
        }
    }

    protected void init() throws Exception {
        if(! no_reuse) {
            parser = makeParser();
        }
    }

    protected void parse(Reader reader) throws SAXException,IOException {
        if(no_reuse) {
            Parser p = makeParser();
            p.parse(new InputSource(reader));
        } else {
            parser.parse(new InputSource(reader));
        }
    }

    //    protected void parse(String file) throws SAXException,IOException {
    //        parser.parse(file);
    //    }
}
