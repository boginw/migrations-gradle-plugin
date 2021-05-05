package com.github.boginw.mybatis_migrations.task;

import org.apache.ibatis.migration.commands.VersionCommand;
import org.gradle.api.tasks.options.Option;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

class VersionTaskTest extends MigrationsTaskTest<VersionTask, VersionCommand> {

    @Override
    Class<VersionTask> getType() {
        return VersionTask.class;
    }

    @Override
    Class<VersionCommand> getMigrationsCommandType() {
        return VersionCommand.class;
    }

    @Test
    void expectVersionToHaveOptionAnnotation() throws NoSuchMethodException {
        Option annotation = getType().getMethod("setVersion", String.class).getAnnotation(Option.class);
        assertEquals("version", annotation.option());
    }

    @Test
    void whenVersionProvidedAndTaskIsRun_expectVersionPassedToCommand() {
        String version = "version";
        task.setVersion(version);
        task.run();

        verify(command).execute(version);
    }
}
