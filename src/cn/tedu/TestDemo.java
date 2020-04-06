package cn.tedu;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class TestDemo {
	private Logger logger=Logger.getLogger(TestDemo.class);
	public static void main(String[] args) throws IOException {
		
		//new TestDemo().connect();
		//new TestDemo().fetchTitle();
		//new TestDemo().fetchPageNumber();
		new TestDemo().fetchAllPhoneTitles();
	}
	public void connect() throws IOException{
		String url="https://item.jd.com/100009177424.html";
		Connection conn=Jsoup.connect(url);
		//获取目标url的真个网页的内容
		Document doc=conn.get();
		//获取手机的标题-->手机定位需要爬取的元素 然后通过检查元素的方法来实现定位的相关的标签内容 最后徐泽合适的选择器来实现爬取
		Elements els=doc.select(".sku-name");
		for(Element el:els){
			//text()方法是用于获取标签的文本的数据（内容）
			String title=el.text();
			System.out.println(title);
		}
		//System.out.println(doc);
		
	}
	//爬取商品详情中的所有的页面的信息
	public void fetchTitle() throws IOException{
		String url="https://list.jd.com/list.html?cat=9987,653,655&ev=exbrand%5F8557&sort=sort_rank_asc&trans=1";
		Connection conn=Jsoup.connect(url);
		Document doc=conn.get();
		//Elements els=doc.select(".p-name");
		//Jsoup也是支持层级选择的 标签之间用空格进行分隔
		Elements els=doc.select(".p-name a em");
		for(Element el:els){
			//System.out.println(el);
			//String title=el.text();
			//System.err.println(title);
			String title=el.text();
			System.err.println(title);
		}		
	}
	public void fetchPageNumber() throws IOException{
		String url="https://list.jd.com/list.html?cat=9987,653,655&ev=exbrand%5F8557&sort=sort_rank_asc&trans=1";
		Connection conn=Jsoup.connect(url);
		Document doc=conn.get();
		//Elements els=doc.select(".p-name");
		//Jsoup也是支持层级选择的 标签之间用空格进行分隔
		/*Elements els=doc.select(".p-skip em b");
		for(Element el:els){
			//System.out.println(el);
			//String title=el.text();
			//System.err.println(title);
			int num=Integer.parseInt(el.text());
			System.err.println(num);
		}*/	
		//如果确定返回的元素只有一个元素 可以直接将返回的结果确定为Element类型的  
		//由于select方法返回的就是集合类型的 所以直接调用一个get方法实现结果的输出
		Element el=doc.select(".p-skip em b").get(0);
		int PageNum=Integer.parseInt(el.text());
		System.err.println(PageNum);
		
	}
	
	//后去全部收集的所有的页
	public int fetchAllPageNumber() throws IOException{
		String url="https://list.jd.com/list.html?cat=9987,653,655&page=1";
		Connection conn=Jsoup.connect(url);
		Document doc=conn.get();
		//Elements els=doc.select(".p-name");
		//Jsoup也是支持层级选择的 标签之间用空格进行分隔
		/*Elements els=doc.select(".p-skip em b");
		for(Element el:els){
			//System.out.println(el);
			//String title=el.text();
			//System.err.println(title);
			int num=Integer.parseInt(el.text());
			System.err.println(num);
		}*/	
		//如果确定返回的元素只有一个元素 可以直接将返回的结果确定为Element类型的  
		//由于select方法返回的就是集合类型的 所以直接调用一个get方法实现结果的输出
		Element el=doc.select(".p-skip em b").get(0);
		int PageNum=Integer.parseInt(el.text());
		return PageNum;
		
	}
	//获取所有页面的标题信息
	public void fetchAllPhoneTitles() throws IOException{
		if(fetchAllPageNumber()>1){
			for(int i=1;i<fetchAllPageNumber();i++){
				String url="https://list.jd.com/list.html?cat=9987,653,655&page="+i;
				Connection conn=Jsoup.connect(url);
				Document doc=conn.get();
				//Elements els=doc.select(".p-name");
				//Jsoup也是支持层级选择的 标签之间用空格进行分隔
				Elements els=doc.select(".p-name a em");
				for(Element el:els){
					//System.out.println(el);
					//String title=el.text();
					//System.err.println(title);
					String title=el.text();
					//一般是将结果的数据以日志的文件记录
					//System.out.println(title);
					logger.info(title);
				}	
			}
		}
	}
}
