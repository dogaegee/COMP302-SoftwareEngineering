package ui;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import domain.Constant;
import domain.Drawable;
import domain.object.GameObject;
import domain.object.obstacle.Obstacle;
import domain.object.obstacle.SimpleObstacle;

/**
 * BoardUI creates a panel where it draws obstacles in the Game Mode of the Need For Spear game 
 * @author Softwaring 
 */
public class BoardUI extends JPanel implements Drawable{
	private ArrayList<GameObject> objects = new ArrayList<GameObject>();
	private Image imageToBeDraw;
	private ImageIcon ii;
	private double width = Constant.width;
	private double height = Constant.height;

	/**
	 * Constructor for BMUI class
	 */
	public BoardUI() {
		super.setSize((int)width, (int)height);
		this.setSize((int)width, (int)height);
	}

	@Override
	public void triggerDraw(ArrayList<GameObject> o) {
		this.objects = o;
		this.repaint();
	}

	@Override
	protected void paintComponent(Graphics g) {
		//System.out.println("LOOOPPP");
		Graphics2D g2d = (Graphics2D)g;
		super.paintComponent(g);
		ArrayList<GameObject> temp = new ArrayList<GameObject>();
		Image bg = Toolkit.getDefaultToolkit().getImage("jpeg/bg.png");
		g.drawImage(bg, 0, 0, (int)Constant.width, (int)Constant.height, this);
		for(GameObject object: objects) {
			temp.add(object);
			imageToBeDraw=Toolkit.getDefaultToolkit().getImage(object.getPath());
			imageToBeDraw = imageToBeDraw.getScaledInstance(object.getWidth(),object.getHeight(),java.awt.Image.SCALE_SMOOTH);
			ii=new ImageIcon(imageToBeDraw);
			AffineTransform transform = new AffineTransform();
			transform.translate(object.getX(), object.getY());
			double desAng = object.getDesiredAngle();
			double ang = object.getAngle();
			if (desAng != 0) {
				double speed = object.getTurnSpeed()/10;
				if(Math.abs(ang+speed) < Math.abs(desAng)) {
					transform.rotate(Math.toRadians(speed+ang),
							object.getWidth() / 2,
							object.getHeight() / 2);
					object.setAngle(speed+ang);
				}else {
					object.setAngle(desAng);
					transform.rotate(Math.toRadians(desAng),
							object.getWidth() / 2,
							object.getHeight() / 2);
				}
			}
			//if (object.getClass()==SimpleObstacle.class)System.out.println(object.getPath());
			g2d.drawImage(imageToBeDraw, transform, this);
			Toolkit.getDefaultToolkit().sync();
		}

	}
}
