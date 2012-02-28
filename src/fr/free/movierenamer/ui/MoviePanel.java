/******************************************************************************
 *                                                                             *
 *    Movie Renamer                                                            *
 *    Copyright (C) 2012 Magré Nicolas                                         *
 *                                                                             *
 *    Movie Renamer is free software: you can redistribute it and/or modify    *
 *    it under the terms of the GNU General Public License as published by     *
 *    the Free Software Foundation, either version 3 of the License, or        *
 *    (at your option) any later version.                                      *
 *                                                                             *
 *    This program is distributed in the hope that it will be useful,          *
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of           *
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the            *
 *    GNU General Public License for more details.                             *
 *                                                                             *
 *    You should have received a copy of the GNU General Public License        *
 *    along with this program.  If not, see <http://www.gnu.org/licenses/>.    *
 *                                                                             *
 ******************************************************************************/
package fr.free.movierenamer.ui;

import java.awt.AlphaComposite;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import fr.free.movierenamer.utils.Cache;
import fr.free.movierenamer.movie.Movie;
import fr.free.movierenamer.movie.MovieImage;
import fr.free.movierenamer.utils.Settings;
import javax.swing.Icon;

/**
 *
 * @author duffy
 */
public class MoviePanel extends javax.swing.JPanel {

  private final DefaultListModel fanartModel = new DefaultListModel();
  private final DefaultListModel thumbnailModel = new DefaultListModel();
  private final DefaultListModel actorModel = new DefaultListModel();
  public Dimension thumbListDim = new Dimension(60, 90);
  public Dimension fanartListDim = new Dimension(200, 90);
  public Dimension actorListDim = new Dimension(60, 90);
  private final Icon STAR = new ImageIcon(getClass().getResource("/image/star.png"));
  private final Icon STAR_HALF = new ImageIcon(getClass().getResource("/image/star-half.png"));
  private final Icon STAR_EMPTY = new ImageIcon(getClass().getResource("/image/star-empty.png"));
  private Image img;
  private ArrayList<MovieImage> thumbs;
  private ArrayList<MovieImage> fanarts;
  private Settings setting;

  /** Creates new form MovieImagePanel
   * @param setting 
   */
  public MoviePanel(final Settings setting) {
    this.setting = setting;
    initComponents();
    thumbs = new ArrayList<MovieImage>();
    fanarts = new ArrayList<MovieImage>();

    thumbnailsList.setModel(thumbnailModel);
    fanartList.setModel(fanartModel);
    actorList.setModel(actorModel);


    //thumbsScrollPane.setVisible(false);
    //fanartsScrollPane.setVisible(false);
    //jPanel2.setVisible(false);
    //movieTabbedPane.setVisible(false);

    thumbnailsList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
    thumbnailsList.setLayoutOrientation(JList.HORIZONTAL_WRAP);
    thumbnailsList.setVisibleRowCount(-1);

    MouseListener mouseListener = new MouseAdapter() {

      @Override
      public void mouseClicked(MouseEvent e) {//A refaire (http request in EDT)
        int index = thumbnailsList.locationToIndex(e.getPoint());
        if (index != -1)
          try {
            URL url = new URL(thumbs.get(index).getOrigUrl().replace(".png", ".jpg")); // API bug, png is jpg on server
            Image img = setting.cache.getImage(url, Cache.thumb);
            if (img == null) {
              setting.cache.add(url.openStream(), url.toString(), Cache.thumb);
              img = setting.cache.getImage(url, Cache.thumb);
            }
            if (img != null)
              jLabel7.setIcon(new ImageIcon(img.getScaledInstance(jLabel7.getWidth(), jLabel7.getHeight(), Image.SCALE_DEFAULT)));
          } catch (IOException ex) {
            Logger.getLogger(MoviePanel.class.getName()).log(Level.SEVERE, null, ex);
          }
      }
    };
    thumbnailsList.addMouseListener(mouseListener);


    fanartList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
    fanartList.setLayoutOrientation(JList.HORIZONTAL_WRAP);
    fanartList.setVisibleRowCount(-1);
    mouseListener = new MouseAdapter() {

      @Override
      public void mouseClicked(MouseEvent e) {//A refaire (http request in EDT)
        int index = fanartList.locationToIndex(e.getPoint());
        if (index != -1) {
          try {
            URL url = new URL(fanarts.get(index).getOrigUrl().replace(".png", ".jpg")); // API bug, png is jpg on server
            img = setting.cache.getImage(url, Cache.fanart);
            if (img == null) {
              setting.cache.add(url.openStream(), url.toString(), Cache.fanart);
              img = setting.cache.getImage(url, Cache.fanart);
            }
          } catch (IOException ex) {
            Logger.getLogger(MoviePanel.class.getName()).log(Level.SEVERE, null, ex);
          }
          jPanel3.validate();
          jPanel3.repaint();
        }
      }
    };
    fanartList.addMouseListener(mouseListener);

    actorList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
    actorList.setLayoutOrientation(JList.HORIZONTAL_WRAP);
    actorList.setVisibleRowCount(-1);

    img = null;
  }

