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
package fr.free.movierenamer.scrapper.impl;

import org.junit.Assert;

import fr.free.movierenamer.scrapper.SubtitleScrapperTest;

/**
 * Class OpenSubtitlesScrapperTest
 * 
 * @author Simon QUÉMÉNEUR
 */
public class OpenSubtitlesScrapperTest extends SubtitleScrapperTest {
  private OpenSubtitlesScrapper opensub = null;

  @Override
  public void init() {
    opensub = new OpenSubtitlesScrapper();
  }
  
  @Override
  public void search() throws Exception {
    Assert.fail();
  }
  
  @Override
  public void getSubtitleInfo() throws Exception {
    Assert.fail();
  }
  
}
