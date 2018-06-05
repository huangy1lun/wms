package com.hyl.wms.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class JSONResult {

    private boolean success = true;

    private String msg;

    public static JSONResult mark (String message) {
        return new JSONResult(false, message);
    }

    public static JSONResult success(String message) {
       return  new JSONResult(true,message);
    }
}
