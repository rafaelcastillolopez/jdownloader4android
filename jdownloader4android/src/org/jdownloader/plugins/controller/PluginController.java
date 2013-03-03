package org.jdownloader.plugins.controller;

import java.util.ArrayList;
import java.util.Enumeration;

import jd.plugins.Plugin;

import org.appwork.utils.logging2.LogSource;
import org.jdownloader.logging.LogController;

import android.content.pm.ApplicationInfo;

import com.castillo.JDownloader4AndroidActivity;

import dalvik.system.DexFile;

public class PluginController<T extends Plugin> {
	
	@SuppressWarnings("unchecked")
    public java.util.List<PluginInfo<T>> scan(String hosterpath) {
        boolean ownLogger = false;
        LogSource logger = LogController.getInstance().getCurrentClassLogger();
        if (logger == null) {
            ownLogger = true;
            logger = LogController.CL();
            logger.setAllowTimeoutFlush(false);
        }
        final java.util.List<PluginInfo<T>> ret = new ArrayList<PluginInfo<T>>();
        try {
            /*File path = null;
            PluginClassLoaderChild cl = null;

            path = Application.getRootByClass(jd.Launcher.class, hosterpath);

            cl = PluginClassLoader.getInstance().getChild();

            final File[] files = path.listFiles(new FilenameFilter() {
                public boolean accept(final File dir, final String name) {
                    return name.endsWith(".class") && !name.contains("$");
                }
            });
            
            final String pkg = hosterpath.replace("/", ".");
            boolean errorFree = true;
            if (files != null) {
                for (final File f : files) {
                    try {
                        String classFileName = f.getName().substring(0, f.getName().length() - 6);
                        ret.add(new PluginInfo<T>(f, (Class<T>) cl.loadClass(pkg + "." + classFileName)));
                        logger.finer("Loaded from: " + new String("" + cl.getResource(hosterpath + "/" + f.getName())));
                    } catch (Throwable e) {
                        errorFree = false;
                        logger.log(e);
                    }
                }
            }
            if (errorFree && ownLogger) logger.clear();*/
            
        	ApplicationInfo ai = JDownloader4AndroidActivity.context.getApplicationInfo();
            String classPath = ai.sourceDir;
            DexFile dex = null;            
            dex = new DexFile(classPath);
            Enumeration<String> apkClassNames = dex.entries();
            while (apkClassNames.hasMoreElements()) {
                String className = apkClassNames.nextElement();
                if (className.startsWith(hosterpath.replace("/", ".")))
                {
	                // (4) Load the class
	                Class<?> entryClass = JDownloader4AndroidActivity.context.getClassLoader().loadClass(className);	                
	                ret.add(new PluginInfo<T>(className, (Class<T>) entryClass));
                }
            }                       
        }
        catch (Exception e)
        {
        	e.printStackTrace();
        }
        finally {
            if (ownLogger) logger.close();
        }
        return ret;
    }
}
