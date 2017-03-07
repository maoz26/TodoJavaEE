package model;

import com.sun.istack.internal.logging.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.jetbrains.annotations.Nullable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * Todo list Dao Object
 * Created by maoztamir on 31/12/2016.
 */
public class HibernateToDoListDAO implements IToDoListDAO {
    //logger instance
    private static Logger logger = Logger.getLogger(HibernateToDoListDAO.class);
    //Session instance
    private Session session;
    //Singleton instance static var
    private static HibernateToDoListDAO instance;

    /**
     * private constructor
     */
    private HibernateToDoListDAO() {}

    /**
     * singleton implementation
     */
    public static HibernateToDoListDAO getInstance() {
        if (instance == null) {
            instance = new HibernateToDoListDAO();
        }
        return instance;
    }

    /* (non-Javadoc)
     * @see model.IToDoListDAO#addToDoListItem(model.ToDoListItem)
     */
    @Override
    public Boolean addToDoListItem(ToDoListItem item) throws ToDoListsPlatformException {
        this.session = SessionFactoryAccess.getInstance().getSessionFactory().openSession();
        try{
            this.session.beginTransaction();
            this.session.save(item);
            this.session.getTransaction().commit();
            logger.info("Added task number- "+item.getUserId()+", title- "+item.getTitle()+", description- "+item.getDescription()+", status- "+item.getStatus());
        }catch (HibernateException e){
            if (this.session.getTransaction() != null) {
                this.session.getTransaction().rollback();
            }
            logger.info("Add item failed"+item.getId()+" "+item.getTitle());
            throw new ToDoListsPlatformException("Problem add task", e);
        }finally {
            try {
                this.session.close();
            }catch (HibernateException e){
                e.printStackTrace();
            }
        }
        return true;
    }

    /* (non-Javadoc)
     * @see model.IToDoListDAO#deleteToDoListItem()
     */
    @Override
    public Boolean deleteToDoListItem(ToDoListItem item) throws ToDoListsPlatformException {
        try{
            this.session = SessionFactoryAccess.getInstance().getSessionFactory().openSession();
            this.session.beginTransaction();
            this.session.delete(item);
            this.session.getTransaction().commit();
            logger.info("Deleted task number - "+item.getId()+", title- "+item.getTitle());
        }catch (HibernateException e){
            if (this.session.getTransaction() != null) {
                this.session.getTransaction().rollback();
            }
            logger.info("Add item failed"+item.getId()+" "+item.getTitle());
            throw new ToDoListsPlatformException("Problem add item", e);
        }finally {
            try {
                this.session.close();
            }catch (HibernateException e){
                e.printStackTrace();
            }
        }
        return true;
    }

    /* (non-Javadoc)
     * @see model.IToDoListDAO#getAllToDoListItem()
     */
    @Override
    public Collection<ToDoListItem> getAllToDoListItem(int userId) throws ToDoListsPlatformException {
        ArrayList<ToDoListItem> arrayList = new ArrayList<>();
        try{
            logger.info("Get items");
            this.session = SessionFactoryAccess.getInstance().getSessionFactory().openSession();
            List<ToDoListItem> itemList = session.createQuery("FROM ToDoListItem WHERE userId="+userId).list();
            Iterator i = itemList.iterator();
            while(i.hasNext())
            {
                ToDoListItem item = (ToDoListItem) i.next();
                logger.info(itemList.toString()+"\n");
                arrayList.add(item);
            }
        }catch (HibernateException e){
            throw new ToDoListsPlatformException("Problem get all items", e);
        }finally {
            try {
                this.session.close();
            }catch (HibernateException e){
                e.printStackTrace();
            }
        }
        return arrayList;
    }

