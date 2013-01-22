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
import swim.sessionbeans.ManagerUtenteRegistratoRemote;
import swim.sessionbeans.SwimBeanException;
import swim.util.ContextUtil;

/**
 * Servlet implementation class RicercaAiutoServlet
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
        List<String> possAiutanti = null;
		try {
			Object obj = ContextUtil.getInitialContext().lookup("ManagerAiuto/remote");
			ManagerAiutoRemote manager = (ManagerAiutoRemote) PortableRemoteObject.narrow(obj, ManagerAiutoRemote.class);
			UtenteRegistrato u = (UtenteRegistrato) request.getSession().getAttribute("utente");
			
			String abilita = request.getParameter("helpKey");
			Abilita a = manager.ricercaAbilita(abilita);
			try {
				possAiutanti = manager.ricercaTraAmici(a, u);
			} catch (SwimBeanException e) {
				e.printStackTrace();
				possAiutanti = null;
				request.getSession().setAttribute("possAiutanti",possAiutanti);
				
			}
			//System.out.println(possAiutanti.get(0).getEmail());
			//
//			for(UtenteRegistrato u: possAiutanti){
//				
//			}
			// 
//Invio l'elenco dei possibili aiutanti alla pagina di scelta
			if(possAiutanti == null){
				request.getSession().setAttribute("messaggio","Nessun utente possiede l'abilità scelta!");
			}
			else{
			request.getSession().setAttribute("possAiutanti",possAiutanti);
			}
			//request.getRequestDispatcher("ShowAiutanti.jsp").forward(request, response);
			//RequestDispatcher disp = null;
			RequestDispatcher disp = request.getRequestDispatcher("ShowAiutanti.jsp");

			disp.forward(request, response);
		} catch (NamingException e) {
			e.printStackTrace();
		}

	}

}
