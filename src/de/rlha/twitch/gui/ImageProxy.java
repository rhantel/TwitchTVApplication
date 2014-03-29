package de.rlha.twitch.gui;

import java.awt.Component;
import java.awt.Graphics;
import java.net.URL;
import javax.swing.Icon;
import javax.swing.ImageIcon;

/**
 *
 * @author Roland Hantel
 */
public class ImageProxy implements Icon
{
   private ImageIcon imageIcon;
   private URL imageURL;
   private Thread retrievalThread;
   private boolean retrieving = false;

   public ImageProxy( URL imageURL )
   {
      this.imageURL = imageURL;
   }

   @Override
   public void paintIcon( final Component c, Graphics g, int x, int y )
   {
      if ( imageIcon != null )
      {
         imageIcon.paintIcon( c, g, x, y );
      }
      else
      {
         g.drawString( "Loading Stream Preview, please wait...", x, y );
         if ( !retrieving )
         {
            retrieving = true;
            retrievalThread = new Thread( () -> {
                try
                {                    
                    imageIcon = new ImageIcon( imageURL, "Stream Preview" );
                    c.repaint();
                }
                catch ( Exception e )
                {
                    e.printStackTrace();
                }
            });
            retrievalThread.start();
         }
      }
   }

   @Override
   public int getIconWidth()
   {
      if ( imageIcon != null )
      {
         return imageIcon.getIconWidth();
      }
      else
      {
         return 320;
      }
   }

   @Override
   public int getIconHeight()
   {
      if ( imageIcon != null )
      {
         return imageIcon.getIconHeight();
      }
      else
      {
         return 200;
      }
   }
}
