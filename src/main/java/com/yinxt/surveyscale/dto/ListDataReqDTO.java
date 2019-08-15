package com.yinxt.surveyscale.dto;

import lombok.Data;

/**
 * 列表数据请求DTO
 */
@Data
public class ListDataReqDTO<T> {

    private int pageNo;
    private int pageSize;
    private T data;
}
