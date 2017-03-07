package model;

import java.util.Collection;

/**
 * ToDoList Data Access Object interface
 * Created by maoztamir on 31/12/2016.
 */
public interface IToDoListDAO {

    /**
     * Add ToDoList item to the database
     * @param Item add todo list item
     * @throws ToDoListsPlatformException custom exception
     */
    public Boolean addToDoListItem(ToDoListItem Item) throws ToDoListsPlatformException;

    /**
     * @param item Delete todo list item
     * @throws ToDoListsPlatformException custom exception
     */
    public Boolean deleteToDoListItem(ToDoListItem item) throws ToDoListsPlatformException;

    /**
     * @param userId Get todo List items collection
     * @throws ToDoListsPlatformException custom exception
     */
    public Collection<ToDoListItem> getAllToDoListItem(int userId) throws ToDoListsPlatformException;


    /**
     * @param item
     * @return true if success or false if failed
     * @throws ToDoListsPlatformException custom exception
     */
    public Boolean updateToDoListItem(ToDoListItem item) throws ToDoListsPlatformException;

    /**
     * @param user gets user object check if exist in DB
     * if not exist create new one
     * the pass will be encrypted in DB with md5 algorithm
     * @throws ToDoListsPlatformException custom exception
     */
    public void signUp(User user) throws  AuthenticationHandlerException;

    /**
     * @param userId -login to DB contain user info
     * @throws AuthenticationHandlerException custom exception
     */
    public User login(int userId, String password) throws AuthenticationHandlerException;

    /**
     * get all user list from DB
     * @return user list
     * @throws AuthenticationHandlerException custom exception
     */
    public Collection<User> getAllUsers() throws AuthenticationHandlerException;

}
