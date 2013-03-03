//    jDownloader - Downloadmanager
//    Copyright (C) 2008  JD-Team support@jdownloader.org
//
//    This program is free software: you can redistribute it and/or modify
//    it under the terms of the GNU General Public License as published by
//    the Free Software Foundation, either version 3 of the License, or
//    (at your option) any later version.
//
//    This program is distributed in the hope that it will be useful,
//    but WITHOUT ANY WARRANTY; without even the implied warranty of
//    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
//    GNU General Public License for more details.
//
//    You should have received a copy of the GNU General Public License
//    along with this program.  If not, see <http://www.gnu.org/licenses/>.

package jd.utils;

import java.io.File;

import jd.plugins.PluginForHost;

import org.appwork.utils.Application;
import org.jdownloader.logging.LogController;
import org.jdownloader.plugins.controller.UpdateRequiredClassNotFoundException;
import org.jdownloader.plugins.controller.host.HostPluginController;
import org.jdownloader.plugins.controller.host.LazyHostPlugin;

import com.castillo.JDownloader4AndroidActivity;

/**
 * @author astaldo/JD-Team
 */
public class JDUtilities {

    public static PluginForHost getPluginForHost(final String host) {
        LazyHostPlugin lplugin = HostPluginController.getInstance().get(host);
        if (lplugin != null) try {
            return lplugin.getPrototype(JDownloader4AndroidActivity.context.getClassLoader());
        } catch (UpdateRequiredClassNotFoundException e) {
            LogController.CL().log(e);
            return null;
        }
        return null;
    }    
    
    public static File getResourceFile(final String resource) {
        return Application.getResource(resource);

    }
    
    public static File getResourceFile(final String resource, final boolean mkdirs) {
        final File f = getResourceFile(resource);
        if (f != null) {
            if (mkdirs) {
                final File f2 = f.getParentFile();
                if (f2 != null && !f2.exists()) f2.mkdirs();
            }
            return f;
        }
        return null;
    }
    
    public static File getJDHomeDirectoryFromEnvironment() {
        return Application.getApplicationRoot();
    }
}