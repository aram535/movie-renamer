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
package fr.free.movierenamer;

import com.alee.laf.WebLookAndFeel;
import fr.free.movierenamer.settings.Settings;
import fr.free.movierenamer.ui.MovieRenamer;
import fr.free.movierenamer.utils.Cache;

/**
 * Class Main
 * 
 * @author Nicolas Magré
 * @author Simon QUÉMÉNEUR
 */
public class Main {
  private static MovieRenamer mvr;

  public static void main(String args[]) {

    Settings setting = Settings.getInstance();

    // Install look and feel
    WebLookAndFeel.install();

    // Clear cache
    if (setting.clearCache) {
      Cache.clearAllCache();
    }

    java.awt.EventQueue.invokeLater(new Runnable() {

      @Override
      public void run() {
        mvr = new MovieRenamer();
        mvr.setVisible(true);
      }
    });
  }
}
