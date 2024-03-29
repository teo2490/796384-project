package servlet;

import java.io.IOException;
import javax.naming.NamingException;
import javax.rmi.PortableRemoteObject;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import swim.sessionbeans.ManagerAmministratoreRemote;
import swim.sessionbeans.ManagerUtenteRegistratoRemote;
import swim.util.ContextUtil;
import swim.entitybeans.Amministratore;
import swim.entitybeans.UtenteRegistrato;


/**
 * Servlet implementation class LoginServlet
 */
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 5909797442154638761L;

	/**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}
	
	private int getRequestId(HttpServletRequest request) {
		try {
			return Integer.parseInt(request.getParameter("id"));
		} catch(NumberFormatException e) {
			return -1;
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
		try {
			Object obj = ContextUtil.getInitialContext().lookup("ManagerUtenteRegistrato/remote");
			ManagerUtenteRegistratoRemote manager = (ManagerUtenteRegistratoRemote) PortableRemoteObject.narrow(obj, ManagerUtenteRegistratoRemote.class);
			String email = request.getParameter("id");
			String password = request.getParameter("password");
			
			RequestDispatcher disp;
			disp = request.getRequestDispatcher("Home.jsp");
			
			if(email.equals("admin1@swim.it") || email.equals("admin2@swim.it")){
				Object objadm = ContextUtil.getInitialContext().lookup("ManagerAmministratore/remote");
				ManagerAmministratoreRemote manageradm = (ManagerAmministratoreRemote) PortableRemoteObject.narrow(objadm, ManagerAmministratoreRemote.class);
				String emailadm = request.getParameter("id");
				String passwordadm = request.getParameter("password");
				Amministratore a = manageradm.verificaLogin(emailadm, passwordadm);
				
				if(a == null) {
					request.setAttribute("messaggio", "Errore: codice utente o password errati.");
					disp = request.getRequestDispatcher("Home.jsp");
				} else {
					request.getSession().setAttribute("utente", a);
					disp = request.getRequestDispatcher("HomeAdmin.jsp");
				}
				disp.forward(request, response);
				
			}else{
			
			UtenteRegistrato u = manager.verificaLogin(email, password);
			//UtenteRegistrato u = manager.prova();
			
			if(u == null) {
				request.setAttribute("messaggio", "Errore: codice utente o password errati.");
				disp = request.getRequestDispatcher("Home.jsp");
			} else {
				request.getSession().setAttribute("utente", u);
				disp = request.getRequestDispatcher("HomeUtente.jsp");
			}
			disp.forward(request, response);}
		} catch (NamingException e) {
			e.printStackTrace();
		}
		
	}

}