    /* (non-Javadoc)
     * @see model.IToDoListDAO#updateToDoListItem()
     */
    @Override
    public Boolean updateToDoListItem(ToDoListItem item) throws ToDoListsPlatformException {
        try{
            this.session = SessionFactoryAccess.getInstance().getSessionFactory().openSession();
            this.session.beginTransaction();
            this.session.update(item);
            this.session.getTransaction().commit();
            logger.info("update item "+item.getId()+" "+item.getTitle());
        }catch (HibernateException e){
            if (this.session.getTransaction() != null) {
                this.session.getTransaction().rollback();
            }
            logger.info("update item failed"+item.getId()+" "+item.getTitle());
            throw new ToDoListsPlatformException("Problem update item", e);
        }finally {
            try {
                this.session.close();
            }catch (HibernateException e){
                e.printStackTrace();
            }
        }
        return true;
    }

    /**
     * @param user gets user object verify if exist in DB
     *  if not exist create new one
     * the pass will be encrypted in DB with md5 algorithm
     * @throws ToDoListsPlatformException display custom exception
     */
    public void signUp(User user) throws  AuthenticationHandlerException{
        logger.info("Sign up request");
        //check if user is in DB
        if (checkIfUserExist(user.getUserId()) == null){
            try{
                this.session = SessionFactoryAccess.getInstance().getSessionFactory().openSession();
                /// set encrypted password to user
                user.setPassword(AuthenticationHandlerUtilitiesScala.passEncryption(user.getPassword()));
                this.session.beginTransaction();
                this.session.save(user);
                this.session.getTransaction().commit();
                System.out.println("user successfully sign up");
            }catch(HibernateException e){
                //roll back in case of problem
                if (this.session.getTransaction() != null) {
                    this.session.getTransaction().rollback();
                }
                throw new  AuthenticationHandlerException("Problem add new user", e);
            } finally {
                try {
                    this.session.close();
                }catch (HibernateException e){
                    e.printStackTrace();
                }
            }
        }else {
            System.out.println("user is already in DB");
            logger.info("error - try to create exist user");
        }
    }

    /**
     * @param userId this method check if user exist in DB
     * @return - user from DB display custom exception
     * @throws AuthenticationHandlerException custom exception
     */
    @Nullable
    private User checkIfUserExist(int userId) throws AuthenticationHandlerException{
        try{
            this.session = SessionFactoryAccess.getInstance().getSessionFactory().openSession();
            User user = (User) this.session.get(User.class , userId);
            if (user != null){
                return user;
            }
        }catch (HibernateException e){
            //roll back in case of problem
            throw new AuthenticationHandlerException("check if user exist failed", e);
        }finally {
            try{
                this.session.close();
            }catch (HibernateException e){
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * gets user object and verify if exist in DB
     * @param userId
     * @throws AuthenticationHandlerException
     */
    public User login(int userId, String password) throws AuthenticationHandlerException{
        //user holder var
        User authenticateUser;
        logger.info("Sign in request from" + userId);
        /// set encrypted password to user
        password = (AuthenticationHandlerUtilitiesScala.passEncryption(password));
        //check if user exist , if yes returns user instance
        if ((authenticateUser = checkIfUserExist(userId)) != null){
            logger.info(authenticateUser.getUserName());
            if(authenticateUser.getPassword().equals(password)){
                logger.info("user exist in DB");
                return authenticateUser;
            }
            else{
                return null;
            }
            //System.out.println(AuthenticationHandlerUtilitiesScala.comperUsers(user, authenticatUser));
        }else {
            //System.out.println("No such user"+user.getUserName());
            logger.info("error authentication user");
        }
        return null;
    }

    /**
     * get all user list from DB
     * @return user list
     * @throws AuthenticationHandlerException
     */
    @Override
    public Collection<User> getAllUsers() throws AuthenticationHandlerException {
        ArrayList<User> arrayList = new ArrayList<User>();
        try{
            logger.info("Get items");
            this.session = SessionFactoryAccess.getInstance().getSessionFactory().openSession();
            List<User> userList = session.createQuery("FROM User").list();
            Iterator i = userList.iterator();
            while(i.hasNext()) {
                User user = (User) i.next();
                arrayList.add(user);
            }
        }catch (HibernateException e){
            throw new AuthenticationHandlerException("Problem to get all the items", e);
        }finally {
            try {
                this.session.close();
            }catch (HibernateException e){
                e.printStackTrace();
            }
        }
        return arrayList;
    }

}