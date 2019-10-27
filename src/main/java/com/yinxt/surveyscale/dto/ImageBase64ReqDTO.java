package com.yinxt.surveyscale.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * base64图片请求dto
 */
@Data
public class ImageBase64ReqDTO {

    @NotBlank(message = "图片内容不能为空")
    private String base64Image;

}
