package com.github.boginw.mybatis_migrations;

import java.io.FileNotFoundException;
import java.io.PrintStream;

public interface PrintStreamFactory {
    PrintStream makeFromFileName(String fileName) throws FileNotFoundException;
}
