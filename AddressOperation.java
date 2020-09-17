package org.bridge.address;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class AddressOperation
{
	public static void main(String[] args) 
	{
		System.out.println("press 1 if you want to add a new address book");
		System.out.println("press 2 if you want to open existing address books and view its contents ");
		System.out.println("press 3 if you want to remove an  address book");
		System.out.println("press 4 if you want to modify data of an address book like add/edit/delete persons info");
		System.out.println("press any other numbers to exit");
		Scanner sc=new Scanner(System.in);
		int choice=sc.nextInt();
		
		switch(choice)
		{
			case 1:	createAddressBook(); break;
			case 2:	addressBookList(); break;
			case 3: removeAddressBook(); break;
			case 4: modifyAddressBookData(); break;
			default: break;
		}
		sc.close();
		
	}
	
	private static void createAddressBook()
	{
		Scanner scanner=new Scanner(System.in);
		System.out.println("please enter table name");
		String table_Name=scanner.nextLine();
		String query="create table address_book."+table_Name+"(first_name varchar(20), last_name varchar(20), address varchar(30), city varchar(20), state varchar(20), zipcode varchar(10),phone varchar(10), PRIMARY KEY(first_name))";
		try 
		{
			Connection connection=DataAccessObject.dataBase();
			Statement statement=connection.createStatement();
			statement.execute(query);
			System.out.println(table_Name+" address book is created successfully");
			connection.close();
			scanner.close();
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
	}
	
	private static void addressBookList()
	{
		String query="show tables in address_book";
		try 
		{
			Scanner scanner=new Scanner(System.in);
			Connection connection=DataAccessObject.dataBase();
			Statement statement=connection.createStatement();
			System.out.println("list of address books are :");
			ResultSet resultSet=statement.executeQuery(query);
			while(resultSet.next())
			{
				System.out.println(resultSet.getString(1));
			}
			System.out.println("please enter the name of address book you want to open");
			String name=scanner.nextLine();
			System.out.println("how do you want to display your address book?");
			System.out.println("press 1 to display normally");
			System.out.println("press 2 to display sort by lastname");
			System.out.println("press 3 to display sory by zipcode");
			int choice=scanner.nextInt();
			String query1="select * from address_book."+name+" order by last_name asc";
			ResultSet resultSet1=statement.executeQuery(query1);
			String query2="select * from address_book."+name+" order by zipcode asc";
			ResultSet resultSet2=statement.executeQuery(query2);
			switch(choice)
			{
			case 1:	DBTablePrinter.printTable(connection, "address_book."+name); break;
			case 2:	DBTablePrinter.printResultSet(resultSet1);
			case 3:	DBTablePrinter.printResultSet(resultSet2);
			}		
			scanner.close();
		} 
		catch (SQLException e)
		{
			e.printStackTrace();
		}
	}
	
	private static void display()
	{
		String query="show tables in address_book";
		try 
		{
			Connection connection=DataAccessObject.dataBase();
			Statement statement=connection.createStatement();
			System.out.println("list of address books are :");
			ResultSet resultSet=statement.executeQuery(query);
			while(resultSet.next())
			{
				System.out.println(resultSet.getString(1));
			}
			connection.close();
		} 
		catch (SQLException e)
		{
			e.printStackTrace();
		}
	}
	
	
	
	
	
	private static void removeAddressBook()
	{
		String query="show tables in address_book";
		try 
		{
			Connection connection=DataAccessObject.dataBase();
			Statement statement=connection.createStatement();
			System.out.println("list of address books are :");
			ResultSet resultSet=statement.executeQuery(query);
			int count=0;
			while(resultSet.next())
			{
				count++;
				System.out.println(count+". "+resultSet.getString(1));
			}
			Scanner scanner=new Scanner(System.in);
			System.out.println("please enter the address book name which you want to delete permenantly");
			String selection=scanner.nextLine();
			query="drop table address_book."+selection;
			statement.execute(query);
			System.out.println("address book successfully deleted");
		
			connection.close();
			scanner.close();
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		
	}
	
	private static void modifyAddressBookData()
	{
		display();
		Scanner scanner=new Scanner(System.in);
		System.out.println("please enter the name of the address book you want to modify");
		
		String name=scanner.nextLine();
		System.out.println("press 1 if you want to add a new person in "+name+" addressbook");
		System.out.println("press 2 if you want to edit a persons info in "+name+" addressbook");
		System.out.println("press 3 if you want to delete a persons info in "+name+" addressbook");
		int choice=scanner.nextInt();
		switch(choice)
		{
		case 1: addNewPerson(name); break;
		case 2: editPersonsInfo(name); break;
		case 3: removePersonInfo(name);
		}
		scanner.close();
	}
	
		

	private static void addNewPerson(String name)
	{
		Scanner scanner=new Scanner(System.in);
		String query="insert into address_book."+name+" values(?,?,?,?,?,?,?)";
		try 
		{
			Connection connection=DataAccessObject.dataBase();
			PreparedStatement statement=connection.prepareStatement(query);
			System.out.println("please enter first name");
			String first_Name=scanner.nextLine();
			System.out.println("please enter last name");
			String last_Name=scanner.nextLine();
			System.out.println("please enter address");
			String address=scanner.nextLine();
			System.out.println("please enter city");
			String city=scanner.nextLine();
			System.out.println("please enter state");
			String state=scanner.nextLine();
			System.out.println("please enter zipcode");
			String zip_code=scanner.nextLine();
			System.out.println("please enter phone number");
			String phone_Number=scanner.nextLine();
			statement.setString(1, first_Name);
			statement.setString(2, last_Name);
			statement.setString(3, address);
			statement.setString(4, city);
			statement.setString(5, state);
			statement.setString(6, zip_code);
			statement.setString(7, phone_Number);
			statement.executeUpdate();
			System.out.println("successfully added address");
			connection.close();
			scanner.close();
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
	private static void editPersonsInfo(String name)
	{
		Scanner scanner=new Scanner(System.in);
		String query="select first_name from address_book."+name;
		
		try 
		{
			Connection connection=DataAccessObject.dataBase();
			PreparedStatement statement=connection.prepareStatement(query);
			ResultSet resultSet=statement.executeQuery();
			while(resultSet.next())
			{
				System.out.println(resultSet.getString(1));
			}
			System.out.println("please enter the name of person you want to edit");
			String first_Name=scanner.nextLine();
			System.out.println("enter last name for "+first_Name);
			String last_Name=scanner.nextLine();
			System.out.println("please enter address for "+first_Name);
			String address=scanner.nextLine();
			System.out.println("please enter city for "+first_Name);
			String city=scanner.nextLine();
			System.out.println("please enter state for "+first_Name);
			String state=scanner.nextLine();
			System.out.println("please enter zipcode for "+first_Name);
			String zip_code=scanner.nextLine();
			System.out.println("please enter phone number for "+first_Name);
			String phone_Number=scanner.nextLine();
			String query1="update address_book."+name+" set last_name = ?, address = ?, city = ?, state = ?, zipcode = ?, phone = ? where first_name = ?";
			PreparedStatement statement1=connection.prepareStatement(query1);
			statement1.setString(1, last_Name);
			statement1.setString(2, address);
			statement1.setString(3, city);
			statement1.setString(4, state);
			statement1.setString(5, zip_code);
			statement1.setString(6, phone_Number);
			statement1.setString(7, first_Name);
			statement1.executeUpdate();
			System.out.println("updated successfully");
			connection.close();
			scanner.close();
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
	private static void removePersonInfo(String name)
	{
		Scanner scanner=new Scanner(System.in);
		String query="delete from address_book."+name+" where first_name=?";
		Connection connection=DataAccessObject.dataBase();
		try 
		{
			PreparedStatement statement = connection.prepareStatement(query);
			System.out.println("please enter the first name of person you want to edit");
			String first_Name=scanner.nextLine();
			statement.setString(1, first_Name);
			statement.executeUpdate();
			System.out.println("deleted successfully");
			connection.close();
			scanner.close();
				
		}
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
	
	}

	
	
}
