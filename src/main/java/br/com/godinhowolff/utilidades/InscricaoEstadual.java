package br.com.godinhowolff.utilidades;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;


@WebService
public class InscricaoEstadual {
	
        private final String RIO_GRANDE_DO_SUL = "###-#######";
        private final String SANTA_CATARINA = "###.###.###";
        private final String PARANA = "########-##";
        private final String SAO_PAULO = "###.###.###.###";
        private final String MINAS_GERAIS = "#########.##-##";
        private final String RIO_DE_JANEIRO = "##.###.##-#";
        private final String ESPÍRITO_SANTO = "###.###.##-#";
        private final String BAHIA = "###.###.##-#";
        private final String SERGIPE = "#########-#";
        private final String ALAGOAS = "#########";
        private final String PERNAMBUCO = "##.#.###.#######-#";
        private final String PARAIBA = "########-#";
        private final String RIO_GRANDE_DO_NORTE = "##.###.###-#";
        private final String PIAUI = "#########";
        private final String MARANHAO = "#########";
        private final String CEARA = "########-#";
        private final String GOIAS = "##.###.###-#";
        private final String TOCANTINS = "###########";
        private final String MATO_GROSSO = "#########";
        private final String MATO_GROSSO_DO_SUL = "#########";
        private final String DISTRITO_FEDERAL = "###########-##";
		private final String AMAZONAS = "##.###.###-#";
        private final String ACRE = "##.###.###/###-##";
        private final String PARA = "##-######-#";
        private final String RONDONIA = "###.#####-#";
        private final String RORAIMA = "########-#";
        private final String AMAPA = "#########";
        
        private final String INSCRICAO_ESTADUAL_INVALIDA = "Inscrição estadual inválida";

        private final String ESTADO_NAO_ENCONTRADO = "Estado não encontrado";
        private final String DOIS_PONTOS = ":";
        private final String QUANTIDADE_DE_DIGITOS_INVALIDA = "Quantidade de dígitos inválida";
        private final String DIGITO_VERIFICADOR_INVALIDO = "Dígito verificador inválido";

	@WebMethod(operationName="validaInscricaoEstadual")
	@WebResult(name="resultado")
	public Boolean validaInscricaoEstadual(@WebParam(name="inscricaoEstaduao") String inscricaoEstadual,
			@WebParam(name="siglaUf") String siglaUf) {
		try {
			new InscricaoEstadual().valida(inscricaoEstadual, siglaUf);
			return true;
		} catch (Exception e) {
			return false;
		}

	}
	
	@WebMethod(operationName="validaFormataInscricaoEstadual")
	@WebResult(name="resultado")
	public String validaFormataInscricaoEstadual(@WebParam(name="inscricaoEstaduao") String inscricaoEstadual,
			@WebParam(name="siglaUf") String siglaUf) {
		try {
			InscricaoEstadual ie = new InscricaoEstadual();
			String ieFormatada = ie.formataMascara(inscricaoEstadual, siglaUf);
			ie.valida(inscricaoEstadual, siglaUf);
			return ieFormatada;
		} catch (Exception e) {
			return this.INSCRICAO_ESTADUAL_INVALIDA;
		}

	}

	private  void valida(String inscricaoEstadual, String siglaUf)
			throws Exception {
		String strIE = removeMascara(inscricaoEstadual);
		siglaUf = siglaUf.toUpperCase();
		if (siglaUf.equals("AC")) {
			validaIEAcre(strIE);
		} else if (siglaUf.equals("AL")) {
			validaIEAlagoas(strIE);
		} else if (siglaUf.equals("AP")) {
			validaIEAmapa(strIE);
		} else if (siglaUf.equals("AM")) {
			validaIEAmazonas(strIE);
		} else if (siglaUf.equals("BA")) {
			validaIEBahia(strIE);
		} else if (siglaUf.equals("CE")) {
			validaIECeara(strIE);
		} else if (siglaUf.equals("ES")) {
			validaIEEspiritoSanto(strIE);
		} else if (siglaUf.equals("GO")) {
			validaIEGoias(strIE);
		} else if (siglaUf.equals("MA")) {
			validaIEMaranhao(strIE);
		} else if (siglaUf.equals("MT")) {
			validaIEMatoGrosso(strIE);
		} else if (siglaUf.equals("MS")) {
			validaIEMatoGrossoSul(strIE);
		} else if (siglaUf.equals("MG")) {
			validaIEMinasGerais(strIE);
		} else if (siglaUf.equals("PA")) {
			validaIEPara(strIE);
		} else if (siglaUf.equals("PB")) {
			validaIEParaiba(strIE);
		} else if (siglaUf.equals("PR")) {
			validaIEParana(strIE);
		} else if (siglaUf.equals("PE")) {
			validaIEPernambuco(strIE);
		} else if (siglaUf.equals("PI")) {
			validaIEPiaui(strIE);
		} else if (siglaUf.equals("RJ")) {
			validaIERioJaneiro(strIE);
		} else if (siglaUf.equals("RN")) {
			validaIERioGrandeNorte(strIE);
		} else if (siglaUf.equals("RS")) {
			validaIERioGrandeSul(strIE);
		} else if (siglaUf.equals("RO")) {
			validaIERondonia(strIE);
		} else if (siglaUf.equals("RR")) {
			validaIERoraima(strIE);
		} else if (siglaUf.equals("SC")) {
			validaIESantaCatarina(strIE);
		} else if (siglaUf.equals("SP")) {
			if (inscricaoEstadual.charAt(0) == 'P') {
				strIE = "P" + strIE;
			}
			validaIESaoPaulo(strIE);
		} else if (siglaUf.equals("SE")) {
			validaIESergipe(strIE);
		} else if (siglaUf.equals("TO")) {
			validaIETocantins(strIE);
		} else if (siglaUf.equals("DF")) {
			validaIEDistritoFederal(strIE);
		} else {
			throw new Exception(this.ESTADO_NAO_ENCONTRADO + this.DOIS_PONTOS + siglaUf);
		}
	}
	
	
	private String formataMascara(String strIE, String siglaUf)
			throws Exception {
		siglaUf = siglaUf.toUpperCase();
		if (siglaUf.equals("AC")) {
			return formatarConformeMascara(strIE, this.ACRE );
		} else if (siglaUf.equals("AL")) {
			return formatarConformeMascara(strIE, this.ALAGOAS);
		} else if (siglaUf.equals("AP")) {
			return formatarConformeMascara(strIE, this.AMAPA);
		} else if (siglaUf.equals("AM")) {
			return formatarConformeMascara(strIE, this.AMAZONAS);
		} else if (siglaUf.equals("BA")) {
			return formatarConformeMascara(strIE, this.BAHIA);
		} else if (siglaUf.equals("CE")) {
			return formatarConformeMascara(strIE, this.CEARA);
		} else if (siglaUf.equals("ES")) {
			return formatarConformeMascara(strIE, this.ESPÍRITO_SANTO);
		} else if (siglaUf.equals("GO")) {
			return formatarConformeMascara(strIE, this.GOIAS);
		} else if (siglaUf.equals("MA")) {
			return formatarConformeMascara(strIE, this.MARANHAO);
		} else if (siglaUf.equals("MT")) {
			return formatarConformeMascara(strIE, this.MATO_GROSSO);
		} else if (siglaUf.equals("MS")) {
			return formatarConformeMascara(strIE, this.MATO_GROSSO_DO_SUL);
		} else if (siglaUf.equals("MG")) {
			return formatarConformeMascara(strIE, this.MINAS_GERAIS);
		} else if (siglaUf.equals("PA")) {
			return formatarConformeMascara(strIE, this.PARA);
		} else if (siglaUf.equals("PB")) {
			return formatarConformeMascara(strIE, this.PARAIBA);
		} else if (siglaUf.equals("PR")) {
			return formatarConformeMascara(strIE, this.PARANA);
		} else if (siglaUf.equals("PE")) {
			return formatarConformeMascara(strIE, this.PERNAMBUCO);
		} else if (siglaUf.equals("PI")) {
			return formatarConformeMascara(strIE, this.PIAUI);
		} else if (siglaUf.equals("RJ")) {
			return formatarConformeMascara(strIE, this.RIO_DE_JANEIRO);
		} else if (siglaUf.equals("RN")) {
			return formatarConformeMascara(strIE, this.RIO_GRANDE_DO_NORTE);
		} else if (siglaUf.equals("RS")) {
			return formatarConformeMascara(strIE, this.RIO_GRANDE_DO_SUL);
		} else if (siglaUf.equals("RO")) {
			return formatarConformeMascara(strIE, this.RONDONIA);
		} else if (siglaUf.equals("RR")) {
			return formatarConformeMascara(strIE, this.RORAIMA);
		} else if (siglaUf.equals("SC")) {
			return formatarConformeMascara(strIE, this.SANTA_CATARINA);
		} else if (siglaUf.equals("SP")) {
			if (strIE.charAt(0) == 'P') {
				strIE = "P" + strIE;
			}
			return formatarConformeMascara(strIE, this.SAO_PAULO);
		} else if (siglaUf.equals("SE")) {
			return formatarConformeMascara(strIE, this.SERGIPE);
		} else if (siglaUf.equals("TO")) {
			return formatarConformeMascara(strIE, this.TOCANTINS);
		} else if (siglaUf.equals("DF")) {
			return formatarConformeMascara(strIE, this.DISTRITO_FEDERAL);
		} else {
			throw new Exception(this.ESTADO_NAO_ENCONTRADO + this.DOIS_PONTOS + siglaUf);
		}
	}
	
