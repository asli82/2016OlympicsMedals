package com.olympics;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class OlympicsMedals{

	WebDriver driver;
	@BeforeMethod 
	public void setup() {
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		
	}

	
	

	@Test 
	public void testCase1() {
		//1. Go to website https://en.wikipedia.org/wiki/2016_Summer_Olympics#Medal_table.o do that you need to capture all the cells in the Rank column and check
		//if they are in ascending order (highlighted in the picture). Use TestNG assertions.
		driver.get("https://en.wikipedia.org/wiki/2016_Summer_Olympics#Medal_table");
		//2. Verify that by default the Medal table is sorted by rank.
		List<WebElement> rows =new ArrayList<>();
		List<String> expRows = Arrays.asList("1", "2", "3","4","5","6","7","8","9","10");
		List<String> actRows = new ArrayList<>();
		for(int i = 1; i<11 ; i++ ) {
			rows.addAll(driver.findElements(By.xpath("//table[@class='wikitable sortable plainrowheaders jquery-tablesorter']/tbody/tr["+i+"]/td[1]")));
			
			}
		
		for(WebElement each : rows) {
			actRows.add(each.getText());
			
		}

		Assert.assertEquals(actRows, expRows);
		//3. Click link NOC.
		driver.findElement(By.xpath("//table[@class='wikitable sortable plainrowheaders jquery-tablesorter']/thead/tr/th[2]")).click();
		//4. Now verify that the table is now sorted by the country names. 
		List<WebElement> countries =new ArrayList<>();
		List<String> expCountries = Arrays.asList(" Australia (AUS)", " China (CHN)"," France (FRA)",
				" Germany (GER)"," Great Britain (GBR)"," Italy (ITA)"," Japan (JPN)"," Russia (RUS)"," South Korea (KOR)"," United States (USA)");
		List<String> actCountries = new ArrayList<>();
		for(int i = 1; i<11 ; i++ ) {
			countries.addAll(driver.findElements(By.xpath("//table[@class='wikitable sortable plainrowheaders jquery-tablesorter']/tbody/tr["+i+"]/th[1]")));
			}
		for(WebElement eachCountry : countries) {
			actCountries.add(eachCountry.getText());
			
		}
		Assert.assertEquals(actCountries, expCountries);
		//5. Verify that Rank column is not in ascending order anymore. Use TestNG assertions.
		List<WebElement> newRows =new ArrayList<>();
		List<String> expNewRows = Arrays.asList("1", "2", "3","4","5","6","7","8","9","10");
		List<String> actNewRows = new ArrayList<>();
		for(int i = 1; i<11 ; i++ ) {
			rows.addAll(driver.findElements(By.xpath("//table[@class='wikitable sortable plainrowheaders jquery-tablesorter']/tbody/tr["+i+"]/td[1]")));
			
			}
		
		for(WebElement eachNewRow : rows) {
			actRows.add(eachNewRow.getText());
			
		}

		Assert.assertNotEquals(actNewRows, expNewRows);
	
	
	}
	
	//Test Case 2: THE MOST
	//1. Go to website https://en.wikipedia.org/wiki/2016_Summer_Olympics.
	//2. Write a method that returns the name of the country with the most number of gold medals.
	//3. Write a method that returns the name of the country with the most number of silver medals.
	//4. Write a method that returns the name of the country with the most number of bronze medals.
	//5. Write a method that returns the name of the country with the most number of medals.
	
	//method to get country name and number of selected medal type
	public String theMost(String typeMedal){
		int a = 0;
		switch(typeMedal) {
		case "gold":
			a = 2;
			break;
		case "silver":	
			a = 3;
			break;
		case "bronze":
			a = 4;
			break;
		case "total":
			a = 5;
			break;	
			
		}
		int noMedals = driver.findElements(By.xpath("//table[@class='wikitable sortable plainrowheaders jquery-tablesorter']/tbody/tr")).size();
		
		List<Integer> numArr = new ArrayList<>();
		
		for(int i = 1; i < noMedals; i++) {
			numArr.add(Integer.parseInt(driver.findElement(By.xpath("//table[@class='wikitable sortable plainrowheaders jquery-tablesorter']/tbody/tr["+i+"]/td["+a+"]")).getText()));
		}
		
		List<String> nameArr = new ArrayList<>();
		
		for(int i = 1; i < noMedals; i++) {
			nameArr.add(driver.findElement(By.xpath("//table[@class='wikitable sortable plainrowheaders jquery-tablesorter']/tbody/tr["+i+"]/th//a")).getText());
		}

		Map<String, Integer> countryAndMedals = new HashMap<>();
		
		for(int i = 0; i < noMedals - 1; i++) {
			countryAndMedals.put(nameArr.get(i), numArr.get(i));
		}

		Set<Entry<String, Integer>> maxMedal = countryAndMedals.entrySet();
		
		int maxNoMedal = 0;
		String maxCountry = "";
		
		for(Entry<String, Integer> each : maxMedal) {
			if(each.getValue() > maxNoMedal) {
				maxNoMedal = each.getValue();
				maxCountry = each.getKey();
			}
		}
		return maxCountry + " " + maxNoMedal;
	}
	
	//6. Write TestNG test for your methods.
	@Test 
	public void testCase2() {
		driver.get("https://en.wikipedia.org/wiki/2016_Summer_Olympics");
		//driver.findElement(By.xpath("//table[@class='wikitable sortable plainrowheaders jquery-tablesorter']/thead/tr[1]/th[3]")).click();
		//driver.findElement(By.xpath("//table[@class='wikitable sortable plainrowheaders jquery-tablesorter']/thead/tr[1]/th[3]")).click();

		String countryGoldMadal = driver.findElement(By.xpath("//table[@class='wikitable sortable plainrowheaders jquery-tablesorter']/tbody/tr[1]/th//a")).getText();
		String numberGoldMadal = driver.findElement(By.xpath("//table[@class='wikitable sortable plainrowheaders jquery-tablesorter']/tbody/tr[1]/td[2]")).getText();
		String resultGold = countryGoldMadal + " " + numberGoldMadal;
		Assert.assertTrue(theMost("gold").equals(resultGold));
		
		driver.findElement(By.xpath("//table[@class='wikitable sortable plainrowheaders jquery-tablesorter']/thead/tr[1]/th[4]")).click();
		driver.findElement(By.xpath("//table[@class='wikitable sortable plainrowheaders jquery-tablesorter']/thead/tr[1]/th[4]")).click();
		String countrySilverMadal = driver.findElement(By.xpath("//table[@class='wikitable sortable plainrowheaders jquery-tablesorter']/tbody/tr[1]/th//a")).getText();
		String numberSilverMadal = driver.findElement(By.xpath("//table[@class='wikitable sortable plainrowheaders jquery-tablesorter']/tbody/tr[1]/td[3]")).getText();
		String resultSilver = countrySilverMadal + " " + numberSilverMadal;

		Assert.assertTrue(theMost("silver").equals(resultSilver));
		
		driver.findElement(By.xpath("//table[@class='wikitable sortable plainrowheaders jquery-tablesorter']/thead/tr[1]/th[5]")).click();
		driver.findElement(By.xpath("//table[@class='wikitable sortable plainrowheaders jquery-tablesorter']/thead/tr[1]/th[5]")).click();
		String countryBronzeMadal = driver.findElement(By.xpath("//table[@class='wikitable sortable plainrowheaders jquery-tablesorter']/tbody/tr[1]/th//a")).getText();
		String numberBronzeMadal = driver.findElement(By.xpath("//table[@class='wikitable sortable plainrowheaders jquery-tablesorter']/tbody/tr[1]/td[4]")).getText();
		String resultBronze = countryBronzeMadal + " " + numberBronzeMadal;

		Assert.assertTrue(theMost("bronze").equals(resultBronze));
		
		driver.findElement(By.xpath("//table[@class='wikitable sortable plainrowheaders jquery-tablesorter']/thead/tr[1]/th[6]")).click();
		driver.findElement(By.xpath("//table[@class='wikitable sortable plainrowheaders jquery-tablesorter']/thead/tr[1]/th[6]")).click();
		String countryTotalMadal = driver.findElement(By.xpath("//table[@class='wikitable sortable plainrowheaders jquery-tablesorter']/tbody/tr[1]/th//a")).getText();
		String numberTotalMadal = driver.findElement(By.xpath("//table[@class='wikitable sortable plainrowheaders jquery-tablesorter']/tbody/tr[1]/td[5]")).getText();
		String resultTotal = countryTotalMadal + " " + numberTotalMadal;
		
		Assert.assertTrue(theMost("total").equals(resultTotal));
		
	}
	//Test Case 3: COUNTRY BY MEDAL
	//1. Go to website https://en.wikipedia.org/wiki/2016_Summer_Olympics.
	//2. Write a method that returns a list of countries whose silver medal count is equal to 18.
	
	
	public Set<String> countryByMedal(){
		driver.get("https://en.wikipedia.org/wiki/2016_Summer_Olympics");
		Set<String> countries = new HashSet();
		
		int rows = driver.findElements(By.xpath("//table[@class='wikitable sortable plainrowheaders jquery-tablesorter']/tbody/tr")).size();
		for(int i = 1; i < rows; i++) {
			int num = Integer.parseInt(driver.findElement(By.xpath("//table[@class='wikitable sortable plainrowheaders jquery-tablesorter']/tbody/tr["+i+"]/td[3]")).getText());
			if(num == 18) {
				String country = driver.findElement(By.xpath("//table[@class='wikitable sortable plainrowheaders jquery-tablesorter']/tbody/tr["+i+"]/th//a")).getText();
				countries.add(country);
			}
		}
		return countries;
	}
	//3. Write TestNG test for your method.
	@Test 
	public void testCase3() {
		List<String> countries = Arrays.asList("China", "France");
		Assert.assertEquals(countryByMedal(), countries);
	}

	//Test Case 4: GET INDEX
	//1. Go to website https://en.wikipedia.org/wiki/2016_Summer_Olympics.
	//2. Write a method that takes country name and returns the row and column number. You decide the datatype of the return.
	//3. Write TestNG test for your method (use Japan as test input).
	
	public String getIndex(String expected) {
		driver.get("https://en.wikipedia.org/wiki/2016_Summer_Olympics");
		List<String> countryList = new ArrayList<>();
		String result ="";
		int num = driver.findElements(By.xpath("//table[@class='wikitable sortable plainrowheaders jquery-tablesorter']/tbody/tr")).size();

		for(int i = 1; i < num; i++) {
			if(expected.equalsIgnoreCase(driver.findElement(By.xpath("//table[@class='wikitable sortable plainrowheaders jquery-tablesorter']/tbody/tr["+i+"]/th//a")).getText())) {
			result += i;
			break;
			}
		}
		return expected + " = row(" + result + "), column(2)";
	}
	
	
	@Test 
	public void testCase4() {
		String expected = getIndex("Japan");
		String actual =  "Japan = row(6), column(2)";
		Assert.assertEquals(actual, expected);
	}

	//Test Case 5: GET SUM
	//1. Go to website https://en.wikipedia.org/wiki/2016_Summer_Olympics.
	//2. Write a method that returns a list of two countries whose sum of bronze medals is 18.
	//3. Write TestNG test for your method.
	
	public List<String> getSum() {
		driver.get("https://en.wikipedia.org/wiki/2016_Summer_Olympics");
		List<String> resultList = new ArrayList<>();
		Map<String, Integer> map = new HashMap<>();
		int num = driver.findElements(By.xpath("//table[@class='wikitable sortable plainrowheaders jquery-tablesorter']/tbody/tr")).size();
		for(int i = 1; i < num; i++) {
			String countryName = driver.findElement(By.xpath("//table[@class='wikitable sortable plainrowheaders jquery-tablesorter']/tbody/tr["+i+"]/th//a")).getText();
			Integer medal = Integer.valueOf(driver.findElement(By.xpath("//table[@class='wikitable sortable plainrowheaders jquery-tablesorter']/tbody/tr["+i+"]/td[4]")).getText());
			map.put(countryName, medal);
		}

		Set<Entry<String, Integer>> countryList = map.entrySet();
		
		for(Entry<String, Integer> each1 : countryList) {
			for(Entry<String, Integer> each2 : countryList) {
				if((each1.getValue() + each2.getValue() == 18) && each1.getValue() != each2.getValue() && !resultList.contains(each2.getKey())) {
					resultList.add(each1.getKey());
					resultList.add(each2.getKey());
				}
			}
		}
		return resultList;
	}
	
	
	@Test 
	public void testCase5() {
		List<String> actual = getSum();
		List<String> expected = Arrays.asList("Australia", "Italy");
		boolean bool = true;
		for(int i = 0; i < actual.size(); i++) {
			if(!actual.contains(expected.get(i))) {
				bool = false;
				break;
			}
		}
		System.out.println(bool);
		for(int i = 0; i < expected.size(); i++) {
			if(!expected.contains(actual.get(i))) {
				bool = false;
				break;
			}
		}
		Assert.assertTrue(bool);
	}
	
	@AfterMethod
	public void tearDown() {
		driver.close();
	}
}
