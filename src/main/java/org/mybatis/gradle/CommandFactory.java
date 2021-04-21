package org.mybatis.gradle;

import org.apache.ibatis.migration.commands.Command;
import org.apache.ibatis.migration.options.SelectedOptions;

import java.lang.reflect.InvocationTargetException;

public class CommandFactory {
    public <T extends Command> T create(Class<T> klass, SelectedOptions options) {
        try {
            return klass.getConstructor(SelectedOptions.class).newInstance(options);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }
}