    public  String formatarConformeMascara( String valor, String mascara ) {
        
        String dado = "";      
        // remove caracteres nao numericos
        for ( int i = 0; i < valor.length(); i++ )  {
            char c = valor.charAt(i);
            if ( Character.isDigit( c ) ) {
                dado += c; 
                }
        }
        int indMascara = mascara.length();
        int indCampo = dado.length();
        for ( ; indCampo > 0 && indMascara > 0; ) {
            if ( mascara.charAt( --indMascara ) == '#' ) {
                indCampo--; 
                }
        }
        String saida = "";
        for ( ; indMascara < mascara.length(); indMascara++ ) {    
            saida += ( ( mascara.charAt( indMascara ) == '#' ) ? dado.charAt( indCampo++ ) : mascara.charAt( indMascara ) );
        }    
        return saida;
    }

	/**
	 * Remove a máscara das inscrições estaduais.
	 * 
	 * @param ie
	 * @return
	 */
	private   String removeMascara(String ie) {
		String strIE = "";
		for (int i = 0; i < ie.length(); i++) {
			if (Character.isDigit(ie.charAt(i))) {
				strIE += ie.charAt(i);
			}
		}
		return strIE;
	}

	/**
	 * Valida Inscri��o estadual do estado do Acre
	 * 
	 * @param ie
	 *            (Inscri��o estadual)
	 * @throws Exception
	 */
	private  void validaIEAcre(String ie) throws Exception {
		// valida a quantidade de d�gitos
		if (ie.length() != 13) {
			throw new Exception(this.QUANTIDADE_DE_DIGITOS_INVALIDA);
		}

		// valida os dois primeiros digitos - devem ser iguais a 01
		for (int i = 0; i < 2; i++) {
			if (Integer.parseInt(String.valueOf(ie.charAt(i))) != i) {
				throw new Exception(this.INSCRICAO_ESTADUAL_INVALIDA);
			}
		}

		int soma = 0;
		int pesoInicial = 4;
		int pesoFinal = 9;
		int d1 = 0; // primeiro digito verificador
		int d2 = 0; // segundo digito verificador

		// calcula o primeiro digito
		for (int i = 0; i < ie.length() - 2; i++) {
			if (i < 3) {
				soma += Integer.parseInt(String.valueOf(ie.charAt(i)))
						* pesoInicial;
				pesoInicial--;
			} else {
				soma += Integer.parseInt(String.valueOf(ie.charAt(i)))
						* pesoFinal;
				pesoFinal--;
			}
		}
		d1 = 11 - (soma % 11);
		if (d1 == 10 || d1 == 11) {
			d1 = 0;
		}

		// calcula o segundo digito
		soma = d1 * 2;
		pesoInicial = 5;
		pesoFinal = 9;
		for (int i = 0; i < ie.length() - 2; i++) {
			if (i < 4) {
				soma += Integer.parseInt(String.valueOf(ie.charAt(i)))
						* pesoInicial;
				pesoInicial--;
			} else {
				soma += Integer.parseInt(String.valueOf(ie.charAt(i)))
						* pesoFinal;
				pesoFinal--;
			}
		}

		d2 = 11 - (soma % 11);
		if (d2 == 10 || d2 == 11) {
			d2 = 0;
		}

		// valida os digitos verificadores
		String dv = d1 + "" + d2;
		if (!dv.equals(ie.substring(ie.length() - 2, ie.length()))) {
			throw new Exception(this.DIGITO_VERIFICADOR_INVALIDO);
		}
	}

