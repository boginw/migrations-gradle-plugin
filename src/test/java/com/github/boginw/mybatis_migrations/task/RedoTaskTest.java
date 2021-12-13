package com.github.boginw.mybatis_migrations.task;

import org.apache.ibatis.migration.commands.RedoCommand;
import org.gradle.api.tasks.options.Option;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

class RedoTaskTest extends MigrationsTaskTest<RedoTask, RedoCommand> {

    @Override
    Class<RedoTask> getType() {
        return RedoTask.class;
    }

    @Override
    Class<RedoCommand> getMigrationsCommandType() {
        return RedoCommand.class;
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
