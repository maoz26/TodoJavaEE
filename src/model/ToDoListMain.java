package model;

/**
 * main class for tests
 * Created by maoztamir on 31/12/2016.
 */
public class ToDoListMain {

	public static void main(String[] args) {
//		BasicConfigurator.configure();
//		try {
//			ToDoListItem item = new ToDoListItem("title","description",1);
//			item.setDescription("updating the description");
//			System.out.print(item);
//			System.out.println(HibernateToDoListDAO.getInstance().addToDoListItem(new ToDoListItem("title","description",1)));
//			ToDoListItem item2 = new ToDoListItem("title2","description2",2);
//			System.out.println(HibernateToDoListDAO.getInstance().addToDoListItem(item2));
//			System.out.println(HibernateToDoListDAO.getInstance().updateToDoListItem(new ToDoListItem(1,"new title","mission id 1 is updated",2)));
//			HibernateToDoListDAO.getInstance().getAllToDoListItem(2);
//			System.out.println(HibernateToDoListDAO.getInstance().deleteToDoListItem(item2));
//		} catch (ToDoListsPlatformException e){
//			e.printStackTrace();
//		}

		try{
//			AuthenticationHandlerUtilitiesScala.passEncryption("ddd");
//			User user = new User("mark","p",222,"bbb");
//			HibernateToDoListDAO.getInstance().signUp(user);
//			System.out.println(user.getUserLastName());
			User me = (HibernateToDoListDAO.getInstance().login(222,"bbb"));
			System.out.print(me);
		} catch(AuthenticationHandlerException e){
			e.printStackTrace();
		}
//		try {
//			HibernateToDoListDAO.getInstance().getAllUsers();
//		} catch (AuthenticationHandlerException e1) {
//			e1.printStackTrace();
//		}
	}
}
