package com.springai.openai.tools;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

import java.time.LocalTime;
import java.time.ZoneId;

@Component
public class Timetools {

    private static final Logger LOGGER = LoggerFactory.getLogger(Timetools.class);

    @Tool(name = "getCurrentLocalTime", description = "Get the current time in User's timezone")
    String getCurrentLocalTime() {
        LOGGER.info("Returning the current time");
        LocalTime localTime = LocalTime.now();
        return localTime.toString();
    }

    @Tool(name = "getCurrentTime",
            description = "Get the current time in the specified time zone.")
    public String getCurrentTime(@ToolParam(
            description = "Value representing the time zone") String timeZone) {
        LOGGER.info("Returning the current time in the timezone {}", timeZone);
        return LocalTime.now(ZoneId.of(timeZone)).toString();
    }
}
