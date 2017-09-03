package Model;

import View.Comunicador;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 *
 * @author Jônatas Trabuco Belotti [jonatas.t.belotti@hotmail.com]
 */
public class Perceptron {

  private final double PASSO_APRENDIZAGEM = 0.01;

  private double peso0;
  private double peso1;
  private double peso2;
  private double peso3;
  private double ultimaResposta;
  private int numEpocasTreinamento;

  public Perceptron(double peso0, double peso1, double peso2, double peso3) {
    this.peso0 = peso0;
    this.peso1 = peso1;
    this.peso2 = peso2;
    this.peso3 = peso3;
    this.ultimaResposta = 0.0;
    this.numEpocasTreinamento = 0;
  }

  public double getUltimaResposta() {
    return ultimaResposta;
  }

  public int getNumEpocasTreinamento() {
    return numEpocasTreinamento;
  }

  public double getPeso0() {
    return peso0;
  }

  public double getPeso1() {
    return peso1;
  }

  public double getPeso2() {
    return peso2;
  }

  public double getPeso3() {
    return peso3;
  }

  public boolean treinarRede(ArquivoDadosTreinamento arquivoTreinamento) {
    FileReader arq;
    BufferedReader lerArq;
    String linha;
    String[] vetor;
    int i;
    int acertou;
    int maiorAcerto = 0;
    boolean erro = true;
    double entrada1;
    double entrada2;
    double entrada3;
    double saidaEsperada;
    
    Comunicador.iniciarLog("Início treinamento (" + peso0 + "; " + peso1 + "; " + peso2 + "; " + peso3 + ")");
    this.numEpocasTreinamento = 0;

    try {
      while (erro) {
        this.numEpocasTreinamento++;
        acertou = 0;
        erro = false;
        arq = new FileReader(arquivoTreinamento.getCaminhoCompleto());
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

          entrada1 = Double.parseDouble(vetor[i++].replace(",", "."));
          entrada2 = Double.parseDouble(vetor[i++].replace(",", "."));
          entrada3 = Double.parseDouble(vetor[i++].replace(",", "."));
          saidaEsperada = Double.parseDouble(vetor[i].replace(",", "."));
          
          this.ultimaResposta = funcaoDegrauBipolar((entrada1 * this.peso1) + (entrada2 * this.peso2) + (entrada3 * this.peso3) + (-1.0 * this.peso0));
          
          if (this.ultimaResposta != saidaEsperada) {
            peso0 = peso0 + (PASSO_APRENDIZAGEM * (saidaEsperada - this.ultimaResposta) * -1.0);
            peso1 = peso1 + (PASSO_APRENDIZAGEM * (saidaEsperada - this.ultimaResposta) * entrada1);
            peso2 = peso2 + (PASSO_APRENDIZAGEM * (saidaEsperada - this.ultimaResposta) * entrada2);
            peso3 = peso3 + (PASSO_APRENDIZAGEM * (saidaEsperada - this.ultimaResposta) * entrada3);
            erro = true;
          } else {
            acertou++;
          }

          linha = lerArq.readLine();
        }

        arq.close();
        
        if (acertou > maiorAcerto) {
          maiorAcerto = acertou;
          Comunicador.addLog("Epoca " + this.numEpocasTreinamento + " acertou " + acertou + " ("+peso0+"; "+peso1+"; "+peso2+"; "+peso3+")");
        }
        
      }
      
      Comunicador.addLog("Fim do treinamento.");
    } catch (FileNotFoundException ex) {
      return false;
    } catch (IOException ex) {
      return false;
    }

    return true;
  }
  
  public String classificar(double valor1, double valor2, double valor3) {
    String resposta;
    
    resposta = "Sem classificação";
    
    this.ultimaResposta = funcaoDegrauBipolar((valor1 * this.peso1) + (valor2 * this.peso2) + (valor3 * this.peso3) + (-1.0 * this.peso0));
    
    if (this.ultimaResposta == -1.0) {
      resposta = "Classe P1";
    }
    if (this.ultimaResposta == 1.0) {
      resposta = "Classe P2";
    }
    
    return resposta;
  }
  
  private double funcaoDegrauBipolar(double valor) {
    if (valor == 0.0) {
      return 0.0;
    }
    
    if (valor < 0.0) {
      return -1.0;
    }
    
    if (valor > 0.0) {
      return 1.0;
    }
    
    return 0.0;
  }

}
