package jUnitTests;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import core.Problem;
import core.Variable;

public class JUnitProblem {

	/**
	 * Class specified to test Problem class using JUnit
	 * @author afgos-iscteiulpt
	 */

	@Test
	public void testGetProblem_name() {
		Problem problem = new Problem();
		assertEquals("", problem.getProblem_name());
		problem.setProblem_name("test");
		assertEquals("test", problem.getProblem_name());
	}

	@Test
	public void testSetProblem_name() {
		Problem problem = new Problem();
		problem.setProblem_name("test");
		assertEquals("test", problem.getProblem_name());
	}

	@Test
	public void testGetProblem_description() {
		Problem problem = new Problem();
		assertEquals("", problem.getProblem_description());
		problem.setProblem_description("test");
		assertEquals("test", problem.getProblem_description());
	}

	@Test
	public void testSetProblem_description() {
		Problem problem = new Problem();
		problem.setProblem_description("test");
		assertEquals("test", problem.getProblem_description());
	}

	@Test
	public void testGetAlgorithm() {
		Problem problem = new Problem();
		assertEquals("", problem.getAlgorithm());
		problem.setAlgorithm("test");
		assertEquals("test", problem.getAlgorithm());
	}

	@Test
	public void testSetAlgorithm() {
		Problem problem = new Problem();
		problem.setAlgorithm("test");
		assertEquals("test", problem.getAlgorithm());
	}

	@Test
	public void testGetUser_name() {
		Problem problem = new Problem();
		assertEquals("", problem.getUser_name());
		problem.setUser_name("test");
		assertEquals("test", problem.getUser_name());
	}

	@Test
	public void testSetUser_name() {
		Problem problem = new Problem();
		problem.setUser_name("test");
		assertEquals("test", problem.getUser_name());
	}

	@Test
	public void testGetUser_email() {
		Problem problem = new Problem();
		assertEquals("", problem.getUser_email());
		problem.setUser_email("test");
		assertEquals("test", problem.getUser_email());
	}

	@Test
	public void testSetUser_email() {
		Problem problem = new Problem();
		problem.setUser_email("test");
		assertEquals("test", problem.getUser_email());
	}

	@Test
	public void testGetVariables() {
		Problem problem = new Problem();
		assertEquals(new ArrayList<>(), problem.getVariables());
		ArrayList<Variable> temp = new ArrayList<Variable>();
		temp.add(new Variable());
		problem.setVariables(temp);
		assertEquals(temp, problem.getVariables());
	}

	@Test
	public void testSetVariables() {
		Problem problem = new Problem();
		ArrayList<Variable> temp = new ArrayList<Variable>();
		temp.add(new Variable());
		problem.setVariables(temp);
		assertEquals(temp, problem.getVariables());
	}

}