	/**
	 * Valida Inscri��o estadual do estado do Alagoas
	 * 
	 * @param ie
	 *            (Inscri��o estadual)
	 * @throws Exception
	 */
	private  void validaIEAlagoas(String ie) throws Exception {
		// valida quantidade de d�gitos
		if (ie.length() != 9) {
			throw new Exception(this.QUANTIDADE_DE_DIGITOS_INVALIDA);
		}

		// valida os dois primeiros d�gitos - deve ser iguais a 24
		if (!ie.substring(0, 2).equals("24")) {
			throw new Exception(this.INSCRICAO_ESTADUAL_INVALIDA);
		}

		// valida o terceiro d�gito - deve ser 0,3,5,7,8
		int[] digits = { 0, 3, 5, 7, 8 };
		boolean check = false;
		for (int i = 0; i < digits.length; i++) {
			if (Integer.parseInt(String.valueOf(ie.charAt(2))) == digits[i]) {
				check = true;
				break;
			}
		}
		if (!check) {
			throw new Exception(this.INSCRICAO_ESTADUAL_INVALIDA);
		}

		// calcula o d�gito verificador
		int soma = 0;
		int peso = 9;
		int d = 0; // d�gito verificador
		for (int i = 0; i < ie.length() - 1; i++) {
			soma += Integer.parseInt(String.valueOf(ie.charAt(i))) * peso;
			peso--;
		}
		d = ((soma * 10) % 11);
		if (d == 10) {
			d = 0;
		}

		// valida o digito verificador
		String dv = d + "";
		if (!ie.substring(ie.length() - 1, ie.length()).equals(dv)) {
			throw new Exception(this.DIGITO_VERIFICADOR_INVALIDO);
		}
	}

	/**
	 * Valida Inscri��o estadual do estado do Amap�
	 * 
	 * @param ie
	 *            (Inscri��o estadual)
	 * @throws Exception
	 */
	private  void validaIEAmapa(String ie) throws Exception {
		// valida quantida de d�gitos
		if (ie.length() != 9) {
			throw new Exception(this.QUANTIDADE_DE_DIGITOS_INVALIDA);
		}

		// verifica os dois primeiros d�gitos - deve ser igual 03
		if (!ie.substring(0, 2).equals("03")) {
			throw new Exception(this.INSCRICAO_ESTADUAL_INVALIDA);
		}

		// calcula o d�gito verificador
		int d1 = -1;
		int soma = -1;
		int peso = 9;

		// configura o valor do d�gito verificador e da soma de acordo com faixa
		// das inscri��es
		long x = Long.parseLong(ie.substring(0, ie.length() - 1)); // x =
																	// Inscri��o
																	// estadual
																	// sem o
																	// d�gito
																	// verificador
		if (x >= 3017001L && x <= 3019022L) {
			d1 = 1;
			soma = 9;
		} else if (x >= 3000001L && x <= 3017000L) {
			d1 = 0;
			soma = 5;
		} else if (x >= 3019023L) {
			d1 = 0;
			soma = 0;
		}

		for (int i = 0; i < ie.length() - 1; i++) {
			soma += Integer.parseInt(String.valueOf(ie.charAt(i))) * peso;
			peso--;
		}

		int d = 11 - ((soma % 11)); // d = armazena o d�gito verificador ap�s
									// c�lculo
		if (d == 10) {
			d = 0;
		} else if (d == 11) {
			d = d1;
		}

		// valida o digito verificador
		String dv = d + "";
		if (!ie.substring(ie.length() - 1, ie.length()).equals(dv)) {
			throw new Exception(this.DIGITO_VERIFICADOR_INVALIDO);
		}
	}

	/**
	 * Valida Inscri��o estadual do estado do Amazonas
	 * 
	 * @param ie
	 *            (Inscri��o estadual)
	 * @throws Exception
	 */
	private  void validaIEAmazonas(String ie) throws Exception {
		// valida quantida de d�gitos
		if (ie.length() != 9) {
			throw new Exception(this.QUANTIDADE_DE_DIGITOS_INVALIDA);
		}

		int soma = 0;
		int peso = 9;
		int d = -1; // d�gito verificador
		for (int i = 0; i < ie.length() - 1; i++) {
			soma += Integer.parseInt(String.valueOf(ie.charAt(i))) * peso;
			peso--;
		}

		if (soma < 11) {
			d = 11 - soma;
		} else if ((soma % 11) <= 1) {
			d = 0;
		} else {
			d = 11 - (soma % 11);
		}

		// valida o digito verificador
		String dv = d + "";
		if (!ie.substring(ie.length() - 1, ie.length()).equals(dv)) {
			throw new Exception(this.DIGITO_VERIFICADOR_INVALIDO);
		}
	}

	/**
	 * Valida Inscri��o estadual do estado do Bahia
	 * 
	 * @param ie
	 *            (Inscri��o estadual)
	 * @throws Exception
	 */
	private  void validaIEBahia(String ie) throws Exception {
		// valida quantida de d�gitos
		if (ie.length() != 8 && ie.length() != 9) {
			throw new Exception(this.QUANTIDADE_DE_DIGITOS_INVALIDA + ie);
		}

		// c�lculo do m�dulo de acordo com o primeiro d�gito da Inscri��o
		// Estadual
		int modulo = 10;
		int firstDigit = Integer
				.parseInt(String.valueOf(ie.charAt(ie.length() == 8 ? 0 : 1)));
		if (firstDigit == 6 || firstDigit == 7 || firstDigit == 9)
			modulo = 11;

		// c�lculo do segundo d�gito
		int d2 = -1; // segundo d�gito verificador
		int soma = 0;
		int peso = ie.length() == 8 ? 7 : 8;
		for (int i = 0; i < ie.length() - 2; i++) {
			soma += Integer.parseInt(String.valueOf(ie.charAt(i))) * peso;
			peso--;
		}

		int resto = soma % modulo;

		if (resto == 0 || (modulo == 11 && resto == 1)) {
			d2 = 0;
		} else {
			d2 = modulo - resto;
		}

		// c�lculo do primeiro d�gito
		int d1 = -1; // primeiro d�gito verificador
		soma = d2 * 2;
		peso = ie.length() == 8 ? 8 : 9;
		for (int i = 0; i < ie.length() - 2; i++) {
			soma += Integer.parseInt(String.valueOf(ie.charAt(i))) * peso;
			peso--;
		}

		resto = soma % modulo;

		if (resto == 0 || (modulo == 11 && resto == 1)) {
			d1 = 0;
		} else {
			d1 = modulo - resto;
		}

		// valida os digitos verificadores
		String dv = d1 + "" + d2;
		if (!dv.equals(ie.substring(ie.length() - 2, ie.length()))) {
			throw new Exception(this.DIGITO_VERIFICADOR_INVALIDO + ie);
		}
	}

