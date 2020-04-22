package de.traunviertler_traunwalchen.trachtenSheetGenerator.utility;

import org.apache.commons.lang3.SystemUtils;
import org.jetbrains.annotations.NotNull;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class SystemCommandUtility {
    private static final Logger LOGGER = Logger.getLogger(SystemCommandUtility.class.getName());
    private static final long COMMAND_TIMEOUT_VALUE = 30;
    private static final TimeUnit COMMAND_TIMEOUT_UNIT = TimeUnit.SECONDS;

    private SystemCommandUtility() {
    }

    public static boolean isCommandAvailable(@NotNull String command) {
        boolean isCommandAvailable;
        if (SystemUtils.IS_OS_WINDOWS) {
            try {
                isCommandAvailable = executePowerShellScript(stdOut -> stdOut.trim().equals("yes"),
                        (stdOut, stdErr) -> false, () -> false,
                        ResourceUtility.getResource("scripts/commandAvailable.ps1"), List.of("-cmdName", command));
            } catch (SystemCommandFailedException | FileNotFoundException ex) {
                LOGGER.log(Level.WARNING,
                        String.format("Can not determine whether command \"%s\" is available", command), ex);
                isCommandAvailable = false;
            }
        } else {
            throw new UnsupportedOperationException("This OS is not supported");
        }
        return isCommandAvailable;
    }

    /**
     * Return a path which is compatible as argument for latexmk.
     */
    @NotNull
    public static Optional<String> resolvePath(@NotNull Path path) {
        String resolvedPath;
        if (SystemUtils.IS_OS_WINDOWS) {
            try {
                resolvedPath = executePowerShellScript(String::trim, (stdOut, stdErr) -> null, () -> null,
                        ResourceUtility.getResource("scripts/getFullPath.ps1"), List.of("-path", path.toString()));
            } catch (SystemCommandFailedException | FileNotFoundException ex) {
                LOGGER.log(Level.WARNING, "Could not resolve path", ex);
                resolvedPath = null;
            }
        } else {
            throw new UnsupportedOperationException("This OS is not supported");
        }
        return Optional.ofNullable(resolvedPath);
    }

    @NotNull
    public static <T> T executePowerShellScript(@NotNull Function<String, T> onSuccess,
                                                @NotNull BiFunction<String, String, T> onFailure,
                                                @NotNull Supplier<T> onTimeout, @NotNull Path script,
                                                @NotNull List<String> params)
            throws SystemCommandFailedException {
        assert SystemUtils.IS_OS_WINDOWS : "PowerShell is Windows-only";
        List<String> powerShellCommand = new ArrayList<>();
        powerShellCommand.addAll(List.of("powershell.exe", "-File", script.normalize().toString()));
        powerShellCommand.addAll(params);
        return executeCommand(onSuccess, onFailure, onTimeout, powerShellCommand);
    }

    /**
     * @param onSuccess Called iff the exit code was zero and the command did not timeout. Accepts the output of stdout.
     * @param onFailure Called otherwise. Accepts the output of stdout and stderr as well as a flag marking whether
     *                  it timed out.
     */
    @NotNull
    public static <T> T executeCommand(@NotNull Function<String, T> onSuccess,
                                       @NotNull BiFunction<String, String, T> onFailure,
                                       @NotNull Supplier<T> onTimeout, @NotNull List<String> command)
            throws SystemCommandFailedException {
        try {
            Process process = new ProcessBuilder(command)
                    .start();
            boolean exited = process.waitFor(COMMAND_TIMEOUT_VALUE, COMMAND_TIMEOUT_UNIT);
            String stdOutResult = new String(process.getInputStream().readAllBytes());
            T commandResult;
            if (exited) {
                if (process.exitValue() == 0) {
                    commandResult = onSuccess.apply(stdOutResult);
                } else {
                    // NOTE There is explicitly no else-if for the purpose of semantically separating the conditions
                    String stdErrResult = new String(process.getErrorStream().readAllBytes());
                    commandResult = onFailure.apply(stdOutResult, stdErrResult);
                }
            } else {
                commandResult = onTimeout.get();
            }
            return commandResult;
        } catch (IOException | InterruptedException ex) {
            throw new SystemCommandFailedException(ex);
        }
    }
}
