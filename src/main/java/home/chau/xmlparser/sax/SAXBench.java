package home.chau.xmlparser.sax;

/**
 * Created by cchau on 6/21/2015.
 */

import org.xml.sax.*;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.SAXParserFactory;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

public class SAXBench implements ErrorHandler {
    private ArrayList parsers = new ArrayList(10);
    private ArrayList tests = new ArrayList(10);

    static public void main(String[] args) throws Exception {
        if (args.length < 1) {
            info("Usage: java SAXBench <saxbench.xml> > results.xml");
            System.exit(1);
        }

        new SAXBench(args[0]);
    }

    public SAXBench(String config) throws Exception {
        readConfig(config);
        runTests();
    }

    static private void msg(String msg) {
        System.out.println(msg);
    }

    static private void info(String msg) {
        System.err.println(msg);
    }

    private void readConfig(String config) throws Exception {
        XMLReader xmlReader =
                SAXParserFactory.newInstance().newSAXParser().getXMLReader();
        xmlReader.setContentHandler(new ConfigHandler(parsers,tests));
        xmlReader.setErrorHandler(this);
        xmlReader.parse(config);
    }

    private void runTests() throws Exception {

        /*
        String defaultParser = SAXParserFactory.newInstance().newSAXParser()
            .getXMLReader().getClass().getName();

         info("Performing a dry run first, "
                 +"using the default: " + defaultParser + "...");

        int numTests = tests.size();
        Runtime rt = Runtime.getRuntime();

        long start,end;
        for (int i=0; i < numTests; i++) {
            TestFile tf = (TestFile) tests.get(i);
            System.err.print(tf.testName+"...");
            start = System.currentTimeMillis();
            Process proc = rt.exec("java JAXPTest "
                                       + tf.testFile + " " + tf.iterations + " ns_off");
            proc.waitFor();
            end = System.currentTimeMillis();
            System.err.println("around "+((end-start)/1000)+" seconds");
        }
         */

        msg("<?xml version='1.0' encoding='US-ASCII'?>");
        msg("<saxbench-results version='2.0'>");

        int numParsers = parsers.size();
        for (int i=0; i < numParsers; i++) {
            ((TestParser)parsers.get(i)).runTests(tests);
        }

        msg("</saxbench-results>");
    }


    public void error(SAXParseException e) throws SAXParseException {
        fatalError(e);
    }

    public void fatalError(SAXParseException e) throws SAXParseException {
        throw e;
    }

    public void warning(SAXParseException e) {}

    static private class ConfigHandler extends DefaultHandler {
        TestParser currentParser = null;
        ArrayList parsers,tests;

        public ConfigHandler(ArrayList parsers,ArrayList tests) {
            this.parsers = parsers;
            this.tests = tests;
        }

        public void startElement(java.lang.String uri,
                                 java.lang.String localName,
                                 java.lang.String qName,
                                 Attributes attr)
                throws SAXException {

            if (qName == "parser") {
                currentParser = new TestParser(
                        attr.getValue("name"),
                        attr.getValue("classpath"),
                        attr.getValue("testClass"),
                        attr.getValue("options"));
                parsers.add(currentParser);
            }
            else
            if (qName == "property")
                currentParser.setProperty(attr.getValue("name"),
                        attr.getValue("value"));
            else
            if (qName == "test")
                tests.add(new TestFile(
                        attr.getValue("name"),
                        attr.getValue("src"),
                        attr.getValue("iterations")));
        }
    }

    static private class TestParser {
        String name,classpath,testClass,options;
        Properties properties = new Properties();

        TestParser(String name,String classpath,String testClass,
                   String options) {
            this.name = name;
            this.classpath = classpath;
            this.testClass = testClass;

            if (options == null)
                this.options = "";
            else
                this.options = options;
        }

        void setProperty(String name, String value) {
            properties.setProperty(name,value);
        }

        void runTests(ArrayList tests) throws Exception {
            msg(" <parser name='"+name+"' class='"+testClass+"'>");

            int numTests = tests.size();
            for (int i=0; i < numTests; i++) {
                runTest( (TestFile) tests.get(i) );
            }

            msg(" </parser>");
        }

        void runTest(TestFile tf) throws Exception {
            msg("  <test name='"+tf.testName+"' src='"+tf.testFile+"'>");

            String cmd = "java -cp \"" + classpath + ";"
                    + System.getProperty("java.class.path") + "\"";
            info(cmd);

            Iterator props = properties.entrySet().iterator();
            while (props.hasNext()) {
                Map.Entry entry = (Map.Entry) props.next();
                String n = (String) entry.getKey();
                String v = (String) entry.getValue();
                cmd += " -D"+n+"="+v;
            }

            final String args = " " + testClass + " " + tf.testFile + " " + tf.iterations
                    + " " + options;
            info("args="+args);
            cmd += args;
            info("cmd="+cmd);

            info("Testing "+name+" with "+tf.iterations +
                    " iterations of "+tf.testName+".");

            Process proc = Runtime.getRuntime().exec(cmd);


            BufferedReader stdout
                    = new BufferedReader(new InputStreamReader
                    (proc.getInputStream()));
            BufferedReader stderr
                    = new BufferedReader(new InputStreamReader
                    (proc.getErrorStream()));

            String errors = "";
            String newLine;
            while ( (newLine=stderr.readLine()) != null ) {
                info("   "+newLine);
                errors += "   " + newLine + "\n";
            }

            if (errors.length() > 0) {
                msg("   <!--\n"+errors+"   -->");
            }

            while ( (newLine=stdout.readLine()) != null)
                msg("   " + newLine);

            proc.waitFor();
            if(proc.exitValue() != 0) {
                info("process exited with non zero value - aborting!");
                //System.exit(1);
            }
            msg("  </test>");

        }
    }

    static private class TestFile {
        String testName, testFile, iterations;

        TestFile(String testName, String testFile, String iterations) {
            this.testName = testName;
            this.testFile = testFile;
            this.iterations = iterations;
        }
    }
}
