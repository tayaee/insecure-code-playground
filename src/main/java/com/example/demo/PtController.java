package com.example.demo;

import org.springframework.core.io.ClassPathResource;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pt")
class PtController {
    @GetMapping
    public String intro() {
        return "<h2>Path Traversal demo</h2>" +
                "<li><a href='/pt/demo1?username=john'>/pt/demo1?username=john</a> (Look up john by username)" +
                "<li><a href='/pt/demo1?username=jane'>/pt/demo1?username=jane</a> (Look up jane by username)" +
                "<li><a href='/pt/demo1?username=../sitesettings'>/pt/demo1?username=../sitesettings</a>  (Hack it)";
    }

    @GetMapping("/demo1")
    public String ptDemo1(@RequestParam(required = false) String username) {
        try {
            String fileName = "userprefs/" + username + ".json";
            ClassPathResource resource = new ClassPathResource(fileName);
            byte[] data = FileCopyUtils.copyToByteArray(resource.getInputStream());
            return new String(data);
        } catch (Exception e) {
            e.printStackTrace();
            return "Error occurred";
        }
    }

    @GetMapping("/demo2")
    public String ptDemo2() {
        return "Not implemented";
    }
}
