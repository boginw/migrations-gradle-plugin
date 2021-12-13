package com.github.boginw.mybatis_migrations.task;

import com.github.boginw.mybatis_migrations.ClassLoaderFactory;
import com.github.boginw.mybatis_migrations.CommandFactory;
import org.apache.ibatis.migration.commands.RedoCommand;
import org.apache.ibatis.migration.options.SelectedOptions;
import org.gradle.api.tasks.TaskAction;
import org.gradle.api.tasks.options.Option;

import javax.inject.Inject;

public class RedoTask extends MigrationsTask {
    public static final String TASK_NAME = TASK_PREFIX + "Redo";
    private String steps;

    @Inject
    public RedoTask(CommandFactory factory, ClassLoaderFactory classLoaderFactory) {
        super(factory, classLoaderFactory);
    }

    @Option(option = "steps", description = "Number of steps")
    public void setSteps(String steps) {
        this.steps = steps;
    }

    @Override
    @TaskAction
    public void run() {
        SelectedOptions options = getSelectedOptions();
        RedoCommand command = factory.create(RedoCommand.class, options);
        command.setDriverClassLoader(classLoaderFactory.getClassLoader(getProject()));
        command.execute(steps);
    }
}
