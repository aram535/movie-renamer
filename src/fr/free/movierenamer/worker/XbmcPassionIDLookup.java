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
package fr.free.movierenamer.worker;

import fr.free.movierenamer.media.MediaID;
import fr.free.movierenamer.parser.XbmcPassionIDLookupParser;
import fr.free.movierenamer.utils.ActionNotValidException;
import fr.free.movierenamer.utils.HttpGet;
import fr.free.movierenamer.utils.Settings;
import java.util.logging.Level;
import javax.swing.SwingWorker;

/**
 *
 * @author Nicolas Magré
 */
public class XbmcPassionIDLookup extends SwingWorker<MediaID, String> {

  private static final int RETRY = 3;
  private HttpGet http;
  private MediaID id;
  private Settings setting;

  /**
   * Constructor arguments
   *
   * @param id Movie API ID (allocine or imdb)
   * @param setting Movie Renamer setting
   * @throws ActionNotValidException
   */
  public XbmcPassionIDLookup(MediaID id, Settings setting) throws ActionNotValidException {
    if (id.getType() != MediaID.IMDBID && id.getType() != MediaID.ALLOCINEID) {
      throw new ActionNotValidException("ImdbInfoWorker can only use imdb ID");
    }
    this.id = id;
    this.setting = setting;
  }

  @Override
  protected MediaID doInBackground() throws Exception {
    MediaID mediaId = null;
    String res = null;
    for (int i = 0; i < RETRY; i++) {
      try {
        String apiID = id.getType() == MediaID.ALLOCINEID ?  id.getID(): id.getID().substring(2);
        http = new HttpGet(setting.xbmcPassionImdblookup + (id.getType() == MediaID.ALLOCINEID ? "IdAllo=":"IdImdb=" ) + apiID);
        res = http.sendGetRequest(false, "UTF-8");
        break;
      } catch (Exception ex) {//Don't care about exception, "res" will be null
        Settings.LOGGER.log(Level.SEVERE, null, ex);
        try {
          Thread.sleep(300);
        } catch (InterruptedException e) {
          Settings.LOGGER.log(Level.SEVERE, null, e);
        }
      }
    }

    if (res == null) {//Http request failed
      Settings.LOGGER.log(Level.SEVERE, null, "Httpget failed on " + http.getURL());
      return null;
    }

    XbmcPassionIDLookupParser xpParser = new XbmcPassionIDLookupParser();
    switch(id.getType()){
      case MediaID.ALLOCINEID:
        mediaId = xpParser.getImdbId(res);
        break;
      case MediaID.IMDBID:
        mediaId = xpParser.getAlloId(res);
        break;
    }

    return mediaId;
  }
}