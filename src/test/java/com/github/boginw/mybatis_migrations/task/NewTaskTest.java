package com.github.boginw.mybatis_migrations.task;

import org.apache.ibatis.migration.commands.NewCommand;
import org.gradle.api.tasks.options.Option;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

class NewTaskTest extends MigrationsTaskTest<NewTask, NewCommand> {

    @Override
    Class<NewTask> getType() {
        return NewTask.class;
    }

    @Override
    Class<NewCommand> getMigrationsCommandType() {
        return NewCommand.class;
    }

    @Test
    void expectHaveToHaveOptionAnnotation() throws NoSuchMethodException {
        Option annotation = getType().getMethod("setName", String.class).getAnnotation(Option.class);
        assertEquals("name", annotation.option());
    }

    @Test
    void whenNameProvidedAndTaskIsRun_expectNamePassedToCommand() {
        String name = "NAME-HERE";
        task.setName(name);
        task.run();

        verify(command).execute(name);
    }
}
