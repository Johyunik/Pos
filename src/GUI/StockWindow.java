package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import DB.Item;
import DB.ItemDAO;

public class StockWindow extends JFrame implements ActionListener{
	
	private JLabel labelID = null;
	private JLabel labelName = null;
	private JLabel labelStock = null;
	private JLabel labelPrice = null;
	
	private JTextField txtID = null;
	private JTextField txtName = null;
	private JTextField txtStock = null;
	private JTextField txtPrice = null;
	
	private JButton btnAccept = null;
	
	private Item item = new Item();
	
	public StockWindow(String text) {
		
		labelID = new JLabel("ID");
		labelName = new JLabel("상품명");
		labelStock = new JLabel("재고량");
		labelPrice = new JLabel("가격");
		
		txtID = new JTextField(10);
		txtID.setEditable(false);
		txtName = new JTextField(10);
		txtStock = new JTextField(10);
		txtPrice = new JTextField(10);
		
		btnAccept = new JButton(text);
		btnAccept.setBackground(new Color(255,204,255));
		btnAccept.addActionListener(this);
		
		display();
		
		setSize(300, 300);
		setVisible(true);
		
	}
	
	public StockWindow(String text, Item item) {
		
		this.item = item;
		
		labelID = new JLabel("ID");
		labelName = new JLabel("상품명");
		labelStock = new JLabel("재고량");
		labelPrice = new JLabel("가격");
		
		txtID = new JTextField(10);
		txtID.setEditable(false);
		txtName = new JTextField(10);
		txtStock = new JTextField(10);
		txtPrice = new JTextField(10);
		
		btnAccept = new JButton(text);
		btnAccept.setBackground(new Color(255,204,255));
		btnAccept.addActionListener(this);
		
		display();
		
		setSize(300, 300);
		setVisible(true);
		
	}
	
	public void display() {
		
		Container ct = getContentPane();
		ct.setLayout(new BorderLayout());
		JPanel p = new JPanel();
		p.setLayout(new GridLayout(4, 2));
		
		if(item != null) {
			txtID.setText(String.valueOf(item.getId()));
			txtName.setText(item.getItem_name());
			txtStock.setText(String.valueOf(item.getItem_stock()));
			txtPrice.setText(String.valueOf(item.getItem_price()));
		}
		
		p.add(labelID);
		p.add(txtID);

		p.add(labelName);
		p.add(txtName);
		
		p.add(labelStock);
		p.add(txtStock);
		
		p.add(labelPrice);
		p.add(txtPrice);
		
		ct.add(p, BorderLayout.CENTER);
		ct.add(btnAccept, BorderLayout.SOUTH);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

		String id;
		String name;
		String stock;
		String price;
		
		if(e.getActionCommand() == "등록") {
			
			name = txtName.getText();
			stock = txtStock.getText();
			price = txtPrice.getText();
			
			Item item = new Item();
			item.setItem_name(name);
			item.setItem_stock(Integer.parseInt(stock));
			item.setItem_price(Integer.parseInt(price));
			
			boolean result = ItemDAO.getInstance().insertItem(item);
			
			if(result == true) {
				System.out.println("등록 성공");
				dispose();
			}
			
		}
		
		else if(e.getActionCommand() == "수정") {
			
			id = txtID.getText();
			name = txtName.getText();
			stock = txtStock.getText();
			price = txtPrice.getText();
			
			Item item = new Item();
			item.setId(Integer.parseInt(id));
			item.setItem_name(name);
			item.setItem_stock(Integer.parseInt(stock));
			item.setItem_price(Integer.parseInt(price));
			
			boolean result = ItemDAO.getInstance().updateitem(item);
			
			if(result) {
				System.out.println("수정 성공");
				JOptionPane.showMessageDialog(null, "데이터 수정 성공", "성공!", JOptionPane.INFORMATION_MESSAGE);
				dispose();
			} else {
				JOptionPane.showMessageDialog(null, "데이터 수정 실패", "실패!", JOptionPane.WARNING_MESSAGE);
				dispose();
			}
		}
		
		else if(e.getActionCommand() == "삭제") {
			
			id = txtID.getText();
			
			boolean result = ItemDAO.getInstance().deleteitem(Integer.parseInt(id));
			
			if(result) {
				System.out.println("삭제 성공");
				JOptionPane.showMessageDialog(null, "데이터 삭제 성공", "성공!", JOptionPane.INFORMATION_MESSAGE);
				dispose();
			} else {
				JOptionPane.showMessageDialog(null, "데이터 삭제 실패", "실패!", JOptionPane.WARNING_MESSAGE);
				dispose();
			}
		}
	}
	
}
