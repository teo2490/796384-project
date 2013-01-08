package servlet;

import java.io.IOException;

import javax.naming.NamingException;
import javax.rmi.PortableRemoteObject;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import swim.sessionbeans.ManagerAbilitaRemote;
import swim.sessionbeans.ManagerUtenteRegistratoRemote;
import swim.util.ContextUtil;

/**
 * Servlet implementation class RichiestaNuovaAbilitaServlet
 */
public class RichiestaNuovaAbilitaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RichiestaNuovaAbilitaServlet() {
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
			Object obj = ContextUtil.getInitialContext().lookup("ManagerAbilita/remote");
			ManagerAbilitaRemote manager = (ManagerAbilitaRemote) PortableRemoteObject.narrow(obj, ManagerAbilitaRemote.class);
			
			String nome = request.getParameter("nome");
			String desc = request.getParameter("desc");
			
			manager.invioRichiestaNuovaAbilita(nome, desc);
			RequestDispatcher disp;
			request.setAttribute("messaggio", "Richiesta inviata!");
			disp = request.getRequestDispatcher("GestAbilita.jsp");
			disp.forward(request, response);
			
		} catch (NamingException e) {
			e.printStackTrace();
		}

	}

}
