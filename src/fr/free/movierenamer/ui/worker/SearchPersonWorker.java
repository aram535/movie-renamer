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
package fr.free.movierenamer.ui.worker;

import fr.free.movierenamer.info.CastingInfo;
import fr.free.movierenamer.ui.LoadingDialog.LoadingDialogPos;
import fr.free.movierenamer.ui.MovieRenamer;
import java.beans.PropertyChangeSupport;
import javax.swing.JPanel;

/**
 * Class SearchPersonWorker
 * 
 * @author Nicolas Magré
 * @author Simon QUÉMÉNEUR
 */
public class SearchPersonWorker extends AbstractWorker {

  /**
   * @param errorSupport
   * @param parent
   * @param actorsPane
   * @param castingInfo
   */
  public SearchPersonWorker(PropertyChangeSupport errorSupport, MovieRenamer parent, JPanel actorsPane, CastingInfo castingInfo) {
    super(errorSupport, parent);
    // TODO Auto-generated constructor stub
  }

  @Override
  protected LoadingDialogPos getLoadingDialogPos() {
    return LoadingDialogPos.person;
  }

  @Override
  public void executeInBackground() throws Exception {
    throw new UnsupportedOperationException("Not supported yet.");
  }

}
