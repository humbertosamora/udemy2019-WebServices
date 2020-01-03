package webservice.config;

import java.time.Instant;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import webservice.entities.Category;
import webservice.entities.Order;
import webservice.entities.OrderItem;
import webservice.entities.Payment;
import webservice.entities.Product;
import webservice.entities.User;
import webservice.entities.enums.OrderStatus;
import webservice.repositories.CategoryRepository;
import webservice.repositories.OrderItemRepository;
import webservice.repositories.OrderRepository;
import webservice.repositories.ProductRepository;
import webservice.repositories.UserRepository;

@Configuration
@Profile("test")
public class TestConfig implements CommandLineRunner {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private ProductRepository productRepository;
		
	@Autowired
	private OrderItemRepository orderItemRepository;
	
	
	@Override
	public void run(String... args) throws Exception {
		
		Category cat1 = new Category(null, "ELECTRONICS");
		Category cat2 = new Category(null, "BOOKS");
		Category cat3 = new Category(null, "COMPUTERS");
		categoryRepository.saveAll(Arrays.asList(cat1, cat2, cat3));
		
		Product p1 = new Product(null, "THE LORD OF THE RINGS", "LOREM IPSUM DOLOR SIT AMET, CONSECTETUR.", 90.5, "");
		Product p2 = new Product(null, "SMART TV", "NULLA EU IMPERDIET PURUS. MAECENAS ANTE.", 2190.0, "");
		Product p3 = new Product(null, "MACBOOK PRO", "NAM ELEIFEND MAXIMUS TORTOR, AT MOLLIS.", 1250.0, "");
		Product p4 = new Product(null, "PC GAMER", "DONEC ALIQUET ODIO AC RHONCUS CURSUS.", 1200.0, "");
		Product p5 = new Product(null, "RAILS FOR DUMMIES", "CRAS FRINGILLA CONVALLIS SEM VEL FAUCIBUS.", 100.99, "");
		productRepository.saveAll(Arrays.asList(p1, p2, p3, p4, p5));
		
		p1.getCategories().add(cat2);
		p2.getCategories().add(cat1);
		p2.getCategories().add(cat3);
		p3.getCategories().add(cat3);
		p4.getCategories().add(cat3);
		p5.getCategories().add(cat2);
		productRepository.saveAll(Arrays.asList(p1, p2, p3, p4, p5));
		
		User u1 = new User(null, "MARIA BROWN", "MARIA@GMAIL.COM", "988888888", "123456");
		User u2 = new User(null, "ALEX GREEN", "ALEX@GMAIL.COM", "977777777", "123456");
		userRepository.saveAll(Arrays.asList(u1, u2));
		
		Order o1 = new Order(null, Instant.parse("2019-06-20T19:53:07Z"), OrderStatus.PAID, u1);
		Order o2 = new Order(null, Instant.parse("2019-07-21T03:42:10Z"), OrderStatus.WAITING_PAYMENT, u2);
		Order o3 = new Order(null, Instant.parse("2019-07-22T15:21:22Z"), OrderStatus.WAITING_PAYMENT, u1);
		orderRepository.saveAll(Arrays.asList(o1, o2, o3));
		
		OrderItem oi1 = new OrderItem(o1, p1, 2, p1.getPrice());
		OrderItem oi2 = new OrderItem(o1, p3, 1, p3.getPrice());
		OrderItem oi3 = new OrderItem(o2, p3, 2, p3.getPrice());
		OrderItem oi4 = new OrderItem(o3, p5, 2, p5.getPrice());
		orderItemRepository.saveAll(Arrays.asList(oi1, oi2, oi3, oi4));
		
		/* To save a dependent object, it should be added to the main (independent) object and
		 * then save this object, which will persist all dependent objects. See CascadeType.ALL
		 * in Payment object declaration in class Order.
		 * */
		Payment pay1 = new Payment(null, Instant.parse("2019-06-20T21:53:07Z"), o1);
		o1.setPayment(pay1);
		orderRepository.save(o1);
	}
	
}