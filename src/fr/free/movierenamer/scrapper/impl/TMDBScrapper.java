/*
" * movie-renamer
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

import fr.free.movierenamer.info.*;
import fr.free.movierenamer.info.CastingInfo.PersonProperty;
import fr.free.movierenamer.info.ImageInfo.ImageCategoryProperty;
import fr.free.movierenamer.info.ImageInfo.ImageProperty;
import fr.free.movierenamer.info.MovieInfo.MovieProperty;
import fr.free.movierenamer.scrapper.MovieScrapper;
import fr.free.movierenamer.searchinfo.Movie;
import fr.free.movierenamer.settings.Settings;
import fr.free.movierenamer.utils.Date;
import fr.free.movierenamer.utils.ImageUtils;
import fr.free.movierenamer.utils.WebRequest;
import fr.free.movierenamer.utils.XPathUtils;
import java.net.URL;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

/**
 * Class TMDBScrapper : search movie on TMDB
 * 
 * @see http://help.themoviedb.org/kb/api/
 * @author Nicolas Magré
 * @author Simon QUÉMÉNEUR
 */
public class TMDBScrapper extends MovieScrapper {

  private static final String host = "api.themoviedb.org";
  private static final String name = "TheMovieDB";
  private static final String version = "2.1"; // TODO change to v3 !!!!

  private final String apikey;

  public TMDBScrapper() {
    String key = Settings.decodeApkKey(Settings.getApplicationProperty("themoviedb.apkapikey"));
    if (key == null) {
      throw new NullPointerException("apikey must not be null");
    }
    this.apikey = key;
  }

  @Override
  public String getName() {
    return name;
  }
  
  @Override
  protected String getHost() {
    return host;
  }

  @Override
  public Icon getIcon() {
    return new ImageIcon(ImageUtils.getImageFromJAR("scrapper/themoviedb.png"));
  }

  @Override
  protected List<Movie> searchMedia(String query, Locale locale) throws Exception {
    URL searchUrl = new URL("http", host, "/" + version + "/Movie.search/" + locale.getLanguage() + "/xml/" + apikey + "/" + WebRequest.encode(query));
    // has to be v3 !!!!
    // URL searchUrl = new URL("http", host, "/" + version + "/search/movie" + "?api_key=" + apikey + "&language=" + locale.getLanguage() + "&query=" + WebRequest.encode(query));
    Document dom = WebRequest.getXmlDocument(searchUrl);

    List<Node> nodes = XPathUtils.selectNodes("OpenSearchDescription/movies/movie", dom);
    Map<Integer, Movie> resultSet = new LinkedHashMap<Integer, Movie>(nodes.size());

    for (Node node : nodes) {
      int id = XPathUtils.getIntegerContent("id", node);
      int imdbId = -1;// XPathUtils.getIntegerContent("imdb_id", node);
      String movieName = XPathUtils.getTextContent("name", node);
      Date released = Date.parse(XPathUtils.getTextContent("released", node), "yyyy-MM-dd");

      if (!resultSet.containsKey(id)) {
        resultSet.put(id, new Movie(id, movieName, null, released.getYear(), imdbId));
      }
    }

    return new ArrayList<Movie>(resultSet.values());
  }

  @Override
  protected MovieInfo fetchMediaInfo(Movie movie, Locale locale) throws Exception {
    URL searchUrl = new URL("http", host, "/" + version + "/Movie.getInfo/" + locale.getLanguage() + "/xml/" + apikey + "/" + movie.getMediaId());
    Document dom = WebRequest.getXmlDocument(searchUrl);

    List<Node> nodes = XPathUtils.selectNodes("OpenSearchDescription/movies/movie", dom);

    for (Node node : nodes) {
      Map<MovieProperty, String> fields = new EnumMap<MovieProperty, String>(MovieProperty.class);
      fields.put(MovieProperty.title, XPathUtils.getTextContent("name", node));
      fields.put(MovieProperty.rating, XPathUtils.getTextContent("rating", node));
      fields.put(MovieProperty.votes, XPathUtils.getTextContent("votes", node));
      fields.put(MovieProperty.id, XPathUtils.getTextContent("imdb_id", node));
      fields.put(MovieProperty.IMDB_ID, XPathUtils.getTextContent("id", node));
      fields.put(MovieProperty.originalTitle, XPathUtils.getTextContent("original_name", node));
      fields.put(MovieProperty.releasedDate, XPathUtils.getTextContent("released", node));
      fields.put(MovieProperty.overview, XPathUtils.getTextContent("overview", node));
      fields.put(MovieProperty.runtime, XPathUtils.getTextContent("runtime", node));
      fields.put(MovieProperty.budget, XPathUtils.getTextContent("budget", node));

      List<String> genres = new ArrayList<String>();
      for (Node category : XPathUtils.selectNodes("categories/category", node)) {
        if ("genre".equals(XPathUtils.getAttribute("type", category))) {
          genres.add(XPathUtils.getAttribute("name", category));
        }
      }

      List<Locale> countries = new ArrayList<Locale>();

      List<CastingInfo> casting = new ArrayList<CastingInfo>();
      for (Node person : XPathUtils.selectNodes("cast/person", node)) {
        Map<PersonProperty, String> personFields = new EnumMap<PersonProperty, String>(PersonProperty.class);
        personFields.put(PersonProperty.name, XPathUtils.getAttribute("name", person));
        personFields.put(PersonProperty.character, XPathUtils.getAttribute("character", person));
        personFields.put(PersonProperty.job, XPathUtils.getAttribute("job", person));
        casting.add(new CastingInfo(personFields));
      }

      MovieInfo movieInfo = new MovieInfo(fields, genres, countries);
      return movieInfo;
    }

    return null;
  }

  @Override
  protected List<ImageInfo> fetchImagesInfo(Movie movie, Locale locale) throws Exception {
    URL searchUrl = new URL("http", host, "/" + version + "/Movie.getImages/" + locale.getLanguage() + "/xml/" + apikey + "/" + movie.getMediaId());
    Document dom = WebRequest.getXmlDocument(searchUrl);

    List<ImageInfo> images = new ArrayList<ImageInfo>();
    for (String section : new String[] { "backdrop", "poster" }) {
      List<Node> sectionNodes = XPathUtils.selectNodes("//" + section, dom);
      for (Node curNode : sectionNodes) {
        for (Node image : XPathUtils.selectNodes("image", curNode)) {
          try {
            if ("original".equals(XPathUtils.getAttribute("size", image))) {
              Map<ImageProperty, String> imageFields = new EnumMap<ImageProperty, String>(ImageProperty.class);
              imageFields.put(ImageProperty.category, (section.equals("poster") ? ImageCategoryProperty.thumb.name() : ImageCategoryProperty.fanart.name()));
              imageFields.put(ImageProperty.height, XPathUtils.getAttribute("height", image));
              imageFields.put(ImageProperty.url, XPathUtils.getAttribute("url", image));
              imageFields.put(ImageProperty.width, XPathUtils.getAttribute("width", image));
              images.add(new ImageInfo(imageFields));
            }
          } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.WARNING, "Invalid image: " + image, e);
          }
        }
      }
    }

    return images;
  }

  @Override
  protected List<CastingInfo> fetchCastingInfo(Movie movie, Locale locale) throws Exception {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Not supported yet.");
    // return null;
  }

}