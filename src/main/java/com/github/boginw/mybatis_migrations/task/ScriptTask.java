package com.github.boginw.mybatis_migrations.task;

import org.apache.ibatis.migration.commands.ScriptCommand;
import org.apache.ibatis.migration.options.SelectedOptions;
import org.gradle.api.tasks.TaskAction;
import org.gradle.api.tasks.options.Option;
import com.github.boginw.mybatis_migrations.ClassLoaderFactory;
import com.github.boginw.mybatis_migrations.CommandFactory;

import javax.inject.Inject;

public class ScriptTask extends MigrationsTask {
    public static final String TASK_NAME = TASK_PREFIX + "Script";
    private String from;
    private String to;

    @Inject
    public ScriptTask(CommandFactory factory, ClassLoaderFactory classLoaderFactory) {
        super(factory, classLoaderFactory);
    }

    @Option(option = "from", description = "Version to generate from")
    public void setFrom(String from) {
        this.from = from;
    }

    @Option(option = "to", description = "Version to generate to")
    public void setTo(String to) {
        this.to = to;
    }

    @Override
    @TaskAction
    public void run() {
        SelectedOptions options = getSelectedOptions();
        ScriptCommand command = factory.create(ScriptCommand.class, options);
        command.setDriverClassLoader(classLoaderFactory.getClassLoader(getProject()));
        if (to != null) {
            command.execute(from, to);
        } else {
            command.execute(from);
        }
    }
}
