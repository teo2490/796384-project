package servlet;

import java.io.IOException;
import java.util.Set;

import javax.naming.NamingException;
import javax.rmi.PortableRemoteObject;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import swim.entitybeans.UtenteRegistrato;
import swim.sessionbeans.ManagerAiutoRemote;
import swim.sessionbeans.ManagerUtenteRegistratoRemote;
import swim.sessionbeans.SwimBeanException;
import swim.util.ContextUtil;

/**
 * Servlet implementation class RicercaProfilo
 */
public class RicercaProfiloServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RicercaProfiloServlet() {
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
        UtenteRegistrato u = null;
		try {
			Object obj = ContextUtil.getInitialContext().lookup("ManagerUtenteRegistrato/remote");
			ManagerUtenteRegistratoRemote manager = (ManagerUtenteRegistratoRemote) PortableRemoteObject.narrow(obj, ManagerUtenteRegistratoRemote.class);
			
			String email = request.getParameter("email");
			u = manager.ricercaUtente(email);
			request.getSession().setAttribute("ut",u);
			RequestDispatcher disp = request.getRequestDispatcher("ShowProfilo.jsp");
			disp.forward(request, response);
		} catch (NamingException e) {
			e.printStackTrace();
		}

	}

}
