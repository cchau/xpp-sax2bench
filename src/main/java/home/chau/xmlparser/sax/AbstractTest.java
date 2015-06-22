package home.chau.xmlparser.sax;

/**
 * Created by cchau on 6/21/2015.
 */
import java.io.*;
import org.xml.sax.*;
import org.xml.sax.helpers.*;
import java.net.*;

abstract public class AbstractTest  {
    static public void msg(String msg) {
        System.out.println(msg);
    }

    static public void info(String msg) {
        System.err.println(msg);
    }


    protected void run(String file, int iterations) {
        long startTime, endTime, startMem, endMem,endMemGC;

        try {
            Runtime rt = Runtime.getRuntime();
            rt.gc();
            startMem = rt.totalMemory() - rt.freeMemory();

            init();

            String content = slurpFileIntoString(file);


            // Get the JIT going; run for 5 seconds
            info("Warming up the parser....");
            startTime = System.currentTimeMillis();
            int count = 0;

            //XMLReader xmlReader = newXMLReader(parserClass, ns);
            while (System.currentTimeMillis() - startTime < 5000) {
                //xmlReader.parse(file);
                //XMLReader xmlReader = newXMLReader(parserClass, ns);
                //xmlReader.parse(new InputSource(new StringReader(content)));
                parse(new StringReader(content));
                ++count;
            }
            info("count="+count);


            info("Parsing "+file+" "+iterations+" times by "+getClass().getName());
            //(ns? " (namespaces on)..." : " (namepaces off)..."));
            startTime = System.currentTimeMillis();
            startMem = rt.totalMemory() - rt.freeMemory();

            for (int i=0; i < iterations; i++) {
                //xmlReader.parse(file);
                //xmlReader.parse(new InputSource(new StringReader(content)));
                parse(new StringReader(content));
            }



            endTime = System.currentTimeMillis();
            endMem = rt.totalMemory() - rt.freeMemory();
            rt.gc();
            endMemGC = rt.totalMemory() - rt.freeMemory();

            info("Elapsed time: "+(endTime-startTime)+"ms");
            info("Average parse time: " + ((float)(endTime-startTime)/iterations)+"ms");
            //info("Free memory after tests: "+(endMem-startMem)+" [bytes]");
            //info("Free memory after tests and GC: "+(endMemGC-startMem)+" [bytes]");

            /*
             // Now check the overhead for just opening the file
             long fopenStartTime = System.currentTimeMillis();
             for (int i=0; i < iterations; i++)
             new InputStreamReader((new URL(file)).openStream());
             long fopenEndTime = System.currentTimeMillis();

             msg("<benchmark elapsed=\""+(endTime-startTime)+
             "\" iterations=\""+iterations+"\" fileOpenElapsed=\""+
             (fopenEndTime-fopenStartTime)+"\"/>");
             */
            msg("<benchmark elapsed=\""+(endTime-startTime)+
                    "\" iterations=\""+iterations+"\"/>");

        }
        catch (Exception e) {
            msg("<error>"+e.toString()+"</error>");
            e.printStackTrace();
        }
    }

    abstract protected void init() throws Exception;
    //abstract protected void parse(String file) throws Exception;
    abstract protected void parse(Reader reader) throws Exception;


    protected static String slurpFileIntoString(String file) throws IOException, RuntimeException
    {
        StringBuffer buf = new StringBuffer();
        char[] cbuf = new char[8*1024];
        FileReader fr = new FileReader(file);
        //InputStream is = (new URL(file)).openStream();
        //Reader fr = new InputStreamReader(is);
        long pos = 0;
        long flen = new File(file).length();
        while(true) {
            int ret = fr.read(cbuf);
            if(ret == -1) break;
            buf.append(cbuf, 0, ret);
            pos += ret;
        }
        if(flen != -1 && flen != pos) throw new RuntimeException("could not get whole file");
        String content = buf.toString();
        return content;
    }


}
