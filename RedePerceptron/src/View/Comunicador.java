package View;

import javax.swing.JTextArea;

/**
 *
 * @author Jônatas Trabuco Belotti [jonatas.t.belotti@hotmail.com]
 */
public abstract class Comunicador {
  private static JTextArea jTxtCampo = null;

  public static void setCampo(JTextArea jTxtLog) {
    jTxtCampo = jTxtLog;
  }
  
  public static void addLog(String log) {
    if (jTxtCampo != null) {
      jTxtCampo.append("\n"+log);;
    }
  }

  public static void iniciarLog(String string) {
    if (jTxtCampo != null) {
      jTxtCampo.setText(string);
    }
  }

}
