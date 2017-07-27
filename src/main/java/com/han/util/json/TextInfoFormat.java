package com.han.util.json;

public enum TextInfoFormat
{
    JSON(0), XML(1);
    private final int code;

    private TextInfoFormat(int value)
    {
        code = value;
    }

    public int getValue()
    {
        return code;
    }
}
