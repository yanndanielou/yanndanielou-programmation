package hmi.views;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import model.Emprunt;

public class EmpruntsInitiauxPropertiesView extends ProjetImmobilierBaseView implements ActionListener {
  private static final long serialVersionUID = -5601747344003397604L;
  private static final EmpruntsInitiauxPropertiesView INSTANCE = new EmpruntsInitiauxPropertiesView();

  private JLabel title;
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
    title = new JLabel("Emprunts");
    addEmpruntButton = new JButton("+");
    addEmpruntButton.addActionListener(this);
    empruntsInitauxContainerPanel = new JPanel();
  }

  @Override
  protected void placeWidgets() {
    setLayout(new BorderLayout());
    add(title, BorderLayout.NORTH);
    add(empruntsInitauxContainerPanel, BorderLayout.CENTER);
    add(addEmpruntButton, BorderLayout.EAST);
  }

  private void fillEmprunts() {
    empruntsInitauxContainerPanel.removeAll();
    int rows = empruntInitiauxPropertyPanels.size();
    int columns = 1;
    empruntsInitauxContainerPanel.setLayout(new GridLayout(rows, columns));
    if (empruntInitiauxPropertyPanels != null) {
      for (EmpruntInitialPropertiesPanel empruntInitialPropertiesPanel : empruntInitiauxPropertyPanels) {
        add(empruntInitialPropertiesPanel);
      }
    }
  }

  public void redraw() {
    removeAll();
    placeWidgets();
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
}
