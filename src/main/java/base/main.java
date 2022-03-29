package base;

import java.util.ArrayList;
import java.util.List;

import org.testng.TestNG;

public class main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		TestNG runner = new TestNG();

		List<String> suitefiles = new ArrayList<String>();

		suitefiles.add(System. getProperty("user.dir")+"//testng.xml");

		runner.setTestSuites(suitefiles);

		runner.run();

	}

}
