package com.yinxt.surveyscale.util.exeption;

import com.yinxt.surveyscale.util.result.ResultEnum;
import lombok.Data;

@Data
public class LogicException extends RuntimeException {

    String code;

    public LogicException() {
        super();
    }

    public LogicException(String message) {
        super(message);
        this.code = ResultEnum.SYSTEM_ERROR.getCode();
    }

    public LogicException(String code, String message) {
        super(message);
        this.code = code;
    }

    public LogicException(ResultEnum resultEnum, String message) {
        super(message);
        this.code = resultEnum.getCode();
    }

    public LogicException(ResultEnum resultEnum) {
        super(resultEnum.getName());
        this.code = resultEnum.getCode();
    }

}
