Project README

📌 Project Overview

This project is designed to execute automated tests using TestNG and supports both local execution and remote execution via BrowserStack for cross-browser testing.

🚀 How to Run the Tests

1️⃣ Clone the Repository

git clone <repository-url>
cd <project-directory>

2️⃣ Configure Execution Mode

Modify the System.properties file to set the execution mode:

Remote Execution (BrowserStack)

Execution=Remote

This will trigger testng.xml.

Tests will run in parallel for cross-browser testing.

Local Execution

Execution=Local

Trigger tests directly using the @Test annotation.

Tests will run on your local machine.

3️⃣ Run the Tests

For Remote Execution (BrowserStack)

mvn test

This runs the tests defined in testng.xml.

For Local Execution

mvn test -Dtest=<TestClassName>

Replace <TestClassName> with the actual test class name.

📌 Post Execution Details

The expected prints can be found in the console output.

Screenshots will be stored in the screenshot folder if a cover image exists for the top 5 displayed articles.

📌 Additional Notes

Ensure Java, Maven, and dependencies are installed before running the tests.

BrowserStack credentials should be configured if executing remotely.

Happy Testing! 🚀