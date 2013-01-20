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
import swim.sessionbeans.ManagerAmministratoreRemote;
import swim.sessionbeans.ManagerUtenteRegistratoRemote;
import swim.util.ContextUtil;

/**
 * Servlet implementation class ConfermaAbilitaServlet
 */
public class ConfermaAbilitaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ConfermaAbilitaServlet() {
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
        RequestDispatcher disp;
		try {
			Object obj = ContextUtil.getInitialContext().lookup("ManagerAmministratore/remote");
			ManagerAmministratoreRemote manager = (ManagerAmministratoreRemote) PortableRemoteObject.narrow(obj, ManagerAmministratoreRemote.class);
			String id = request.getParameter("id");
			String nome = request.getParameter("nome");
			String desc = request.getParameter("desc");
			String azione = request.getParameter("azione");
			if(azione.equals("conferma")){
			Amministratore a = (Amministratore) request.getSession().getAttribute("utente");
			manager.aggiungiAbilita(id, nome, desc, a.getEmail());
			request.setAttribute("messaggio", "Abilità aggiunta!");
			}
			if(azione.equals("elimina")){
				manager.eliminaAbilita(id);
				request.setAttribute("messaggio", "Richiesta eliminata!");
			}
		} catch (NamingException e) {
			e.printStackTrace(); 
		}
		disp = request.getRequestDispatcher("GestAbilitaAdmin.jsp");
		disp.forward(request, response);
	}

}
