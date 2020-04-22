package de.traunviertler_traunwalchen.trachtenSheetGenerator.utility;

import java.io.FileNotFoundException;
import java.lang.StackWalker.Option;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;

public class ResourceUtility {

    public static final StackWalker STACK_WALKER = StackWalker.getInstance(Option.RETAIN_CLASS_REFERENCE);

    private ResourceUtility() {
    }

    /**
     * Return a resource which is either absolute or relative to the callers class.
     *
     * @see Class#getResource(String)
     */
    public static Path getResource(String resourcePath) throws FileNotFoundException {
        URL foundResource = STACK_WALKER.getCallerClass()
                .getResource(resourcePath);
        if (foundResource == null) {
            throw new FileNotFoundException("Could not find or access resource");
        } else {
            try {
                return Path.of(foundResource.toURI());
            } catch (URISyntaxException ex) {
                throw new IllegalArgumentException("The given resource path is invalid", ex);
            }
        }
    }
}
