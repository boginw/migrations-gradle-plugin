package com.github.boginw.mybatis_migrations.task;

import org.apache.ibatis.migration.commands.UpCommand;
import org.gradle.api.tasks.options.Option;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

class UpTaskTest extends MigrationsTaskTest<UpTask, UpCommand> {

    @Override
    Class<UpTask> getType() {
        return UpTask.class;
    }

    @Override
    Class<UpCommand> getMigrationsCommandType() {
        return UpCommand.class;
    }

    @Test
    void expectStepsToHaveOptionAnnotation() throws NoSuchMethodException {
        Option annotation = getType().getMethod("setSteps", String.class).getAnnotation(Option.class);
        assertEquals("steps", annotation.option());
    }

    @Test
    void whenStepsProvidedAndTaskIsRun_expectStepsPassedToCommand() {
        String steps = "steps";
        task.setSteps(steps);
        task.run();

        verify(command).execute(steps);
    }
}
