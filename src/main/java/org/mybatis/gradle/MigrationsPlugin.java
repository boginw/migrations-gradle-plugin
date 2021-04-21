package org.mybatis.gradle;

import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.mybatis.gradle.task.BootstrapTask;
import org.mybatis.gradle.task.InitTask;
import org.mybatis.gradle.task.PendingTask;
import org.mybatis.gradle.task.StatusTask;

public class MigrationsPlugin implements Plugin<Project> {

    @Override
    public void apply(Project project) {
        var factory = new CommandFactory();
        project.getExtensions().create("migrations", MigrationsExtension.class);
        project.getTasks().create(InitTask.TASK_NAME, InitTask.class, factory);
        project.getTasks().create(BootstrapTask.TASK_NAME, BootstrapTask.class, factory);
        project.getTasks().create(StatusTask.TASK_NAME, StatusTask.class, factory);
        project.getTasks().create(PendingTask.TASK_NAME, PendingTask.class, factory);
    }
}
