package org.mybatis.gradle.task;

import org.apache.ibatis.migration.commands.NewCommand;
import org.apache.ibatis.migration.options.SelectedOptions;
import org.gradle.api.tasks.TaskAction;
import org.gradle.api.tasks.options.Option;
import org.mybatis.gradle.ClassLoaderFactory;
import org.mybatis.gradle.CommandFactory;

import javax.inject.Inject;

public class NewTask extends MigrationsTask {
    public static final String TASK_NAME = TASK_PREFIX + "New";
    private String name;

    @Inject
    public NewTask(CommandFactory factory, ClassLoaderFactory classLoaderFactory) {
        super(factory, classLoaderFactory);
    }

    @Option(option = "name", description = "Name of the migration")
    public void setName(String name) {
        this.name = name;
    }

    @Override
    @TaskAction
    public void run() {
        SelectedOptions options = getSelectedOptions();
        NewCommand command = factory.create(NewCommand.class, options);
        command.setDriverClassLoader(classLoaderFactory.getClassLoader(getProject()));
        command.execute(name);
    }
}
