package com.jammus.pvttest.interactors;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.jammus.pvt.interactors.Result;

public class ResultTest {

	private static final int ERROR_TYPE_ONE = -1;
	private static final int ERROR_TYPE_TWO = -2;
	private static final int ERROR_TYPE_ELEVEN = -11;

	@Test
	public void testIsOkByDefault() {
		Result result = new Result();
		assertTrue(result.isOk());
	}
	
	@Test
	public void testIsNotOkWhenErrorSuppled() {
		Result result = new Result(ERROR_TYPE_ONE);
		assertFalse(result.isOk());
	}
	
	@Test
	public void testDoesNotHaveSuppliedErrorByDefault() {
		Result result = new Result();
		assertFalse(result.hasError(ERROR_TYPE_ONE));
	}
	
	@Test
	public void testDoesHaveSuppliedErrorWhenSpecifiedAtContruction() {
		Result result = new Result(ERROR_TYPE_ONE);
		assertTrue(result.hasError(ERROR_TYPE_ONE));
	}
	
	@Test
	public void testDoesNotHaveSuppliedErrorWhenOtherSuppliedAtConstruction() {
		Result result = new Result(ERROR_TYPE_ONE);
		assertFalse(result.hasError(ERROR_TYPE_TWO));
	}

	@Test
	public void testCanHaveMultipleErrors() {
		List<Integer> errors = new ArrayList<Integer>();
		errors.add(ERROR_TYPE_ONE);
		errors.add(ERROR_TYPE_TWO);
		Result result = new Result(errors);
		assertTrue(result.hasError(ERROR_TYPE_ONE));
		assertTrue(result.hasError(ERROR_TYPE_TWO));
		assertFalse(result.hasError(ERROR_TYPE_ELEVEN));
	}
	
}
