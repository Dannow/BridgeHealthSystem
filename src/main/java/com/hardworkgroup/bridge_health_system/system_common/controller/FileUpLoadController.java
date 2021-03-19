package com.hardworkgroup.bridge_health_system.system_common.controller;

import com.hardworkgroup.bridge_health_system.system_common.entity.Result;
import com.hardworkgroup.bridge_health_system.system_common.entity.ResultCode;
import com.hardworkgroup.bridge_health_system.system_common.utils.FileNameUtils;
import com.hardworkgroup.bridge_health_system.system_common.utils.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

@RestController
@CrossOrigin
public class FileUpLoadController {
    @Value("${prop.upload-folder}")
    private String UPLOAD_FOLDER;

    @RequestMapping(value = "/upload",method = RequestMethod.POST)
    public Result upload(@RequestParam(name = "file", required = false) MultipartFile file,HttpServletRequest request) {
        if (file == null) {
            return new Result(ResultCode.SERVER_ERROR, "请选择要上传的图片");
        }
        if (file.getSize() > 1024 * 1024 * 50) {
            return new Result(ResultCode.SERVER_ERROR, "文件大小不能大于50M");
        }
        //获取文件后缀
        //String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1, file.getOriginalFilename().length());
        String suffix = FileNameUtils.getSuffix(Objects.requireNonNull(file.getOriginalFilename()));
        if (!".jpg,.jpeg,.gif,.png".toUpperCase().contains(suffix.toUpperCase())) {
            //return ResultUtil.error(0, "请选择jpg,jpeg,gif,png格式的图片");
            return new Result(ResultCode.SERVER_ERROR, "请选择jpg,jpeg,gif,png格式的图片");
        }
        String savePath = UPLOAD_FOLDER;
        String filename = FileNameUtils.getFileName(file.getOriginalFilename());
        if (FileUtils.upload(file, savePath+"/"+filename)){
            // 上传成功，给出页面提示
            return new Result(ResultCode.SUCCESS, filename);
        }else {
            return new Result(ResultCode.SERVER_ERROR, "保存文件异常");
        }
    }
}