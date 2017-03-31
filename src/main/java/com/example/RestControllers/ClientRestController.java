package com.example.RestControllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.Entities.Booking;
import com.example.Entities.Restaurant;
import com.example.Entities.Review;
import com.example.Entities.User;
import com.example.Entities.Voucher;
import com.example.Repositories.UserRepository;
import com.example.Services.ClientService;
import com.fasterxml.jackson.annotation.JsonView;

@RestController
@RequestMapping("/api/clients")
public class ClientRestController {

	@Autowired
	private ClientService clientService;


	interface UserDetail extends User.Basic,User.Restaurants, User.Reviews,/* User.Users,*/ Review.Basic, Voucher.Basic, Booking.Basic, Restaurant.Basic,
	User.Vouchers, User.Bookings{}

	@ResponseBody
	@JsonView(UserDetail.class)
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<User> getClient(HttpSession session, @PathVariable long id) {
		session.setMaxInactiveInterval(-1);
		User user = clientService.clientServiceFindOne(id);
		if (user != null) {
			return new ResponseEntity<>(clientService.clientServiceFindOne(id), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@ResponseBody
	@JsonView(UserDetail.class)
	@RequestMapping(value = "/signin", method = RequestMethod.POST)
	public ResponseEntity<User> postClient(HttpSession session, @RequestBody User user) {
		session.setMaxInactiveInterval(-1);
		if (clientService.clientServiceFindByName(user.getName())== null) {
			user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
			clientService.clientServiceSave(user);
			return new ResponseEntity<>(user, HttpStatus.CREATED);
		} else {
			return new ResponseEntity<>(HttpStatus.CONFLICT);
		}

	}

	@ResponseBody
	@JsonView(UserDetail.class)
	@RequestMapping(value = "/", method = RequestMethod.PUT)
	public ResponseEntity<User> putUser(HttpSession session, Authentication authenticate, @RequestBody User updatedUser) {
		session.setMaxInactiveInterval(-1);

		User user = clientService.clientServiceFindByEmail(authenticate.getName());
		if (user != null) {
			updatedUser.setId(clientService.clientServiceFindByEmail(authenticate.getName()).getId());
			updatedUser.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
			clientService.clientServiceSave(updatedUser);
			return new ResponseEntity<>(updatedUser, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	/*@ResponseBody
	@JsonView(UserDetail.class)
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<User> deleteUser(HttpSession session, @PathVariable long id) {
		session.setMaxInactiveInterval(-1);
		userRepository.delete(id);
		return new ResponseEntity<>(null, HttpStatus.OK);

	}*/

	@ResponseBody
	@JsonView(User.Basic.class)
	@RequestMapping(value = "/{id}/following", method = RequestMethod.GET)
	public ResponseEntity<List<User>> getUserFollowing(HttpSession session, @PathVariable long id) {

		session.setMaxInactiveInterval(-1);
		User user = clientService.clientServiceFindOne(id);
		if (user != null) {
			return new ResponseEntity<>(user.getFollowing(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

	}

	@ResponseBody
	@JsonView(User.Basic.class)

	@RequestMapping(value = "/{id}/unfollow", method = RequestMethod.DELETE)
	public ResponseEntity<List<User>> deleteUserFollows(HttpServletRequest request, Authentication authentication,
			HttpSession session, @PathVariable long id) {
		session.setMaxInactiveInterval(-1);
		User user2follow = clientService.clientServiceFindOne(id);
		if (request.isUserInRole("USER")) {
			User userSession = clientService.clientServiceFindByEmail(authentication.getName());
			if (user2follow != null) {
				if (userSession.getFollowing().contains(user2follow)) {
					userSession.getFollowing().remove(user2follow);
					clientService.clientServiceSave(userSession);
				}
				return new ResponseEntity<>(userSession.getFollowing(), HttpStatus.OK);
			} else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		}
		return null;
	}

	@ResponseBody
	@JsonView(User.Basic.class)
	@RequestMapping(value = "/api/clients/{id}/follow", method = RequestMethod.POST)

	public ResponseEntity<List<User>> postUserFollows(HttpServletRequest request, Authentication authentication,
			HttpSession session, @PathVariable long id) {
		session.setMaxInactiveInterval(-1);
		User user2follow = clientService.clientServiceFindOne(id);
		if (request.isUserInRole("USER")) {
			User userSession = clientService.clientServiceFindByEmail(authentication.getName());
			if (user2follow != null) {
				userSession.getFollowing().add(user2follow);
				clientService.clientServiceSave(userSession);
				return new ResponseEntity<>(userSession.getFollowing(), HttpStatus.CREATED);
			} else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		}
		return null;
	}

	@ResponseBody
	@JsonView(User.Basic.class)
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ResponseEntity<Page<User>> getClients(HttpSession session, Pageable page) {
		session.setMaxInactiveInterval(-1);
		Page<User> users = clientService.clientServiceFindAll(page);
		if (users != null) {
			return new ResponseEntity<>(users, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
}
