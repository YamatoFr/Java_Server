package threadedServer;

import java.io.Serializable;

public class Form implements Serializable {
	// Attributes
	private static final long serialVersionUID = 1L;

	public String firstName;
	public String lastName;
	public String email;

	public int age;

	public int birthDay;
	public int birthMonth;
	public int birthYear;

	// Constructor
	public Form(String firstName, String lastName, String email, int age, int birthDay, int birthMonth, int birthYear) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;

		this.age = age;

		this.birthDay = birthDay;
		this.birthMonth = birthMonth;
		this.birthYear = birthYear;
	}

	public Form() {
		this.firstName = "";
		this.lastName = "";
		this.email = "";

		this.age = 0;

		this.birthDay = 0;
		this.birthMonth = 0;
		this.birthYear = 0;
	}

	// Methods
	public String toString() {
		return "First name: " + this.firstName + "\nLast name: " + this.lastName + "\nEmail: " + this.email + "\nAge: "
				+ this.age + "\nBirth date: " + this.birthDay + "/" + this.birthMonth + "/" + this.birthYear;
	}
}
