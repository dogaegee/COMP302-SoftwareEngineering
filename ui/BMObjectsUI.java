package ui;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import domain.Constant;
import domain.Drawable;
import domain.object.GameObject;

/**
 * BMObjectsUI creates a panel where it draws obstacles in the Building Mode of the Need For Spear game 
 * @author Softwaring 
 * @since 02-12-2000
 * @version 2.0
 */
public class BMObjectsUI extends JPanel implements Drawable{

	private ArrayList<GameObject> objects = new ArrayList<GameObject>();
	private Image imageToBeDraw;
	private ImageIcon ii;
	private double width = Constant.width;
	private double height = Constant.height;

	/**
	 * Constructor for BMObjectsUI class
	 */
	public BMObjectsUI() {
		super.setSize((int)width, (int)height);
	}

	@Override
	public void triggerDraw(ArrayList<GameObject> o) {
		// TODO Auto-generated method stub
		this.objects = o;
		//Get image. You must change image location follow to image location in your computer.		 
		//Create an ImageIcon object
		this.repaint();
	}

	@Override
	protected void paintComponent(Graphics g) {
		// TODO Auto-generated method stub
		super.paintComponent(g);
		Image bg = Toolkit.getDefaultToolkit().getImage("jpeg/bg.png");
		g.drawImage(bg, 0, 0, (int)Constant.width, (int)Constant.height, this);
		for(GameObject object: objects) {
			if((int) object.getX() == 0) continue;
			imageToBeDraw=Toolkit.getDefaultToolkit().getImage(object.getPath());
			imageToBeDraw = imageToBeDraw.getScaledInstance(object.getWidth(),object.getHeight(),java.awt.Image.SCALE_SMOOTH);
			ii=new ImageIcon(imageToBeDraw);
			AffineTransform transform = new AffineTransform();
			transform.translate(object.getX(), object.getY());
			g.drawImage(imageToBeDraw,(int)object.getX(), (int)object.getY(),this);
			transform.translate(-object.getX(), -object.getY());
			Toolkit.getDefaultToolkit().sync();
		}
	}
}
