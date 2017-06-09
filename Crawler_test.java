package final_project;

import java.io.*;
import java.net.URL;
//import java.net.URLConnection;
//import java.util.Iterator;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
//import org.jsoup.nodes.Element;
//import org.jsoup.parser.Parser;
import org.jsoup.select.Elements;

public class Crawler_test {
	// 銀行資料參考網頁: http://www.findrate.tw/glossary.html#.WTbShhOGP-Y
	//宣告兩個字串陣列 一個儲存買入or賣出 一個儲存七種幣別
	private static String[] buyORsell = new String[2];
	private static String[] currency = new String[7];
	
	//宣告7個長度14的double陣列 儲存七間銀行的七種貨幣資料(包含買入賣出) 奇數存取買入偶數存取賣出
	private static double[] bot = new double[14]; //台灣銀行
	private static double[] mega = new double[14]; //兆豐銀行
	private static double[] esun = new double[14]; //玉山銀行
	private static double[] hncb = new double[14]; // 華南銀行
	private static double[] scb = new double[14]; // 渣打銀行
 	private static double[] hsbc = new double[14]; // 匯豐銀行
 	private static double[] tsb = new double[14]; // 台新銀行
	
	public static void main(String[] args) throws IOException{
		
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
		URL esun_web = new URL("https://www.esunbank.com.tw/bank/Layouts/esunbank/Accessibility/rate_exchange.aspx"); //玉山銀行
		URL hncb_web = new URL("https://ibank.hncb.com.tw/netbank/pages/jsp/ExtSel/RTExange.html"); //華南銀行
		URL scb_web = new URL("http://www.standardchartered.com.tw/check/inquiry-rate-foreign-exchange.asp"); //渣打銀行
		URL hsbc_web = new URL("https://www.hsbc.com.tw/1/2/Misc/popup-tw/currency-calculator"); //匯豐銀行
		URL tsb_web = new URL("http://www.findrate.tw/bank/9/#.WTjyIBN97-Y"); //台新銀行
		
		/*
         * 向網頁伺服發出請求，並將回應分析成document。
         * 第一個參數是：請求位置與QueryString。
         * 第二個參數是：連線時間(毫秒)，在指定時間內若無回應則會丟出IOException
         */
		
        Document bot_doc = Jsoup.parse(bot_web, 4000); // bot: 台灣銀行
        Document mega_doc = Jsoup.parse(mega_web, 4000); // mega: 兆豐銀行
        Document esun_doc = Jsoup.parse(esun_web, 4000); // esun: 玉山銀行
        Document hncb_doc = Jsoup.parse(hncb_web, 4000); // hncb: 華南銀行
        Document scb_doc = Jsoup.parse(scb_web, 4000); // scb: 渣打銀行
        Document hsbc_doc = Jsoup.parse(hsbc_web, 4000); // hsbc:匯豐銀行
        Document tsb_doc = Jsoup.parse(tsb_web, 4000); // tsb: 台新銀行
        
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
        
        /*for(int i = 0; i < bot.length; i++){
        	System.out.println(bot[i]);
        }*/
        
        Elements mega_elements = mega_doc.select("td.WordB");
        mega[0] = Double.parseDouble(mega_elements.get(0).text()); //兆豐銀行美金現金買入
        mega[1] = Double.parseDouble(mega_elements.get(1).text()); //兆豐銀行美金現金賣出
        mega[2] = Double.parseDouble(mega_elements.get(4).text()); //兆豐銀行港幣現金買入
        mega[3] = Double.parseDouble(mega_elements.get(5).text()); //兆豐銀行港幣現金賣出
        mega[4] = Double.parseDouble(mega_elements.get(12).text()); //兆豐銀行日圓現金買入
        mega[5] = Double.parseDouble(mega_elements.get(13).text()); //兆豐銀行日圓現金賣出
        mega[6] = Double.parseDouble(mega_elements.get(52).text()); //兆豐銀行韓元現金買入
        mega[7] = Double.parseDouble(mega_elements.get(53).text()); //兆豐銀行韓元現金賣出
        mega[8] = Double.parseDouble(mega_elements.get(48).text()); //兆豐銀行歐元現金買入
        mega[9] = Double.parseDouble(mega_elements.get(49).text()); //兆豐銀行歐元現金賣出
        mega[10] = Double.parseDouble(mega_elements.get(76).text()); //兆豐銀行人民幣現金買入
        mega[11] = Double.parseDouble(mega_elements.get(77).text()); //兆豐銀行人民幣現金賣出
        mega[12] = Double.parseDouble(mega_elements.get(8).text()); //兆豐銀行英鎊現金買入
        mega[13] = Double.parseDouble(mega_elements.get(9).text()); //兆豐銀行英鎊現金賣出
        
        /*for(int i = 0; i < mega.length; i++){
        	System.out.println(mega[i]);
        }*/
        
        Elements esun_elements = esun_doc.select("td");
        esun[0] = Double.parseDouble(esun_elements.get(1).text()); //玉山銀行美金現金買入
        esun[1] = Double.parseDouble(esun_elements.get(2).text()); //玉山銀行美金現金賣出
        esun[2] = Double.parseDouble(esun_elements.get(4).text()); //玉山銀行港幣現金買入
        esun[3] = Double.parseDouble(esun_elements.get(5).text()); //玉山銀行港幣現金賣出
        esun[4] = Double.parseDouble(esun_elements.get(7).text()); //玉山銀行日圓現金買入
        esun[5] = Double.parseDouble(esun_elements.get(8).text()); //玉山銀行日圓現金賣出
        //esun[6] = Double.parseDouble(esun_elements.get(52).text()); //玉山銀行韓元現金買入
        //esun[7] = Double.parseDouble(esun_elements.get(53).text()); //玉山銀行韓元現金賣出
        esun[8] = Double.parseDouble(esun_elements.get(10).text()); //玉山銀行歐元現金買入
        esun[9] = Double.parseDouble(esun_elements.get(11).text()); //玉山銀行歐元現金賣出
        esun[10] = Double.parseDouble(esun_elements.get(40).text()); //玉山銀行人民幣現金買入
        esun[11] = Double.parseDouble(esun_elements.get(41).text()); //玉山銀行人民幣現金賣出
        esun[12] = Double.parseDouble(esun_elements.get(13).text()); //玉山銀行英鎊現金買入
        esun[13] = Double.parseDouble(esun_elements.get(14).text()); //玉山銀行英鎊現金賣出
        //玉山銀行無韓元現金兌換 因此陣列存入值皆為 0.0
        
        /*for(int i = 0; i < esun.length; i++){
        	System.out.println(esun[i]);
        }*/
        
        Elements hncb_elements = hncb_doc.select("td");
        hncb[0] = Double.parseDouble(hncb_elements.get(14).text()); //華南銀行美金現金買入
        hncb[1] = Double.parseDouble(hncb_elements.get(15).text()); //華南銀行美金現金賣出
        hncb[2] = Double.parseDouble(hncb_elements.get(20).text()); //華南銀行港幣現金買入
        hncb[3] = Double.parseDouble(hncb_elements.get(21).text()); //華南銀行港幣現金賣出
        hncb[4] = Double.parseDouble(hncb_elements.get(53).text()); //華南銀行日圓現金買入
        hncb[5] = Double.parseDouble(hncb_elements.get(54).text()); //華南銀行日圓現金賣出
        hncb[6] = Double.parseDouble(hncb_elements.get(77).text()); //華南銀行韓元現金買入
        hncb[7] = Double.parseDouble(hncb_elements.get(78).text()); //華南銀行韓元現金賣出
        hncb[8] = Double.parseDouble(hncb_elements.get(68).text()); //華南銀行歐元現金買入
        hncb[9] = Double.parseDouble(hncb_elements.get(69).text()); //華南銀行歐元現金賣出
        hncb[10] = Double.parseDouble(hncb_elements.get(74).text()); //華南銀行人民幣現金買入
        hncb[11] = Double.parseDouble(hncb_elements.get(75).text()); //華南銀行人民幣現金賣出
        hncb[12] = Double.parseDouble(hncb_elements.get(26).text()); //華南銀行英鎊現金買入
        hncb[13] = Double.parseDouble(hncb_elements.get(27).text()); //華南銀行英鎊現金賣出
        
        /*for(int i = 0; i < hncb.length; i++){
        	System.out.println(hncb[i]);
        }*/
        
        Elements scb_elements = scb_doc.select("td.talign_c");
        scb[0] = Double.parseDouble(scb_elements.get(0).text()); //渣打銀行美金現金買入
        scb[1] = Double.parseDouble(scb_elements.get(1).text()); //渣打銀行美金現金賣出
        scb[2] = Double.parseDouble(scb_elements.get(4).text()); //渣打銀行港幣現金買入
        scb[3] = Double.parseDouble(scb_elements.get(5).text()); //渣打銀行港幣現金賣出
        scb[4] = Double.parseDouble(scb_elements.get(28).text()); //渣打銀行日圓現金買入
        scb[5] = Double.parseDouble(scb_elements.get(29).text()); //渣打銀行日圓現金賣出
        //scb[6] = Double.parseDouble(scb_elements.get(52).text()); //渣打銀行韓元現金買入
        //scb[7] = Double.parseDouble(scb_elements.get(53).text()); //渣打銀行韓元現金賣出
        scb[8] = Double.parseDouble(scb_elements.get(36).text()); //渣打銀行歐元現金買入
        scb[9] = Double.parseDouble(scb_elements.get(37).text()); //渣打銀行歐元現金賣出
        scb[10] = Double.parseDouble(scb_elements.get(52).text()); //渣打銀行人民幣現金買入
        scb[11] = Double.parseDouble(scb_elements.get(53).text()); //渣打銀行人民幣現金賣出
        scb[12] = Double.parseDouble(scb_elements.get(8).text()); //渣打銀行英鎊現金買入
        scb[13] = Double.parseDouble(scb_elements.get(9).text()); //渣打銀行英鎊現金賣出*/
        //渣打銀行無韓元現金匯兌
        
        /*for(int i = 0; i < scb.length; i++){
        	System.out.println(scb[i]);
        }*/
        
        Elements hsbc_elements = hsbc_doc.select("td.ForRatesColumn02");
        hsbc[0] = Double.parseDouble(hsbc_elements.get(3).text()); //匯豐銀行美金現金買入
        hsbc[1] = Double.parseDouble(hsbc_elements.get(4).text()); //匯豐銀行美金現金賣出
        hsbc[2] = Double.parseDouble(hsbc_elements.get(28).text()); //匯豐銀行港幣現金買入
        hsbc[3] = Double.parseDouble(hsbc_elements.get(29).text()); //匯豐銀行港幣現金賣出
        hsbc[4] = Double.parseDouble(hsbc_elements.get(48).text()); //匯豐銀行日圓現金買入
        hsbc[5] = Double.parseDouble(hsbc_elements.get(49).text()); //匯豐銀行日圓現金賣出
        //hsbc[6] = Double.parseDouble(hsbc_elements.get(52).text()); //匯豐銀行韓元現金買入
        //hsbc[7] = Double.parseDouble(hsbc_elements.get(53).text()); //匯豐銀行韓元現金賣出
        hsbc[8] = Double.parseDouble(hsbc_elements.get(8).text()); //匯豐銀行歐元現金買入
        hsbc[9] = Double.parseDouble(hsbc_elements.get(9).text()); //匯豐銀行歐元現金賣出
        hsbc[10] = Double.parseDouble(hsbc_elements.get(63).text()); //匯豐銀行人民幣現金買入
        hsbc[11] = Double.parseDouble(hsbc_elements.get(64).text()); //匯豐銀行人民幣現金賣出
        hsbc[12] = Double.parseDouble(hsbc_elements.get(13).text()); //匯豐銀行英鎊現金買入
        hsbc[13] = Double.parseDouble(hsbc_elements.get(14).text()); //匯豐銀行英鎊現金賣出
        // 匯豐銀行無韓元匯兌
        
        /*for(int i = 0; i < hsbc.length; i++){
        	System.out.println(hsbc[i]);
        }*/
        
        Elements tsb_elements = tsb_doc.select("td.wordB");
        tsb[0] = Double.parseDouble(tsb_elements.get(48).text()); //台新銀行美金現金買入
        tsb[1] = Double.parseDouble(tsb_elements.get(49).text()); //台新銀行美金現金賣出
        tsb[2] = Double.parseDouble(tsb_elements.get(24).text()); //台新銀行港幣現金買入
        tsb[3] = Double.parseDouble(tsb_elements.get(25).text()); //台新銀行港幣現金賣出
        tsb[4] = Double.parseDouble(tsb_elements.get(28).text()); //台新銀行日圓現金買入
        tsb[5] = Double.parseDouble(tsb_elements.get(29).text()); //台新銀行日圓現金賣出
        //tsb[6] = Double.parseDouble(tsb_elements.get(52).text()); //台新銀行韓元現金買入
        //tsb[7] = Double.parseDouble(tsb_elements.get(53).text()); //台新銀行韓元現金賣出
        tsb[8] = Double.parseDouble(tsb_elements.get(16).text()); //台新銀行歐元現金買入
        tsb[9] = Double.parseDouble(tsb_elements.get(17).text()); //台新銀行歐元現金賣出
        tsb[10] = Double.parseDouble(tsb_elements.get(12).text()); //台新銀行人民幣現金買入
        tsb[11] = Double.parseDouble(tsb_elements.get(13).text()); //台新銀行人民幣現金賣出
        //tsb[12] = Double.parseDouble(tsb_elements.get(48).text()); //台新銀行英鎊現金買入
        //tsb[13] = Double.parseDouble(tsb_elements.get(49).text()); //台新銀行英鎊現金賣出 
        //台新銀行無韓元匯兌, 無英鎊現金匯兌
        /*for(int i = 0; i < tsb.length; i++){
        	System.out.println(tsb[i]);
        }
        /*for(int i = 0; i < 72; i++){
        	System.out.println(tsb_elements.get(i).text());
        } 依序輸出所選取tag之所有元素*/
        
        //System.out.println(bot_elements.size());  求該tag數量方便確認
        //System.out.println(mega_elements.size());
        //System.out.println(esun_elements.size()); 
        //System.out.println(hncb_elements.size()); 
        //System.out.println(scb_elements.size()); 
        //System.out.println(hsbc_elements.size()); 
        //System.out.println(tsb_elements.size()); 
                	
	}
}
