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
import swim.sessionbeans.ManagerAiutoRemote;
import swim.sessionbeans.ManagerAmiciziaRemote;
import swim.sessionbeans.ManagerUtenteRegistratoRemote;
import swim.util.ContextUtil;

/**
 * Servlet implementation class RichiestaAmiciziaServlet
 */
public class RichiestaAmiciziaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RichiestaAmiciziaServlet() {
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
			Object obj = ContextUtil.getInitialContext().lookup("ManagerAmicizia/remote");
			ManagerAmiciziaRemote manager = (ManagerAmiciziaRemote) PortableRemoteObject.narrow(obj, ManagerAmiciziaRemote.class);
			Object obju = ContextUtil.getInitialContext().lookup("ManagerUtenteRegistrato/remote");
			ManagerUtenteRegistratoRemote manageru = (ManagerUtenteRegistratoRemote) PortableRemoteObject.narrow(obju, ManagerUtenteRegistratoRemote.class);
			
			String email = request.getParameter("email");
			
			UtenteRegistrato amico = manageru.ricercaUtente(email);
			//Controllo che non ci sia già un'amicizia tra i due utenti
			if(!manager.esisteAmicizia(u, amico)){
				manager.invioRichiestaAmicizia(u, amico);
				request.setAttribute("messaggio", "Richiesta di Amicizia inviata correttamente!");
			}
			else{
				request.setAttribute("messaggio", "Sei già amico di questo utente!");
			}
			
			RequestDispatcher disp = request
					.getRequestDispatcher("GestAmicizia.jsp");
			disp.forward(request, response);
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	}

