package jp.ac.jc21.jk3a34;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class UserConfirmServlet
 */
@WebServlet("/UserConfirm")
public class UserConfirmServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserConfirmServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		// FORM�̓��͂��󂯎��I�������f�[�^��DB�ɓ��͂��鏤�i���A���[�UID�A���ʁA��������
				String orderItem = request.getParameter("orderItem");
				String orderID = request.getParameter("orderID");
				String orderQty = request.getParameter("orderQty");
				
				final String driverName = "oracle.jdbc.driver.OracleDriver";
				final String url = "jdbc:oracle:thin:@192.168.54.192:1521/orcl";
				final String id = "team2";   //
				final String pass = "Oracle_11g";  //
				
				try {
				Class.forName(driverName);
				Connection connection=DriverManager.getConnection(url,id,pass);
				PreparedStatement st = 
						connection.prepareStatement(
							"insert into emp_order(order_ID,bento_ID,user_ID,qty,order_date,order_flag) "+"values(?,?,?,getdate(),0)"
							);
				
				} catch(ClassNotFoundException e) {
					System.out.println("ClassNotFoundException");
					e.printStackTrace(System.out);
				}catch(SQLException e) {
					System.out.println("SQLException");
					e.printStackTrace(System.out);
				}
				
				
				// JSP�Ŏg���l��ۑ�����
				request.setAttribute("orderItem",orderItem);
				request.setAttribute("orderID",orderID);
				request.setAttribute("orderQty",orderQty);
		RequestDispatcher rd = request.getRequestDispatcher("/UserConfirm.jsp");
		rd.forward(request, response);
	}

}
