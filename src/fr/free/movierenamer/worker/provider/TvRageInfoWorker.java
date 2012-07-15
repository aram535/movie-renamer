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
package fr.free.movierenamer.worker.provider;

import fr.free.movierenamer.media.MediaID;
import fr.free.movierenamer.media.tvshow.SxE;
import fr.free.movierenamer.media.tvshow.TvShowImage;
import fr.free.movierenamer.media.tvshow.TvShowInfo;
import fr.free.movierenamer.parser.xml.MrParser;
import fr.free.movierenamer.utils.ActionNotValidException;
import fr.free.movierenamer.worker.TvShowInfoWorker;
import javax.swing.event.SwingPropertyChangeSupport;

/**
 * Class TvRageInfoWorker
 *
 * @author Nicolas Magré
 */
public class TvRageInfoWorker extends TvShowInfoWorker {

  private final SxE sxe;

  /**
   * Constructor arguments
   *
   * @param errorSupport Swing change support
   * @param id Media id
   * @param sxe
   * @throws ActionNotValidException
   */
  public TvRageInfoWorker(SwingPropertyChangeSupport errorSupport, MediaID id, SxE sxe) throws ActionNotValidException {
    super(errorSupport, id);
    if (id.getType() != MediaID.TVRAGETVID) {
      throw new ActionNotValidException("TvRageInfoWorker can only use tvrage ID");
    }
    this.sxe = sxe;
  }

  @Override
  protected MrParser<TvShowInfo> getInfoParser() throws Exception {// TODO
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  protected MrParser<TvShowImage> getImageParser() throws Exception {// TODO
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  protected String getSearchUri() throws Exception {// TODO
    throw new UnsupportedOperationException("Not supported yet.");
  }
}
