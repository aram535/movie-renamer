/*
 * movie-renamer-core
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
package fr.free.movierenamer.scrapper;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import fr.free.movierenamer.settings.Settings;
import fr.free.movierenamer.utils.Cache;
import fr.free.movierenamer.utils.CacheObject;
import fr.free.movierenamer.utils.LocaleUtils.AvailableLanguages;
import fr.free.movierenamer.utils.LocaleUtils.Language;
import java.util.logging.Level;

/**
 * Class Scrapper
 *
 * @author Nicolas Magré
 * @author Simon QUÉMÉNEUR
 */
public abstract class Scrapper {

  public abstract String getName();

  protected abstract String getHost();

  protected final CacheObject getCache() {
    String host = getHost();
    String cacheName = getCacheName();
    if (host != null && cacheName != null) {
      return new CacheObject(host, Cache.getCache(cacheName));
    } else {
      return null;
    }
  }

  protected String getCacheName() {
    return null;
  }

  @Override
  public final String toString() {
    return String.format("%s", getName());
  }
}
