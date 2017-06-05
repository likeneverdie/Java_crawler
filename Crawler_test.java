package final_project;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.Iterator;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;
import org.jsoup.select.Elements;

public class Crawler_test {

	public static void main(String[] args) throws Exception{
		
		URL bot_currency = new URL("http://rate.bot.com.tw/xrt?Lang=zh-TW");
		URL sinopac_currency = new URL("http://www.findrate.tw/bank/21/#.WTVzWxOGOt8");
		
		/*
         * 向網頁伺服發出請求，並將回應分析成document。
         * 第一個參數是：請求位置與QueryString。
         * 第二個參數是：連線時間(毫秒)，在指定時間內若無回應則會丟出IOException
         */
		
        Document bot = Jsoup.parse(bot_currency, 3000); // bot: 台灣銀行
        Document sinopac = Jsoup.parse(sinopac_currency, 3000); // sinopac: 永豐銀行
        
        String bot_title = bot.title(); // 抓取網頁標題
        String mega_title = sinopac.title();
        System.out.println(bot_title); 
        System.out.println(mega_title);

        Elements bot_elements = bot.select("td.rate-content-cash"); //JQuery selector
        Elements sinopac_elements = sinopac.select("td.WordB");
        System.out.println(bot_elements.size()); 
        System.out.println(sinopac_elements.size()); 
        for(int i = 0; i < 64; i++){
        	System.out.println(sinopac_elements.get(i).text());  
        }
        //System.out.println(mega_elements.get(0).text()); 
        
        /*System.out.print("美金現金買入(台灣銀行): ");
        System.out.println(bot_elements.get(0).text());
        System.out.print("美金現金賣出(台灣銀行): ");
        System.out.println(bot_elements.get(1).text());  
        
        double d1 = Double.parseDouble(bot_elements.get(0).text());
        double d2 = Double.parseDouble(bot_elements.get(1).text());
        System.out.print("美金現金匯差(台灣銀行): ");
        System.out.println(d2 - d1);
        
        /*for(int i = 0; i < 20; i++){
        	System.out.println(bot_elements.get(i).text());  
        }*/
        	
	}
}
