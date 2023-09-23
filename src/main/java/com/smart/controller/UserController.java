package com.smart.controller;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.smart.dao.FamilyDao;
import com.smart.dao.cFamDao;
import com.smart.entities.Contact;
import com.smart.entities.Family;
import com.smart.entities.User;
import com.smart.entities.cFam;
import com.smart.helper.Message;
import com.smart.repo.CFamRepository;
import com.smart.repo.ContactRepository;
import com.smart.repo.FamRepository;
import com.smart.repo.FamilyRepository;
import com.smart.repo.UserRepository;

@Controller
@RequestMapping("/user")
public class UserController {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private ContactRepository contactRepository;
	@Autowired
	private FamilyRepository familyRepository;
	@Autowired
	private FamRepository famRepository;

	@Autowired
	private CFamRepository cfamRepository;

	// Getting User
	@ModelAttribute
	public void addCommonData(Model model, Principal principal) {
		String userName = principal.getName();
		System.out.println("Username:" + userName);
		User user = userRepository.getUserByUserName(userName);
		System.out.println("User" + user);
		model.addAttribute("user", user);

	}

//Handler for Employee Dashboard
	@RequestMapping("/index")
	public String dashboard(Model model, Principal principal) {
		model.addAttribute("title", "User DashBoard");

		return "normal/user_dashboard";
	}

	// Open Form Handler:
	@GetMapping("/add-contact")
	public String openAddContactForm(Model model) {
		model.addAttribute("title", "Add Contact");
		model.addAttribute("contact", new Contact());
		return "normal/add_contact_form";
	}

	// Processing add contact form
	@PostMapping("/process-contact")
	public String processContact(@ModelAttribute Contact contact, @RequestParam("profileImage") MultipartFile file,
			Principal principal, HttpSession session) {
		try {
			String name = principal.getName();
			User user = this.userRepository.getUserByUserName(name);
			if (file.isEmpty()) {
				// If the file is empty :
				contact.setImage("contact.png");

			} else {
				// Upload the file to folder and update the details to contact
				contact.setImage(file.getOriginalFilename());

				// in place of java.io.File must use File
				java.io.File saveFile = new ClassPathResource("static/images").getFile();
				// Just after getting absolute path need to add +file.seperator
				Path path = Paths.get(saveFile.getAbsolutePath() + file.getOriginalFilename());
				Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
				System.out.println("Image is uploaded");
			}

			contact.setUser(user);
			user.getContacts().add(contact);
			this.userRepository.save(user);
			System.out.println("Data: " + contact);
			System.out.println("Added to data base");
			// Success mesasage
			session.setAttribute("message", new Message("Contact added successfully!! You can add more.. ", "success"));
		} catch (Exception e) {
			System.out.println("Error:" + e);
			e.printStackTrace();
			// Error message
			session.setAttribute("message", new Message("something went wrong!! Try again", "danger"));

		}
		return "normal/add_contact_form";
	}

	// show contacts handler
	// per page :5[n]
	// current page =0[page]
	@GetMapping("/show-contacts/{page}")
	public String showContacts(@PathVariable("page") Integer page, Model m, Principal principal) {
		m.addAttribute("title", "View user Contacts");
		String userName = principal.getName();
		User user = this.userRepository.getUserByUserName(userName);
		// Current Page -page
		// Contact per page-5
		Pageable pageable = PageRequest.of(page, 3);

		Page<Contact> contacts = this.contactRepository.findContactsByUser(user.getId(), pageable);
		m.addAttribute("contacts", contacts);
		m.addAttribute("currentPage", page);
		m.addAttribute("totalPages", contacts.getTotalPages());
		return "normal/show_contacts";
	}

	// Showing specific contact details
	@GetMapping("/{cId}/contact")
	public String showContactDetail(@PathVariable("cId") Integer cId, Model model, Principal principal) {
		System.out.println("CID:_____" + cId);
		Optional<Contact> contactOptional = this.contactRepository.findById(cId);
		Contact contact = contactOptional.get();
		// Security Bugs
		String userName = principal.getName();
		User user = this.userRepository.getUserByUserName(userName);
		if (user.getId() == contact.getUser().getId()) {
			model.addAttribute("contact", contact);
			model.addAttribute("title", contact.getName());
		}
		return "normal/contact_detail";

	}

	// Deleting Contact handler
	@GetMapping("/delete/{cid}")
	public String deleteContact(@PathVariable("cid") Integer cId, Model model, HttpSession session,
			Principal principal) {

		Contact contact = this.contactRepository.findById(cId).get();

		// Contact contact = contactOptional.get();
		System.out.println("Contact" + contact.getcId());
		// contact.setUser(null);
		// this.contactRepository.delete(contact);
		User user = this.userRepository.getUserByUserName(principal.getName());
		user.getContacts().remove(contact);
		this.userRepository.save(user);

		System.out.println("Deleted");
		session.setAttribute("message", new Message("contact deleted successfully", "success"));
		return "redirect:/user/show-contacts/0";
	}

	// Update Contact
	@PostMapping("/update-contact/{cid}")
	public String updateForm(@PathVariable("cid") Integer cid, Model m) {

		m.addAttribute("title", "Update-Contact");
		Contact contact = this.contactRepository.findById(cid).get();
		m.addAttribute("contact", contact);

		return "normal/update_form";
	}

