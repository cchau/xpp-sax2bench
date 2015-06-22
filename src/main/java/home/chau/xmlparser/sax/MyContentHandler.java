package home.chau.xmlparser.sax;

/**
 * Created by cchau on 6/21/2015.
 */
import org.xml.sax.*;
import org.xml.sax.helpers.*;


import org.xml.sax.*;
import org.xml.sax.helpers.*;

public class MyContentHandler extends DefaultHandler {
    StringBuffer buf = new StringBuffer();

//    private static String printable(char ch) {
//        if(ch == '\n') {
//          return "\\n";
//        } else if(ch == '\r') {
//          return "\\r";
//        } else if(ch == '\t') {
//          return "\\t";
//        }
//        return ""+ch;
//    }


    public void characters(char[] ch,
                           int start,
                           int length)
            throws SAXException {

        //for (int i=0; i < length; i++) {
        //System.out.print("["+ch[start+i]+"]");
        //System.out.print(printable(ch[start+i]));
        //}

        buf.append(ch, start, length);

    }

    public void ignorableWhitespace(char[] ch,
                                    int start,
                                    int length)
            throws SAXException {
        /*
         for (int i=0; i < length; i++) {
         System.out.print("{"+ch[start+i]+"}");
         }
         */
        buf.append(ch, start, length);
    }


    public void endElement(java.lang.String uri,
                           java.lang.String localName,
                           java.lang.String qName)
            throws SAXException {
        //              System.out.println("</"+qName+ "(uri:"+uri+",local:"+localName+")" + ">");
        String c = buf.toString();
        //System.out.println("got '"+c+"'");
    }

    public void processingInstruction(String target,String data)
            throws SAXException {
        //              System.out.print("<?"+target+" "+data+"?>");
    }


    public void startElement(java.lang.String uri,
                             java.lang.String localName,
                             java.lang.String qName,
                             Attributes attr)
            throws SAXException {

        //        System.out.print("<"+qName + "(uri:"+uri+",local:"+localName+")");

        int len = attr.getLength();
        for (int i=0; i < len; i++) {
            String n = attr.getQName(i);
            String t = attr.getType(i);
            String v = attr.getValue(i);
            //            System.out.print(" "+n+"(uri:"+attr.getURI(i)+",local:"+attr.getLocalName(i)+",type:"+t+")=\""+v+"\"");
        }
        buf.setLength(0);
        //        System.out.println(">");
    }

}
