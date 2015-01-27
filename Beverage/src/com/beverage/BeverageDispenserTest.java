package com.beverage;

import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class BeverageDispenserTest {
	
	private BeverageDispenser bd = null;
	
	@Before
	public void init() {
		bd = new BeverageDispenser();
	}
	
	@Test
	public void test_null_input_returns_empty_output() {

		String result = bd.interact(null);
		
		assertThat(result).isEmpty();
	}
	
	@Test
	public void test_empty_input_returns_empty_output() {

		String result = bd.interact("");
		
		assertThat(result).isEmpty();
	}
	
	
	@Test
	public void test_put_coins_returns_empty_output() {
		
		String result = bd.interact("PUT 2");
		
		assertThat(result).isEmpty();
	}
	
	@Test
	public void test_put_multiple_coins_returns_empty_output() {
		
		String result = bd.interact("PUT 2, PUT 1, PUT 0.50");
		
		assertThat(result).isEmpty();
	}
	
	
	@Test
	public void test_coin_return_without_coins_returns_empty_output() {
		
		String result = bd.interact("COIN-RETURN");
		
		assertThat(result).isEmpty();
	}
	
	@Test
	public void test_coin_return_returns_inserted_coins() {
		
		String result = bd.interact("PUT 2, PUT 1, PUT 0.5, COIN-RETURN");
		
		assertThat(result).isEqualTo("2.0, 1.0, 0.5");
	}
	
	
	@Test
	public void test_get_beverage_without_coins_inserted_returns_nothing() {
		
		BeverageDispenser bd = new BeverageDispenser();
		String result = bd.interact("GET COCA");
		
		assertThat(result).isEmpty();
	}
	
	@Test
	public void test_get_beverage_with_insufficient_money_returns_nothing() {
		
		String result = bd.interact("PUT 1.0, GET COCA");
		
		assertThat(result).isEmpty();
	}
	
	@Test
	public void test_get_beverage_with_coins_inserted_returns_the_wanted_beverage() {
		
		String result = bd.interact("PUT 2, PUT 1, GET COCA");
		
		assertThat(result).isEqualTo("COCA");
	}
	
	@Test
	public void test_get_beverage_with_too_many_coins_inserted_returns_the_wanted_beverage() {
		
		String result = bd.interact("PUT 2, PUT 1, PUT 2, GET COCA");
		
		assertThat(result).isEqualTo("COCA");
	}
	
	@Test
	public void test_get_beverage_and_coin_return_returns_the_wanted_beverage_and_change() {
		
		String result = bd.interact("PUT 2, PUT 1, PUT 2, GET COCA, COIN-RETURN");
		
		assertThat(result).isEqualTo("COCA, 2.0");
	}
	
	@Test
	public void test_get_muliple_beverages_and_coin_return_returns_the_wanted_beverages_and_change() {
		
		String result = bd.interact("PUT 2, PUT 1, PUT 2, GET COCA, PUT 2, PUT 1, GET ORANGINA, COIN-RETURN");
		
		assertThat(result).isEqualTo("COCA, ORANGINA, 1.0");
	}
	
	@Test
	public void test_malformed_commands_return_empty_string() {
		
		String result = bd.interact(",,PUT PUT GET,,,");
		
		assertThat(result).isEmpty();
	}
	
}
