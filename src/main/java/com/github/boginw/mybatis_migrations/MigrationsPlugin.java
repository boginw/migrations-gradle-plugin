package com.github.boginw.mybatis_migrations;

import com.github.boginw.mybatis_migrations.task.*;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.plugins.JavaBasePlugin;

public class MigrationsPlugin implements Plugin<Project> {

    @Override
    public void apply(Project project) {
        project.getPlugins().apply(JavaBasePlugin.class);
        project.getConfigurations().create("migrations");
        project.getDependencies().add("migrations", "org.mybatis:mybatis-migrations:3.3.10");

        CommandFactory commandFactory = new CommandFactory();
        ClassLoaderFactory classLoaderFactory = new ClassLoaderFactory();
        project.getExtensions().create("migrations", MigrationsExtension.class);
        project.getTasks().create(InitTask.TASK_NAME, InitTask.class, commandFactory, classLoaderFactory);
        project.getTasks().create(BootstrapTask.TASK_NAME, BootstrapTask.class, commandFactory, classLoaderFactory);
        project.getTasks().create(StatusTask.TASK_NAME, StatusTask.class, commandFactory, classLoaderFactory);
        project.getTasks().create(PendingTask.TASK_NAME, PendingTask.class, commandFactory, classLoaderFactory);
        project.getTasks().create(NewTask.TASK_NAME, NewTask.class, commandFactory, classLoaderFactory);
        project.getTasks().create(UpTask.TASK_NAME, UpTask.class, commandFactory, classLoaderFactory);
        project.getTasks().create(DownTask.TASK_NAME, DownTask.class, commandFactory, classLoaderFactory);
        project.getTasks().create(RedoTask.TASK_NAME, RedoTask.class, commandFactory, classLoaderFactory);
        project.getTasks().create(VersionTask.TASK_NAME, VersionTask.class, commandFactory, classLoaderFactory);
        project.getTasks().create(ScriptTask.TASK_NAME, ScriptTask.class, commandFactory, classLoaderFactory);
    }
}