	/**
	 * Valida Inscri��o estadual do estado do Cear�
	 * 
	 * @param ie
	 *            (Inscri��o estadual)
	 * @throws Exception
	 */
	private  void validaIECeara(String ie) throws Exception {
		// valida quantida de d�gitos
		if (ie.length() != 9) {
			throw new Exception(this.QUANTIDADE_DE_DIGITOS_INVALIDA);
		}

		// c�lculo do d�gito verificador
		int soma = 0;
		int peso = 9;
		int d = -1; // d�gito verificador
		for (int i = 0; i < ie.length() - 1; i++) {
			soma += Integer.parseInt(String.valueOf(ie.charAt(i))) * peso;
			peso--;
		}

		d = 11 - (soma % 11);
		if (d == 10 || d == 11) {
			d = 0;
		}
		// valida o digito verificador
		String dv = d + "";
		if (!ie.substring(ie.length() - 1, ie.length()).equals(dv)) {
			throw new Exception(this.DIGITO_VERIFICADOR_INVALIDO);
		}
	}

	/**
	 * Valida Inscri��o estadual do estado do Esp�rito Santo
	 * 
	 * @param ie
	 *            (Inscri��o estadual)
	 * @throws Exception
	 */
	private  void validaIEEspiritoSanto(String ie) throws Exception {
		// valida quantida de d�gitos
		if (ie.length() != 9) {
			throw new Exception(this.QUANTIDADE_DE_DIGITOS_INVALIDA);
		}

		// c�lculo do d�gito verificador
		int soma = 0;
		int peso = 9;
		int d = -1; // d�gito verificador
		for (int i = 0; i < ie.length() - 1; i++) {
			soma += Integer.parseInt(String.valueOf(ie.charAt(i))) * peso;
			peso--;
		}

		int resto = soma % 11;
		if (resto < 2) {
			d = 0;
		} else if (resto > 1) {
			d = 11 - resto;
		}

		// valida o digito verificador
		String dv = d + "";
		if (!ie.substring(ie.length() - 1, ie.length()).equals(dv)) {
			throw new Exception(this.DIGITO_VERIFICADOR_INVALIDO);
		}
	}

	/**
	 * Valida Inscri��o estadual do estado do Goi�s
	 * 
	 * @param ie
	 *            (Inscri��o estadual)
	 * @throws Exception
	 */
	private  void validaIEGoias(String ie) throws Exception {
		// valida quantida de d�gitos
		if (ie.length() != 9) {
			throw new Exception(this.QUANTIDADE_DE_DIGITOS_INVALIDA);
		}

		// valida os dois primeiros d�gito
		if (!"10".equals(ie.substring(0, 2))) {
			if (!"11".equals(ie.substring(0, 2))) {
				if (!"15".equals(ie.substring(0, 2))) {
					throw new Exception(this.INSCRICAO_ESTADUAL_INVALIDA);
				}
			}
		}

		if (ie.substring(0, ie.length() - 1).equals("11094402")) {
			if (!ie.substring(ie.length() - 1, ie.length()).equals("0")) {
				if (!ie.substring(ie.length() - 1, ie.length()).equals("1")) {
					throw new Exception(this.INSCRICAO_ESTADUAL_INVALIDA);
				}
			}
		} else {

			// c�lculo do d�gito verificador
			int soma = 0;
			int peso = 9;
			int d = -1; // d�gito verificador
			for (int i = 0; i < ie.length() - 1; i++) {
				soma += Integer.parseInt(String.valueOf(ie.charAt(i))) * peso;
				peso--;
			}

			int resto = soma % 11;
			long faixaInicio = 10103105;
			long faixaFim = 10119997;
			long insc = Long.parseLong(ie.substring(0, ie.length() - 1));
			if (resto == 0) {
				d = 0;
			} else if (resto == 1) {
				if (insc >= faixaInicio && insc <= faixaFim) {
					d = 1;
				} else {
					d = 0;
				}
			} else if (resto != 0 && resto != 1) {
				d = 11 - resto;
			}

			// valida o digito verificador
			String dv = d + "";
			if (!ie.substring(ie.length() - 1, ie.length()).equals(dv)) {
				throw new Exception(this.DIGITO_VERIFICADOR_INVALIDO);
			}
		}
	}

	/**
	 * Valida Inscri��o estadual do estado do Maranh�o
	 * 
	 * @param ie
	 *            (Inscri��o estadual)
	 * @throws Exception
	 */
	private  void validaIEMaranhao(String ie) throws Exception {
		// valida quantida de d�gitos
		if (ie.length() != 9) {
			throw new Exception(this.QUANTIDADE_DE_DIGITOS_INVALIDA);
		}

		// valida os dois primeiros d�gitos
		if (!ie.substring(0, 2).equals("12")) {
			throw new Exception(this.INSCRICAO_ESTADUAL_INVALIDA);
		}

		// Calcula o d�gito verificador
		int soma = 0;
		int peso = 9;
		int d = -1; // d�gito verificador
		for (int i = 0; i < ie.length() - 1; i++) {
			soma += Integer.parseInt(String.valueOf(ie.charAt(i))) * peso;
			peso--;
		}

		d = 11 - (soma % 11);
		if ((soma % 11) == 0 || (soma % 11) == 1) {
			d = 0;
		}

		// valida o digito verificador
		String dv = d + "";
		if (!ie.substring(ie.length() - 1, ie.length()).equals(dv)) {
			throw new Exception(this.DIGITO_VERIFICADOR_INVALIDO);
		}
	}

	/**
	 * Valida Inscri��o estadual do estado do Mato Grosso
	 * 
	 * @param ie
	 *            (Inscri��o estadual)
	 * @throws Exception
	 */
	private  void validaIEMatoGrosso(String ie) throws Exception {
		// valida quantida de d�gitos
		if (ie.length() != 11) {
			throw new Exception(this.QUANTIDADE_DE_DIGITOS_INVALIDA);
		}

		// Calcula o d�gito verificador
		int soma = 0;
		int pesoInicial = 3;
		int pesoFinal = 9;
		int d = -1;

		for (int i = 0; i < ie.length() - 1; i++) {
			if (i < 2) {
				soma += Integer.parseInt(String.valueOf(ie.charAt(i)))
						* pesoInicial;
				pesoInicial--;
			} else {
				soma += Integer.parseInt(String.valueOf(ie.charAt(i)))
						* pesoFinal;
				pesoFinal--;
			}
		}

		d = 11 - (soma % 11);
		if ((soma % 11) == 0 || (soma % 11) == 1) {
			d = 0;
		}

		// valida o digito verificador
		String dv = d + "";
		if (!ie.substring(ie.length() - 1, ie.length()).equals(dv)) {
			throw new Exception(this.DIGITO_VERIFICADOR_INVALIDO);
		}
	}

