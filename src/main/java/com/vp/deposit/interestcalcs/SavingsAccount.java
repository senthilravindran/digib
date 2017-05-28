package com.vp.deposit.interestcalcs;

public class SavingsAccount
{
//Data fields
private double balance; //Account Balance
private double annualInterestRate; //Account annual interest rate
private double monthlyInterestRate; 
private double totalDeposits; 
private double totalWithdraws; 
private double totalInterest; 

/**
 * Constructor
 * @param startBalance The account's balance. 
 * @param annual_Interest_Rate The annual interest rate. 
 */
public SavingsAccount(double startBalance, double annual_Interest_Rate)
{
    balance = startBalance;
    annualInterestRate = annual_Interest_Rate; 
}
//end of Constructor

/**
* setAnnualInterestRate method sets the annual interest 
* rate and calculates the monthly interest rate
* @param annual_Interest_Rate The annual interest rate.
*/
public void setAnnualInterestRate(double annual_Interest_Rate)
{
   monthlyInterestRate = annualInterestRate / 12; 
}
//end of setAnnualInterestRate method

/**
* The deposit method adds the amount to the balance  
* and calculates the total deposit
* @param amount
*/
public void setDeposit(double amount)
{
   balance += amount; 
   totalDeposits += amount; 
}
//end of deposit method

/**
* The withdraw method subtracts the amount to the balance
* and calculates the total withdraws
* @param amount
*/
public void setWithdraw(double amount)
{
   balance -= amount; 
   totalWithdraws += amount; 
}
//end of withdraw method

/**
* The calculateMonthlyInterest method calculates the total 
* interest and adds the monthly interest to the balance 
*/
public void calculateMonthlyInterest()
{
   totalInterest = totalInterest + balance * monthlyInterestRate; 
   balance = balance + balance * monthlyInterestRate; 
}
//end of calculateMonthlyInterest method

/**
* The getBalance method returns the account's balance. 
* @return The value of the balance field.
*/
public double getBalance()
{
   return balance; 
}

/**
* The getAnnualInterestRate method returns the annual interest rate.
* @return The value of the annual interest rate field.
*/
public double getAnnualInterestRate()
{
   return annualInterestRate; 
}

/**
* The getMonthlyInterestRate method returns the monthly interest rate.
* @return The value of the monthly interest rate field.
*/
public double getMonthlyInterestRate()
{
   return monthlyInterestRate; 
}

/**
* The getTotalDeposits method returns the total deposit amount.
* @return The value of the total deposits field.
*/
public double getTotalDeposits()
{
   return totalDeposits; 
}

/**
* The getTotalWithdraws method returns the total withdraws amount.
* @return The value of the total withdraws field.
*/
public double getTotalWithdraws()
{
   return totalWithdraws; 
}

/**
* The getTotalInterest method returns the total interest amount.
* @return The value of the total interest field.
*/
public double getTotalnterest()
{
   return totalInterest; 
}

/* displayData method displays the ending details of the savings account */
public void displayData()
{
   balance = Math.round(balance * 100.0) / 100.0; 
   totalInterest = Math.round(totalInterest * 100.0) / 100.0; 
   System.out.println(); 
   System.out.println("The ending balance is: $" + balance); 
   System.out.println("Total amount of deposits: $" + totalDeposits);
   System.out.println("Total amount of withdraws: $" + totalWithdraws);
   System.out.println("Total interest earned: $" + totalInterest);
}
//end of displayData method
}
