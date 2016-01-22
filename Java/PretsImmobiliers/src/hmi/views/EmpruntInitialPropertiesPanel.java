package hmi.views;

import hmi.LoanViewsMediator;

import java.awt.GridLayout;
import java.text.NumberFormat;

import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;

import model.ProjetImmobilier;

public class EmpruntInitialPropertiesPanel extends JPanel {

  private static final long serialVersionUID = -5637680529592116720L;

  private static final EmpruntInitialPropertiesPanel INSTANCE = new EmpruntInitialPropertiesPanel();

  protected LoanViewsMediator loanViewsMediator;
  protected ProjetImmobilier projetImmobilier;

  private JLabel capitalEmprunteLabel;
  private JFormattedTextField capitalEmprunteInput;
  private JLabel annualInterestRateLabel;
  private JFormattedTextField annualInterestRateInput;
  private JLabel monthlyPaymentLabel;
  private JFormattedTextField monthlyPaymentInput;
  private JLabel nombreMensualitesLabel;
  private JFormattedTextField nombreMensualitesInput;

  protected EmpruntInitialPropertiesPanel() {
    init();
  }

  private void init() {
    projetImmobilier = ProjetImmobilier.getInstance();
    loanViewsMediator = LoanViewsMediator.getInstance();
    createWidgets();
    placeWidgets();
  }

  public static EmpruntInitialPropertiesPanel getInstance() {
    return INSTANCE;
  }

  protected void createWidgets() {
    capitalEmprunteLabel = new JLabel("Capital emprunte");
    capitalEmprunteInput = new JFormattedTextField(NumberFormat.getNumberInstance());
    annualInterestRateLabel = new JLabel("Taux intérêt annuel");
    annualInterestRateInput = new JFormattedTextField(NumberFormat.getPercentInstance());
    monthlyPaymentLabel = new JLabel("Mensualité");
    monthlyPaymentInput = new JFormattedTextField(NumberFormat.getNumberInstance());
    nombreMensualitesLabel = new JLabel("Nombre mensualites");
    nombreMensualitesInput = new JFormattedTextField(NumberFormat.getIntegerInstance());
  }

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
  }

}
