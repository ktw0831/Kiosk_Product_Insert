package a_010_java_after2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.Scanner;

class KioskInsert {
    private int pdt_id;        
    private String pdt_id_name;            
    private int pdt_unit_price;            
    private int pdt_order_method;            
    public int cnt;



    
public int getPdt_id() {
		return pdt_id;
	}

	public void setPdt_id(int pdt_id) {
		this.pdt_id = pdt_id;
	}

	public String getPdt_id_name() {
		return pdt_id_name;
	}

	public void setPdt_id_name(String pdt_id_name) {
		this.pdt_id_name = pdt_id_name;
	}

	public int getPdt_unit_price() {
		return pdt_unit_price;
	}

	public void setPdt_unit_price(int pdt_unit_price) {
		this.pdt_unit_price = pdt_unit_price;
	}

	public int getPdt_order_method() {
		return pdt_order_method;
	}

	public void setPdt_order_method(int pdt_order_method) {
		this.pdt_order_method = pdt_order_method;
	}


	void printScore() {
        System.out.printf(" %3d %5d %5s %5d %5d \n", cnt, pdt_id,  pdt_id_name,pdt_unit_price, pdt_order_method  );
    }
}


public class Kiosk_Product_Insert {
    public static void main(String[] args) {
    	 Scanner input = new Scanner(System.in);
         
         System.out.println("상품 코드 입력은 몇 건 입니까(숫자입력) : ");
         int num = input.nextInt();
        
         KioskInsert stu[] = new KioskInsert[num];
        
         for(int i=0; i<stu.length; i++) {
            
             stu[i] = new KioskInsert();    // 매우 중요!!! 배열은 인덱스 모두에 객체생성 후 참조배열 연계
             
             String  buffer = input.nextLine();
             System.out.print("상품명을 입력하세요: ");
             stu[i].setPdt_id_name(input.nextLine());
             
             System.out.print("상품코드를 입력하세요: ");
             stu[i].setPdt_id (input.nextInt());
             System.out.print("상품 단가를 입력하세요: ");
             stu[i].setPdt_unit_price(input.nextInt());
             System.out.print("주문방법을 입력하세요: ");
             stu[i].setPdt_order_method(input.nextInt());
         }
         // 석차 부여, 같은 총점일 경우 같은 석차
//         for (int i=0; i<stu.length; i++) {
//             int rank=1;
//             for (int j=0; j<stu.length; j++) {
//                 if(stu[i].getSum() < stu[j].getSum()) rank++;
//             }
//             stu[i].setRank(rank);
//         }
//        
         System.out.println("==================출력==================");
         System.out.printf(" NO    상품코드 상품명 단가 주문방법  \n");
         System.out.println("=======================================");
         for (int i=0; i<stu.length; i++) {
             stu[i].cnt = i+1;
             stu[i].printScore();
         }
         // DB연결 후 입력된 자료 등록
             Connection conn = null;
             PreparedStatement pstmt = null;
             String sql;

             String url = "jdbc:oracle:thin:@localhost:1521:xe";
             String id = "system";
             String pw = "1234";
             try {
                 Class.forName("oracle.jdbc.OracleDriver");
                 System.out.println("클래스 로딩 성공");
                 conn = DriverManager.getConnection(url, id, pw);
                 System.out.println("DB 접속");
                
                 //setCharacterEncoding("UTF-8);
                 for(int i=0; i<stu.length; i++) {
                     sql = "insert into tbl_product_master values (?,?,?,?)";
                     pstmt = conn.prepareStatement(sql);
                    
                     //pstmt.setInt(1,Integer.parseInt(getParameter("custno")));
                     pstmt.setInt(1, stu[i].getPdt_id());
                     pstmt.setString(2, stu[i].getPdt_id_name());
                     pstmt.setInt(3, stu[i].getPdt_unit_price());
                     pstmt.setInt(4, stu[i].getPdt_order_method());
           
                    
                     pstmt.executeUpdate();
                 }
                 System.out.println("==================================================");
                 System.out.println("DB 입력작업 성공:"+stu.length+"건 입력");
                
                 pstmt.close();
                 conn.close();
             }catch(Exception e) {
                 e.printStackTrace();
             }
             
     }
 }