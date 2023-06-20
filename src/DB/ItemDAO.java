package DB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

public class ItemDAO {
	
	private ItemDAO() {		
		
	}
	
	public static ItemDAO instance = new ItemDAO();
	
	public static ItemDAO getInstance() {
		return instance;
	}
	
	// 1.전체 레코드 검색 기능(getAllItem())
	public Vector<Item> getAllItems(){
		Vector<Item> list = new Vector<Item>();
		
		try {
			//1.DB 연결
			Connection conn = DBconnect.connect();
			//2.Select SQL - Statement
			Statement stmt = conn.createStatement();
			//3.SQL 객체 쿼리 할당
			String sql = "select * from item";
			//4.쿼리 실행 - ResultSet 객체 참조
			ResultSet rs = stmt.executeQuery(sql);
			
			while(rs.next()) {
				Item item = new Item();
				item.setId(rs.getInt("id"));
				item.setItem_name(rs.getString("item_name"));
				item.setItem_stock(rs.getInt("item_stock"));
				item.setItem_price(rs.getInt("item_price"));
				list.add(item);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBconnect.close();
		}
		//ResultSet순회하면서 Item 객체 생성
		
		//Vector에 Item 객체 추가
		
		return list;
	}
	
	// 2. 상품목록 검색
	
	public Vector<String> getItems(){
		Vector<String> namelist = new Vector<String>();
		
		try {
			//1.DB 연결
			Connection conn = DBconnect.connect();
			//2.Select SQL - Statement
			Statement stmt = conn.createStatement();
			//3.SQL 객체 쿼리 할당
			String sql = "select item_name from item";
			//4.쿼리 실행 - ResultSet 객체 참조
			ResultSet rs = stmt.executeQuery(sql);
			
			while(rs.next()) {
				String name = rs.getString("item_name");
				namelist.add(name);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBconnect.close();
		}
		//ResultSet순회하면서 Item 객체 생성
		
		//Vector에 Item 객체 추가
		
		return namelist;
	}
	
	
	//3. 상품 재고량 검색 -->
	
	public String getstock(String itemName){
		String s = null;
		
		try {
			//1.DB 연결
			Connection conn = DBconnect.connect();
			
			//2.SQL 객체 쿼리 할당
			String sql = "select item_stock from item where item_name=?";

			//3.SQL 객체 생성 - PreaparedStatement
			PreparedStatement stmt = conn.prepareStatement(sql);
			
			//SQL 완성
			stmt.setString(1, itemName);
			
			//4.쿼리 실행 - ResultSet 객체 참조
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next()) {
				s = rs.getString("item_stock");
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBconnect.close();
		}
		//ResultSet순회하면서 Item 객체 생성
		
		//Vector에 Item 객체 추가
		
		return s;
	}
	
	//4. 상품 가격 검색
	public String getprice(String itemName){
		
		String price = null;
		
		try {
			//1.DB 연결
			Connection conn = DBconnect.connect();
			
			//2.SQL 객체 쿼리 할당
			String sql = "select item_price from item where item_name=?";
			
			//3.SQL 객체 생성 - PreaparedStatement
			PreparedStatement stmt = conn.prepareStatement(sql);
			
			//SQL 완성
			stmt.setString(1, itemName);
			
			//4.쿼리 실행 - ResultSet 객체 참조
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next()) {
				price = rs.getString("item_price");
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBconnect.close();
		}
		//ResultSet순회하면서 Item 객체 생성
		
		//Vector에 Item 객체 추가
		
		return price;
	}
	
	// 5. 상품 재고량 업데이트 updateStock(전체 재고량, 판매 수량, 지정 상품)
	
	public void updateStock(String totalStock, String sales, String itemName){
		
		try {
			//1.DB 연결
			Connection conn = DBconnect.connect();
			
			//2.SQL 객체 쿼리 할당
			String sql = "update item set item_stock=?-? where item_name=?";

			//3.SQL 객체 생성 - PreaparedStatement
			PreparedStatement stmt = conn.prepareStatement(sql);
			
			//SQL 완성
			stmt.setString(1, totalStock);
			stmt.setString(2, sales);
			stmt.setString(3, itemName);
			
			//SQL 실행
			stmt.execute();
			
			/*
			//4.쿼리 실행 - ResultSet 객체 참조
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next()) {
				s = rs.getString("item_stock");
			}
			*/
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBconnect.close();
		}
		//ResultSet순회하면서 Item 객체 생성
		
		//Vector에 Item 객체 추가
		
	}
	
	// 6.상품 추가
	
	public boolean insertItem(Item item){
		
		boolean result = false;
		
		try {
			//1.DB 연결
			Connection conn = DBconnect.connect();
			
			//2.SQL 객체 쿼리 할당
			String sql = "insert into item(item_name, item_stock, item_price) values(?, ?, ?)";
			
			//3.SQL 객체 생성 - PreaparedStatement
			PreparedStatement stmt = conn.prepareStatement(sql);
			

			
			//SQL 완성
			stmt.setString(1, item.getItem_name());
			stmt.setInt(2, item.getItem_stock());
			stmt.setInt(3, item.getItem_price());
			int r = stmt.executeUpdate();
			
			if(r > 0)
				result = true;
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBconnect.close();
		}
		
		return result;
	}
	
	// 7. 레코드 수정
	
	public boolean updateitem(Item item){
		
		boolean result = false;
		
		try {
			//1.DB 연결
			Connection conn = DBconnect.connect();
			
			//2.SQL 객체 쿼리 할당
			String sql = "update item set item_name=?, item_stock=?, item_price=? where (id=?)";
			
			//3.SQL 객체 생성 - PreaparedStatement
			PreparedStatement stmt = conn.prepareStatement(sql);
			
			
			//SQL 완성
			stmt.setString(1, item.getItem_name());
			stmt.setInt(2, item.getItem_stock());
			stmt.setInt(3, item.getItem_price());
			stmt.setInt(4, item.getId());
			
			int r = stmt.executeUpdate();
			
			if(r > 0)
				result = true;
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBconnect.close();
		}
		
		return result;
	}
	
	// 상품 삭제
	
	public boolean deleteitem(int id){
		
		boolean result = false;
		
		try {
			//1.DB 연결
			Connection conn = DBconnect.connect();
			
			//2.SQL 객체 쿼리 할당
			String sql = "delete from item where id=?";
			
			//3.SQL 객체 생성 - PreaparedStatement
			PreparedStatement stmt = conn.prepareStatement(sql);
		
			//SQL 완성
			stmt.setInt(1, id);
			
			int r = stmt.executeUpdate();
			
			if(r > 0)
				result = true;
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBconnect.close();
		}
		
		return result;
	}
}
