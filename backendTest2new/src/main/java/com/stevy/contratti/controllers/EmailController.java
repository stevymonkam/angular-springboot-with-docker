package com.stevy.contratti.controllers;

import com.stevy.contratti.models.DateAndCalendar;
import com.stevy.contratti.payload.response.MessageResponse;
import com.stevy.contratti.repository.ContratRepository;
import com.stevy.contratti.service.ContraService;
import com.stevy.contratti.service.FileContratService;
import com.stevy.contratti.service.email.EmailService;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.xml.crypto.Data;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Calendar;
import java.util.Date;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class EmailController {
    @Autowired
    private ContratRepository contratRepository;

    @Autowired
    private  ContraService contraService;

    @Autowired
    EmailService es;

    @Autowired
    FileContratService fileContratService;

    private final Path root = Paths.get("uploads");

    @PostMapping("/sendNotallegato")
    public ResponseEntity<MessageResponse>  sentEmailTest(
            @RequestParam String a,
            @RequestParam String cc,
            @RequestParam String oggetto,
            @RequestParam String corpo,
            @RequestParam("file") MultipartFile file
    )  {
        JSONArray jsonArr = new JSONArray(a);
        JSONArray jsonArr1 = new JSONArray(cc);
        String[] a_arr=new String[jsonArr.length()];
        String[] cc_arr=new String[jsonArr1.length()];
        for (int i = 0; i < jsonArr.length(); i++) {
            a_arr[i] = jsonArr.getString( i );
        }
        for (int i = 0; i < jsonArr1.length(); i++) {
            cc_arr[i] = jsonArr1.getString( i );
        }
        fileContratService.save(file);
        es.sendSimpleMessage( a_arr,cc_arr,oggetto,corpo);
        return ResponseEntity.ok(new MessageResponse("send mail", "ok",true, "mail send successfully"));
    }

    @PostMapping("/sendMailWithallegato")
    public ResponseEntity<MessageResponse> sentEmailTest2(
            @RequestParam("file") MultipartFile file,
            @RequestParam String a,
            @RequestParam String cc,
            @RequestParam String oggetto,
            @RequestParam String corpo
    ) {
        JSONArray jsonArr = new JSONArray(a);
        JSONArray jsonArr1 = new JSONArray(cc);
        String[] a_arr=new String[jsonArr.length()];
        String[] cc_arr=new String[jsonArr1.length()];
        for (int i = 0; i < jsonArr.length(); i++) {
            a_arr[i] = jsonArr.getString( i );
        }
        for (int i = 0; i < jsonArr1.length(); i++) {
            cc_arr[i] = jsonArr1.getString( i );
        }
        DateAndCalendar obj = new DateAndCalendar();
        Calendar data_oggi = Calendar.getInstance();
        Date t3_oggi = obj.calendarToDate(data_oggi);

        fileContratService.save(file);
        System.out.println("uploads/"+file.getOriginalFilename());

        es.sendMessageWithAttachment(a_arr,cc_arr, oggetto, corpo, "uploads/"+file.getOriginalFilename());
        String message = "message send successfully !";
        return ResponseEntity.ok(new MessageResponse("send mail successful", "ok",true, message));
    }

}
