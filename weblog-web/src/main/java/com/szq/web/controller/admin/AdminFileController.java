package com.szq.web.controller.admin;

import com.szq.web.service.admin.AdminFileService;
import com.szq.web.utils.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/admin/file")
@RequiredArgsConstructor
public class AdminFileController {

    private final AdminFileService adminFileService;


    /**
     * 上传文件
     */
    @PostMapping("/upload")
    public BaseResponse<Object> uploadFile(@RequestParam MultipartFile file) {
        return adminFileService.uploadFile(file);
    }


}
