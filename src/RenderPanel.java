import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;


public class RenderPanel extends JPanel {

	private BufferedImage image;

	public void setImage(BufferedImage bi){
		image = bi;
		repaint();
	}
	
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (image != null){
        	int x = (this.getWidth() - image.getWidth()) / 2;
            int y = (this.getHeight() - image.getHeight()) / 2;
            g.drawImage(image, x, y, null);     
        }
    }

	
}
