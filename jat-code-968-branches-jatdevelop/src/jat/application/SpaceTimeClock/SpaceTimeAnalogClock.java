package jat.application.SpaceTimeClock;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Calendar;

import javax.swing.JComponent;
import javax.swing.JFrame;

public class SpaceTimeAnalogClock extends JComponent implements Runnable {

	private static final long serialVersionUID = -6890729917079686421L;

	public SpaceTimeAnalogClock() {
		(new Thread(this)).start();
	}

	public void run() {
		try {
			for (;;) {
				Thread.sleep(500);
				repaint();
			}
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
	}

	public void paint(Graphics graphics) {
		super.paint(graphics);

		double Y, M, D, hour, minute, second;

		Calendar cal = Calendar.getInstance();
		hour = cal.get(Calendar.HOUR_OF_DAY);
		minute = cal.get(Calendar.MINUTE);
		second = cal.get(Calendar.SECOND);


		double sec_angle = Math.PI*(-.5+second/60)  ;
		double min_angle = -Math.PI / 2 + minute ;
		double hour_angle = -Math.PI / 2 + hour ;

		Graphics2D g = (Graphics2D) graphics;
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		size = getSize(size);
		insets = getInsets(insets);
		int radius = Math.min(size.width - insets.left - insets.top, size.height - insets.top - insets.bottom) / 2;
		g.translate((double) size.width / 2D, (double) size.height / 2D);

		g.setColor(Color.red);
		g.setStroke(SEC_STROKE);
		g.rotate(sec_angle);
		g.drawLine(0, 0, radius - 6, 0);
		g.rotate(-sec_angle);

		g.setColor(Color.black);
		g.setStroke(MIN_STROKE);
		g.rotate(min_angle);
		g.drawLine(0, 0, radius - 10, 0);
		g.rotate(-min_angle);

		g.setColor(Color.black);
		g.setStroke(HOUR_STROKE);
		g.rotate(hour_angle);
		g.drawLine(0, 0, radius / 2, 0);
		g.rotate(-hour_angle);

		g.setColor(Color.darkGray);
		g.drawOval(-radius + 2, -radius + 2, 2 * radius - 4, 2 * radius - 4);
	}

	private static Stroke SEC_STROKE = new BasicStroke();
	private static Stroke MIN_STROKE = new BasicStroke(4F, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
	private static Stroke HOUR_STROKE = MIN_STROKE;

	private Dimension size = null;
	private Insets insets = new Insets(0, 0, 0, 0);

	public static void main(String[] args) {
		JFrame f = new JFrame("Clock");
		SpaceTimeAnalogClock clock = new SpaceTimeAnalogClock();
		f.getContentPane().add(clock);
		f.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		f.setBounds(50, 50, 200, 200);
		f.show();
	}

}