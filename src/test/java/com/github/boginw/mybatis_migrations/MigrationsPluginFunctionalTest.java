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
                    + "    implementation 'com.h2database:h2:1.4.200'\n"
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

        result.getTasks().forEach(t -> assertNotEquals(t.getOutcome(), TaskOutcome.FAILED));
    }

    @Test
    void whenNewAndHasMigrationsExtension_expectBaseDirAndEnvironmentFileCreated() {
        BuildResult result = GradleRunner.create()
            .withProjectDir(buildDir)
            .withArguments(InitTask.TASK_NAME, "--debug")
            .withPluginClasspath()
            .build();

        result.getTasks().forEach(t -> assertEquals(t.getOutcome(), TaskOutcome.SUCCESS));

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

    protected void appendToGradleBuildFile(String append) throws IOException {
        try (FileWriter writer = new FileWriter(buildFile, true)) {
            writer.append(append);
        }
    }
}
