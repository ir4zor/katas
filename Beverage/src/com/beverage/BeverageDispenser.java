package com.beverage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class BeverageDispenser {

	private final static Map<String, Double> BEVERAGES = new HashMap<String, Double>();
	static {
		BEVERAGES.put("COCA", 3.0);
		BEVERAGES.put("ORANGINA", 2.5);
		BEVERAGES.put("PERRIER", 2.5);
	}

	private List<Double> coinsInserted = new ArrayList<Double>();

	/**
	 * 
	 * @param commandLine
	 * @return
	 */
	public String interact(String commandLine) {

		if (!isValidInput(commandLine)) {
			return "";
		}

		StringBuilder result = new StringBuilder();
		String[] commands = commandLine.split(",");

		for (String token : commands) {
			try {
				String[] command = token.trim().split(" ");
				switch (command[0]) {
				case "PUT":
					commandPutCoins(command);
					break;
				case "GET":
					commandGetBeverage(result, command);
					break;
				case "COIN-RETURN":
					commandReturnChange(result);
					break;
				default : 
				}

			} catch (Throwable e) {
				// Should never happen
				// TODO JSC add log
				// coin-return ?
				System.out.println(e);
			}
		}
		return result.toString();
	}

	// main command methods

	/**
	 * 
	 * @param command
	 */
	private void commandPutCoins(String[] command) {
		coinsInserted.add(Double.parseDouble(command[1]));
	}

	/**
	 * 
	 * @param result
	 * @param command
	 */
	private void commandGetBeverage(StringBuilder result, String[] command) {
		double beveragePrice = BEVERAGES.get(command[1]);
		if (enoughMoneyToPay(beveragePrice)) {
			payBeverage(coinsInserted, beveragePrice);
			addToOutput(result, command[1]);
		}
	}

	/**
	 * 
	 * @param result
	 */
	private void commandReturnChange(StringBuilder result) {
		if (moneyInserted() != 0) {
			for (Double coin : coinsInserted) {
				addToOutput(result, coin);
			}
			coinsInserted.clear();
		}
	}

	// helper methods

	private boolean isValidInput(String commandLine) {
		if (commandLine == null) {
			return false;
		}
		// TODO JSC add validations
		return true;
	}

	/**
	 * 
	 * @param result
	 * @param command
	 */
	private void addToOutput(StringBuilder result, Object command) {
		if (result.length() > 0) {
			result.append(", ");
		}
		result.append(command);
	}

	/**
	 * 
	 * @param beveragePrice
	 * @return
	 */
	private boolean enoughMoneyToPay(double beveragePrice) {
		return beveragePrice <= moneyInserted();
	}

	/**
	 * 
	 * @param coinsInserted
	 * @param beveragePrice
	 */
	private void payBeverage(List<Double> coinsInserted, double amountToPay) {
		while (amountToPay > 0) {
			Double coin = coinsInserted.remove(0);
			amountToPay -= coin;
		}
	}

	/**
	 * 
	 * @return
	 */
	private double moneyInserted() {
		double sum = 0;
		for (Iterator<Double> iter = coinsInserted.iterator(); iter.hasNext();) {
			Double coin = iter.next();
			sum += coin;
		}
		return sum;
	}
}