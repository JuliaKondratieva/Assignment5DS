package com.julieandco.bookcrossingMain;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.julieandco.bookcrossing.grpc.BookProtoServiceGrpc;

import com.julieandco.bookcrossingMain.dto.*;
import com.julieandco.bookcrossingMain.entity.*;


import com.julieandco.bookcrossingMain.grpc.BookorderGrpc;
import com.julieandco.bookcrossingMain.grpc.BoxGrpc;
import com.julieandco.bookcrossingMain.grpc.UserGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import com.julieandco.bookcrossingMain.grpc.BookGrpc;
import org.springframework.http.*;
import org.springframework.http.converter.json.GsonBuilderUtils;
import org.springframework.web.client.RestTemplate;


import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BookcrossingMainApplication {
	private static final String URL = "http://127.0.0.1:9081";
	private static final RestTemplate restTemplate = new RestTemplate();
	private static final HttpHeaders headers = new HttpHeaders();
	private static final HttpEntity<Object> headersEntity = new HttpEntity<>(headers);
	public static void main(String[] args) {

		//BookGrpc bookGrpc = new BookGrpc();
		UserGrpc userGrpc=new UserGrpc();
		BookorderGrpc bookorderGrpc=new BookorderGrpc();
		BoxGrpc boxGrpc=new BoxGrpc();
		//bookGrpc.postingBook("Pines", "Blake", 2010, 8, Genre.Thriller);
		//userGrpc.postingUser("Oxlade");
		bookorderGrpc.postingOrder("Pines", "Oxlade");
		boxGrpc.postingBox("Khreschatyk");
		boxGrpc.CheckIn("Pines", "Khreschatyk");
		userGrpc.postingUser("Arnold");
		System.out.println("Arnold is ordering the same book");
		bookorderGrpc.postingOrder("Pines", "Arnold");
		System.out.println("Ox is picking up a book");
		boxGrpc.CheckOut("Pines", "Khreschatyk");
		System.out.println("Checked out");


		headers.setContentType(MediaType.APPLICATION_JSON);

		User user1 = new User();
		user1.setUsername("Ox");

		saveUser(user1);

		Book b1=new Book();
		Book b2=new Book();
		Book b3=new Book();
		b1.setTitle("11/22/63");
		b1.setAuthor("Stephen King");
		b1.setGenre(Genre.Fantasy);
		b1.setRating(8);
		b1.setYear(2011);

		b2.setTitle("The Wayward Pines");
		b2.setAuthor("Blake Crouch");
		b2.setGenre(Genre.Thriller);
		b2.setRating(7);
		b2.setYear(2012);

		b3.setTitle("The Three-Body Problem");
		b3.setAuthor("Liu Cixin");
		b3.setGenre(Genre.Scifi);
		b3.setRating(7.5);
		b3.setYear(2008);

		saveBook(b1);
		saveBook(b2);
		saveBook(b3);

		Bookorder newOrder= new Bookorder();
		newOrder.setBook(b1);
		//newOrder.setUser(user1);
		submitOrder(b1, user1);
		Box box1=new Box();
		HttpEntity<String> address = new HttpEntity<>("Khreschatyk");
		ResponseEntity<Void> response1 = restTemplate
				.exchange(URL + "/api/boxes/save", HttpMethod.POST,
						address, Void.class);


		box1.setAddress("Khreschatyk");

		deliverToBox(box1, b1);


		User user2 = new User();
		user2.setUsername("Arnold");
		saveUser(user2);
		Bookorder order2= new Bookorder();
		System.out.println("ARNOLD IS ORDERING THE TAKEN BOOK:\n");
		order2.setBook(b1);

		submitOrder(b1, user2);

		System.out.println("Ox is picking up a book");
		checkOutFromBox(box1, b1);
	}

	private static void submitOrder(Book book, User customer) {
		SubmitOrderDTO submitOrderDTO = new SubmitOrderDTO();
		UserDTO userDTO = new UserDTO();
		userDTO.setUsername(customer.getUsername());
		submitOrderDTO.setUser(userDTO);
		BookDTO bookDTO = new BookDTO();
		bookDTO.setTitle(book.getTitle());
		bookDTO.setAuthor(book.getAuthor());
		bookDTO.setGenre(book.getGenre());
		bookDTO.setYear(book.getYear());
		bookDTO.setRating(book.getRating());
		bookDTO.setAvailable(book.getAvailability());
		bookDTO.setNeedRepair(book.getRepair());
		submitOrderDTO.setBook(bookDTO);

		Gson gson = new GsonBuilder().disableHtmlEscaping().create();
		String deliverJsonStr = gson.toJson(submitOrderDTO);
		HttpEntity<String> deliverJson = new HttpEntity<>(deliverJsonStr, headers);


		HttpEntity<SubmitOrderDTO> submitOrder = new HttpEntity<>(submitOrderDTO);
		ResponseEntity<Void> response3 = restTemplate
				.exchange(URL + "/api/bookorders/submit", HttpMethod.POST,
						submitOrder, Void.class);
	}

	private static void saveBook(Book book) {
		BookDTO bookToSave = new BookDTO();
		bookToSave.setTitle(book.getTitle());
		bookToSave.setAuthor(book.getAuthor());
		bookToSave.setGenre(book.getGenre());
		bookToSave.setYear(book.getYear());
		bookToSave.setRating(book.getRating());
		bookToSave.setAvailable(book.getAvailability());
		bookToSave.setNeedRepair(book.getRepair());
		HttpEntity<BookDTO> saveBook = new HttpEntity<>(bookToSave);
		ResponseEntity<Void> response0 = restTemplate
				.exchange(URL + "/api/books/save", HttpMethod.POST,
						saveBook, Void.class);
	}

	private static void saveUser(User customer) {
		UserDTO userDTO=new UserDTO();
		userDTO.setUsername(customer.getUsername());
		HttpEntity<UserDTO> saveUser = new HttpEntity<>(userDTO);
		ResponseEntity<Void> response1 = restTemplate
				.exchange(URL + "/api/customers/save", HttpMethod.POST,
						saveUser, Void.class);
	}

	private static void saveBox(Box box) {
		BoxDTO boxDTO=new BoxDTO();
		boxDTO.setAddress(box.getAddress());
		boxDTO.setBooks(box.getBooks());
		HttpEntity<BoxDTO> saveUser = new HttpEntity<>(boxDTO);
		ResponseEntity<Void> response1 = restTemplate
				.exchange(URL + "/api/boxes/save", HttpMethod.POST,
						saveUser, Void.class);
	}


	private static void checkOutFromBox(Box box, Book book){
		DeliveryDTO deliveryDTO = new DeliveryDTO();
		BoxDTO boxDTO=new BoxDTO();
		boxDTO.setAddress(box.getAddress());
		boxDTO.setBooks(box.getBooks());
		BookDTO bookDTO=new BookDTO();
		bookDTO.setTitle(book.getTitle());
		bookDTO.setAuthor(book.getAuthor());
		bookDTO.setGenre(book.getGenre());
		bookDTO.setYear(book.getYear());
		bookDTO.setRating(book.getRating());
		bookDTO.setAvailable(book.getAvailability());
		bookDTO.setNeedRepair(book.getRepair());
		deliveryDTO.setBook(bookDTO);
		deliveryDTO.setBook(bookDTO);
		deliveryDTO.setBox(boxDTO);
		Gson gson = new GsonBuilder().disableHtmlEscaping().create();
		String deliverJsonStr = gson.toJson(deliveryDTO);
		HttpEntity<String> deliverJson = new HttpEntity<>(deliverJsonStr, headers);
		ResponseEntity<Void> response1 = restTemplate
				.exchange(URL + "/api/boxes/checkout", HttpMethod.POST, deliverJson, Void.class);

		System.out.println("Checked book:" + book.getTitle() + "\n" + "From : "
				+ box.getAddress() + " box \n");
	}


	private static void deliverToBox(Box box, Book book){
		DeliveryDTO deliveryDTO = new DeliveryDTO();
		BoxDTO boxDTO=new BoxDTO();
		boxDTO.setAddress(box.getAddress());
		boxDTO.setBooks(box.getBooks());
		BookDTO bookDTO=new BookDTO();
		bookDTO.setTitle(book.getTitle());
		bookDTO.setAuthor(book.getAuthor());
		bookDTO.setGenre(book.getGenre());
		bookDTO.setYear(book.getYear());
		bookDTO.setRating(book.getRating());
		bookDTO.setAvailable(book.getAvailability());
		bookDTO.setNeedRepair(book.getRepair());
		deliveryDTO.setBook(bookDTO);
		deliveryDTO.setBox(boxDTO);
		Gson gson = new GsonBuilder().disableHtmlEscaping().create();
		String deliverJsonStr = gson.toJson(deliveryDTO);

		HttpEntity<String> deliverJson = new HttpEntity<>(deliverJsonStr, headers);
		ResponseEntity<Void> response1 = restTemplate
				.exchange(URL + "/api/boxes/deliver", HttpMethod.POST, deliverJson, Void.class);

		System.out.println("Delivery to the" + box.getAddress() + " box: \n" + "Book: "
				+ book.getTitle() + " is delivered \n");
	}
}





