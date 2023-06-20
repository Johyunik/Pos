package GUI;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;

public class Main_POS extends JFrame{

	private static Pos_pos pos = null;
	private static POS_itemManagement itemManagement = null;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		pos = new Pos_pos();
		itemManagement = new POS_itemManagement();
		
		JTabbedPane jtab = new JTabbedPane();
		
		jtab.add("POS", pos);
		jtab.add("상품관리", itemManagement);
		
		
		Main_POS main_pos = new Main_POS();
		
		main_pos.setTitle("POS System");
		
		main_pos.add(jtab);
		
		main_pos.setSize(550, 400);
		main_pos.setVisible(true);
		
	}

}
