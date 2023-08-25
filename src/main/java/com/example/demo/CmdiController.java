package com.example.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;

@RestController
@RequestMapping("/cmdi")
class CmdiController {
    static {
        initializeDirectoriesAndFiles();
    }

    // 초기화 작업 수행 메서드
    private static void initializeDirectoriesAndFiles() {
        try {
            File johnDir = new File("john");
            johnDir.mkdir();
            File test1File = new File("john/Test1.txt");
            test1File.createNewFile();
            FileWriter fw = new FileWriter(test1File);
            PrintWriter pw = new PrintWriter(fw);
            pw.println("This is Test1.txt in the john directory.");
            pw.close();
            fw.close();

            File janeDir = new File("jane");
            janeDir.mkdir();
            File test2File = new File("jane/Test2.txt");
            test2File.createNewFile();
            FileWriter fw2 = new FileWriter(test2File);
            PrintWriter pw2 = new PrintWriter(fw2);
            pw.println("This is Test2.txt in the jane directory.");
            pw.close();
            fw.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @GetMapping
    public String intro() {
        return "<h2>CMDi demo</h2>" +
                "<li><a href='/cmdi/demo1?username=john'>/cmdi/demo1?username=john</a> (Get file list from john directory)" +
                "<li><a href='/cmdi/demo1?username=jane'>/cmdi/demo1?username=jane</a> (Get file list from jane directory)" +
                "<li><a href='/cmdi/demo1?username=%26hostname%26date/t%26REM '>/cmdi/demo1?username=%26hostname%26date/t%26REM </a> (Hack it. Run hostname and time /t)";
    }

    @GetMapping("/demo1")
    public String cmdiDemo1(@RequestParam String username) {
        String output = "";
        try {
            String os = System.getProperty("os.name").toLowerCase();
            Process process;

            if (os.contains("win")) {
                String cmd = "cmd /c dir /b " + username;
                process = Runtime.getRuntime().exec(cmd);
            } else {
                String cmd = "ls -l " + username;
                process = Runtime.getRuntime().exec(cmd);
            }

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                output += line + "\n";
            }

            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
            output = "Error occurred";
        }
        return output;
    }

    @GetMapping("/demo2")
    public String cmdiDemo2() {
        return "Not implemented";
    }
}
