package home.chau.xmlparser.sax;

/**
 * Created by cchau on 6/21/2015.
 */

import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXParseException;

abstract public class SAXTest extends AbstractTest implements ErrorHandler {
    static public void msg(String msg) {
        System.out.println(msg);
    }

    static public void info(String msg) {
        System.err.println(msg);
    }

    public void error(SAXParseException e) {
        fatalError(e);
        e.printStackTrace();
    }

    public void fatalError(SAXParseException e) {
        String pubID = (e.getPublicId() == null ? "" : e.getPublicId());
        String sysID = (e.getSystemId() == null ? "" : e.getSystemId());

        msg("<error line=\"" + e.getLineNumber() +
                "\" column=\"" + e.getColumnNumber() +
                "\" public=\"" + pubID +
                "\" system=\"" + sysID + ">" +
                e.toString() + "</error>");
        e.printStackTrace();
        System.exit(1);
    }

    public void warning(SAXParseException e) {
    }


}
