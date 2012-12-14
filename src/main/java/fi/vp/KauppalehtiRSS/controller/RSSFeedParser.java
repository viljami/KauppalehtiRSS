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
	static final String ITEM = "item";
	static final String TITLE = "title";
	static final String DESCRIPTION = "description";
	static final String LINK = "link";
	static final String URL = "url";
	static final String DATE = "date";
	
  private URL url;
  
  public RSSFeedParser() { 
  }

  public List<FeedItem> readFeed( String feedUrl ) {
	
	try {
		this.url = new URL( feedUrl );
	} catch ( MalformedURLException e ) {
	    throw new RuntimeException( e );
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
      
      while ( eventReader.hasNext() ) {
    	  XMLEvent event = eventReader.nextEvent();
    	  
          if( event.isStartElement() ) {
        	  if( !inItem && event.asStartElement().getName().getLocalPart() != ITEM ) {
        		  if( event.asStartElement().getName().getLocalPart() == TITLE ) {
        			  event = eventReader.nextEvent();
        			  if( event.isCharacters() ) {
	        			  category = event.asCharacters().getData();
	        		  }
        		  } else if( event.asStartElement().getName().getLocalPart() == URL ) {
        			  event = eventReader.nextEvent();
					  if(event.isCharacters()) {
						  imageURL = event.asCharacters().getData();
					  }
        		  }
        	  } else if(event.asStartElement().getName().getLocalPart() == ITEM ) {
            	  inItem = true;
        	  }
        	  
        	  if( inItem ) {
        		  String element = event.asStartElement().getName().getLocalPart();
        		  event = eventReader.nextEvent();
        		  if( event.isCharacters() ) {
        			  if ( element == TITLE ) {
            			  title = event.asCharacters().toString();
            		  } else if ( element == DESCRIPTION ) {
            			  description = event.asCharacters().toString();
            		  } else if ( element == LINK ) {
            			  link = event.asCharacters().toString();
            		  } else if ( element == DATE ) {
            			  date = event.asCharacters().toString();
            		  }
        		  }
        	  }
          } else if ( event.isEndElement() ) {
           	  if ( event.asEndElement().getName().getLocalPart() == ITEM ) {
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
    } catch ( XMLStreamException e ) {
        throw new RuntimeException( e );
    }
    
    return feedItems;
  }

  private InputStream read() {
    try {
      return url.openStream();
    } catch ( IOException e ) {
      throw new RuntimeException( e );
    }
  }
} 
