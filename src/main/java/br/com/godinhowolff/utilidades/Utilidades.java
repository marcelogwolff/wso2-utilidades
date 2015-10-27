package br.com.godinhowolff.utilidades;

import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;

/**
 * Classe de utilidades que valida CPF.
 * 
 * Algoritmo retirado de http://goo.gl/UpjuFa
 * 
 * @author marcelo
 *
 */
@WebService(serviceName = "Utilidades")
public class Utilidades{


	@WebMethod(operationName = "validaCPF")
    public  Boolean validaCPF(@WebParam(name = "cpf")String cpf) {  
        if (cpf.length() != 11) { 
            return false;  
        }
        String numDig = cpf.substring(0, 9);  
        return calcDigVerif(numDig).equals(cpf.substring(9, 11));  
    }
    
	 private   String calcDigVerif(String num) {  
	        Integer primDig, segDig;  
	        int soma = 0, peso = 10;  
	        for (int i = 0; i < num.length(); i++)  
	                soma += Integer.parseInt(num.substring(i, i + 1)) * peso--;  
	  
	        if (soma % 11 == 0 | soma % 11 == 1)  
	            primDig = new Integer(0);  
	        else  
	            primDig = new Integer(11 - (soma % 11));  
	  
	        soma = 0;  
	        peso = 11;  
	        for (int i = 0; i < num.length(); i++)  
	                soma += Integer.parseInt(num.substring(i, i + 1)) * peso--;  
	          
	        soma += primDig.intValue() * 2;  
	        if (soma % 11 == 0 | soma % 11 == 1)  
	            segDig = new Integer(0);  
	        else  
	            segDig = new Integer(11 - (soma % 11));  
	  
	        return primDig.toString() + segDig.toString();  
	    }  
	  
	 @WebMethod(operationName = "geraCPF")
	    public  String geraCPF() {  
	        String iniciais = "";  
	        Integer numero;  
	        for (int i = 0; i < 9; i++) {  
	            numero = new Integer((int) (Math.random() * 10));  
	            iniciais += numero.toString();  
	        }  
	        return iniciais + calcDigVerif(iniciais);  
	    }  
	  

}