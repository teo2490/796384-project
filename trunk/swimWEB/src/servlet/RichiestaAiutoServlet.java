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
import swim.sessionbeans.ManagerAiutoRemote;
import swim.sessionbeans.ManagerUtenteRegistratoRemote;
import swim.util.ContextUtil;

/**
 * Servlet implementation class RichiestaAiutoServlet
 */
public class RichiestaAiutoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RichiestaAiutoServlet() {
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
        //---Impedisce di fare azioni senza essere loggato
        UtenteRegistrato u = (UtenteRegistrato) request.getSession().getAttribute("utente");
        if(u.equals(null)){
        	return;
        }
        //---
		UtenteRegistrato uRichiedente = (UtenteRegistrato) request.getSession().getAttribute("utente");
		try {
			Object obj = ContextUtil.getInitialContext().lookup("ManagerAiuto/remote");
			ManagerAiutoRemote manager = (ManagerAiutoRemote) PortableRemoteObject.narrow(obj, ManagerAiutoRemote.class);
			Object obju = ContextUtil.getInitialContext().lookup("ManagerUtenteRegistrato/remote");
			ManagerUtenteRegistratoRemote manageru = (ManagerUtenteRegistratoRemote) PortableRemoteObject.narrow(obju, ManagerUtenteRegistratoRemote.class);
			
			String abilita = request.getParameter("abilita");
			String email = request.getParameter("email");
			if(manageru.ricercaUtente(email)==null){
				request.setAttribute("messaggio", "L'utente non è iscritto al sistema!");
			}
			else if(u.getEmail().equals(email)){
				request.setAttribute("messaggio", "Non puoi chiedere aiuto a te stesso!");
			}else{
			Abilita a = manager.ricercaAbilita(abilita);
			UtenteRegistrato uRichiesto = manageru.ricercaUtente(email);
			//Controllo che uRichiesto abbia l'abilità specificata
			if(manageru.utentePossiedeAbilita(uRichiesto, a)){
				manager.invioRichiestaAiuto(a.getNome(), uRichiedente, uRichiesto);
				request.setAttribute("messaggio", "Richiesta inviata correttamente!");
			}
			else{
				request.setAttribute("messaggio", "L'utente non possiede l'abilità selezionata!");
			}
			}
			RequestDispatcher disp = request
					.getRequestDispatcher("GestAiuto.jsp");
			disp.forward(request, response);
		} catch (NamingException e) {
			e.printStackTrace();
		}

	}

}