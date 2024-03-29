package com.fizz.cernal;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;


public class MainActivity extends Activity {
	ListView lister;
	ProgressDialog ShowProgress;
	public ArrayList<Post> PostList=new ArrayList<Post>();

    @Override
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lister = (ListView) findViewById(R.id.listView1);
		ShowProgress = ProgressDialog.show(MainActivity.this, "","Loading. Please wait...", true);
		new loadingTask().execute("http://home.web.cern.ch/about/updates/feed");
		lister.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				Intent intent = new Intent(Intent.ACTION_VIEW).setData(Uri
						.parse(PostList.get(position).getUrl()));
				startActivity(intent);
				}
		});
    }
	class loadingTask extends AsyncTask<String,Void,String>{
		protected String doInBackground(String... urls) {
		SAXHelper sh = null;
		try {
			sh = new SAXHelper(urls[0]);
			} 
		catch (MalformedURLException e) {
			e.printStackTrace();
			}
			sh.parseContent("");
			return "";
		}
		protected void onPostExecute(String s) {
			lister.setAdapter(new NiceAdapter(MainActivity.this, PostList));
			ShowProgress.dismiss();

		}
	}
	class SAXHelper {
		public HashMap<String, String> userList = new HashMap<String, String>();
		private URL url2;

		public SAXHelper(String url1) throws MalformedURLException {
			this.url2 = new URL(url1);
		}

		public RSSHandler parseContent(String parseContent) {
			RSSHandler df = new RSSHandler();
			try {

				SAXParserFactory spf = SAXParserFactory.newInstance();
				SAXParser sp = spf.newSAXParser();
				XMLReader xr = sp.getXMLReader();
				xr.setContentHandler(df);
				xr.parse(new InputSource(url2.openStream()));
			} catch (Exception e) {
				e.printStackTrace();
			}
			return df;
		}
	}

	class RSSHandler extends DefaultHandler {

		private Post currentPost = new Post();
		StringBuffer chars = new StringBuffer();

		@Override
		public void startElement(String uri, String localName, String qName,
				Attributes atts) {

			chars = new StringBuffer();
			if (localName.equalsIgnoreCase("item")) {

			}
		}

		@Override
		public void endElement(String uri, String localName, String qName)
				throws SAXException {

			if (localName.equalsIgnoreCase("title")
					&& currentPost.getTitle() == null) {
				currentPost.setTitle(chars.toString());

			}
			if (localName.equalsIgnoreCase("pubDate")
					&& currentPost.getPubDate() == null) {
				currentPost.setPubDate(chars.toString());

			}
			if (localName.equalsIgnoreCase("thumbnail")
					&& currentPost.getThumbnail() == null) {
				currentPost.setThumbnail(chars.toString());

			}
			if (localName.equalsIgnoreCase("link")
					&& currentPost.getUrl() == null) {
				currentPost.setUrl(chars.toString());
			}

			if (localName.equalsIgnoreCase("item")) {
				PostList.add(currentPost);
				currentPost = new Post();
			}

		}

		@Override
		public void characters(char ch[], int start, int length) {
			chars.append(new String(ch, start, length));
		}

	}

		
	}
    

	
