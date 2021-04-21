package org.mybatis.gradle.task;

import org.apache.ibatis.migration.commands.InitializeCommand;
import org.apache.ibatis.migration.options.SelectedOptions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

class InitTaskTest extends MigrationsTaskTest<InitTask, InitializeCommand> {

    @Override
    @BeforeEach
    void setUp() {
        super.setUp();
    }

    @Test
    void whenIdPatternProvidedAndTaskIsRun_expectIdPatternPassedToCommand() {
        SelectedOptions options = new SelectedOptions();
        options.setIdPattern("ID-PATTERN");

        task.setIdPattern(options.getIdPattern());
        task.run();

        verify(factory).create(any(), optionsEqualTo(options));
    }

    @Override
    Class<InitTask> getType() {
        return InitTask.class;
    }

    @Override
    Class<InitializeCommand> getMigrationsCommandType() {
        return InitializeCommand.class;
    }
}
