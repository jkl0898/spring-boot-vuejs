package de.jonashackt.springbootvuejs.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
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

        String noteBookInfo = "{\"name\":\"xieyao-test-3\",\"namespace\":\"xieyao\",\"image\":\"reg.hrlyit.com:443/kubeflow/j1r0q0g6/notebooks/notebook-servers/jupyter-scipy@sha256:98826f77e566aa3fc55da7fed29c283506a10c7053a84528e2dd39294af3586b\",\"allowCustomImage\":true,\"imagePullPolicy\":\"IfNotPresent\",\"customImage\":\"reg.hrlyit.com:443/kubeflow/j1r0q0g6/notebooks/notebook-servers/jupyter-scipy@sha256:98826f77e566aa3fc55da7fed29c283506a10c7053a84528e2dd39294af3586b\",\"customImageCheck\":true,\"serverType\":\"jupyter\",\"cpu\":\"0.5\",\"cpuLimit\":\"0.6\",\"memory\":\"1Gi\",\"memoryLimit\":\"1.2Gi\",\"gpus\":{\"num\":\"none\"},\"noWorkspace\":false,\"workspace\":{\"type\":\"New\",\"name\":\"workspace-xieyao-test-3\",\"templatedName\":\"workspace-{notebook-name}\",\"size\":\"5Gi\",\"templatedPath\":\"/home/jovyan\",\"mode\":\"ReadWriteOnce\",\"class\":\"{none}\",\"extraFields\":{}},\"affinityConfig\":\"\",\"tolerationGroup\":\"\",\"datavols\":[],\"shm\":true,\"configurations\":[]}";

        //String noteBookInfo = "{'name': 'xieyao-test-4', 'namespace': 'xieyao', 'image': 'reg.hrlyit.com:443/kubeflow/j1r0q0g6/notebooks/notebook-servers/jupyter-scipy@sha256:98826f77e566aa3fc55da7fed29c283506a10c7053a84528e2dd39294af3586b', 'allowCustomImage': True, 'imagePullPolicy': 'IfNotPresent', 'customImage': 'reg.hrlyit.com:443/kubeflow/j1r0q0g6/notebooks/notebook-servers/jupyter-scipy@sha256:98826f77e566aa3fc55da7fed29c283506a10c7053a84528e2dd39294af3586b', 'customImageCheck': True, 'serverType': 'jupyter', 'cpu': '0.5', 'cpuLimit': '0.6', 'memory': '1Gi', 'memoryLimit': '1.2Gi', 'gpus': {'num': 'none'}, 'noWorkspace': False, 'workspace': {'type': 'Existing', 'name': 'workspace-xieyao-test-4', 'templatedName': 'workspace-{notebook-name}', 'templatedPath': '/home/jovyan', 'class': '{none}', 'extraFields': {}}, 'affinityConfig': '', 'tolerationGroup': '', 'datavols': [], 'shm': True, 'configurations': []}";
        JSON result = notebookClient.createNotebooks("xieyao", JSONObject.parseObject(noteBookInfo));
        LOG.info("Create notebook result:{}", result);

        Object notebooks = notebookClient.getUserNotebooks();
        LOG.info("Get note book:{}", notebooks);
        return HELLO_TEXT;
    }

    @ResponseBody
    @RequestMapping(path = "/hi")
    public String sayHi() {
        LOG.info("GET called on /hi resource");
        return "hi";
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
