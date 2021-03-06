import java.io.BufferedReader;
import java.io.Console;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;

import java.net.URLConnection;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import org.jsoup.Jsoup;

import org.jsoup.nodes.Document;

import org.jsoup.nodes.Element;

import org.jsoup.select.Elements;
public class DoveQuery 

{

	public String searchKeyword;
	
	public int resultNum;
	
	public String resultLan;

	public String url;
	
	public KeywordList defaultKeywordList;  //設定關鍵字
	
	public KeywordList userLst;  //使用者輸入
	
	public String content;
	
	public WebHeap orderedResults;
	
	public String result;
	
	public int okUrl;
	public int errorUrl;
	
	
	

	public DoveQuery(String searchKeyword, int resultNum, String resultLan) throws IOException{
		this.orderedResults = new WebHeap();
		this.resultLan = resultLan;
		this.resultNum = resultNum;
		this.searchKeyword = searchKeyword;
		this.url = "http://www.google.com/search?q=Chocolate+"+URLEncoder.encode(searchKeyword,"UTF-8")+"&oe=utf8&num="+resultNum;
		setUrl();
		this.okUrl = 0;
		this.errorUrl = 0;
		addKeyword();
		setKeyword();
		fetchContent();
		populateOrderedResults();

	}
	public void setUrl() {
		
		if(resultLan=="chinese") {
			
				
				this.url+="&lr=lang_zh-CN%7Clang_zh-TW";
				System.out.println(url);
					
				
				
		}
	}
	
	public void addKeyword() throws FileNotFoundException {//設定原始keyword
		
		ArrayList<Keyword> defaultList = new ArrayList<Keyword>();
		

//		File file = new File("./WebContent/input.txt");		
//		Scanner scanner = new Scanner(file);
//	
//		while(scanner.hasNextLine()){
//			
//			try {
//				String operation = scanner.next();
//			
//				switch (operation){
//					case "add":
//						String name = scanner.next();
//						int count = scanner.nextInt();			
//						defaultList.add(new Keyword(name, count));
//						System.out.print(name);
//						break;
//				
//					default:
//						break;
//			}
//			
//			}catch(Exception e) {
//				
//			}
//		}
//		scanner.close();
		
		
		defaultList.add(new Keyword("Chocolate",5));			defaultList.add(new Keyword("巧克力",5));
		defaultList.add(new Keyword("Candy",3));				defaultList.add(new Keyword("糖果",3));
		defaultList.add(new Keyword("Delicious",2));			defaultList.add(new Keyword("冰淇淋",2));
		defaultList.add(new Keyword("Dessert",3));				defaultList.add(new Keyword("甜點",3));
		defaultList.add(new Keyword("Ice cream",2));			defaultList.add(new Keyword("冰淇淋",3));
		defaultList.add(new Keyword("Cocoa",4));				defaultList.add(new Keyword("可可",2));
		defaultList.add(new Keyword("Hair",-5));				defaultList.add(new Keyword("焦糖",4));	
		defaultList.add(new Keyword("Body",-4));				defaultList.add(new Keyword("身體",-5));
		defaultList.add(new Keyword("Cleansing",-3));			defaultList.add(new Keyword("頭髮",-4));
		defaultList.add(new Keyword("Deodorant",-5));			defaultList.add(new Keyword("柔順",-3));	
		defaultList.add(new Keyword("Lotion",-5));				defaultList.add(new Keyword("潤髮",-5));	
		defaultList.add(new Keyword("Antiperspirant",-5));		defaultList.add(new Keyword("香氛",-5));	
		defaultList.add(new Keyword("Conditioner",-5));			defaultList.add(new Keyword("清新",-5));	
		defaultList.add(new Keyword("除臭劑",-5));	
		
		
		this.defaultKeywordList = new KeywordList(defaultList);
//	
		
	}
	
	public void setKeyword() throws UnsupportedEncodingException {
		
		
		String[] token = searchKeyword.split(" ");
		ArrayList<Keyword> list = new ArrayList<Keyword>(); 
		String k = "";
		for(int i=0;i<token.length;i++) {
			
			k += token[i]+"+";
			list.add(new Keyword(k));
		}
		
		
		userLst = new KeywordList(list);
		
	}


	

	private String fetchContent() throws IOException

	{
		String retVal = "";

		URL u = new URL(url);

		URLConnection conn = u.openConnection();

		conn.setRequestProperty("User-agent", "Chrome/7.0.517.44");

		InputStream in = conn.getInputStream();

		InputStreamReader inReader = new InputStreamReader(in,"utf-8");

		BufferedReader bufReader = new BufferedReader(inReader);
		String line = null;
		

		while((line=bufReader.readLine())!=null)
		{
			retVal += line;

		}
		return retVal;
		
	}
	
	public void populateOrderedResults() throws IOException
	{

		if(content==null)
		{
			content= fetchContent();
		}
//		System.out.println(content);
		Document doc = Jsoup.parse(content);
//		System.out.println(doc.text());
		Elements lis = doc.select("div");
//		 System.out.println(lis);
		lis = lis.select(".kCrYT");
		
//		System.out.println(lis);
//		 System.out.println(lis.size());
		
		
		for(Element li : lis)
		{
			try 

			{
				String citeUrl = li.select("a").get(0).attr("href");
				citeUrl=URLDecoder.decode(citeUrl,"UTF-8");
				citeUrl=URLDecoder.decode(citeUrl,"UTF-8");
				
				String htmlUrl = li.select("a").get(0).attr("href");
				htmlUrl=URLDecoder.decode(htmlUrl,"UTF-8");
				htmlUrl=URLDecoder.decode(htmlUrl,"UTF-8");
				
				
				String title = li.select("a").get(0).select(".vvjwJb").text();
				if(title.equals("")) {
					continue;
				}
				
				if(citeUrl.contains("http")) {
					int index = citeUrl.indexOf("http");
					citeUrl = citeUrl.substring(index);
					if(citeUrl.contains("&sa=")) {
						citeUrl = citeUrl.substring(0,citeUrl.indexOf("&sa"));
						htmlUrl=htmlUrl.substring(0, htmlUrl.indexOf("&sa"));
					
					}
//					System.out.println("url:"+citeUrl+"title"+title);
				}
				
				try {			
					WebPage wp=new WebPage(citeUrl,title,userLst);
					wp.setCiteUrl(htmlUrl);
					WebNode w = new WebNode(wp);
					w.setNodeScore(this.defaultKeywordList);
//					System.out.println(w.nodeScore);
					orderedResults.add(w);
					okUrl++;
					
				}catch(Exception e) {
					System.out.println("Error:"+e.getMessage()+e);
					
					errorUrl++;
				}
				
//				System.out.println(title + ","+citeUrl);
			} catch (IndexOutOfBoundsException e) {

//				e.printStackTrace();

			}
		
//				System.out.println(title + ","+citeUrl);
				
				}
	}
	
	public void printResult() {
		
		System.out.println("<------Result----->");
		
		WebHeap cloneHeap = orderedResults.clone();
		
		System.out.println(orderedResults.heap.size());
		
		for(int i=1;cloneHeap.heap.size()>0;i++){
			
			WebNode w = cloneHeap.removeMax();
			
			System.out.println(i+". "+w.webPage.name);
			System.out.println("Score: "+w.nodeScore);
			
			
		}
		
		System.out.println("Successfully count:"+okUrl);
		System.out.println("Fail to count"+errorUrl);
		
		System.out.println("End");
		
		
		
	}

	

	

}
