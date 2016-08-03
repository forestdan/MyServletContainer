import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SessionServlet extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("SessionServlet -- service");
		resp.setContentType("text/html");
	    PrintWriter out = resp.getWriter();
	    out.println("<html>");
	    out.println("<head><title>SessionServlet</title></head>");
	    out.println("<body>");
	    String value = req.getParameter("value");
	    HttpSession session = req.getSession(true);
	    out.println("<br>the previous value is " + 
	      (String) session.getAttribute("value"));
	    out.println("<br>the current value is " + value);
	    session.setAttribute("value", value);
	    out.println("<br><hr>");
	    out.println("<form>");
	    out.println("New Value: <input name=value>");
	    out.println("<input type=submit>");
	    out.println("</form>");
	    out.println("</body>");
	    out.println("</html>");
	}
}
