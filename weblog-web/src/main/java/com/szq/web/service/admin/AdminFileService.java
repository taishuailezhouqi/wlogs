package com.szq.web.service.admin;

import com.szq.web.utils.BaseResponse;
import org.springframework.web.multipart.MultipartFile;

public interface AdminFileService {
    /**
     * 上传文件
     */
    BaseResponse<Object> uploadFile(MultipartFile file);
}
