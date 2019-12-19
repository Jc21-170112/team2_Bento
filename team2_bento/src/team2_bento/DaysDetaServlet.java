package jp.ac.jc21.jk3a34;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class DaysDetaServlet
 */
@WebServlet("/DaysDeta")
public class DaysDetaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DaysDetaServlet() {
        super();
    }
        
        protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        	
    		final String driverName = "oracle.jdbc.driver.OracleDriver";
    		final String url = "jdbc:oracle:thin:@192.168.54.192:1521/orcl";
    		final String id = "team2";
    		final String pass = "Oracle_11g";
    		
    		try {
    			
    		String day1 = request.getParameter("day");
    		System.out.println(day1);
    		
    		//Date day = Date.valueOf(day1);
    		Class.forName(driverName);
    		
    		Connection connection=DriverManager.getConnection(url,id,pass);
			PreparedStatement st =
    		connection.prepareStatement("select bento_name,qty,price*qty as test"
    				+ " from emp_order inner join emp on emp_order.emp_id = emp.emp_id "
    				+ "inner join bento on emp_order.bento_id = bento.bento_id "
    				+ "where trunc(order_date)= ?");
			st.setString(1, day1);
    		ResultSet rs1 = st.executeQuery();
    		
    		ArrayList<String[]> list =new ArrayList<String[]>();
    		
			while(rs1.next() != false) {
    			
    			String[] s =new String[3];
    			
    			s[0] = rs1.getString("bento_name");
    			s[1] = rs1.getString("qty");
    			s[2] = rs1.getString("test");
    			
    			list.add(s);
    			
    		}
			
    		
    		request.setAttribute("list",list);
    		
    
    		PreparedStatement st1 =
    	    connection.prepareStatement("select sum(qty) as result "
    	    		+ "from emp_order inner join emp on emp_order.emp_id = emp.emp_id "
    	    		+ "inner join bento on emp_order.bento_id = bento.bento_id where trunc(order_date)= ?");
    		st1.setString(1, day1);
    		ResultSet rs2 = st1.executeQuery();
    		
    		String s1 = "";
    		while (rs2.next()) {
    			s1 = rs2.getString("result");
    		};
    		
    		request.setAttribute("s1",s1);
    		

 
    		PreparedStatement st2 =
    	    		connection.prepareStatement("select sum(qty*price) as result from emp_order inner join emp on emp_order.emp_id = emp.emp_id inner join bento on emp_order.bento_id = bento.bento_id where trunc(order_date)= ? ");
    		st2.setString(1, day1);
    		
    		ResultSet rs3 = st2.executeQuery();
    		
    		String s2 = "";
    		while (rs3.next()) {
    			s2 = rs3.getString("result");
    		}
    		
    		request.setAttribute("s2",s2);
    		
    		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/DaysDeta.jsp");
    		rd.forward(request, response);
    	
    		}catch (ClassNotFoundException e) {
    			e.printStackTrace();
    		}catch (SQLException e) {
    			e.printStackTrace();
    		}
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	
	
	}


