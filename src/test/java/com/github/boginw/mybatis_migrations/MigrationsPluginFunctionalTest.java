package com.github.boginw.mybatis_migrations;

import com.github.boginw.mybatis_migrations.task.InitTask;
import com.github.boginw.mybatis_migrations.task.NewTask;
import org.gradle.testkit.runner.BuildResult;
import org.gradle.testkit.runner.GradleRunner;
import org.gradle.testkit.runner.TaskOutcome;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class MigrationsPluginFunctionalTest {
    String baseDir = "BASE-DIR";
    String environment = "ENVIRONMENT";
    boolean force = true;

    @TempDir
    File buildDir;
    File buildFile;

    @BeforeEach
    void setUp() throws IOException {
        buildFile = Path.of(buildDir.getPath(), "build.gradle").toFile();
        assertTrue(buildFile.createNewFile());

        appendToGradleBuildFile(
            String.format(
                "plugins {\n"
                    + "    id 'java'\n"
                    + "    id 'com.github.boginw.mybatis-migrations'\n"
                    + "}\n"
                    + "migrations {\n"
                    + "    baseDir = new File('%s')\n"
                    + "    environment = '%s'\n"
                    + "    force = %s\n"
                    + "}\n"
                    + "repositories {\n"
                    + "    mavenCentral()\n"
                    + "}\n"
                    + "dependencies {\n"
                    + "    migrationsRuntime 'com.h2database:h2:1.4.200'\n"
                    + "    implementation 'org.codehaus.groovy:groovy:3.0.9'\n"
                    + "    migrationsRuntime 'org.codehaus.groovy:groovy-jsr223:3.0.9'\n"
                    + "}\n",
                baseDir, environment, force
            )
        );
    }

    @Test
    void whenBuildAndHasMigrationsExtension_expectSuccess() {
        BuildResult result = GradleRunner.create()
            .withProjectDir(buildDir)
            .withArguments("build")
            .withPluginClasspath()
            .build();

        result.getTasks().forEach(t -> assertNotEquals(TaskOutcome.FAILED, t.getOutcome()));
    }

    @Test
    void whenNewAndHasMigrationsExtension_expectBaseDirAndEnvironmentFileCreated() {
        BuildResult result = GradleRunner.create()
            .withProjectDir(buildDir)
            .withArguments(InitTask.TASK_NAME, "--debug")
            .withPluginClasspath()
            .build();

        result.getTasks().forEach(t -> assertEquals(TaskOutcome.SUCCESS, t.getOutcome()));

        File file = Path.of(buildDir.getPath(), baseDir, "environments", environment + ".properties").toFile();
        assertTrue(file.exists());
        assertTrue(file.isFile());
    }

    @Test
    void whenHelpIsRunWithNewTask_expectNameToBeIncluded() {
        BuildResult result = GradleRunner.create()
            .withProjectDir(buildDir)
            .withArguments("help", "--task", NewTask.TASK_NAME)
            .withPluginClasspath()
            .build();

        assertTrue(result.getOutput().contains("--name"));
    }

    @Test
    void whenEnvironmentOptionGiven_expectOptionToOverrideExtensionValue() {
        String newEnvironment = "OTHER-ENVIRONMENT";
        GradleRunner.create()
            .withProjectDir(buildDir)
            .withArguments(InitTask.TASK_NAME, "--env", newEnvironment)
            .withPluginClasspath()
            .build();

        File file = Path.of(
            buildDir.getPath(),
            baseDir,
            "environments",
            newEnvironment + ".properties"
        ).toFile();

        assertTrue(file.exists());
        assertTrue(file.isFile());
    }

    @Test
    void whenOutputOptionGivenAndTaskIsRun_expectOutputToBeWrittenToFile() throws IOException {
        File output = Path.of(buildDir.getPath(), "output.txt").toFile();

        GradleRunner.create()
            .withProjectDir(buildDir)
            .withArguments(InitTask.TASK_NAME, "--output", output.getPath())
            .withPluginClasspath()
            .build();

        assertTrue(output.exists());

        try (var stream = new FileInputStream(output)) {
            String outputContents = new String(stream.readAllBytes());
            assertTrue(outputContents.contains(buildDir.getPath()));
        }
    }

    @Test
    void whenHookAdded_expectScriptToRunOnHookEvent() throws IOException {
        GradleRunner.create()
            .withProjectDir(buildDir)
            .withArguments(InitTask.TASK_NAME)
            .withPluginClasspath()
            .build();

        File hooksDir = Path.of(buildDir.getPath(), baseDir, "hooks").toFile();
        assertTrue(hooksDir.mkdir());

        File hookFile = Path.of(hooksDir.getPath(), "test.groovy").toFile();

        String message = "Groovy script ran successfully.";
        String script = String.format("println \"%s.\"", message);

        appendToFile(hookFile, script);

        File environmentFile = Path.of(
            buildDir.getPath(),
            baseDir,
            "environments",
            environment + ".properties"
        ).toFile();

        appendToFile(environmentFile, "\nhook_before_new=groovy:test.groovy");

        BuildResult result = GradleRunner.create()
            .withProjectDir(buildDir)
            .withArguments(NewTask.TASK_NAME, "--name", "migration", "--stacktrace")
            .withPluginClasspath()
            .build();

        assertTrue(result.getOutput().contains(message));
    }

    protected void appendToGradleBuildFile(String append) throws IOException {
        appendToFile(buildFile, append);
    }

    protected void appendToFile(File file, String append) throws IOException {
        try (FileWriter writer = new FileWriter(file, true)) {
            writer.append(append);
        }
    }
}
