package servlet;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import javax.naming.NamingException;
import javax.rmi.PortableRemoteObject;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import swim.entitybeans.Abilita;
import swim.entitybeans.UtenteRegistrato;
import swim.sessionbeans.ManagerAiutoRemote;
import swim.sessionbeans.SwimBeanException;
import swim.util.ContextUtil;

/**
 * Servlet implementation class RicercaAiutoAmiciServlet
 */
public class RicercaAiutoAmiciServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RicercaAiutoAmiciServlet() {
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
        List<UtenteRegistrato> possAiutanti = null;
        //---Impedisce di fare azioni senza essere loggato
        UtenteRegistrato u = (UtenteRegistrato) request.getSession().getAttribute("utente");
        if(u.equals(null)){
        	return;
        }
        //---
		try {
			Object obj = ContextUtil.getInitialContext().lookup("ManagerAiuto/remote");
			ManagerAiutoRemote manager = (ManagerAiutoRemote) PortableRemoteObject.narrow(obj, ManagerAiutoRemote.class);
			
			String abilita = request.getParameter("helpKey");
			Abilita a = manager.ricercaAbilita(abilita);
			try {
				possAiutanti = manager.ricercaTraAmici(a, u);
			} catch (SwimBeanException e) {
				e.printStackTrace();
			}
			//Invio l'elenco dei possibili amici aiutanti alla pagina di scelta
			request.setAttribute("possAiutanti",possAiutanti);
			request.getRequestDispatcher("ShowAiutanti.jsp").forward(request, response);
			
			RequestDispatcher disp = null;

			disp.forward(request, response);
		} catch (NamingException e) {
			e.printStackTrace();
		}

	}

}