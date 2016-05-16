package ihm;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import core.ItunesToAndroidProcessor;
import core.UserInputs;
import ihm.util.WidgetUtil;
import ihm.widgets.Label;

public class UserInputsMainView extends JFrame {

  private JPanel container = new JPanel();

  private Label libraryLabel = new Label("Itunes library");
  private Label libraryFileValue = new Label();
  private JButton chooseLibraryFileButton = new JButton("Choose...");

  private JButton runButton = new JButton("Run");

  private UserInputs userInputs = new UserInputs();

  public static final int marginBetweenLabelAndValue = 5;
  public static final int vertical_margin_beween_widgets = 5;
  public static final int horizontal_margin_from_component_and_first_widgets = 5;
  public static final int horizontal_margin_from_component_and_last_widgets = horizontal_margin_from_component_and_first_widgets;
  public static final int vertical_margin_from_component_and_first_widgets = 5;
  public static final int vertical_margin_from_component_and_last_widgets = vertical_margin_from_component_and_first_widgets;
  public static final int widget_height = 20;

  /**
   * 
   */
  private static final long serialVersionUID = -8350678575014786285L;

  public UserInputsMainView() {

    setTitle("Itunes To Android");
    setSize(300, 300);
    setResizable(false);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLocationRelativeTo(null);

    setContentPane(container);
    container.setBackground(Color.white);
    container.setSize(getSize());

    setLayout(null);
    addWidgets();
    replaceWidgets();

    chooseLibraryFileButton.addActionListener(new ChooseLibraryFileButtonListener());
    runButton.addActionListener(new RunButtonListener());

    setVisible(true);
  }

  protected void addWidgets() {
    container.add(libraryLabel);
    container.add(chooseLibraryFileButton);
    container.add(runButton);
  }

  protected void replaceWidgets() {
    resizeWidgets();
    placeWidgetsWithoutLayout();
  }

  protected void resizeWidgets() {

    setLabelSize(libraryLabel);
    chooseLibraryFileButton.setSize((int) chooseLibraryFileButton.getPreferredSize().getWidth(), widget_height);

    runButton.setSize((int) runButton.getPreferredSize().getWidth(), widget_height);

  }

  private void placeWidgetsWithoutLayout() {

    libraryLabel.setLocation(horizontal_margin_from_component_and_first_widgets, vertical_margin_from_component_and_first_widgets);
    chooseLibraryFileButton.setLocation(libraryLabel.getRight() + marginBetweenLabelAndValue, libraryLabel.getY());

    int runButtonX = WidgetUtil.getXSoComponentIsAtHorizontalCenterOfContainer(runButton, container);
    int runButtonY = container.getHeight() - widget_height - vertical_margin_from_component_and_last_widgets - 50;
    runButton.setLocation(runButtonX, runButtonY);
  }

  public UserInputs retrieveUserInputs() {
    return userInputs;
  }

  protected void setLabelSize(JLabel label) {
    label.setSize((int) label.getPreferredSize().getWidth(), widget_height);
  }

  private class ChooseLibraryFileButtonListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
      ItunesLibraryFileChooser itunesLibraryFileChooser = new ItunesLibraryFileChooser();
      userInputs.setItunesLibraryFile(itunesLibraryFileChooser.retrieveItunesLibraryFile());
    }
  }

  private class RunButtonListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
      ItunesToAndroidProcessor itunesToAndroidProcessor = new ItunesToAndroidProcessor();
      itunesToAndroidProcessor.run(userInputs);
    }
  }

}