	/**
	 * Valida Inscri��o estadual do estado do Mato Grosso do Sul
	 * 
	 * @param ie
	 *            (Inscri��o estadual)
	 * @throws Exception
	 */
	private  void validaIEMatoGrossoSul(String ie) throws Exception {
		// valida quantida de d�gitos
		if (ie.length() != 9) {
			throw new Exception(this.QUANTIDADE_DE_DIGITOS_INVALIDA);
		}

		// valida os dois primeiros d�gitos
		if (!ie.substring(0, 2).equals("28")) {
			throw new Exception(this.INSCRICAO_ESTADUAL_INVALIDA);
		}

		// Calcula o d�gito verificador
		int soma = 0;
		int peso = 9;
		int d = -1; // d�gito verificador
		for (int i = 0; i < ie.length() - 1; i++) {
			soma += Integer.parseInt(String.valueOf(ie.charAt(i))) * peso;
			peso--;
		}

		int resto = soma % 11;
		int result = 11 - resto;
		if (resto == 0) {
			d = 0;
		} else if (resto > 0) {
			if (result > 9) {
				d = 0;
			} else if (result < 10) {
				d = result;
			}
		}

		// valida o digito verificador
		String dv = d + "";
		if (!ie.substring(ie.length() - 1, ie.length()).equals(dv)) {
			throw new Exception(this.DIGITO_VERIFICADOR_INVALIDO);
		}
	}

	/**
	 * Valida Inscri��o estadual do estado do Minas Gerais
	 * 
	 * @param ie
	 *            (Inscri��o estadual)
	 * @throws Exception
	 */
	private  void validaIEMinasGerais(String ie) throws Exception {
		/*
		 * FORMATO GERAL: A1A2A3B1B2B3B4B5B6C1C2D1D2 Onde: A= c�digo do
		 * Munic�pio B= N�mero da Inscri��o C= N�mero de ordem do
		 * estabelecimento D= d�gitos de controle
		 */

		// valida quantida de d�gitos
		if (ie.length() != 13) {
			throw new Exception(this.QUANTIDADE_DE_DIGITOS_INVALIDA);
		}

		// iguala a casas para o c�lculo
		// em inserir o algarismo zero "0" imediatamente ap�s o n�mero de c�digo
		// do munic�pio,
		// desprezando-se os d�gitos de controle.
		String str = "";
		for (int i = 0; i < ie.length() - 2; i++) {
			if (Character.isDigit(ie.charAt(i))) {
				if (i == 3) {
					str += "0";
					str += ie.charAt(i);
				} else {
					str += ie.charAt(i);
				}
			}
		}

		// c�lculo do primeiro d�gito verificador
		int soma = 0;
		int pesoInicio = 1;
		int pesoFim = 2;
		int d1 = -1; // primeiro d�gito verificador
		for (int i = 0; i < str.length(); i++) {
			if (i % 2 == 0) {
				int x = Integer.parseInt(String.valueOf(str.charAt(i)))
						* pesoInicio;
				String strX = Integer.toString(x);
				for (int j = 0; j < strX.length(); j++) {
					soma += Integer.parseInt(String.valueOf(strX.charAt(j)));
				}
			} else {
				int y = Integer.parseInt(String.valueOf(str.charAt(i)))
						* pesoFim;
				String strY = Integer.toString(y);
				for (int j = 0; j < strY.length(); j++) {
					soma += Integer.parseInt(String.valueOf(strY.charAt(j)));
				}
			}
		}

		int dezenaExata = soma;
		while (dezenaExata % 10 != 0) {
			dezenaExata++;
		}
		d1 = dezenaExata - soma; // resultado - primeiro d�gito verificador

		// c�lculo do segundo d�gito verificador
		soma = d1 * 2;
		pesoInicio = 3;
		pesoFim = 11;
		int d2 = -1;
		for (int i = 0; i < ie.length() - 2; i++) {
			if (i < 2) {
				soma += Integer.parseInt(String.valueOf(ie.charAt(i)))
						* pesoInicio;
				pesoInicio--;
			} else {
				soma += Integer.parseInt(String.valueOf(ie.charAt(i)))
						* pesoFim;
				pesoFim--;
			}
		}

		d2 = 11 - (soma % 11); // resultado - segundo d�gito verificador
		if ((soma % 11 == 0) || (soma % 11 == 1)) {
			d2 = 0;
		}

		// valida os digitos verificadores
		String dv = d1 + "" + d2;
		if (!dv.equals(ie.substring(ie.length() - 2, ie.length()))) {
			throw new Exception(this.DIGITO_VERIFICADOR_INVALIDO);
		}
	}

	/**
	 * Valida Inscri��o estadual do estado do Par�
	 * 
	 * @param ie
	 *            (Inscri��o estadual)
	 * @throws Exception
	 */
	private  void validaIEPara(String ie) throws Exception {
		// valida quantida de d�gitos
		if (ie.length() != 9) {
			throw new Exception(this.QUANTIDADE_DE_DIGITOS_INVALIDA);
		}

		// valida os dois primeiros d�gitos
		if (!ie.substring(0, 2).equals("15")) {
			throw new Exception(this.INSCRICAO_ESTADUAL_INVALIDA);
		}

		// Calcula o d�gito verificador
		int soma = 0;
		int peso = 9;
		int d = -1; // d�gito verificador
		for (int i = 0; i < ie.length() - 1; i++) {
			soma += Integer.parseInt(String.valueOf(ie.charAt(i))) * peso;
			peso--;
		}

		d = 11 - (soma % 11);
		if ((soma % 11) == 0 || (soma % 11) == 1) {
			d = 0;
		}

		// valida o digito verificador
		String dv = d + "";
		if (!ie.substring(ie.length() - 1, ie.length()).equals(dv)) {
			throw new Exception(this.DIGITO_VERIFICADOR_INVALIDO);
		}
	}

	/**
	 * Valida Inscri��o estadual do estado do Para�ba
	 * 
	 * @param ie
	 *            (Inscri��o estadual)
	 * @throws Exception
	 */
	private  void validaIEParaiba(String ie) throws Exception {
		// valida quantida de d�gitos
		if (ie.length() != 9) {
			throw new Exception(this.QUANTIDADE_DE_DIGITOS_INVALIDA);
		}

		// Calcula o d�gito verificador
		int soma = 0;
		int peso = 9;
		int d = -1; // d�gito verificador
		for (int i = 0; i < ie.length() - 1; i++) {
			soma += Integer.parseInt(String.valueOf(ie.charAt(i))) * peso;
			peso--;
		}

		d = 11 - (soma % 11);
		if (d == 10 || d == 11) {
			d = 0;
		}

		// valida o digito verificador
		String dv = d + "";
		if (!ie.substring(ie.length() - 1, ie.length()).equals(dv)) {
			throw new Exception(this.DIGITO_VERIFICADOR_INVALIDO);
		}
	}

