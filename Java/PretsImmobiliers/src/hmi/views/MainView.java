package hmi.views;

import hmi.LoanViewsMediator;

import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

import model.ProjetImmobilier;

public class MainView extends JFrame implements ComponentListener {

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
    addComponentListener(this);
  }

  public static MainView getInstance() {
    return INSTANCE;
  }

  private void placeViews() {
    // placeViewsWithBorderLayout();
    placeViewsWithGridBagLayout();
  }

  private void placeViewsWithGridBagLayout() {
    //    System.out.println("Main view: placeViews: begin");

    setLayout(null);

    //printSize();
    getContentPane().removeAll();

    RealEstateView realEstateView = RealEstateView.getInstance();
    realEstateView.setSize(getWidth(), realEstateView.getRequiredHeight());
    realEstateView.setLocation(0, 0);
    getContentPane().add(realEstateView);
    //    realEstateView.printLocationAndSize();

    EmpruntsInitiauxPropertiesView empruntsInitiauxPropertiesView = EmpruntsInitiauxPropertiesView.getInstance();
    int echeancesViewWidth = empruntsInitiauxPropertiesView.getRequiredWidth() != null ? empruntsInitiauxPropertiesView.getRequiredWidth() : (int) (getWidth() * 0.2);

    EcheancesView echeancesView = EcheancesView.getInstance();
    echeancesView.setSize(getWidth() - echeancesViewWidth, (int) (getHeight() * 0.8));
    echeancesView.setLocation(0, realEstateView.getHeight());
    getContentPane().add(echeancesView);
    //   echeancesView.printLocationAndSize();

    empruntsInitiauxPropertiesView.setSize(echeancesViewWidth, echeancesView.getHeight());
    empruntsInitiauxPropertiesView.setLocation(echeancesView.getWidth(), realEstateView.getHeight());
    getContentPane().add(empruntsInitiauxPropertiesView);
    //    empruntsInitiauxPropertiesView.printLocationAndSize();

    LoanOverviewView loanOverviewView = LoanOverviewView.getInstance();
    loanOverviewView.setSize(getWidth(), echeancesView.getHeight() / 10);
    loanOverviewView.setLocation(0, empruntsInitiauxPropertiesView.getY() + empruntsInitiauxPropertiesView.getHeight());
    getContentPane().add(loanOverviewView);
    //   loanOverviewView.printLocationAndSize();

    //    System.out.println("Main view: placeViews: end");
  }

  private void init() {
    projetImmobilier = ProjetImmobilier.getInstance();

    setTitle("Calculateur projet immobilier");
    setSize(700, 500);
    setLocationRelativeTo(null);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    contentPane = new JPanel();
    setContentPane(contentPane);

    placeViews();

    setVisible(true);
  }

  public void afterEmpruntCreated() {
    placeViews();
  }

  @Override
  public void componentResized(ComponentEvent event) {
    if (event.getComponent() == this) {
      //System.out.println("Main view: componentResized:" + event);
      placeViews();
    }
  }

  @Override
  public void componentMoved(ComponentEvent e) {

  }

  @Override
  public void componentShown(ComponentEvent e) {
  }

  @Override
  public void componentHidden(ComponentEvent e) {

  }

  private void printSize() {
    System.out.println("Main view: width:" + getWidth() + ", height:" + getHeight());
  }

}
