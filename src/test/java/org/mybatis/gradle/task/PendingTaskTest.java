package org.mybatis.gradle.task;

import org.apache.ibatis.migration.commands.PendingCommand;

class PendingTaskTest extends MigrationsTaskTest<PendingTask, PendingCommand> {

    @Override
    Class<PendingTask> getType() {
        return PendingTask.class;
    }

    @Override
    Class<PendingCommand> getMigrationsCommandType() {
        return PendingCommand.class;
    }
}
