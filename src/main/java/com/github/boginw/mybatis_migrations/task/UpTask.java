package com.github.boginw.mybatis_migrations.task;

import org.apache.ibatis.migration.commands.UpCommand;
import org.apache.ibatis.migration.options.SelectedOptions;
import org.gradle.api.tasks.TaskAction;
import org.gradle.api.tasks.options.Option;
import com.github.boginw.mybatis_migrations.ClassLoaderFactory;
import com.github.boginw.mybatis_migrations.CommandFactory;

import javax.inject.Inject;

public class UpTask extends MigrationsTask {
    public static final String TASK_NAME = TASK_PREFIX + "Up";
    private String steps;

    @Inject
    public UpTask(CommandFactory factory, ClassLoaderFactory classLoaderFactory) {
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
        UpCommand command = factory.create(UpCommand.class, options);
        command.setDriverClassLoader(classLoaderFactory.getClassLoader(getProject()));
        command.execute(steps);
    }
}
