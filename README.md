# Selenium TestNG Automation Framework

A complete automation framework using Java, Selenium WebDriver, TestNG, and Extent Reports for testing web applications.

## Project Structure

```
├── src/
│   ├── main/
│   │   └── java/
│   │       ├── base/
│   │       │   └── BaseTest.java
│   │       ├── pages/
│   │       │   ├── LoginPage.java
│   │       │   ├── ProductPage.java
│   │       │   ├── CartPage.java
│   │       │   └── CheckoutPage.java
│   │       └── utils/
│   │           ├── ConfigReader.java
│   │           └── ExcelUtils.java
│   └── test/
│       ├── java/
│       │   ├── listeners/
│       │   │   └── ExtentReportListener.java
│       │   └── tests/
│       │       └── LoginTest.java
│       └── resources/
│           ├── config.properties
│           └── testdata.xlsx
├── reports/
├── screenshots/
├── pom.xml
├── testng.xml
└── README.md
```

## Prerequisites

- Java 11 or higher
- Maven 3.6 or higher
- Eclipse IDE (recommended)
- Chrome/Firefox browser

## Setup Instructions

### 1. Import Project into Eclipse

1. Extract the project ZIP file
2. Open Eclipse IDE
3. File → Import → Maven → Existing Maven Projects
4. Browse to the extracted project folder
5. Select the project and click Finish

### 2. Maven Dependencies

The project uses the following key dependencies:
- Selenium WebDriver 4.15.0
- TestNG 7.8.0
- WebDriverManager 5.6.2
- Apache POI 5.2.4
- Extent Reports 5.1.1
- Commons IO 2.11.0

### 3. Configuration

Edit `src/test/resources/config.properties` to modify:
- Browser type (chrome/firefox)
- Application URL
- Implicit wait time
- Test data file path

### 4. Test Data

The `src/test/resources/testdata.xlsx` file contains test credentials:
- Valid credentials: hemak8375@gmail.com / Hema@1234
- Invalid credentials: wrong@gmail.com / wrong123

## Running Tests

### Using TestNG XML

1. Right-click on `testng.xml`
2. Run As → TestNG Suite

### Using Maven Command

```bash
mvn clean test
```

### Using Eclipse TestNG Plugin

1. Right-click on `LoginTest.java`
2. Run As → TestNG Test

## Test Scenarios

### 1. Valid Login Flow
- Opens https://automationexercise.com
- Clicks Signup/Login
- Logs in with valid credentials
- Verifies login success
- Navigates to Products page
- Searches for "Dress"
- Adds first product to cart (with hover)
- Clicks View Cart
- Proceeds to Checkout
- Places order

### 2. Invalid Login Flow
- Opens the application
- Navigates to login page
- Enters invalid credentials
- Verifies error message
- Captures screenshot on failure

## Features

- **Page Object Model (POM)**: Clean and maintainable page object structure
- **WebDriverManager**: Automatic driver management
- **Explicit Waits**: No Thread.sleep() - uses WebDriverWait
- **Excel Integration**: Test data from Excel using Apache POI
- **Extent Reports**: Detailed HTML reports with screenshots
- **Configuration**: External configuration via properties file
- **Cross-browser**: Supports Chrome and Firefox
- **Data-driven**: TestNG DataProvider for multiple test data

## Reports

After test execution:
- HTML reports are generated in `reports/` folder
- Screenshots are saved in `screenshots/` folder
- Report format: `ExtentReport_yyyy-MM-dd_HH-mm-ss.html`

## Browser Configuration

To change browser, edit `config.properties`:
```
browser=chrome  # or firefox
```

## Troubleshooting

### Common Issues

1. **Compilation Errors**: Ensure Maven dependencies are properly installed
2. **Driver Issues**: WebDriverManager handles driver setup automatically
3. **Excel File Issues**: Ensure testdata.xlsx exists and has correct format
4. **Screenshot Issues**: Ensure screenshots directory has write permissions

### Best Practices

- Run tests in headless mode for CI/CD pipelines
- Use relative paths for configuration files
- Keep test data separate from test logic
- Review Extent Reports for detailed test results

## Maven Commands

```bash
# Clean and compile
mvn clean compile

# Run tests
mvn test

# Run tests with specific suite
mvn test -DsuiteXmlFile=testng.xml

# Generate reports only
mvn surefire-report:report

# Skip tests during build
mvn clean install -DskipTests
```

## Support

For issues or questions, check:
1. Console output for error messages
2. Extent Reports for test execution details
3. Screenshots for visual verification of failures
