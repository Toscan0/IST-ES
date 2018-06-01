package pt.ulisboa.tecnico.softeng.broker;

import org.joda.time.LocalDate;

import pt.ulisboa.tecnico.softeng.bank.domain.Account;
import pt.ulisboa.tecnico.softeng.bank.domain.Bank;
import pt.ulisboa.tecnico.softeng.bank.domain.Client;
import pt.ulisboa.tecnico.softeng.broker.domain.Adventure;
import pt.ulisboa.tecnico.softeng.broker.domain.Broker;
import pt.ulisboa.tecnico.softeng.broker.domain.BClient;

public class Application {

	public static void main(String[] args) {
		System.out.println("Adventures!");

		Bank bank = new Bank("MoneyPlus", "BK01");
		Client client = new Client(bank, "José dos Anzóis");
		Account account = new Account(bank, client);
		account.deposit(1000);
		BClient client2 = new BClient(account.getIBAN(), "NIF", 33);
		
		Broker broker = new Broker("BR01", "Fun","NIFSELLER", "NIFBUYER");
		Adventure adventure = new Adventure(broker, new LocalDate(), new LocalDate(), client2, 50, true);

		adventure.process();

		System.out.println("Your payment reference is " + adventure.getPaymentConfirmation() + " and you have "
				+ account.getBalance() + " euros left in your account");
	}

}
