package servlet;

import java.io.IOException;
import java.io.Serializable;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import swim.entitybeans.*;

/**
 * Servlet implementation class LoginServlet
 */
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	public static final String
		ATT_USER = "user",
		ATT_ADMIN = "admin",
		
		PAR_LOGIN = "login",
		PAR_LOGOUT = "logout";

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest( request, response );
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest( request, response );
	}

	private void processRequest(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String nextPage = "access/login.jsp";
		try {
			AccessRemote accessRemote;
			/*try {
				accessRemote = ServletUtils.getAccessRemote( request );
			} catch ( LessonWebException e ) {
				accessRemote = ServletUtils.getNewAccessRemote( request );
			}*/

			String par;
			if ( ( par = request.getParameter( PAR_LOGIN ) ) != null ) {
				accessRemote = ServletUtils.getNewAccessRemote( request );
				
				String m = request.getParameter( "matricola" );
				String p = request.getParameter( "password" );
				Serializable ret = accessRemote.login( m, p );
				
				request.getSession().removeAttribute( ATT_USER_TRAINER );
				request.getSession().removeAttribute( ATT_USER_TRAINEE );
				request.getSession().removeAttribute( ATT_USER_ASSISTANT );
				if ( ret instanceof TrainerObject ) {
					nextPage = "TrainerServlet";
					//request.setAttribute( "command", "show" );
					//throw new LessonBeanException( accessRemote.getCourses().toString() );
					request.getSession().setAttribute( ATT_USER_TRAINER, (TrainerObject)ret );
				} else if ( ret instanceof TraineeObject ) {
					nextPage = "TraineeServlet";
					request.getSession().setAttribute( ATT_USER_TRAINEE, (TraineeObject)ret );
				} else if ( ret instanceof AssistantObject ) {
					nextPage = "AssistantServlet";
					request.getSession().setAttribute( ATT_USER_ASSISTANT, (AssistantObject)ret );
				} else {
					throw new LessonWebException( "Tipo di utente non valido" );
				}
				
			} else if ( ( par = request.getParameter( PAR_LOGOUT ) ) != null ) {
				try {
					accessRemote = ServletUtils.getAccessRemote( request );
					
					accessRemote.logout();
					request.getSession().removeAttribute( ATT_USER_TRAINER );
					request.getSession().removeAttribute( ATT_USER_TRAINEE );
					request.getSession().removeAttribute( ATT_USER_ASSISTANT );
					request.getSession().removeAttribute( ServletUtils.ACCESS_REMOTE_ATTRIBUTE );
					request.getSession().invalidate();
				} catch ( Exception e ) {
				} finally {
					nextPage = "access/logout.jsp";
				}
			}
		/*} catch ( LessonBeanException e ) {
			request.setAttribute( "error", e.getMessage() );*/
		} catch ( Exception e ) {
			nextPage = "include/error.jsp";
			request.setAttribute( "error", e.getMessage() );
			e.printStackTrace();
		}
		request.getRequestDispatcher( nextPage ).forward( request, response );
	}
}
