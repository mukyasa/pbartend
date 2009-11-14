package com.codemobiles.demo.xmlparsing;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;


public class XMLHandler extends DefaultHandler{

     // ===========================================================
     // Fields
     // ===========================================================
     
     private boolean in_outertag = false;
     private boolean in_innertag = false;
     private boolean question = false;
     private boolean answer = false;
     
     private FlashCard card = new FlashCard();

     // ===========================================================
     // Getter & Setter
     // ===========================================================

     public FlashCard getParsedData() {
          return this.card;
     }

     // ===========================================================
     // Methods
     // ===========================================================
     @Override
     public void startDocument() throws SAXException {
          this.card = new FlashCard();
     }

     @Override
     public void endDocument() throws SAXException {
          // Nothing to do
     }

     /** Gets be called on opening tags like:
      * <tag>
      * Can provide attribute(s), when xml was like:
      * <tag attribute="attributeValue">*/
     @Override
     public void startElement(String namespaceURI, String localName,
               String qName, Attributes atts) throws SAXException {
          if (localName.equals("FlashCards")) {
               this.in_outertag = true;
          }else if (localName.equals("Card")) {
        	  // Extract an Attribute
              String attrValue = atts.getValue("number");
              int i = Integer.parseInt(attrValue);
              card.cardNum=i;
               this.in_innertag = true;
          }else if (localName.equals("Question")) {
               this.question = true;
          }else if (localName.equals("Answer")) {
        	  this.answer = true;
          }
     }
     
     /** Gets be called on closing tags like:
      * </tag> */
     @Override
     public void endElement(String namespaceURI, String localName, String qName)
               throws SAXException {
          if (localName.equals("FlashCards")) {
               this.in_outertag = false;
          }else if (localName.equals("Card")) {
               this.in_innertag = false;
          }else if (localName.equals("Question")) {
               this.question = false;
          }else if (localName.equals("Answer")) {
               this.answer = false;
          }else if (localName.equals("number")) {
               // Nothing to do here
          }
     }
     
     /** Gets be called on the following structure:
      * <tag>characters</tag> */
     @Override
    public void characters(char ch[], int start, int length) {
    	 if(this.question)
             card.question = new String(ch, start, length);
          else if(this.answer)
             card.answer = new String(ch, start, length);
    }
}