import org.junit.Test;

import java.util.List;

public class MainTest {

    @Test
    public void get() {
        Main main = new Main();
        main.get(1);
        System.out.println(main.get(1).getName());

    }

    @Test
    public void getAll() {
        Main main = new Main();
        List<Product> products = main.getAll();
        for (Product product : products){
            System.out.println(product.getName());
        }
    }

}