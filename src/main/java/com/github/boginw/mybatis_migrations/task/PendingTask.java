package com.github.boginw.mybatis_migrations.task;

import com.github.boginw.mybatis_migrations.ClassLoaderFactory;
import com.github.boginw.mybatis_migrations.CommandFactory;
import org.apache.ibatis.migration.commands.PendingCommand;
import org.apache.ibatis.migration.options.SelectedOptions;
import org.gradle.api.tasks.TaskAction;

import javax.inject.Inject;

public class PendingTask extends MigrationsTask {
    public static final String TASK_NAME = TASK_PREFIX + "Pending";

    @Inject
    public PendingTask(CommandFactory factory, ClassLoaderFactory classLoaderFactory) {
        super(factory, classLoaderFactory);
    }

    @Override
    @TaskAction
    public void run() {
        SelectedOptions options = getSelectedOptions();
        PendingCommand command = factory.create(PendingCommand.class, options);
        command.setDriverClassLoader(classLoaderFactory.getClassLoader(getProject()));
        command.execute();
    }
}
