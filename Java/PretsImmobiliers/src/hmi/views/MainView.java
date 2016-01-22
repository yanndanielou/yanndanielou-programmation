package hmi.views;

import hmi.LoanViewsMediator;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

import model.ProjetImmobilier;

public class MainView extends JFrame {

  private static final long serialVersionUID = -1189895025175532900L;
  private static final MainView INSTANCE = new MainView();

  private JPanel contentPane;
  private ProjetImmobilier projetImmobilier;

  private LoanViewsMediator loanViewsMediator;

  private MainView() {
    init();
    //Force mediator creation because of circular dependencies
    loanViewsMediator = LoanViewsMediator.getInstance();
    loanViewsMediator.setMainView(this);
  }

  public static MainView getInstance() {
    return INSTANCE;
  }

  private void init() {
    projetImmobilier = ProjetImmobilier.getInstance();

    setTitle("Calculateur projet immobilier");
    setSize(700, 500);
    setLocationRelativeTo(null);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    contentPane = new JPanel();
    setContentPane(contentPane);

    setLayout(new BorderLayout());

    getContentPane().add(RealEstateView.getInstance(), BorderLayout.NORTH);
    getContentPane().add(EcheancesView.getInstance(), BorderLayout.CENTER);
    getContentPane().add(EmpruntsInitiauxPropertiesView.getInstance(), BorderLayout.EAST);
    getContentPane().add(LoanOverviewView.getInstance(), BorderLayout.SOUTH);

    setVisible(true);
  }
}
