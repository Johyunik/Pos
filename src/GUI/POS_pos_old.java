package GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import DB.ItemDAO;

public class POS_pos_old extends JPanel implements ActionListener{
	
	ItemDAO dao = ItemDAO.getInstance();
	
	JButton btnDB;
	
	JLabel labelItemName;
	JComboBox comboBox;
	
	JLabel labelItemStock;
	JTextField txtStock;
	
	JLabel labelItemTotal;
	JTextField txtTotal;
	
	JButton btnAdd;
	JButton btnPay;
	JButton btnCancel;
	
	JTable jStock;
	
	DefaultTableModel tableModel; 
	DefaultComboBoxModel comboModel;
	
	int total = 0;
	
	public POS_pos_old() {
		
		setLayout(null);
		
		btnDB = new JButton("제품 불러오기");
		btnDB.setBounds(20, 20, 140, 40);
		
		labelItemName = new JLabel("상품");
		labelItemName.setBounds(20, 90, 100, 30);
		
		comboModel = new DefaultComboBoxModel();
		comboBox = new JComboBox(comboModel);
		comboBox.setBounds(70, 90, 200, 30);
		
		labelItemStock = new JLabel("수량");
		labelItemStock.setBounds(20, 140, 100, 30);
		
		txtStock = new JTextField(10);
		txtStock.setBounds(70, 140, 200, 30);
		
		labelItemTotal = new JLabel("총가격");
		labelItemTotal.setBounds(20, 250, 100, 40);
		
		txtTotal = new JTextField(10);
		txtTotal.setBounds(70, 250, 200, 40);
		txtTotal.setEditable(false);
		
		btnAdd = new JButton("추가");
		btnAdd.setBounds(170, 190, 100, 40);
		
		btnPay = new JButton("결재");
		btnPay.setBounds(300,250,100,40);
		
		btnCancel = new JButton("취소");
		btnCancel.setBounds(410, 250, 100, 40);
		
		tableModel = new DefaultTableModel();		
		tableModel.addColumn("상품명");
		tableModel.addColumn("구매량");
		tableModel.addColumn("단가");
		tableModel.addColumn("총액");
		JTable jStock = new JTable(tableModel);		
		jStock.setBounds(300, 20, 210, 210);
		jStock.setEnabled(false);
		
		add(btnDB);
		add(labelItemName);
		add(comboBox);
		add(labelItemStock);
		add(txtStock);
		add(labelItemTotal);
		add(txtTotal);
		add(btnAdd);
		add(btnPay);
		add(btnCancel);
		add(jStock);
		
		btnDB.addActionListener(this);
		btnAdd.addActionListener(this);
		btnPay.addActionListener(this);
		btnCancel.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
				
		String menu = e.getActionCommand();
		int stock = 0;
		String itemName = "";
		int price = 0;
		
		//[제품 불러오기] 버튼 클릭 시
		if(menu=="제품 불러오기") {
			comboBox.removeAllItems();			
			Vector<String> itemlist = new Vector<String>();
			itemlist = dao.getItems();
			Iterator it = itemlist.iterator();
			while(it.hasNext()) {
				comboBox.addItem(it.next());
			}
		}else if(menu=="추가") {
			stock = Integer.parseInt(txtStock.getText());
			itemName = (String)comboBox.getSelectedItem();
			try {
				price = Integer.parseInt(dao.getprice(itemName));
			} catch (NumberFormatException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			int sum = stock * price;
			total = total + sum;
			txtTotal.setText(String.valueOf(total));
			
			Vector<String> in = new Vector<String>();
			in.add(itemName);
			in.add(String.valueOf(stock));
			in.add(String.valueOf(price));
			in.add(String.valueOf(sum));
			tableModel.addRow(in);			
		}else if(menu=="결재") {
			int result = JOptionPane.showConfirmDialog(null, "결재하시겠습니까?");
			if(result==0) {
				int input = Integer.parseInt(JOptionPane.showInputDialog("총 금액은 " + txtTotal.getText() +"입니다"));
				if(input > Integer.parseInt(txtTotal.getText())) {
					JOptionPane.showMessageDialog(null, "지불하신 금액은 " + input+"이고 " + "\n상품의 합계는 " + txtTotal.getText()+"이며, \n거스름돈은 " + (input-Integer.parseInt(txtTotal.getText())) + "입니다");
					try {
						stockUpdate(tableModel);
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					clean();					
				}else {
					JOptionPane.showMessageDialog(null, "금액이 부족합니다.\n결재를 취소합니다");
				}
			}
		}else {
			JOptionPane.showMessageDialog(null, "주문을 취소하시겠습니까?");
			clean();
		}
	}
	
	public void clean() {
		int rows = tableModel.getRowCount();
		for(int i=rows-1; i>=0; i--)
			tableModel.removeRow(i);
		txtTotal.setText("");
		txtStock.setText("");
	}
	
	public void stockUpdate(DefaultTableModel model) throws SQLException {
		for(int i=0; i<model.getRowCount(); i++) {
			String name = (String)model.getValueAt(i, 0);
			String stock = (String)model.getValueAt(i, 1);
			String price = (String)model.getValueAt(i, 2);
			
			String total = dao.getstock(name);
			
			int tot = Integer.parseInt(total);
						
			System.out.println("name = " + name);
			System.out.println("total = " + tot);
			System.out.println("stock = " + stock);
			System.out.println("remain = " + (tot-Integer.parseInt(stock)));
			
			dao.updateStock(total, stock, name);			
		}		
	}
}
