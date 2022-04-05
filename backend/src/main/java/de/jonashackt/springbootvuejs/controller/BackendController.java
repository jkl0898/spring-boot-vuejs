package de.jonashackt.springbootvuejs.controller;

import com.alibaba.fastjson.JSON;
import de.jonashackt.springbootvuejs.domain.User;
import de.jonashackt.springbootvuejs.exception.UserNotFoundException;
import de.jonashackt.springbootvuejs.feignClient.NotebookClient;
import de.jonashackt.springbootvuejs.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

@Controller
@RequestMapping("/api")
public class BackendController {

    private static final Logger LOG = LoggerFactory.getLogger(BackendController.class);

    public static final String HELLO_TEXT = "Hello from Spring Boot Backend!";
    public static final String SECURED_TEXT = "Hello from the secured resource!";
    public static final String LOGIN_USER_ID = "kubeflow-userid";

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private NotebookClient notebookClient;

    @ResponseBody
    @RequestMapping(path = "/hello")
    public String sayHello(HttpServletRequest request) {
        LOG.info("GET called on /hello resource");
        String userId = request.getHeader(LOGIN_USER_ID);
        LOG.info("GET user id:{}", userId);
        printHeaders(request);

        JSON notebooks = notebookClient.getUserNotebooks();
        LOG.info("Get note book:{}", notebooks);
        return HELLO_TEXT;
    }

    @ResponseBody
    @RequestMapping(path = "/user/{lastName}/{firstName}", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public long addNewUser (@PathVariable("lastName") String lastName, @PathVariable("firstName") String firstName) {
        User savedUser = userRepository.save(new User(firstName, lastName));

        LOG.info(savedUser.toString() + " successfully saved into DB");

        return savedUser.getId();
    }

    @ResponseBody
    @GetMapping(path = "/user/{id}")
    public User getUserById(@PathVariable("id") long id) {

        return userRepository.findById(id).map(user -> {
            LOG.info("Reading user with id " + id + " from database.");
            return user;
        }).orElseThrow(() -> new UserNotFoundException("The user with the id " + id + " couldn't be found in the database."));
    }

    @ResponseBody
    @RequestMapping(path="/secured", method = RequestMethod.GET)
    public String getSecured() {
        LOG.info("GET successfully called on /secured resource");
        return SECURED_TEXT;
    }


    private void printHeaders(HttpServletRequest request) {
        Enumeration<String> headerNames = request.getHeaderNames();
        while(headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            LOG.info("Header Name - {}, Value - {}",headerName , request.getHeader(headerName));
        }
    }
}
