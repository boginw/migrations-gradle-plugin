package com.github.boginw.mybatis_migrations.task;

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
