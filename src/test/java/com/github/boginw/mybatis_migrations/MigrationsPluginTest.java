package com.github.boginw.mybatis_migrations;

import com.github.boginw.mybatis_migrations.task.*;
import org.gradle.api.Project;
import org.gradle.testfixtures.ProjectBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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

    @Test
    void expectNewToBeRegistered() {
        assertNotNull(project.getTasks().withType(NewTask.class).getByName(NewTask.TASK_NAME));
    }

    @Test
    void expectUpToBeRegistered() {
        assertNotNull(project.getTasks().withType(UpTask.class).getByName(UpTask.TASK_NAME));
    }

    @Test
    void expectDownToBeRegistered() {
        assertNotNull(project.getTasks().withType(DownTask.class).getByName(DownTask.TASK_NAME));
    }

    @Test
    void expectRedoToBeRegistered() {
        assertNotNull(project.getTasks().withType(RedoTask.class).getByName(RedoTask.TASK_NAME));
    }

    @Test
    void expectVersionToBeRegistered() {
        assertNotNull(project.getTasks().withType(VersionTask.class).getByName(VersionTask.TASK_NAME));
    }

    @Test
    void expectScriptToBeRegistered() {
        assertNotNull(project.getTasks().withType(ScriptTask.class).getByName(ScriptTask.TASK_NAME));
    }
}
