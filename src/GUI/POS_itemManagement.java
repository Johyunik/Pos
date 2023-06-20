package GUI;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import DB.Item;
import DB.ItemDAO;

public class POS_itemManagement extends JPanel implements ActionListener{
	
	private JLabel display = null;
	private JButton btnSelect = null;
	private JButton btnInsert = null;
	private JButton btnUpdate = null;
	private JButton btnDelete = null;
	private JTable jtableStock = null; // 데이터정보-model
	public DefaultTableModel model = null; // JTable에 연결될 model(data)
	
	public POS_itemManagement() {
		
		display = new JLabel("재고현황");
		btnSelect = new JButton("상품 새로고침");
		btnInsert = new JButton("등록");
		btnUpdate = new JButton("수정");
		btnDelete = new JButton("삭제");
		
		String name[] = {"ID", "상품명", "재고량", "가격"};
		model = new DefaultTableModel(name, 0); // 0 을 적으면 알아서 갯수만큼 붙음
		jtableStock = new JTable(model); // model
		JScrollPane scroll = new JScrollPane(jtableStock);
		
		setLayout(null);
		
		display.setSize(100, 40);
		display.setLocation(60, 20);
		
		btnSelect.setBounds(10, 70, 150, 40);
		btnInsert.setBounds(10, 130, 150, 40);
		btnUpdate.setBounds(10, 190, 150, 40);
		btnDelete.setBounds(10, 250, 150, 40);
		
		scroll.setBounds(200, 30, 300, 270);
		
		btnSelect.setBackground(new Color(255,051,153));
		btnInsert.setBackground(new Color(255,204,255));
		btnUpdate.setBackground(new Color(255,153,204));
		btnDelete.setBackground(new Color(255,153,255));
		
		add(display);
		add(btnSelect);
		add(btnInsert);
		add(btnUpdate);
		add(btnDelete);
		add(scroll);
		
		btnSelect.addActionListener(this);
		btnInsert.addActionListener(this);
		btnUpdate.addActionListener(this);
		btnDelete.addActionListener(this);
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
		if(e.getActionCommand() == "상품 새로고침") {
			//System.out.println("상품 새로고침 눌림");
			
			loadDB();
			
		} else if(e.getActionCommand() == "등록") {
			//System.out.println("등록 버튼 눌림");
			
			String text = e.getActionCommand();
			StockWindow window = new StockWindow(text);
			
		} else if(e.getActionCommand() == "수정") {
			//System.out.println("수정 버튼 눌림");
			
			int row = jtableStock.getSelectedRow();
			
			if(row == -1) {
				JOptionPane.showMessageDialog(null, "데이터를 선택하지 않았습니다.", "경고!", JOptionPane.WARNING_MESSAGE);
			} else {
				String text = btnUpdate.getText();
				String id = (String)jtableStock.getValueAt(row, 0);
				String name = (String)jtableStock.getValueAt(row, 1);
				String stock = (String)jtableStock.getValueAt(row, 2);
				String price = (String)jtableStock.getValueAt(row, 3);
				
				Item item = new Item();
				item.setId(Integer.parseInt(id));
				item.setItem_name(name);
				item.setItem_stock(Integer.parseInt(stock));
				item.setItem_price(Integer.parseInt(price));
				
				StockWindow window = new StockWindow(text, item);
			}
			
		} else if(e.getActionCommand() == "삭제") {
			//System.out.println("삭제 버튼 눌림");
			
			int row = jtableStock.getSelectedRow();
			
			if(row == -1) {
				JOptionPane.showMessageDialog(null, "데이터를 선택하지 않았습니다.", "경고!", JOptionPane.WARNING_MESSAGE);
			} else {
				String text = e.getActionCommand();
				String id = (String)jtableStock.getValueAt(row, 0);
				String name = (String)jtableStock.getValueAt(row, 1);
				String stock = (String)jtableStock.getValueAt(row, 2);
				String price = (String)jtableStock.getValueAt(row, 3);
				
				Item item = new Item();
				item.setId(Integer.parseInt(id));
				item.setItem_name(name);
				item.setItem_stock(Integer.parseInt(stock));
				item.setItem_price(Integer.parseInt(price));
				
				StockWindow window = new StockWindow(text, item);
			}
			
		}
		
	}
	
	
	private void loadDB() {
		
		Vector<Item> list = ItemDAO.getInstance().getAllItems();
		
		// 기존의 model 데이터 삭제(화면)
		int rows = model.getRowCount();
		
		for(int i=rows-1; i>=0; i--) {
			model.removeRow(i);
		}
		//
		
		for(Item item: list) {
			String id = String.valueOf(item.getId());
			String name = item.getItem_name();
			String stock = String.valueOf(item.getItem_stock());
			String price = String.valueOf(item.getItem_price());
			
			Vector<String> in = new Vector<String>();
			in.add(id);
			in.add(name);
			in.add(stock);
			in.add(price);
			
			model.addRow(in);
		}
		
	}
	
	
	
}
