package Y2U;

import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import Y2U.Transformer.Translator;

public class main {

	public static void main(String[] args) throws IOException {
		
		//int a = 5%3;
		
		/*
		// Run a java app in a separate system process
		Process proc = Runtime.getRuntime().exec("java -jar G:\\uppaal-4.1.19\\uppaal.jar");
		// Then retreive the process output
		InputStream in = proc.getInputStream();
		InputStream err = proc.getErrorStream();
		*/
		
		//String test = "v=5;g=6";
		//test = test.replace("=", ":=");
		String guardStr = "(A||B)|| C &amp;&amp; !E";
		guardStr = guardStr.replace(" ", "");
		String[] g = guardStr.split("&amp;&amp;|!");
		//String[] g = guardStr.split("&amp;&amp;|!|(|)");
		
		
		String sss = "raise ea; raise eb";
		String str = sss.split(";")[0].trim();
		str = str.substring(6, str.length());
		System.out.println(str);
		
		Hashtable<String, Integer> eventTable= new Hashtable<String, Integer>();
		eventTable.put("r", 1);
		eventTable.put("t", 3);
		eventTable.put("u", 5);
		
		Set set = eventTable.entrySet();
	    Iterator it = set.iterator();
	    while (it.hasNext()) {
	      Map.Entry entry = (Map.Entry) it.next();
	      System.out.println(entry.getKey() + " : " + entry.getValue());
	    }
		
		
		System.out.println(Math.pow(10, 3));
		
		try {
            FileWriter writer = new FileWriter("G://MyFile.txt", true);
            writer.write("Hello World");
            writer.write("\r\n");   // write new line
            writer.write("Good Bye!");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
		
		
		String inPath = "";
		String outPath = "";
		
		
		/*
		System.out.println("Please input the Yakindu model full path (such as E:/test/yakindu.sct):");
		
        Scanner sc = new Scanner(System.in);   
        inPath = sc.nextLine();  
        
		System.out.println("Please input the Uppaal model full path (such as E:/test/uppaal.xml):");		
         
		outPath = sc.nextLine();  
		
		System.out.println("Start Transformation");
		*/
		
		
		//inPath = "E:/laser.sct";
		//outPath = "E:/laserU.sct";
		
		//inPath = "E:/CardiacArrestModular.sct";
		//outPath = "E:/CardiacArrestModular-UPPAAL.xml";
		
		//inPath = "G://state flow//laser//laser//model//test.sct";
		//outPath = "G://state flow//laser//laser//model//test.xml";
		
		Translator translator = new Translator(inPath, outPath);
		
		translator.translate();
		
		System.out.println("Transformation Done");
		
	}
}
