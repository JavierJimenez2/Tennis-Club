# Tennis Club Reservation System

## Overview

The Tennis Club Reservation System is a Java-based application that allows members to register, authenticate, reserve tennis courts, view their reservations, and update their profile information. The system is designed to be user-friendly, ensuring a smooth experience for members who want to book courts at the GreenBall Tennis Club.

This application uses a database to persist user and reservation data. The system offers functionalities such as viewing court availability, managing bookings, and canceling reservations with a 24-hour notice.

---

## Features

- **User Registration**: New users can register by providing their personal details, including name, nickname, password, contact number, and an optional profile image.
- **Authentication**: Users must authenticate to access the reservation system. They can recover from errors such as incorrect passwords.
- **Court Reservation**: Members can reserve a tennis court for up to two consecutive hours. The system handles payment and updates the reservation status accordingly.
- **View Reservations**: Members can view their past and upcoming reservations, including reservation details such as court name, time, and payment status.
- **Cancel Reservations**: Members can cancel reservations as long as they provide at least 24 hours' notice.
- **Court Availability**: Members can check court availability for a specific day without logging in.
- **Profile Update**: Members can update their personal information, except for their nickname, which cannot be changed once set.

---

## Technical Setup

### Libraries and Dependencies

This project uses the following libraries:

- **tenisClub.jar**: Contains the core classes for managing members, courts, and bookings.
- **sqlite-jdbc-3.41.2.1.jar**: Used to interact with the SQLite database.

### Installation

1. **Clone the Repository**:  
   Clone this repository to your local machine.
   
   ```bash
   git clone https://github.com/your-username/greenball-tennis-reservation.git
   ```

2. **Set Up Project in NetBeans**:  
   - Open NetBeans IDE and create a new Java project.
   - Copy the `tenisClub.jar` and `sqlite-jdbc-3.41.2.1.jar` files into the `lib` folder of the project.
   - Right-click the "Libraries" section of your project in NetBeans and select **Add JAR/Folder** to add both JAR files.

3. **Database Setup**:  
   The application uses SQLite for data storage. Upon initialization, the database is created automatically if it doesn't exist.

4. **Running the Application**:  
   Once everything is set up, run the application from within NetBeans.

---

## Classes and Methods

### 1. **Club**
The `Club` class is a Singleton that manages the entire club's information, including members, courts, and bookings.

- **Methods**:
  - `getInstance()`: Returns the single instance of the `Club` class.
  - `getClubName()`: Returns the name of the club.
  - `getMembers()`: Returns a list of all club members.
  - `getCourts()`: Returns a list of all tennis courts in the club.

### 2. **Court**
The `Court` class represents a tennis court in the club.

- **Attributes**:
  - `name`: Name of the court (e.g., "Court 1", "Court 2").

### 3. **Member**
The `Member` class stores information about a club member.

- **Attributes**:
  - `name`: Member's first name.
  - `surname`: Member's last name.
  - `telephone`: Member's contact number.
  - `nickname`: Unique credential for logging in.
  - `password`: Member's password for authentication.
  - `creditCard`: Credit card information (optional).
  - `image`: Member's profile image (optional).

- **Methods**:
  - `registerMember()`: Registers a new member.
  - `updateProfile()`: Updates member details (except for the nickname).

### 4. **Booking**
The `Booking` class stores information about a reservation made by a member.

- **Attributes**:
  - `bookingDate`: Date and time the reservation was made.
  - `madeForDay`: The date the court is reserved for.
  - `fromTime`: Start time of the reservation.
  - `paid`: Boolean indicating whether the reservation has been paid.
  - `court`: The court reserved for the booking.
  - `member`: The member who made the reservation.

- **Methods**:
  - `setPaid()`: Sets the payment status for a reservation.

---

## Usage

1. **Register a New Member**:  
   Navigate to the registration section, fill out the form with your personal details, and submit the registration.

2. **Authenticate**:  
   Log in with your nickname and password.

3. **Reserve a Court**:  
   After authentication, view available courts and reserve your preferred slot.

4. **View Reservations**:  
   Check your past and upcoming reservations, including reservation details and payment status.

5. **Cancel a Reservation**:  
   If you need to cancel a reservation, you must do so at least 24 hours before the scheduled time.

6. **Check Court Availability**:  
   Without logging in, you can check the availability of courts for a specific day.

7. **Update Profile**:  
   Members can update their profile information through the settings page.

---

## Notes

- **Nickname**: Once registered, the nickname cannot be changed.
- **Reservation Limitations**: Members can only reserve a court for a maximum of two consecutive hours.
- **Cancellation Policy**: Reservations must be canceled at least 24 hours in advance to avoid penalties.

---

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.
