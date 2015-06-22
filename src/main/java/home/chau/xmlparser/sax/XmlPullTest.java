package home.chau.xmlparser.sax;

/**
 * Created by cchau on 6/21/2015.
 */

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.Reader;


public class XmlPullTest extends AbstractTest {
    private static final boolean USE_SB = true;
    private XmlPullParser xpp;
    private XmlPullParserFactory factory;
    private StringBuffer buf = new StringBuffer();
    private static boolean no_reuse;
    private static boolean ns;

    public static void main(String[] args) {
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

        new XmlPullTest(args[0],Integer.parseInt(args[1]),ns);
    }

    static public void usage(String msg) {
        System.out.println("Usage: java XmlPullTest <file> <iterations> <ns_on|ns_off> [no_reuse]");
        if(msg != null) System.err.println("Error:"+msg);
    }

    public XmlPullTest(String file, int iterations, boolean ns) {
        this.ns = ns;
        run(file,iterations);
    }

    protected void init() throws Exception {
        factory = XmlPullParserFactory.newInstance(
                System.getProperty(XmlPullParserFactory.PROPERTY_NAME), null);
        info("using factory "+factory.getClass());
        info("namespaces: "+ns);
        info("reuse parser instances: "+(!no_reuse));
        factory.setNamespaceAware(ns);
        xpp = factory.newPullParser();
        info("using parser "+xpp.getClass());
    }

    int [] holderForStartAndLength = new int[2];
    protected void parse(Reader r) throws Exception {
        //xpp.reset(); // reset is doen by setInput()
        //xpp.setInput(new InputStreamReader((new URL(file)).openStream()) );
        if(no_reuse) {
            xpp = factory.newPullParser();
        }
        xpp.setInput(r);

        int token;
        LOOP:
        while (true) {
            //info(""+xpp.getPositionDescription());
            token = xpp.next();
            switch (token) {
                case XmlPullParser.START_TAG:
                    //stag.resetStartTag(); //automatically done when tag is read
                    String uri = xpp.getNamespace();
                    String localName = xpp.getName();
                    String prefix = xpp.getPrefix();
                    int len = xpp.getAttributeCount();
                    for (int i=0; i < len; i++) {
                        String n = xpp.getAttributeName(i);
                        // No way to get type
                        // String t = stag.?
                        String t = xpp.getAttributeType(i);
                        String v = xpp.getAttributeValue(i);
                    }
                    if(USE_SB) buf.setLength(0);
                    //stag.resetStartTag();
                    break;

                case XmlPullParser.END_TAG:
                    if(USE_SB) {String c = buf.toString(); }
                    //xpp.readEndTag(etag);
                    //etag.resetEndTag(); //automaticaly done when tag is read
                    break;

                case XmlPullParser.TEXT:
                    if(USE_SB) {
                        char[] ch = xpp.getTextCharacters(holderForStartAndLength);
                        int start = holderForStartAndLength[0];
                        int length = holderForStartAndLength[1];
                        buf.append(ch, start, length);
                    } else {
                        String s = xpp.getText();
                    }
                    break;

                case XmlPullParser.END_DOCUMENT:
                    break LOOP;
            }
        }

        // good for resource utilization
        r.close();
    }
}
