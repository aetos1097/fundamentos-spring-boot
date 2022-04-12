package com.fundamentos.springboot.fundamentos;

import com.fundamentos.springboot.fundamentos.bean.MyBean;
import com.fundamentos.springboot.fundamentos.bean.MyBeanWithDependecy;
import com.fundamentos.springboot.fundamentos.bean.MyBeanWithProperties;
import com.fundamentos.springboot.fundamentos.component.ComponentDependency;
import com.fundamentos.springboot.fundamentos.entity.User;
import com.fundamentos.springboot.fundamentos.pojo.UserPojo;
import com.fundamentos.springboot.fundamentos.repository.UserRepository;
import com.fundamentos.springboot.fundamentos.service.UserService;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.domain.Sort;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class FundamentosApplication implements CommandLineRunner {
	private Log LOOGER = LogFactory.getLog(FundamentosApplication.class);
	private ComponentDependency componentDependency;
	private MyBean myBean;
	private MyBeanWithDependecy myBeanWithDependecy;
	private MyBeanWithProperties myBeanWithProperties;
	private UserPojo userPojo;
	private UserRepository userRepository;
	private UserService userService;



	public FundamentosApplication(@Qualifier("componentTwoImplement") ComponentDependency componentDependency, MyBean myBean, MyBeanWithDependecy myBeanWithDependecy, MyBeanWithProperties myBeanWithProperties, UserPojo userPojo, UserRepository userRepository, UserService userService){
		this.componentDependency = componentDependency;
		this.myBean = myBean;
		this.myBeanWithDependecy = myBeanWithDependecy;
		this.myBeanWithProperties = myBeanWithProperties;
		this.userPojo = userPojo;
		this.userRepository = userRepository;
		this.userService = userService;
	}

	public static void main(String[] args) {

		SpringApplication.run(FundamentosApplication.class, args);
	}

	@Override
	public void run(String... args){
		//ejemplosAnteriores();
		saveUsersInDataBase();
		getInformationJpqlFromUser();
		saveWithErrorTransactional();
	}
	private void saveWithErrorTransactional(){
		User test1 = new User("Test1Transactional1","TestTransactional1@domain.com", LocalDate.now());
		User test2 = new User("Test2Transactional2","TestTransactional2@domain.com", LocalDate.now());
		User test3 = new User("Test3Transactional3","TestTransactional1@domain.com", LocalDate.now());
		User test4 = new User("Test4Transactional4","TestTransactional4@domain.com", LocalDate.now());

		List<User> users = Arrays.asList(test1,test2,test3,test4);

		try {
			userService.saveTransactional(users);
		}catch (Exception e){
			LOOGER.error("Esta es una excepcion dentro del metodo transacional "+ e);
		}
		userService.getAllUsers().stream()
				.forEach(user -> LOOGER.info("Este es el usuario dentro del metodo transacional "+ user));


	}

	private void getInformationJpqlFromUser(){
		LOOGER.info("Usuario con el metodo findByUserEmail "+
				userRepository.findByUserEmail("jpd@domain.com")
						.orElseThrow(()->new RuntimeException("No se encontro el usuario")));

		userRepository.findAndSort("user", Sort.by("id").descending())
				.stream()
				.forEach(user-> LOOGER.info("User with method sort" + user));
		userRepository.findByName("pablo")
				.stream()
				.forEach(user -> LOOGER.info("Usuario con query method" + user));

		LOOGER.info("Usuario con Query method findByNameAndName "+userRepository.findByEmailAndName("daniel@domain.com", "Daniel")
				.orElseThrow(()-> new RuntimeException("Usuario no encontrdo")));
		userRepository.findByNameLike("%user%")
				.stream()
				.forEach(user -> LOOGER.info("Usuario findBynameLike "+user));

		userRepository.findByNameOrEmail(null,"user10@domain.com")
				.stream()
				.forEach(user -> LOOGER.info("Usuario findByNameOrEmail "+user));

		/*userRepository.findByBirthdateBetween(LocalDate.of(2021, 02, 2 ), LocalDate.of(2021,07,4))
				.stream()
				.forEach(user -> LOOGER.info("Usuario con intervalo de fechas "+user));*/
		userRepository.findByNameLikeOrderByIdDesc("%user%")
				.stream()
				.forEach(user -> LOOGER.info("Usario con like y ordenado "+ user));
		LOOGER.info("El usuario apartir del named parameter es: "+userRepository.getALLByBirthDateAndEmail(LocalDate.of(2021,07,4),
						"daniel@domain.com")
				.orElseThrow(()->
						new RuntimeException("No se encontro el usuario apartir del named parameter")));

	}
	public void saveUsersInDataBase(){
		User user1 = new User("Juan","jpd@domain.com", LocalDate.of(2021,04,11));
		User user2 = new User("pablo","pde@domain.com", LocalDate.of(2021,05,5));
		User user3 = new User("Daniel","daniel@domain.com", LocalDate.of(2021,07,4));
		User user4 = new User("user4","user4@domain.com", LocalDate.of(2021,01,3));
		User user5 = new User("user5","user5@domain.com", LocalDate.of(2021,02,2));
		User user6 = new User("user6","user6@domain.com", LocalDate.of(2021,03,1));
		User user7 = new User("user7","user7@domain.com", LocalDate.of(2021,11,7));
		User user8 = new User("user8","user8@domain.com", LocalDate.of(2021,12,11));
		User user9 = new User("user9","user9@domain.com", LocalDate.of(2021,1,25));
		User user10 = new User("user10","user10@domain.com", LocalDate.of(2021,8,11));
		User user11= new User("user11","user11@domain.com", LocalDate.of(2021,2,12));
		User user12= new User("user12","user12@domain.com", LocalDate.of(2021,1,22));
		List<User> list = Arrays.asList(user1, user2,user3,user4,user5,user6,user7,user8,user9,user10,user11,user12);
		list.stream().forEach(userRepository::save);

	}

	private void ejemplosAnteriores(){
		componentDependency.saludar();
		myBean.print();
		myBeanWithDependecy.printWithDependency();
		System.out.println(myBeanWithProperties.function());
		System.out.println(userPojo.getEmail()+ "-"+ userPojo.getPassword() + "-"+ userPojo.getAge());
		try {
			int value = 10/0;
			LOOGER.debug("Mi valor: "+value);
		}catch (Exception e){
			LOOGER.error("Esto es un error al dividir por cero "+ e.getMessage());
		}
	}
}
