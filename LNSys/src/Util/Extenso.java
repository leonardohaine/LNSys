package Util;










import java.math.BigInteger;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.text.DecimalFormat;


public class Extenso {
	private ArrayList nro;
	private BigInteger num;

	private String Qualificadores[][] = {
			{"centavo", "centavos"},
			{"", ""},
			{"mil", "mil"},
			{"milhão", "milhões"},
			{"bilhão", "bilhões"},
			{"trilhão", "trilhões"},
			{"quatrilhão", "quatrilhões"},
			{"quintilhão", "quintilhões"},
			{"sextilhão", "sextilhões"},
			{"septilhão", "septilhões"}
			};
	private String Numeros[][] = {
			{"zero", "um", "dois", "três", "quatro", "cinco", "seis", "sete", "oito", "nove", "dez",
			"onze", "doze", "treze", "quatorze", "quinze", "desesseis", "desessete", "dezoito", "desenove"},
			{"vinte", "trinta", "quarenta", "cinquenta", "sessenta", "setenta", "oitenta", "noventa"},
			{"cem", "cento", "duzentos", "trezentos", "quatrocentos", "quinhentos", "seiscentos",
			"setecentos", "oitocentos", "novecentos"}
			};


	/**
	 *  Construtor
	 */
	public Extenso() {
		nro = new ArrayList();
	}


	/**
	 *  Construtor
	 *
	 *@param  dec  valor para colocar por extenso
	 */
	public Extenso(BigDecimal dec) {
		this();
		setNumber(dec);
	}


	/**
	 *  Constructor for the Extenso object
	 *
	 *@param  dec  valor para colocar por extenso
	 */
	public Extenso(double dec) {
		this();
		setNumber(dec);
	}


	/**
	 *  Sets the Number attribute of the Extenso object
	 *
	 *@param  dec  The new Number value
	 */
	public void setNumber(BigDecimal dec) {
		// Converte para inteiro arredondando os centavos
		num = dec
			.setScale(2, BigDecimal.ROUND_HALF_UP)
			.multiply(BigDecimal.valueOf(100))
			.toBigInteger();

		// Adiciona valores
		nro.clear();
		if (num.equals(BigInteger.ZERO)) {
			// Centavos
			nro.add(new Integer(0));
			// Valor
			nro.add(new Integer(0));
		}
		else {
			// Adiciona centavos
			addRemainder(100);

			// Adiciona grupos de 1000
			while (!num.equals(BigInteger.ZERO)) {
				addRemainder(1000);
			}
		}
	}

	public void setNumber(double dec) {
		setNumber(new BigDecimal(dec));
	}

	/**
	 *  Description of the Method
	 */
	public void show() {
		Iterator valores = nro.iterator();

		while (valores.hasNext()) {
			System.out.println(((Integer) valores.next()).intValue());
		}
		System.out.println(toString());
	}


	/**
	 *  Description of the Method
	 *
	 *@return    Description of the Returned Value
	 */
	public String toString() {
		StringBuffer buf = new StringBuffer();

		int numero = ((Integer) nro.get(0)).intValue();
		int ct;

		for (ct = nro.size() - 1; ct > 0; ct--) {
			// Se ja existe texto e o atual não é zero
			if (buf.length() > 0 && ! ehGrupoZero(ct)) {
				buf.append(" e ");
			}
			buf.append(numToString(((Integer) nro.get(ct)).intValue(), ct));
		}
		if (buf.length() > 0) {
			if (ehUnicoGrupo())
				buf.append(" de ");
			while (buf.toString().endsWith(" "))
				buf.setLength(buf.length()-1);
			if (ehPrimeiroGrupoUm())
				buf.insert(0, "h");
			if (nro.size() == 2 && ((Integer)nro.get(1)).intValue() == 1) {
				buf.append(" real");
			} else {
				buf.append(" reais");
			}
			if (((Integer) nro.get(0)).intValue() != 0) {
				buf.append(" e ");
			}
		}
		if (((Integer) nro.get(0)).intValue() != 0) {
			buf.append(numToString(((Integer) nro.get(0)).intValue(), 0));
		}
		return buf.toString();
	}

