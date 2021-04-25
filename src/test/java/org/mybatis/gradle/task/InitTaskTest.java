package org.mybatis.gradle.task;

import org.apache.ibatis.migration.commands.InitializeCommand;
import org.apache.ibatis.migration.options.SelectedOptions;
import org.gradle.api.tasks.options.Option;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

class InitTaskTest extends MigrationsTaskTest<InitTask, InitializeCommand> {

    @Override
    @BeforeEach
    void setUp() {
        super.setUp();
    }

    @Test
    void expectSetIdPatternToHaveOptionAnnotation() throws NoSuchMethodException {
        Option annotation = InitTask.class.getMethod("setIdPattern", String.class).getAnnotation(Option.class);
        assertEquals("idPattern", annotation.option());
    }

    @Test
    void whenIdPatternProvidedAndTaskIsRun_expectIdPatternPassedToCommand() {
        SelectedOptions options = getOptionsWithDefaultDir();
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
