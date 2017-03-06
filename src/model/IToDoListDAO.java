/**
 * ToDoList Data Access Object
 * Created by maoztamir on 31/12/2016.
 */

package model;

import java.util.Collection;

public interface IToDoListDAO {

    /**
     * Add ToDoList item to the database
     * @param Item
     * @throws ToDoListsPlatformException
     */
    public Boolean addToDoListItem(ToDoListItem Item) throws ToDoListsPlatformException;

    /**
     * Delete ToDoList item from the database
     * @return
     * @throws ToDoListsPlatformException
     */
    public Boolean deleteToDoListItem(ToDoListItem item) throws ToDoListsPlatformException;

    /**
     * Get ToDoList items collection
     * @return
     * @throws ToDoListsPlatformException
     */
    public Collection<ToDoListItem> getAllToDoListItem(int userId) throws ToDoListsPlatformException;

    /**
     * Update ToDoList item to the database
     * @return true if success or false if failed
     * @throws ToDoListsPlatformException
     */
    public Boolean updateToDoListItem(ToDoListItem item) throws ToDoListsPlatformException;

    /**
     * gets user object check if exist in DB
     * if not exist create new one
     * the pass will be encrypted in DB with md5 algorithm
     * @param user
     * @throws ToDoListsPlatformException
     */
    public void signUp(User user) throws  AuthenticationHandlerException;

    /**
     * login to DB
     * @param userId - contain user info
     * @throws AuthenticationHandlerException
     */
    public User login(int userId, String password) throws AuthenticationHandlerException;

    /**
     * get all user list from DB
     * @return user list
     * @throws AuthenticationHandlerException
     */
    public Collection<User> getAllUsers() throws AuthenticationHandlerException;

}
