package servlet;

import java.io.IOException;

import javax.naming.NamingException;
import javax.rmi.PortableRemoteObject;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import swim.entitybeans.Amministratore;
import swim.sessionbeans.ManagerAiutoRemote;
import swim.sessionbeans.ManagerAmministratoreRemote;
import swim.util.ContextUtil;

/**
 * Servlet implementation class ConfermaAiutoServlet
 */
public class ConfermaAiutoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ConfermaAiutoServlet() {
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
			Object obj = ContextUtil.getInitialContext().lookup("ManagerAiuto/remote");
			ManagerAiutoRemote manager = (ManagerAiutoRemote) PortableRemoteObject.narrow(obj, ManagerAiutoRemote.class);
			String id = request.getParameter("id");
			
			manager.confermaRichiesta(id);
		} catch (NamingException e) {
			e.printStackTrace(); 
		}
		RequestDispatcher disp;
		request.setAttribute("messaggio", "Aiuto confermato!");
		disp = request.getRequestDispatcher("GestAiuto.jsp");
		disp.forward(request, response);
	}


}
