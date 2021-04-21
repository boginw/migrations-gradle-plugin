package org.mybatis.gradle.task;

import org.apache.ibatis.migration.commands.StatusCommand;

class StatusTaskTest extends MigrationsTaskTest<StatusTask, StatusCommand> {

    @Override
    Class<StatusTask> getType() {
        return StatusTask.class;
    }

    @Override
    Class<StatusCommand> getMigrationsCommandType() {
        return StatusCommand.class;
    }
}
