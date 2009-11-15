package com.codemobiles.demo.xmlparsing;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class XMLParsingDemo extends Activity {

	private TextView orgXmlTxt;
	private TextView parsedXmlTxt;
	private Button parseBtn;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);

		try {
			setContentView(R.layout.main);
			orgXmlTxt = (TextView) findViewById(R.id.orgXMLTxt);
			parsedXmlTxt = (TextView) findViewById(R.id.parsedXMLTxt);
			parseBtn = (Button) findViewById(R.id.parseBtn);

			orgXmlTxt.setText(getOriginalMyXML());
			parseBtn.setOnClickListener(new View.OnClickListener() {

				public void onClick(View v) {
					try {
						parsedXmlTxt.setText(getParsedMyXML());
					} catch (Exception e) {
						// TODO Auto-generated catch block
						orgXmlTxt.setText(e.getMessage());
					}
				}
			});
		} catch (Exception e) {
			orgXmlTxt.setText(e.getMessage());
		}

	}

	private String getOriginalMyXML() throws Exception {
		URL url = new URL("http://quizlet.com/api/1.0/sets?dev_key=f4ndi00uluokcc0c&q=creator:jalenack&extended=on&sort=most_recent&callback=processData");
		InputStream is = url.openStream();
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		StringBuilder sb = new StringBuilder();
		
		String line;
		while ((line = reader.readLine()) != null) 
		{
			sb.append(line + "\n");
		}
		
		JSONTokener toke = new JSONTokener(sb.toString());
		sb = new StringBuilder();
		boolean start=false;
		while(toke.more())
		{
			char c = toke.next();
			
			if(start)
				sb.append(c);
			
			if(c=='(')
				start = true;
		}
		
		JSONObject jsonObj = new JSONObject(sb.toString());
		JSONArray sets = (JSONArray)jsonObj.get("sets");
		StringBuffer titles = new StringBuffer();
		//gets the titles
		for(int i=0;i<sets.length();i++)
		{
			JSONObject set = (JSONObject)sets.get(i);
			String title = (String)set.get("title");
			titles.append(title +" | ");
			
		}
		
		FileInputStream in = new FileInputStream("/sdcard/FlashCardSet_pda.xml"); 
		StringBuffer inLine = new StringBuffer();
		InputStreamReader isr = new InputStreamReader(in);

		BufferedReader inRd = new BufferedReader(isr);

		String text;
		while ((text = inRd.readLine()) != null) {
			inLine.append(text);
			inLine.append("\n");
		}
		in.close();
		return inLine.toString();
	}

	private String getParsedMyXML() throws Exception {
		StringBuffer inLine = new StringBuffer();
		/* Get a SAXParser from the SAXPArserFactory. */
		SAXParserFactory spf = SAXParserFactory.newInstance();
		SAXParser sp = spf.newSAXParser();

		/* Get the XMLReader of the SAXParser we created. */
		XMLReader xr = sp.getXMLReader();
		/* Create a new ContentHandler and apply it to the XML-Reader */
		XMLHandler myExampleHandler = new XMLHandler();
		xr.setContentHandler(myExampleHandler);

		FileInputStream in = new FileInputStream("/sdcard/FlashCardSet_pda.xml");

		xr.parse(new InputSource(in));
		FlashCard parsedExampleDataSet = myExampleHandler.getParsedData();
		inLine.append(parsedExampleDataSet.toString());
		in.close();
		return inLine.toString();
	}
}