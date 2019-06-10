import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class Client {
	public static void main(String argv[ ]) throws Exception{ 
        String sentence; 
        String modifiedSentence; 
        
        BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in)); 

        System.out.println ("Abrindo conexão na porta 5000 ");
        /*Começando por declarar um socket Cliente. Uma String com o IP e a Porta do servidor com o qual ele deve se conectar*/
        Socket clientSocket = new Socket("127.0.0.1", 5000); 
        /*Streams são os canais de troca de dados que um socket possui. Dois tipos principais, os de saida(output) e os de entrada(input). Os valores enviados 
         * podem ser de string, de inteiros, de bytes ou objetos java. Mais especificamente os DataInputStream e DataOutputStream
         *  servem para troca de dados em byte[]*/
        DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());         
        DataInputStream inFromServer = new DataInputStream(clientSocket.getInputStream()); 
      
        System.out.println ("Digite a mensagem e tecle enter");
        sentence = inFromUser.readLine(); 
      
        /* Uma técnica comum em conexões sockets é enviar, antes da mensagem em si, o tamanho dela. O que você quer enviar
         * nem sempre pode ser resumido em apenas uma linha a ser lida por um BufferedReader (e.g., usando readLine()). Com o envio do tamanho, o programa
         * não corre o risco de fazer buffers grandes para receber os dados do stream
         */
        outToServer.writeInt(sentence.length()); // enviando o tamanho da cadeia
        outToServer.writeBytes(sentence); // enviado a cadeia
        outToServer.flush();
        modifiedSentence = receberMensagemTCP(inFromServer); //recebendo do servidor com o método escrito abaixo
        
        System.out.println("FROM SERVER: " + modifiedSentence); 
        modifiedSentence = modifiedSentence.split(",")[1];
        Double res = (Double.valueOf(modifiedSentence)*2);
        String resp = "RESPOSTA,"+String.valueOf(res);
        outToServer.writeInt(String.valueOf(resp).length()); // enviando o tamanho da cadeia
        outToServer.writeBytes(String.valueOf(resp)); // enviado a cadeia
        outToServer.flush();
        
        System.out.println("Enviar: "+String.valueOf(resp) );
        
        modifiedSentence = receberMensagemTCP(inFromServer); //recebendo do servidor com o método escrito abaixo
        
        
        
        
        System.out.println("Resposta: "+modifiedSentence );
        
        
        clientSocket.close(); //fechando a conexão                         
      } 
    
    /*Esse é um método de recepção de mensagens, imagine que a mensagem foi enviada nos mesmos moldes das mensagens acima
    i.e. tamanho da mensagem +  mensagem*/
    public static String receberMensagemTCP(DataInputStream stream) throws IOException{
        byte[] message = null;//Bytes que irão conter a mensagem, chamamos isso de buffer
        int length = stream.read(); // o primeiro int da mensagem e o tamanho dela, útil pra mensagens que são de mais de uma linha
        if(length>0) {//Se tiver uma mensagem...
            message = new byte[length];//Inicializar o Array de bytes do tamanho da mensagem
            stream.readFully(message, 0, message.length); //Ler os bytes do stream até chegar no tamanho indicado pelo primeiro int
        }
        /*Como vamos focar principalmente em mensagens de formato String, temos ainda que converter o objeto byte[] para um to tipo
        String, por sorte o String tem um construtor especifico para isso*/
        String resultado = new String(message,"ASCII");//Converter de byte[] para um String usando o encoding ASCII
        return resultado;//Retorno da função
    }
}
