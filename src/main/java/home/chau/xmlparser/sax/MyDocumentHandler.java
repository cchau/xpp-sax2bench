package home.chau.xmlparser.sax;

/**
 * Created by cchau on 6/21/2015.
 */
import org.xml.sax.*;
import org.xml.sax.helpers.*;

public class MyDocumentHandler implements DocumentHandler {
    StringBuffer buf = new StringBuffer();

    public void characters(char[] ch,
                           int start,
                           int length)
            throws SAXException {
        //        for (int i=0; i < length; i++)
        //              System.out.print("["+ch[start+i]+"]");
        buf.append(ch, start, length);

    }

    public void ignorableWhitespace(char[] ch,
                                    int start,
                                    int length)
            throws SAXException {

        //        for (int i=0; i < length; i++)
        //              System.out.print("{"+ch[start+i]+"}");
        buf.append(ch, start, length);

    }


    public void endElement(String name)
            throws SAXException {

        //        System.out.print("</" + name + ">");
        String c = buf.toString();
    }

    public void processingInstruction(String target,String data)
            throws SAXException {
        //        System.out.print("<?" + target + " " + data + "?>");
    }


    public void startElement(String name,
                             AttributeList attr)
            throws SAXException {

        //        System.out.print("<" + name);

        int len = attr.getLength();
        for (int i=0; i < len; i++) {
            String n = attr.getName(i);
            String t = attr.getType(i);
            String v = attr.getValue(i);

            //            System.out.println(" "+n+"(type:"+t+")=\""+v+"\"");
        }

        //        System.out.print(">");
        buf.setLength(0);
    }

    public void startDocument() { }
    public void endDocument() { }
    public void setDocumentLocator(Locator l) { }

}