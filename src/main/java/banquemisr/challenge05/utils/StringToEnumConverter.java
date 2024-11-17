package banquemisr.challenge05.utils;

import banquemisr.challenge05.models.TaskStatus;
import org.springframework.core.convert.converter.Converter;

public class StringToEnumConverter implements Converter<String, TaskStatus> {
    @Override
    public TaskStatus convert(String source) {
        return TaskStatus.valueOf(source.toUpperCase());
    }
}

