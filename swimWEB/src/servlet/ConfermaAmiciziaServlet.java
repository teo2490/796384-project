package servlet;

import java.io.IOException;

import javax.naming.NamingException;
import javax.rmi.PortableRemoteObject;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import swim.sessionbeans.ManagerAiutoRemote;
import swim.sessionbeans.ManagerAmiciziaRemote;
import swim.util.ContextUtil;

/**
 * Servlet implementation class ConfermaAmiciziaServlet
 */
public class ConfermaAmiciziaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ConfermaAmiciziaServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
		try {
			Object obj = ContextUtil.getInitialContext().lookup("ManagerAmicizia/remote");
			ManagerAmiciziaRemote manager = (ManagerAmiciziaRemote) PortableRemoteObject.narrow(obj, ManagerAmiciziaRemote.class);
			String id = request.getParameter("id");
			
			manager.accettaAmicizia(id);
		} catch (NamingException e) {
			e.printStackTrace(); 
		}
		RequestDispatcher disp;
		request.setAttribute("messaggio", "Amiciza accettata!");
		disp = request.getRequestDispatcher("GestAmicizia.jsp");
		disp.forward(request, response);
	}


}
