package raven.progressindicator;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.LayoutManager;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import javax.accessibility.Accessible;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.ListModel;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author Raven
 * @param <E>
 */
public class ProgressIndicator<E> extends JComponent implements Accessible {

    public ListModel<E> getModel() {
        return model;
    }

    public void setModel(ListModel<E> model) {
        this.model = model;
        repaint();
        revalidate();
    }

    public int getProgressIndex() {
        return (int) progress;
    }

    public float getProgress() {
        return progress;
    }

    public void setProgress(float progress) {
        this.progress = progress;
        repaint();
    }

    public Font getProgressFont() {
        return progressFont;
    }

    public void setProgressFont(Font progressFont) {
        this.progressFont = progressFont;
        repaint();
        revalidate();
    }

    public Color getProgressColor() {
        return progressColor;
    }

    public void setProgressColor(Color progressColor) {
        this.progressColor = progressColor;
        repaint();
    }

    public Color getProgressColorGradient() {
        return progressColorGradient;
    }

    public void setProgressColorGradient(Color progressColorGradient) {
        this.progressColorGradient = progressColorGradient;
        repaint();
    }

    public Color getProgressColorSelected() {
        return progressColorSelected;
    }

    public void setProgressColorSelected(Color progressColorSelected) {
        this.progressColorSelected = progressColorSelected;
        repaint();
    }

    public int getProgressLineSize() {
        return progressLineSize;
    }

    public void setProgressLineSize(int progressLineSize) {
        this.progressLineSize = progressLineSize;
        repaint();
        revalidate();
    }

    public int getProgressSize() {
        return progressSize;
    }

    public void setProgressSize(int progressSize) {
        this.progressSize = progressSize;
        repaint();
        revalidate();
    }

    public int getProgressSpaceLabel() {
        return progressSpaceLabel;
    }

    public void setProgressSpaceLabel(int progressSpaceLabel) {
        this.progressSpaceLabel = progressSpaceLabel;
        repaint();
        revalidate();
    }

    public boolean isProgressFill() {
        return progressFill;
    }

    public void setProgressFill(boolean progressFill) {
        this.progressFill = progressFill;
        repaint();
    }

    private ListModel<E> model;
    private PanelSlider panelSlider;
    private float progress = -1;
    private Font progressFont;
    private Color progressColor = new Color(63, 171, 222);
    private Color progressColorGradient = null;
    private Color progressColorSelected = Color.WHITE;
    private int progressLineSize = 3;
    private int progressSize = 35;
    private int progressSpaceLabel = 5;
    private boolean progressFill = false;

    public ProgressIndicator() {
        setOpaque(true);
        setBackground(new Color(250, 250, 250));
        setForeground(new Color(200, 200, 200));
        setBorder(new EmptyBorder(5, 20, 5, 20));
        Font lbFont = new JLabel().getFont();
        progressFont = lbFont.deriveFont(Font.BOLD, lbFont.getSize() + 5f);
        setLayout(new LayoutManager() {
            @Override
            public void addLayoutComponent(String name, Component comp) {
            }

            @Override
            public void removeLayoutComponent(Component comp) {
            }

            @Override
            public Dimension preferredLayoutSize(Container parent) {
                return getSize(parent);
            }

            @Override
            public Dimension minimumLayoutSize(Container parent) {
                return getSize(parent);
            }

            @Override
            public void layoutContainer(Container parent) {
            }
        });
    }

