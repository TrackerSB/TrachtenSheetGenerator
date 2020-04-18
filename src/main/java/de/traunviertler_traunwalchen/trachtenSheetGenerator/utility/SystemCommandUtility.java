package de.traunviertler_traunwalchen.trachtenSheetGenerator.utility;

import com.profesorfalken.jpowershell.PowerShell;
import com.profesorfalken.jpowershell.PowerShellNotAvailableException;
import com.profesorfalken.jpowershell.PowerShellResponse;
import org.apache.commons.lang3.SystemUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.util.Optional;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class SystemCommandUtility {
    private static final Logger LOGGER = Logger.getLogger(SystemCommandUtility.class.getName());

    private SystemCommandUtility() {
    }

    public static boolean isCommandAvailable(@NotNull String command) {
        if (SystemUtils.IS_OS_WINDOWS) {
            boolean isCommandAvailable;
            String params = "-cmdName " + command;
            try {
                PowerShellResponse result = executePowerShellScript("scripts/commandAvailable.ps1", params);
                isCommandAvailable = result.getCommandOutput().equalsIgnoreCase("yes");
            } catch (SystemCommandFailedException ex) {
                LOGGER.log(Level.WARNING, "Can not determine whether command \"" + command + "\" is available", ex);
                isCommandAvailable = false;
            }
            return isCommandAvailable;
        } else {
            throw new UnsupportedOperationException("OS not supported");
        }
    }

    @NotNull
    public static Optional<String> resolvePath(@NotNull Path path) {
        String resolvedPath;
        if (SystemUtils.IS_OS_WINDOWS) {
            try {
                resolvedPath = executePowerShellScript("scripts/getFullPath.ps1", "-path \"" + path.toString() + "\"")
                        .getCommandOutput();
            } catch (SystemCommandFailedException ex) {
                LOGGER.log(Level.WARNING, "Could not resolve path", ex);
                resolvedPath = null;
            }
        } else {
            throw new UnsupportedOperationException("OS not supported");
        }
        return Optional.ofNullable(resolvedPath);
    }

    public static void execute(@NotNull String command, @NotNull String params,
                               @Nullable Consumer<String> stdOutConsumer) throws SystemCommandFailedException {
        if (SystemUtils.IS_OS_WINDOWS) {
            PowerShellResponse response = executePowerShellCommand(command + " " + params);
            if (stdOutConsumer != null) {
                stdOutConsumer.accept(response.getCommandOutput());
            }
        } else {
            throw new UnsupportedOperationException("OS not supported");
        }
    }

    /////////////////////////
    // Windows OS specific //
    /////////////////////////
    private static PowerShellResponse executePowerShellScript(
            @NotNull String script, @NotNull String params) throws SystemCommandFailedException {
        assert SystemUtils.IS_OS_WINDOWS : "Tried to execute PowerShell command on non-Windows OS";
        try (BufferedReader scriptReader = new BufferedReader(new InputStreamReader(
                SystemCommandUtility.class.getResourceAsStream(script)))) {
            return PowerShellSessionProvider.runWithSession(ps -> ps.executeScript(scriptReader, params));
        } catch (InterruptedException ex) {
            throw new SystemCommandFailedException("Could not open Powershell", ex);
        } catch (IOException ex) {
            throw new SystemCommandFailedException("Could not execute script", ex);
        }
    }

    private static PowerShellResponse executePowerShellCommand(@NotNull String command)
            throws SystemCommandFailedException {
        assert SystemUtils.IS_OS_WINDOWS : "Tried to execute PowerShell command on non-Windows OS";
        try {
            return PowerShellSessionProvider.runWithSession(ps -> ps.executeCommand(command));
        } catch (InterruptedException ex) {
            throw new SystemCommandFailedException("Could not open Powershell", ex);
        }
    }

    private static class PowerShellSessionProvider {
        private static final int MAX_NUMBER_POWERSHELL_SESSIONS = 4;
        private static final BlockingDeque<PowerShell> availablePowerShellSessions
                = new LinkedBlockingDeque<>(MAX_NUMBER_POWERSHELL_SESSIONS) {
            {
                if (SystemUtils.IS_OS_WINDOWS) {
                    try {
                        for (int i = 0; i < MAX_NUMBER_POWERSHELL_SESSIONS; i++) {
                            push(PowerShell.openSession());
                        }
                    } catch (PowerShellNotAvailableException ex) {
                        throw new ExceptionInInitializerError(ex);
                    }
                    // FIXME Close all the sessions
                }
            }
        };

        private PowerShellSessionProvider() {
        }

        public static <T> T runWithSession(@NotNull Function<PowerShell, T> consumer) throws InterruptedException {
            assert SystemUtils.IS_OS_WINDOWS : "PowerShell is Windows-only";
            // FIXME Introduce timeout for taking
            PowerShell powerShell = availablePowerShellSessions.take();
            T result = consumer.apply(powerShell);
            availablePowerShellSessions.push(powerShell);
            return result;
        }
    }
}
