package com.tcc.CRUDItau.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.tcc.CRUDItau.model.Comment;
import com.tcc.CRUDItau.model.Pet;
import com.tcc.CRUDItau.model.User;
import com.tcc.CRUDItau.repository.CommentRepository;
import com.tcc.CRUDItau.repository.PetRepository;
import com.tcc.CRUDItau.repository.UserRepository;

@RestController
@RequestMapping("comment")
public class CommentController {
	@Autowired
	CommentRepository commentRepository;
	@Autowired
	UserRepository userRepository;
	@Autowired
	PetRepository petRepository;

	@PostMapping("/add")
	public ResponseEntity<?> addComment(@RequestBody Map<String, String> commentPet) {
		try {
			if (commentPet.get("idPet") == null || commentPet.get("idReceived") == null) {
				return new ResponseEntity<String>("Preencha os ids do usuário e do pet", HttpStatus.BAD_REQUEST);
			}
		
			if (commentPet.get("userName") == null || commentPet.get("userPhone") == null
					|| commentPet.get("userPhoneWithWhats") == null) {
				return new ResponseEntity<String>("Preencha os dados do usuário", HttpStatus.BAD_REQUEST);
			}

			Optional<User> optionalUserReceived = userRepository.findById(Long.parseLong(commentPet.get("idReceived")));
			if (!optionalUserReceived.isPresent()) {
				return new ResponseEntity<String>("User não existe", HttpStatus.BAD_REQUEST);
			}
			User userReceived = optionalUserReceived.get();

			Optional<Pet> getPet = petRepository.findById(Long.parseLong(commentPet.get("idPet")));
			if (!getPet.isPresent()) {
				return new ResponseEntity<String>("Pet não existe", HttpStatus.BAD_REQUEST);
			}
			Pet pet = getPet.get();

			SimpleDateFormat simpleFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
			Date date = simpleFormat.parse(commentPet.get("date"));

			Comment comment = new Comment();
			comment.setDate(date);
			comment.setComment(commentPet.get("comment"));
                        comment.setLink(commentPet.get("link"));
			comment.setNotificationActive(true);
			
			comment.setUserName(commentPet.get("userName")); 
			comment.setUserPhone(Long.parseLong(commentPet.get("userPhone"))); 
			comment.setUserPhoneWithWhats(Boolean.valueOf(commentPet.get("userPhoneWithWhats")));
			
            comment.setUserReceived(userReceived); // Usuário logado
            comment.setPet(pet);

			commentRepository.save(comment);
			return new ResponseEntity<Comment>(comment, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(e, HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping("/notification/desactive/{id}")
	public ResponseEntity<?> desactiveNotifications(@PathVariable Long id) {
		try {
			Optional<Comment> comment = commentRepository.findById(id);
			if (!comment.isPresent()) {
				return new ResponseEntity<String>("Comentário não existe", HttpStatus.BAD_REQUEST);
			}
			Comment desactiveComment = comment.get();
			desactiveComment.setNotificationActive(false);
			commentRepository.save(desactiveComment);

			Map<String, String> msg = new HashMap<String, String>();
			msg.put("msg", "Notificação removida com sucesso");
			return new ResponseEntity<Object>(msg, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(e, HttpStatus.BAD_REQUEST);
		}
	}

	@Transactional
	@GetMapping("/notification/user/{userId}/active/asc")
	public ResponseEntity<?> getActiveNotificationByUserAndOrderAsc(@PathVariable Long userId) {
		try {
			Optional<User> optionalUserReceived = userRepository.findById(userId);
			if (!optionalUserReceived.isPresent()) {
				return new ResponseEntity<String>("User logado não existe", HttpStatus.BAD_REQUEST);
			}
			User userReceived = optionalUserReceived.get();
			User admin = userRepository.findByEmail("cademeubichinho02@outlook.com");

			List<Comment> comments = new ArrayList<Comment>();
			if (userReceived.equals(admin)) {
				comments = commentRepository.findAllByOrderByIdAsc();
			} else {
				comments = commentRepository.findByNotificationActiveAndUserReceivedOrderByIdAsc(true, userReceived);
			}

			List<Map<String, String>> commentsMap = new ArrayList<Map<String, String>>();
			for (Comment comment : comments) {
				Map<String, String> map = new HashMap<String, String>();

				map.put("id", comment.getId().toString());
				map.put("userName", comment.getUserName());
				map.put("userPhone", comment.getUserPhone().toString());
				map.put("userPhoneWithWhats", comment.getUserPhoneWithWhats().toString());

				map.put("name", comment.getPet().getName());
				map.put("specie", comment.getPet().getSpecie().toString());
				map.put("sex", comment.getPet().getSex().toString());
				map.put("lostPet", comment.getPet().getLostPet().toString());

				map.put("date", comment.getDate().toString());
				map.put("comment", comment.getComment());
				map.put("link", comment.getLink());

				commentsMap.add(map);
			}

			return new ResponseEntity<>(commentsMap, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(e, HttpStatus.BAD_REQUEST);
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteComment(@PathVariable Long id) {
		try {
			Optional<Comment> comment = commentRepository.findById(id);
			if (!comment.isPresent()) {
				return new ResponseEntity<String>("Comentário não existe", HttpStatus.BAD_REQUEST);
			}
			Comment removeComment = comment.get();
			commentRepository.delete(removeComment);

			Map<String, String> msg = new HashMap<String, String>();
			msg.put("msg", "Comentário removido com sucesso");
			return new ResponseEntity<>(msg, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(e, HttpStatus.BAD_REQUEST);
		}
	}

}