  public void addThumbToList(final Image thumb, final MovieImage mvImg) {//A refaire (http request in EDT)

    SwingUtilities.invokeLater(new Thread() {

      @Override
      public void run() {
        thumbs.add(mvImg);
        if (thumbnailModel.isEmpty())
          try {
            URL url = new URL(thumbs.get(0).getOrigUrl().replace(".png", ".jpg")); // API bug, png is jpg on server
            Image img = setting.cache.getImage(url, Cache.thumb);
            if (img == null) {
              setting.cache.add(url.openStream(), url.toString(), Cache.thumb);
              img = setting.cache.getImage(url, Cache.thumb);
            }
            if (img != null)
              jLabel7.setIcon(new ImageIcon(img.getScaledInstance(jLabel7.getWidth(), jLabel7.getHeight(), Image.SCALE_DEFAULT)));
          } catch (IOException ex) {
            Logger.getLogger(MoviePanel.class.getName()).log(Level.SEVERE, null, ex);
          }
        if (thumb != null)
          thumbnailModel.addElement(new ImageIcon(thumb.getScaledInstance(thumbListDim.width, thumbListDim.height, Image.SCALE_DEFAULT)));
        if (!thumbnailModel.isEmpty())
          thumbnailsList.setSelectedIndex(0);
      }
    });
  }

  public void addFanartToList(final Image fanart, final MovieImage mvImg) {//A refaire (http request in EDT)

    SwingUtilities.invokeLater(new Thread() {

      @Override
      public void run() {
        fanarts.add(mvImg);
        if (fanartModel.isEmpty()) {
          try {
            URL url = new URL(fanarts.get(0).getOrigUrl().replace(".png", ".jpg")); // API bug, png is jpg on server
            img = setting.cache.getImage(url, Cache.fanart);
            if (img == null) {
              setting.cache.add(url.openStream(), url.toString(), Cache.fanart);
              img = setting.cache.getImage(url, Cache.fanart);
            }
          } catch (IOException ex) {
            Logger.getLogger(MoviePanel.class.getName()).log(Level.SEVERE, null, ex);
          }
          jPanel3.validate();
          jPanel3.repaint();
        }

        if (fanart != null)
          fanartModel.addElement(new ImageIcon(fanart.getScaledInstance(fanartListDim.width, fanartListDim.height, Image.SCALE_DEFAULT)));
        if (!fanartModel.isEmpty())
          fanartList.setSelectedIndex(0);
      }
    });
  }

  public void addActorToList(final String actor, final Image actorImg, final String desc) {
    SwingUtilities.invokeLater(new Thread() {

      @Override
      public void run() {
        if (actorImg != null)
          actorModel.addElement(new ImageIcon(actorImg.getScaledInstance(actorListDim.width, actorListDim.height, Image.SCALE_DEFAULT), desc));
        else actorModel.addElement(actor);
      }
    });
  }

  public void clearList() {
    fanarts.clear();
    thumbs.clear();
    img = null;
    SwingUtilities.invokeLater(new Thread() {

      @Override
      public void run() {
        img = null;
        fanartModel.clear();
        thumbnailModel.clear();
        actorModel.clear();
        genreField.setText("");
        yearField.setText("");
        runtimeField.setText("");
        jTextArea1.setText("");
        origTitleField.setText("");
        countryField.setText("");
        jLabel7.setIcon(null);
        star.setIcon(STAR_EMPTY);
        star1.setIcon(STAR_EMPTY);
        star2.setIcon(STAR_EMPTY);
        star3.setIcon(STAR_EMPTY);
        star4.setIcon(STAR_EMPTY);

        validate();
        repaint();
      }
    });
  }

  public void addMovie(final Movie movie) {
    SwingUtilities.invokeLater(new Thread() {

      @Override
      public void run() {
        genreField.setText(movie.getGenresString());
        yearField.setText(movie.getYear());
        runtimeField.setText(movie.getRuntime() + " min");
        jTextArea1.setText(movie.getSynopsis());
        origTitleField.setText(movie.getOrigTitle());
        countryField.setText(movie.getCountriesString());
        setRate(Double.parseDouble(movie.getRating()));
      }
    });
  }

