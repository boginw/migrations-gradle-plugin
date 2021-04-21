package org.mybatis.gradle;

import org.gradle.api.Project;
import org.gradle.testfixtures.ProjectBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mybatis.gradle.task.BootstrapTask;
import org.mybatis.gradle.task.InitTask;
import org.mybatis.gradle.task.PendingTask;
import org.mybatis.gradle.task.StatusTask;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class MigrationsPluginTest {
    private Project project;

    @BeforeEach
    void setUp() {
        project = ProjectBuilder.builder().build();
        project.getPlugins().apply(MigrationsPlugin.class);
    }

    @Test
    void expectMigrationsExtensionToBeRegistered() {
        assertNotNull(project.getExtensions().getByType(MigrationsExtension.class));
    }

    @Test
    void expectInitToBeRegistered() {
        assertNotNull(project.getTasks().withType(InitTask.class).getByName(InitTask.TASK_NAME));
    }

    @Test
    void expectBootstrapToBeRegistered() {
        assertNotNull(project.getTasks().withType(BootstrapTask.class).getByName(BootstrapTask.TASK_NAME));
    }

    @Test
    void expectStatusToBeRegistered() {
        assertNotNull(project.getTasks().withType(StatusTask.class).getByName(StatusTask.TASK_NAME));
    }

    @Test
    void expectPendingToBeRegistered() {
        assertNotNull(project.getTasks().withType(PendingTask.class).getByName(PendingTask.TASK_NAME));
    }
}