	// Update contact Handler:
	@PostMapping("/process-update")
	public String updateHandler(@ModelAttribute Contact contact, @RequestParam("profileImage") MultipartFile file,
			Model m, HttpSession session, Principal principal) {
		try {
			Contact oldContactDetail = this.contactRepository.findById(contact.getcId()).get();

			// image..
			if (!file.isEmpty())
			// Fetching Old Contact Details
			{
				// file work
				// rewrite

				// Deleting old Photo:
				java.io.File deleteFile = new ClassPathResource("static/images").getFile();
				java.io.File file1 = new java.io.File(deleteFile, oldContactDetail.getImage());
				file1.delete();
				// Updating new profile image

				// in place of java.io.File must use File
				java.io.File saveFile = new ClassPathResource("static/images").getFile();
				// Just after getting absolute path need to add +file.seperator
				Path path = Paths.get(saveFile.getAbsolutePath() + file.getOriginalFilename());
				Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
				contact.setImage(file.getOriginalFilename());
			} else {
				contact.setImage(oldContactDetail.getImage());
			}
			User user = this.userRepository.getUserByUserName(principal.getName());
			contact.setUser(user);
			this.contactRepository.save(contact);
			session.setAttribute("message", new Message("Your Contact is updated", "success"));

		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("Contact name:" + contact.getName());
		System.out.println("Contact name:" + contact.getcId());
		return "redirect:/user/" + contact.getcId() + "/contact";
	}

	// Profile Page handler:
	@GetMapping("/profile")
	public String yourProfile(Model model) {
		model.addAttribute("title", "profilePage");
		return "normal/profile";
	}

	// Family Details Page Handler:
	@GetMapping("/family")
	public String family(Model model, @ModelAttribute("family") Family family) {
		Family fam = new Family();
		System.out.println(fam.getUser());
		model.addAttribute("title", "family_details");
		return "normal/family";
	}

	// Adding Details

	@GetMapping("/details")
	public String familyDetails(Model model) {
		model.addAttribute("title", "fam_details");
		model.addAttribute("family", new Family());
		return "normal/family_details";
	}

	// Handler For adding Family details
	@PostMapping("/do_add")
	public String addFamily(@ModelAttribute("family") FamilyDao family,
			@RequestParam(value = "agreement", defaultValue = "false") boolean agreement, Principal principal) {
		User user = userRepository.findByID(family.getUserId());

		Family fam = new Family(family.getName(), family.getGender(), family.getAge(), family.getCountry(),
				family.getState(), family.getCity(), family.getRelation(), user);
		this.familyRepository.save(fam);
		return "normal/family_details";
	}

	// Getting Family Details
	@GetMapping("/show_famDetails")
	public String showFamilyDetails(Model m, Principal principal) {
		m.addAttribute("title", "family_details");
		String username = principal.getName();
		User user = this.userRepository.getUserByUserName(username);

		List<Family> family = this.famRepository.findFamilyDetailsByUser(user.getId());
		// List<Family> all = this.famRepository.findAll();
		m.addAttribute("family", family);

		return "normal/family";
	}

	// Adding family Details of specific contact
	@GetMapping("/add/{cId}")
	public String addFamilyDetails(@PathVariable("cId") String cId, @ModelAttribute cFamDao cfam, Model model) {
		cFam famc = new cFam();
		int contactid = Integer.parseInt(cId);
		System.out.println("contact Id:-----------------" + cId);
		Contact contact = contactRepository.findByContactId(contactid);
		System.out.println("Contact:"+contact);
		model.addAttribute("title", "contactFamilyDetails");
		model.addAttribute("cfam", famc);

		return "normal/contactFamDetails";

	}

	@PostMapping("/process/{cId}")
	public String processContactFamily(@ModelAttribute cFam cfam,String cId,Principal principal,Model model) {
		String name = principal.getName();
	Contact contact = this.contactRepository.getContactByContactName(name);
	//int cID = Integer.parseInt(cId);
	//System.out.println(cID);
	
		cFam cfam1 = new cFam(cfam.getName(), cfam.getAge(), cfam.getGender(), cfam.getCountry(), cfam.getState(),
				cfam.getCity(), cfam.getRelation(), cfam.getBloodGroup(),contact);
			this.cfamRepository.save(cfam1);
		
		System.out.println("Data:" + cfam);
		return "normal/contactFamDetails";
	
		//int cID = Integer.parseInt(cId); 
				//  Contact contact= this.contactRepository.getContactByContactId(cfam.get);
				
		/*
		 * Integer getcId = this.contactRepository.getcId(contact.getcId());
		 * System.out.println("cId:++++++"+getcId);
		 */		

		//Contact contact=this.contactRepository.getContactByContactId();
	//	Contact id = this.contactRepository.getContactByContactId(contact.getcId());
		
		
	//	System.out.println("Id:++++"+id);
	
		
		
	}

// Viewing Contact Family Details
	@GetMapping("/view")
	public String viewContact(Model model, Principal principal) {
		model.addAttribute("title", "View Contact Family Details");
		List<cFam> cfam = this.cfamRepository.findAll();
		model.addAttribute("cfam", cfam);
		return "normal/viewContactDetails";
	}
}
