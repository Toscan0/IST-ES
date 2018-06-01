package pt.ulisboa.tecnico.softeng.broker.domain;

import pt.ulisboa.tecnico.softeng.bank.exception.BankException;
import pt.ulisboa.tecnico.softeng.broker.domain.Adventure.State;
import pt.ulisboa.tecnico.softeng.broker.exception.RemoteAccessException;
import pt.ulisboa.tecnico.softeng.broker.interfaces.BankInterface;
import pt.ulisboa.tecnico.softeng.tax.exception.TaxException;
import pt.ulisboa.tecnico.softeng.tax.dataobjects.InvoiceData;
import pt.ulisboa.tecnico.softeng.broker.interfaces.TaxInterface;

public class ProcessPaymentState extends AdventureState {
	public static final int MAX_REMOTE_ERRORS = 3;

	@Override
	public State getState() {
		return State.PROCESS_PAYMENT;
	}

	@Override
	public void process(Adventure adventure) {
		try {
			adventure.setPaymentConfirmation(BankInterface.processPayment(adventure.getBClient().getIBAN(), adventure.getAmount()));	

			
			try {
				/* InvoiceData(String sellerNIF, String buyerNIF, 
				 * String itemType, double value, 
				 * LocalDate date) */
				InvoiceData invoiceData = new InvoiceData(adventure.getBroker().getNifSeller(), 
						adventure.getBroker().getNifBuyer(), adventure.getType(),(double) adventure.getAmount(), 
					adventure.getBegin());
				
				adventure.setInvoiceReference(TaxInterface.submitInvoice(invoiceData));

			}
			catch (TaxException tax) {
				adventure.setState(State.CANCELLED);
				return;
			}

		} 
		catch (BankException be) {
			adventure.setState(State.UNDO);
			return;
		} 
		catch (RemoteAccessException rae) {
			incNumOfRemoteErrors();
			if (getNumOfRemoteErrors() == MAX_REMOTE_ERRORS) {
				adventure.setState(State.UNDO);
			}
			return;

		} 
		
		adventure.setState(State.CONFIRMED);
	}
}
