package home.chau.xmlparser.sax;

/**
 * Created by cchau on 6/21/2015.
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Random;

public class RandXMLGen
{
    private int maxChars = 512;	// Max. no. of characters in a text node.
    private int textOccurrencePercentage = 60;
    private int maxAttrs = 5;

    String[] elems = {
            "Country", "City", "Town", "MainMarket", "Stadium", "Park", "Library", "SuperMarket",
            "Airport", "HistoricalPlace", "RailwayStation", "ShoppingMall", "ParkingLot",
            "AmusementPark", "Studio", "Factory", "BookStore", "Museum"
    };
    String[] attrs = {
            "name", "capacity", "location", "size", "area", "administrator", "description",
            "population", "length", "width", "rent", "attractiveness"
    };
    char[] chars = {
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
            'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
            'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
            '-', '_', '=', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            ' ', ' ', ' ', '\t', '\n'
    };
    boolean[] attrMarker = new boolean[attrs.length];

    Random rand = new Random();

    String outFileName = null;
    int noOfElems = 10;

    void usage()
    {
        System.err.print(
                "Usage\n"
                        +"  java org.xperf.xpb.RandXMLGen <no_of_elements>\n"
                        +"\n"
                        +"This program generates a random XML file.\n"
                        +"\n"
        );
        System.exit(1);
    }

    private void parseCLArgs(String[] args)
    {
        for (int i = 0; i < args.length; ++i)
        {
            String arg = args[i];
            String value = null;
            if (i < args.length - 1)
                value = args[i + 1];

            if (arg.charAt(0) == '-')
            {
                if (arg.equals("-help"))
                    usage();
                else if (arg.equals("-elemcount"))
                    setNoOfElems(value);
                else if (arg.equals("-output"))
                    setOutFileName(value);
                else
                    usage();
            }
            else
            {
                if (i == 0)
                    setNoOfElems(arg);
                else if (i == 1)
                    setOutFileName(arg);
                else
                    usage();
            }
        }
    }

    void setNoOfElems(String value)
    {
        try
        {
            noOfElems = (Integer.decode(value)).intValue();
        }
        catch (NumberFormatException e)
        {
            System.err.println("Invalid Loop Count specified. Exception: " + e);
            System.err.println("Resorting to default value of: " + noOfElems);
        }
    }

    void setOutFileName(String fileName){
        outFileName = fileName;
    }

    void init()
    {

    }

    void initAttrMarker()
    {
        for (int i = 0; i < attrMarker.length; i++)
            attrMarker[i] = false;
    }

    String randString(int size)
    {
        StringBuffer sb = new StringBuffer(size);
        for (int i = 0; i < size; i++)
            sb.append(chars[rand.nextInt(chars.length)]);
        return sb.toString();
    }

    void generateElement(int elemIdx, PrintWriter writer)
    {
        String name = elems[elemIdx];
        writer.print("<" + name);
        int noOfAttrs = rand.nextInt(maxAttrs);
        initAttrMarker();
        for (int i = 0; i < noOfAttrs; i++)	// Actual no. of attributes generate would be less.
        {
            int attrIdx = rand.nextInt(attrs.length);
            if (attrMarker[attrIdx])	// This attribute is already generated.
                continue;
            writer.print(" " + attrs[attrIdx] + "=\"" + randString(10) + "\"");
            attrMarker[attrIdx] = true;
        }
        writer.println(">");

        if (rand.nextInt(100) < textOccurrencePercentage)
            writer.println(randString(rand.nextInt(maxChars)));
        else
            generateElement(rand.nextInt(elems.length), writer);

        writer.println("</" + name + ">");
    }

    void generate()
    {
        if (outFileName == null)
            return;

        File file = new File(outFileName);
        PrintWriter writer = null;
        try {
            writer =  new PrintWriter(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        if (writer == null)
            return;

        writer.println("<?xml version = \"1.0\" encoding = \"UTF-8\"?>");
        writer.println("<RXGenTopElement>");
        for (int i = 0; i < noOfElems; i++)
        {
            int elemIdx = rand.nextInt(elems.length);
            generateElement(elemIdx, writer);
        }
        writer.println("</RXGenTopElement>");
        writer.close();
    }



    public static void main(String [] args)
    {
        RandXMLGen generator = new RandXMLGen();
        generator.parseCLArgs(args);

        generator.init();
        generator.generate();
        System.exit(0);
    }
}