  private void setRate(Double rate) {
    if (rate < 0.00) return;
    if (rate > 5.00) rate /= 2;
    int n = rate.intValue();
    switch (n) {
      case 0:
        break;
      case 1:
        star.setIcon(STAR);
        if ((rate - rate.intValue()) >= 0.50) star1.setIcon(STAR_HALF);
        break;
      case 2:
        star.setIcon(STAR);
        star1.setIcon(STAR);
        if ((rate - rate.intValue()) >= 0.50) star2.setIcon(STAR_HALF);
        break;
      case 3:
        star.setIcon(STAR);
        star1.setIcon(STAR);
        star2.setIcon(STAR);
        if ((rate - rate.intValue()) >= 0.50) star3.setIcon(STAR_HALF);
        break;
      case 4:
        star.setIcon(STAR);
        star1.setIcon(STAR);
        star2.setIcon(STAR);
        star3.setIcon(STAR);
        if ((rate - rate.intValue()) >= 0.50) star4.setIcon(STAR_HALF);
        break;
      case 5:
        star.setIcon(STAR);
        star1.setIcon(STAR);
        star2.setIcon(STAR);
        star3.setIcon(STAR);
        star4.setIcon(STAR);
        break;
      default:
        return;
    }
  }

  public URL getSelectedThumb() {
    return getSelectedItem(thumbs, thumbnailsList);
  }

  public URL getSelectedFanart() {
    return getSelectedItem(fanarts, fanartList);
  }

  private URL getSelectedItem(ArrayList<MovieImage> array, JList list) {
    try {
      return new URL(array.get(list.getSelectedIndex()).getOrigUrl().replace(".png", ".jpg"));
    } catch (MalformedURLException ex) {
      Logger.getLogger(MoviePanel.class.getName()).log(Level.SEVERE, null, ex);
    }
    return null;
  }

  /** This method is called from within the constructor to
   * initialize the form.
   * WARNING: Do NOT modify this code. The content of this method is
   * always regenerated by the Form Editor.
   */
  @SuppressWarnings("unchecked")
  // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
  private void initComponents() {

    jPanel2 = new JPanel();
    fanartsScrollPane = new JScrollPane();
    thumbsScrollPane = new JScrollPane();
    jPanel1 = new JPanel();
    movieTabbedPane = new JTabbedPane();
    jPanel3 = new JPanel(){
      @Override
      public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if(img != null){
          g.drawImage(img, 0, 0, getWidth(), getHeight(), this);
        }

        // Create an AlphaComposite with 50% translucency.
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
          RenderingHints.VALUE_ANTIALIAS_ON);
        Composite oldComp = g2d.getComposite();
        Composite alphaComp = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.6f);

        // Set the composite on the Graphics2D object.
        g2d.setComposite(alphaComp);

        // Invoke arbitrary paint methods, which will paint
        // with 50% translucency.
        g2d.setPaint(Color.black);
        g2d.fillRoundRect(20, 20, getWidth()-40, getHeight()-40, 50, 50);

