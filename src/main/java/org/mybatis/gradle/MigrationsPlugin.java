package org.mybatis.gradle;

import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.mybatis.gradle.task.*;

public class MigrationsPlugin implements Plugin<Project> {

    @Override
    public void apply(Project project) {
        CommandFactory factory = new CommandFactory();
        project.getExtensions().create("migrations", MigrationsExtension.class);
        project.getTasks().create(InitTask.TASK_NAME, InitTask.class, factory);
        project.getTasks().create(BootstrapTask.TASK_NAME, BootstrapTask.class, factory);
        project.getTasks().create(StatusTask.TASK_NAME, StatusTask.class, factory);
        project.getTasks().create(PendingTask.TASK_NAME, PendingTask.class, factory);
        project.getTasks().create(NewTask.TASK_NAME, NewTask.class, factory);
        project.getTasks().create(UpTask.TASK_NAME, UpTask.class, factory);
        project.getTasks().create(DownTask.TASK_NAME, DownTask.class, factory);
        project.getTasks().create(VersionTask.TASK_NAME, VersionTask.class, factory);
        project.getTasks().create(ScriptTask.TASK_NAME, ScriptTask.class, factory);
    }
}
