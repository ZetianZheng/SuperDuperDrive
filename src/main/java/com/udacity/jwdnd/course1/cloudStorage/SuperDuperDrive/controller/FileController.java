package com.udacity.jwdnd.course1.cloudStorage.SuperDuperDrive.controller;

import com.udacity.jwdnd.course1.cloudStorage.SuperDuperDrive.exceptions.FileDuplicateException;
import com.udacity.jwdnd.course1.cloudStorage.SuperDuperDrive.exceptions.FileNotSelectedException;
import com.udacity.jwdnd.course1.cloudStorage.SuperDuperDrive.model.CredentialForm;
import com.udacity.jwdnd.course1.cloudStorage.SuperDuperDrive.model.FileForm;
import com.udacity.jwdnd.course1.cloudStorage.SuperDuperDrive.model.NoteForm;
import com.udacity.jwdnd.course1.cloudStorage.SuperDuperDrive.model.User;
import com.udacity.jwdnd.course1.cloudStorage.SuperDuperDrive.service.FileService;
import com.udacity.jwdnd.course1.cloudStorage.SuperDuperDrive.util.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;

import static com.udacity.jwdnd.course1.cloudStorage.SuperDuperDrive.constants.Messages.*;

@Controller
@RequestMapping("/home")
public class FileController {
    private final FileService fileService;
    private final Util util;

    private Logger logger = LoggerFactory.getLogger(FileController.class);

    public FileController(FileService fileService, Util util) {
        this.fileService = fileService;
        this.util = util;
    }

    /**
     * Add new file to the database.
     * @param authentication authentication used to get the username info
     * @param multipartFile post param, uploaded file
     * @return
     */
    @PostMapping("/file-uploading")
    public String addNewFile(Authentication authentication,
                             @RequestParam("fileUpload") MultipartFile multipartFile,
                             RedirectAttributes redirectAttributes) {
        logger.info("[file][uploading] uploading file start!");
        String fileErr = null;
        final String fileOk = FILE_UPLOAD_SUCCESS;
        String fileName = null;

        try {
            // get username from authentication and then got User POJO from User service:
            Integer userId = util.getUserByAuth(authentication).getUserId();
            fileName = multipartFile.getOriginalFilename();
            long fileSize =multipartFile.getSize();
            //First test if user really selected a file

            logger.info("[file] File \"{}\" has been selected.", fileName);
            if(fileName.length()==0) {
                throw(new FileNotSelectedException("No file was selected"));
            }

            // throw exception if file is bigger than 1 M
            if (fileSize > 1048576) {
                throw new MaxUploadSizeExceededException(fileSize);
            }

            // throw exception if there exists same file in database
            if (!fileService.noDuplicateFileName(fileName, userId)) {
                throw(new FileDuplicateException("Duplicated file uploaded"));
            }

            // try to add this file to database.
            fileService.addFile(multipartFile, userId);
        }
        catch (MaxUploadSizeExceededException me){
            logger.warn("[Error][file] File {} upload failed, limitation exceed!", fileName);
            logger.error(me.toString());
            fileErr = FILE_SIZE_LIMIT_EXCEED;
        }
        catch (FileDuplicateException fe){
            logger.warn("[Error][file] File {} upload failed, this file exists!", fileName);
            fileErr = FILE_DUPLICATE_ERROR;
        }
        catch (FileNotSelectedException fe){
            logger.warn("[Error][file] File upload failed, file is empty!");
            fileErr = FILE_NOT_SELECTED_ERR;
        }
        catch (Exception e){
            logger.warn("[Error][file] File {} upload failed, some unknown problems occurred!", fileName);
            fileErr = FILE_OTHER_ERR;
            logger.error(e.toString());
        }

        return redirectToHome(redirectAttributes, fileOk, fileErr, fileName);
    }

    /**
     * The @ResponseBody annotation:
     *      tells a controller that the object returned is automatically serialized
     *      into JSON and passed back into the HttpResponse object.
     * @param filename
     * @return
     * reference: [Spring's RequestBody and ResponseBody Annotations | Baeldung](https://www.baeldung.com/spring-request-response-body)
     */
    @GetMapping(
            value = "/get-file/{filename}",
            produces = MediaType.APPLICATION_OCTET_STREAM_VALUE
    )
    @ResponseBody
    public byte[] getFile(@PathVariable String filename) {
        return fileService.getFile(filename).getFileData();
    }

    /**
     * TODO: here is GetMapping, but why?
     *
     * TODO what if different user have same file name?
     *      two parameters: fileService.deleteFile(filename, userId);
     *
     * TODO why I need to render NoteForm too?
     * delete file by the filename which got by path variable.
     * before delete, the newFileForm object be created to render home.
     * @param authentication
     * @param filename
     * @return
     */
    @GetMapping("/delete-file/{filename}")
    public String deleteFile(Authentication authentication,
                             @PathVariable String filename,
                             RedirectAttributes redirectAttributes) {
        String fileErr = null;
        String fileOk = FILE_DELETE_SUCCESS;

        // delete the file:
        try {
            User user = util.getUserByAuth(authentication);
            Integer userId = user.getUserId();
            int rowNum = fileService.deleteFile(filename, userId);
            if (rowNum < 0) fileErr = FILE_DELETE_ERROR;
        } catch (Exception e) {
            fileErr = FILE_OTHER_ERR;
            logger.warn(e.getMessage());
        }

        return redirectToHome(redirectAttributes, fileOk, fileErr, filename);
    }

    /**
     * use redirectAttributes to combine url and send params to get render by html.
     * @param redirectAttributes
     * @param fileOk
     * @param fileErr
     * @param filename
     * @return
     */
    private String redirectToHome(RedirectAttributes redirectAttributes, String fileOk, String fileErr, String filename) {
        if(fileErr==null) {
            redirectAttributes.addAttribute("fileupok",true);
            redirectAttributes.addAttribute("fileupmsg",String.format(fileOk, filename));
        } else {
            redirectAttributes.addAttribute("fileupnotok",true);
            redirectAttributes.addAttribute("fileupmsg",String.format(fileErr, filename));
        }

        return ("redirect:/home");
    }
}
