package servlet;

import java.io.IOException;
import java.util.List;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.rmi.PortableRemoteObject;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import swim.entitybeans.Abilita;
import swim.sessionbeans.ManagerAmministratoreRemote;
import swim.test.ManagerInizializzazioneDatabaseRemote;
import swim.util.ContextUtil;

/**
 * Servlet implementation class PannelloControllo
 */
public class PannelloControllo extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PannelloControllo() {
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
		InitialContext ctx;
		try {
			ctx = ContextUtil.getInitialContext();
			ManagerInizializzazioneDatabaseRemote db = (ManagerInizializzazioneDatabaseRemote) PortableRemoteObject.narrow(ctx.lookup("ManagerInizializzazioneDatabase/remote"), ManagerInizializzazioneDatabaseRemote.class);
			ManagerAmministratoreRemote manAdmin = (ManagerAmministratoreRemote) PortableRemoteObject.narrow(ctx.lookup("ManagerAmministratore/remote"), ManagerAmministratoreRemote.class);
			db.pulisciAmicizia();
			db.pulisciAiuto();
			db.pulisciABILITA_UTENTE();
			db.pulisciUtenteRegistrato();
			db.pulisciAbilita();
			db.pulisciAmministratore();
			db.creaAdminPredefiniti();
			db.creaAbilitaPredefinite();
			
			List<Abilita> elenco = (List<Abilita>) manAdmin.getElencoRichieste();
			for(int i=0; i<elenco.size(); i++){
				manAdmin.aggiungiAbilita(Integer.toString(elenco.get(i).getId()), elenco.get(i).getNome(), elenco.get(i).getDescrizione(), "admin1@swim.it");
			}
		} catch (NamingException e) {
			e.printStackTrace();
		}
		RequestDispatcher disp;
		request.setAttribute("messaggio", "Database creato correttamente!");
		disp = request.getRequestDispatcher("Home.jsp");
		disp.forward(request, response);
	}

}
