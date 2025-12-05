package Y2U.Transformer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentType;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import Y2U.DataStructure.Model;
import Y2U.Parser.UPPAALWriter;
import Y2U.Parser.YakinduReader;

public class Translator
{
	
	private String yakinduFilePath;	
	private String uppaalFilePath;
	private Model model;
	private YakinduReader reader;
	private UPPAALWriter writer;
	private ElementTransformer elementTransformer;
	private PositionSetter positionSetter;
	private Synchronizer synchronizer;
	
	
	public Translator(String yakinduFilePath, String uppaalFilePath) {
		super();
		this.yakinduFilePath = yakinduFilePath;
		this.uppaalFilePath = uppaalFilePath;
		
		model = new Model();
		reader = new YakinduReader(yakinduFilePath, model);
		writer = new UPPAALWriter(uppaalFilePath, model);
		elementTransformer = new ElementTransformer(model);
		positionSetter = new PositionSetter(model);		
		synchronizer = new Synchronizer(model);
	}



	public void translate() {
		
		Document yakinduDoc = reader.read();
		
		elementTransformer.transform(yakinduDoc);
		
		positionSetter.setPositions(yakinduDoc);
		
		//if only one statechart, do not need synchronization
		if(model.getAutomataList().size()>1)
			synchronizer.synchronizeAutomata();	
		
		positionSetter.adjustOpsitions();
		
		
		writer.write();
	}	
	
}