/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package emulador;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;
import javafx.scene.control.Button;

/**
 *
 * @author Marcos
 */
public class FXMLDocumentController implements Initializable {
    public String palavraemissor="";
    public String palavrareceptor="";
    public char[] palavraemissorchar = new char[100];
    public char[] palavrareceptorchar = new char[100];
    public int[] palavraemissorint = new int[100];
    public int[] palavrareceptorint = new int[100];
    public int numerodeletras;
    public String hexa="";
    public String bina="";
    public String deci="";
    public int[] binaseparado = new int[100];
    public int[] binaseparadomanchester = new int[200];
    
    int clock = 1;
    int j = 0;
    
    @FXML
    private CategoryAxis x;

    @FXML
    private NumberAxis y;
    
    @FXML
    private LineChart<?, ?> LineChart; 
    
    @FXML
    public Label label;
    
    @FXML
    public TextArea tabinaemissor;
    
    @FXML
    public TextArea tadeciemissor;
    
    @FXML
    public TextArea tahexaemissor;
    
    @FXML
    public TextArea tabinareceptor;
    
    @FXML
    public TextArea tadecireceptor;
    
    @FXML
    public TextField tfpalavraemissor;
    
    @FXML
    public TextField tfpalavrareceptor;
    
    @FXML
    public TextField tfnumerodeletras;
    
    @FXML
    public Button btnClock;
    
    @FXML
    public Button btncodificar;
    
    @FXML
    public Button btntransmitir;
    
