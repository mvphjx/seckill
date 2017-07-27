package com.han.util.json;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class LocalDateTimeSerializer extends JsonSerializer<LocalDateTime>
{

    private static final Logger LOG = LoggerFactory.getLogger(LocalDateTimeSerializer.class);
    @Override public void serialize(LocalDateTime value, JsonGenerator jgen, SerializerProvider provider)
            throws IOException, JsonProcessingException
    {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy年MM月dd日 HH时mm分ss秒");
        jgen.writeString(format.format(value));
    }

    /*
     * jdk8并发优化，线程安全
     * Java 8：健壮、易用的时间/日期API
     * Instant 代替 Date，
     * LocalDateTime 代替 Calendar，
     * DateTimeFormatter代替Simpledateformatter，
    */
    /*
     * 根据 时间戳 获取 字符串
	 */
    private String getTimeJDK8(Long timeLong)
    {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy年MM月dd日 HH时mm分ss秒");
        LocalDateTime date = LocalDateTime.ofInstant(Instant.ofEpochMilli(timeLong), ZoneId.systemDefault());
        return format.format(date);
    }

    /*
     * 根据 时间戳 获取  时间
     */
    private LocalDateTime getLocalDateTime(Long timeLong)
    {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy年MM月dd日 HH时mm分ss秒");
        LocalDateTime date = LocalDateTime.ofInstant(Instant.ofEpochMilli(timeLong), ZoneId.systemDefault());
        return date;
    }

    /*
     * 字符串 获取 时间
     */
    private LocalDateTime getTimeJDK8(String time) throws ParseException
    {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy年MM月dd日 HH时mm分ss秒");
        LocalDateTime date = LocalDateTime.parse(time, format);
        return date;
    }

}
