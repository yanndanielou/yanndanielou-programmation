package hmi.views;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;

public class EmpruntsInitiauxPropertiesView extends ProjetImmobilierBaseView implements ActionListener {
  private static final long serialVersionUID = -5601747344003397604L;
  private static final EmpruntsInitiauxPropertiesView INSTANCE = new EmpruntsInitiauxPropertiesView();

  private JLabel capitalEmprunteLabel;
  private JFormattedTextField capitalEmprunteInput;
  private JLabel annualInterestRateLabel;
  private JFormattedTextField annualInterestRateInput;
  private JLabel monthlyPaymentLabel;
  private JFormattedTextField monthlyPaymentInput;
  private JLabel nombreMensualitesLabel;
  private JFormattedTextField nombreMensualitesInput;
  private JButton startSimulationButton;

  private EmpruntsInitiauxPropertiesView() {
    loanViewsMediator.setLoanPropertiesView(this);
  }

  public static EmpruntsInitiauxPropertiesView getInstance() {
    return INSTANCE;
  }

  @Override
  protected void createWidgets() {
    capitalEmprunteLabel = new JLabel("Capital emprunte");
    capitalEmprunteInput = new JFormattedTextField(NumberFormat.getNumberInstance());
    annualInterestRateLabel = new JLabel("Taux intérêt annuel");
    annualInterestRateInput = new JFormattedTextField(NumberFormat.getPercentInstance());
    monthlyPaymentLabel = new JLabel("Mensualité");
    monthlyPaymentInput = new JFormattedTextField(NumberFormat.getNumberInstance());
    nombreMensualitesLabel = new JLabel("Nombre mensualites");
    nombreMensualitesInput = new JFormattedTextField(NumberFormat.getIntegerInstance());
    startSimulationButton = new JButton("Start simulation");
    startSimulationButton.addActionListener(this);
  }

  @Override
  protected void placeWidgets() {
    int columns = 2;
    int rows = 5;
    setLayout(new GridLayout(rows, columns));
    add(capitalEmprunteLabel);
    add(capitalEmprunteInput);
    add(annualInterestRateLabel);
    add(annualInterestRateInput);
    add(monthlyPaymentLabel);
    add(monthlyPaymentInput);
    add(nombreMensualitesLabel);
    add(nombreMensualitesInput);
    add(startSimulationButton);
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    if (e.getSource() == startSimulationButton) {
      Double capitalEmprunte = getValueAsDouble(capitalEmprunteInput);
      Double annualInterestRate = getValueAsDouble(annualInterestRateInput);
      Double monthlyPayment = getValueAsDouble(monthlyPaymentInput);
      Long nombreMensualites = (Long) nombreMensualitesInput.getValue();
      loanViewsMediator.onEmpruntAdded(capitalEmprunte, annualInterestRate, monthlyPayment, nombreMensualites);
    }
  }

}
