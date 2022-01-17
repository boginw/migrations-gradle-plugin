package com.github.boginw.mybatis_migrations.task;

import org.apache.ibatis.migration.commands.ScriptCommand;
import org.gradle.api.tasks.options.Option;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

class ScriptTaskTest extends MigrationsTaskTest<ScriptTask, ScriptCommand> {

    @Override
    Class<ScriptTask> getType() {
        return ScriptTask.class;
    }

    @Override
    Class<ScriptCommand> getMigrationsCommandType() {
        return ScriptCommand.class;
    }

    @Test
    void expectFromToHaveOptionAnnotation() throws NoSuchMethodException {
        Option annotation = getType().getMethod("setFrom", String.class).getAnnotation(Option.class);
        assertEquals("from", annotation.option());
    }

    @Test
    void expectToToHaveOptionAnnotation() throws NoSuchMethodException {
        Option annotation = getType().getMethod("setTo", String.class).getAnnotation(Option.class);
        assertEquals("to", annotation.option());
    }

    @Test
    void whenOnlyFromProvidedAndTaskIsRun_expectFromPassedToCommand() {
        String from = "from";
        task.setFrom(from);
        task.run();

        verify(command).execute(from);
    }

    @Test
    void whenBothFromAndToProvidedAndTaskIsRun_expectFromAndToPassedToCommand() {
        String from = "from";
        String to = "to";
        task.setFrom(from);
        task.setTo(to);
        task.run();

        verify(command).execute(from + " " + to);
    }
}
