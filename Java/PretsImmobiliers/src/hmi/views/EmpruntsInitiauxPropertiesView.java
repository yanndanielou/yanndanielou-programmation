package hmi.views;

import hmi.widgets.Label;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;

import model.Emprunt;

public class EmpruntsInitiauxPropertiesView extends ProjetImmobilierBaseView implements ActionListener {
  private static final long serialVersionUID = -5601747344003397604L;
  private static final EmpruntsInitiauxPropertiesView INSTANCE = new EmpruntsInitiauxPropertiesView();

  private Label title;
  private JButton addEmpruntButton;
  private JPanel empruntsInitauxContainerPanel;
  private List<EmpruntInitialPropertiesPanel> empruntInitiauxPropertyPanels;// = new ArrayList<EmpruntInitialPropertiesPanel>();

  private EmpruntsInitiauxPropertiesView() {
    empruntInitiauxPropertyPanels = new ArrayList<EmpruntInitialPropertiesPanel>();
    loanViewsMediator.setLoanPropertiesView(this);
    //setBackground(Color.RED);
  }

  public static EmpruntsInitiauxPropertiesView getInstance() {
    return INSTANCE;
  }

  @Override
  protected void createWidgets() {
    title = new Label("Emprunts");
    addEmpruntButton = new JButton("+");
    addEmpruntButton.addActionListener(this);
    empruntsInitauxContainerPanel = new JPanel();
  }

  @Override
  protected void placeWidgetsAtInit() {
    setLayout(null);
    addWidgets();
    replaceWidgets();
  }

  private void fillEmprunts() {
    empruntsInitauxContainerPanel.removeAll();
    empruntsInitauxContainerPanel.setLayout(null);
    empruntsInitauxContainerPanel.setBackground(Color.RED);
    if (empruntInitiauxPropertyPanels != null) {
      for (EmpruntInitialPropertiesPanel empruntInitialPropertiesPanel : empruntInitiauxPropertyPanels) {
        empruntsInitauxContainerPanel.add(empruntInitialPropertiesPanel);
        empruntInitialPropertiesPanel.setSize(empruntsInitauxContainerPanel.getWidth(), empruntInitialPropertiesPanel.getRequiredHeight());
        empruntInitialPropertiesPanel.setLocation(0, 0);
      }
    }
  }

  @Override
  protected void addWidgets() {
    add(title);
    add(addEmpruntButton);
    add(empruntsInitauxContainerPanel);
  }

  @Override
  protected void replaceWidgets() {
    resizeWidgets();
    placeWidgets();
    fillEmprunts();
  }

  @Override
  protected void resizeWidgets() {
    setLabelSize(title);
    addEmpruntButton.setSize((int) (getWidth() * 0.3), widget_height);
    empruntsInitauxContainerPanel.setSize(getWidth() - horizontal_margin_from_component_and_first_widgets - horizontal_margin_from_component_and_last_widgets, getHeight() - title.getHeight() - addEmpruntButton.getHeight() - vertical_margin_from_component_and_first_widgets - vertial_margin_beween_widgets - vertical_margin_from_component_and_last_widgets);
  }

  private void placeWidgets() {
    title.setLocation((getWidth() - title.getWidth()) / 2, vertical_margin_from_component_and_first_widgets);
    addEmpruntButton.setLocation((getWidth() - addEmpruntButton.getWidth()) / 2, title.getBottom() + vertial_margin_beween_widgets);
    empruntsInitauxContainerPanel.setLocation(0, addEmpruntButton.getY() + addEmpruntButton.getHeight());
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    if (e.getSource() == addEmpruntButton) {
      loanViewsMediator.onEmpruntAdded();
    }
  }

  public void afterEmpruntCreated(Emprunt emprunt) {
    EmpruntInitialPropertiesPanel empruntInitialPropertiesPanel = new EmpruntInitialPropertiesPanel(emprunt);
    empruntInitiauxPropertyPanels.add(empruntInitialPropertiesPanel);
    fillEmprunts();
  }

  public void afterEmpruntModified(Emprunt emprunt) {
    for (EmpruntInitialPropertiesPanel empruntInitialPropertiesPanel : empruntInitiauxPropertyPanels) {
      if (empruntInitialPropertiesPanel.getEmprunt() == emprunt) {
        empruntInitialPropertiesPanel.refresh();
      }
    }
  }

  @Override
  public void componentResized(ComponentEvent event) {
    if (event.getComponent() == this) {
      replaceWidgets();
    }
  }

  @Override
  public Integer getRequiredWidth() {
    if (empruntInitiauxPropertyPanels.isEmpty()) {
      return null;
    } else {
      return empruntInitiauxPropertyPanels.get(0).getRequiredWidth();
    }
  }
}