	/**
	 * Valida Inscri��o estadual do estado do Paran�
	 * 
	 * @param ie
	 *            (Inscri��o estadual)
	 * @throws Exception
	 */
	private  void validaIEParana(String ie) throws Exception {
		// valida quantida de d�gitos
		if (ie.length() != 10) {
			throw new Exception(this.QUANTIDADE_DE_DIGITOS_INVALIDA);
		}

		// c�lculo do primeiro d�gito
		int soma = 0;
		int pesoInicio = 3;
		int pesoFim = 7;
		int d1 = -1; // d�gito verificador
		for (int i = 0; i < ie.length() - 2; i++) {
			if (i < 2) {
				soma += Integer.parseInt(String.valueOf(ie.charAt(i)))
						* pesoInicio;
				pesoInicio--;
			} else {
				soma += Integer.parseInt(String.valueOf(ie.charAt(i)))
						* pesoFim;
				pesoFim--;
			}
		}

		d1 = 11 - (soma % 11);
		if ((soma % 11) == 0 || (soma % 11) == 1) {
			d1 = 0;
		}

		// c�lculo do segundo d�gito
		soma = d1 * 2;
		pesoInicio = 4;
		pesoFim = 7;
		int d2 = -1; // segundo d�gito
		for (int i = 0; i < ie.length() - 2; i++) {
			if (i < 3) {
				soma += Integer.parseInt(String.valueOf(ie.charAt(i)))
						* pesoInicio;
				pesoInicio--;
			} else {
				soma += Integer.parseInt(String.valueOf(ie.charAt(i)))
						* pesoFim;
				pesoFim--;
			}
		}

		d2 = 11 - (soma % 11);
		if ((soma % 11) == 0 || (soma % 11) == 1) {
			d2 = 0;
		}

		// valida os digitos verificadores
		String dv = d1 + "" + d2;
		if (!dv.equals(ie.substring(ie.length() - 2, ie.length()))) {
			throw new Exception(this.DIGITO_VERIFICADOR_INVALIDO);
		}
	}

	/**
	 * Valida Inscri��o estadual do estado do Pernambuco
	 * 
	 * @param ie
	 *            (Inscri��o estadual)
	 * @throws Exception
	 */
	private  void validaIEPernambuco(String ie) throws Exception {
		// valida quantida de d�gitos
		if (ie.length() != 14) {
			throw new Exception(this.QUANTIDADE_DE_DIGITOS_INVALIDA);
		}

		// c�lculo do d�gito verificador
		int soma = 0;
		int pesoInicio = 5;
		int pesoFim = 9;
		int d = -1; // d�gito verificador

		for (int i = 0; i < ie.length() - 1; i++) {
			if (i < 5) {
				soma += Integer.parseInt(String.valueOf(ie.charAt(i)))
						* pesoInicio;
				pesoInicio--;
			} else {
				soma += Integer.parseInt(String.valueOf(ie.charAt(i)))
						* pesoFim;
				pesoFim--;
			}
		}

		d = 11 - (soma % 11);
		if (d > 9) {
			d -= 10;
		}

		System.out.println(soma);
		System.out.println(11 - (soma % 11));
		System.out.println(d);

		// valida o digito verificador
		String dv = d + "";
		if (!ie.substring(ie.length() - 1, ie.length()).equals(dv)) {
			throw new Exception(this.DIGITO_VERIFICADOR_INVALIDO);
		}
	}

	/**
	 * Valida Inscri��o estadual do estado do Piau�
	 * 
	 * @param ie
	 *            (Inscri��o estadual)
	 * @throws Exception
	 */
	private  void validaIEPiaui(String ie) throws Exception {
		// valida quantida de d�gitos
		if (ie.length() != 9) {
			throw new Exception(this.QUANTIDADE_DE_DIGITOS_INVALIDA);
		}

		// c�lculo do d�gito verficador
		int soma = 0;
		int peso = 9;
		int d = -1; // d�gito verificador
		for (int i = 0; i < ie.length() - 1; i++) {
			soma += Integer.parseInt(String.valueOf(ie.charAt(i))) * peso;
			peso--;
		}

		d = 11 - (soma % 11);
		if (d == 11 || d == 10) {
			d = 0;
		}

		// valida o digito verificador
		String dv = d + "";
		if (!ie.substring(ie.length() - 1, ie.length()).equals(dv)) {
			throw new Exception(this.DIGITO_VERIFICADOR_INVALIDO);
		}
	}

	/**
	 * Valida Inscri��o estadual do estado do Rio de Janeiro
	 * 
	 * @param ie
	 *            (Inscri��o estadual)
	 * @throws Exception
	 */
	private  void validaIERioJaneiro(String ie) throws Exception {
		// valida quantida de d�gitos
		if (ie.length() != 8) {
			throw new Exception(this.QUANTIDADE_DE_DIGITOS_INVALIDA);
		}

		// C�lculo do d�gito verficador
		int soma = 0;
		int peso = 7;
		int d = -1; // d�gito verificador
		for (int i = 0; i < ie.length() - 1; i++) {
			if (i == 0) {
				soma += Integer.parseInt(String.valueOf(ie.charAt(i))) * 2;
			} else {
				soma += Integer.parseInt(String.valueOf(ie.charAt(i))) * peso;
				peso--;
			}
		}

		d = 11 - (soma % 11);
		if ((soma % 11) <= 1) {
			d = 0;
		}

		// valida o digito verificador
		String dv = d + "";
		if (!ie.substring(ie.length() - 1, ie.length()).equals(dv)) {
			throw new Exception(this.DIGITO_VERIFICADOR_INVALIDO);
		}
	}

	/**
	 * Valida Inscri��o estadual do estado do Rio Grande do Norte
	 * 
	 * @param ie
	 *            (Inscri��o estadual)
	 * @throws Exception
	 */
	private  void validaIERioGrandeNorte(String ie) throws Exception {
		// valida quantida de d�gitos
		if (ie.length() != 10 && ie.length() != 9) {
			throw new Exception(this.QUANTIDADE_DE_DIGITOS_INVALIDA);
		}

		// valida os dois primeiros d�gitos
		if (!ie.substring(0, 2).equals("20")) {
			throw new Exception(this.INSCRICAO_ESTADUAL_INVALIDA);
		}

		// calcula o d�gito para Inscri��o de 9 d�gitos
		if (ie.length() == 9) {
			int soma = 0;
			int peso = 9;
			int d = -1; // d�gito verificador
			for (int i = 0; i < ie.length() - 1; i++) {
				soma += Integer.parseInt(String.valueOf(ie.charAt(i))) * peso;
				peso--;
			}

			d = ((soma * 10) % 11);
			if (d == 10) {
				d = 0;
			}

			// valida o digito verificador
			String dv = d + "";
			if (!ie.substring(ie.length() - 1, ie.length()).equals(dv)) {
				throw new Exception(this.DIGITO_VERIFICADOR_INVALIDO);
			}
		} else {
			int soma = 0;
			int peso = 10;
			int d = -1; // d�gito verificador
			for (int i = 0; i < ie.length() - 1; i++) {
				soma += Integer.parseInt(String.valueOf(ie.charAt(i))) * peso;
				peso--;
			}
			d = ((soma * 10) % 11);
			if (d == 10) {
				d = 0;
			}

			// valida o digito verificador
			String dv = d + "";
			if (!ie.substring(ie.length() - 1, ie.length()).equals(dv)) {
				throw new Exception(this.DIGITO_VERIFICADOR_INVALIDO);
			}
		}

	}

