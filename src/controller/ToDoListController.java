package controller;

import listeners.SessionCounterListener;
import model.*;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Collection;
import java.util.Hashtable;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Servlet implementation class ToDoListController
 */
@WebServlet("/ToDoListController/*")
public class ToDoListController extends HttpServlet {

	private static final long serialVersionUID = 1L;
	public final static Logger logger = Logger.getLogger(ToDoListController.class.getName());
	private HttpSession session ;
	private static boolean isLogin = false;
	private Collection<ToDoListItem> items;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ToDoListController() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//create new session if not exist 
		this.session = request.getSession(true);
		//user objet ref 
		User currentUser = null;
		//checking which url address the request was equipped with
		StringBuffer sb = request.getRequestURL();
		String url = sb.toString();
		try{
			currentUser = (User)this.session.getAttribute("user");
			if (currentUser == null && isLogin == false){
				//check if user id logged in
				logger.info("Redirect to login");
				logout(request, response);
			}else {
				//if current user is not null , user is logged
				userActionPageSelector(request,response,url);
			}
		}catch (NullPointerException e){
			logout(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		User currentUser = null;
		//checking which url address the request was equipped with
		StringBuffer sb = request.getRequestURL();
		String url = sb.toString();
		try{
			//login requset
			if ((userAuthentication(request, response) == true) && (isLogin == false)){
				isLogin = true;
				logger.info("user has login");
				currentUser = (User)this.session.getAttribute("user");
				if (currentUser.getUserId()== 0){
					doAction(request,response,ControllerConst.ADMIN_MENU);
				}else{
				doAction(request,response,ControllerConst.USER_MENU);
				}
			}else if (isLogin == true){
				// if user login go to user menu
				userActionPageSelector(request, response , url);
			}
		}catch (Exception e){
			e.printStackTrace();
			logout(request, response);
		}
	}

	/**
	 * this method run only if user is login
	 * it select action by url
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void userActionPageSelector(HttpServletRequest request, HttpServletResponse response , String url ) throws ServletException, IOException {
		logger.info("Get items is active");
		//user object ref 
		User currentUser = null;
		try{
			//get all items
			currentUser = (User)this.session.getAttribute("user");
			if(url.endsWith(ControllerConst.USER_MENU)){
				doAction(request,response,ControllerConst.USER_MENU);
			//get all user items
			}else if (url.endsWith(ControllerConst.USER_TASKS)){
				getItems(request, response, currentUser.getUserId());
				doAction(request,response,ControllerConst.USER_TASKS);
			//add new task
			}else if (url.endsWith(ControllerConst.ADD_TASK)){
				doAction(request,response,ControllerConst.ADD_TASK);
				logger.info(ControllerConst.ADD_TASK);
				addItem(request,response,currentUser.getUserId());
			//delete task
			}else if (url.endsWith(ControllerConst.DELETE_TASK)){
				doAction(request,response,ControllerConst.DELETE_TASK);
				logger.info(ControllerConst.DELETE_TASK);
				deleteItem(request, response, currentUser.getUserId());
			//edit task
			}else if (url.endsWith(ControllerConst.EDIT_TASK)){
				doAction(request,response,ControllerConst.EDIT_TASK);
				logger.info(ControllerConst.EDIT_TASK);
				editItem(request, response, currentUser.getUserId());
			//log out
			}else if (url.endsWith(ControllerConst.LOG_OUT)){
				logout(request,response);
			//about
			}else if (url.endsWith(ControllerConst.ABOUT)){
				doAction(request,response,ControllerConst.ABOUT);
			}else if (url.endsWith(ControllerConst.SESSION)){
				SessionCounterListener counterListener = new SessionCounterListener();
				request.setAttribute("sessions", SessionCounterListener.getActivesessions());
				doAction(request,response,ControllerConst.SESSION);
			}else if (url.endsWith(ControllerConst.USERS_LIST)){
				getUsers(request, response);
				doAction(request,response,ControllerConst.USERS_LIST);
			}else{
				doAction(request,response,ControllerConst.ERROR);
			}
		}catch(Exception e){
			logout(request, response);
		}
	}
	
	/**
	 * this method get user list from DB
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void getUsers(HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
		try {
			Collection<User> UsersList = HibernateToDoListDAO.getInstance().getAllUsers();
			Map<Integer , User> userList = new Hashtable<Integer,User>();
			int i = 1;
			for (User user : UsersList) {
				userList.put(i++, user);
			}
			this.session.setAttribute("users",userList);
		}catch (AuthenticationHandlerException e) {
			e.printStackTrace();
		}
	}

	/**
	 * this method get all todo list from DB
	 * @param request
	 * @param response
	 * @param userID
	 * @throws ServletException
	 * @throws IOException
	 */
	private void getItems(HttpServletRequest request, HttpServletResponse response ,int userID ) throws ServletException, IOException {
		try {
			items = HibernateToDoListDAO.getInstance().getAllToDoListItem(userID);
			Map<Integer , ToDoListItem> userTasks = new Hashtable<Integer,ToDoListItem>();
			int i = 1;
			for (ToDoListItem toDoListItem : items) {
				userTasks.put(i++, toDoListItem);
			}this.session.setAttribute("items",userTasks);
		} catch (ToDoListsPlatformException e) {
			e.printStackTrace();
		}
	}

	/**
	 * this method create new to list item and add it to the DB
	 * @param request
	 * @param response
	 * @param userID
	 * @throws ServletException
	 * @throws IOException
	 */
	private void addItem(HttpServletRequest request, HttpServletResponse response ,int userID ) throws ServletException, IOException {
		logger.info("Add item");
		String title = (String) request.getParameter("title");
		String description = (String) request.getParameter("description");
		response.getWriter().println(title + " "+description );
		if(title != null  && description != null ){
			try {
				HibernateToDoListDAO.getInstance().addToDoListItem(new ToDoListItem(title ,description,userID ));
				upDateOnDataChange(userID); //update session on changes
			} catch (ToDoListsPlatformException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * this method delete exist item by item id
	 * @param request
	 * @param response
	 * @param userID
	 * @throws ServletException
	 * @throws IOException
	 */
	private void deleteItem(HttpServletRequest request, HttpServletResponse response ,int userID ) throws ServletException, IOException {
		String id = (String) request.getParameter("id");	
		if(id != null ){
			try {
				int itemID =Integer.parseInt(id);
				Map<Integer , ToDoListItem> userTasks = (Map<Integer , ToDoListItem>)session.getAttribute("items");
				for(Map.Entry<Integer , ToDoListItem> userTask : userTasks.entrySet()){
					if (userTask.getKey() == itemID){
						HibernateToDoListDAO.getInstance().deleteToDoListItem(userTask.getValue());//delete item
						upDateOnDataChange(userID); //update session on changes	
					}
				}
			} catch (ToDoListsPlatformException e) {
				e.printStackTrace();
			}catch (NumberFormatException e){
			}
		}
	}

	/**
	 * this method edit item by item id 
	 * @param request
	 * @param response
	 * @param userID
	 * @throws ServletException
	 * @throws IOException
	 */
	private void editItem(HttpServletRequest request, HttpServletResponse response ,int userID ) throws ServletException, IOException {
		String id = (String) request.getParameter("id");	
		String title = (String) request.getParameter("title");
		String description = (String) request.getParameter("description");
		String status =(String) request.getParameter("status");
		boolean isChanged = false; // in case of data change flag is to and then update DB
		ToDoListItem itemToUpdate = null;
		if(id != null && title != null && description != null  && status != null){
			try {
				int itemID =Integer.parseInt(id);
				Map<Integer , ToDoListItem> userTasks = (Map<Integer , ToDoListItem>)session.getAttribute("items");
				for(Map.Entry<Integer , ToDoListItem> userTask : userTasks.entrySet()){
					if (userTask.getKey() == itemID){
						itemToUpdate = userTask.getValue();
						if (!(itemToUpdate.getTitle().equals(title))&& !(title.equals(""))){
							// if title is changed
							itemToUpdate.setTitle(title);
							isChanged = true;
						}
						if (!(itemToUpdate.getDescription().equals(description)) && !description.equals("")){
							// if description is changed
							itemToUpdate.setDescription(description);
							isChanged = true;
						}
						if (!(itemToUpdate.getStatus().equals(description)) && !status.equals("")){
							// if status is changed
							itemToUpdate.setStatus(status);
							isChanged = true;
						}
						//save if item changed
						if (isChanged == true){
							HibernateToDoListDAO.getInstance().updateToDoListItem(itemToUpdate);
							upDateOnDataChange(userID); //update session on changes
						}
					}
				}
			} catch (ToDoListsPlatformException e) {
				e.printStackTrace();
			} catch (NumberFormatException e){
			}
		}
	}

	/**
	 * this method logout current user
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void logout(HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
		session.invalidate();
		isLogin = false;
		doAction(request, response, ControllerConst.LOG_OUT);
	}

	/**
	 * this method verify if user exist in registered on DB 
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	private boolean userAuthentication(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		User user = null;
		this.session = request.getSession();
		String password =(String) request.getParameter("password");
		String id =(String) request.getParameter("id");
		if(password != null && id !=null){
			password = password.trim();
			id = id.trim();
			try {
				user = HibernateToDoListDAO.getInstance().login(Integer.parseInt(id), password);
				this.session.setAttribute("user", user);
				return true;
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				logout(request, response);
			} catch (AuthenticationHandlerException e) {
				// TODO Auto-generated catch block
				logout(request, response);
			}
			logger.info("user id" + id + " try to login");
		}
		return false;
	}

	/**
	 * when submitted form in post
	 * obtain the end of the url and redirect to the page
	 * @param request
	 * @param response
	 * @param action
	 * @throws ServletException
	 * @throws IOException
	 */
	private void doAction(HttpServletRequest request, HttpServletResponse response , String action) throws ServletException, IOException {
		RequestDispatcher dispatcher = null;
		dispatcher = getServletContext().getRequestDispatcher("/"+action+".jsp");	
		dispatcher.forward(request, response);
	}

	/**
	 * update on data change
	 * @param userId
	 */
	private void upDateOnDataChange(int userId){
		this.session.removeAttribute("items");
		try {
			this.items = HibernateToDoListDAO.getInstance().getAllToDoListItem(userId);
		} catch (ToDoListsPlatformException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.session.setAttribute("items", this.items);
	}

}