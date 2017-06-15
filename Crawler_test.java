package final_project;

import java.io.IOException;
import java.net.URL;
import java.util.Scanner;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class Crawler_test {
	
	static int buyInbuyOut = 0;
	static int whichCurrency = 0;
	static int amount = 0;
	
	static double[] price = new double[7];
	static String BestBank = "";
	
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
 	
 	/* 各間銀行現金匯兌手續費
 	 * -------------------
 	 * 台灣銀行: 免手續費
 	 * 兆豐銀行: 免手續費
 	 * 玉山銀行: 每筆NT100元
 	 * 華南銀行: 免手續費
 	 * 渣打銀行: 每筆NT100元
 	 * 匯豐銀行: 0.5%手續費
 	 * 台新銀行: 免手續費
 	 */
	
	public static void main(String[] args){
		
		buyORsell[0] = "現金買入";
		buyORsell[1] = "現金賣出";
		
		currency[0] = "美金"; 
		currency[1] = "港幣"; 
		currency[2] = "日圓"; 
		currency[3] = "韓元"; 
		currency[4] = "歐元"; 
		currency[5] = "人民幣"; 
		currency[6] = "英鎊"; 
		
		try{
			Scanner input = new Scanner(System.in);
	        System.out.println("請選擇現金買入or現金賣出(0: 現金買入, 1: 現金賣出 ): ");
	        buyInbuyOut = input.nextInt();
	        System.out.println("請選擇貨幣(0: 美金, 1: 港幣, 2: 日幣, 3: 韓元, 4: 歐元, 5: 人民幣, 6: 英鎊): ");
	        whichCurrency = input.nextInt();
	        System.out.println("請輸入金額(最低輸入金額為1, 最大輸入金額為2,147,483,647, 你也比你想像中窮XD): ");
	        amount = input.nextInt();
			
	        /*System.out.println(buyInbuyOut);
	        System.out.println(whichCurrency);
	        System.out.println(amount);*/
	       
			
			start_Crawling(); 
			
			String Bank = get_BestBank(buyInbuyOut, whichCurrency);
			double BestRate = Crawler_test.get_BestRate(buyInbuyOut, whichCurrency);
			int BestPrice = (int) Crawler_test.get_BestPrice(buyInbuyOut, whichCurrency, amount);
			
			System.out.println(buyORsell[buyInbuyOut]);
			System.out.println("____________________________________________");
			System.out.println("您輸入的貨幣為: " + currency[whichCurrency]);
			System.out.println("換匯最佳銀行: " + Bank);
			System.out.println("最佳匯率: " + BestRate);
			System.out.println("您可換得的最划算金額(含手續費): " + BestPrice);
			System.out.println("____________________________________________");
			if(buyInbuyOut == 0){
				System.out.println("台灣銀行: " + bot[2*whichCurrency + 1]);
				System.out.println("兆豐銀行: " + mega[2*whichCurrency + 1]);
				System.out.println("玉山銀行: " + esun[2*whichCurrency + 1]);
				System.out.println("華南銀行: " + hncb[2*whichCurrency + 1]);
				System.out.println("渣打銀行: " + scb[2*whichCurrency + 1]);
				System.out.println("匯豐銀行: " + hsbc[2*whichCurrency + 1]);
				System.out.println("台新銀行: " + tsb[2*whichCurrency + 1]);
			}
			else{
				System.out.println("台灣銀行: " + bot[2*whichCurrency]);
				System.out.println("兆豐銀行: " + mega[2*whichCurrency]);
				System.out.println("玉山銀行: " + esun[2*whichCurrency]);
				System.out.println("華南銀行: " + hncb[2*whichCurrency]);
				System.out.println("渣打銀行: " + scb[2*whichCurrency]);
				System.out.println("匯豐銀行: " + hsbc[2*whichCurrency]);
				System.out.println("台新銀行: " + tsb[2*whichCurrency]);
			}
			
			/*System.out.println(bot[13]);   測試匯率是否正常用
	 		System.out.println(mega[13]);
			System.out.println(esun[13]);
			System.out.println(hncb[13]);
			System.out.println(scb[13]);
			System.out.println(hsbc[13]);
			System.out.println(tsb[13]);*/
			
	        input.close();       	
		}
		catch(Exception e){
			System.out.println("404 Not Found");
		}
	}
	
	public static void start_Crawling(){
		
		try{
			//網址一定要走http協定
			URL bot_web = new URL("http://rate.bot.com.tw/xrt?Lang=zh-TW"); // 台灣銀行
			URL mega_web = new URL("http://www.findrate.tw/bank/5/#.WTZwjROGOt8"); //兆豐銀行
			URL esun_web = new URL("https://www.esunbank.com.tw/bank/personal/deposit/rate/forex/foreign-exchange-rates"); //玉山銀行
			URL hncb_web = new URL("https://ibank.hncb.com.tw/netbank/pages/jsp/ExtSel/RTExange.html"); //華南銀行
			URL scb_web = new URL("http://www.standardchartered.com.tw/check/inquiry-rate-foreign-exchange.asp"); //渣打銀行
			URL hsbc_web = new URL("https://www.hsbc.com.tw/1/2/Misc/popup-tw/currency-calculator"); //匯豐銀行
			URL tsb_web = new URL("http://www.findrate.tw/bank/9/#.WTjyIBN97-Y"); //台新銀行
			
			/*
	         * 向網頁伺服發出請求，並將回應分析成document。
	         * 第一個參數是：請求位置與QueryString。
	         * 第二個參數是：連線時間(毫秒)，在指定時間內若無回應則會丟出IOException
	         */
			
	        Document bot_doc = Jsoup.parse(bot_web, 5000); // bot: 台灣銀行
	        Document mega_doc = Jsoup.parse(mega_web, 5000); // mega: 兆豐銀行
	        Document esun_doc = Jsoup.parse(esun_web, 5000); // esun: 玉山銀行
	        Document hncb_doc = Jsoup.parse(hncb_web, 5000); // hncb: 華南銀行
	        Document scb_doc = Jsoup.parse(scb_web, 5000); // scb: 渣打銀行
	        Document hsbc_doc = Jsoup.parse(hsbc_web, 5000); // hsbc:匯豐銀行
	        Document tsb_doc = Jsoup.parse(tsb_web, 5000); // tsb: 台新銀行
	        
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
	        esun[0] = Double.parseDouble(esun_elements.get(10).text()); //玉山銀行美金現金買入
	        esun[1] = Double.parseDouble(esun_elements.get(11).text()); //玉山銀行美金現金賣出
	        esun[2] = Double.parseDouble(esun_elements.get(20).text()); //玉山銀行港幣現金買入
	        esun[3] = Double.parseDouble(esun_elements.get(21).text()); //玉山銀行港幣現金賣出
	        esun[4] = Double.parseDouble(esun_elements.get(25).text()); //玉山銀行日圓現金買入
	        esun[5] = Double.parseDouble(esun_elements.get(26).text()); //玉山銀行日圓現金賣出
	        //esun[6] = Double.parseDouble(esun_elements.get(52).text()); //玉山銀行韓元現金買入
	        //esun[7] = Double.parseDouble(esun_elements.get(53).text()); //玉山銀行韓元現金賣出
	        esun[8] = Double.parseDouble(esun_elements.get(30).text()); //玉山銀行歐元現金買入
	        esun[9] = Double.parseDouble(esun_elements.get(31).text()); //玉山銀行歐元現金賣出
	        esun[10] = Double.parseDouble(esun_elements.get(15).text()); //玉山銀行人民幣現金買入
	        esun[11] = Double.parseDouble(esun_elements.get(16).text()); //玉山銀行人民幣現金賣出
	        esun[12] = Double.parseDouble(esun_elements.get(45).text()); //玉山銀行英鎊現金買入
	        esun[13] = Double.parseDouble(esun_elements.get(46).text()); //玉山銀行英鎊現金賣出
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
		catch(IOException e){
			e.printStackTrace();
			System.out.println("404 Not Found");
			System.exit(0);
		}
	}
	
	public static double get_BestPrice(int buyInbuyOut, int whichCurrency, int amount){
		
		double BestPrice = 0;
		
		if(buyInbuyOut == 0){ // 現金買入(對使用者而言): 找尋換算金額之最大值  有考慮手續費
			if(bot[2*whichCurrency + 1] != 0.0){
				price[0] = amount / bot[2*whichCurrency + 1]; 
			}
			if(mega[2*whichCurrency + 1] != 0.0){
				price[1] = amount / mega[2*whichCurrency + 1]; 
			}
			if(esun[2*whichCurrency + 1] != 0.0){
				price[2] = amount / esun[2*whichCurrency + 1] - 100; 
			}
			if(hncb[2*whichCurrency + 1] != 0.0){
				price[3] = amount / hncb[2*whichCurrency + 1]; 
			}
			if(scb[2*whichCurrency + 1] != 0.0){
				price[4] = amount / scb[2*whichCurrency + 1] - 100; 
			}
			if(hsbc[2*whichCurrency + 1] != 0.0){
				price[5] = (amount / hsbc[2*whichCurrency + 1]) * 0.095; 
			}
			if(tsb[2*whichCurrency + 1] != 0.0){
				price[6] = amount / tsb[2*whichCurrency + 1]; 
			}
			
			
			BestPrice = price[0];
			for(int i = 1; i < 7; i++){
				if(BestPrice < price[i]){
					BestPrice = price[i];
				}
			}
		}
		if(buyInbuyOut == 1){ // 現金賣出(對使用者而言): 找尋換算金額之最大值  有考慮手續費
			price[0] = amount * bot[2*whichCurrency];
			price[1] = amount * mega[2*whichCurrency];
			price[2] = amount * esun[2*whichCurrency] - 100;
			price[3] = amount * hncb[2*whichCurrency];
			price[4] = amount * scb[2*whichCurrency] - 100;
			price[5] = (amount * hsbc[2*whichCurrency]) * 0.995;
			price[6] = amount * tsb[2*whichCurrency];
			
			BestPrice = price[0];
			for(int i = 1; i < 7; i++){
				if(BestPrice < price[i]){
					BestPrice = price[i];
				}
			}
		}
		
		return BestPrice;	
	}
	
	public static String get_BestBank(int buyInbuyOut, int whichCurrency){
		
		double BestPrice = get_BestPrice(buyInbuyOut, whichCurrency, amount);
		
		if(BestPrice == price[0]){
			BestBank = "台灣銀行";
		}
		if(BestPrice == price[1]){
			BestBank = "兆豐銀行";
		}
		if(BestPrice == price[2]){
			BestBank = "玉山銀行";
		}
		if(BestPrice == price[3]){
			BestBank = "華南銀行";
		}
		if(BestPrice == price[4]){
			BestBank = "渣打銀行";
		}
		if(BestPrice == price[5]){
			BestBank = "匯豐銀行";
		}
		if(BestPrice == price[6]){
			BestBank = "台新銀行";
		}
		
		return BestBank;
	}
	
	public static double get_BestRate(int buyInbuyOut, int whichCurrency){
		
		double BestRate = 0.0;
		
		if(buyInbuyOut == 0){
			if(BestBank.equals("台灣銀行")){
				BestRate = bot[2*whichCurrency + 1];
			}
			if(BestBank.equals("兆豐銀行")){
				BestRate = mega[2*whichCurrency + 1];
			}
			if(BestBank.equals("玉山銀行")){
				BestRate = esun[2*whichCurrency + 1];
			}
			if(BestBank.equals("華南銀行")){
				BestRate = hncb[2*whichCurrency + 1];
			}
			if(BestBank.equals("渣打銀行")){
				BestRate = scb[2*whichCurrency + 1];
			}
			if(BestBank.equals("匯豐銀行")){
				BestRate = hsbc[2*whichCurrency + 1];
			}
			if(BestBank.equals("台新銀行")){
				BestRate = tsb[2*whichCurrency + 1];
			}
		}
		if(buyInbuyOut == 1){
			if(BestBank.equals("台灣銀行")){
				BestRate = bot[2*whichCurrency];
			}
			if(BestBank.equals("兆豐銀行")){
				BestRate = mega[2*whichCurrency];
			}
			if(BestBank.equals("玉山銀行")){
				BestRate = esun[2*whichCurrency];
			}
			if(BestBank.equals("華南銀行")){
				BestRate = hncb[2*whichCurrency];
			}
			if(BestBank.equals("渣打銀行")){
				BestRate = scb[2*whichCurrency];
			}
			if(BestBank.equals("匯豐銀行")){
				BestRate = hsbc[2*whichCurrency];
			}
			if(BestBank.equals("台新銀行")){
				BestRate = tsb[2*whichCurrency];
			}
		}
		return BestRate;
	}
}
