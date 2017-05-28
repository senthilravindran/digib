package com.vp.lending;

import java.text.DecimalFormat;

public class Calc {
	
	public static void main (String args[]) {
		
		Calc calc = new Calc();


	       // setting the variables 
	        calc.term = 30;
	        calc.interestRate = (float) 0.0575;
	        calc.principal = (float) 200000.00;
	        calc.monthlyPayment = 0;




	      //format currency correctly
	            DecimalFormat df = new java.text.DecimalFormat("$,###.00");  

	       //Method call to calc class
	        try {
				calc.DoWork();
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

	        //out put 
	        System.out.println("\n__________________________________________________________________________________________________________");
	        System.out.println("\nPrincipal Amount: " + df.format (calc.principal ));
	        System.out.println("interest rate: " + calc.interestRate);
	        System.out.println("Term of loan(number of years): " + calc.term);
	        System.out.println("Monthly payment is: " + df.format (calc.monthlyPayment));
	        System.out.println("__________________________________________________________________________________________________________\n");
	        try {
				Thread.sleep(600);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}


	        try {
				calc.DoWorkAmortization();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	}

	int term; // how long the loan is
	float interestRate; // loan interest rate
	float principal; // loan amount
	float monthlyPayment; // monthly payment
	private int period = 12; // 12 months in a year

	// Amortization Variables

	int n = 360;
	double i = 0.0575;
	double a = 200000.00;
	double r = (1 + i / 12);

	DecimalFormat df = new java.text.DecimalFormat("$,###.00");

	// Method to calculate the payment.
	void DoWork() throws InterruptedException {

		// Payment calculation
		monthlyPayment = (float) (principal * Math.pow(1 + interestRate / period, term * period)
				* (interestRate / period) / (Math.pow(1 + interestRate / period, term * period) - 1));

	}// Method to calculate Amortization

	void DoWorkAmortization() throws InterruptedException {

		int number = 1;
		double monthlyPayments = a * (r - 1) / (1 - Math.pow(r, -n));
		System.out.println(
				"Month \t\t Payment \t\0\0\0\0 Interest Paid \t\0\0\0\0 Principal Paid \t\0\0\0\0 Remaining Balance");
		System.out.println(
				"__________________________________________________________________________________________________________");

		for (number = 1; number <= 360; number++) {

			double interest = a * (i / 12);
			double principal = monthlyPayments - interest;
			double balance = a - principal;
			a = balance;

			System.out.println("\0\0" + number + "\t\t" + df.format(monthlyPayments) + "\t\t" + df.format(interest)
					+ "\t\t\t" + df.format(principal) + "\t\t\t" + df.format(balance));

			if (number % 12 == 0) {

				Thread.sleep(600);

			}

		}

	}
}
