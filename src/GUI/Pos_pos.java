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
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import DB.Item;
import DB.ItemDAO;

public class Pos_pos extends JPanel implements ActionListener{
	
	//ItemDAO 객체 생성(dao) 및 로드
	ItemDAO dao= ItemDAO.getInstance();
	

	
	
	Item item = new Item();
	
	private JButton btnDB = null; //"제품 불러오기" 버튼 객체 선언(btnDB)	
	private JLabel lblItem = null; //"상품" 라벨 객체 선언(lblItem)
	private JComboBox<String> cmbBox = new JComboBox<String>(); //"상품리스트" 콤보박스 객체 선언(cmbBox)
	private JLabel lblStock = null; //"수량" 라벨 객체 선언(lblStock)
	private JTextField txtStock = new JTextField(); //"수량입력박스" 텍스트필드 객체 선언(txtStock)
	private JLabel lblTotal = null;	//"총가격" 라벨 객체 선언(lblTotal)
	private JTextField txtTotal = new JTextField(); //"총가격 출력박스" 텍스트필드 객체 선언(txtTotal)
	private JButton btnAdd = null; //"추가" 버튼 객체 선언(btnAdd)	
	private JButton btnPay = null; //"결재" 버튼 객체 선언(btnPay)	
	private JButton btnCancel = null; //"취소" 버튼 객체 선언(btnCancel)	
	
	private JTable jTableItem = new JTable(); //"테이블출력" JTable 객체 선언(jTableItem)
	
	DefaultTableModel tableModel = new DefaultTableModel(); //JTable에 출력할 Model 객체 선언(tableModel)
	
	private DefaultTableModel comboModel = null; //JComboBox에 출력할 Model 객체 선언(comboModel)
	
