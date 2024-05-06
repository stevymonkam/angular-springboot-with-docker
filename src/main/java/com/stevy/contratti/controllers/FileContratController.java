package com.stevy.contratti.controllers;

import com.stevy.contratti.models.Contrat;
import com.stevy.contratti.models.FileContrat;
import com.stevy.contratti.models.Role;
import com.stevy.contratti.payload.response.MessageResponse;
import com.stevy.contratti.repository.ContratRepository;
import com.stevy.contratti.repository.FileContratRepository;
import com.stevy.contratti.service.FileContratService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class FileContratController {
    @Autowired
    private FileContratRepository fileContratRepository;

    @Autowired
    FileContratService fileContratService;

    @Autowired
    private ContratRepository contratRepository;

    @PostMapping("/upload")
    @PreAuthorize("hasRole('ROLE_HR') or hasRole('ROLE_ADMMINISTRAZION') or hasRole('ROLE_LOGISTICA') or hasRole('ROLE_GESTIONE')or hasRole('ROLE_SUPER_ADMIN')")
    public ResponseEntity<MessageResponse> create(@Valid @RequestBody FileContrat filecontrat) {
        FileContrat ct = fileContratRepository.save(filecontrat);
        return ResponseEntity.ok(new MessageResponse("create file contrat", "ok",true, "contrat created successfully",ct));
    }

    @DeleteMapping("/filecontrat/delete/{id}")
    @PreAuthorize("hasRole('ROLE_HR') or hasRole('ROLE_ADMMINISTRAZION') or hasRole('ROLE_LOGISTICA') or hasRole('ROLE_GESTIONE')or hasRole('ROLE_SUPER_ADMIN')")
    public ResponseEntity<MessageResponse> Delete(@PathVariable(name = "id") Long id_filecontrat){
        Contrat c = contratRepository.findByFileContratsId(id_filecontrat);
        FileContrat f =  fileContratRepository.findFileContratById(id_filecontrat);
        String document_name = f.getDocumenti().substring(8);
        c.getFileContrats().remove(f);
        fileContratRepository.deleteById(id_filecontrat);
        fileContratService.deleteOne(document_name);
        return ResponseEntity.ok(new MessageResponse("delete file contrat", "ok",true, "file contrat deleted successfully",c));
    }

    @PostMapping("/contrat/upload")
    public ResponseEntity<MessageResponse> uploadFileContrat(
            @RequestParam("file") MultipartFile file,
            @RequestParam String note,
            @RequestParam Long id_contrat,
            @RequestParam String tipo_doc,
            FileContrat filecontrat) {
        String message = "";
        try {
            fileContratService.save(file);
            filecontrat.setDocumenti("uploads/"+file.getOriginalFilename());
            filecontrat.setNote(note);
            filecontrat.setTipo_doc(tipo_doc);
            Contrat contrat = contratRepository.findContratById(id_contrat);
            contrat.getFileContrats().add(filecontrat);
            FileContrat ct = fileContratRepository.save(filecontrat);
            message = "Uploaded the file successfully: " + file.getOriginalFilename();
            return ResponseEntity.ok(new MessageResponse("create file contrat", "ok",true, message,ct,contrat));
        } catch (Exception e) {
            message = "Could not upload the file: " + file.getOriginalFilename() + "!" ;
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new MessageResponse("upload File for Contrat","error",false,message));
        }
    }

    @GetMapping("/files")
    public ResponseEntity<List<FileContrat>> getListFiles() {
        List<FileContrat> fileInfos = fileContratService.loadAll().map(path -> {
            String filename = path.getFileName().toString();
            String url = MvcUriComponentsBuilder
                .fromMethodName(ContratController.class, "getFile", path.getFileName().toString()).build().toString();
            return new FileContrat(filename, url);
        }).collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(fileInfos);
    }

    @GetMapping("/files/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> getFile(@PathVariable String filename) {
        Resource file = fileContratService.load(filename);
        return ResponseEntity.ok()
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }
}
