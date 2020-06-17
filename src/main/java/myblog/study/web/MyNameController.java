package myblog.study.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyNameController {

    private MyNameService myNameService;

    public MyNameController(MyNameService myNameService) {
        this.myNameService = myNameService;
    }

    @GetMapping("/my-name")
    public String myName(@RequestParam String name) throws InterruptedException {
        return myNameService.sayName(name);
    }
}
