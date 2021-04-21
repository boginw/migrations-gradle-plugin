package org.mybatis.gradle.task;

import org.apache.ibatis.migration.commands.BootstrapCommand;

class BootstrapTaskTest extends MigrationsTaskTest<BootstrapTask, BootstrapCommand> {

    @Override
    Class<BootstrapTask> getType() {
        return BootstrapTask.class;
    }

    @Override
    Class<BootstrapCommand> getMigrationsCommandType() {
        return BootstrapCommand.class;
    }
}
