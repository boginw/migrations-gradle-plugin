package org.mybatis.gradle;

import org.gradle.testkit.runner.BuildResult;
import org.gradle.testkit.runner.GradleRunner;
import org.gradle.testkit.runner.TaskOutcome;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.mybatis.gradle.task.InitTask;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
                    id 'org.mybatis.gradle.migrations-gradle-plugin'
                }
                migrations {
                    baseDir = new File('%s')
                    environment = '%s'
                    force = %s
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

        result.getTasks().forEach(t -> assertEquals(t.getOutcome(), TaskOutcome.SUCCESS));
    }

    @Test
    void whenNewAndHasMigrationsExtension_expectBaseDirAndEnvironmentFileCreated() {
        BuildResult result = GradleRunner.create()
                .withProjectDir(buildDir)
                .withArguments(InitTask.TASK_NAME)
                .withPluginClasspath()
                .build();

        result.getTasks().forEach(t -> assertEquals(t.getOutcome(), TaskOutcome.SUCCESS));

        File file = Path.of(buildDir.getPath(), baseDir, "environments", environment + ".properties").toFile();
        assertTrue(file.exists());
        assertTrue(file.isFile());
    }

    protected void appendToGradleBuildFile(String append) throws IOException {
        try (FileWriter writer = new FileWriter(buildFile, true)) {
            writer.append(append);
        }
    }
}
