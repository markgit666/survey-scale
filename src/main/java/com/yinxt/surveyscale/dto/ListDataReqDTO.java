package com.yinxt.surveyscale.dto;

import lombok.Data;

/**
 * 列表数据请求DTO
 */
@Data
public class ListDataReqDTO<T> {

    private int pageNo = 1;
    private int pageSize = 10;
    private T data;
}
