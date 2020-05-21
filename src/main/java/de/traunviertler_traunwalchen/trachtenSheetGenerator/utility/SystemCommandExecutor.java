package de.traunviertler_traunwalchen.trachtenSheetGenerator.utility;

import de.traunviertler_traunwalchen.trachtenSheetGenerator.generators.TempFileGenerator;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

public final class SystemCommandExecutor {

    private SystemCommandExecutor() {
    }

    private static String wrapSystemCommand(String command) {
        return String.format("stdout_result = check_output(\"%s\")", command);
    }

    @Nullable
    public static String resolveSystemPath(@NotNull String path) throws SystemCommandFailedException {
        Objects.requireNonNull(path);
        Iterable<String> commands = List.of(
                "from os.path import realpath",
                String.format("print(realpath(\"%s\"))", path)
        );
        return executePythonScript(commands, Function.identity(), (stdout, stderr) -> "not found", () -> "not found");
    }

    @Nullable
    public static <T> T executeSystemCommand(@NotNull String command, @Nullable Path workingDir,
                                             @NotNull Function<String, T> onSuccess,
                                             @NotNull BiFunction<String, String, T> onFailure,
                                             @NotNull Supplier<T> onTimeout) throws SystemCommandFailedException {
        if (workingDir == null) {
            try {
                workingDir = TempFileGenerator.createTempDir();
            } catch (IOException ex) {
                throw new SystemCommandFailedException("Could not create temporary working directory", ex);
            }
        }

        Iterable<String> commands = List.of(
                "from subprocess import check_output",
                wrapSystemCommand(String.format("cd \"%s\"", workingDir.toString())),
                wrapSystemCommand(command),
                "print(stdout_result.decode(\"utf8\"))"
        );
        return executePythonScript(commands, onSuccess, onFailure, onTimeout);
    }

    @Nullable
    public static <T> T executePythonScript(@NotNull Iterable<String> scriptLines,
                                            @NotNull Function<String, T> onSuccess,
                                            @NotNull BiFunction<String, String, T> onFailure,
                                            @NotNull Supplier<T> onTimeout) throws SystemCommandFailedException {
        try {
            Path workingDir = TempFileGenerator.createTempDir();
            PythonProcess process = PythonProcess.exec(null, workingDir.toFile());
            for (String line : scriptLines) {
                process.runCommand(line);
            }
            process.close();
            boolean exited = process.waitFor();
            T result;
            if (exited) {
                String stdOut = process.getStdOutResult();
                if (process.exitValue() == 0) {
                    result = onSuccess.apply(stdOut);
                } else {
                    String stdErr = process.getStdErrResult();
                    result = onFailure.apply(stdOut, stdErr);
                }
            } else {
                result = onTimeout.get();
            }
            return result;
        } catch (IOException | InterruptedException ex) {
            throw new SystemCommandFailedException(ex);
        }
    }

    private static class PythonProcess {
        private static final long COMMAND_TIMEOUT_VALUE = 30;
        private static final TimeUnit COMMAND_TIMEOUT_UNIT = TimeUnit.SECONDS;
        private final Process process;
        private String stdOutResult = null;
        private String stdErrResult = null;

        private PythonProcess(Process process) {
            this.process = process;
        }

        public boolean waitFor() throws InterruptedException {
            return process.waitFor(COMMAND_TIMEOUT_VALUE, COMMAND_TIMEOUT_UNIT);
        }

        /**
         * Add a level of escaping within quoted arguments of the command
         */
        private static String escapeCommand(String command) {
            return command.replaceAll("\\\\(?!\")", "/") // Replace \ in paths with /
                    .replaceAll("\\\\", "\\\\\\\\") // Replace \ with \\
                    .replaceAll("\"", "\\\\\""); // Replace " with \\"
        }

        public void runCommand(String command) throws SystemCommandFailedException {
            try {
                process.getOutputStream()
                        .write(escapeCommand(command + '\n').getBytes());
            } catch (IOException ex) {
                throw new SystemCommandFailedException("Could not execute Python command", ex);
            }
        }

        public void close() throws SystemCommandFailedException {
            runCommand("quit()");
            // FIXME How to make sure the output stream to the Python process is closed?
            try {
                process.getOutputStream()
                        .close();
            } catch (IOException ex) {
                throw new SystemCommandFailedException("Could not close output stream to Python process", ex);
            }
        }

        public String getStdOutResult() throws IOException {
            if (stdOutResult == null) {
                stdOutResult = new String(process.getInputStream().readAllBytes());
            }
            return stdOutResult;
        }

        public String getStdErrResult() throws IOException {
            if (stdErrResult == null) {
                stdErrResult = new String(process.getErrorStream().readAllBytes());
            }
            return stdErrResult;
        }

        public int exitValue() {
            return process.exitValue();
        }

        public static PythonProcess exec(String[] envp, File workingDir) throws IOException {
            Process process = Runtime.getRuntime()
                    .exec("python3", null, workingDir);
            return new PythonProcess(process);
        }
    }
}
