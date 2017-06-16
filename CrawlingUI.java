package final_project;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

//import java.util.Scanner;
//import java.lang.StringBuffer;

public class CrawlingUI extends Crawler_test{
	
	private JFrame frame;	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CrawlingUI window = new CrawlingUI();
					window.frame.setVisible(true);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public CrawlingUI() {
		initialize();
	}
	
	/**
	 * Initialize the contents of the frame.

	 */
	
	private void initialize() {
		
		//³]¸mµøµ¡
		frame = new JFrame();
		frame.setTitle("\u9280\u884C\u532F\u7387\u63DB\u7B97");
		frame.setBounds(100, 100, 687, 300);
		frame.getContentPane().setLayout(null);
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.addWindowListener(new closeTemp());
		
		//³]¸m¼ÐÅÒ
		JLabel label_bank = new JLabel("您可換得的最佳銀行為");
		label_bank.setBounds(14, 83, 162, 21);
		frame.getContentPane().add(label_bank);
		label_bank.setFont(new Font("·L³n¥¿¶ÂÅé", Font.BOLD, 15));
		
		JLabel label_rate = new JLabel("\u60A8\u53EF\u63DB\u5F97\u7684\u6700\u4F73\u532F\u7387\u70BA\uFF1A");
		label_rate.setFont(new Font("·L³n¥¿¶ÂÅé", Font.BOLD, 15));
		label_rate.setBounds(14, 121, 162, 21);
		frame.getContentPane().add(label_rate);
		
		JLabel label_price = new JLabel("\u60A8\u53EF\u63DB\u5F97\u7684\u6700\u4F73\u50F9\u9322\u70BA\uFF1A");
		label_price.setFont(new Font("·L³n¥¿¶ÂÅé", Font.BOLD, 15));
		label_price.setBounds(14, 155, 162, 21);
		frame.getContentPane().add(label_price);
		
		//³]¸m¤å¦r°Ï
		JTextArea textArea_bank = new JTextArea();
		textArea_bank.setBounds(179, 84, 116, 25);
		frame.getContentPane().add(textArea_bank);
		textArea_bank.setColumns(10);
		
		JTextArea textArea_rate = new JTextArea();
		textArea_rate.setColumns(10);
		textArea_rate.setBounds(179, 118, 116, 25);
		frame.getContentPane().add(textArea_rate);
		
		JTextArea textArea_price = new JTextArea();
		textArea_price.setColumns(10);
		textArea_price.setBounds(179, 156, 116, 25);
		frame.getContentPane().add(textArea_price);
		
		//³]¸m²{ª÷¶R¤Jµøµ¡
		JInternalFrame internalFrame_buyin = new JInternalFrame("\u73FE\u91D1\u8CB7\u5165\r\n");
		internalFrame_buyin.setBounds(330, 44, 300, 150);
		frame.getContentPane().add(internalFrame_buyin);
		internalFrame_buyin.setBackground(Color.ORANGE);
		internalFrame_buyin.setOpaque(true);
		internalFrame_buyin.setResizable(true);
		internalFrame_buyin.setClosable(true);
		internalFrame_buyin.getContentPane().setLayout(null);
		//²{ª÷¶R¤Jlabel
		JLabel labelbuyin = new JLabel("\u8ACB\u8F38\u5165\u91D1\u984D");
		labelbuyin.setBounds(50, 25, 75, 19);
		internalFrame_buyin.getContentPane().add(labelbuyin);
		//²{ª÷¶R¤Jtext
		JTextArea textAreaprice_0 = new JTextArea();
		textAreaprice_0.setBounds(50, 50, 100, 25);
		internalFrame_buyin.getContentPane().add(textAreaprice_0);
		//²{ª÷¶R¤J½T©w
		JButton buttonenter_0 = new JButton("\u78BA\u5B9A");
		buttonenter_0.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String price=textAreaprice_0.getText();
				amount=Integer.parseInt(price);
				//´ú¸ÕbuyInbuyOut,amount­È
				//System.out.println(buyInbuyOut);
				//System.out.println(whichCurrency);
				//System.out.println(amount);
				
				Crawler_test.start_Crawling();
				double bestrate=0.00;
				double bestprice=0.00;
				BestBank=Crawler_test.get_BestBank(buyInbuyOut, whichCurrency);
				bestrate=Crawler_test.get_BestRate(buyInbuyOut, whichCurrency);
				bestprice=Crawler_test.get_BestPrice(buyInbuyOut, whichCurrency, amount);
				String sbestrate=String.valueOf(bestrate);
				String sbestprice=String.valueOf(bestprice);
				textArea_bank.append(BestBank);
				textArea_rate.append(sbestrate);
				textArea_price.append(sbestprice);
				internalFrame_buyin.setVisible(false);
			}
		});
		buttonenter_0.setBounds(171, 48, 99, 27);
		internalFrame_buyin.getContentPane().add(buttonenter_0);
		internalFrame_buyin.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		//³]¸m²{ª÷½æ¥Xµøµ¡
		JInternalFrame internalFrame_buyout = new JInternalFrame("\u73FE\u91D1\u8CE3\u51FA\r\n");
		internalFrame_buyout.setBounds(341, 77, 300, 150);
		frame.getContentPane().add(internalFrame_buyout);
		internalFrame_buyout.setBackground(Color.ORANGE);
		internalFrame_buyout.setOpaque(true);
		internalFrame_buyout.setResizable(true);
		internalFrame_buyout.setClosable(true);
		internalFrame_buyout.getContentPane().setLayout(null);
		//²{ª÷½æ¥Xlabel
		JLabel labelbuyout = new JLabel("\u8ACB\u8F38\u5165\u91D1\u984D");
		labelbuyout.setBounds(50, 25, 75, 19);
		internalFrame_buyout.getContentPane().add(labelbuyout);
		//²{ª÷½æ¥Xtext
		JTextArea textAreaprice_1 = new JTextArea();
		textAreaprice_1.setBounds(50, 50, 100, 25);
		internalFrame_buyout.getContentPane().add(textAreaprice_1);
		//²{ª÷½æ¥X½T©w
		JButton buttonenter_1 = new JButton("\u78BA\u5B9A");
		buttonenter_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String price=textAreaprice_1.getText();
				amount=Integer.parseInt(price);
				//´ú¸ÕbuyInbuyOut,amount­È
				//System.out.println(buyInbuyOut);
				//System.out.println(whichCurrency);
				//System.out.println(amount);
				
				Crawler_test.start_Crawling();
				double bestrate=0.00;
				double bestprice=0.00;
				BestBank=Crawler_test.get_BestBank(buyInbuyOut, whichCurrency);
				bestrate=Crawler_test.get_BestRate(buyInbuyOut, whichCurrency);
				bestprice=Crawler_test.get_BestPrice(buyInbuyOut, whichCurrency, amount);
				String sbestrate=String.valueOf(bestrate);
				String sbestprice=String.valueOf(bestprice);
				textArea_bank.append(BestBank);
				textArea_rate.append(sbestrate);
				textArea_price.append(sbestprice);
				internalFrame_buyout.setVisible(false);
			}
		});
		buttonenter_1.setBounds(171, 48, 99, 27);
		internalFrame_buyout.getContentPane().add(buttonenter_1);
		internalFrame_buyout.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		//³]¸m¿ï³æ¦C
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBounds(0, 0, 669, 26);
		frame.getContentPane().add(menuBar);
		

		//«Ø¥ß¹ô§O¿ï¶µ¿ï³æ
		JMenu option = new JMenu("\u5E63\u5225\u9078\u9805");
		option.setFont(new Font("·L³n¥¿¶ÂÅé", Font.BOLD, 15));
		menuBar.add(option);
		
		//¹ô§O«¬ºA³]¸m
		JCheckBoxMenuItem checkmenuItemUSD = new JCheckBoxMenuItem("\u7F8E\u91D1");
		checkmenuItemUSD.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				whichCurrency=0;
				//´ú¸Õ­È
				//System.out.println(whichCurrency);
			}
		});
		checkmenuItemUSD.setFont(new Font("·L³n¥¿¶ÂÅé", Font.BOLD, 15));
		option.add(checkmenuItemUSD);
		JCheckBoxMenuItem checkmenuItemHKD = new JCheckBoxMenuItem("\u6E2F\u5E63");
		checkmenuItemHKD.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				whichCurrency=1;
				//´ú¸Õ­È
				//System.out.println(whichCurrency);
			}
		});
		option.add(checkmenuItemHKD);
		checkmenuItemHKD.setFont(new Font("·L³n¥¿¶ÂÅé", Font.BOLD, 15));
		JCheckBoxMenuItem checkmenuItemJPY = new JCheckBoxMenuItem("\u65E5\u5143");
		checkmenuItemJPY.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				whichCurrency=2;
				//´ú¸Õ­È
				//System.out.println(whichCurrency);
			}
		});
		checkmenuItemJPY.setFont(new Font("·L³n¥¿¶ÂÅé", Font.BOLD, 15));
		option.add(checkmenuItemJPY);
		JCheckBoxMenuItem checkmenuItemKRW = new JCheckBoxMenuItem("\u97D3\u5143");
		checkmenuItemKRW.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				whichCurrency=3;
				//´ú¸Õ­È
				//System.out.println(whichCurrency);
			}
		});
		checkmenuItemKRW.setFont(new Font("·L³n¥¿¶ÂÅé", Font.BOLD, 15));
		option.add(checkmenuItemKRW);
		JCheckBoxMenuItem checkmenuItemEUR = new JCheckBoxMenuItem("\u6B50\u5143");
		checkmenuItemEUR.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				whichCurrency=4;
				//´ú¸Õ­È
				//System.out.println(whichCurrency);
			}
		});
		checkmenuItemEUR.setFont(new Font("·L³n¥¿¶ÂÅé", Font.BOLD, 15));
		option.add(checkmenuItemEUR);
		JCheckBoxMenuItem checkmenuItemRMB = new JCheckBoxMenuItem("\u4EBA\u6C11\u5E63");
		checkmenuItemRMB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				whichCurrency=5;
				//´ú¸Õ­È
				//System.out.println(whichCurrency);
			}
		});
		checkmenuItemRMB.setFont(new Font("·L³n¥¿¶ÂÅé", Font.BOLD, 15));
		option.add(checkmenuItemRMB);
		JCheckBoxMenuItem checkmenuItemGBP = new JCheckBoxMenuItem("\u82F1\u938A");
		checkmenuItemGBP.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				whichCurrency=6;
				//´ú¸Õ­È
				//System.out.println(whichCurrency);
			}
		});
		checkmenuItemGBP.setFont(new Font("·L³n¥¿¶ÂÅé", Font.BOLD, 15));
		option.add(checkmenuItemGBP);
		
		//³]¸m¿ï³æ
		JMenu choose = new JMenu("\u9078\u64C7");
		choose.setFont(new Font("·L³n¥¿¶ÂÅé", Font.BOLD, 15));
		menuBar.add(choose);
				
		//³]¸m²{ª÷¶R¤JÁä
		JMenuItem buyin = new JMenuItem("\u73FE\u91D1\u8CB7\u5165");
		buyin.setFont(new Font("·L³n¥¿¶ÂÅé", Font.BOLD, 15));
		buyin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				buyInbuyOut=0;
				internalFrame_buyin.setVisible(true);
			}
		});
		choose.add(buyin);
		
		//³]¸m²{ª÷½æ¥XÁä
		JMenuItem sellout = new JMenuItem("\u73FE\u91D1\u8CE3\u51FA");
		sellout.setFont(new Font("·L³n¥¿¶ÂÅé", Font.BOLD, 15));
		sellout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				buyInbuyOut=1;
				internalFrame_buyout.setVisible(true);
			}
		});
		choose.add(sellout);
	} 
}


class closeTemp implements WindowListener{
	public void windowClosing(WindowEvent e)
   {
       int choice ;
       choice = JOptionPane.showConfirmDialog(null,
                   "確定離開本程式?","關閉視窗", JOptionPane
                   .YES_NO_OPTION,JOptionPane.WARNING_MESSAGE);
      if (choice == JOptionPane.YES_OPTION)
      {
           System.exit(0);
      }
      else
     {
          JOptionPane.showMessageDialog(null, "取消關閉視窗");
     }
   }
   public void windowActivated(WindowEvent e) {}
   public void windowClosed(WindowEvent e) {}
   public void windowDeactivated(WindowEvent e) {}
   public void windowDeiconified(WindowEvent e) {}
   public void windowIconified(WindowEvent e) {}
   public void windowOpened(WindowEvent e) {}  
}