	int total = 0; //총가격 저장할 정수형 멤버변수 선언(total)
	
	
	public Pos_pos() {
		
		String name[] = {"ID", "상품명", "재고량", "가격"};
		tableModel = new DefaultTableModel(name, 0); // 0 을 적으면 알아서 갯수만큼 붙음
		jTableItem = new JTable(tableModel); // model
		JScrollPane scroll = new JScrollPane(jTableItem);
		
		setLayout(null);
		
		//각 컴포넌트 객체 생성 및 화면 배치/크기 조정
		btnDB = new JButton("제품 불러오기");
		btnDB.setBounds(20, 20, 140, 40);
		
		lblItem = new JLabel("상품");
		lblItem.setBounds(20, 90, 100, 30);
		
		lblStock = new JLabel("수량");
		lblStock.setBounds(20, 140, 100, 30);
		
		lblTotal = new JLabel("총가격");
		lblTotal.setBounds(20, 250, 100, 40);
		
		btnAdd = new JButton("추가");
		btnAdd.setBounds(170, 190, 100, 40);
		
		btnPay = new JButton("결제");
		btnPay.setBounds(300, 250, 100, 40);
		
		btnCancel = new JButton("취소");
		btnCancel.setBounds(410, 250, 100, 40);
		
		scroll.setBounds(300, 20, 210, 210);
		
		cmbBox.setBounds(70, 90, 200, 30);
		txtStock.setBounds(70, 140, 200, 30);
		txtTotal.setBounds(70, 250, 200, 40);
		
		
		add(btnDB);
		add(lblItem);
		add(lblStock);
		add(lblTotal);
		add(btnAdd);
		add(btnPay);
		add(btnCancel);
		add(cmbBox);
		add(txtStock);
		add(txtTotal);
		add(scroll);
		
		//이벤트 처리를 위한 리스너 등록
		btnDB.addActionListener(this);
		btnAdd.addActionListener(this);
		btnPay.addActionListener(this);
		btnCancel.addActionListener(this);
		cmbBox.addActionListener(this);
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		//이벤트 객체로부터 텍스트 가져오기
		//제품명, 재고량, 가격 저장할 지역변수 선언 및 초기화
		
		//[제품 불러오기] 버튼 클릭 시
		if(e.getActionCommand() == "제품 불러오기") {
			//comboBox의 모든 데이터 요소 삭제(removeAllItems());
			cmbBox.removeAllItems();
			//DB로부터 상품명 전체 검색 및 Vector에 저장
			//Vector에 저장한 상품명을 comboBox에 추가
			Vector<String> list = dao.getItems();
			for(int i=0; i<list.size(); i++) {
				cmbBox.addItem(list.get(i));
			}
			
		}
		
		//[추가] 버튼 클릭 시
		else if(e.getActionCommand() == "추가") {
			// comboBox에서 선택한 상품명과 텍스트필드에 입력한 수량 저장
			String selectedItem = (String) cmbBox.getSelectedItem();
			int selectedItemStock = Integer.parseInt(txtStock.getText());
			// DB로부터 사용자가 선택한 상품명의 단가 불러오기
			int itemPrice = Integer.parseInt(dao.getprice(selectedItem));
			// 사용자가 선택한 상품의 구매가격(단가*수량)과 누적 총액 연산하기
			int sumPrice = Integer.parseInt(dao.getprice(selectedItem)) * selectedItemStock;
			// 상품명, 구매수량, 구매가격, 누적총액을 Vector에 저장
			Vector<String> item = new Vector<String>();
			
			
			item.add(selectedItem);
			item.add(String.valueOf(selectedItemStock));
			item.add(String.valueOf(itemPrice));
			item.add(String.valueOf(sumPrice));
			total += sumPrice;
			
			txtTotal.setText(String.valueOf(total));
			// Vector 객체를 tableModel에 추가
			
			tableModel.addRow(item);
		    
		    
		}//[결재] 버튼 클릭 시
		else if(e.getActionCommand() == "결제") {
			// "결재하시겠습니까?"라는 다이얼로그 창 출력(JOptionPane.showConfirmDialog())
			int result = JOptionPane.showConfirmDialog(null, "결제하시겠습니까?");
			// "YES"를 누르면 "총금액은 ~입니다"를 출력한 후 사용자로부터 숫자 입력받기(JOptionPane.showInputDialog())
			if(result == 0) {
				int input = Integer.parseInt(JOptionPane.showInputDialog("총 금액은 " + txtTotal.getText() +"원 입니다"));
				if(input > Integer.parseInt(txtTotal.getText())) {
					JOptionPane.showMessageDialog(null, "지불하신 금액은 " + input +"원 이고 " + "\n상품의 합계는 " + txtTotal.getText() +"원 이며, \n거스름돈은 " + (input-Integer.parseInt(txtTotal.getText())) + "원 입니다");
					stockUpdate(tableModel);
					clean();					
				}else {
					JOptionPane.showMessageDialog(null, "금액이 부족합니다.\n결제를 취소합니다");
				}
			}
		}
			// 사용자 입력금액이 총금액보다 크면 "지불금액,거스름돈"을 출력한 후 DB 업데이트(stockUpdate), 모든 컴포넌트 내의 데이터 초기화(clean())
			
			// 그렇지 않으면 "금액이 적습니다" Dialog 창 출력
			
		//[취소] 버튼 클릭 시
		else if(e.getActionCommand() == "취소"){
			// "주문을 취소하시겠습니까?" Dialog 창 출력
			JOptionPane.showMessageDialog(null, "주문을 취소하시겠습니까?", "Message", JOptionPane.INFORMATION_MESSAGE);
			clean();
		}
	}
	
	// JTable, 수량과 총가격의 JTextField 내 데이터 초기화
	public void clean() {
		int rows = tableModel.getRowCount();
		for(int i=rows-1; i>=0; i--)
			tableModel.removeRow(i);
		txtTotal.setText("");
		txtStock.setText("");
	}
	
	// JTable에 출력된 모든 데이터의 상품명, 재고량, 가격을 이용하여 DB 데이터 업데이트 
	public void stockUpdate(DefaultTableModel model) {
		for(int i=0; i<model.getRowCount(); i++) {
			String name = (String)model.getValueAt(i, 0);
			String stock = (String)model.getValueAt(i, 1);
			String price = (String)model.getValueAt(i, 2);
			
			String total = dao.getstock(name);
			
			int tot = Integer.parseInt(total);
			
			dao.updateStock(total, stock, name);			
		}
		}		
	}

