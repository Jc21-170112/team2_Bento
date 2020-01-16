package jp.ac.jc21.jk3a34;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sun.xml.internal.bind.v2.schemagen.xmlschema.List;

/**
 * Servlet implementation class CheckListServlet
 */
@WebServlet("/CheckList")
public class CheckListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CheckListServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    ArrayList<String[]> list;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		final String driverName = "oracle.jdbc.driver.OracleDriver";
		final String url = "jdbc:oracle:thin:@192.168.54.192:1521/orcl";
		final String id = "team2";   //
		final String pass = "Oracle_11g";  //
		
		try {
		Class.forName(driverName);
		Connection connection=DriverManager.getConnection(url,id,pass);
		PreparedStatement st = 
				connection.prepareStatement(
					"select EMP_NAME,EMP_ORDER.EMP_ID,BENTO_NAME,sum(QTY) as qty,order_flag,ORDER_ID from (emp_order left outer join emp on(emp_order.emp_ID=emp.emp_ID)) left outer join bento on(emp_order.bento_ID=bento.bento_ID) where trunc(order_date)=trunc(sysdate) group by emp_name,emp_order.emp_ID,bento_name,order_flag,order_id order by emp_order.emp_ID asc"
					);
		ResultSet rs = st.executeQuery();
		list= new ArrayList<String[]>();
		
		while(rs.next() != false) {
			String[] s = new String[6];
			
			s[0]=rs.getString("EMP_NAME");
			s[1]=rs.getString("EMP_ID");
			s[2]=rs.getString("BENTO_NAME");
			s[3]=rs.getString("QTY");
			s[4]=rs.getString("ORDER_FLAG");
			s[5]=rs.getString("ORDER_ID");
			list.add(s);
			}
		request.setAttribute("list", list);
		RequestDispatcher rd = request.getRequestDispatcher("/CheckList.jsp");
		rd.forward(request, response);
		}catch(ClassNotFoundException e) {
			System.out.println("ClassNotFoundException");
			e.printStackTrace(System.out);
		}catch(SQLException e) {
			System.out.println("SQLException");
			e.printStackTrace(System.out);
		}
		
		for(String[] s1 :list) {
			if(request.getParameterValues(s1[5])==null) {
				try {
					Connection conn = DriverManager.getConnection(url,id,pass);
					String sql = "update emp_order set order_flag=0 where order_id=?";
					PreparedStatement pstmt=conn.prepareStatement(sql);
					pstmt.setString(1, s1[5]);
					int num = pstmt.executeUpdate();
				}catch(SQLException e) {
					System.out.println("SQLException:" + e.getMessage());
				}
			}else{
				try {
					Connection conn = DriverManager.getConnection(url,id,pass);
					String sql = "update emp_order set order_flag=1 where order_id=?";
					PreparedStatement pstmt = conn.prepareStatement(sql);
					pstmt.setString(1, s1[5]);
					int num = pstmt.executeUpdate();
				}catch(SQLException e) {
					System.out.println("SQLException:" + e.getMessage());
				}
			}
		}
	}

}
