package de.rlha.twitch.gui;

import java.awt.Graphics;
import javax.swing.Icon;
import javax.swing.JComponent;

/**
 *
 * @author Roland Hantel
 */
class ImageComponent extends JComponent
{
   private Icon icon;

   public ImageComponent( Icon icon )
   {
      this.icon = icon;
   }

   public void setIcon( Icon icon )
   {
      this.icon = icon;
   }

   @Override
   public void paintComponent( Graphics g )
   {
      super.paintComponent( g );
      int x = 0;
      int y = 0;
      icon.paintIcon( this, g, x, y );
   }
}
