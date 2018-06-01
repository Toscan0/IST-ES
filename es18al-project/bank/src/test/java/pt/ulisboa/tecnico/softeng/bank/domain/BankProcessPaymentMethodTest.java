package pt.ulisboa.tecnico.softeng.bank.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import pt.ulisboa.tecnico.softeng.bank.dataobjects.BankOperationData;
import pt.ulisboa.tecnico.softeng.bank.domain.Operation.Type;
import pt.ulisboa.tecnico.softeng.bank.exception.BankException;

public class BankProcessPaymentMethodTest {
	private static int AMOUNT = 100;
	private Bank bank;
	private Account account;
	private String reference;

	@Before
	public void setUp() {
		
	}
	
	@Test
	public void success() {
		this.bank = new Bank("Money", "BK01");
		Client client = new Client(this.bank, "António");
		this.account = new Account(this.bank, client);
		this.reference = this.account.deposit(AMOUNT);
		
		String referencia = Bank.processPayment(account.getIBAN(), 50);
		
		assertNotNull(referencia);
	} 
	
	@Test
	public void success_Min_amount() {
		this.bank = new Bank("Money", "BK01");
		Client client = new Client(this.bank, "António");
		this.account = new Account(this.bank, client);
		this.reference = this.account.deposit(AMOUNT);
		
		String referencia = Bank.processPayment(account.getIBAN(), 1);
		
		assertNotNull(referencia);
	}
	
	@Test
	public void success_Max_amount() {
		this.bank = new Bank("Money", "BK01");
		Client client = new Client(this.bank, "António");
		this.account = new Account(this.bank, client);
		this.reference = this.account.deposit(AMOUNT);
		
		String referencia = Bank.processPayment(account.getIBAN(), 100);
		
		assertNotNull(referencia);		
	}
	
/* 	@Test
	public void minimo_amount() {
		Client client = new Client(this.bank, "António");
		this.account = new Account(this.bank, client);
		this.reference = this.account.deposit(AMOUNT);
		
		String referencia = Bank.processPayment(account.getIBAN(), 1);
	
		
		assertEquals(referencia, "BK0123");
	}*/ 
	
	@Test(expected = BankException.class)
	public void nulo_IBAN() {
		this.bank = new Bank("Money", "BK01");
		
		String referencia = Bank.processPayment(null, 50);
	}
	
	
	@Test(expected = BankException.class)
	public void n_exist_IBAN() {
		this.bank = new Bank("Money", "BK01");
		Client client = new Client(this.bank, "António");
		this.account = new Account(this.bank, client);
		this.reference = this.account.deposit(AMOUNT);
		
		String referencia = Bank.processPayment("IBANFALSO", 50);
	}
	
	@Test(expected = BankException.class)
	public void nulo_account() {
		this.bank = new Bank("Money", "BK01");
		String referencia = Bank.processPayment(null, 50);
	}
	
	@Test(expected = BankException.class)
	public void sem_IBAN() {
		this.bank = new Bank("Money", "BK01");
		Client client = new Client(this.bank, "António");
		this.account = new Account(this.bank, client);
		this.reference = this.account.deposit(AMOUNT);
		
		String referencia = Bank.processPayment("", 50);
	}

	@Test(expected = BankException.class)
	public void maxAmount() {
		this.bank = new Bank("Money", "BK01");
		Client client = new Client(this.bank, "António");
		this.account = new Account(this.bank, client);
		this.reference = this.account.deposit(AMOUNT);
		
		String referencia = Bank.processPayment(account.getIBAN(), 101);
	}

	@Test(expected = BankException.class)
	public void minAmount() {
		this.bank = new Bank("Money", "BK01");
		Client client = new Client(this.bank, "António");
		this.account = new Account(this.bank, client);
		this.reference = this.account.deposit(AMOUNT);
		
		String referencia = Bank.processPayment(account.getIBAN(), -1);
	}
	
	@Test(expected = BankException.class)
	public void miniAmount() {
		this.bank = new Bank("Money", "BK01");
		Client client = new Client(this.bank, "António");
		this.account = new Account(this.bank, client);
		this.reference = this.account.deposit(AMOUNT);
		
		String referencia = Bank.processPayment(account.getIBAN(), 0);
	}
	
	@Test(expected = BankException.class)
	public void bankNull() {
		this.bank = new Bank(null, null);
		Client client = new Client(this.bank, "António");
		this.account = new Account(this.bank, client);
		this.reference = this.account.deposit(AMOUNT);
		
		String referencia = Bank.processPayment(account.getIBAN(), 0);
	}
	@Test(expected = BankException.class)
	public void clientNull() {
		this.bank = new Bank("Money", "BK01");
		Client client = new Client(null, null);
		this.account = new Account(this.bank, client);
		this.reference = this.account.deposit(AMOUNT);
		
		String referencia = Bank.processPayment(account.getIBAN(), 0);
	}
	@Test(expected = BankException.class)
	public void accountNull() {
		this.bank = new Bank("Money", "BK01");
		Client client = new Client(this.bank, "António");
		this.account = new Account(null, null);
		this.reference = this.account.deposit(AMOUNT);
		
		String referencia = Bank.processPayment(account.getIBAN(), 0);
	}
	
	@Test(expected = BankException.class)
	public void sem_Banco() {
		
		String referencia = Bank.processPayment(" ", 20);
	}
	
	@After
	public void tearDown() {
		Bank.banks.clear();
	}
}


