package threadedServer;

public class Form {
	// Attributes
	private String firstName;
	private String lastName;
	private String email;

	private int age;

	private int birthDay;
	private int birthMonth;
	private int birthYear;

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
}