	private boolean ehPrimeiroGrupoUm() {
		if (((Integer)nro.get(nro.size()-1)).intValue() == 1)
			return true;
		return false;
	}

	/**
	 *  Adds a feature to the Remainder attribute of the Extenso object
	 *
	 *@param  divisor  The feature to be added to the Remainder attribute
	 */
	private void addRemainder(int divisor) {
		// Encontra newNum[0] = num modulo divisor, newNum[1] = num dividido divisor
		BigInteger[] newNum = num.divideAndRemainder(BigInteger.valueOf(divisor));

		// Adiciona modulo
		nro.add(new Integer(newNum[1].intValue()));

		// Altera numero
		num = newNum[0];
	}


	/**
	 *  Description of the Method
	 *
	 *@param  ps  Description of Parameter
	 *@return     Description of the Returned Value
	 */
	private boolean temMaisGrupos(int ps) {
		for (; ps > 0; ps--) {
			if (((Integer) nro.get(ps)).intValue() != 0) {
				return true;
			}
		}

		return false;
	}


	/**
	 *  Description of the Method
	 *
	 *@param  ps  Description of Parameter
	 *@return     Description of the Returned Value
	 */
	private boolean ehUltimoGrupo(int ps) {
		return (ps > 0) && ((Integer)nro.get(ps)).intValue() != 0 && !temMaisGrupos(ps - 1);
	}


	/**
	 *  Description of the Method
	 *
	 *@return     Description of the Returned Value
	 */
	private boolean ehUnicoGrupo() {
		if (nro.size() <= 3)
			return false;
		if (!ehGrupoZero(1) && !ehGrupoZero(2))
			return false;
		boolean hasOne = false;
		for(int i=3; i < nro.size(); i++) {
			if (((Integer)nro.get(i)).intValue() != 0) {
				if (hasOne)
					return false;
				hasOne = true;
			}
		}
		return true;
	}

	boolean ehGrupoZero(int ps) {
		if (ps <= 0 || ps >= nro.size())
			return true;
		return ((Integer)nro.get(ps)).intValue() == 0;
	}

	/**
	 *  Description of the Method
	 *
	 *@param  numero  Description of Parameter
	 *@param  escala  Description of Parameter
	 *@return         Description of the Returned Value
	 */
	private String numToString(int numero, int escala) {
		int unidade = (numero % 10);
		int dezena = (numero % 100); //* nao pode dividir por 10 pois verifica de 0..19
		int centena = (numero / 100);
		StringBuffer buf = new StringBuffer();

		if (numero != 0) {
			if (centena != 0) {
				if (dezena == 0 && centena == 1) {
					buf.append(Numeros[2][0]);
				}
				else {
					buf.append(Numeros[2][centena]);
				}
			}

			if ((buf.length() > 0) && (dezena != 0)) {
				buf.append(" e ");
			}
			if (dezena > 19) {
				dezena /= 10;
				buf.append(Numeros[1][dezena - 2]);
				if (unidade != 0) {
					buf.append(" e ");
					buf.append(Numeros[0][unidade]);
				}
			}
			else if (centena == 0 || dezena != 0) {
				buf.append(Numeros[0][dezena]);
			}

			buf.append(" ");
			if (numero == 1) {
				buf.append(Qualificadores[escala][0]);
			}
			else {
				buf.append(Qualificadores[escala][1]);
			}
		}

		return buf.toString();
	}


	/**
	 *  Para teste
	 *
	 *@param  args  numero a ser convertido
	 */
	public static void main(String[] args) {
//		if (args.length == 0) {
//			System.out.println("Sintax : ...Extenso <numero>");
//			return;
//		}
Extenso teste = new Extenso(new BigDecimal(1150));
System.out.println("Numero  : " + (new DecimalFormat().format(Double.valueOf("1150.00"))));
System.out.println("Extenso : " + teste.toString().toUpperCase());
	}
}



















