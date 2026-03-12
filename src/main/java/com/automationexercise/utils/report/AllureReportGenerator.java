package com.automationexercise.utils.report;

import com.automationexercise.utils.OSUtils;
import com.automationexercise.utils.TerminalUtils;
import com.automationexercise.utils.TimeManager;
import com.automationexercise.utils.logs.LogsManager;
import org.apache.commons.io.FileUtils;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static com.automationexercices.utils.report.AllureConstants.HISTORY_FOLDER;
import static com.automationexercices.utils.report.AllureConstants.RESULTS_HISTORY_FOLDER;
import static com.automationexercise.utils.dataReader.PropertyReader.getProperty;

public class AllureReportGenerator {
    //Generate Allure report
    //--single-file - generate single file report
    public static void generateReports(boolean isSingleFile) {
        Path outputFolder = isSingleFile ? com.automationexercices.utils.report.AllureConstants.REPORT_PATH : com.automationexercices.utils.report.AllureConstants.FULL_REPORT_PATH;
        // allure generate -o reports --single-file --clean
        List<String> command = new ArrayList<>(List.of(
                AllureBinaryManager.getExecutable().toString(),
                "generate",
                com.automationexercices.utils.report.AllureConstants.RESULTS_FOLDER.toString(),
                "-o", outputFolder.toString(),
                "--clean"
        ));
        if (isSingleFile) command.add("--single-file");
        TerminalUtils.executeTerminalCommand(command.toArray(new String[0]));
    }

    //rename report file to AllureReport_timestamp.html
    public static String renameReport() {
        String newFileName = com.automationexercices.utils.report.AllureConstants.REPORT_PREFIX + TimeManager.getTimestamp() + com.automationexercices.utils.report.AllureConstants.REPORT_EXTENSION; // AllureReport_20250720_211230.html
        com.automationexercise.FileUtils.renameFile(com.automationexercices.utils.report.AllureConstants.REPORT_PATH.resolve(com.automationexercices.utils.report.AllureConstants.INDEX_HTML).toString(), newFileName);
        return newFileName;
    }

    //open Allure report in browser
    public static void openReport(String reportFileName) {
        if (!getProperty("executionType").toLowerCase().contains("local")) return;

        Path reportPath = com.automationexercices.utils.report.AllureConstants.REPORT_PATH.resolve(reportFileName);
        switch (OSUtils.getCurrentOS()) {
            case WINDOWS -> TerminalUtils.executeTerminalCommand("cmd.exe", "/c", "start", reportPath.toString());
            case MAC, LINUX -> TerminalUtils.executeTerminalCommand("open", reportPath.toString());
            default -> LogsManager.warn("Opening Allure Report is not supported on this OS.");
        }
    }

    //copy history folder to results folder
    public static void copyHistory() {
        try {
            FileUtils.copyDirectory(HISTORY_FOLDER.toFile(), RESULTS_HISTORY_FOLDER.toFile());
        } catch (Exception e) {
            LogsManager.error("Error copying history files", e.getMessage());
        }
    }
}
