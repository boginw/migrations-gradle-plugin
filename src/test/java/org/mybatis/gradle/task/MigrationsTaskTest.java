package org.mybatis.gradle.task;

import org.apache.ibatis.migration.commands.Command;
import org.apache.ibatis.migration.options.SelectedOptions;
import org.apache.ibatis.migration.options.SelectedPaths;
import org.gradle.api.Project;
import org.gradle.api.tasks.TaskAction;
import org.gradle.testfixtures.ProjectBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mybatis.gradle.CommandFactory;
import org.mybatis.gradle.MigrationsExtension;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

abstract class MigrationsTaskTest<T extends MigrationsTask, C extends Command> {
    @Mock
    CommandFactory factory;
    Project project;
    C command;
    T task;

    @BeforeEach
    void setUp() {
        command = mock(getMigrationsCommandType());
        MockitoAnnotations.openMocks(this);

        project = ProjectBuilder.builder().build();
        MigrationsExtension defaultValues = new MigrationsExtension(project.getObjects(), project.getLayout());
        project.getExtensions().add(MigrationsExtension.class, "migrations", defaultValues);
        task = project.getTasks().create(getType().getSimpleName(), getType(), factory);
        doReturn(command).when(factory).create(eq(getMigrationsCommandType()), any(SelectedOptions.class));
    }

    abstract Class<T> getType();

    abstract Class<C> getMigrationsCommandType();

    @Test
    void expectToHaveTaskActionAnnotation() throws NoSuchMethodException {
        String nameOfRunMethodInRunnable = Runnable.class.getMethods()[0].getName();
        assertTrue(getType().getMethod(nameOfRunMethodInRunnable).isAnnotationPresent(TaskAction.class));
    }

    @Test
    void whenTaskIsRun_expectInitializeCommandToBeRun() {
        task.run();
        verify(command).execute(any());
    }

    @Test
    void whenTaskIsRun_expectEnvironmentPassedToCommand() {
        SelectedOptions options = getOptionsWithDefaultDir();
        options.setEnvironment("ENVIRONMENT");

        MigrationsExtension extension = getExtensionFromOptions(options);
        task.setExtension(extension);
        task.run();

        verify(factory).create(any(), optionsEqualTo(options));
    }

    @Test
    void whenTaskIsRun_expectBasePassedToCommand() {
        SelectedOptions options = getOptionsWithDefaultDir();
        options.getPaths().setBasePath(project.getLayout().getProjectDirectory().dir("BASE-PATH").getAsFile());

        MigrationsExtension extension = getExtensionFromOptions(options);
        task.setExtension(extension);
        task.run();

        verify(factory).create(any(), optionsEqualTo(options));
    }

    @Test
    void whenTaskIsRun_expectForcePassedToCommand() {
        SelectedOptions options = getOptionsWithDefaultDir();
        options.setForce(true);

        MigrationsExtension extension = getExtensionFromOptions(options);
        task.setExtension(extension);
        task.run();

        verify(factory).create(any(), optionsEqualTo(options));
    }

    SelectedOptions optionsEqualTo(final SelectedOptions options) {
        return argThat(o -> optionsAreEqual(o, options));
    }

    SelectedOptions getOptionsWithDefaultDir() {
        SelectedOptions selectedOptions = new SelectedOptions();
        selectedOptions.getPaths().setBasePath(
                new MigrationsExtension(project.getObjects(), project.getLayout()).getBaseDir().get().getAsFile()
        );
        return selectedOptions;
    }

    MigrationsExtension getExtensionFromOptions(SelectedOptions options) {
        MigrationsExtension extension = new MigrationsExtension(project.getObjects(), project.getLayout());
        extension.getEnvironment().set(options.getEnvironment());
        extension.getBaseDir().set(options.getPaths().getBasePath());
        extension.getForce().set(options.isForce());
        return extension;
    }

    boolean optionsAreEqual(SelectedOptions options, SelectedOptions otherOptions) {
        if (options == null && otherOptions == null) {
            return true;
        }
        if (options == null || otherOptions == null) {
            return false;
        }
        return pathsAreEqual(options.getPaths(), otherOptions.getPaths())
                && Objects.equals(options.getEnvironment(), otherOptions.getEnvironment())
                && Objects.equals(options.getTemplate(), otherOptions.getTemplate())
                && Objects.equals(options.getIdPattern(), otherOptions.getIdPattern())
                && Objects.equals(options.isForce(), otherOptions.isForce())
                && Objects.equals(options.isTrace(), otherOptions.isTrace())
                && Objects.equals(options.getCommand(), otherOptions.getCommand())
                && Objects.equals(options.getParams(), otherOptions.getParams())
                && Objects.equals(options.needsHelp(), otherOptions.needsHelp())
                && Objects.equals(options.isQuiet(), otherOptions.isQuiet())
                && Objects.equals(options.hasColor(), otherOptions.hasColor());
    }

    private boolean pathsAreEqual(SelectedPaths paths, SelectedPaths otherPaths) {
        if (paths == null && otherPaths == null) {
            return true;
        }
        if (paths == null || otherPaths == null) {
            return false;
        }
        return Objects.equals(paths.getBasePath(), otherPaths.getBasePath())
                && Objects.equals(paths.getEnvPath(), otherPaths.getEnvPath())
                && Objects.equals(paths.getScriptPath(), otherPaths.getScriptPath())
                && Objects.equals(paths.getDriverPath(), otherPaths.getDriverPath())
                && Objects.equals(paths.getHookPath(), otherPaths.getHookPath());
    }
}
