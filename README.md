# MYBATIS Migrations

[![Java CI](https://github.com/boginw/migrations-gradle-plugin/actions/workflows/java.yml/badge.svg)](https://github.com/boginw/migrations-gradle-plugin/actions/workflows/java.yml)

Migration Gradle plugin is a Gradle plugin that integrates into the Gradle life cycle, the MyBatis 3 Migration tool.
MyBatis 3 Migration Schema is a tool that helps you to manage database schema changes.

## Getting Started

First off, make sure your project uses Java 11 or higher, as this is required. After that, we need to apply the plugin
to your project. The way we do this is with the `plugins` block:

```groovy
plugins {
    id 'com.github.boginw.mybatis-migrations' version '0.2.1'
}
```

After applying the plugin, all the same functionality of
the [MyBatis Migrations CLI tool](http://mybatis.org/migrations/index.html) are now available by using Gradle Tasks.

## Configuration

You might already have a setup going, or you want something custom. In this case, the plugin provides a configuration
block named `migrations`, which you can use to change the defaults of the tasks. The plugin allows configuring:

* `baseDir`: The base directory for which tasks are run. Default is `migrations`.
* `environment`: Which environment the tasks will use.
  See [MyBatis' documentation](http://mybatis.org/migrations/migrate.html). Default is `development`
* `force`: Whether to force script to continue even if SQL errors or warnings are encountered. Default is `false`.

An example of such a block is shown below:

```groovy
migrations {
    baseDir = new File('migrations')
    environment = "development"
    force = false
}
```

It is possible to override the configured environment by providing a new environment with the `--env` option,
i.e. `--env production`.

## Tasks

The plugin enables the same commands as available in
the [MyBatis Migrations CLI tool](http://mybatis.org/migrations/migrate.html) through Gradle. All tasks are prefixed
with `migrate` followed by the command name with the initial letter of the command capitalized, i.e., `migrateInit`,
for `init`. Gradle requires giving names to arguments; therefore, the arguments are not the same between the CLI tool
and this plugin. Below is a list of commands and their arguments.

* [`migrateInit`](http://mybatis.org/migrations/init.html): Initializes a new 'migration path', also called a '
  repository' (of migration scripts)
    * `--idPattern`: Change the default timestamp based file prefix to number sequence
* [`migrateBootstrap`](http://mybatis.org/migrations/bootstrap.html): Use the current state of the database schema as
  the starting point
* [`migrateNew`](http://mybatis.org/migrations/new.html): Generates the skeleton of a migration script that you can then
  simply fill in
    * `--name`: The name of the migration
* [`migrateStatus`](http://mybatis.org/migrations/status.html): Report the current state of the database
* [`migrateUp`](http://mybatis.org/migrations/updown.html): Runs the **do** section of all pending migrations in order,
  one after the other
    * `--steps`: The number of migrations that should be applied
* [`migrateDown`](http://mybatis.org/migrations/updown.html): Runs the **undo** section of the last applied migration
  only
    * `--steps`: The number of migrations that should be undone
* [`migrateVersion`](http://mybatis.org/migrations/version.html): Migrate the schema to any specific version of the
  database you like
    * `--version`: The version you like to migrate to
* [`migratePending`](http://mybatis.org/migrations/pending.html): Runs all pending migrations regardless of their order
  or position in the status log
* [`migrateScript`](http://mybatis.org/migrations/script.html): Generate a script that can migrate a schema from one
  version to another
