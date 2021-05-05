package com.github.boginw.mybatis_migrations;

import org.gradle.testkit.runner.BuildResult;
import org.gradle.testkit.runner.GradleRunner;
import org.gradle.testkit.runner.TaskOutcome;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import com.github.boginw.mybatis_migrations.task.InitTask;
import com.github.boginw.mybatis_migrations.task.NewTask;

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

        appendToGradleBuildFile("""
                plugins {
                    id 'java'
                    id 'com.github.boginw.mybatis-migrations'
                }
                migrations {
                    baseDir = new File('%s')
                    environment = '%s'
                    force = %s
                }
                repositories {
                        mavenCentral()
                }
                dependencies {
                    implementation 'com.h2database:h2:1.4.200'
                }
                """.formatted(baseDir, environment, force)
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

    protected void appendToGradleBuildFile(String append) throws IOException {
        try (FileWriter writer = new FileWriter(buildFile, true)) {
            writer.append(append);
        }
    }
}
