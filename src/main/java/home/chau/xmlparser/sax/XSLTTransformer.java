package home.chau.xmlparser.sax;

/**
 * Created by cchau on 6/21/2015.
 */


import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

public class XSLTTransformer {

    static public void main(String[] args) throws Exception {
        if (args.length < 1) {
            System.err.println(
                    "Usage: java xslt style.xsl < input.xml  > output.html ");
            System.exit(1);
        }

        new XSLTTransformer(args[0]);
    }

    public XSLTTransformer(String style) throws Exception {
        Source xmlSource = new StreamSource(System.in);
        Source xslSource = new StreamSource(style);
        Result result = new StreamResult(System.out);

        Transformer tx = TransformerFactory.newInstance()
                .newTransformer(xslSource);

        tx.transform(xmlSource,result);
    }
}