        // Restore the old composite.
        g2d.setComposite(oldComp);
      }
    }
    ;
    jScrollPane2 = new JScrollPane();
    jTextArea1 = new JTextArea();
    genreField = new JTextField();
    yearField = new JTextField();
    runtimeField = new JTextField();
    jTextField4 = new JTextField();
    origTitleField = new JTextField();
    countryField = new JTextField();
    jPanel4 = new JPanel();
    jScrollPane3 = new JScrollPane();
    actorList = new JList();
    jLabel7 = new JLabel();
    star4 = new JLabel();
    star3 = new JLabel();
    star2 = new JLabel();
    star1 = new JLabel();
    star = new JLabel();

    setLayout(new BorderLayout());

    jPanel2.setPreferredSize(new Dimension(562, 125));

    fanartsScrollPane.setBorder(BorderFactory.createTitledBorder("Fanarts"));

    fanartList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    fanartList.setAutoscrolls(false);
    fanartList.setLayoutOrientation(JList.HORIZONTAL_WRAP);
    fanartList.setMinimumSize(new Dimension(0, 110));
    fanartList.setVisibleRowCount(1);
    fanartsScrollPane.setViewportView(fanartList);

    thumbsScrollPane.setBorder(BorderFactory.createTitledBorder("Thumbnails"));
    thumbsScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

    thumbnailsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    thumbnailsList.setLayoutOrientation(JList.HORIZONTAL_WRAP);
    thumbnailsList.setVisibleRowCount(1);
    thumbsScrollPane.setViewportView(thumbnailsList);

    GroupLayout jPanel2Layout = new GroupLayout(jPanel2);
    jPanel2.setLayout(jPanel2Layout);
    jPanel2Layout.setHorizontalGroup(
      jPanel2Layout.createParallelGroup(Alignment.LEADING)
      .addGroup(jPanel2Layout.createSequentialGroup()
        .addComponent(thumbsScrollPane, GroupLayout.DEFAULT_SIZE, 266, Short.MAX_VALUE)
        .addPreferredGap(ComponentPlacement.RELATED)
        .addComponent(fanartsScrollPane, GroupLayout.DEFAULT_SIZE, 266, Short.MAX_VALUE))
    );
    jPanel2Layout.setVerticalGroup(
      jPanel2Layout.createParallelGroup(Alignment.LEADING)
      .addComponent(thumbsScrollPane, GroupLayout.DEFAULT_SIZE, 125, Short.MAX_VALUE)
      .addComponent(fanartsScrollPane, GroupLayout.DEFAULT_SIZE, 125, Short.MAX_VALUE)
    );

    add(jPanel2, BorderLayout.PAGE_END);

    jTextArea1.setColumns(20);
    jTextArea1.setLineWrap(true);
    jTextArea1.setRows(5);
    jTextArea1.setWrapStyleWord(true);
    jTextArea1.setBorder(null);
    jTextArea1.setOpaque(false);
    jScrollPane2.setViewportView(jTextArea1);

    genreField.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0)));

    yearField.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0)));

    runtimeField.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0)));

    jTextField4.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0)));

    origTitleField.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0)));

    countryField.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0)));

    GroupLayout jPanel3Layout = new GroupLayout(jPanel3);
    jPanel3.setLayout(jPanel3Layout);
    jPanel3Layout.setHorizontalGroup(
      jPanel3Layout.createParallelGroup(Alignment.LEADING)
      .addGroup(Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
        .addContainerGap()
        .addGroup(jPanel3Layout.createParallelGroup(Alignment.TRAILING)
          .addComponent(jScrollPane2, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 323, Short.MAX_VALUE)
          .addGroup(jPanel3Layout.createSequentialGroup()
            .addGroup(jPanel3Layout.createParallelGroup(Alignment.LEADING)
              .addComponent(genreField, GroupLayout.DEFAULT_SIZE, 143, Short.MAX_VALUE)
              .addComponent(yearField, GroupLayout.DEFAULT_SIZE, 143, Short.MAX_VALUE)
              .addComponent(runtimeField, GroupLayout.DEFAULT_SIZE, 143, Short.MAX_VALUE))
            .addGap(37, 37, 37)
            .addGroup(jPanel3Layout.createParallelGroup(Alignment.TRAILING)
              .addComponent(jTextField4, GroupLayout.DEFAULT_SIZE, 143, Short.MAX_VALUE)
              .addComponent(origTitleField, GroupLayout.DEFAULT_SIZE, 143, Short.MAX_VALUE)
              .addComponent(countryField, GroupLayout.DEFAULT_SIZE, 143, Short.MAX_VALUE))))
        .addContainerGap())
    );
    jPanel3Layout.setVerticalGroup(
      jPanel3Layout.createParallelGroup(Alignment.LEADING)
      .addGroup(jPanel3Layout.createSequentialGroup()
        .addContainerGap()
        .addGroup(jPanel3Layout.createParallelGroup(Alignment.LEADING, false)
          .addGroup(jPanel3Layout.createSequentialGroup()
            .addComponent(genreField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
            .addGap(18, 18, 18)
            .addComponent(yearField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
          .addGroup(jPanel3Layout.createSequentialGroup()
            .addComponent(countryField, GroupLayout.PREFERRED_SIZE, 17, GroupLayout.PREFERRED_SIZE)
            .addGap(18, 18, 18)
            .addComponent(origTitleField, GroupLayout.PREFERRED_SIZE, 17, GroupLayout.PREFERRED_SIZE)))
        .addGap(18, 18, 18)
        .addGroup(jPanel3Layout.createParallelGroup(Alignment.BASELINE)
          .addComponent(jTextField4, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
          .addComponent(runtimeField, GroupLayout.PREFERRED_SIZE, 17, GroupLayout.PREFERRED_SIZE))
        .addPreferredGap(ComponentPlacement.UNRELATED)
        .addComponent(jScrollPane2, GroupLayout.DEFAULT_SIZE, 78, Short.MAX_VALUE)
        .addContainerGap())
    );

    jPanel3.setBorder(new EmptyBorder(35, 35, 35, 35));

    movieTabbedPane.addTab("Details", jPanel3);

    actorList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    jScrollPane3.setViewportView(actorList);

    GroupLayout jPanel4Layout = new GroupLayout(jPanel4);
    jPanel4.setLayout(jPanel4Layout);
    jPanel4Layout.setHorizontalGroup(
      jPanel4Layout.createParallelGroup(Alignment.LEADING)
      .addGroup(jPanel4Layout.createSequentialGroup()
        .addContainerGap()
        .addComponent(jScrollPane3, GroupLayout.DEFAULT_SIZE, 323, Short.MAX_VALUE)
        .addContainerGap())
    );
    jPanel4Layout.setVerticalGroup(
      jPanel4Layout.createParallelGroup(Alignment.LEADING)
      .addGroup(jPanel4Layout.createSequentialGroup()
        .addContainerGap()
        .addComponent(jScrollPane3, GroupLayout.DEFAULT_SIZE, 174, Short.MAX_VALUE)
        .addContainerGap())
    );

    movieTabbedPane.addTab("Actor", jPanel4);






    jLabel7.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));

    star4.setIcon(new ImageIcon(getClass().getResource("/image/star-empty.png"))); // NOI18N
    star3.setIcon(new ImageIcon(getClass().getResource("/image/star-empty.png"))); // NOI18N
    star2.setIcon(new ImageIcon(getClass().getResource("/image/star-empty.png"))); // NOI18N
    star1.setIcon(new ImageIcon(getClass().getResource("/image/star-empty.png"))); // NOI18N
    star.setIcon(new ImageIcon(getClass().getResource("/image/star-empty.png"))); // NOI18N
    GroupLayout jPanel1Layout = new GroupLayout(jPanel1);
    jPanel1.setLayout(jPanel1Layout);
    jPanel1Layout.setHorizontalGroup(
      jPanel1Layout.createParallelGroup(Alignment.LEADING)
      .addGroup(jPanel1Layout.createSequentialGroup()
        .addGap(28, 28, 28)
        .addGroup(jPanel1Layout.createParallelGroup(Alignment.LEADING)
          .addGroup(jPanel1Layout.createSequentialGroup()
            .addComponent(star)
            .addComponent(star1)
            .addComponent(star2)
            .addComponent(star3)
            .addComponent(star4))
          .addComponent(jLabel7, GroupLayout.PREFERRED_SIZE, 140, GroupLayout.PREFERRED_SIZE))
        .addPreferredGap(ComponentPlacement.UNRELATED)
        .addComponent(movieTabbedPane, GroupLayout.DEFAULT_SIZE, 352, Short.MAX_VALUE))
    );
    jPanel1Layout.setVerticalGroup(
      jPanel1Layout.createParallelGroup(Alignment.LEADING)
      .addGroup(jPanel1Layout.createSequentialGroup()
        .addContainerGap()
        .addGroup(jPanel1Layout.createParallelGroup(Alignment.LEADING)
          .addComponent(star)
          .addComponent(star1)
          .addComponent(star2)
          .addComponent(star3)
          .addComponent(star4))
        .addPreferredGap(ComponentPlacement.RELATED)
        .addComponent(jLabel7, GroupLayout.PREFERRED_SIZE, 161, GroupLayout.PREFERRED_SIZE)
        .addGap(22, 22, 22))
      .addComponent(movieTabbedPane, GroupLayout.DEFAULT_SIZE, 225, Short.MAX_VALUE)
    );

    add(jPanel1, BorderLayout.CENTER);
  }// </editor-fold>//GEN-END:initComponents
  // Variables declaration - do not modify//GEN-BEGIN:variables
  private JList actorList;
  private JTextField countryField;
  private final JList fanartList = new JList();
  private JScrollPane fanartsScrollPane;
  private JTextField genreField;
  private JLabel jLabel7;
  private JPanel jPanel1;
  private JPanel jPanel2;
  private JPanel jPanel3;
  private JPanel jPanel4;
  private JScrollPane jScrollPane2;
  private JScrollPane jScrollPane3;
  private JTextArea jTextArea1;
  private JTextField jTextField4;
  private JTabbedPane movieTabbedPane;
  private JTextField origTitleField;
  private JTextField runtimeField;
  private JLabel star;
  private JLabel star1;
  private JLabel star2;
  private JLabel star3;
  private JLabel star4;
  private final JList thumbnailsList = new JList(){
    // This method is called as the cursor moves within the list.
    public String getToolTipText(MouseEvent evt) {
      int index = locationToIndex(evt.getPoint());
      ImageIcon item = (ImageIcon) getModel().getElementAt(index);
      return item.getDescription();
    }
  };
  private JScrollPane thumbsScrollPane;
  private JTextField yearField;
  // End of variables declaration//GEN-END:variables
}
