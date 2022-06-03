package ch.zhaw.prog2.concurrency4.singlecafe;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.LinkedList;

public class SingleCafe extends JFrame implements ActionListener {
    private Button slower;
    private Button faster;
    private Label guestInfo;
    private Label cookInfo;
    private TextArea queueArea;
    private final transient CafeQueue queue;
    private final transient Cook cook;
    private final transient Guest guest;

    public SingleCafe() {
        this.setTitle("Single Cafe");
        this.setLocation(200, 200);
        this.initWindow();
        this.pack();
        this.setMinimumSize(this.getSize());
        this.setVisible(true);
        // Queue has 5 slots
        queue = new CafeQueue(5);
        queue.addPropertyChangeListener(evt -> queueArea.setText(evt.getNewValue().toString()));
        // Cook-Thread
        cook = new Cook(queue);
        cook.addPropertyChangeListener(event -> cookInfo.setText(event.getNewValue().toString()));
        // Guest-Thread
        guest = new Guest(queue);
        guest.addPropertyChangeListener(event -> guestInfo.setText(event.getNewValue().toString()));
        // Start threads
        guest.start();
        cook.start();
    }

    public static void main(String[] args) {
        new SingleCafe();
    }

    public void initWindow() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                destroy();
            }
        });
        JPanel contentPane = (JPanel) getContentPane();
        contentPane.setBorder(new EmptyBorder(5, 10, 10, 10));
        contentPane.setLayout(new BorderLayout());

        // Add Control buttons
        Panel buttonBar = new Panel(new FlowLayout());
        contentPane.add(BorderLayout.NORTH, buttonBar);
        Label lab = new Label("Cook: ");
        buttonBar.add(lab);
        slower = new Button("slower");
        buttonBar.add(slower);
        slower.addActionListener(this);
        faster = new Button("faster");
        buttonBar.add(faster);
        faster.addActionListener(this);
        // Add main panel
        Panel mainPane = new Panel();
        mainPane.setLayout(new BoxLayout(mainPane, BoxLayout.Y_AXIS));
        contentPane.add(BorderLayout.CENTER, mainPane);
        // Add separator
        mainPane.add(new JSeparator());
        // Add guest info field
        guestInfo = new Label(" ");
        guestInfo.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
        mainPane.add(guestInfo);
        // Add cook info field
        cookInfo = new Label(" ");
        cookInfo.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
        mainPane.add(cookInfo);
        // Add separator
        mainPane.add(new JSeparator());
        // Add queue info
        mainPane.add(new Label("Kitchen-Queue:"));
        queueArea = new TextArea(6, 30);
        mainPane.add(queueArea);
    }

    public void destroy() {
        guest.terminate();
        cook.terminate();
        try {
            guest.join();
        } catch (InterruptedException e) {
            System.out.println("Guest join: " + e.getMessage());
        }
        try {
            cook.join();
        } catch (InterruptedException e) {
            System.out.println("Cook join: " + e.getMessage());
        }
    }

    public void actionPerformed(ActionEvent event) {
        if (event.getSource() == faster) {
            cook.faster();
        } else if (event.getSource() == slower) {
            cook.slower();
        }
    }

}


// provides a synchronized queue of strings
class CafeQueue {
    private final PropertyChangeSupport changeSupport = new PropertyChangeSupport(this);
    private final LinkedList<String> queueList = new LinkedList<>();
    private final int capacity;

    public CafeQueue(int size) {
        this.capacity = size;
    }

    // Add element to Queue
    public synchronized void add(String item) throws InterruptedException {
        while (queueList.size() >= capacity) {
            wait(); // wait for free space
        }
        queueList.addLast(item);
        notifyAll(); // wakup threads waiting in remove
        this.changeSupport.firePropertyChange("Queue", "", String.join("\n", queueList));
    }

    // Removes element from Queue
    public synchronized String remove() throws InterruptedException {
        while (queueList.isEmpty()) {
            wait(); // wait for item
        }
        String item = queueList.removeFirst();
        notifyAll(); // wecke Threads, die in addLast warten
        this.changeSupport.firePropertyChange("Queue", "", String.join("\n", queueList));
        return item;
    }


    public void addPropertyChangeListener(PropertyChangeListener listener) {
        this.changeSupport.addPropertyChangeListener(listener);
    }
}

class Guest extends Thread {
    private volatile boolean running = true;
    private final CafeQueue queue;
    private final PropertyChangeSupport changeSupport = new PropertyChangeSupport(this);
    private int mealNum = 0;
    private int sleepTime = 1000;


    public Guest(CafeQueue q) {
        this.queue = q;
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        this.changeSupport.addPropertyChangeListener(listener);
    }

    @Override
    public void run() {
        System.out.println("Guest thread started.");
        while (this.running) { // fill orders continuously
            try {
                Thread.sleep(sleepTime);
                queue.add("Order" + mealNum);
                this.changeSupport.firePropertyChange("Guest", "", "Order" + mealNum + " sent to Cook!");
                System.out.println("Guest ordered meal " + mealNum);
                mealNum++;
            } catch (InterruptedException e) {
                System.out.println("Guest Interrupted: " + e.getMessage());
            }
        } // end while
        System.out.println("Guest thread ended.");
    } // end run

    public void terminate() {
        this.running = false;
        this.interrupt();
    }
} // end Guest

class Cook extends Thread {
    private volatile boolean running = true;
    private final CafeQueue queue;
    private final PropertyChangeSupport changeSupport = new PropertyChangeSupport(this);
    private int sleepTime = 1000;

    public Cook(CafeQueue q) {
        this.queue = q;
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        this.changeSupport.addPropertyChangeListener(listener);
    }

    @Override
    public void run() {
        System.out.println("Cook thread started");
        while (this.running) { // processes orders continuously
            try {
                Thread.sleep(sleepTime);
                String meal = queue.remove();
                this.changeSupport.firePropertyChange("Cook", "", meal + " is ready!");
                System.out.println("Cook delivered " + meal);
            } catch (InterruptedException e) {
                System.out.println("Cook Interrupted: " + e.getMessage());
            }
        }
        System.out.println("Cook thread ended.");
    }

    public void terminate() {
        this.running = false;
        this.interrupt();
    }

    // work faster
    public void faster() {
        sleepTime = sleepTime / 2;
    }

    // work slower
    public void slower() {
        sleepTime = sleepTime * 2;
    }
}
