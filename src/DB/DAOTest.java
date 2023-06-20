package DB;
import java.util.Iterator;
import java.util.Vector;

public class DAOTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		ItemDAO dao= ItemDAO.getInstance();
		
		Vector<Item> list = new Vector<Item>();
		
		list = dao.getAllItems();
		
		
		System.out.println("1. 전체상품 출력");
		for(int i=0; i<list.size(); i++) {
			Item item = new Item();
			item = list.get(i);
			System.out.println(item.getId() + " : " + item.getItem_name() + ", " + 
								item.getItem_stock() + ", " +item.getItem_price());
		}
	
		System.out.println();
		System.out.println("2. 상품목록 출력");
		
		Vector<String> namelist = new Vector<String>();
		namelist = dao.getItems();		
		
		Iterator it = namelist.iterator();
		while(it.hasNext()) {
			String name = (String) it.next();
			System.out.println(name);
		}
		
		System.out.println();
		System.out.println("3. 지정 상품의 재고 출력");
		
		String stock = new String();
		stock = dao.getstock("aaa");		
		
		/*
		it = stock.iterator();
		while(it.hasNext()) {
			String stock = (String) it.next();
			System.out.println(stock);
		}
		*/
		
		System.out.println("aaa의 상품의 재고 : " + stock);
		
		System.out.println();
		System.out.println("4. 지정 상품의 가격 출력");
		
		/*
		Vector<String> pricelist = new Vector<String>();
		pricelist = dao.getprice();		
		
		it = pricelist.iterator();
		while(it.hasNext()) {
			String price = (String) it.next();
			System.out.println(price);
		}
		*/
		
		String price = new String();
		price = dao.getprice("ccc");
		
		System.out.println("ccc 상품의 가격 : " + price);
		
		System.out.println();
		System.out.println("5. aaa상품의 판매 후 재고 수량");
		
		dao.updateStock(stock, "10", "aaa");
		System.out.println("aaa 재고 : " + stock);
		
		// 상품 추가
		
//		Item item = new Item();
//		item.setItem_name("코카콜라");
//		item.setItem_stock(100);
//		item.setItem_price(1000);
		
//		boolean r = false;
//		r = dao.insertItem(item);
		
//		if(r == true) 
//			System.out.println("상품 추가 성공");
//		else 
//			System.out.println("상품 추가 실패");
		
		
		
	}

}
