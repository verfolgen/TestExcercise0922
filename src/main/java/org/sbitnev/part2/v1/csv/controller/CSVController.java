package org.sbitnev.part2.v1.csv.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.sbitnev.part2.v1.csv.helper.CSVHelper;
import org.sbitnev.part2.v1.csv.service.CSVService;
import org.sbitnev.part2.v1.util.message.ResponseMessage;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/csv")
@Tag(name = "CSV", description = "All methods for working with csv of the system")
public class CSVController {
    private final CSVService csvService;

    @PostMapping("/upload")
    @Operation(summary = "Method for upload csv")
    public ResponseEntity<ResponseMessage> uploadFile(@Parameter(description = "CSV file")
                                                          @RequestParam("file")MultipartFile file) {
        String message = "";
                if(csvService.saveUserFromCSV(file)) {
                    message = "Uploaded the file successfully: " + file.getOriginalFilename();
                    return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
                }
        message = "Please upload a csv file or try another file!";
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(message));
    }


    @GetMapping("/download")
    @Operation(summary = "Method for download csv")
    public ResponseEntity<Resource> downloadFile() {
        InputStreamResource file = new InputStreamResource(csvService.load());
        String filename = "users.csv";
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
                .contentType(MediaType.parseMediaType("application/csv"))
                .body(file);
    }

}
