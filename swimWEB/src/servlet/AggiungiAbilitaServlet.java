package servlet;

import java.io.IOException;

import javax.naming.NamingException;
import javax.rmi.PortableRemoteObject;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import swim.entitybeans.Abilita;
import swim.entitybeans.UtenteRegistrato;
import swim.sessionbeans.ManagerAbilitaRemote;
import swim.sessionbeans.ManagerUtenteRegistratoRemote;
import swim.sessionbeans.SwimBeanException;
import swim.util.ContextUtil;

/**
 * Servlet implementation class AggiungiAbilitaServlet
 */
public class AggiungiAbilitaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AggiungiAbilitaServlet() {
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
		UtenteRegistrato u = (UtenteRegistrato) request.getSession().getAttribute("utente");
		try {
			Object obj = ContextUtil.getInitialContext().lookup("ManagerAbilita/remote");
			ManagerAbilitaRemote manager = (ManagerAbilitaRemote) PortableRemoteObject.narrow(obj, ManagerAbilitaRemote.class);
			
			String abilita = request.getParameter("abilita");
			System.out.println(abilita);
			
			Abilita a = manager.ricercaAbilita(abilita);
			manager.aggiungiAbilita(u, a);

			request.setAttribute("messaggio", "Abilita aggiunta correttamente!");
			RequestDispatcher disp = request
					.getRequestDispatcher("GestAbilita.jsp");
			disp.forward(request, response);
		} catch (NamingException e) {
			e.printStackTrace();
		}

	}

}