package home.chau.xmlparser.sax;

/**
 * Created by cchau on 6/21/2015.
 */

import org.gjt.xpp.XmlEndTag;
import org.gjt.xpp.XmlPullParser;
import org.gjt.xpp.XmlPullParserFactory;
import org.gjt.xpp.XmlStartTag;

import java.io.Reader;

public class XPP2Test extends AbstractTest {
    private XmlPullParser xpp;
    private XmlPullParserFactory factory;
    private XmlStartTag stag;
    private XmlEndTag etag;
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


        new XPP2Test(args[0],Integer.parseInt(args[1]),ns);
    }

    static public void usage(String msg) {
        System.out.println("Usage: java XPP2Test <file> <iterations> <ns_on|ns_off> [no_reuse]");
        if(msg != null) System.err.println("Error:"+msg);
    }

    public XPP2Test(String file, int iterations, boolean ns) {
        this.ns = ns;
        run(file,iterations);
    }

    protected void init() throws Exception {
        factory = XmlPullParserFactory.newInstance();
        info("using factory "+factory.getClass());
        xpp = factory.newPullParser();
        info("using parser "+xpp.getClass());
        info("namespaces: "+ns);
        info("reuse parser instances: "+(!no_reuse));
        xpp.setNamespaceAware(ns);
        xpp.setNamespaceAttributesReporting(!ns);

        // Prepare parsing variables
        stag = factory.newStartTag();
        etag = factory.newEndTag();
    }

    protected void parse(Reader r) throws Exception {
        //xpp.reset(); // reset is doen by setInput()
        //xpp.setInput(new InputStreamReader((new URL(file)).openStream()) );
        if(no_reuse) {
            xpp = factory.newPullParser();
        }
        xpp.setInput(r);

        byte token;
        LOOP:
        while (true) {
            token = xpp.next();
            switch (token) {
                case XmlPullParser.START_TAG:
                    //stag.resetStartTag(); //automatically done when tag is read
                    xpp.readStartTag(stag);
                    String uri = stag.getNamespaceUri();
                    String localName = stag.getLocalName();
                    String rawName = stag.getRawName();
                    int len = stag.getAttributeCount();
                    for (int i=0; i < len; i++) {
                        String n = stag.getAttributeRawName(i);
                        // No way to get type
                        // String t = stag.?
                        //String t = stag.getAttributeType(i);
                        String v = stag.getAttributeValue(i);
                    }
                    buf.setLength(0);
                    //stag.resetStartTag();
                    break;

                case XmlPullParser.END_TAG:
                    String c = buf.toString();
                    xpp.readEndTag(etag);
                    //etag.resetEndTag(); //automaticaly done when tag is read
                    break;

                case XmlPullParser.CONTENT:
                    String s = xpp.readContent();
                    buf.append(s);
                    break;

                case XmlPullParser.END_DOCUMENT:
                    break LOOP;
            }
        }

        // good for resource utilization
        r.close();
    }
}
