package pt.ulisboa.tecnico.softeng.broker.services.local;

import java.util.ArrayList;
import java.util.List;

import pt.ist.fenixframework.Atomic;
import pt.ist.fenixframework.Atomic.TxMode;
import pt.ist.fenixframework.FenixFramework;
import pt.ulisboa.tecnico.softeng.broker.domain.Adventure;
import pt.ulisboa.tecnico.softeng.broker.domain.Broker;
import pt.ulisboa.tecnico.softeng.broker.domain.BulkRoomBooking;
import pt.ulisboa.tecnico.softeng.broker.domain.Client;
import pt.ulisboa.tecnico.softeng.broker.services.local.dataobjects.AdventureData;
import pt.ulisboa.tecnico.softeng.broker.services.local.dataobjects.BrokerData;
import pt.ulisboa.tecnico.softeng.broker.services.local.dataobjects.BrokerData.CopyDepth;
import pt.ulisboa.tecnico.softeng.broker.services.local.dataobjects.BulkData;
import pt.ulisboa.tecnico.softeng.broker.services.local.dataobjects.ClientData;

public class BrokerInterface {

	@Atomic(mode = TxMode.READ)
	public static List<BrokerData> getBrokers() {
		List<BrokerData> brokers = new ArrayList<>();
		for (Broker broker : FenixFramework.getDomainRoot().getBrokerSet()) {
			brokers.add(new BrokerData(broker, CopyDepth.SHALLOW));
		}
		return brokers;
	}
	
	@Atomic(mode = TxMode.READ)
	public static List<ClientData> getClients() {
		List<ClientData> clients = new ArrayList<>();
		for (Broker broker : FenixFramework.getDomainRoot().getBrokerSet()) {
			for (Client client : broker.getClient()) {
				clients.add(new ClientData(client));
			}
		}
		return clients;
	}

	@Atomic(mode = TxMode.WRITE)
	public static void createBroker(BrokerData brokerData) {
		new Broker(brokerData.getCode(), brokerData.getName(), brokerData.getNifAsSeller(), brokerData.getNifAsBuyer(),
				brokerData.getIban());
	}

	@Atomic(mode = TxMode.READ)
	public static BrokerData getBrokerDataByCode(String brokerCode,CopyDepth depth) {
		Broker broker = getBrokerByCode(brokerCode);

		if (broker != null) {
			return new BrokerData(broker, depth);
		} else {
			return null;
		}
	}
	
	@Atomic(mode = TxMode.READ)
	public static BrokerData getBrokerDataByCodeIban(String brokerCode, String iban, CopyDepth depth) {
		Broker broker = getBrokerByCodeIban(brokerCode, iban);

		if (broker != null) {
			return new BrokerData(broker, depth);
		} else {
			return null;
		}
	}
	
	@Atomic(mode = TxMode.READ)
	public static ClientData getClientDataByIban(String iban, CopyDepth depth) {
		Client client = getClientByIban(iban);

		if (client != null) {
			return new ClientData(client);
		} else {
			return null;
		}
	}



	@Atomic(mode = TxMode.WRITE)
	public static void createAdventure(String brokerCode, String iban, AdventureData adventureData) {
		// TODO: receive client and margin
		new Adventure(getBrokerByCode(brokerCode),  adventureData.getBegin(), adventureData.getEnd(), null, 0.1);
	}
	
	@Atomic(mode = TxMode.WRITE)
	public static void createClient(String brokerCode, ClientData clientData) {
		// TODO: receive client and margin
		new Client(getBrokerByCode(brokerCode), clientData.getIban(), clientData.getNif(), clientData.getDrivingLicense(), clientData.getAge());
	}

	@Atomic(mode = TxMode.WRITE)
	public static void createBulkRoomBooking(String brokerCode, BulkData bulkData) {
		// TODO: receive nif and iban
		new BulkRoomBooking(getBrokerByCode(brokerCode), bulkData.getNumber() != null ? bulkData.getNumber() : 0,
				bulkData.getArrival(), bulkData.getDeparture(), "ERROR", "ERROR");

	}

	private static Broker getBrokerByCode(String code) {
		for (Broker broker : FenixFramework.getDomainRoot().getBrokerSet()) {
			if (broker.getCode().equals(code)) {
				return broker;
			}
		}
		return null;
	}
	private static Broker getBrokerByCodeIban(String code, String iban) {
		for (Broker broker : FenixFramework.getDomainRoot().getBrokerSet()) {
			if (broker.getCode().equals(code) && broker.getClientByNIF(iban)!=null) {
				return broker;
			}
		}
		return null;
	}
	
	@SuppressWarnings("deprecation")
	private static Client getClientByIban(String iban) {
		for (Broker broker : FenixFramework.getDomainRoot().getBrokerSet()) {
			for (Client client : broker.getClient()) {
				if (client.getIban().equals(iban)) {
					return client;
				}
			}
		}
		return null;
	}

}