    private Dimension getSize(Container parent) {
        if (model != null) {
            int width = (getInsets().left + getInsets().left) + ((progressSize + 5) * model.getSize());
            int fontheight = getFontMetrics(getFont()).getHeight();
            int height = (getInsets().top + getInsets().bottom) + (progressSize) + progressSpaceLabel + fontheight;
            return new Dimension(width, height);
        } else {
            return new Dimension(100, 100);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        if (isOpaque()) {
            g.setColor(getBackground());
            g.fillRect(0, 0, getWidth(), getHeight());
        }
        if (model != null && model.getSize() > 0) {
            BufferedImage img = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2 = img.createGraphics();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            int x = model.getSize() == 1 ? getWidth() / 2 - (progressSize / 2) : getInsets().left;
            int y = getInsets().top;
            int width = getWidth() - x * 2 - progressSize;
            int size = model.getSize() == 1 ? width : width / (model.getSize() - 1);
            int ly = (progressSize - progressLineSize) / 2 + y;
            int lx = x + progressSize / 2;
            Area line = new Area(new RoundRectangle2D.Double(lx, ly, width, progressLineSize, progressLineSize, progressLineSize));
            boolean paint;
            for (int i = 0; i < model.getSize(); i++) {
                paint = i > (int) progress;
                int sx = x + (i * size);
                Shape box = new Ellipse2D.Double(sx, y, progressSize, progressSize);
                Area area = new Area(box);
                line.subtract(area);
                area.subtract(new Area(new Ellipse2D.Double(sx + progressLineSize, y + progressLineSize, progressSize - progressLineSize * 2, progressSize - progressLineSize * 2)));
                g2.setComposite(AlphaComposite.SrcOver);
                g2.setColor(getForeground());
                if (paint) {
                    g2.fill(area);
                }
                //  Draw Progress Number
                g2.setFont(progressFont);
                FontMetrics m = g2.getFontMetrics();
                String text = i + 1 + "";
                Rectangle2D r2 = m.getStringBounds(text, g2);
                double fx = (progressSize - r2.getWidth()) / 2f;
                double fy = (progressSize - r2.getHeight()) / 2f;
                if (paint) {
                    g2.drawString(text, (int) (sx + fx), (int) (y + fy + m.getAscent()));
                }
                //  Drea Label Text
                g2.setFont(getFont());
                FontMetrics m2 = g2.getFontMetrics();
                g2.setColor(getForeground());
                String label = model.getElementAt(i).toString();
                r2 = m2.getStringBounds(label, g2);
                double lfx = (progressSize - r2.getWidth()) / 2f;
                double lfy = y + progressSize + progressSpaceLabel;
                if (paint) {
                    g2.drawString(label, (int) (sx + lfx), (int) (lfy + m2.getAscent()));
                }
                if (i <= (int) progress) {
                    setColor(g2);
                    g2.fill(progressFill ? box : area);
                    g2.setFont(getFont());
                    g2.drawString(label, (int) (sx + lfx), (int) (lfy + m2.getAscent()));
                    if (progressFill) {
                        g2.setColor(progressColorSelected);
                    }
                    g2.setFont(progressFont);
                    g2.drawString(text, (int) (sx + fx), (int) (y + fy + m.getAscent()));
                } else {
                    if (i == Math.ceil(progress)) {
                        float c = progress + 1 - i;
                        if (c > 0) {
                            g2.setComposite(AlphaComposite.SrcOver.derive(c));
                            setColor(g2);
                            g2.fill(progressFill ? box : area);
                            g2.setFont(getFont());
                            g2.drawString(label, (int) (sx + lfx), (int) (lfy + m2.getAscent()));
                            if (progressFill) {
                                g2.setColor(progressColorSelected);
                            }
                            g2.setFont(progressFont);
                            g2.drawString(text, (int) (sx + fx), (int) (y + fy + m.getAscent()));
                        }
                    }
                }
            }
            if (model.getSize() > 1) {
                g2.setComposite(AlphaComposite.SrcOver);
                float s = model.getSize() - 1;
                float p = progress / s;
                g2.setColor(getForeground());
                g2.fill(line);
                setColor(g2);
                line.intersect(new Area(new Rectangle2D.Double(lx, ly, width * p, progressLineSize)));
                g2.fill(line);
            }
            g2.dispose();
            g.drawImage(img, 0, 0, null);
        }
        super.paintComponent(g);
    }

    private void setColor(Graphics2D g2) {
        if (progressColorGradient == null) {
            g2.setColor(progressColor);
        } else {
            g2.setPaint(new GradientPaint(0, 0, progressColor, getWidth(), 0, progressColorGradient));
        }
    }

    public void initSlider(PanelSlider slider) {
        panelSlider = slider;
        slider.addEventSliderAnimatorChanged((PanelSlider.SliderType type, float f) -> {
            if (type == PanelSlider.SliderType.RIGHT_TO_LEFT) {
                int index = (int) getProgress();
                setProgress(index + f);
            } else {
                float index = (float) Math.ceil(getProgress());
                setProgress(index - f);
            }
        });
        slider.showSlid(slider.getSliderComponent()[getProgressIndex()], PanelSlider.SliderType.NONE);
    }

    public void previous() {
        if (panelSlider != null) {
            if (panelSlider.isSlidAble()) {
                if (getProgress() > 0) {
                    panelSlider.showSlid(panelSlider.getSliderComponent()[getProgressIndex() - 1], PanelSlider.SliderType.LEFT_TO_RIGHT);
                }
            }
        }
    }

    public void next() {
        if (panelSlider != null) {
            if (panelSlider.isSlidAble()) {
                if (getProgress() < getModel().getSize() - 1) {
                    panelSlider.showSlid(panelSlider.getSliderComponent()[getProgressIndex() + 1], PanelSlider.SliderType.RIGHT_TO_LEFT);
                }
            }
        }
    }
}
