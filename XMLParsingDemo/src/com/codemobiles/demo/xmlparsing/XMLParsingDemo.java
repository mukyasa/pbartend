package com.codemobiles.demo.xmlparsing;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
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
		FileInputStream in = new FileInputStream("/sdcard/text.txt");
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

		FileInputStream in = new FileInputStream("/sdcard/text.txt");

		xr.parse(new InputSource(in));
		XMLDataSet parsedExampleDataSet = myExampleHandler.getParsedData();
		inLine.append(parsedExampleDataSet.toString());
		in.close();
		return inLine.toString();
	}
}