# FindMyDesk UBC – 2025W CPEN 221 Final Project

**Authors / Contributors:**  
- Anson Chan  
- Arshee Charania  
- Dana Ebadi  
- Becky Huynh  
- Mahika Mahika  

**Description:**  
FindMyDesk UBC is a full-stack web application developed as the CPEN 221 final project for the 2025W session. The application recommends study spots across UBC's Vancouver campus based on student preferences. The frontend is built with HTML/CSS/JavaScript, while the backend uses Java with Spring Boot. The backend includes a matching algorithm that selects optimal study spots, validated through comprehensive JUnit5 tests.

---

## Getting Started

### Prerequisites
- JDK 17 or JDK 21
- VSCode with Live Server Extension
- Spring Boot
- Gradle

### Installation & Setup

1. **Clone the repository**
```bash
   git clone https://github.com/ansonnchan/FindMyDeskUBC.git
   cd project-sequoia
```

2. **Switch to the RELEASE branch**
```bash
   git checkout RELEASE
```

3. **Install VSCode Extensions**
   - Open VSCode
   - Install the **Live Server Extension** from the Extensions panel (left sidebar)

4. **Start the Backend Server**
```bash
   cd BACKEND
   ./gradlew.bat bootRun
```
   *Note: The progress bar may stop at 80% – this is normal and indicates the server is running.*

5. **Launch the Frontend**
   - Navigate to `frontend/htmlCode/index.html` in VSCode
   - Right-click anywhere in the file
   - Select **Open with Live Server**
   - The application will open in your default browser

*Note: The application runs on a local development server.*

---

## Running Tests and Code Coverage

Tests are implemented using JUnit5 and located in `BACKEND/src/test/`.

### Running Tests in IntelliJ (Recommended for Coverage):
1. Open the project in IntelliJ IDEA
2. Navigate to test files (e.g., `MatchingScoreTests.java`)
3. Click the green run button, or
4. Click the three dots next to the debug icon → **Run with Coverage**

### Test Scope:
- Tests focus on backend logic, particularly the matching score algorithm
- Frontend/UI testing should be performed manually by interacting with the running application
- Code coverage metrics reflect backend-only testing

*Note: Line and branch coverage may appear limited as tests currently focus on core matching functionality.*

---

## Project Structure
```
project-sequoia/
├── BACKEND/
│   └── src/
│       ├── main/java/          # Backend source code
│       └── test/               # JUnit5 tests
└── frontend/
    └── htmlCode/               # Frontend HTML/CSS/JS
        └── index.html          # Main entry point
```

**Quick Links:**
- [Backend Code](./BACKEND/src/main/java)
- [Frontend Code](./frontend/htmlCode)

---

## Technologies Used
- **Frontend**: HTML, CSS, JavaScript
- **Backend**: Java, Spring Boot, Gradle
- **Testing**: JUnit5
- **Development**: VSCode, IntelliJ IDEA

---

License and Copyright
This project is developed as part of the CPEN 221 course at the University of British Columbia. All code and materials are the intellectual property of UBC, the course instructors, and the listed authors unless otherwise stated.

You may view, study, and modify the code for educational purposes within the course context only. Redistribution or commercial use is not permitted without explicit permission.