//
//
//
//
//
//
//public class Extenso {
//  /**  Description of the Field */
//  private double num; //The number that is going to be converted
//  /**  Description of the Field */
//  private String s; //The String that is going to be returned
//  /**  Description of the Field */
//  private int maxlen; //our result string's wrap limit..
//  /**  Description of the Field */
//  private int cut_point;
//  /**  Description of the Field */
//  private boolean centavo = false;
//
//  //Constructors
//  /**Construtor para o objeto Extenso */
//  public Extenso() { }
//
//  /**
//   *Construtor para o objeto Extenso
//   *
//   * @param  num_     Description of the Parameter
//   * @param  maxlen_  Description of the Parameter
//   */
//  public Extenso(double num_, int maxlen_) {
//    setNumber(num_, maxlen_);
//  }
//
//  /**
//   * To set the number to be converted
//   *
//   * @param  num_     Description of the Parameter
//   * @param  maxlen_  Description of the Parameter
//   */
//  public void setNumber(double num_, int maxlen_) {
//    num = num_;
//    s = new String();
//    maxlen = maxlen_;
//    Extenso();
//  }
//
//  /** The function that makes the convertion */
//  private void Extenso() {
//
//    String nome[] = {
//        "um bi-lhão",
//        " bi-lhões",
//        "um mi-lhão",
//        " mi-lhões"};
//    long n = (long)num;
//    long mil_milhoes;
//    long milhoes;
//    long milhares;
//    long unidades;
//    long centavos;
//    char numero[];
//    double frac = num - n;
//    int nl;
//    int rp;
//    int last;
//    int p;
//    int len;
//    if (num == 0) {
//      s += "zero";
//      return;
//    }
//    mil_milhoes = (n - n % 1000000000) / 1000000000;
//    n -= mil_milhoes * 1000000000;
//    milhoes = (n - n % 1000000) / 1000000;
//    n -= milhoes * 1000000;
//    milhares = (n - n % 1000) / 1000;
//    n -= milhares * 1000;
//    unidades = n;
//    centavos = (long)(frac * 100);
//    if ((long)(frac * 1000 % 10) > 5) {
//      centavos++;
//    }
////                      s = "\0";
//    //s[0] = '\0' ; //??
//    if (mil_milhoes > 0) {
//      if (mil_milhoes == 1) {
//        s += nome[0];
//      } else {
//        s += numero(mil_milhoes);
//        s += nome[1];
//      }
//      if ((unidades == 0) && ((milhares == 0) && (milhoes > 0))) {
//        s += " e ";
//      } else if ((unidades != 0) || ((milhares != 0) || (milhoes != 0))) {
//        s += ", ";
//      }
//    }
//    if (milhoes > 0) {
//      if (milhoes == 1) {
//        s += nome[2];
//      } else {
//        s += numero(milhoes);
//        s += nome[3];
//      }
//      if ((unidades == 0) && (milhares > 0)) {
//        s += " e ";
//      } else if ((unidades != 0) || (milhares != 0)) {
//        s += ", ";
//      }
//    }
//    if (milhares > 0) {
//      if (milhares != 1) {
//        s += numero(milhares);
//      }
//      s += " mil";
//      if (unidades > 0) {
//        if ((milhares > 100) && (unidades > 100)) {
//          s += ", ";
//        } else if (((unidades % 100) != 0) || ((unidades % 100 == 0) &&
//(milhares < 10))) {
//          s += " e ";
//        } else {
//          s += " ";
//        }
//      }
//    }
//    s += numero(unidades);
//    if (num > 0) {
//      s += ((long)num == 1L) ? " real" : " reais";
//    }
//    if (centavos != 0) {
//      if (n != 0) {
//        centavo = true;
//      }
//      s += " e ";
//      s += numero(centavos);
//      s += (centavos==1) ? " cen-ta-vo" : " cen-ta-vos";
//    }
//
//    len = s.length();
//    StringBuffer sar = new StringBuffer(s);
//    StringBuffer l = new StringBuffer();
//    last = 0;
//    rp = 0;
//    nl = 1;
//
//    for (p = 0; p < len; ++p) {
//      if (sar.charAt(p) != '-') {
//        rp++;
//      }
//      if (rp > maxlen) {
//        if (sar.charAt(last) == ' ') {
//          sar.replace(last, last + 1, "\n");
//        } else {
//          sar.insert(last + 1, '\n');
//        }
//        rp -= maxlen;
//        nl++;
//      }
//      if ((sar.charAt(p) == ' ') || (sar.charAt(p) == '-')) {
//        last = p;
//      }
//    } //for
//    rp = 0;
//    len = sar.length();
//
//    for (p = 0; p < len; ++p) {
//      if (!((sar.charAt(p) == '-') && (sar.charAt(p + 1) != '\n'))) {
//        l.insert(rp++, sar.charAt(p));
//      }
//    } //for
//
//    s = l.toString();
//  }
//
//  /**
//   * Return the written form of the number
//   *
//   * @return    ...
//   */
//  public String getResult() {
//    String temp;
//    if (s == null) {
//      return "Number is not set!";
//    }
//    temp = s;
//    s = null;
//    return temp;
//  }
//
//
//  /**
//   * Return the numbers between 0-999 in written form
//   *
//   * @param  n  Description of the Parameter
//   * @return    ...
//   */
//  private String numero(long n) {
//    int flag;
//    String u[] = {"", "um", "dois", "tres", "qua-tro", "cin-co", "seis",
//"se-te", "oi-to", "no-ve", "dez", "on-ze", "do-ze", "tre-ze", "ca-tor-ze",
//"quin-ze", "de-zas-seis", "de-zas-sete", "de-zoi-to", "de-za-no-ve"};
//    String d[] = {"", "", "vin-te", "trin-ta", "qua-ren-ta", "cin-quen-ta",
//"ses-sen-ta", "se-ten-ta", "oi-ten-ta", "no-ven-ta"};
//    String c[] = {"", "cen-to", "du-zen-tos", "tre-zen-tos",
//"qua-tro-cen-tos", "qui-nhen-tos", "seis-cen-tos", "se-te-cen-tos",
//"oi-to-cen-tos", "no-ve-cen-tos"};
//    String extenso_do_numero = new String();
////                      extenso_do_numero  = "\0" ;
//    if ((n < 1000) && (n != 0)) {
//      if (n == 100) {
//        extenso_do_numero = "cem";
//      } else {
//        if (n > 99) {
//          extenso_do_numero += c[(int)(n / 100)];
//          if (n % 100 > 0) {
//            extenso_do_numero += " e ";
//          }
//        }
//        if (n % 100 < 20) {
//          extenso_do_numero += u[(int)n % 100];
//        } else {
//          extenso_do_numero += d[((int)n % 100) / 10];
//          if ((n % 10 > 0) && (n > 10)) {
//            extenso_do_numero += " e ";
//            extenso_do_numero += u[(int)n % 10];
//          }
//        }
//      }
//    } else if (n > 999) {
//      extenso_do_numero = "<<ERRO: NUMERO > 999>>";
//    }
//    return extenso_do_numero;
//  }
//
//  public static void main(String[] args) {
//    StringBuffer sb = new StringBuffer();
//    double nn = 1150.00D;
//
//    if (args.length > 0) {
//      try {
//        nn = Double.parseDouble(args[0]);
//      } catch (NumberFormatException e) {
//        System.out.println("Use java Extenso [número com '.' decimal]");
//        System.exit(1);
//      }
//    }
//
//    Extenso ex = new Extenso(nn, 50);
//
//    sb.append(String.valueOf(nn)).append(" = \r");
//    sb.append(ex.getResult());
//    System.out.println(sb);
//
//  }
//}
