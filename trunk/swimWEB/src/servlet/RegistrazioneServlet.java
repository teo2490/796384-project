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
			
			String email = request.getParameter("mail");
			String password = request.getParameter("psw");
			String nome = request.getParameter("nome");
			String cognome = request.getParameter("cognome");
			
			boolean exist = false;
			if(manager.esisteMail(email))	exist = true;
			
			if(exist == false){
				manager.aggiungiUtente(email,password,nome,cognome);
			}
			
			RequestDispatcher disp;
			if(exist == true) {
				request.setAttribute("messaggio", "Errore: Registrazione.");
			} else {
				request.setAttribute("messaggio", "Registrazione completata! Ora puoi effettuare il login.");
			}
			disp = request.getRequestDispatcher("Home.jsp");
			disp.forward(request, response);
		} catch (NamingException e) {
			e.printStackTrace();
		}

	}

}
