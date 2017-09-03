package Model;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 *
 * @author Jônatas Trabuco Belotti [jonatas.t.belotti@hotmail.com]
 */
public class ArquivoDadosTreinamento extends Arquivo {

  public ArquivoDadosTreinamento(String caminho, String nome) {
    super(caminho, nome);
  }

  public boolean validarDados() {
    FileReader arq;
    BufferedReader lerArq;
    String linha;
    String[] vetor;
    boolean resposta;
    int i;

    if (super.validarArquivo() == false) {
      return false;
    }

    try {
      resposta = true;
      arq = new FileReader(caminhoArquivo + nomeArquivo);
      lerArq = new BufferedReader(arq);

      linha = lerArq.readLine();
      if (linha.contains("x1")) {
        linha = lerArq.readLine();
      }
      
      while (linha != null) {
        vetor = linha.split("\\s+");
        i = 0;
        
        if (vetor[0].equals("")) {
          i = 1;
        }
        
        for (int j = i; j < vetor.length; j++) {
          try {
            Double.parseDouble(vetor[j].replace(",", "."));
          } catch(NumberFormatException e) {
            resposta = false;
          }
        }
        
        if ((i == 0 && vetor.length != 4) || (i == 1 && vetor.length != 5)) {
          resposta = false;
        }
        
        linha = lerArq.readLine();
      }

      arq.close();
      return resposta;
    } catch (FileNotFoundException ex) {
      return false;
    } catch (IOException ex) {
      return false;
    }
  }

}
