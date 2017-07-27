package com.han.util.json;

import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.deser.std.StdDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class TimestampDeserializer extends StdDeserializer<Timestamp>
{

    private static final Logger LOG = LoggerFactory.getLogger(TimestampDeserializer.class);

    public TimestampDeserializer()
    {
        super(Timestamp.class);
    }

    @Override
    public Timestamp deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException
    {
        String time = jp.getText();

        if (time==null||time.equals(""))
        {
            return null;
        }
        Date parsedDate = null;
        try
        {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
            parsedDate = dateFormat.parse(time);
        }
        catch (ParseException e1)
        {
            try
            {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddhhmmss");
                parsedDate = dateFormat.parse(time);
            }
            catch (ParseException e2)
            {
                try
                {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                    parsedDate = dateFormat.parse(time);
                }
                catch (ParseException e3)
                {
                    try
                    {
                        // try local format, like "Dec 1, 2010 12:00:00 AM"
                        SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd, yyyy hh:mm:ss a", Locale.ENGLISH);
                        parsedDate = dateFormat.parse(time);
                    }
                    catch (ParseException e4)
                    {
                        LOG.error("deserialize time stamp failed" + e4.getMessage());
                    }
                }
            }
        }
        return new Timestamp(parsedDate.getTime());
    }

}
