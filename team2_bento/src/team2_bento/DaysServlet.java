package jp.ac.jc21.jk3a34;
import java.io.IOException;
import java.sql.Connection;
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
 * Servlet implementation class DaysServlet
 */
@WebServlet("/Days")
public class DaysServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DaysServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
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
				connection.prepareStatement(//ì˙ïtÅAã‡äz
						"select to_char(order_date,'YY/MM/DD') as hiduke,sum(price*qty) as kingaku from emp_order inner join emp on emp_order.emp_id = emp.emp_id inner join bento on emp_order.bento_id = bento.bento_id where extract(month from order_date)=? group by to_char(order_date,'YY/MM/DD') order by hiduke asc");
		ResultSet rs = st.executeQuery();
		ArrayList<String[]>list= new ArrayList<String[]>();
		int kingaku=0;
		while(rs.next() != false) {
			String[] s = new String[2];
			s[0]=rs.getString("hiduke");
			s[1]=rs.getString("kingaku");
			kingaku=kingaku+Integer.parseInt(s[1]);
			list.add(s);
			}
		
		request.setAttribute("list", list);
		request.setAttribute("kingaku", kingaku);
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/Days.jsp");
		rd.forward(request, response);
		}catch (ClassNotFoundException e) {
			e.printStackTrace();
		}catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
}