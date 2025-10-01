# 📱 Mid-Term Result Management System

A professional Android application for managing mid-term examination results with role-based access control.

## 🎯 Features

### 👨‍🏫 Professor Features
- **Subject Management**: View assigned subjects (max 2)
- **Marks Entry**: Input/update student marks (0-100 scale)
- **Student Overview**: Manage enrolled students (max 5 per subject)
- **Real-time Updates**: Instant marks saving and validation

### 👨‍🎓 Student Features
- **Result Viewing**: Check marks for enrolled subjects
- **Status Tracking**: Clear indication of published vs pending results
- **User-friendly Interface**: Clean and intuitive design

## 🛠️ Technical Stack

- **Platform**: Android (Java)
- **Database**: SQLite with persistent storage
- **Architecture**: MVC pattern with clean separation
- **UI/UX**: Material Design with dark blue theme
- **Storage**: Local SQLite database with CRUD operations

## 🎨 Design Features

- **Professional UI**: Dark blue corporate theme with white text
- **Capsule Headers**: Modern pill-shaped headers with gradient backgrounds
- **Accessibility**: High contrast dark blue/white color scheme
- **Consistent Branding**: Unified dark blue theme throughout
- **Modern Layout**: Clean, intuitive interface with proper spacing

## 🚀 Installation

1. **Clone Repository**
   ```bash
   git clone https://github.com/solankitushar2404/Mid-Term-Result-App.git
   ```

2. **Open in Android Studio**
   - Import project
   - Sync Gradle files
   - Build and run

3. **Demo Credentials**
   ```
   Professor: prof.vyas / pass123
   Students: 220210107067 / 107067 (last 6 digits format)
   Additional: 220210107003/107003, 220210107074/107074, etc.
   ```

## 📊 Database Schema

### Tables
- **users**: User authentication and profiles
- **subjects**: Course information and professor assignments
- **enrollments**: Student-subject relationships
- **results**: Marks storage with student-subject mapping

### Key Features
- **Referential Integrity**: Proper foreign key relationships
- **Data Validation**: Input validation and error handling
- **Persistent Storage**: Cross-session data retention

## 🎓 Academic Compliance

This project meets all requirements for:
- **Mobile Application Development (MAD)**
- **Role-based Authentication System**
- **Database Integration and Management**
- **Professional UI/UX Design**
- **Academic Result Management**

## 📱 Key Features Implemented

- ✅ **Role-based Authentication** (Professor/Student login)
- ✅ **Marks Management** (0-100 scale with real-time updates)
- ✅ **SQLite Database** (Persistent storage with CRUD operations)
- ✅ **Professional UI** (Dark blue theme with capsule headers)
- ✅ **Input Validation** (Secure data handling and error prevention)
- ✅ **Academic Compliance** (Meets all MAD course requirements)

## 🔧 Development Setup

### Prerequisites
- Android Studio Arctic Fox or later
- Android SDK API 21+
- Java 8 or higher

### Build Instructions
1. Open project in Android Studio
2. Sync Gradle files
3. Run on emulator or physical device
4. Use demo credentials for testing

## 📝 Project Structure

```
app/
├── src/main/
│   ├── java/com/example/mad_project/
│   │   ├── database/          # Database helper and models
│   │   ├── models/            # Data models (User, Subject, Result)
│   │   ├── LoginActivity.java
│   │   ├── ProfessorDashboardActivity.java
│   │   ├── StudentDashboardActivity.java
│   │   └── MarksEntryActivity.java
│   ├── res/
│   │   ├── layout/            # UI layouts
│   │   ├── drawable/          # Icons and backgrounds
│   │   └── values/            # Colors, strings, themes
│   └── AndroidManifest.xml
└── build.gradle
```

## 🔒 Security Features

- **No Hardcoded Credentials**: Uses demo accounts for GitHub
- **Input Validation**: Prevents SQL injection and invalid data
- **Role-based Access**: Proper authentication and authorization
- **Data Sanitization**: Clean input handling throughout

## 🤝 Contributing

1. Fork the repository
2. Create feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit changes (`git commit -m 'Add AmazingFeature'`)
4. Push to branch (`git push origin feature/AmazingFeature`)
5. Open Pull Request

## 📄 License

This project is created for academic purposes as part of Mobile Application Development coursework.

## 👨‍💻 Developer

**SOLANKI TUSHARKUMAR**  
Mobile Application Development (MAD)  
Computer Engineering Department  

### 🎆 Project Status
- ✅ **Complete**: All requirements implemented
- ✅ **Tested**: Fully functional with demo data
- ✅ **Documented**: Professional README and security policy
- ✅ **GitHub Ready**: Clean code with no sensitive data
- ✅ **Academic Submission**: Ready for evaluation

---

⭐ **Star this repository if you found it helpful!**
