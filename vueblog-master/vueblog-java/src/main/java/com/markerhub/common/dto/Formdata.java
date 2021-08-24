package com.markerhub.common.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

/**
 * @ClassName : Formdata
 * @Author : Administrator
 * @Date: 2021/8/19 15:07
 * @Description :
 */
@Data
public class Formdata {
    MultipartFile file;
    Long blogid;
}
