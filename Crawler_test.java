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
	
	//宣告兩個字串陣列 一個儲存買入or賣出 一個儲存七種幣別
	private static String[] buyORsell = new String[2];
	private static String[] currency = new String[7];
	
	//宣告7個長度14的double陣列 儲存七間銀行的七種貨幣資料(包含買入賣出) 奇數存取買入偶數存取賣出
	private static double[] bot = new double[14];
 	
	public static void main(String[] args) throws Exception{
		
		buyORsell[0] = "現金買入";
		buyORsell[1] = "現金賣出";
		
		currency[0] = "USD"; // 美金
		currency[1] = "HKD"; // 港幣
		currency[2] = "JPY"; // 日圓
		currency[3] = "KRW"; // 韓元
		currency[4] = "EUR"; // 歐元
		currency[5] = "RMB"; // 人民幣
		currency[6] = "GBP"; // 英鎊 Great British Pound
		
		//System.out.println(buyORsell[0]);
		
		URL bot_web = new URL("http://rate.bot.com.tw/xrt?Lang=zh-TW"); // 台灣銀行
		URL mega_web = new URL("http://www.findrate.tw/bank/5/#.WTZwjROGOt8"); //兆豐銀行
		
		/*
         * 向網頁伺服發出請求，並將回應分析成document。
         * 第一個參數是：請求位置與QueryString。
         * 第二個參數是：連線時間(毫秒)，在指定時間內若無回應則會丟出IOException
         */
		
        Document bot_doc = Jsoup.parse(bot_web, 3000); // bot: 台灣銀行
        Document mega = Jsoup.parse(mega_web, 3000); // mega: 兆豐銀行
        
        /*String bot_title = bot.title(); // 抓取網頁標題
        String mega_title = sinopac.title();
        System.out.println(bot_title); 
        System.out.println(mega_title);*/
      
        Elements bot_elements = bot_doc.select("td.rate-content-cash"); //JQuery selector
        bot[0] = Double.parseDouble(bot_elements.get(0).text()); //台灣銀行美金現金買入
        bot[1] = Double.parseDouble(bot_elements.get(1).text()); //台灣銀行美金現金賣出
        bot[2] = Double.parseDouble(bot_elements.get(2).text()); //台灣銀行港幣現金買入
        bot[3] = Double.parseDouble(bot_elements.get(3).text()); //台灣銀行港幣現金賣出
        bot[4] = Double.parseDouble(bot_elements.get(14).text()); //台灣銀行日圓現金買入
        bot[5] = Double.parseDouble(bot_elements.get(15).text()); //台灣銀行日圓現金賣出
        bot[6] = Double.parseDouble(bot_elements.get(30).text()); //台灣銀行韓元現金買入
        bot[7] = Double.parseDouble(bot_elements.get(31).text()); //台灣銀行韓元現金賣出
        bot[8] = Double.parseDouble(bot_elements.get(28).text()); //台灣銀行歐元現金買入
        bot[9] = Double.parseDouble(bot_elements.get(29).text()); //台灣銀行歐元現金賣出
        bot[10] = Double.parseDouble(bot_elements.get(36).text()); //台灣銀行人民幣現金買入
        bot[11] = Double.parseDouble(bot_elements.get(37).text()); //台灣銀行人民幣現金賣出
        bot[12] = Double.parseDouble(bot_elements.get(4).text()); //台灣銀行英鎊現金買入
        bot[13] = Double.parseDouble(bot_elements.get(5).text()); //台灣銀行英鎊現金賣出
        
        
        for(int i = 0; i < bot.length; i++){
        	System.out.println(bot[i]);
        }
        /*Elements mega_elements = mega.select("td.WordB");
        System.out.println(bot_elements.size()); 
        System.out.println(mega_elements.size()); 
        for(int i = 0; i < 64; i++){
        	System.out.println(mega_elements.get(i).text());  
        }
        //System.out.println(mega_elements.get(0).text()); 
        
        System.out.print("美金現金買入(台灣銀行): ");
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
