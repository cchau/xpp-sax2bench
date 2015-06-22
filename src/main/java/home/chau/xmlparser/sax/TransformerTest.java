package home.chau.xmlparser.sax;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.TransformerFactory;

/**
 * Created by cchau on 6/21/2015.
 */
public class TransformerTest {
    public static void main(String[] args) {
        TransformerFactory t = TransformerFactory.newInstance();
        System.out.println(t.getClass());

        DocumentBuilderFactory db = DocumentBuilderFactory.newInstance();
        System.out.println(db.getClass());

        SAXParserFactory spf = SAXParserFactory.newInstance();
        System.out.println(spf.getClass());
    }
}
