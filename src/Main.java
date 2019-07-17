import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import java.util.List;


public class Main {
    private static final SessionFactory ourSessionFactory;

    static {
        try {
            //获取配置
            Configuration configuration = new Configuration();
            configuration.configure();

            ourSessionFactory = configuration.buildSessionFactory();
        } catch (Throwable ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static Session getSession() throws HibernateException {
        return ourSessionFactory.openSession();
    }

    public static void main(final String[] args) throws Exception {

        Session session1 = getSession();
        Session session2 = getSession();
        try {

            session1.beginTransaction();
            session2.beginTransaction();
            //通过session1得到product1
            Product p1 = session1.get(Product.class, 1);
            p1.setPrice(p1.getPrice() + 1000);
            System.out.println("p1的price:" + p1.getPrice()+1000);
            //在p1更新前，通过session2得到product2
            Product p2 = session2.get(Product.class, 1);
            p2.setPrice(p2.getPrice() + 1000);
            //更新数据
            session1.update(p1);
            session2.update(p2);
            //提交事务
            session1.getTransaction().commit();
            session2.getTransaction().commit();
            //获得的值
            Product p = session1.get(Product.class, 1);
            System.out.println("更新后的p1的price:" + p.getPrice());


        } catch (NullPointerException e) {
            e.printStackTrace();
            throw e;
        } finally {
            if(ourSessionFactory != null) {
                ourSessionFactory.close();
            }
        }
    }

    /**
     * 添加数据
     * @param product 实体类
     */
    public void add(Product product){
        Session session = getSession();
        session.beginTransaction();
        session.save(product);
        session.getTransaction().commit();
        session.close();
    }

    /**
     * 获取指定项
     * @param i 主键id
     * @return 返回
     */
    public Product get(int i){
        Session session = getSession();
        Product product = session.get(Product.class, i);
        session.close();
        return product;
    }

    /**
     * 获取所有结果
     * @return 返回list
     */
    public List<Product> getAll (){
        Session session = getSession();
        Query query = session.createQuery("from Product");
        return query.list();
    }

    /**
     * 删除指定项
     * @param i
     */
    public void delete(int i){
        Session session = getSession();
        session.beginTransaction();
        Product p = session.get(Product.class, i);
        session.delete(p);
        session.getTransaction().commit();
        session.close();
    }

    /**
     * 更新指定项
     * @param i
     */
    public void update(int i){
        Session session = getSession();
        session.beginTransaction();
        Product product =session.get(Product.class,i);
        session.update(product);
        session.getTransaction().commit();
        session.close();
    }

}