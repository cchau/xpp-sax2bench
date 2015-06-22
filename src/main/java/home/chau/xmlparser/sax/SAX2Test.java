package home.chau.xmlparser.sax;

/**
 * Created by cchau on 6/21/2015.
 */

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import java.io.IOException;
import java.io.Reader;

public class SAX2Test extends SAXTest {
    private XMLReader xmlReader = null;
    private boolean ns;
    private static boolean no_reuse;

    public static void main(String[] args) {
        boolean ns=false;

        if (args.length < 3) {
            usage("at least two arguments must be present");
            System.exit(1);
        }
        else {
            if (args[2].equalsIgnoreCase("ns_on"))
                ns = true;
            else if (args[2].equalsIgnoreCase("ns_off"))
                ns = false;
            else {
                usage("expected ns_on or ns_off as third argument");
                System.exit(1);
            }
        }
        if(args.length == 4) {
            if(! "no_reuse".equals(args[3])) {
                usage("third option if present must be 'no_reuse'");
            }
            no_reuse = true;
        }

        new SAX2Test(args[0],Integer.parseInt(args[1]),ns);
    }

    static public void usage(String msg) {
        System.out.println("Usage: java SAX2Test <file> <iterations> <ns_on|ns_off> [no_reuse]");
        if(msg != null) System.err.println("Error:"+msg);
    }

    public SAX2Test(String file, int iterations, boolean ns) {
        this.ns = ns;
        run(file,iterations);
    }


    protected XMLReader makeParser() throws SAXException {
        try {
            XMLReader xr = XMLReaderFactory.createXMLReader();

            xr.setErrorHandler(this);
            if(ns) {
                xr.setFeature("http://xml.org/sax/features/namespaces",ns);
                xr.setFeature("http://xml.org/sax/features/namespace-prefixes",!ns);
            }
            xr.setContentHandler(new MyContentHandler());

            return xr;
        } catch(Exception ex){
            ex.printStackTrace();
            throw new SAXException("could not create parser: "+ex, ex);
        }
    }

    protected void init() throws Exception {
        //if(! no_reuse) {
        xmlReader = makeParser();
        info("using parser "+xmlReader.getClass());
        info("namespaces: "+ns);
        info("reuse parser instances: "+(!no_reuse));
        //}
    }

    protected void parse(Reader reader) throws SAXException,IOException {
        if(no_reuse) {
            XMLReader xr = makeParser();
            xr.parse(new InputSource(reader));
        } else {
            xmlReader.parse(new InputSource(reader));
        }
    }

    //protected void parse(String file) throws SAXException,IOException {
    //    xmlReader.parse(file);
    //}
}
