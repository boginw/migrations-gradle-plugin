package org.mybatis.gradle.task;

import org.apache.ibatis.migration.commands.NewCommand;
import org.junit.jupiter.api.Test;

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
    void whenNameProvidedAndTaskIsRun_expectNamePassedToCommand() {
        String name = "NAME-HERE";
        task.setName(name);
        task.run();

        verify(command).execute(name);
    }
}
