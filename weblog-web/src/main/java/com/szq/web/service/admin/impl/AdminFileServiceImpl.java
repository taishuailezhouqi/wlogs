package com.szq.web.service.admin.impl;

import com.szq.web.exception.BizException;
import com.szq.web.model.vo.UploadFileRspVO;
import com.szq.web.service.admin.AdminFileService;
import com.szq.web.utils.BaseResponse;
import com.szq.web.utils.Log;
import com.szq.web.utils.MinioUtil;
import com.szq.web.utils.ReturnType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@Slf4j
public class AdminFileServiceImpl implements AdminFileService {

    @Autowired
    private MinioUtil minioUtil;

    /**
     * 上传文件
     *
     */
    @Override
    public BaseResponse<Object> uploadFile(MultipartFile file) {
        try {
            // 上传文件
            String url = minioUtil.uploadFile(file);

            // 构建成功返参，将图片的访问链接返回
            return BaseResponse.success(UploadFileRspVO.builder().url(url).build());
        } catch (Exception e) {
            Log.sdk.error("==> 上传文件至 Minio 错误: ", e);
            // 手动抛出业务异常，提示 “文件上传失败”
            throw new BizException(ReturnType.FILE_UPLOAD_FAILED);
        }
    }
}
