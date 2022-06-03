package ch.zhaw.prog2.concurrency3;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Clock3SyncBidi extends JFrame implements ActionListener {
    private volatile boolean running = false;
    Button start, stop;
    private Minute minute = null;
    private Second second = null;
	static Clock3SyncBidi oClock = null;
    private Label secondLabel = null;
    private Label minuteLabel = null;

    public static void main(String[] args) {
        oClock = new Clock3SyncBidi();
        oClock.setTitle("Uhrentest mit bidirectional wait");
        oClock.setSize(300, 200);
        oClock.setLocation(200, 200);
        oClock.init();
        oClock.setVisible(true);
    }

    public void init() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        JPanel contentPane = (JPanel) getContentPane();
        contentPane.setBorder(new EmptyBorder(5, 10, 10, 10));
        contentPane.setLayout(new BorderLayout());
        // add start / stop buttons
        Panel buttonBar = new Panel(new FlowLayout());
        contentPane.add(BorderLayout.NORTH, buttonBar);
        start = new Button("Start");
        buttonBar.add(start);
        start.addActionListener(this);
        stop = new Button("Stop");
        buttonBar.add(stop);
        stop.addActionListener(this);
        // Add main panel
        Panel mainPane = new Panel();
        mainPane.setLayout(new BoxLayout(mainPane, BoxLayout.Y_AXIS));
        contentPane.add(BorderLayout.CENTER, mainPane);
        // add second and minute labels
        secondLabel = new Label("                ");
        secondLabel.setFont(new Font("Arial", Font.PLAIN, 36));
        mainPane.add(secondLabel);
        minuteLabel = new Label("                ");
        minuteLabel.setFont(new Font("Arial", Font.PLAIN, 36));
        mainPane.add(minuteLabel);

    }

    public void actionPerformed(ActionEvent event) {
        if (event.getSource() == start) {
            if (!running) {
                Tick minuteTick = new Tick();
                minute = new Minute(minuteTick, minuteLabel);
                minute.start();
                second = new Second(minuteTick, secondLabel);
                second.start();
                running = true;
            }
        } else if (event.getSource() == stop) {
            if (running) {
                minute.terminate();
                second.terminate();
                running = false;
            }
        }
    }

    // manages minutes
    class Minute extends Thread {
        private volatile boolean running = true;
        private int minutes = 0;
        private Label minuteLabel;
        private Tick minuteTick;

        public Minute(Tick minuteTick, Label label) {
            this.minuteLabel = label;
            this.minuteTick = minuteTick;
        }

        @Override
        public void run() {
            System.out.println("Minute thread started");
            while (this.running) {
                try {
                    minuteTick.waitForTick();   // wait for tick event
                    if (minutes == 59) {
                        minutes = 0;            // reset after 1 hour
                    } else {
                        minutes++;
                    }
                    minuteLabel.setText(minutes + " Minute(s)");
                } catch (InterruptedException e) {
                    System.out.println("Interrupted: " + e.getMessage());
                }
            }
            System.out.println("Minute thread ended");
        }

        public void terminate() {
            this.running = false;
            this.interrupt();
        }
    }

    // manages seconds
    class Second extends Thread {
        private volatile boolean running = true;
        private int seconds = 0;
        private Label secondLabel;
        private Tick minuteTick;

        public Second(Tick minuteTick, Label label) {
            this.secondLabel = label;
            this.minuteTick = minuteTick;
        }

        @Override
        public void run() {
            System.out.println("Second thread started");
            while (this.running) {
                try {
                    Thread.sleep(100);   // the clock is 10x faster for demo purpose
                    if (seconds == 59) {        // after 60 seconds
                        minuteTick.tick();      // send tick signal
                        seconds = 0;            // reset seconds
                    } else {
                        seconds++;
                    }
                    secondLabel.setText(seconds + " Seconds");
                } catch (InterruptedException e) {
                    System.out.println("Interrupted: " + e.getMessage());
                }
            }
            System.out.println("Second thread ended");
        }

        public void terminate() {
            this.running = false;
            this.interrupt();
        }
    }

	class Tick{
		// Complete solution of the producer-consumer problem
		// consumer waits until producer provides product.
		// producer waits until consumer fetched product
		// before he provides new products.
		private boolean tickHappens = false;

		public synchronized void waitForTick() throws InterruptedException {
			while (!tickHappens) {
				try {
					wait(); // wait until producer set tickHappens
					// releases lock!
				} catch (InterruptedException e) {
					System.out.println("Interrupt in waitForTick");
					throw e; // rethrow exeption to allow program to stop
				}
			} // Thread must check status of tickHappens after wake-up
			tickHappens = false;
			notifyAll(); // send wake-up signal (to possible producers)
		}

		public synchronized void tick() throws InterruptedException {
			while(tickHappens){
				try {
					wait();// wait until consumer fetched tickHappens
				} catch (InterruptedException e) {
					System.out.println("Interrupt in tick");
					throw e;  // rethrow exception to allow program to stop
				}
			} // Thread must check status of tickHappens after wake-up
			tickHappens = true;
			notifyAll();  // send wake-up signal (to possible consumers)
		}
	}
}


