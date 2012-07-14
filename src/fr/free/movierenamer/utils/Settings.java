/*
 * Movie Renamer
 * Copyright (C) 2012 Nicolas Magré
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package fr.free.movierenamer.utils;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.UIManager;

/**
 * Class Settings , Movie Renamer settings
 *
 * @author Nicolas Magré
 */
public class Settings implements Cloneable {

  public static final String APPNAME = "Movie Renamer";
  private final String version = Utils.getRbTok("apps.version");
  private static final String userPath = System.getProperty("user.home");
  private final String apkMdb = "BQRjATHjATV3Zwx2AwWxLGOvLwEwZ2WwZQWyBGyvMQx=";
  private final String apkTdb = "DmIOExH5DwV1AwZkZRZ3Zt==";
  private final static String movieRenamerFolder = Utils.isWindows() ? "Movie_Renamer" : ".Movie_Renamer";
  public static final String mrFolder = userPath + File.separator + movieRenamerFolder;
  //Cache
  public Cache cache;
  public final String cacheDir = userPath + File.separator + movieRenamerFolder + File.separator + "cache" + File.separator;
  public final String imageCacheDir = cacheDir + "images" + File.separator;
  public final String thumbCacheDir = imageCacheDir + "thumbnails" + File.separator;
  public final String fanartCacheDir = imageCacheDir + "fanarts" + File.separator;
  public final String actorCacheDir = imageCacheDir + "actors" + File.separator;
  public final String xmlCacheDir = cacheDir + "XML" + File.separator;
  public final String tvshowZipCacheDir = cacheDir + "Zip" + File.separator;
  //Files
  public final String configFile = userPath + File.separator + movieRenamerFolder + File.separator + "conf" + File.separator + "movie_renamer.conf";
  public final String renamedFile = cacheDir + "renamed.xml";
  private final String logFile = userPath + File.separator + movieRenamerFolder + File.separator + "Logs" + File.separator + "movie_renamer.log";
  //Logger
  public static final Logger LOGGER = Logger.getLogger("Movie Renamer Logger");
  //IMDB
  public final String imdbSearchUrl = "http://www.imdb.com/find?s=tt&q=";
  public final String imdbMovieUrl = "http://www.imdb.com/title/";
  public final String imdbSearchUrl_fr = "http://www.imdb.fr/find?s=tt&q=";
  public final String imdbMovieUrl_fr = "http://www.imdb.fr/title/";
  //The Movie DB
  public final String tmdbAPISearchUrl = "http://api.themoviedb.org/2.1/Movie.search/en/xml/";
  public final String tmdbAPMovieImdbLookUp = "http://api.themoviedb.org/2.1/Movie.imdbLookup/en/xml/";
  public final String tmdbAPIMovieInf = "http://api.themoviedb.org/2.1/Movie.getInfo/en/xml/";
  //Tvdb
  public final String tvdbAPIUrlTvShow = "http://thetvdb.com/api/";
  public static final String tvdbAPIUrlTvShowImage = "http://thetvdb.com/banners/";
  //Allocine
  public final String allocineAPISearch = "http://api.allocine.fr/rest/v3/search?partner=yW5kcm9pZC12M3M&filter=FILTER&striptags=synopsis,synopsisshort&q=";
  public final String allocineAPIInfo = "http://api.allocine.fr/rest/v3/MEDIA?partner=yW5kcm9pZC12M3M&profile=large&filter=MEDIA&striptags=synopsis,synopsisshort&code=";
  //Xbmc Passion
  public final String xbmcPassionImdblookup = "http://passion-xbmc.org/scraper/ajax.php?Ajax=Home&";
  // List
  public int[] nbResultList = {-1, 5, 10, 15, 20, 30};
  public String[] thumbExtList = {".jpg", ".tbn", "-thumb.jpg"};
  public String[] fanartExtList = {".jpg", "-fanart.jpg"};
  //LAF
  public final static UIManager.LookAndFeelInfo lookAndFeels[] = UIManager.getInstalledLookAndFeels();
  public boolean lafChanged = false;
  public boolean interfaceChanged = false;
  //Apk
  public String xurlMdb = Utils.rot13(apkMdb);
  public String xurlTdb = Utils.rot13(apkTdb);
  public boolean xmlError = false;
  /**
   * Saved settings *
   */
  //General
  public boolean selectFrstMedia = false;
  public boolean scanSubfolder = false;
  public boolean showNotaMovieWarn = true;
  public boolean movieInfoPanel = true;
  public boolean actorImage = true;
  public boolean thumb = true;
  public boolean fanart = true;
  public String laf = "";
  public int nfoType = 0;
  public boolean checkUpdate = false;
  public String locale = "";
  //Rename movie filename
  public String movieFilenameFormat = "<t> (<y>)";
  public String movieFilenameSeparator = ", ";
  public int movieFilenameLimit = 3;
  public int movieFilenameCase = 1;
  public boolean movieFilenameTrim = true;
  public boolean movieFilenameRmDupSpace = true;
  public boolean movieFilenameCreateDirectory = false;
  //Renamer movie folder
  public String movieFolderFormat = "<t> (<y>)";
  public String movieFolderSeparator = ", ";
  public int movieFolderLimit = 3;
  public int movieFolderCase = 1;
  public boolean movieFolderTrim = true;
  public boolean movieFolderRmDupSpace = true;
  //Rename Tv show filename
  public String tvShowFilenameFormat = "<st> S<s>E<e> <et>";
  public String tvShowFilenameSeparator = ", ";
  public int tvShowFilenameLimit = 3;
  public int tvShowFilenameCase = 1;
  public boolean tvShowFilenameTrim = true;
  public boolean tvShowFilenameRmDupSpace = true;
  //Image
  public int thumbSize = 0;
  public int fanartSize = 0;
  public int thumbExt = 0;
  //
  //Filter 
  public String[] extensions = {"mkv", "avi", "wmv", "mp4", "m4v", "mov", "ts", "m2ts", "ogm", "mpg", "mpeg", "flv", "iso", "rm", "mov", "asf"};
  public static String[] nameFilters = {
    "notv", "readnfo", "repack", "proper$", "nfo$", "extended.cut", "limitededition", "limited", "k-sual",
    "extended", "uncut", "n° [0-9][0-9][0-9]", "yestv", "stv", "remastered", "limited", "x264", "bluray",
    "bd5", "bd9", "hddvd", "hdz", "unrated", "dvdrip", "cinefile",
    "hdmi", "dvd5", "ac3", "culthd", "dvd9", "remux", "edition.platinum", "frenchhqc", "frenchedit",
    "h264", "bdrip", "brrip", "hdteam", "hddvdrip", "subhd", "xvid", "divx", "null$", "divx511",
    "vorbis", "=str=", "www", "ffm", "mp3", "divx5", "dvb", "mpa2", "blubyte", "brmp", "avs", "filmhd",
    "hd4u", "1080p", "1080i", "720p", "720i", "720", "truefrench", "dts", "french", "vostfr", "1cd", "2cd", "vff", " vo$", " vf ", "hd",
    " cam$ ", "telesync", " ts ", " tc ", "ntsc", " pal$ ", "dvd-r", "dvdscr", "scr$", "r1", "r2", "r3", "r4",
    "r5", "wp", "subforced", "dvd", "vcd", "avchd", " md"
  };
  public ArrayList<String> mediaNameFilters;
  public boolean useExtensionFilter = true;
  //Cache
  public boolean clearXMLCache = false;
  //Search
  public int movieScrapper = 0;
  public int tvshowScrapper = 0;
  public boolean movieScrapperFR = false;
  public boolean tvshowScrapperFR = false;
  public boolean displayThumbResult = true;
  public boolean autoSearchMedia = true;
  public boolean selectFrstRes = true;
  public boolean sortBySimiYear = true;
  public int nbResult = 2;
  public boolean displayApproximateResult = false;
  //Misc
  public String xmlVersion = "";
  //Not used
  public boolean showMovieFilePath = false;
  public boolean hideRenamedMedia = false;

