package com.han.util.json;

import com.han.entity.Seckill;

import java.time.LocalDateTime;

/**
 * Created by han on 2017/7/27.
 */
public class Test
{
    public static void main(String[] a)
    {
        LocalDateTime startTime = LocalDateTime.now();
        LocalDateTime endTime = LocalDateTime.now();
        LocalDateTime createTIme = LocalDateTime.now();

        Seckill mode = new Seckill(1, "test",0, startTime, endTime, createTIme);
        TextInfoUtil txtUtil = TextInfoUtil.createJsonTextInfoUtil();
        String str = txtUtil.convertToString(mode);
        System.out.println(str);
    }
}
