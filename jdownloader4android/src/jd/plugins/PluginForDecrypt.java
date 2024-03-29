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

package jd.plugins;

import java.util.ArrayList;
import java.util.regex.Pattern;

import jd.PluginWrapper;
import jd.config.SubConfiguration;
import jd.controlling.IOPermission;
import jd.controlling.ProgressController;
import jd.http.Browser;
import jd.nutils.encoding.Encoding;

import org.jdownloader.plugins.controller.crawler.LazyCrawlerPlugin;

/**
 * Dies ist die Oberklasse für alle Plugins, die Links entschlüsseln können
 * 
 * @author astaldo
 */
public abstract class PluginForDecrypt extends Plugin {

    private IOPermission           ioPermission = null;
    //private LinkCrawlerDistributer distributer  = null;

    private LazyCrawlerPlugin      lazyC        = null;
    //private CrawledLink            currentLink  = null;
    //private LinkCrawlerAbort       linkCrawlerAbort;

    /*public LinkCrawlerDistributer getDistributer() {
        return distributer;
    }*/

    @Override
    public SubConfiguration getPluginConfig() {
        return SubConfiguration.getConfig(lazyC.getDisplayName());
    }

    public Browser getBrowser() {
        return br;
    }

    /*public void setDistributer(LinkCrawlerDistributer distributer) {
        this.distributer = distributer;
    }*/

    /**
     * @return the ioPermission
     */
    public IOPermission getIOPermission() {
        return ioPermission;
    }

    /**
     * @param ioPermission
     *            the ioPermission to set
     */
    public void setIOPermission(IOPermission ioPermission) {
        this.ioPermission = ioPermission;
    }

    public PluginForDecrypt() {
    }

    public Pattern getSupportedLinks() {
        return lazyC.getPattern();
    }

    public String getHost() {
        return lazyC.getDisplayName();
    }

    @Deprecated
    public PluginForDecrypt(PluginWrapper wrapper) {
        super(wrapper);
        this.lazyC = (LazyCrawlerPlugin) wrapper.getLazy();
    }

    public void setBrowser(Browser br) {
        this.br = br;
    }

    @Override
    public long getVersion() {
        return lazyC.getVersion();
    }

    public void sleep(long i, CryptedLink link) throws InterruptedException {
        while (i > 0) {
            i -= 1000;
            synchronized (this) {
                this.wait(1000);
            }
        }
    }

    /**
     * return how many Instances of this PluginForDecrypt may crawl concurrently
     * 
     * @return
     */
    public int getMaxConcurrentProcessingInstances() {
        return Integer.MAX_VALUE;
    }

    /**
     * Diese Methode entschlüsselt Links.
     * 
     * @param cryptedLinks
     *            Ein Vector, mit jeweils einem verschlüsseltem Link. Die einzelnen verschlüsselten Links werden aufgrund des Patterns
     *            {@link jd.plugins.Plugin#getSupportedLinks() getSupportedLinks()} herausgefiltert
     * @return Ein Vector mit Klartext-links
     */

    protected DownloadLink createDownloadlink(String link) {
        return new DownloadLink(null, null, getHost(), Encoding.urlDecode(link, true), true);
    }

    /**
     * Die Methode entschlüsselt einen einzelnen Link.
     */
    public abstract ArrayList<DownloadLink> decryptIt(CryptedLink parameter, ProgressController progress) throws Exception;

    /*public ArrayList<DownloadLink> decryptLink(CrawledLink source) {
        CryptedLink cryptLink = source.getCryptedLink();
        if (cryptLink == null) return null;
        ProgressController progress = new ProgressController();
        cryptLink.setProgressController(progress);
        ArrayList<DownloadLink> tmpLinks = null;
        boolean showException = true;
        Throwable exception = null;
        try {
            this.currentLink = source;
            // we now lets log into plugin specific loggers with all verbose/debug on            
            br.setLogger(logger);
            br.setVerbose(true);
            br.setDebug(true);
            // now we let the decrypter do its magic 
            tmpLinks = decryptIt(cryptLink, progress);
        } catch (DecrypterException e) {
            if (DecrypterException.CAPTCHA.equals(e.getMessage())) {
                showException = false;
            } else if (DecrypterException.PASSWORD.equals(e.getMessage())) {
                showException = false;
            } else if (DecrypterException.ACCOUNT.equals(e.getMessage())) {
                showException = false;
            }
            // we got a decrypter exception, clear log and note that something went wrong             
            if (logger instanceof LogSource) {
                // make sure we use the right logger 
                ((LogSource) logger).clear();
            }
            LogSource.exception(logger, e);
        } catch (InterruptedException e) {
            // plugin got interrupted, clear log and note what happened 
            if (logger instanceof LogSource) {
                // make sure we use the right logger 
                ((LogSource) logger).clear();
                ((LogSource) logger).log(e);
            } else {
                LogSource.exception(logger, e);
            }
        } catch (Throwable e) {
            // damn, something must have gone really really bad, lets keep the log            
            exception = e;
            LogSource.exception(logger, e);
        } finally {
            this.currentLink = null;
        }
        if (tmpLinks == null && showException) {
            // null as return value? something must have happened, do not clear log             
            errLog(exception, br, source);
            logger.severe("CrawlerPlugin out of date: " + this + " :" + getVersion());
            logger.severe("URL was: " + source.getURL());

            // lets forward the log 
            if (logger instanceof LogSource) {
                // make sure we use the right logger 
                ((LogSource) logger).flush();
            }
        }
        if (logger instanceof LogSource) {
            // make sure we use the right logger 
            ((LogSource) logger).clear();
        }
        return tmpLinks;
    }*/