	/**
	 * Valida Inscri��o estadual do estado do Rio Grande do Sul
	 * 
	 * @param ie
	 *            (Inscri��o estadual)
	 * @throws Exception
	 */
	private  void validaIERioGrandeSul(String ie) throws Exception {
		// valida quantida de d�gitos
		if (ie.length() != 10) {
			throw new Exception(this.QUANTIDADE_DE_DIGITOS_INVALIDA);
		}

		// c�lculo do d�gito verificador
		int soma = Integer.parseInt(String.valueOf(ie.charAt(0))) * 2;
		int peso = 9;
		int d = -1; // d�gito verificador
		for (int i = 1; i < ie.length() - 1; i++) {
			soma += Integer.parseInt(String.valueOf(ie.charAt(i))) * peso;
			peso--;
		}

		d = 11 - (soma % 11);
		if (d == 10 || d == 11) {
			d = 0;
		}

		// valida o digito verificador
		String dv = d + "";
		if (!ie.substring(ie.length() - 1, ie.length()).equals(dv)) {
			throw new Exception(this.DIGITO_VERIFICADOR_INVALIDO);
		}
	}

	/**
	 * Valida Inscri��o estadual do estado do Rond�nia
	 * 
	 * @param ie
	 *            (Inscri��o estadual)
	 * @throws Exception
	 */
	private  void validaIERondonia(String ie) throws Exception {
		// valida quantida de d�gitos
		if (ie.length() != 14) {
			throw new Exception(this.QUANTIDADE_DE_DIGITOS_INVALIDA);
		}

		// c�lculo do d�gito verificador
		int soma = 0;
		int pesoInicio = 6;
		int pesoFim = 9;
		int d = -1; // d�gito verificador
		for (int i = 0; i < ie.length() - 1; i++) {
			if (i < 5) {
				soma += Integer.parseInt(String.valueOf(ie.charAt(i)))
						* pesoInicio;
				pesoInicio--;
			} else {
				soma += Integer.parseInt(String.valueOf(ie.charAt(i)))
						* pesoFim;
				pesoFim--;
			}
		}

		d = 11 - (soma % 11);
		if (d == 11 || d == 10) {
			d -= 10;
		}

		// valida o digito verificador
		String dv = d + "";
		if (!ie.substring(ie.length() - 1, ie.length()).equals(dv)) {
			throw new Exception(this.DIGITO_VERIFICADOR_INVALIDO);
		}
	}

	/**
	 * Valida Inscri��o estadual do estado do Roraima
	 * 
	 * @param ie
	 *            (Inscri��o estadual)
	 * @throws Exception
	 */
	private  void validaIERoraima(String ie) throws Exception {
		// valida quantida de d�gitos
		if (ie.length() != 9) {
			throw new Exception(this.QUANTIDADE_DE_DIGITOS_INVALIDA);
		}

		// valida os dois primeiros d�gitos
		if (!ie.substring(0, 2).equals("24")) {
			throw new Exception(this.INSCRICAO_ESTADUAL_INVALIDA);
		}

		int soma = 0;
		int peso = 1;
		int d = -1; // d�gito verificador
		for (int i = 0; i < ie.length() - 1; i++) {
			soma += Integer.parseInt(String.valueOf(ie.charAt(i))) * peso;
			peso++;
		}

		d = soma % 9;

		// valida o digito verificador
		String dv = d + "";
		if (!ie.substring(ie.length() - 1, ie.length()).equals(dv)) {
			throw new Exception(this.DIGITO_VERIFICADOR_INVALIDO);
		}
	}

	/**
	 * Valida Inscri��o estadual do estado do Santa Catarina
	 * 
	 * @param ie
	 *            (Inscri��o estadual)
	 * @throws Exception
	 */
	private  void validaIESantaCatarina(String ie) throws Exception {
		// valida quantida de d�gitos
		if (ie.length() != 9) {
			throw new Exception(this.QUANTIDADE_DE_DIGITOS_INVALIDA);
		}

		// c�lculo do d�gito verificador
		int soma = 0;
		int peso = 9;
		int d = -1; // d�gito verificador
		for (int i = 0; i < ie.length() - 1; i++) {
			soma += Integer.parseInt(String.valueOf(ie.charAt(i))) * peso;
			peso--;
		}

		d = 11 - (soma % 11);
		if ((soma % 11) == 0 || (soma % 11) == 1) {
			d = 0;
		}

		// valida o digito verificador
		String dv = d + "";
		if (!ie.substring(ie.length() - 1, ie.length()).equals(dv)) {
			throw new Exception(this.DIGITO_VERIFICADOR_INVALIDO);
		}
	}

