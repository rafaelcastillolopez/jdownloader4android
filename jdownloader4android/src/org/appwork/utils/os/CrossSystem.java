/**
 * Copyright (c) 2009 - 2010 AppWork UG(haftungsbeschränkt) <e-mail@appwork.org>
 * 
 * This file is part of org.appwork.utils.os
 * 
 * This software is licensed under the Artistic License 2.0,
 * see the LICENSE file or http://www.opensource.org/licenses/artistic-license-2.0.php
 * for details
 */
package org.appwork.utils.os;

import org.appwork.utils.Regex;
import org.appwork.utils.StringUtils;

/**
 * This class provides a few native features.
 * 
 * @author $Author: unknown$
 */

public class CrossSystem {

    /**
     * splits filename into name,extension
     * 
     * @param filename
     * @return
     */
    public static String[] splitFileName(final String filename) {
        final String extension = new Regex(filename, "\\.+([^\\.]*$)").getMatch(0);
        final String name = new Regex(filename, "(.*?)(\\.+[^\\.]*$|$)").getMatch(0);
        return new String[] { name, extension };
    }
    
    /**
     * use this method to make pathPart safe to use in a full absoluePath.
     * 
     * it will remove driveletters/path seperators and all known chars that are
     * forbidden in a path
     * 
     * @param pathPart
     * @return
     */
    public static String alleviatePathParts(String pathPart) {
        if (StringUtils.isEmpty(pathPart)) {
            if (pathPart != null) { return pathPart; }
            return null;
        }
        /* remove invalid chars */
        pathPart = pathPart.replaceAll("([\\\\|<|>|\\||\"|:|\\*|\\?|/|\\x00])+", "_");
        pathPart = pathPart.replaceFirst("\\.+$", "");
        return pathPart.trim();
    }
    
    public static boolean isAbsolutePath(final String path) {
        if (StringUtils.isEmpty(path)) { return false; }
        if (path.startsWith("/")) { return true; }
        return false;
    }
}