  /**
   * Constructor
   */
  public Settings() {
    mediaNameFilters = new ArrayList<String>();
    mediaNameFilters.addAll(Arrays.asList(nameFilters));
    Utils.createFilePath(configFile, false);
    Utils.createFilePath(fanartCacheDir, true);
    Utils.createFilePath(thumbCacheDir, true);
    Utils.createFilePath(actorCacheDir, true);
    Utils.createFilePath(xmlCacheDir, true);
    Utils.createFilePath(tvshowZipCacheDir, true);
    Utils.createFilePath(logFile, false);
    try {
      FileHandler fh = new FileHandler(logFile);
      LOGGER.addHandler(fh);
    } catch (SecurityException e) {
      LOGGER.log(Level.SEVERE, e.getMessage());
    } catch (IOException e) {
      LOGGER.log(Level.SEVERE, e.getMessage());
    }
    cache = new Cache(this);
  }

  /**
   * Save setting
   *
   * @return True if setting was saved, False otherwise
   */
  public boolean saveSetting() {
    LOGGER.log(Level.INFO, "Save configuration");
    try {
      String endl = Utils.ENDLINE;
      BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(configFile), "UTF-8"));
      out.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + endl);
      out.write("<Movie_Renamer Version=\"" + version + "\">" + endl);
      out.write("  <setting>" + endl);

      // Variables
      out.write("    <locale>" + locale + "</locale>" + endl);
      out.write("    <nameFilters>" + Utils.escapeXML(Utils.arrayToString(nameFilters, "/_", 0)) + "</nameFilters>" + endl);
      out.write("    <extensions>" + Utils.arrayToString(extensions, "/_", 0) + "</extensions>" + endl);
      out.write("    <thumbSize>" + thumbSize + "</thumbSize>" + endl);
      out.write("    <fanartSize>" + fanartSize + "</fanartSize>" + endl);
      out.write("    <nbResult>" + nbResult + "</nbResult>" + endl);
      out.write("    <movieFilenameFormat>" + Utils.escapeXML(movieFilenameFormat) + "</movieFilenameFormat>" + endl);
      out.write("    <thumbExt>" + thumbExt + "</thumbExt>" + endl);
      out.write("    <movieFilenameCase>" + movieFilenameCase + "</movieFilenameCase>" + endl);
      out.write("    <nfoType>" + nfoType + "</nfoType>" + endl);
      out.write("    <movieFilenameSeparator>" + Utils.escapeXML(movieFilenameSeparator) + "</movieFilenameSeparator>" + endl);
      out.write("    <movieFilenameLimit>" + movieFilenameLimit + "</movieFilenameLimit>" + endl);
      out.write("    <laf>" + laf + "</laf>" + endl);
      out.write("    <movieScrapper>" + movieScrapper + "</movieScrapper>" + endl);
      out.write("    <tvshowScrapper>" + tvshowScrapper + "</tvshowScrapper>" + endl);
      out.write("    <movieFolderFormat>" + Utils.escapeXML(movieFolderFormat) + "</movieFolderFormat>" + endl);
      out.write("    <movieFolderSeparator>" + Utils.escapeXML(movieFolderSeparator) + "</movieFolderSeparator>" + endl);
      out.write("    <movieFolderLimit>" + movieFolderLimit + "</movieFolderLimit>" + endl);
      out.write("    <movieFolderCase>" + movieFolderCase + "</movieFolderCase>" + endl);
      out.write("    <tvShowFilenameFormat>" + Utils.escapeXML(tvShowFilenameFormat) + "</tvShowFilenameFormat>" + endl);
      out.write("    <tvShowFilenameSeparator>" + Utils.escapeXML(tvShowFilenameSeparator) + "</tvShowFilenameSeparator>" + endl);
      out.write("    <tvShowFilenameLimit>" + tvShowFilenameLimit + "</tvShowFilenameLimit>" + endl);
      out.write("    <tvShowFilenameCase>" + tvShowFilenameCase + "</tvShowFilenameCase>" + endl);

      // booleans
      out.write("    <useExtensionFilter>" + (useExtensionFilter ? 0 : 1) + "</useExtensionFilter>" + endl);
      out.write("    <showMovieFilePath>" + (showMovieFilePath ? 0 : 1) + "</showMovieFilePath>" + endl);
      out.write("    <scanSubfolder>" + (scanSubfolder ? 0 : 1) + "</scanSubfolder>" + endl);
      out.write("    <hideRenamedMedia>" + (hideRenamedMedia ? 0 : 1) + "</hideRenamedMedia>" + endl);
      out.write("    <displayApproximateResult>" + (displayApproximateResult ? 0 : 1) + "</displayApproximateResult>" + endl);
      out.write("    <displayThumbResult>" + (displayThumbResult ? 0 : 1) + "</displayThumbResult>" + endl);
      out.write("    <movieFilenameCreateDirectory>" + (movieFilenameCreateDirectory ? 0 : 1) + "</movieFilenameCreateDirectory>" + endl);
      out.write("    <movieScrapperFR>" + (movieScrapperFR ? 0 : 1) + "</movieScrapperFR>" + endl);
      out.write("    <tvshowScrapperFR>" + (tvshowScrapperFR ? 0 : 1) + "</tvshowScrapperFR>" + endl);
      out.write("    <selectFrstMedia>" + (selectFrstMedia ? 0 : 1) + "</selectFrstMedia>" + endl);
      out.write("    <selectFrstRes>" + (selectFrstRes ? 0 : 1) + "</selectFrstRes>" + endl);
      out.write("    <movieInfoPanel>" + (movieInfoPanel ? 0 : 1) + "</movieInfoPanel>" + endl);
      out.write("    <actorImage>" + (actorImage ? 0 : 1) + "</actorImage>" + endl);
      out.write("    <thumb>" + (thumb ? 0 : 1) + "</thumb>" + endl);
      out.write("    <fanart>" + (fanart ? 0 : 1) + "</fanart>" + endl);
      out.write("    <checkUpdate>" + (checkUpdate ? 0 : 1) + "</checkUpdate>" + endl);
      out.write("    <showNotaMovieWarn>" + (showNotaMovieWarn ? 0 : 1) + "</showNotaMovieWarn>" + endl);
      out.write("    <autoSearchMedia>" + (autoSearchMedia ? 0 : 1) + "</autoSearchMedia>" + endl);
      out.write("    <movieFilenameTrim>" + (movieFilenameTrim ? 0 : 1) + "</movieFilenameTrim>" + endl);
      out.write("    <movieFilenameRmDupSpace>" + (movieFilenameRmDupSpace ? 0 : 1) + "</movieFilenameRmDupSpace>" + endl);
      out.write("    <clearXMLCache>" + (clearXMLCache ? 0 : 1) + "</clearXMLCache>" + endl);
      out.write("    <sortBySimiYear>" + (sortBySimiYear ? 0 : 1) + "</sortBySimiYear>" + endl);
      out.write("    <movieFolderTrim>" + (movieFolderTrim ? 0 : 1) + "</movieFolderTrim>" + endl);
      out.write("    <movieFolderRmDupSpace>" + (movieFolderRmDupSpace ? 0 : 1) + "</movieFolderRmDupSpace>" + endl);
      out.write("    <tvShowFilenameTrim>" + (tvShowFilenameTrim ? 0 : 1) + "</tvShowFilenameTrim>" + endl);
      out.write("    <tvShowFilenameRmDupSpace>" + (tvShowFilenameRmDupSpace ? 0 : 1) + "</tvShowFilenameRmDupSpace>" + endl);

      out.write("  </setting>" + endl);
      out.write("</Movie_Renamer>" + endl);
      out.close();
    } catch (IOException e) {
      LOGGER.log(Level.SEVERE, e.getMessage());
      return false;
    }
    return true;
  }

  /**
   * Get Movie Renamer version
   *
   * @return Movie Renamer Version
   */
  public String getVersion() {
    return version;
  }

  @Override
  public boolean equals(Object object) {
    if (object instanceof Settings) {
      Settings obj = (Settings) object;
      if (this.selectFrstMedia != obj.selectFrstMedia) {
        return false;
      }
      if (this.scanSubfolder != obj.scanSubfolder) {
        return false;
      }
      if (this.showNotaMovieWarn != obj.showNotaMovieWarn) {
        return false;
      }
      if (this.movieInfoPanel != obj.movieInfoPanel) {
        return false;
      }
      if (this.actorImage != obj.actorImage) {
        return false;
      }
      if (this.thumb != obj.thumb) {
        return false;
      }
      if (this.fanart != obj.fanart) {
        return false;
      }
      if (!this.laf.equals(obj.laf)) {
        return false;
      }
      if (this.nfoType != obj.nfoType) {
        return false;
      }
      if (this.checkUpdate != obj.checkUpdate) {
        return false;
      }
      if (!this.locale.equals(obj.locale)) {
        return false;
      }
      if (!this.movieFilenameFormat.equals(obj.movieFilenameFormat)) {
        return false;
      }
      if (!this.movieFilenameSeparator.equals(obj.movieFilenameSeparator)) {
        return false;
      }
      if (this.movieFilenameLimit != obj.movieFilenameLimit) {
        return false;
      }
      if (this.movieFilenameCase != obj.movieFilenameCase) {
        return false;
      }
      if (this.movieFilenameTrim != obj.movieFilenameTrim) {
        return false;
      }
      if (this.movieFilenameRmDupSpace != obj.movieFilenameRmDupSpace) {
        return false;
      }
      if (this.movieFilenameCreateDirectory != obj.movieFilenameCreateDirectory) {
        return false;
      }
      if (!this.movieFolderFormat.equals(obj.movieFolderFormat)) {
        return false;
      }
      if (!this.movieFolderSeparator.equals(obj.movieFolderSeparator)) {
        return false;
      }
      if (this.movieFolderLimit != obj.movieFolderLimit) {
        return false;
      }
      if (this.movieFolderCase != obj.movieFolderCase) {
        return false;
      }
      if (this.movieFolderTrim != obj.movieFolderTrim) {
        return false;
      }
      if (this.movieFolderRmDupSpace != obj.movieFolderRmDupSpace) {
        return false;
      }
      if (!this.tvShowFilenameFormat.equals(obj.tvShowFilenameFormat)) {
        return false;
      }
      if (!this.tvShowFilenameSeparator.equals(obj.tvShowFilenameSeparator)) {
        return false;
      }
      if (this.tvShowFilenameLimit != obj.tvShowFilenameLimit) {
        return false;
      }
      if (this.tvShowFilenameCase != obj.tvShowFilenameCase) {
        return false;
      }
      if (this.tvShowFilenameTrim != obj.tvShowFilenameTrim) {
        return false;
      }
      if (this.tvShowFilenameRmDupSpace != obj.tvShowFilenameRmDupSpace) {
        return false;
      }
      if (this.thumbSize != obj.thumbSize) {
        return false;
      }
      if (this.fanartSize != obj.fanartSize) {
        return false;
      }
      if (this.thumbExt != obj.thumbExt) {
        return false;
      }
      if (this.extensions != obj.extensions) {
        return false;
      }
      if (this.useExtensionFilter != obj.useExtensionFilter) {
        return false;
      }
      if (this.clearXMLCache != obj.clearXMLCache) {
        return false;
      }
      if (this.movieScrapper != obj.movieScrapper) {
        return false;
      }
      if (this.tvshowScrapper != obj.tvshowScrapper) {
        return false;
      }
      if (this.movieScrapperFR != obj.movieScrapperFR) {
        return false;
      }
      if (this.tvshowScrapperFR != obj.tvshowScrapperFR) {
        return false;
      }
      if (this.displayThumbResult != obj.displayThumbResult) {
        return false;
      }
      if (this.autoSearchMedia != obj.autoSearchMedia) {
        return false;
      }
      if (this.selectFrstRes != obj.selectFrstRes) {
        return false;
      }
      if (this.sortBySimiYear != obj.sortBySimiYear) {
        return false;
      }
      if (this.nbResult != obj.nbResult) {
        return false;
      }
      if (this.displayApproximateResult != obj.displayApproximateResult) {
        return false;
      }
    } else {
      return false;
    }
    return true;
  }

  @Override
  public int hashCode() {
    int hash = 7;
    hash = 97 * hash + (this.interfaceChanged ? 1 : 0);
    hash = 97 * hash + (this.selectFrstMedia ? 1 : 0);
    hash = 97 * hash + (this.scanSubfolder ? 1 : 0);
    hash = 97 * hash + (this.showNotaMovieWarn ? 1 : 0);
    hash = 97 * hash + (this.movieInfoPanel ? 1 : 0);
    hash = 97 * hash + (this.actorImage ? 1 : 0);
    hash = 97 * hash + (this.thumb ? 1 : 0);
    hash = 97 * hash + (this.fanart ? 1 : 0);
    hash = 97 * hash + (this.laf != null ? this.laf.hashCode() : 0);
    hash = 97 * hash + this.nfoType;
    hash = 97 * hash + (this.checkUpdate ? 1 : 0);
    hash = 97 * hash + (this.locale != null ? this.locale.hashCode() : 0);
    hash = 97 * hash + (this.movieFilenameFormat != null ? this.movieFilenameFormat.hashCode() : 0);
    hash = 97 * hash + (this.movieFilenameSeparator != null ? this.movieFilenameSeparator.hashCode() : 0);
    hash = 97 * hash + this.movieFilenameLimit;
    hash = 97 * hash + this.movieFilenameCase;
    hash = 97 * hash + (this.movieFilenameTrim ? 1 : 0);
    hash = 97 * hash + (this.movieFilenameRmDupSpace ? 1 : 0);
    hash = 97 * hash + (this.movieFilenameCreateDirectory ? 1 : 0);
    hash = 97 * hash + (this.movieFolderFormat != null ? this.movieFolderFormat.hashCode() : 0);
    hash = 97 * hash + (this.movieFolderSeparator != null ? this.movieFolderSeparator.hashCode() : 0);
    hash = 97 * hash + this.movieFolderLimit;
    hash = 97 * hash + this.movieFolderCase;
    hash = 97 * hash + (this.movieFolderTrim ? 1 : 0);
    hash = 97 * hash + (this.movieFolderRmDupSpace ? 1 : 0);
    hash = 97 * hash + (this.tvShowFilenameFormat != null ? this.tvShowFilenameFormat.hashCode() : 0);
    hash = 97 * hash + (this.tvShowFilenameSeparator != null ? this.tvShowFilenameSeparator.hashCode() : 0);
    hash = 97 * hash + this.tvShowFilenameLimit;
    hash = 97 * hash + this.tvShowFilenameCase;
    hash = 97 * hash + (this.tvShowFilenameTrim ? 1 : 0);
    hash = 97 * hash + (this.tvShowFilenameRmDupSpace ? 1 : 0);
    hash = 97 * hash + this.thumbSize;
    hash = 97 * hash + this.fanartSize;
    hash = 97 * hash + this.thumbExt;
    hash = 97 * hash + Arrays.deepHashCode(this.extensions);
    hash = 97 * hash + (this.mediaNameFilters != null ? this.mediaNameFilters.hashCode() : 0);
    hash = 97 * hash + (this.useExtensionFilter ? 1 : 0);
    hash = 97 * hash + (this.clearXMLCache ? 1 : 0);
    hash = 97 * hash + this.movieScrapper;
    hash = 97 * hash + this.tvshowScrapper;
    hash = 97 * hash + (this.movieScrapperFR ? 1 : 0);
    hash = 97 * hash + (this.tvshowScrapperFR ? 1 : 0);
    hash = 97 * hash + (this.displayThumbResult ? 1 : 0);
    hash = 97 * hash + (this.autoSearchMedia ? 1 : 0);
    hash = 97 * hash + (this.selectFrstRes ? 1 : 0);
    hash = 97 * hash + (this.sortBySimiYear ? 1 : 0);
    hash = 97 * hash + this.nbResult;
    hash = 97 * hash + (this.displayApproximateResult ? 1 : 0);
    hash = 97 * hash + (this.showMovieFilePath ? 1 : 0);
    hash = 97 * hash + (this.hideRenamedMedia ? 1 : 0);
    return hash;
  }
  
  @Override
  public Settings clone() throws CloneNotSupportedException{
    return (Settings) super.clone();
  }
}
