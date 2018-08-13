package com.winmax.suites;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.openqa.selenium.WebDriver;

import com.winmax.config.Const;
import com.winmax.driver.DriverFactory;
import com.winmax.steps.BaseStep;
import com.winmax.steps.WebDriverSteps;
import com.winmax.utils.cucumber.ExtendedRuntimeOptions;

import cucumber.runtime.Runtime;
import cucumber.runtime.io.MultiLoader;
import cucumber.runtime.io.ResourceLoaderClassFinder;
import net.masterthought.cucumber.Configuration;
import net.masterthought.cucumber.ReportBuilder;

public class BaseSuiteTest {
	private static final Logger logger = LogManager.getLogger(BaseSuiteTest.class);
	 
	private static WebDriver driver;
    private String glue = Const.GLUE;
	
	@BeforeClass
	public static void setup() {		
		try {
			driver = DriverFactory.startDriver();
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (driver==null) {
			new RuntimeException("Failed to start driver.");
		}
		WebDriverSteps.setDriver(driver);
		BaseStep.setDriver(driver);
	}

	@AfterClass
	public static void tearDown() {
		try {
			driver.quit();
		} catch (Exception e) {
		}
	}	
	
	//----------------------- For rerun failed test Cases -------------------------------------------------
	protected static String getReportSourceFolder(String outputPath) {
		return outputPath + "source/source";
	}
	
    public void reRunTest(String outputPath, int count) {
    	String sourcePath = getReportSourceFolder(outputPath);
    	logger.info("Re run times is " + count);
    	for (int i=1; i<=count; i++) {
    		nReRun(sourcePath, i);
    	}        
        GenerateAllRunReports(outputPath);
    }
    
    protected void defaultRun(String outputPath, String[] tags, String featureSource) {
    	String sourcePath = getReportSourceFolder(outputPath);
        List<String> arguments = new ArrayList<String>();
        if (featureSource==null) {
        	arguments.add(".");
        } else {
        	arguments.add(featureSource);
        }
        if(tags.length>0) {
	        for (String tag : tags) {
	            arguments.add("--tags");
	            arguments.add(tag);
	        }
        }
        arguments.add("--plugin");
        arguments.add("html:" + sourcePath + ".html");
        arguments.add("--plugin");
        arguments.add("json:" + sourcePath + ".json");
        arguments.add("--plugin");
        arguments.add("rerun:" + sourcePath + ".txt");
        String[] gluepackages = glue.split(",");
        for (String packages : gluepackages) {
            if (!packages.contains("none")) {
                arguments.add("--glue");
                arguments.add(packages);
            }
        }
        final String[] argv = arguments.toArray(new String[0]);
        try {
            executetests(argv);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void nReRun(String sourcePath, int n) {
    	String file = null;
    	String fileJson = null;
    	String fileTxt = null;
    	if (n==1) {
    		file = sourcePath + ".txt";
    		fileJson = sourcePath + "/cucumber1.json";
    		fileTxt = sourcePath + "/rerun1.txt";
    	} else {
    		file = sourcePath + "/rerun" + (n-1) + ".txt";
    		fileJson = sourcePath + "/cucumber" + (n-1) + ".json";
    		fileTxt = sourcePath + "/rerun" + (n-1) + ".txt";
    	}    	
        try {
            if (new File(file).exists() && new BufferedReader(new FileReader(file)).readLine() != null) {
            	//ExecuteReRerun first arg: refactored input file ; second arg:- output json file path for getting result
                ExecuteReRerun("@" + file, fileJson, fileTxt, sourcePath);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void ExecuteReRerun(String rerunFile, String targetJson, String targetRerun, String sourcePath) {
        List<String> arguments = new ArrayList<String>();
        arguments.add(rerunFile);
        arguments.add("--plugin");
        arguments.add("pretty:" + sourcePath + "/cucumber-pretty.txt");
        arguments.add("--plugin");
        arguments.add("json:" + targetJson);
        arguments.add("--plugin");
        arguments.add("rerun:" + targetRerun);
        String[] gluepackages = glue.split(",");
        for (String packages : gluepackages) {
            if (!packages.contains("none")) {
                arguments.add("--glue");
                arguments.add(packages);
            }
        }
        final String[] argv = arguments.toArray(new String[0]);
        try {
            executetests(argv);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public byte executetests(final String[] argv) throws InterruptedException, IOException {
        ExtendedRuntimeOptions runtimeOptions = new ExtendedRuntimeOptions(new ArrayList(Arrays.asList(argv)));
        MultiLoader resourceLoader = new MultiLoader(this.getClass().getClassLoader());
        ResourceLoaderClassFinder classFinder = new ResourceLoaderClassFinder(resourceLoader, this.getClass().getClassLoader());
        Runtime runtime = new Runtime(resourceLoader, classFinder, this.getClass().getClassLoader(), runtimeOptions);
        runtime.run();
        //logger.info(runtime.exitStatus());
        return runtime.exitStatus();
    }

    public static void GenerateAllRunReports(String outputPath) {
        try {
            List<File> jsons = finder(outputPath);
            List<File> defaultRunJSONs = new ArrayList<File>();
            List<File> firstRunJSONs = new ArrayList<File>();
            List<File> secondRunJSONs = new ArrayList<File>();
            List<File> thirdRunJSONs = new ArrayList<File>();
            List<File> fourthRunJSONs = new ArrayList<File>();
            List<File> fifthRunJSONs = new ArrayList<File>();
            for (File f : jsons) {
                if (f.getName().contains("cucumber1") && f.getName().endsWith(".json")) {
                    firstRunJSONs.add(f);
                } else if (f.getName().contains("cucumber2") && f.getName().endsWith(".json")) {
                    secondRunJSONs.add(f);
                } else if (f.getName().contains("cucumber3") && f.getName().endsWith(".json")) {
                    thirdRunJSONs.add(f);
                } else if (f.getName().contains("cucumber4") && f.getName().endsWith(".json")) {
                    fourthRunJSONs.add(f);
                } else if (f.getName().contains("cucumber5") && f.getName().endsWith(".json")) {
                    fifthRunJSONs.add(f);
                } else {
                    defaultRunJSONs.add(f);
                }
            }
            if (defaultRunJSONs.size() != 0) {
                generateRunWiseReport(defaultRunJSONs, "Default_Run", outputPath);
            }
            if (firstRunJSONs.size() != 0) {
                generateRunWiseReport(firstRunJSONs, "First_Re-Run", outputPath);
            }
            if (secondRunJSONs.size() != 0) {
                generateRunWiseReport(secondRunJSONs, "Second_Re-Run", outputPath);
            }
            if (thirdRunJSONs.size() != 0) {
                generateRunWiseReport(thirdRunJSONs, "Third_Re-Run", outputPath);
            }
            if (fourthRunJSONs.size() != 0) {
                generateRunWiseReport(fourthRunJSONs, "Fourth_Re-Run", outputPath);
            }
            if (fifthRunJSONs.size() != 0) {
                generateRunWiseReport(fifthRunJSONs, "Fifth_Re-Run", outputPath);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static List<File> finder(String dirName) {
        return (List<File>) FileUtils.listFiles(new File(dirName), new String[] {"json"}, true);
    }

    public static void generateRunWiseReport(List<File> jsons, String run, String outputPath) {
        try {
            File rd = new File(outputPath + "Consolidated-Report/" + run);
            List<String> jsonReports = new ArrayList<String>();
            for (File json : jsons) {
                jsonReports.add(json.getAbsolutePath());
            }
            Configuration config = new Configuration(rd, "Regression Report");
            //List<String> jsonReports, File reportDirectory, String pluginUrlPath, String buildNumber, String buildProject, boolean skippedFails, boolean undefinedFails, boolean flashCharts, boolean runWithJenkins, boolean artifactsEnabled, String artifactConfig, boolean highCharts
           
            ReportBuilder reportBuilder = new ReportBuilder(jsonReports, config);
            		//new ReportBuilder(jsonReports, rd, "", run, "cucumber-reporting", true, true, true, false, false, "", false);
            reportBuilder.generateReports();
            //logger.info(run + " consolidated reports are generated under directory " + outputPath + "Consolidated-Report");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
