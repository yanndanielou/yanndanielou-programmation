package hmi.views;

import java.awt.GridLayout;
import java.text.NumberFormat;

import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.Document;

public class RealEstateView extends ProjetImmobilierBaseView implements DocumentListener {

  private static final long serialVersionUID = 863212423235558907L;

  private static final RealEstateView INSTANCE = new RealEstateView();

  private JLabel prixNetAcheteurLabel;
  private JFormattedTextField prixNetAcheteurInput;
  private JLabel apportPersonnelLabel;
  private JFormattedTextField apportPersonnelInput;
  private JLabel fraisNotaireLabel;
  private JLabel fraisNotaireValue;

  private RealEstateView() {
    loanViewsMediator.setRealEstateView(this);
  }

  public static RealEstateView getInstance() {
    return INSTANCE;
  }

  @Override
  protected void createWidgets() {
    prixNetAcheteurLabel = new JLabel("Prix net acheteur");
    prixNetAcheteurInput = new JFormattedTextField(NumberFormat.getNumberInstance());
    prixNetAcheteurInput.getDocument().addDocumentListener(this);
    apportPersonnelLabel = new JLabel("Apport personnel");
    apportPersonnelInput = new JFormattedTextField(NumberFormat.getNumberInstance());
    fraisNotaireLabel = new JLabel("Frais notaire");
    fraisNotaireValue = new JLabel();
  }

  @Override
  protected void placeWidgets() {
    int columns = 2;
    int rows = 3;
    setLayout(new GridLayout(rows, columns));
    add(prixNetAcheteurLabel);
    add(prixNetAcheteurInput);
    add(apportPersonnelLabel);
    add(apportPersonnelInput);
    add(fraisNotaireLabel);
    add(fraisNotaireValue);
  }

  @Override
  public void changedUpdate(DocumentEvent event) {
    onTextFieldChanged(event);
  }

  @Override
  public void insertUpdate(DocumentEvent event) {
    onTextFieldChanged(event);
  }

  @Override
  public void removeUpdate(DocumentEvent event) {
    onTextFieldChanged(event);
  }

  private void onTextFieldChanged(DocumentEvent event) {
    JFormattedTextField textField = getTextFieldFromEvent(event);
    if (textField == prixNetAcheteurInput) {
      int prixNetAcheteur = getTextAsInt(prixNetAcheteurInput);
      onPrixNetAcheteurModified(prixNetAcheteur);
    } else if (textField == apportPersonnelInput) {
      int apportPersonnel = getTextAsInt(apportPersonnelInput);
      onApportPersonnelModified(apportPersonnel);
    }
  }

  private void onApportPersonnelModified(int apportPersonnel) {
    loanViewsMediator.onApportPersonnelModified(apportPersonnel);
  }

  private void onPrixNetAcheteurModified(int prixNetAcheteur) {
    loanViewsMediator.onPrixNetAcheteurModified(prixNetAcheteur);
  }

  private JFormattedTextField getTextFieldFromEvent(DocumentEvent event) {
    Document eventDocument = event.getDocument();
    if (eventDocument == prixNetAcheteurInput.getDocument()) {
      return prixNetAcheteurInput;
    } else if (eventDocument == apportPersonnelInput.getDocument()) {
      return apportPersonnelInput;
    }
    return null;
  }

  public void afterPrixNetAcheteurModified() {
    updateFraisNotaireValue();
  }

  private void updateFraisNotaireValue() {
    fraisNotaireValue.setText(String.valueOf(projetImmobilier.getRealEstate().getFraisNotaire()));
  }
}
