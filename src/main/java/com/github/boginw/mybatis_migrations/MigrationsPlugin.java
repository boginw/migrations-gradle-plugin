package com.github.boginw.mybatis_migrations;

import com.github.boginw.mybatis_migrations.task.*;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.Task;
import org.gradle.api.plugins.JavaBasePlugin;

import java.io.PrintStream;

public class MigrationsPlugin implements Plugin<Project> {
    private final CommandFactory commandFactory = new CommandFactory();
    private final ClassLoaderFactory classLoaderFactory = new ClassLoaderFactory();
    private final PrintStreamFactory printStreamFactory = PrintStream::new;

    @Override
    public void apply(Project project) {
        project.getPlugins().apply(JavaBasePlugin.class);
        project.getConfigurations().create("migrations");
        project.getDependencies().add("migrations", "org.mybatis:mybatis-migrations:3.3.10");

        project.getExtensions().create("migrations", MigrationsExtension.class);
        registerTask(project, InitTask.TASK_NAME, InitTask.class);
        registerTask(project, BootstrapTask.TASK_NAME, BootstrapTask.class);
        registerTask(project, StatusTask.TASK_NAME, StatusTask.class);
        registerTask(project, PendingTask.TASK_NAME, PendingTask.class);
        registerTask(project, NewTask.TASK_NAME, NewTask.class);
        registerTask(project, UpTask.TASK_NAME, UpTask.class);
        registerTask(project, DownTask.TASK_NAME, DownTask.class);
        registerTask(project, RedoTask.TASK_NAME, RedoTask.class);
        registerTask(project, VersionTask.TASK_NAME, VersionTask.class);
        registerTask(project, ScriptTask.TASK_NAME, ScriptTask.class);
    }

    private void registerTask(Project project, String taskName, Class<? extends Task> taskClass) {
        project.getTasks().create(
            taskName,
            taskClass,
            commandFactory,
            classLoaderFactory,
            printStreamFactory
        );
    }
}
