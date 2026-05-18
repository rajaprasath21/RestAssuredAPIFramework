package com.api.listeners;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.api.utilities.ExtentManager;

public class TestListener implements ITestListener  {
	
	private static final Logger logger=LogManager.getLogger(TestListener.class);

	@Override
	public void onStart(ITestContext context) {
		logger.info("Test Suite started..");
		//Initialize the Extent Reports
		ExtentManager.getReporter();
	}

	@Override
	public void onFinish(ITestContext context) {
		logger.info("Test Suite completed..");
		ExtentManager.endTest();
	}
	
	@Override
	public void onTestStart(ITestResult result) {
		String testName=result.getMethod().getMethodName();
		//Start logging in Extent Reports
		ExtentManager.startTest(testName);
		ExtentManager.logStep("Test Started: "+testName);
		logger.info("Test Started!!! -> " + testName);
		logger.info("Description!! -> "+ result.getMethod().getDescription());
	}
	
	@Override
	public void onTestSuccess(ITestResult result) {
		
		String testName=result.getMethod().getMethodName();
		
		ExtentManager.logStepValidationForAPI("Test End: "+testName+ " - ✔️ Test Passed");

		logger.info("Test Passed!!! -> " + testName);
		logger.info("Description!! -> "+ result.getMethod().getDescription());
	}

	@Override
	public void onTestFailure(ITestResult result) {
		
		
		logger.error("Test Failed!!! -> " + result.getMethod().getMethodName());
		logger.error("Exception:  -> " + result.getThrowable());
		
		String testName=result.getMethod().getMethodName();
		String failureMessage=result.getThrowable().getMessage();
		ExtentManager.logStep(failureMessage);
		ExtentManager.logFailureAPI("Test End: "+testName+ " - ❌ Test Failed");

	}

	@Override
	public void onTestSkipped(ITestResult result) {
		String testName=result.getMethod().getMethodName();
		ExtentManager.logSkip("Test Skipped "+testName);
		logger.info("Test Skipped!!! -> " + testName);
	}

}
