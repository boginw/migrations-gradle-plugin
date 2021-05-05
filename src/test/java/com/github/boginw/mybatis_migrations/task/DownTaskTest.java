package com.github.boginw.mybatis_migrations.task;

import org.apache.ibatis.migration.commands.DownCommand;
import org.gradle.api.tasks.options.Option;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

class DownTaskTest extends MigrationsTaskTest<DownTask, DownCommand> {

    @Override
    Class<DownTask> getType() {
        return DownTask.class;
    }

    @Override
    Class<DownCommand> getMigrationsCommandType() {
        return DownCommand.class;
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
