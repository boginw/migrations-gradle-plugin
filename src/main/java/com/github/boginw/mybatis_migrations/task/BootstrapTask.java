package com.github.boginw.mybatis_migrations.task;

import com.github.boginw.mybatis_migrations.ClassLoaderFactory;
import com.github.boginw.mybatis_migrations.CommandFactory;
import org.apache.ibatis.migration.commands.BootstrapCommand;
import org.apache.ibatis.migration.options.SelectedOptions;
import org.gradle.api.tasks.TaskAction;

import javax.inject.Inject;

public class BootstrapTask extends MigrationsTask {
    public static final String TASK_NAME = TASK_PREFIX + "Bootstrap";

    @Inject
    public BootstrapTask(CommandFactory factory, ClassLoaderFactory classLoaderFactory) {
        super(factory, classLoaderFactory);
    }

    @Override
    @TaskAction
    public void run() {
        SelectedOptions options = getSelectedOptions();
        BootstrapCommand command = factory.create(BootstrapCommand.class, options);
        command.setDriverClassLoader(classLoaderFactory.getClassLoader(getProject()));
        command.execute();
    }
}
