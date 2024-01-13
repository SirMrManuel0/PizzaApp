package de.sirmrmanuel0.gui.custom_components;


import javax.swing.*;
import java.awt.*;
import java.util.Enumeration;

/**
 * CustomFrame is a subclass of JFrame providing additional functionalities
 * and customization for creating GUI windows.
 */
public class CustomFrame extends JFrame {

    // Font for the frame
    protected Font font;

    // Width and height of the frame
    protected int frameWidth;
    protected int frameHeight;

    // Golden ratio constant
    public static final double PHI = 1.618033988749;

    // Constants for specifying PHI as height or width
    public static final int PHI_HEIGHT = 0;
    public static final int PHI_WIDTH = 1;

    // Image for the background
    private Image backgroundImage;

    /**
     * Constructs a CustomFrame with default size, title, and resizable setting.
     *
     * @param title The title of the frame.
     */
    public CustomFrame(String title) {
        this(title, true); // Default to resizable
    }

    /**
     * Constructs a CustomFrame with a custom size, title, and resizable setting.
     *
     * @param title      The title of the frame.
     * @param resizable  Whether the frame should be resizable.
     */
    public CustomFrame(String title, boolean resizable) {
        initializeFont();
        initializeFrameBasedOnPhi(PHI_HEIGHT, 1, 1.5, title, resizable);
    }

    /**
     * Constructs a CustomFrame with a custom size, title, and resizable setting based on the golden ratio.
     * Example:
     * phi = PHI_HEIGHT
     * frameHeight = (int) Math.round((screenHeight * multiplier / scale));
     * frameWidth = (int) Math.round(frameHeight * PHI);
     *
     * @param phi              Specifies whether the golden ratio is applied to width or height.
     * @param multiplier       The multiplier for calculating the frame size.
     * @param scale            The scale factor for the frame size.
     * @param title            The title of the frame.
     * @param resizable        Whether the frame should be resizable.
     */
    public CustomFrame(int phi, double multiplier, double scale, String title, boolean resizable) {
        initializeFont();
        initializeFrameBasedOnPhi(phi, multiplier, scale, title, resizable);
    }

    /**
     * Constructs a CustomFrame with a custom size, title, and resizable setting.
     * (int) Math.round((screenWidth * widthMultiplier) / widthScale)
     * (int) Math.round((screenHeight * heightMultiplier) / heightScale)
     *
     * @param widthMultiplier  The multiplier for calculating the frame width.
     * @param widthScale       The scale factor for the frame width.
     * @param heightMultiplier The multiplier for calculating the frame height.
     * @param heightScale      The scale factor for the frame height.
     * @param title            The title of the frame.
     * @param resizable        Whether the frame should be resizable.
     */
    public CustomFrame(double widthMultiplier, double widthScale,
                       double heightMultiplier, double heightScale, String title, boolean resizable) {
        initializeFont();
        initializeFrame(widthMultiplier, widthScale, heightMultiplier, heightScale, title, resizable);
    }


    /**
     * Calculates the scaled dimension based on width and height scales.
     * Example:
     * widthScale = 2, heightScale = 1.5
     * scaledWidth = (int) Math.round(getWidth() / widthScale)
     *
     * @param widthScale  The scale factor for the width.
     * @param heightScale The scale factor for the height.
     * @return The scaled dimension.
     */
    public Dimension getScaledDimension(double widthScale, double heightScale) {
        return new Dimension((int) Math.round(getWidth() / widthScale),
                (int) Math.round(getHeight() / heightScale));
    }