    /*public void errLog(Throwable e, Browser br, CrawledLink link) {
        LogSource errlogger = LogController.getInstance().getLogger("PluginErrors");
        try {
            errlogger.severe("CrawlerPlugin out of date: " + this + " :" + getVersion());
            errlogger.severe("URL was: " + link.getURL());
            if (e != null) errlogger.log(e);
        } finally {
            errlogger.close();
        }
    }

    public CrawledLink getCurrentLink() {
        return currentLink;
    }

    protected void distribute(DownloadLink... links) {
        LinkCrawlerDistributer dist = distributer;
        if (dist == null || links == null || links.length == 0) return;
        dist.distribute(links);
    }*/

    public int getDistributeDelayerMinimum() {
        return 1000;
    }

    public int getDistributeDelayerMaximum() {
        return 5000;
    }

    /*protected String getCaptchaCode(String captchaAddress, CryptedLink param) throws IOException, DecrypterException {
        return getCaptchaCode(getHost(), captchaAddress, param);
    }

    protected String getCaptchaCode(LoadImage li, CryptedLink param) throws IOException, DecrypterException {
        return getCaptchaCode(getHost(), li.file, param);
    }

    protected String getCaptchaCode(String method, String captchaAddress, CryptedLink param) throws IOException, DecrypterException {
        if (captchaAddress == null) {
            logger.severe("Captcha Adresse nicht definiert");
            throw new DecrypterException(DecrypterException.CAPTCHA);
        }
        File captchaFile = this.getLocalCaptchaFile();
        try {
            Browser brc = br.cloneBrowser();
            try {
                brc.getDownload(captchaFile, captchaAddress);
            } catch (Exception e) {
                logger.severe("Captcha Download fehlgeschlagen: " + captchaAddress);
                throw new DecrypterException(DecrypterException.CAPTCHA);
            }
            // erst im Nachhinein das der Bilddownload nicht gestört wird
            String captchaCode = getCaptchaCode(method, captchaFile, param);
            return captchaCode;
        } finally {
            if (captchaFile != null) captchaFile.delete();
        }
    }

    protected String getCaptchaCode(File captchaFile, CryptedLink param) throws DecrypterException {
        return getCaptchaCode(getHost(), captchaFile, param);
    }

    protected String getCaptchaCode(String methodname, File captchaFile, CryptedLink param) throws DecrypterException {
        return getCaptchaCode(methodname, captchaFile, 0, param, null, null);
    }

    protected String getCaptchaCode(String method, File file, int flag, CryptedLink link, String defaultValue, String explain) throws DecrypterException {
        CaptchaResult suggest = new CaptchaResult();
        suggest.setCaptchaText(defaultValue);
        String orgCaptchaImage = link.getStringProperty("orgCaptchaFile", null);
        ArrayList<File> captchaFiles = new ArrayList<File>();
        if (orgCaptchaImage != null && new File(orgCaptchaImage).exists()) {
            captchaFiles.add(new File(orgCaptchaImage));
        }
        captchaFiles.add(file);
        CaptchaResult cc = new CaptchaController(ioPermission, method, captchaFiles, suggest, explain, this).getCode(flag);
        if (cc == null) throw new DecrypterException(DecrypterException.CAPTCHA);
        return cc.getCaptchaText();
    }*/

    protected void setBrowserExclusive() {
        if (br == null) return;
        br.setCookiesExclusive(true);
        br.clearCookies(getHost());
    }

    /**
     * @param lazyC
     *            the lazyC to set
     */
    public void setLazyC(LazyCrawlerPlugin lazyC) {
        this.lazyC = lazyC;
    }

    /**
     * @return the lazyC
     */
    public LazyCrawlerPlugin getLazyC() {
        return lazyC;
    }

    /**
     * Can be overridden to show the current status for example in captcha dialog
     * 
     * @return
     */
    public String getCrawlerStatusString() {
        return null;
    }

    /*public void setLinkCrawlerAbort(LinkCrawlerAbort linkCrawlerAbort) {
        this.linkCrawlerAbort = linkCrawlerAbort;
    }

    public boolean isAbort() {
        LinkCrawlerAbort llinkCrawlerAbort = linkCrawlerAbort;
        if (llinkCrawlerAbort != null) return llinkCrawlerAbort.isAbort();
        return Thread.currentThread().isInterrupted();
    }*/

}