    @FXML
    public Button btndecodificar;
    
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        System.out.println("Classe principal");
        
        
    }
    
    
    
    
    @FXML
    public void handleButtonAction(ActionEvent event) {
        System.out.println("You clicked me!");
        label.setText("oi!");
    }
    @FXML
    public void codificarButtonAction(ActionEvent event) {
        System.out.println("Botão codificar!");
        palavraemissor = tfpalavraemissor.getText();
        tfnumerodeletras.setText(Integer.toString(palavraemissor.length()));
        numerodeletras=Integer.parseInt(tfnumerodeletras.getText());
        System.out.println(palavraemissor);
        palavraemissorchar = palavraemissor.toCharArray();
        System.out.println(palavraemissorchar);
        System.out.println((int)palavraemissorchar[0]);
        for(int i=0;i<numerodeletras;i++){
            System.out.println("Iteracao:"+ i +" :: -->" + (int)palavraemissorchar[i] );
            palavraemissorint[i] = (int)palavraemissorchar[i];
        }
        //////////////////DECI////////////////////////////////////
        deci="";
        hexa="";
        bina="";
        for(int i=0;i<numerodeletras;i++){
            deci+=(int)palavraemissorchar[i];
        }
        tadeciemissor.setText(deci);
        //////////////////HEXA////////////////////////////////////
        for(int i=0;i<numerodeletras;i++){
            hexa+=Integer.toHexString((int)palavraemissorchar[i]);
        }
        tahexaemissor.setText(hexa);
        /////////////////BINA////////////////////////////////////
        for(int i=0;i<numerodeletras;i++){
            bina+=Integer.toBinaryString((int)palavraemissorchar[i]);
        }
        tabinaemissor.setText(bina);
        
        
    }
    
    @FXML
    public void transmitirButtonAction(ActionEvent event) {
        System.out.println("You clicked me2!");
        tabinareceptor.setText(bina);
        
        //System.out.println("Valor com erro"+bina.substring(1,2));
        /////////////////BINASEPARADO///////////////tira de uma string e coloca em um vetor
        for(int i=0;i<(7*numerodeletras);i++){
            binaseparado[i] = Integer.parseInt(bina.substring(i,(i+1)));
        }
        
        //////////Codigo do grafico/////
        XYChart.Series series = new XYChart.Series();
        
        for(int i=0;i<(7*numerodeletras);i++){
            //series.getData().add(new XYChart.Data(i,binaseparado[i]));
            series.getData().add(new XYChart.Data(Integer.toString(i),binaseparado[i]));
        }
        System.out.println("You clicked me2!");
        
        //LineChart.getData().addAll(series);
    }
    
    
    @FXML
    public void btnClock(ActionEvent event) {//Manchester
        //////////Codigo do grafico2/////
        XYChart.Series series2 = new XYChart.Series();
        //binaseparado[i]
        //binaseparadomanchester[i]
        for(int i=0;i<(7*numerodeletras);i++){
            if(clock==0){              //anterior é 0
                if(binaseparado[i]==0){//presente é 0
                    binaseparadomanchester[j]=1;
                    j++;
                    binaseparadomanchester[j]=(-1);
                    j++;
                }else{                 //presente é 1
                    binaseparadomanchester[j]=(-1);
                    j++;
                    binaseparadomanchester[j]=1;
                    j++;
                }
                clock=binaseparado[i];    
            }else{                     //anterior é 1
                if(binaseparado[i]==0){//presente é 0
                    binaseparadomanchester[j]=1;
                    j++;
                    binaseparadomanchester[j]=(-1);
                    j++;
                }else{                 //presente é 1
                    binaseparadomanchester[j]=(-1);
                    j++;
                    binaseparadomanchester[j]=1;
                    j++;
                }
                clock=binaseparado[i];
            }   
        }
        clock=1;
        for(int i=0;i<(7*(numerodeletras*2));i++){
            if(clock==1){//anterior 1//
                if(binaseparadomanchester[i]==-1){//presente 0
                    series2.getData().add(new XYChart.Data(Integer.toString(i),1));
                    series2.getData().add(new XYChart.Data(Integer.toString(i),binaseparadomanchester[i]));
                }else{   //presente 1
                    series2.getData().add(new XYChart.Data(Integer.toString(i),1));
                    series2.getData().add(new XYChart.Data(Integer.toString(i),binaseparadomanchester[i]));
                }
            }else{       //anterior 0//
                if(binaseparadomanchester[i]==-1){    //presente 0
                    series2.getData().add(new XYChart.Data(Integer.toString(i),-1));
                    series2.getData().add(new XYChart.Data(Integer.toString(i),binaseparadomanchester[i]));
                }else{   //presente 1
                    series2.getData().add(new XYChart.Data(Integer.toString(i),-1));
                    series2.getData().add(new XYChart.Data(Integer.toString(i),binaseparadomanchester[i]));
                }
            }
            series2.getData().add(new XYChart.Data(Integer.toString(i),binaseparadomanchester[i]));
            clock=binaseparadomanchester[i];
        }    
        
        System.out.println("You clicked me2!");
        
        LineChart.getData().addAll(series2);
    }
    @FXML
    public void decodificarButtonAction(ActionEvent event) {
        System.out.println("You clicked me3!");
        
        
        System.out.println("String binaria: "+bina);
        j=0;
        for(int i=0;i<numerodeletras;i++){
            palavrareceptorint[i]=Integer.parseInt(bina.substring(j, j+7),2);
            System.out.println("Controle de iteracao i="+i+" Substring="+bina.substring(j, j+7));
            j+=7;
        }
        System.out.println("vetor decimal(inteiro):");
        deci="";
        for(int i=0;i<numerodeletras;i++){
            System.out.print(palavrareceptorint[i]);
            deci+=palavrareceptorint[i];
        }
        tadecireceptor.setText(deci);
        //
        for(int i=0;i<numerodeletras;i++){
            palavrareceptorchar[i]=(char)palavrareceptorint[i];
            System.out.println("Caractere char n"+i+" ->"+(char)palavrareceptorint[i]);
        }
        for(int i=0;i<numerodeletras;i++){
            palavrareceptor+=palavrareceptorchar[i];
        }
        tfpalavrareceptor.setText(palavrareceptor);
    }
    
        
    
}
