package main.agent;

import main.utility.Sfmt;

public class Gene implements IGene{
	//bit number
	private int digit;

	//main body
	private int[] gene;

//----------------------------------------
	public Gene(int digit) {
		this.digit = digit;
		gene = new int[digit];
	}

//----------------------------------------
	public int getDigit() {
		return digit;
	}
	public int[] getGene() {
		return gene;
	}
	public int getGeneToInt() {

		int result=0;
		for(int d=0; d<digit; d++) {

			if(gene[d]==0) continue;
			result += (int)Math.pow(2, d);
		}
		return result;
	}
	public double getGeneToDouble() {
		double result = (double)getGeneToInt();
		return result / (Math.pow(2, digit) - 1);
	}
	public String getGeneToPrint() {
		String result = "[";
		for(int d=0; d<digit; d++) {
			result = result + String.valueOf(gene[d]);
			if(d!=digit-1) {
				result = result + ", ";
			}
		}
		result = result + "]";
		return result;
	}

//----------------------------------------
	public void setGene(int value) {
		int v = value;
		for(int d=0; d<digit; d++) {
			gene[d] = v % 2;
			v /= 2;
		}
	}
	public void setGene(int[] g) {
		for(int d=0; d<this.digit; d++) {
			gene[d] = g[d];
		}
	}
	public void setGene(Sfmt sfmt) {
		int result=0;
		double res=0;
		do {
				res = (sfmt.NextNormal() / (2 * MAX_NEXT_NORMAL) + 0.5) * Math.pow(2, digit);
				result  = (int) res;

		}while(result<0 || result>=(int)Math.pow(2, digit));

		setGene(result);
	}

//----------------------------------------
	public void changeBit(int d) {
		if(gene[d] == 0) {
			gene[d] = 1;
		}
		else {
			gene[d] = 0;
		}
	}
}