    /**
     * Calculates the scaled dimension based on width and height multipliers and scales.
     * Example:
     * widthMultiplier = 0.8, widthScale = 2, heightMultiplier = 1.2, heightScale = 1.5
     * scaledWidth = (int) Math.round(getWidth() * widthMultiplier / widthScale)
     *
     * @param widthMultiplier  The multiplier for calculating the width.
     * @param widthScale       The scale factor for the width.
     * @param heightMultiplier The multiplier for calculating the height.
     * @param heightScale      The scale factor for the height.
     * @return The scaled dimension.
     */
    public Dimension getScaledDimension(double widthMultiplier, double widthScale,
                                        double heightMultiplier, double heightScale) {
        return new Dimension((int) Math.round(getWidth() * widthMultiplier / widthScale),
                (int) Math.round(getHeight() * heightMultiplier / heightScale));
    }

    /**
     * Sets the background image of the frame.
     *
     * @param path The path to the background image.
     */
    public void setBackgroundImage(String path) {
        try {
            backgroundImage = new ImageIcon(path).getImage();
        } catch (Exception e) {
            // Handle exceptions, e.g., file not found
            e.printStackTrace();
        }

        setContentPane(new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;

                g2d.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            }
        });
    }
    /**
     * Sets the background image of the frame.
     *
     * @param image The background image.
     */
    public void setBackgroundImage(Image image) {
        backgroundImage = image;
        setContentPane(new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;

                g2d.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            }
        });
    }

    /**
     * Loads an image from the specified filename.
     *
     * @param filename The filename of the image.
     * @param path The path to the image in case the getResource does not work.
     * @return         The loaded image.
     */
    public Image loadImage(String filename, String path) {
        try{
            return new ImageIcon(getClass().getClassLoader().getResource(filename)).getImage();
        } catch (Exception e){
            return new ImageIcon(path).getImage();
        }

    }
    /**
     * Loads an image from the specified filename.
     *
     * @param filename The filename of the image.
     * @return         The loaded image.
     */
    public Image loadImage(String filename) {
        return new ImageIcon(getClass().getClassLoader().getResource(filename)).getImage();
    }

    // Private helper methods

    private void initializeFont() {
        font = new Font("SansSerif", Font.PLAIN, 15);
    }

    private void initializeFrame(double widthMultiplier, double widthScale,
                                 double heightMultiplier, double heightScale, String title, boolean resizable) {
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();
        int screenHeight = (int) screenSize.getHeight();
        int screenWidth = (int) screenSize.getWidth();

        frameHeight = (int) Math.round((screenHeight * heightMultiplier) / heightScale);
        frameWidth = (int) Math.round((screenWidth * widthMultiplier) / widthScale);

        setTitle(title);
        setSize(new Dimension(frameWidth, frameHeight));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLocationRelativeTo(null);

        Enumeration<Object> test = UIManager.getDefaults().keys();
        while (test.hasMoreElements()) {
            Object key = test.nextElement();
            Object value = UIManager.get(key);
            if (value instanceof Font) {
                UIManager.put(key, font);
            }
        }
        SwingUtilities.updateComponentTreeUI(this);

        setResizable(resizable);
    }

    private void initializeFrameBasedOnPhi(int phi, double multiplier, double scale,
                                           String title, boolean resizable) {
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();
        int screenWidth = (int) screenSize.getWidth();
        int screenHeight = (int) screenSize.getHeight();

        if (phi == PHI_HEIGHT) {
            frameHeight = (int) Math.round((screenHeight * multiplier / scale));
            frameWidth = (int) Math.round(frameHeight * PHI);
        } else if (phi == PHI_WIDTH) {
            frameWidth = (int) Math.round((screenWidth * multiplier / scale));
            frameHeight = (int) Math.round(frameWidth * PHI);
        } else {
            frameWidth = 200;
            frameHeight = 200;
        }

        setTitle(title);
        setSize(new Dimension(frameWidth, frameHeight));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLocationRelativeTo(null);

        Enumeration<Object> test = UIManager.getDefaults().keys();
        while (test.hasMoreElements()) {
            Object key = test.nextElement();
            Object value = UIManager.get(key);
            if (value instanceof Font) {
                UIManager.put(key, font);
            }
        }
        SwingUtilities.updateComponentTreeUI(this);

        setResizable(resizable);
    }
}
