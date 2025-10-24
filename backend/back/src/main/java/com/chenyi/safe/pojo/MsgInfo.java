package com.chenyi.safe.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MsgInfo<T> implements Serializable {

    private T userId;

    private T message;

    @Override
    public String toString() {
        return "MsgInfo{" +
                "userId='" + userId + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