	/**
	 * Valida Inscri��o estadual do estado do S�o Paulo
	 * 
	 * @param ie
	 *            (Inscri��o estadual)
	 * @throws Exception
	 */
	private  void validaIESaoPaulo(String ie) throws Exception {
		// valida quantida de d�gitos
		if (ie.length() != 12 && ie.length() != 13) {
			throw new Exception(this.QUANTIDADE_DE_DIGITOS_INVALIDA);
		}

		if (ie.length() == 12) {
			int soma = 0;
			int peso = 1;
			int d1 = -1; // primeiro d�gito verificador
			// c�lculo do primeiro d�gito verificador (nona posi��o)
			for (int i = 0; i < ie.length() - 4; i++) {
				if (i == 1 || i == 7) {
					soma += Integer.parseInt(String.valueOf(ie.charAt(i)))
							* ++peso;
					peso++;
				} else {
					soma += Integer.parseInt(String.valueOf(ie.charAt(i)))
							* peso;
					peso++;
				}
			}

			d1 = soma % 11;
			String strD1 = Integer.toString(d1); // O d�gito ? igual ao
													// algarismo mais a direita
													// do resultado de (soma %
													// 11)
			d1 = Integer
					.parseInt(String.valueOf(strD1.charAt(strD1.length() - 1)));

			// c�lculo do segunfo d�gito
			soma = 0;
			int pesoInicio = 3;
			int pesoFim = 10;
			int d2 = -1; // segundo d�gito verificador
			for (int i = 0; i < ie.length() - 1; i++) {
				if (i < 2) {
					soma += Integer.parseInt(String.valueOf(ie.charAt(i)))
							* pesoInicio;
					pesoInicio--;
				} else {
					soma += Integer.parseInt(String.valueOf(ie.charAt(i)))
							* pesoFim;
					pesoFim--;
				}
			}

			d2 = soma % 11;
			String strD2 = Integer.toString(d2); // O d�gito ? igual ao
													// algarismo mais a direita
													// do resultado de (soma %
													// 11)
			d2 = Integer
					.parseInt(String.valueOf(strD2.charAt(strD2.length() - 1)));

			// valida os d�gitos verificadores
			if (!ie.substring(8, 9).equals(d1 + "")) {
				throw new Exception(this.INSCRICAO_ESTADUAL_INVALIDA);
			}
			if (!ie.substring(11, 12).equals(d2 + "")) {
				throw new Exception(this.INSCRICAO_ESTADUAL_INVALIDA);
			}

		} else {
			// valida o primeiro caracter
			if (ie.charAt(0) != 'P') {
				throw new Exception(this.INSCRICAO_ESTADUAL_INVALIDA);
			}

			String strIE = ie.substring(1, 10); // Obt�m somente os d�gitos
												// utilizados no c�lculo do
												// d�gito verificador
			int soma = 0;
			int peso = 1;
			int d1 = -1; // primeiro d�gito verificador
			// c�lculo do primeiro d�gito verificador (nona posi��o)
			for (int i = 0; i < strIE.length() - 1; i++) {
				if (i == 1 || i == 7) {
					soma += Integer.parseInt(String.valueOf(strIE.charAt(i)))
							* ++peso;
					peso++;
				} else {
					soma += Integer.parseInt(String.valueOf(strIE.charAt(i)))
							* peso;
					peso++;
				}
			}

			d1 = soma % 11;
			String strD1 = Integer.toString(d1); // O d�gito ? igual ao
													// algarismo mais a direita
													// do resultado de (soma %
													// 11)
			d1 = Integer
					.parseInt(String.valueOf(strD1.charAt(strD1.length() - 1)));

			// valida o d�gito verificador
			if (!ie.substring(9, 10).equals(d1 + "")) {
				throw new Exception(this.INSCRICAO_ESTADUAL_INVALIDA);
			}
		}
	}

	/**
	 * Valida Inscri��o estadual do estado do Sergipe
	 * 
	 * @param ie
	 *            (Inscri��o estadual)
	 * @throws Exception
	 */
	private  void validaIESergipe(String ie) throws Exception {
		// valida quantida de d�gitos
		if (ie.length() != 9) {
			throw new Exception(this.QUANTIDADE_DE_DIGITOS_INVALIDA);
		}

		// c�lculo do d�gito verificador
		int soma = 0;
		int peso = 9;
		int d = -1; // d�gito verificador
		for (int i = 0; i < ie.length() - 1; i++) {
			soma += Integer.parseInt(String.valueOf(ie.charAt(i))) * peso;
			peso--;
		}

		d = 11 - (soma % 11);
		if (d == 11 || d == 11 || d == 10) {
			d = 0;
		}

		// valida o digito verificador
		String dv = d + "";
		if (!ie.substring(ie.length() - 1, ie.length()).equals(dv)) {
			throw new Exception(this.DIGITO_VERIFICADOR_INVALIDO);
		}
	}

	/**
	 * Valida Inscri��o estadual do estado do Tocantins
	 * 
	 * @param ie
	 *            (Inscri��o estadual)
	 * @throws Exception
	 */
	private  void validaIETocantins(String ie) throws Exception {
		// valida quantida de d�gitos
		if (ie.length() != 9 && ie.length() != 11) {
			throw new Exception(this.QUANTIDADE_DE_DIGITOS_INVALIDA);
		} else if (ie.length() == 9) {
			ie = ie.substring(0, 2) + "02" + ie.substring(2);
		}

		int soma = 0;
		int peso = 9;
		int d = -1; // d�gito verificador
		for (int i = 0; i < ie.length() - 1; i++) {
			if (i != 2 && i != 3) {
				soma += Integer.parseInt(String.valueOf(ie.charAt(i))) * peso;
				peso--;
			}
		}
		d = 11 - (soma % 11);
		if ((soma % 11) < 2) {
			d = 0;
		}

		// valida o digito verificador
		String dv = d + "";
		if (!ie.substring(ie.length() - 1, ie.length()).equals(dv)) {
			throw new Exception(this.DIGITO_VERIFICADOR_INVALIDO);
		}
	}

	/**
	 * Valida Inscri��o estadual do estado do Distrito Federal
	 * 
	 * @param ie
	 *            (Inscri��o estadual)
	 * @throws Exception
	 */
	private  void validaIEDistritoFederal(String ie) throws Exception {
		// valida quantida de d�gitos
		if (ie.length() != 13) {
			throw new Exception(this.QUANTIDADE_DE_DIGITOS_INVALIDA);
		}

		// c�lculo do primeiro d�gito verificador
		int soma = 0;
		int pesoInicio = 4;
		int pesoFim = 9;
		int d1 = -1; // primeiro d�gito verificador
		for (int i = 0; i < ie.length() - 2; i++) {
			if (i < 3) {
				soma += Integer.parseInt(String.valueOf(ie.charAt(i)))
						* pesoInicio;
				pesoInicio--;
			} else {
				soma += Integer.parseInt(String.valueOf(ie.charAt(i)))
						* pesoFim;
				pesoFim--;
			}
		}

		d1 = 11 - (soma % 11);
		if (d1 == 11 || d1 == 10) {
			d1 = 0;
		}

		// c�lculo do segundo d�gito verificador
		soma = d1 * 2;
		pesoInicio = 5;
		pesoFim = 9;
		int d2 = -1; // segundo d�gito verificador
		for (int i = 0; i < ie.length() - 2; i++) {
			if (i < 4) {
				soma += Integer.parseInt(String.valueOf(ie.charAt(i)))
						* pesoInicio;
				pesoInicio--;
			} else {
				soma += Integer.parseInt(String.valueOf(ie.charAt(i)))
						* pesoFim;
				pesoFim--;
			}
		}

		d2 = 11 - (soma % 11);
		if (d2 == 11 || d2 == 10) {
			d2 = 0;
		}

		// valida os digitos verificadores
		String dv = d1 + "" + d2;
		if (!dv.equals(ie.substring(ie.length() - 2, ie.length()))) {
			throw new Exception(this.DIGITO_VERIFICADOR_INVALIDO);
		}
	}
}

