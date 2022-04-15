package de.jonashackt.springbootvuejs.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import de.jonashackt.springbootvuejs.domain.User;
import de.jonashackt.springbootvuejs.exception.UserNotFoundException;
import de.jonashackt.springbootvuejs.feignClient.KbProfileClient;
import de.jonashackt.springbootvuejs.feignClient.NotebookClient;
import de.jonashackt.springbootvuejs.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

@Controller
@RequestMapping("/api")
@EnableAsync
public class BackendController {

    private static final Logger LOG = LoggerFactory.getLogger(BackendController.class);

    static final String HELLO_TEXT = "Hello from Spring Boot Backend!";
    static final String SECURED_TEXT = "Hello from the secured resource!";
    private static final String LOGIN_USER_ID = "kubeflow-userid";

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private NotebookClient notebookClient;

    @Autowired
    private KbProfileClient kbProfileClient;

    @ResponseBody
    @RequestMapping(path = "/profiles", method = RequestMethod.POST)
    public String createProfile(@RequestBody String workspaceInfo) {
        LOG.info("GET workspace info:{}, thread name:{}", workspaceInfo, Thread.currentThread().getName());
        String result = "success";
        try {
            kbProfileClient.createProfile(JSONObject.parseObject(workspaceInfo));
        } catch (Exception e) {
            result = "failed";
            LOG.error("Failed to create profile.", e);
        }
        LOG.info("FINISH create workspace. thread name:{}", Thread.currentThread().getName());
        return result;
    }

    @ResponseBody
    @RequestMapping(path = "/profiles/{workspace}", method = RequestMethod.DELETE)
    public String deleteProfile(@PathVariable("workspace") String workspace) {
        LOG.info("GET workspace name:{}", workspace);
        String result = "success";
        try {
            kbProfileClient.deleteProfile(workspace);
        } catch (Exception e) {
            result = "failed";
            LOG.error("Failed to delete profile.", e);
        }
        return result;
    }

    @ResponseBody
    @RequestMapping(path = "/hello")
    public String sayHello(HttpServletRequest request) {
        LOG.info("GET called on /hello resource");
        String userId = request.getHeader(LOGIN_USER_ID);
        LOG.info("GET user id:{}", userId);
        printHeaders(request);
        return HELLO_TEXT;
    }

    @ResponseBody
    @RequestMapping(path = "/{user}/notebooks/info")
    public String getNotebookList(@PathVariable("user") String user) {
        JSON notebooks = notebookClient.getUserNotebooks(user);
        LOG.info("Get note book:{}", notebooks);
        return notebooks.toJSONString();
    }

    @ResponseBody
    @RequestMapping(path = "/createNoteBook", method = RequestMethod.POST)
    public String createNoteBook(@RequestBody String noteBookInfo) {
        LOG.info("Create notebook info:{}", noteBookInfo);
        JSON result = notebookClient.createNotebooks("xieyao", JSONObject.parseObject(noteBookInfo));
        LOG.info("Create notebook result:{}", result);
        return result.toJSONString();
    }

    @ResponseBody
    @RequestMapping(path = "/namespaces/{namespace}/notebooks/{notebook}", method = RequestMethod.DELETE)
    public JSON deleteNoteBook(@PathVariable("namespace") String namespace,
                                 @PathVariable("notebook") String notebook) {
        LOG.info("Delete notebook info, namespace:{}, notebook:{}", namespace, notebook);
        JSON result = notebookClient.deleteNoteBook(namespace, notebook);
        LOG.info("Delete notebook result:{}", result);
        return result;
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
