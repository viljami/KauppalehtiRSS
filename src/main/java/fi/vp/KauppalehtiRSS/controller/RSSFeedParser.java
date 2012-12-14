package fi.vp.KauppalehtiRSS.controller;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;

import fi.vp.KauppalehtiRSS.model.FeedItem;

/*
 * The idea got from a plog post about how to handle RSS feed the technique is simplified for this use.
 * http://www.vogella.com/articles/RSSFeed/article.html
 */
public class RSSFeedParser {
  static final String TITLE = "title";
  static final String DESCRIPTION = "description";
  static final String CHANNEL = "channel";
  static final String LANGUAGE = "language";
  static final String COPYRIGHT = "copyright";
  static final String LINK = "link";
  static final String AUTHOR = "author";
  static final String ITEM = "item";
  static final String PUB_DATE = "pubDate";
  static final String GUID = "guid";
	
  private URL url;
  
  public RSSFeedParser() { 
  }

  public List<FeedItem> readFeed( String feedUrl ) {
	
	try {
		this.url = new URL(feedUrl);
	} catch (MalformedURLException e) {
	    throw new RuntimeException(e);
	}
	
	List<FeedItem> feedItems = new ArrayList<FeedItem>();
	
    try {
      XMLInputFactory inputFactory = XMLInputFactory.newInstance();
      InputStream in = read();
      XMLEventReader eventReader = inputFactory.createXMLEventReader(in);

      Boolean inItem = false;
      String category = "";
      String title = "";
      String description = "";
      String link = "";
      String date = "";
      String imageURL = "";
      
      while (eventReader.hasNext()) {
    	  XMLEvent event = eventReader.nextEvent();
    	  
          if(event.isStartElement()) {
        	  if( !inItem && event.asStartElement().getName().getLocalPart() != "item" ) {
        		  if( event.asStartElement().getName().getLocalPart() == "title" ) {
        			  event = eventReader.nextEvent();
        			  if( event.isCharacters() ) {
	        			  category = event.asCharacters().getData();
	        		  }
        		  } else if( event.asStartElement().getName().getLocalPart() == "url" ) {
        			  event = eventReader.nextEvent();
					  if(event.isCharacters()) {
						  imageURL = event.asCharacters().getData();
					  }
        		  }
        	  } else if(event.asStartElement().getName().getLocalPart() == "item" ) {
            	  inItem = true;
        	  }
        	  
        	  if( inItem ) {
        		  String element = event.asStartElement().getName().getLocalPart();
        		  event = eventReader.nextEvent();
        		  if( event.isCharacters() ) {
        			  if (element == "title") {
            			  title = event.asCharacters().toString();
            		  } else if (element == "description") {
            			  description = event.asCharacters().toString();
            		  } else if (element == "link") {
            			  link = event.asCharacters().toString();
            		  } else if (element == "date") {
            			  date = event.asCharacters().toString();
            		  }
        		  }
        	  }
          } else if ( event.isEndElement() ) {
           	  if(event.asEndElement().getName().getLocalPart() == "item") {
        		  inItem = false;
        		  FeedItem item = new FeedItem ();
        		  item.setCategory( category );
        		  item.setTitle( title );
        		  item.setDescription( description );
        		  item.setLink( link );
        		  item.setImageURL( imageURL );
        		  item.setDate( date );
            	  feedItems.add( item );
        	  }
          }    	  
      } 
    } catch (XMLStreamException e) {
      //throw new RuntimeException(e);
    	return feedItems;
    }
    
    return feedItems;
  }

  private InputStream read() {
    try {
      return url.openStream();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
} 

/*
<?xml version="1.0" encoding="ISO-8859-1"?>
<rss xmlns:rdf="http://www.w3.org/1999/02/22-rdf-syntax-ns#" xmlns:dc="http://purl.org/dc/elements/1.1/" xmlns:atom="http://www.w3.org/2005/Atom" version="2.0">   
<channel>
	<title>Etusivun uutiset | Kauppalehti.fi</title>
	<image>
		<url>http://kuvat.kauppalehti.fi/5/i/img/logo_kl_igoogle.gif</url>
		<title>Etusivun uutiset | Kauppalehti.fi</title>,         
		<link>http://www.kauppalehti.fi/</link>,      
	</image>
	<description>Kauppalehti.fi:n etusivulta n?et nopeasti yhdell? silm?yksell? p?iv?n t?rkeimm?t talousuutiset ja -tapahtumat.</description>
	<link>http://www.kauppalehti.fi</link>,      <language>fi</language>
	<atom:link href="http://rss.kauppalehti.fi/rss/etusivun_uutiset.jsp" rel="self" type="application/rss+xml"/>
	<item>         
		<title>UM: Kehitysavusta vahinkoja 415 000 euroa</title>
		<link>http://www.kauppalehti.fi/etusivu/um+kehitysavusta+vahinkoja+415+000+euroa/201212321013&amp;ext=rss</link>
		<guid>http://www.kauppalehti.fi/etusivu/um+kehitysavusta+vahinkoja+415+000+euroa/201212321013&amp;ext=rss</guid>
		<description>Helsingin Sanomat uutisoi eilen, ett? v??rink?yt?ksi? tulee esiin ministeri?n omassa valvonnassa vuosittain noin 10 miljoonan euron verran.</description>
		<pubDate>Wed, 12 Dec 2012 17:20:47 +0200</pubDate>
		<dc:date>2012-12-12T17:20:47+02:00</dc:date>
	</item>
	<item>
		<title>Kilometrikorvausten leikkaus raivostuttaa</title>
		<link>http://www.kauppalehti.fi/auto/uutiset/kilometrikorvausten+leikkaus+raivostuttaa/201212321009&amp;ext=rss</link>,         <guid>http://www.kauppalehti.fi/auto/uutiset/kilometrikorvausten+leikkaus+raivostuttaa/201212321009&amp;ext=rss</guid>,         <description>Autoilijoita raivostuttaa eduskunnan p??t?s pienent?? ty?ajoista maksettavia verottomia kilometrikorvauksia.</description>,         <pubDate>Wed, 12 Dec 2012 17:20:45 +0200</pubDate>,         <dc:date>2012-12-12T17:20:45+02:00</dc:date>,      </item>,      <item>,         <title>Lumiat vied??n Ruotsissa k?sist?</title>,         <link>http://www.kauppalehti.fi/etusivu/lumiat+vieda
*/