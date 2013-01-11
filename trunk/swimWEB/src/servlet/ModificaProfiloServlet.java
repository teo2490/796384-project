package servlet;

import java.io.IOException;

import javax.naming.NamingException;
import javax.rmi.PortableRemoteObject;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import swim.entitybeans.UtenteRegistrato;
import swim.sessionbeans.ManagerUtenteRegistratoRemote;
import swim.sessionbeans.SwimBeanException;
import swim.util.ContextUtil;

/**
 * Servlet implementation class ModificaProfiloServlet
 */
public class ModificaProfiloServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ModificaProfiloServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		UtenteRegistrato u = (UtenteRegistrato) request.getSession()
				.getAttribute("utente");
		try {
			Object obj = ContextUtil.getInitialContext().lookup(
					"ManagerUtenteRegistrato/remote");
			ManagerUtenteRegistratoRemote manager = (ManagerUtenteRegistratoRemote) PortableRemoteObject
					.narrow(obj, ManagerUtenteRegistratoRemote.class);

			String oldpsw = request.getParameter("oldpsw");
			String newpsw = request.getParameter("newpsw");

			if (!newpsw.equals("")) {
				manager.cambioPsw(u.getEmail(), newpsw, oldpsw);
			}
			String url = request.getParameter("url");
			manager.cambioImm(u, url);

			request.setAttribute("messaggio", "Dati modificati correttamente!");
			RequestDispatcher disp = request
					.getRequestDispatcher("GestProfilo.jsp");
			disp.forward(request, response);
		} catch (NamingException e) {
			e.printStackTrace();
		} catch (SwimBeanException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}