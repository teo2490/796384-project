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
import swim.util.ContextUtil;
// INCOMPLETA!! ------------------------------------------------------
/**
 * Servlet implementation class RegistrazioneServlet
 */
public class RegistrazioneServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegistrazioneServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
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
			
			String email = request.getParameter("email");
			String password = request.getParameter("password");
			String nome = request.getParameter("nome");
			String cognome = request.getParameter("cognome");
			
			boolean exist = true;
			if(manager.esisteMail(email)==false)	exist = false;
			
			if(exist = false){
				manager.aggiungiUtente(email,password,nome,cognome);
			}
			
			RequestDispatcher disp;
			if(u == null) {
				request.setAttribute("messaggio", "Errore: codice utente o password errati.");
				disp = request.getRequestDispatcher("Home.jsp");
			} else {
				request.getSession().setAttribute("utente", u);
				
				if(u.getNome() != null) {
					disp = request.getRequestDispatcher("HomeUtente.jsp");
				} 
				else {
					disp = request.getRequestDispatcher("HomeAdmin.jsp");
				}
			}
			disp.forward(request, response);
		} catch (NamingException e) {
			e.printStackTrace();
		}

	}

}
