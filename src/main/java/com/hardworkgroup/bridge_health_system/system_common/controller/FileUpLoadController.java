package com.hardworkgroup.bridge_health_system.system_common.controller;

import com.hardworkgroup.bridge_health_system.system_common.entity.Result;
import com.hardworkgroup.bridge_health_system.system_common.entity.ResultCode;
import com.hardworkgroup.bridge_health_system.system_common.utils.FileNameUtils;
import com.hardworkgroup.bridge_health_system.system_common.utils.FileUtils;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

@RestController
@CrossOrigin
@Api(tags = "文件上传接口接口")
public class FileUpLoadController {
    @Value("${prop.upload-folder}")
    private String UPLOAD_FOLDER;

    @Value("${prop.upload-folder-excel}")
    private String UPLOAD_FOLDER_EXCEL;
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

    /**
     * 上传Excel表格
     * @param file
     * @return
     */
    @RequestMapping(value = "/upload/excel",method = RequestMethod.POST)
    public Result excelUpload(@RequestParam(name = "file", required = false) MultipartFile file) {
        if (file == null) {
            return new Result(ResultCode.SERVER_ERROR, "请选择要上传的图片");
        }
        if (file.getSize() > 1024 * 1024 * 50) {
            return new Result(ResultCode.SERVER_ERROR, "文件大小不能大于50M");
        }
        //获取文件后缀
        String suffix = FileNameUtils.getSuffix(Objects.requireNonNull(file.getOriginalFilename()));
        if (!".xlsx,.xls".toUpperCase().contains(suffix.toUpperCase())) {
            //return ResultUtil.error(0, "请选择jpg,jpeg,gif,png格式的图片");
            return new Result(ResultCode.SERVER_ERROR, "请选择xlsx,xls格式的图片");
        }
        String savePath = UPLOAD_FOLDER_EXCEL;
        //String filename = FileNameUtils.getFileName(file.getOriginalFilename());
        String filename = file.getOriginalFilename();
        if (FileUtils.upload(file, savePath+"/"+filename)){
            // 上传成功，给出页面提示
            return new Result(ResultCode.SUCCESS, filename);
        }else {
            return new Result(ResultCode.SERVER_ERROR, "保存文件异常");
        }
    }
}
